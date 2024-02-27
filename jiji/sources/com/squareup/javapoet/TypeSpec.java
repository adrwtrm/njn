package com.squareup.javapoet;

import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.squareup.javapoet.CodeBlock;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/* loaded from: classes2.dex */
public final class TypeSpec {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final Set<String> alwaysQualifiedNames;
    public final List<AnnotationSpec> annotations;
    public final CodeBlock anonymousTypeArguments;
    public final Map<String, TypeSpec> enumConstants;
    public final List<FieldSpec> fieldSpecs;
    public final CodeBlock initializerBlock;
    public final CodeBlock javadoc;
    public final Kind kind;
    public final List<MethodSpec> methodSpecs;
    public final Set<Modifier> modifiers;
    public final String name;
    final Set<String> nestedTypesSimpleNames;
    public final List<Element> originatingElements;
    public final CodeBlock staticBlock;
    public final TypeName superclass;
    public final List<TypeName> superinterfaces;
    public final List<TypeSpec> typeSpecs;
    public final List<TypeVariableName> typeVariables;

    private TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.anonymousTypeArguments = builder.anonymousTypeArguments;
        this.javadoc = builder.javadoc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.typeVariables = Util.immutableList(builder.typeVariables);
        this.superclass = builder.superclass;
        this.superinterfaces = Util.immutableList(builder.superinterfaces);
        this.enumConstants = Util.immutableMap(builder.enumConstants);
        this.fieldSpecs = Util.immutableList(builder.fieldSpecs);
        this.staticBlock = builder.staticBlock.build();
        this.initializerBlock = builder.initializerBlock.build();
        this.methodSpecs = Util.immutableList(builder.methodSpecs);
        this.typeSpecs = Util.immutableList(builder.typeSpecs);
        this.alwaysQualifiedNames = Util.immutableSet(builder.alwaysQualifiedNames);
        this.nestedTypesSimpleNames = new HashSet(builder.typeSpecs.size());
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(builder.originatingElements);
        for (TypeSpec typeSpec : builder.typeSpecs) {
            this.nestedTypesSimpleNames.add(typeSpec.name);
            arrayList.addAll(typeSpec.originatingElements);
        }
        this.originatingElements = Util.immutableList(arrayList);
    }

    private TypeSpec(TypeSpec typeSpec) {
        this.kind = typeSpec.kind;
        this.name = typeSpec.name;
        this.anonymousTypeArguments = null;
        this.javadoc = typeSpec.javadoc;
        this.annotations = Collections.emptyList();
        this.modifiers = Collections.emptySet();
        this.typeVariables = Collections.emptyList();
        this.superclass = null;
        this.superinterfaces = Collections.emptyList();
        this.enumConstants = Collections.emptyMap();
        this.fieldSpecs = Collections.emptyList();
        this.staticBlock = typeSpec.staticBlock;
        this.initializerBlock = typeSpec.initializerBlock;
        this.methodSpecs = Collections.emptyList();
        this.typeSpecs = Collections.emptyList();
        this.originatingElements = Collections.emptyList();
        this.nestedTypesSimpleNames = Collections.emptySet();
        this.alwaysQualifiedNames = Collections.emptySet();
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    public static Builder classBuilder(String str) {
        return new Builder(Kind.CLASS, (String) Util.checkNotNull(str, "name == null", new Object[0]), null);
    }

    public static Builder classBuilder(ClassName className) {
        return classBuilder(((ClassName) Util.checkNotNull(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder interfaceBuilder(String str) {
        return new Builder(Kind.INTERFACE, (String) Util.checkNotNull(str, "name == null", new Object[0]), null);
    }

    public static Builder interfaceBuilder(ClassName className) {
        return interfaceBuilder(((ClassName) Util.checkNotNull(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder enumBuilder(String str) {
        return new Builder(Kind.ENUM, (String) Util.checkNotNull(str, "name == null", new Object[0]), null);
    }

    public static Builder enumBuilder(ClassName className) {
        return enumBuilder(((ClassName) Util.checkNotNull(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder anonymousClassBuilder(String str, Object... objArr) {
        return anonymousClassBuilder(CodeBlock.of(str, objArr));
    }

    public static Builder anonymousClassBuilder(CodeBlock codeBlock) {
        return new Builder(Kind.CLASS, null, codeBlock);
    }

    public static Builder annotationBuilder(String str) {
        return new Builder(Kind.ANNOTATION, (String) Util.checkNotNull(str, "name == null", new Object[0]), null);
    }

    public static Builder annotationBuilder(ClassName className) {
        return annotationBuilder(((ClassName) Util.checkNotNull(className, "className == null", new Object[0])).simpleName());
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.kind, this.name, this.anonymousTypeArguments);
        builder.javadoc.add(this.javadoc);
        builder.annotations.addAll(this.annotations);
        builder.modifiers.addAll(this.modifiers);
        builder.typeVariables.addAll(this.typeVariables);
        builder.superclass = this.superclass;
        builder.superinterfaces.addAll(this.superinterfaces);
        builder.enumConstants.putAll(this.enumConstants);
        builder.fieldSpecs.addAll(this.fieldSpecs);
        builder.methodSpecs.addAll(this.methodSpecs);
        builder.typeSpecs.addAll(this.typeSpecs);
        builder.initializerBlock.add(this.initializerBlock);
        builder.staticBlock.add(this.staticBlock);
        builder.originatingElements.addAll(this.originatingElements);
        builder.alwaysQualifiedNames.addAll(this.alwaysQualifiedNames);
        return builder;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void emit(CodeWriter codeWriter, String str, Set<Modifier> set) throws IOException {
        List<TypeName> singletonList;
        List<TypeName> list;
        int i = codeWriter.statementLine;
        codeWriter.statementLine = -1;
        boolean z = true;
        try {
            if (str != null) {
                codeWriter.emitJavadoc(this.javadoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emit("$L", str);
                if (!this.anonymousTypeArguments.formatParts.isEmpty()) {
                    codeWriter.emit("(");
                    codeWriter.emit(this.anonymousTypeArguments);
                    codeWriter.emit(")");
                }
                if (this.fieldSpecs.isEmpty() && this.methodSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                    return;
                }
                codeWriter.emit(" {\n");
            } else if (this.anonymousTypeArguments != null) {
                codeWriter.emit("new $T(", !this.superinterfaces.isEmpty() ? this.superinterfaces.get(0) : this.superclass);
                codeWriter.emit(this.anonymousTypeArguments);
                codeWriter.emit(") {\n");
            } else {
                codeWriter.pushType(new TypeSpec(this));
                codeWriter.emitJavadoc(this.javadoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emitModifiers(this.modifiers, Util.union(set, this.kind.asMemberModifiers));
                if (this.kind == Kind.ANNOTATION) {
                    codeWriter.emit("$L $L", "@interface", this.name);
                } else {
                    codeWriter.emit("$L $L", this.kind.name().toLowerCase(Locale.US), this.name);
                }
                codeWriter.emitTypeVariables(this.typeVariables);
                if (this.kind == Kind.INTERFACE) {
                    singletonList = this.superinterfaces;
                    list = Collections.emptyList();
                } else {
                    if (this.superclass.equals(ClassName.OBJECT)) {
                        singletonList = Collections.emptyList();
                    } else {
                        singletonList = Collections.singletonList(this.superclass);
                    }
                    list = this.superinterfaces;
                }
                if (!singletonList.isEmpty()) {
                    codeWriter.emit(" extends");
                    boolean z2 = true;
                    for (TypeName typeName : singletonList) {
                        if (!z2) {
                            codeWriter.emit(RemotePrefUtils.SEPARATOR);
                        }
                        codeWriter.emit(" $T", typeName);
                        z2 = false;
                    }
                }
                if (!list.isEmpty()) {
                    codeWriter.emit(" implements");
                    boolean z3 = true;
                    for (TypeName typeName2 : list) {
                        if (!z3) {
                            codeWriter.emit(RemotePrefUtils.SEPARATOR);
                        }
                        codeWriter.emit(" $T", typeName2);
                        z3 = false;
                    }
                }
                codeWriter.popType();
                codeWriter.emit(" {\n");
            }
            codeWriter.pushType(this);
            codeWriter.indent();
            Iterator<Map.Entry<String, TypeSpec>> it = this.enumConstants.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, TypeSpec> next = it.next();
                if (!z) {
                    codeWriter.emit("\n");
                }
                next.getValue().emit(codeWriter, next.getKey(), Collections.emptySet());
                if (it.hasNext()) {
                    codeWriter.emit(",\n");
                } else {
                    if (this.fieldSpecs.isEmpty() && this.methodSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                        codeWriter.emit("\n");
                    }
                    codeWriter.emit(";\n");
                }
                z = false;
            }
            for (FieldSpec fieldSpec : this.fieldSpecs) {
                if (fieldSpec.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    fieldSpec.emit(codeWriter, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.staticBlock.isEmpty()) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                codeWriter.emit(this.staticBlock);
                z = false;
            }
            for (FieldSpec fieldSpec2 : this.fieldSpecs) {
                if (!fieldSpec2.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    fieldSpec2.emit(codeWriter, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.initializerBlock.isEmpty()) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                codeWriter.emit(this.initializerBlock);
                z = false;
            }
            for (MethodSpec methodSpec : this.methodSpecs) {
                if (methodSpec.isConstructor()) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    methodSpec.emit(codeWriter, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (MethodSpec methodSpec2 : this.methodSpecs) {
                if (!methodSpec2.isConstructor()) {
                    if (!z) {
                        codeWriter.emit("\n");
                    }
                    methodSpec2.emit(codeWriter, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (TypeSpec typeSpec : this.typeSpecs) {
                if (!z) {
                    codeWriter.emit("\n");
                }
                typeSpec.emit(codeWriter, null, this.kind.implicitTypeModifiers);
                z = false;
            }
            codeWriter.unindent();
            codeWriter.popType();
            codeWriter.popTypeVariables(this.typeVariables);
            codeWriter.emit("}");
            if (str == null && this.anonymousTypeArguments == null) {
                codeWriter.emit("\n");
            }
        } finally {
            codeWriter.statementLine = i;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            emit(new CodeWriter(sb), null, Collections.emptySet());
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    /* loaded from: classes2.dex */
    public enum Kind {
        CLASS(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet()),
        INTERFACE(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Collections.singletonList(Modifier.STATIC))),
        ENUM(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.singleton(Modifier.STATIC)),
        ANNOTATION(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Collections.singletonList(Modifier.STATIC)));
        
        private final Set<Modifier> asMemberModifiers;
        private final Set<Modifier> implicitFieldModifiers;
        private final Set<Modifier> implicitMethodModifiers;
        private final Set<Modifier> implicitTypeModifiers;

        Kind(Set set, Set set2, Set set3, Set set4) {
            this.implicitFieldModifiers = set;
            this.implicitMethodModifiers = set2;
            this.implicitTypeModifiers = set3;
            this.asMemberModifiers = set4;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        public final Set<String> alwaysQualifiedNames;
        public final List<AnnotationSpec> annotations;
        private final CodeBlock anonymousTypeArguments;
        public final Map<String, TypeSpec> enumConstants;
        public final List<FieldSpec> fieldSpecs;
        private final CodeBlock.Builder initializerBlock;
        private final CodeBlock.Builder javadoc;
        private final Kind kind;
        public final List<MethodSpec> methodSpecs;
        public final List<Modifier> modifiers;
        private final String name;
        public final List<Element> originatingElements;
        private final CodeBlock.Builder staticBlock;
        private TypeName superclass;
        public final List<TypeName> superinterfaces;
        public final List<TypeSpec> typeSpecs;
        public final List<TypeVariableName> typeVariables;

        private Builder(Kind kind, String str, CodeBlock codeBlock) {
            this.javadoc = CodeBlock.builder();
            this.superclass = ClassName.OBJECT;
            this.staticBlock = CodeBlock.builder();
            this.initializerBlock = CodeBlock.builder();
            this.enumConstants = new LinkedHashMap();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.typeVariables = new ArrayList();
            this.superinterfaces = new ArrayList();
            this.fieldSpecs = new ArrayList();
            this.methodSpecs = new ArrayList();
            this.typeSpecs = new ArrayList();
            this.originatingElements = new ArrayList();
            this.alwaysQualifiedNames = new LinkedHashSet();
            Util.checkArgument(str == null || SourceVersion.isName(str), "not a valid name: %s", str);
            this.kind = kind;
            this.name = str;
            this.anonymousTypeArguments = codeBlock;
        }

        public Builder addJavadoc(String str, Object... objArr) {
            this.javadoc.add(str, objArr);
            return this;
        }

        public Builder addJavadoc(CodeBlock codeBlock) {
            this.javadoc.add(codeBlock);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> iterable) {
            Util.checkArgument(iterable != null, "annotationSpecs == null", new Object[0]);
            for (AnnotationSpec annotationSpec : iterable) {
                this.annotations.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            Util.checkNotNull(annotationSpec, "annotationSpec == null", new Object[0]);
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName className) {
            return addAnnotation(AnnotationSpec.builder(className).build());
        }

        public Builder addAnnotation(Class<?> cls) {
            return addAnnotation(ClassName.get(cls));
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Collections.addAll(this.modifiers, modifierArr);
            return this;
        }

        public Builder addTypeVariables(Iterable<TypeVariableName> iterable) {
            Util.checkArgument(iterable != null, "typeVariables == null", new Object[0]);
            for (TypeVariableName typeVariableName : iterable) {
                this.typeVariables.add(typeVariableName);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariableName) {
            this.typeVariables.add(typeVariableName);
            return this;
        }

        public Builder superclass(TypeName typeName) {
            Util.checkState(this.kind == Kind.CLASS, "only classes have super classes, not " + this.kind, new Object[0]);
            Util.checkState(this.superclass == ClassName.OBJECT, "superclass already set to " + this.superclass, new Object[0]);
            Util.checkArgument(!typeName.isPrimitive(), "superclass may not be a primitive", new Object[0]);
            this.superclass = typeName;
            return this;
        }

        public Builder superclass(Type type) {
            return superclass(type, true);
        }

        public Builder superclass(Type type, boolean z) {
            Class<?> rawType;
            superclass(TypeName.get(type));
            if (z && (rawType = getRawType(type)) != null) {
                avoidClashesWithNestedClasses(rawType);
            }
            return this;
        }

        public Builder superclass(TypeMirror typeMirror) {
            return superclass(typeMirror, true);
        }

        public Builder superclass(TypeMirror typeMirror, boolean z) {
            superclass(TypeName.get(typeMirror));
            if (z && (typeMirror instanceof DeclaredType)) {
                avoidClashesWithNestedClasses((TypeElement) ((DeclaredType) typeMirror).asElement());
            }
            return this;
        }

        public Builder addSuperinterfaces(Iterable<? extends TypeName> iterable) {
            Util.checkArgument(iterable != null, "superinterfaces == null", new Object[0]);
            for (TypeName typeName : iterable) {
                addSuperinterface(typeName);
            }
            return this;
        }

        public Builder addSuperinterface(TypeName typeName) {
            Util.checkArgument(typeName != null, "superinterface == null", new Object[0]);
            this.superinterfaces.add(typeName);
            return this;
        }

        public Builder addSuperinterface(Type type) {
            return addSuperinterface(type, true);
        }

        public Builder addSuperinterface(Type type, boolean z) {
            Class<?> rawType;
            addSuperinterface(TypeName.get(type));
            if (z && (rawType = getRawType(type)) != null) {
                avoidClashesWithNestedClasses(rawType);
            }
            return this;
        }

        private Class<?> getRawType(Type type) {
            if (type instanceof Class) {
                return (Class) type;
            }
            if (type instanceof ParameterizedType) {
                return getRawType(((ParameterizedType) type).getRawType());
            }
            return null;
        }

        public Builder addSuperinterface(TypeMirror typeMirror) {
            return addSuperinterface(typeMirror, true);
        }

        public Builder addSuperinterface(TypeMirror typeMirror, boolean z) {
            addSuperinterface(TypeName.get(typeMirror));
            if (z && (typeMirror instanceof DeclaredType)) {
                avoidClashesWithNestedClasses((TypeElement) ((DeclaredType) typeMirror).asElement());
            }
            return this;
        }

        public Builder addEnumConstant(String str) {
            return addEnumConstant(str, TypeSpec.anonymousClassBuilder("", new Object[0]).build());
        }

        public Builder addEnumConstant(String str, TypeSpec typeSpec) {
            this.enumConstants.put(str, typeSpec);
            return this;
        }

        public Builder addFields(Iterable<FieldSpec> iterable) {
            Util.checkArgument(iterable != null, "fieldSpecs == null", new Object[0]);
            for (FieldSpec fieldSpec : iterable) {
                addField(fieldSpec);
            }
            return this;
        }

        public Builder addField(FieldSpec fieldSpec) {
            this.fieldSpecs.add(fieldSpec);
            return this;
        }

        public Builder addField(TypeName typeName, String str, Modifier... modifierArr) {
            return addField(FieldSpec.builder(typeName, str, modifierArr).build());
        }

        public Builder addField(Type type, String str, Modifier... modifierArr) {
            return addField(TypeName.get(type), str, modifierArr);
        }

        public Builder addStaticBlock(CodeBlock codeBlock) {
            this.staticBlock.beginControlFlow("static", new Object[0]).add(codeBlock).endControlFlow();
            return this;
        }

        public Builder addInitializerBlock(CodeBlock codeBlock) {
            if (this.kind != Kind.CLASS && this.kind != Kind.ENUM) {
                throw new UnsupportedOperationException(this.kind + " can't have initializer blocks");
            }
            this.initializerBlock.add("{\n", new Object[0]).indent().add(codeBlock).unindent().add("}\n", new Object[0]);
            return this;
        }

        public Builder addMethods(Iterable<MethodSpec> iterable) {
            Util.checkArgument(iterable != null, "methodSpecs == null", new Object[0]);
            for (MethodSpec methodSpec : iterable) {
                addMethod(methodSpec);
            }
            return this;
        }

        public Builder addMethod(MethodSpec methodSpec) {
            this.methodSpecs.add(methodSpec);
            return this;
        }

        public Builder addTypes(Iterable<TypeSpec> iterable) {
            Util.checkArgument(iterable != null, "typeSpecs == null", new Object[0]);
            for (TypeSpec typeSpec : iterable) {
                addType(typeSpec);
            }
            return this;
        }

        public Builder addType(TypeSpec typeSpec) {
            this.typeSpecs.add(typeSpec);
            return this;
        }

        public Builder addOriginatingElement(Element element) {
            this.originatingElements.add(element);
            return this;
        }

        public Builder alwaysQualify(String... strArr) {
            Util.checkArgument(strArr != null, "simpleNames == null", new Object[0]);
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                Util.checkArgument(str != null, "null entry in simpleNames array: %s", Arrays.toString(strArr));
                this.alwaysQualifiedNames.add(str);
            }
            return this;
        }

        public Builder avoidClashesWithNestedClasses(TypeElement typeElement) {
            Util.checkArgument(typeElement != null, "typeElement == null", new Object[0]);
            for (TypeElement typeElement2 : ElementFilter.typesIn(typeElement.getEnclosedElements())) {
                alwaysQualify(typeElement2.getSimpleName().toString());
            }
            DeclaredType superclass = typeElement.getSuperclass();
            if (!(superclass instanceof NoType) && (superclass instanceof DeclaredType)) {
                avoidClashesWithNestedClasses((TypeElement) superclass.asElement());
            }
            for (DeclaredType declaredType : typeElement.getInterfaces()) {
                if (declaredType instanceof DeclaredType) {
                    avoidClashesWithNestedClasses((TypeElement) declaredType.asElement());
                }
            }
            return this;
        }

        public Builder avoidClashesWithNestedClasses(Class<?> cls) {
            Util.checkArgument(cls != null, "clazz == null", new Object[0]);
            for (Class<?> cls2 : cls.getDeclaredClasses()) {
                alwaysQualify(cls2.getSimpleName());
            }
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass != null && !Object.class.equals(superclass)) {
                avoidClashesWithNestedClasses(superclass);
            }
            for (Class<?> cls3 : cls.getInterfaces()) {
                avoidClashesWithNestedClasses(cls3);
            }
            return this;
        }

        public TypeSpec build() {
            for (AnnotationSpec annotationSpec : this.annotations) {
                Util.checkNotNull(annotationSpec, "annotationSpec == null", new Object[0]);
            }
            boolean z = true;
            if (!this.modifiers.isEmpty()) {
                Util.checkState(this.anonymousTypeArguments == null, "forbidden on anonymous types.", new Object[0]);
                Iterator<Modifier> it = this.modifiers.iterator();
                while (it.hasNext()) {
                    Util.checkArgument(it.next() != null, "modifiers contain null", new Object[0]);
                }
            }
            Util.checkArgument((this.kind == Kind.ENUM && this.enumConstants.isEmpty()) ? false : true, "at least one enum constant is required for %s", this.name);
            Iterator<TypeName> it2 = this.superinterfaces.iterator();
            while (it2.hasNext()) {
                Util.checkArgument(it2.next() != null, "superinterfaces contains null", new Object[0]);
            }
            if (!this.typeVariables.isEmpty()) {
                Util.checkState(this.anonymousTypeArguments == null, "typevariables are forbidden on anonymous types.", new Object[0]);
                Iterator<TypeVariableName> it3 = this.typeVariables.iterator();
                while (it3.hasNext()) {
                    Util.checkArgument(it3.next() != null, "typeVariables contain null", new Object[0]);
                }
            }
            for (Map.Entry<String, TypeSpec> entry : this.enumConstants.entrySet()) {
                Util.checkState(this.kind == Kind.ENUM, "%s is not enum", this.name);
                Util.checkArgument(entry.getValue().anonymousTypeArguments != null, "enum constants must have anonymous type arguments", new Object[0]);
                Util.checkArgument(SourceVersion.isName(this.name), "not a valid enum constant: %s", this.name);
            }
            for (FieldSpec fieldSpec : this.fieldSpecs) {
                if (this.kind == Kind.INTERFACE || this.kind == Kind.ANNOTATION) {
                    Util.requireExactlyOneOf(fieldSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
                    EnumSet of = EnumSet.of(Modifier.STATIC, Modifier.FINAL);
                    Util.checkState(fieldSpec.modifiers.containsAll(of), "%s %s.%s requires modifiers %s", this.kind, this.name, fieldSpec.name, of);
                }
            }
            for (MethodSpec methodSpec : this.methodSpecs) {
                if (this.kind == Kind.INTERFACE) {
                    Util.requireExactlyOneOf(methodSpec.modifiers, Modifier.ABSTRACT, Modifier.STATIC, Modifier.DEFAULT);
                    Util.requireExactlyOneOf(methodSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
                } else if (this.kind == Kind.ANNOTATION) {
                    Util.checkState(methodSpec.modifiers.equals(this.kind.implicitMethodModifiers), "%s %s.%s requires modifiers %s", this.kind, this.name, methodSpec.name, this.kind.implicitMethodModifiers);
                }
                if (this.kind != Kind.ANNOTATION) {
                    Util.checkState(methodSpec.defaultValue == null, "%s %s.%s cannot have a default value", this.kind, this.name, methodSpec.name);
                }
                if (this.kind != Kind.INTERFACE) {
                    Util.checkState(!methodSpec.hasModifier(Modifier.DEFAULT), "%s %s.%s cannot be default", this.kind, this.name, methodSpec.name);
                }
            }
            for (TypeSpec typeSpec : this.typeSpecs) {
                Util.checkArgument(typeSpec.modifiers.containsAll(this.kind.implicitTypeModifiers), "%s %s.%s requires modifiers %s", this.kind, this.name, typeSpec.name, this.kind.implicitTypeModifiers);
            }
            boolean z2 = this.modifiers.contains(Modifier.ABSTRACT) || this.kind != Kind.CLASS;
            for (MethodSpec methodSpec2 : this.methodSpecs) {
                Util.checkArgument(z2 || !methodSpec2.hasModifier(Modifier.ABSTRACT), "non-abstract type %s cannot declare abstract method %s", this.name, methodSpec2.name);
            }
            int size = (!this.superclass.equals(ClassName.OBJECT)) + this.superinterfaces.size();
            if (this.anonymousTypeArguments != null && size > 1) {
                z = false;
            }
            Util.checkArgument(z, "anonymous type has too many supertypes", new Object[0]);
            return new TypeSpec(this);
        }
    }
}
