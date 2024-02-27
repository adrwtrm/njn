package javassist.compiler;

import javassist.compiler.ast.ASTList;
import javassist.compiler.ast.ASTree;
import javassist.compiler.ast.ArrayInit;
import javassist.compiler.ast.AssignExpr;
import javassist.compiler.ast.BinExpr;
import javassist.compiler.ast.CallExpr;
import javassist.compiler.ast.CastExpr;
import javassist.compiler.ast.CondExpr;
import javassist.compiler.ast.Declarator;
import javassist.compiler.ast.DoubleConst;
import javassist.compiler.ast.Expr;
import javassist.compiler.ast.FieldDecl;
import javassist.compiler.ast.InstanceOfExpr;
import javassist.compiler.ast.IntConst;
import javassist.compiler.ast.Keyword;
import javassist.compiler.ast.Member;
import javassist.compiler.ast.MethodDecl;
import javassist.compiler.ast.NewExpr;
import javassist.compiler.ast.Pair;
import javassist.compiler.ast.Stmnt;
import javassist.compiler.ast.StringL;
import javassist.compiler.ast.Symbol;
import javassist.compiler.ast.Variable;

/* loaded from: classes2.dex */
public final class Parser implements TokenId {
    private static final int[] binaryOpPrecedence = {0, 0, 0, 0, 1, 6, 0, 0, 0, 1, 2, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0};
    private Lex lex;

    private static boolean isAssignOp(int i) {
        return i == 61 || i == 351 || i == 352 || i == 353 || i == 354 || i == 355 || i == 356 || i == 360 || i == 361 || i == 365 || i == 367 || i == 371;
    }

    private static boolean isBuiltinType(int i) {
        return i == 301 || i == 303 || i == 306 || i == 334 || i == 324 || i == 326 || i == 317 || i == 312;
    }

    public Parser(Lex lex) {
        this.lex = lex;
    }

    public boolean hasMore() {
        return this.lex.lookAhead() >= 0;
    }

    public ASTList parseMember(SymbolTable symbolTable) throws CompileError {
        ASTList parseMember1 = parseMember1(symbolTable);
        return parseMember1 instanceof MethodDecl ? parseMethod2(symbolTable, (MethodDecl) parseMember1) : parseMember1;
    }

    public ASTList parseMember1(SymbolTable symbolTable) throws CompileError {
        Declarator parseFormalType;
        ASTList parseMemberMods = parseMemberMods();
        boolean z = false;
        if (this.lex.lookAhead() == 400 && this.lex.lookAhead(1) == 40) {
            parseFormalType = new Declarator((int) TokenId.VOID, 0);
            z = true;
        } else {
            parseFormalType = parseFormalType(symbolTable);
        }
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        parseFormalType.setVariable(new Symbol(z ? "<init>" : this.lex.getString()));
        if (z || this.lex.lookAhead() == 40) {
            return parseMethod1(symbolTable, z, parseMemberMods, parseFormalType);
        }
        return parseField(symbolTable, parseMemberMods, parseFormalType);
    }

    private FieldDecl parseField(SymbolTable symbolTable, ASTList aSTList, Declarator declarator) throws CompileError {
        ASTree aSTree;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            aSTree = parseExpression(symbolTable);
        } else {
            aSTree = null;
        }
        int i = this.lex.get();
        if (i == 59) {
            return new FieldDecl(aSTList, new ASTList(declarator, new ASTList(aSTree)));
        }
        if (i == 44) {
            throw new CompileError("only one field can be declared in one declaration", this.lex);
        }
        throw new SyntaxError(this.lex);
    }

    private MethodDecl parseMethod1(SymbolTable symbolTable, boolean z, ASTList aSTList, Declarator declarator) throws CompileError {
        ASTList aSTList2;
        ASTList aSTList3;
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        if (this.lex.lookAhead() != 41) {
            aSTList2 = null;
            while (true) {
                aSTList2 = ASTList.append(aSTList2, parseFormalParam(symbolTable));
                int lookAhead = this.lex.lookAhead();
                if (lookAhead == 44) {
                    this.lex.get();
                } else if (lookAhead == 41) {
                    break;
                }
            }
        } else {
            aSTList2 = null;
        }
        this.lex.get();
        declarator.addArrayDim(parseArrayDimension());
        if (z && declarator.getArrayDim() > 0) {
            throw new SyntaxError(this.lex);
        }
        if (this.lex.lookAhead() == 341) {
            this.lex.get();
            aSTList3 = null;
            while (true) {
                aSTList3 = ASTList.append(aSTList3, parseClassType(symbolTable));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        } else {
            aSTList3 = null;
        }
        return new MethodDecl(aSTList, new ASTList(declarator, ASTList.make(aSTList2, aSTList3, null)));
    }

    public MethodDecl parseMethod2(SymbolTable symbolTable, MethodDecl methodDecl) throws CompileError {
        Stmnt parseBlock;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
            parseBlock = null;
        } else {
            parseBlock = parseBlock(symbolTable);
            if (parseBlock == null) {
                parseBlock = new Stmnt(66);
            }
        }
        methodDecl.sublist(4).setHead(parseBlock);
        return methodDecl;
    }

    private ASTList parseMemberMods() {
        ASTList aSTList = null;
        while (true) {
            int lookAhead = this.lex.lookAhead();
            if (lookAhead != 300 && lookAhead != 315 && lookAhead != 332 && lookAhead != 331 && lookAhead != 330 && lookAhead != 338 && lookAhead != 335 && lookAhead != 345 && lookAhead != 342 && lookAhead != 347) {
                return aSTList;
            }
            aSTList = new ASTList(new Keyword(this.lex.get()), aSTList);
        }
    }

    private Declarator parseFormalType(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead) || lookAhead == 344) {
            this.lex.get();
            return new Declarator(lookAhead, parseArrayDimension());
        }
        return new Declarator(parseClassType(symbolTable), parseArrayDimension());
    }

    private Declarator parseFormalParam(SymbolTable symbolTable) throws CompileError {
        Declarator parseFormalType = parseFormalType(symbolTable);
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        String string = this.lex.getString();
        parseFormalType.setVariable(new Symbol(string));
        parseFormalType.addArrayDim(parseArrayDimension());
        symbolTable.append(string, parseFormalType);
        return parseFormalType;
    }

    public Stmnt parseStatement(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        if (lookAhead == 123) {
            return parseBlock(symbolTable);
        }
        if (lookAhead == 59) {
            this.lex.get();
            return new Stmnt(66);
        } else if (lookAhead == 400 && this.lex.lookAhead(1) == 58) {
            this.lex.get();
            String string = this.lex.getString();
            this.lex.get();
            return Stmnt.make(76, new Symbol(string), parseStatement(symbolTable));
        } else if (lookAhead == 320) {
            return parseIf(symbolTable);
        } else {
            if (lookAhead == 346) {
                return parseWhile(symbolTable);
            }
            if (lookAhead == 311) {
                return parseDo(symbolTable);
            }
            if (lookAhead == 318) {
                return parseFor(symbolTable);
            }
            if (lookAhead == 343) {
                return parseTry(symbolTable);
            }
            if (lookAhead == 337) {
                return parseSwitch(symbolTable);
            }
            if (lookAhead == 338) {
                return parseSynchronized(symbolTable);
            }
            if (lookAhead == 333) {
                return parseReturn(symbolTable);
            }
            if (lookAhead == 340) {
                return parseThrow(symbolTable);
            }
            if (lookAhead == 302) {
                return parseBreak(symbolTable);
            }
            if (lookAhead == 309) {
                return parseContinue(symbolTable);
            }
            return parseDeclarationOrExpression(symbolTable, false);
        }
    }

    private Stmnt parseBlock(SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        Stmnt stmnt = null;
        while (this.lex.lookAhead() != 125) {
            Stmnt parseStatement = parseStatement(symbolTable2);
            if (parseStatement != null) {
                stmnt = (Stmnt) ASTList.concat(stmnt, new Stmnt(66, parseStatement));
            }
        }
        this.lex.get();
        return stmnt == null ? new Stmnt(66) : stmnt;
    }

    private Stmnt parseIf(SymbolTable symbolTable) throws CompileError {
        Stmnt stmnt;
        int i = this.lex.get();
        ASTree parseParExpression = parseParExpression(symbolTable);
        Stmnt parseStatement = parseStatement(symbolTable);
        if (this.lex.lookAhead() == 313) {
            this.lex.get();
            stmnt = parseStatement(symbolTable);
        } else {
            stmnt = null;
        }
        return new Stmnt(i, parseParExpression, new ASTList(parseStatement, new ASTList(stmnt)));
    }

    private Stmnt parseWhile(SymbolTable symbolTable) throws CompileError {
        return new Stmnt(this.lex.get(), parseParExpression(symbolTable), parseStatement(symbolTable));
    }

    private Stmnt parseDo(SymbolTable symbolTable) throws CompileError {
        int i = this.lex.get();
        Stmnt parseStatement = parseStatement(symbolTable);
        if (this.lex.get() != 346 || this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree parseExpression = parseExpression(symbolTable);
        if (this.lex.get() != 41 || this.lex.get() != 59) {
            throw new SyntaxError(this.lex);
        }
        return new Stmnt(i, parseExpression, parseStatement);
    }

    private Stmnt parseFor(SymbolTable symbolTable) throws CompileError {
        Stmnt parseDeclarationOrExpression;
        int i = this.lex.get();
        SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
            parseDeclarationOrExpression = null;
        } else {
            parseDeclarationOrExpression = parseDeclarationOrExpression(symbolTable2, true);
        }
        ASTree parseExpression = this.lex.lookAhead() == 59 ? null : parseExpression(symbolTable2);
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        Stmnt parseExprList = this.lex.lookAhead() != 41 ? parseExprList(symbolTable2) : null;
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        return new Stmnt(i, parseDeclarationOrExpression, new ASTList(parseExpression, new ASTList(parseExprList, parseStatement(symbolTable2))));
    }

    private Stmnt parseSwitch(SymbolTable symbolTable) throws CompileError {
        return new Stmnt(this.lex.get(), parseParExpression(symbolTable), parseSwitchBlock(symbolTable));
    }

    private Stmnt parseSwitchBlock(SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        Stmnt parseStmntOrCase = parseStmntOrCase(symbolTable2);
        if (parseStmntOrCase == null) {
            throw new CompileError("empty switch block", this.lex);
        }
        int operator = parseStmntOrCase.getOperator();
        if (operator != 304 && operator != 310) {
            throw new CompileError("no case or default in a switch block", this.lex);
        }
        Stmnt stmnt = new Stmnt(66, parseStmntOrCase);
        while (this.lex.lookAhead() != 125) {
            Stmnt parseStmntOrCase2 = parseStmntOrCase(symbolTable2);
            if (parseStmntOrCase2 != null) {
                int operator2 = parseStmntOrCase2.getOperator();
                if (operator2 == 304 || operator2 == 310) {
                    stmnt = (Stmnt) ASTList.concat(stmnt, new Stmnt(66, parseStmntOrCase2));
                    parseStmntOrCase = parseStmntOrCase2;
                } else {
                    parseStmntOrCase = (Stmnt) ASTList.concat(parseStmntOrCase, new Stmnt(66, parseStmntOrCase2));
                }
            }
        }
        this.lex.get();
        return stmnt;
    }

    private Stmnt parseStmntOrCase(SymbolTable symbolTable) throws CompileError {
        Stmnt stmnt;
        int lookAhead = this.lex.lookAhead();
        if (lookAhead != 304 && lookAhead != 310) {
            return parseStatement(symbolTable);
        }
        this.lex.get();
        if (lookAhead == 304) {
            stmnt = new Stmnt(lookAhead, parseExpression(symbolTable));
        } else {
            stmnt = new Stmnt(310);
        }
        if (this.lex.get() == 58) {
            return stmnt;
        }
        throw new CompileError(": is missing", this.lex);
    }

    private Stmnt parseSynchronized(SymbolTable symbolTable) throws CompileError {
        int i = this.lex.get();
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree parseExpression = parseExpression(symbolTable);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        return new Stmnt(i, parseExpression, parseBlock(symbolTable));
    }

    private Stmnt parseTry(SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        Stmnt parseBlock = parseBlock(symbolTable);
        Stmnt stmnt = null;
        ASTList aSTList = null;
        while (this.lex.lookAhead() == 305) {
            this.lex.get();
            if (this.lex.get() != 40) {
                throw new SyntaxError(this.lex);
            }
            SymbolTable symbolTable2 = new SymbolTable(symbolTable);
            Declarator parseFormalParam = parseFormalParam(symbolTable2);
            if (parseFormalParam.getArrayDim() > 0 || parseFormalParam.getType() != 307) {
                throw new SyntaxError(this.lex);
            }
            if (this.lex.get() != 41) {
                throw new SyntaxError(this.lex);
            }
            aSTList = ASTList.append(aSTList, new Pair(parseFormalParam, parseBlock(symbolTable2)));
        }
        if (this.lex.lookAhead() == 316) {
            this.lex.get();
            stmnt = parseBlock(symbolTable);
        }
        return Stmnt.make(TokenId.TRY, parseBlock, aSTList, stmnt);
    }

    private Stmnt parseReturn(SymbolTable symbolTable) throws CompileError {
        Stmnt stmnt = new Stmnt(this.lex.get());
        if (this.lex.lookAhead() != 59) {
            stmnt.setLeft(parseExpression(symbolTable));
        }
        if (this.lex.get() == 59) {
            return stmnt;
        }
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseThrow(SymbolTable symbolTable) throws CompileError {
        int i = this.lex.get();
        ASTree parseExpression = parseExpression(symbolTable);
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return new Stmnt(i, parseExpression);
    }

    private Stmnt parseBreak(SymbolTable symbolTable) throws CompileError {
        return parseContinue(symbolTable);
    }

    private Stmnt parseContinue(SymbolTable symbolTable) throws CompileError {
        Stmnt stmnt = new Stmnt(this.lex.get());
        int i = this.lex.get();
        if (i == 400) {
            stmnt.setLeft(new Symbol(this.lex.getString()));
            i = this.lex.get();
        }
        if (i == 59) {
            return stmnt;
        }
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseDeclarationOrExpression(SymbolTable symbolTable, boolean z) throws CompileError {
        Stmnt stmnt;
        int nextIsClassType;
        int lookAhead = this.lex.lookAhead();
        while (lookAhead == 315) {
            this.lex.get();
            lookAhead = this.lex.lookAhead();
        }
        if (isBuiltinType(lookAhead)) {
            return parseDeclarators(symbolTable, new Declarator(this.lex.get(), parseArrayDimension()));
        }
        if (lookAhead == 400 && (nextIsClassType = nextIsClassType(0)) >= 0 && this.lex.lookAhead(nextIsClassType) == 400) {
            return parseDeclarators(symbolTable, new Declarator(parseClassType(symbolTable), parseArrayDimension()));
        }
        if (z) {
            stmnt = parseExprList(symbolTable);
        } else {
            stmnt = new Stmnt(69, parseExpression(symbolTable));
        }
        if (this.lex.get() == 59) {
            return stmnt;
        }
        throw new CompileError("; is missing", this.lex);
    }

    private Stmnt parseExprList(SymbolTable symbolTable) throws CompileError {
        Stmnt stmnt = null;
        while (true) {
            stmnt = (Stmnt) ASTList.concat(stmnt, new Stmnt(66, new Stmnt(69, parseExpression(symbolTable))));
            if (this.lex.lookAhead() != 44) {
                return stmnt;
            }
            this.lex.get();
        }
    }

    private Stmnt parseDeclarators(SymbolTable symbolTable, Declarator declarator) throws CompileError {
        int i;
        Stmnt stmnt = null;
        do {
            stmnt = (Stmnt) ASTList.concat(stmnt, new Stmnt(68, parseDeclarator(symbolTable, declarator)));
            i = this.lex.get();
            if (i == 59) {
                return stmnt;
            }
        } while (i == 44);
        throw new CompileError("; is missing", this.lex);
    }

    private Declarator parseDeclarator(SymbolTable symbolTable, Declarator declarator) throws CompileError {
        ASTree aSTree;
        if (this.lex.get() != 400 || declarator.getType() == 344) {
            throw new SyntaxError(this.lex);
        }
        String string = this.lex.getString();
        Symbol symbol = new Symbol(string);
        int parseArrayDimension = parseArrayDimension();
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            aSTree = parseInitializer(symbolTable);
        } else {
            aSTree = null;
        }
        Declarator make = declarator.make(symbol, parseArrayDimension, aSTree);
        symbolTable.append(string, make);
        return make;
    }

    private ASTree parseInitializer(SymbolTable symbolTable) throws CompileError {
        if (this.lex.lookAhead() == 123) {
            return parseArrayInitializer(symbolTable);
        }
        return parseExpression(symbolTable);
    }

    private ArrayInit parseArrayInitializer(SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        ArrayInit arrayInit = new ArrayInit(parseExpression(symbolTable));
        while (this.lex.lookAhead() == 44) {
            this.lex.get();
            ASTList.append(arrayInit, parseExpression(symbolTable));
        }
        if (this.lex.get() == 125) {
            return arrayInit;
        }
        throw new SyntaxError(this.lex);
    }

    private ASTree parseParExpression(SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree parseExpression = parseExpression(symbolTable);
        if (this.lex.get() == 41) {
            return parseExpression;
        }
        throw new SyntaxError(this.lex);
    }

    public ASTree parseExpression(SymbolTable symbolTable) throws CompileError {
        ASTree parseConditionalExpr = parseConditionalExpr(symbolTable);
        return !isAssignOp(this.lex.lookAhead()) ? parseConditionalExpr : AssignExpr.makeAssign(this.lex.get(), parseConditionalExpr, parseExpression(symbolTable));
    }

    private ASTree parseConditionalExpr(SymbolTable symbolTable) throws CompileError {
        ASTree parseBinaryExpr = parseBinaryExpr(symbolTable);
        if (this.lex.lookAhead() == 63) {
            this.lex.get();
            ASTree parseExpression = parseExpression(symbolTable);
            if (this.lex.get() != 58) {
                throw new CompileError(": is missing", this.lex);
            }
            return new CondExpr(parseBinaryExpr, parseExpression, parseExpression(symbolTable));
        }
        return parseBinaryExpr;
    }

    private ASTree parseBinaryExpr(SymbolTable symbolTable) throws CompileError {
        ASTree parseUnaryExpr = parseUnaryExpr(symbolTable);
        while (true) {
            int opPrecedence = getOpPrecedence(this.lex.lookAhead());
            if (opPrecedence == 0) {
                return parseUnaryExpr;
            }
            parseUnaryExpr = binaryExpr2(symbolTable, parseUnaryExpr, opPrecedence);
        }
    }

    private ASTree parseInstanceOf(SymbolTable symbolTable, ASTree aSTree) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead)) {
            this.lex.get();
            return new InstanceOfExpr(lookAhead, parseArrayDimension(), aSTree);
        }
        return new InstanceOfExpr(parseClassType(symbolTable), parseArrayDimension(), aSTree);
    }

    private ASTree binaryExpr2(SymbolTable symbolTable, ASTree aSTree, int i) throws CompileError {
        int i2 = this.lex.get();
        if (i2 == 323) {
            return parseInstanceOf(symbolTable, aSTree);
        }
        ASTree parseUnaryExpr = parseUnaryExpr(symbolTable);
        while (true) {
            int opPrecedence = getOpPrecedence(this.lex.lookAhead());
            if (opPrecedence == 0 || i <= opPrecedence) {
                break;
            }
            parseUnaryExpr = binaryExpr2(symbolTable, parseUnaryExpr, opPrecedence);
        }
        return BinExpr.makeBin(i2, aSTree, parseUnaryExpr);
    }

    private int getOpPrecedence(int i) {
        if (33 > i || i > 63) {
            if (i == 94) {
                return 7;
            }
            if (i == 124) {
                return 8;
            }
            if (i == 369) {
                return 9;
            }
            if (i == 368) {
                return 10;
            }
            if (i == 358 || i == 350) {
                return 5;
            }
            if (i == 357 || i == 359 || i == 323) {
                return 4;
            }
            return (i == 364 || i == 366 || i == 370) ? 3 : 0;
        }
        return binaryOpPrecedence[i - 33];
    }

    private ASTree parseUnaryExpr(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        if (lookAhead != 33) {
            if (lookAhead == 40) {
                return parseCast(symbolTable);
            }
            if (lookAhead != 43 && lookAhead != 45 && lookAhead != 126 && lookAhead != 362 && lookAhead != 363) {
                return parsePostfix(symbolTable);
            }
        }
        int i = this.lex.get();
        if (i == 45) {
            int lookAhead2 = this.lex.lookAhead();
            switch (lookAhead2) {
                case 401:
                case 402:
                case 403:
                    this.lex.get();
                    return new IntConst(-this.lex.getLong(), lookAhead2);
                case TokenId.FloatConstant /* 404 */:
                case TokenId.DoubleConstant /* 405 */:
                    this.lex.get();
                    return new DoubleConst(-this.lex.getDouble(), lookAhead2);
            }
        }
        return Expr.make(i, parseUnaryExpr(symbolTable));
    }

    private ASTree parseCast(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead(1);
        if (isBuiltinType(lookAhead) && nextIsBuiltinCast()) {
            this.lex.get();
            this.lex.get();
            int parseArrayDimension = parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(lookAhead, parseArrayDimension, parseUnaryExpr(symbolTable));
        } else if (lookAhead == 400 && nextIsClassCast()) {
            this.lex.get();
            ASTList parseClassType = parseClassType(symbolTable);
            int parseArrayDimension2 = parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(parseClassType, parseArrayDimension2, parseUnaryExpr(symbolTable));
        } else {
            return parsePostfix(symbolTable);
        }
    }

    private boolean nextIsBuiltinCast() {
        int i = 2;
        while (true) {
            int i2 = i + 1;
            if (this.lex.lookAhead(i) != 91) {
                return this.lex.lookAhead(i2 - 1) == 41;
            }
            int i3 = i2 + 1;
            if (this.lex.lookAhead(i2) != 93) {
                return false;
            }
            i = i3;
        }
    }

    private boolean nextIsClassCast() {
        int nextIsClassType = nextIsClassType(1);
        if (nextIsClassType >= 0 && this.lex.lookAhead(nextIsClassType) == 41) {
            int lookAhead = this.lex.lookAhead(nextIsClassType + 1);
            return lookAhead == 40 || lookAhead == 412 || lookAhead == 406 || lookAhead == 400 || lookAhead == 339 || lookAhead == 336 || lookAhead == 328 || lookAhead == 410 || lookAhead == 411 || lookAhead == 403 || lookAhead == 402 || lookAhead == 401 || lookAhead == 405 || lookAhead == 404;
        }
        return false;
    }

    private int nextIsClassType(int i) {
        do {
            int i2 = i + 1;
            if (this.lex.lookAhead(i2) == 46) {
                i = i2 + 1;
            } else {
                while (true) {
                    int i3 = i2 + 1;
                    if (this.lex.lookAhead(i2) != 91) {
                        return i3 - 1;
                    }
                    int i4 = i3 + 1;
                    if (this.lex.lookAhead(i3) != 93) {
                        return -1;
                    }
                    i2 = i4;
                }
            }
        } while (this.lex.lookAhead(i) == 400);
        return -1;
    }

    private int parseArrayDimension() throws CompileError {
        int i = 0;
        while (this.lex.lookAhead() == 91) {
            i++;
            this.lex.get();
            if (this.lex.get() != 93) {
                throw new CompileError("] is missing", this.lex);
            }
        }
        return i;
    }

    private ASTList parseClassType(SymbolTable symbolTable) throws CompileError {
        ASTList aSTList = null;
        while (this.lex.get() == 400) {
            aSTList = ASTList.append(aSTList, new Symbol(this.lex.getString()));
            if (this.lex.lookAhead() != 46) {
                return aSTList;
            }
            this.lex.get();
        }
        throw new SyntaxError(this.lex);
    }

    private ASTree parsePostfix(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        switch (lookAhead) {
            case 401:
            case 402:
            case 403:
                this.lex.get();
                return new IntConst(this.lex.getLong(), lookAhead);
            case TokenId.FloatConstant /* 404 */:
            case TokenId.DoubleConstant /* 405 */:
                this.lex.get();
                return new DoubleConst(this.lex.getDouble(), lookAhead);
            default:
                ASTree parsePrimaryExpr = parsePrimaryExpr(symbolTable);
                while (true) {
                    int lookAhead2 = this.lex.lookAhead();
                    if (lookAhead2 == 35) {
                        this.lex.get();
                        if (this.lex.get() != 400) {
                            throw new CompileError("missing static member name", this.lex);
                        }
                        parsePrimaryExpr = Expr.make(35, new Symbol(toClassName(parsePrimaryExpr)), new Member(this.lex.getString()));
                    } else if (lookAhead2 == 40) {
                        parsePrimaryExpr = parseMethodCall(symbolTable, parsePrimaryExpr);
                    } else if (lookAhead2 == 46) {
                        this.lex.get();
                        int i = this.lex.get();
                        if (i == 307) {
                            parsePrimaryExpr = parseDotClass(parsePrimaryExpr, 0);
                        } else if (i == 336) {
                            parsePrimaryExpr = Expr.make(46, new Symbol(toClassName(parsePrimaryExpr)), new Keyword(i));
                        } else if (i == 400) {
                            parsePrimaryExpr = Expr.make(46, parsePrimaryExpr, new Member(this.lex.getString()));
                        } else {
                            throw new CompileError("missing member name", this.lex);
                        }
                    } else if (lookAhead2 != 91) {
                        if (lookAhead2 != 362 && lookAhead2 != 363) {
                            return parsePrimaryExpr;
                        }
                        parsePrimaryExpr = Expr.make(this.lex.get(), (ASTree) null, parsePrimaryExpr);
                    } else if (this.lex.lookAhead(1) == 93) {
                        int parseArrayDimension = parseArrayDimension();
                        if (this.lex.get() == 46 && this.lex.get() == 307) {
                            parsePrimaryExpr = parseDotClass(parsePrimaryExpr, parseArrayDimension);
                        }
                    } else {
                        ASTree parseArrayIndex = parseArrayIndex(symbolTable);
                        if (parseArrayIndex == null) {
                            throw new SyntaxError(this.lex);
                        }
                        parsePrimaryExpr = Expr.make(65, parsePrimaryExpr, parseArrayIndex);
                    }
                }
                throw new SyntaxError(this.lex);
        }
    }

    private ASTree parseDotClass(ASTree aSTree, int i) throws CompileError {
        String className = toClassName(aSTree);
        if (i > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                }
                stringBuffer.append('[');
                i = i2;
            }
            stringBuffer.append('L').append(className.replace('.', '/')).append(';');
            className = stringBuffer.toString();
        }
        return Expr.make(46, new Symbol(className), new Member("class"));
    }

    private ASTree parseDotClass(int i, int i2) throws CompileError {
        String str;
        if (i2 > 0) {
            return Expr.make(46, new Symbol(CodeGen.toJvmTypeName(i, i2)), new Member("class"));
        }
        if (i == 301) {
            str = "java.lang.Boolean";
        } else if (i == 303) {
            str = "java.lang.Byte";
        } else if (i == 306) {
            str = "java.lang.Character";
        } else if (i == 312) {
            str = "java.lang.Double";
        } else if (i == 317) {
            str = "java.lang.Float";
        } else if (i == 324) {
            str = "java.lang.Integer";
        } else if (i == 326) {
            str = "java.lang.Long";
        } else if (i == 334) {
            str = "java.lang.Short";
        } else if (i != 344) {
            throw new CompileError("invalid builtin type: " + i);
        } else {
            str = "java.lang.Void";
        }
        return Expr.make(35, new Symbol(str), new Member("TYPE"));
    }

    private ASTree parseMethodCall(SymbolTable symbolTable, ASTree aSTree) throws CompileError {
        int operator;
        if (aSTree instanceof Keyword) {
            int i = ((Keyword) aSTree).get();
            if (i != 339 && i != 336) {
                throw new SyntaxError(this.lex);
            }
        } else if (!(aSTree instanceof Symbol) && (aSTree instanceof Expr) && (operator = ((Expr) aSTree).getOperator()) != 46 && operator != 35) {
            throw new SyntaxError(this.lex);
        }
        return CallExpr.makeCall(aSTree, parseArgumentList(symbolTable));
    }

    private String toClassName(ASTree aSTree) throws CompileError {
        StringBuffer stringBuffer = new StringBuffer();
        toClassName(aSTree, stringBuffer);
        return stringBuffer.toString();
    }

    private void toClassName(ASTree aSTree, StringBuffer stringBuffer) throws CompileError {
        if (aSTree instanceof Symbol) {
            stringBuffer.append(((Symbol) aSTree).get());
            return;
        }
        if (aSTree instanceof Expr) {
            Expr expr = (Expr) aSTree;
            if (expr.getOperator() == 46) {
                toClassName(expr.oprand1(), stringBuffer);
                stringBuffer.append('.');
                toClassName(expr.oprand2(), stringBuffer);
                return;
            }
        }
        throw new CompileError("bad static member access", this.lex);
    }

    private ASTree parsePrimaryExpr(SymbolTable symbolTable) throws CompileError {
        int i = this.lex.get();
        if (i == 40) {
            ASTree parseExpression = parseExpression(symbolTable);
            if (this.lex.get() == 41) {
                return parseExpression;
            }
            throw new CompileError(") is missing", this.lex);
        } else if (i != 328) {
            if (i != 336 && i != 339) {
                if (i == 400) {
                    String string = this.lex.getString();
                    Declarator lookup = symbolTable.lookup(string);
                    if (lookup == null) {
                        return new Member(string);
                    }
                    return new Variable(string, lookup);
                } else if (i == 406) {
                    return new StringL(this.lex.getString());
                } else {
                    switch (i) {
                        case TokenId.TRUE /* 410 */:
                        case TokenId.FALSE /* 411 */:
                        case TokenId.NULL /* 412 */:
                            break;
                        default:
                            if (isBuiltinType(i) || i == 344) {
                                int parseArrayDimension = parseArrayDimension();
                                if (this.lex.get() == 46 && this.lex.get() == 307) {
                                    return parseDotClass(i, parseArrayDimension);
                                }
                            }
                            throw new SyntaxError(this.lex);
                    }
                }
            }
            return new Keyword(i);
        } else {
            return parseNew(symbolTable);
        }
    }

    private NewExpr parseNew(SymbolTable symbolTable) throws CompileError {
        int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead)) {
            this.lex.get();
            return new NewExpr(lookAhead, parseArraySize(symbolTable), this.lex.lookAhead() == 123 ? parseArrayInitializer(symbolTable) : null);
        }
        if (lookAhead == 400) {
            ASTList parseClassType = parseClassType(symbolTable);
            int lookAhead2 = this.lex.lookAhead();
            if (lookAhead2 == 40) {
                return new NewExpr(parseClassType, parseArgumentList(symbolTable));
            }
            if (lookAhead2 == 91) {
                return NewExpr.makeObjectArray(parseClassType, parseArraySize(symbolTable), this.lex.lookAhead() == 123 ? parseArrayInitializer(symbolTable) : null);
            }
        }
        throw new SyntaxError(this.lex);
    }

    private ASTList parseArraySize(SymbolTable symbolTable) throws CompileError {
        ASTList aSTList = null;
        while (this.lex.lookAhead() == 91) {
            aSTList = ASTList.append(aSTList, parseArrayIndex(symbolTable));
        }
        return aSTList;
    }

    private ASTree parseArrayIndex(SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 93) {
            this.lex.get();
            return null;
        }
        ASTree parseExpression = parseExpression(symbolTable);
        if (this.lex.get() == 93) {
            return parseExpression;
        }
        throw new CompileError("] is missing", this.lex);
    }

    private ASTList parseArgumentList(SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 40) {
            throw new CompileError("( is missing", this.lex);
        }
        ASTList aSTList = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                aSTList = ASTList.append(aSTList, parseExpression(symbolTable));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        }
        if (this.lex.get() == 41) {
            return aSTList;
        }
        throw new CompileError(") is missing", this.lex);
    }
}
