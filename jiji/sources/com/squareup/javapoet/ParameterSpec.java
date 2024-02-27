package com.squareup.javapoet;

import com.squareup.javapoet.CodeBlock;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/* loaded from: classes2.dex */
public final class ParameterSpec {
    public final List<AnnotationSpec> annotations;
    public final CodeBlock javadoc;
    public final Set<Modifier> modifiers;
    public final String name;
    public final TypeName type;

    private ParameterSpec(Builder builder) {
        this.name = (String) Util.checkNotNull(builder.name, "name == null", new Object[0]);
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.type = (TypeName) Util.checkNotNull(builder.type, "type == null", new Object[0]);
        this.javadoc = builder.javadoc.build();
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void emit(CodeWriter codeWriter, boolean z) throws IOException {
        codeWriter.emitAnnotations(this.annotations, true);
        codeWriter.emitModifiers(this.modifiers);
        if (z) {
            TypeName.asArray(this.type).emit(codeWriter, true);
        } else {
            this.type.emit(codeWriter);
        }
        codeWriter.emit(" $L", this.name);
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
            emit(new CodeWriter(sb), false);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static ParameterSpec get(VariableElement variableElement) {
        Util.checkArgument(variableElement.getKind().equals(ElementKind.PARAMETER), "element is not a parameter", new Object[0]);
        return builder(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString(), new Modifier[0]).addModifiers(variableElement.getModifiers()).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ParameterSpec> parametersOf(ExecutableElement executableElement) {
        ArrayList arrayList = new ArrayList();
        for (VariableElement variableElement : executableElement.getParameters()) {
            arrayList.add(get(variableElement));
        }
        return arrayList;
    }

    private static boolean isValidParameterName(String str) {
        if (str.endsWith(".this")) {
            return SourceVersion.isIdentifier(str.substring(0, str.length() - 5));
        }
        return str.equals("this") || SourceVersion.isName(str);
    }

    public static Builder builder(TypeName typeName, String str, Modifier... modifierArr) {
        Util.checkNotNull(typeName, "type == null", new Object[0]);
        Util.checkArgument(isValidParameterName(str), "not a valid name: %s", str);
        return new Builder(typeName, str).addModifiers(modifierArr);
    }

    public static Builder builder(Type type, String str, Modifier... modifierArr) {
        return builder(TypeName.get(type), str, modifierArr);
    }

    public Builder toBuilder() {
        return toBuilder(this.type, this.name);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Builder toBuilder(TypeName typeName, String str) {
        Builder builder = new Builder(typeName, str);
        builder.annotations.addAll(this.annotations);
        builder.modifiers.addAll(this.modifiers);
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        public final List<AnnotationSpec> annotations;
        private final CodeBlock.Builder javadoc;
        public final List<Modifier> modifiers;
        private final String name;
        private final TypeName type;

        private Builder(TypeName typeName, String str) {
            this.javadoc = CodeBlock.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.type = typeName;
            this.name = str;
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
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public Builder addAnnotation(Class<?> cls) {
            return addAnnotation(ClassName.get(cls));
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Collections.addAll(this.modifiers, modifierArr);
            return this;
        }

        public Builder addModifiers(Iterable<Modifier> iterable) {
            Util.checkNotNull(iterable, "modifiers == null", new Object[0]);
            for (Modifier modifier : iterable) {
                if (!modifier.equals(Modifier.FINAL)) {
                    throw new IllegalStateException("unexpected parameter modifier: " + modifier);
                }
                this.modifiers.add(modifier);
            }
            return this;
        }

        public ParameterSpec build() {
            return new ParameterSpec(this);
        }
    }
}
