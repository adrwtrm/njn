package javassist.compiler;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtPrimitiveType;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.FieldDecl;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.MethodDecl;
import javassist.compiler.ast.Stmnt;
import javassist.compiler.ast.Symbol;

/* loaded from: classes2.dex */
public class Javac {
    public static final String param0Name = "$0";
    public static final String proceedName = "$proceed";
    public static final String resultVarName = "$_";
    private Bytecode bytecode;
    JvstCodeGen gen;
    SymbolTable stable;

    public Javac(CtClass ctClass) {
        this(new Bytecode(ctClass.getClassFile2().getConstPool(), 0, 0), ctClass);
    }

    public Javac(Bytecode bytecode, CtClass ctClass) {
        this.gen = new JvstCodeGen(bytecode, ctClass, ctClass.getClassPool());
        this.stable = new SymbolTable();
        this.bytecode = bytecode;
    }

    public Bytecode getBytecode() {
        return this.bytecode;
    }

    public CtMember compile(String str) throws CompileError {
        Parser parser = new Parser(new Lex(str));
        ASTList parseMember1 = parser.parseMember1(this.stable);
        try {
            if (parseMember1 instanceof FieldDecl) {
                return compileField((FieldDecl) parseMember1);
            }
            CtBehavior compileMethod = compileMethod(parser, (MethodDecl) parseMember1);
            CtClass declaringClass = compileMethod.getDeclaringClass();
            compileMethod.getMethodInfo2().rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
            return compileMethod;
        } catch (CannotCompileException e) {
            throw new CompileError(e.getMessage());
        } catch (BadBytecode e2) {
            throw new CompileError(e2.getMessage());
        }
    }

    /* loaded from: classes2.dex */
    public static class CtFieldWithInit extends CtField {
        private ASTree init;

        CtFieldWithInit(CtClass ctClass, String str, CtClass ctClass2) throws CannotCompileException {
            super(ctClass, str, ctClass2);
            this.init = null;
        }

        protected void setInit(ASTree aSTree) {
            this.init = aSTree;
        }

        @Override // javassist.CtField
        protected ASTree getInitAST() {
            return this.init;
        }
    }

    private CtField compileField(FieldDecl fieldDecl) throws CompileError, CannotCompileException {
        Declarator declarator = fieldDecl.getDeclarator();
        CtFieldWithInit ctFieldWithInit = new CtFieldWithInit(this.gen.resolver.lookupClass(declarator), declarator.getVariable().get(), this.gen.getThisClass());
        ctFieldWithInit.setModifiers(MemberResolver.getModifiers(fieldDecl.getModifiers()));
        if (fieldDecl.getInit() != null) {
            ctFieldWithInit.setInit(fieldDecl.getInit());
        }
        return ctFieldWithInit;
    }

    private CtBehavior compileMethod(Parser parser, MethodDecl methodDecl) throws CompileError {
        int modifiers = MemberResolver.getModifiers(methodDecl.getModifiers());
        CtClass[] makeParamList = this.gen.makeParamList(methodDecl);
        CtClass[] makeThrowsList = this.gen.makeThrowsList(methodDecl);
        recordParams(makeParamList, Modifier.isStatic(modifiers));
        MethodDecl parseMethod2 = parser.parseMethod2(this.stable, methodDecl);
        try {
            if (parseMethod2.isConstructor()) {
                CtConstructor ctConstructor = new CtConstructor(makeParamList, this.gen.getThisClass());
                ctConstructor.setModifiers(modifiers);
                parseMethod2.accept(this.gen);
                ctConstructor.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
                ctConstructor.setExceptionTypes(makeThrowsList);
                return ctConstructor;
            }
            Declarator declarator = parseMethod2.getReturn();
            CtClass lookupClass = this.gen.resolver.lookupClass(declarator);
            recordReturnType(lookupClass, false);
            CtMethod ctMethod = new CtMethod(lookupClass, declarator.getVariable().get(), makeParamList, this.gen.getThisClass());
            ctMethod.setModifiers(modifiers);
            this.gen.setThisMethod(ctMethod);
            parseMethod2.accept(this.gen);
            if (parseMethod2.getBody() != null) {
                ctMethod.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
            } else {
                ctMethod.setModifiers(modifiers | 1024);
            }
            ctMethod.setExceptionTypes(makeThrowsList);
            return ctMethod;
        } catch (NotFoundException e) {
            throw new CompileError(e.toString());
        }
    }

    public Bytecode compileBody(CtBehavior ctBehavior, String str) throws CompileError {
        CtClass ctClass;
        try {
            recordParams(ctBehavior.getParameterTypes(), Modifier.isStatic(ctBehavior.getModifiers()));
            if (ctBehavior instanceof CtMethod) {
                this.gen.setThisMethod((CtMethod) ctBehavior);
                ctClass = ((CtMethod) ctBehavior).getReturnType();
            } else {
                ctClass = CtClass.voidType;
            }
            recordReturnType(ctClass, false);
            boolean z = ctClass == CtClass.voidType;
            if (str == null) {
                makeDefaultBody(this.bytecode, ctClass);
            } else {
                Parser parser = new Parser(new Lex(str));
                Stmnt parseStatement = parser.parseStatement(new SymbolTable(this.stable));
                if (parser.hasMore()) {
                    throw new CompileError("the method/constructor body must be surrounded by {}");
                }
                this.gen.atMethodBody(parseStatement, ctBehavior instanceof CtConstructor ? !((CtConstructor) ctBehavior).isClassInitializer() : false, z);
            }
            return this.bytecode;
        } catch (NotFoundException e) {
            throw new CompileError(e.toString());
        }
    }

    private static void makeDefaultBody(Bytecode bytecode, CtClass ctClass) {
        int i;
        int i2;
        if (ctClass instanceof CtPrimitiveType) {
            i = ((CtPrimitiveType) ctClass).getReturnOp();
            i2 = i == 175 ? 14 : i == 174 ? 11 : i == 173 ? 9 : i == 177 ? 0 : 3;
        } else {
            i = 176;
            i2 = 1;
        }
        if (i2 != 0) {
            bytecode.addOpcode(i2);
        }
        bytecode.addOpcode(i);
    }

    public boolean recordLocalVariables(CodeAttribute codeAttribute, int i) throws CompileError {
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (localVariableAttribute == null) {
            return false;
        }
        int tableLength = localVariableAttribute.tableLength();
        for (int i2 = 0; i2 < tableLength; i2++) {
            int startPc = localVariableAttribute.startPc(i2);
            int codeLength = localVariableAttribute.codeLength(i2);
            if (startPc <= i && i < startPc + codeLength) {
                this.gen.recordVariable(localVariableAttribute.descriptor(i2), localVariableAttribute.variableName(i2), localVariableAttribute.index(i2), this.stable);
            }
        }
        return true;
    }

    public boolean recordParamNames(CodeAttribute codeAttribute, int i) throws CompileError {
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (localVariableAttribute == null) {
            return false;
        }
        int tableLength = localVariableAttribute.tableLength();
        for (int i2 = 0; i2 < tableLength; i2++) {
            int index = localVariableAttribute.index(i2);
            if (index < i) {
                this.gen.recordVariable(localVariableAttribute.descriptor(i2), localVariableAttribute.variableName(i2), index, this.stable);
            }
        }
        return true;
    }

    public int recordParams(CtClass[] ctClassArr, boolean z) throws CompileError {
        return this.gen.recordParams(ctClassArr, z, "$", "$args", "$$", this.stable);
    }

    public int recordParams(String str, CtClass[] ctClassArr, boolean z, int i, boolean z2) throws CompileError {
        return this.gen.recordParams(ctClassArr, z2, "$", "$args", "$$", z, i, str, this.stable);
    }

    public void setMaxLocals(int i) {
        this.gen.setMaxLocals(i);
    }

    public int recordReturnType(CtClass ctClass, boolean z) throws CompileError {
        this.gen.recordType(ctClass);
        return this.gen.recordReturnType(ctClass, "$r", z ? resultVarName : null, this.stable);
    }

    public void recordType(CtClass ctClass) {
        this.gen.recordType(ctClass);
    }

    public int recordVariable(CtClass ctClass, String str) throws CompileError {
        return this.gen.recordVariable(ctClass, str, this.stable);
    }

    public void recordProceed(String str, final String str2) throws CompileError {
        final ASTree parseExpression = new Parser(new Lex(str)).parseExpression(this.stable);
        this.gen.setProceedHandler(new ProceedHandler() { // from class: javassist.compiler.Javac.1
            {
                Javac.this = this;
            }

            @Override // javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
                ASTree member = new Member(str2);
                ASTree aSTree = parseExpression;
                if (aSTree != null) {
                    member = Expr.make(46, aSTree, member);
                }
                jvstCodeGen.compileExpr(CallExpr.makeCall(member, aSTList));
                jvstCodeGen.addNullIfVoid();
            }

            @Override // javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
                ASTree member = new Member(str2);
                ASTree aSTree = parseExpression;
                if (aSTree != null) {
                    member = Expr.make(46, aSTree, member);
                }
                CallExpr.makeCall(member, aSTList).accept(jvstTypeChecker);
                jvstTypeChecker.addNullIfVoid();
            }
        }, proceedName);
    }

    public void recordStaticProceed(final String str, final String str2) throws CompileError {
        this.gen.setProceedHandler(new ProceedHandler() { // from class: javassist.compiler.Javac.2
            {
                Javac.this = this;
            }

            @Override // javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
                jvstCodeGen.compileExpr(CallExpr.makeCall(Expr.make(35, new Symbol(str), new Member(str2)), aSTList));
                jvstCodeGen.addNullIfVoid();
            }

            @Override // javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
                CallExpr.makeCall(Expr.make(35, new Symbol(str), new Member(str2)), aSTList).accept(jvstTypeChecker);
                jvstTypeChecker.addNullIfVoid();
            }
        }, proceedName);
    }

    public void recordSpecialProceed(String str, final String str2, final String str3, final String str4, final int i) throws CompileError {
        final ASTree parseExpression = new Parser(new Lex(str)).parseExpression(this.stable);
        this.gen.setProceedHandler(new ProceedHandler() { // from class: javassist.compiler.Javac.3
            {
                Javac.this = this;
            }

            @Override // javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError {
                jvstCodeGen.compileInvokeSpecial(parseExpression, i, str4, aSTList);
            }

            @Override // javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError {
                jvstTypeChecker.compileInvokeSpecial(parseExpression, str2, str3, str4, aSTList);
            }
        }, proceedName);
    }

    public void recordProceed(ProceedHandler proceedHandler) {
        this.gen.setProceedHandler(proceedHandler, proceedName);
    }

    public void compileStmnt(String str) throws CompileError {
        Parser parser = new Parser(new Lex(str));
        SymbolTable symbolTable = new SymbolTable(this.stable);
        while (parser.hasMore()) {
            Stmnt parseStatement = parser.parseStatement(symbolTable);
            if (parseStatement != null) {
                parseStatement.accept(this.gen);
            }
        }
    }

    public void compileExpr(String str) throws CompileError {
        compileExpr(parseExpr(str, this.stable));
    }

    public static ASTree parseExpr(String str, SymbolTable symbolTable) throws CompileError {
        return new Parser(new Lex(str)).parseExpression(symbolTable);
    }

    public void compileExpr(ASTree aSTree) throws CompileError {
        if (aSTree != null) {
            this.gen.compileExpr(aSTree);
        }
    }
}
