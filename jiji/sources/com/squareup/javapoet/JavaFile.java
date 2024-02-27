package com.squareup.javapoet;

import com.squareup.javapoet.CodeBlock;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/* loaded from: classes2.dex */
public final class JavaFile {
    private static final Appendable NULL_APPENDABLE = new Appendable() { // from class: com.squareup.javapoet.JavaFile.1
        @Override // java.lang.Appendable
        public Appendable append(char c) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) {
            return this;
        }
    };
    private final Set<String> alwaysQualify;
    public final CodeBlock fileComment;
    private final String indent;
    public final String packageName;
    public final boolean skipJavaLangImports;
    private final Set<String> staticImports;
    public final TypeSpec typeSpec;

    private JavaFile(Builder builder) {
        this.fileComment = builder.fileComment.build();
        this.packageName = builder.packageName;
        this.typeSpec = builder.typeSpec;
        this.skipJavaLangImports = builder.skipJavaLangImports;
        this.staticImports = Util.immutableSet(builder.staticImports);
        this.indent = builder.indent;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        fillAlwaysQualifiedNames(builder.typeSpec, linkedHashSet);
        this.alwaysQualify = Util.immutableSet(linkedHashSet);
    }

    private void fillAlwaysQualifiedNames(TypeSpec typeSpec, Set<String> set) {
        set.addAll(typeSpec.alwaysQualifiedNames);
        for (TypeSpec typeSpec2 : typeSpec.typeSpecs) {
            fillAlwaysQualifiedNames(typeSpec2, set);
        }
    }

    public void writeTo(Appendable appendable) throws IOException {
        CodeWriter codeWriter = new CodeWriter(NULL_APPENDABLE, this.indent, this.staticImports, this.alwaysQualify);
        emit(codeWriter);
        emit(new CodeWriter(appendable, this.indent, codeWriter.suggestedImports(), this.staticImports, this.alwaysQualify));
    }

    public void writeTo(Path path) throws IOException {
        writeToPath(path);
    }

    public void writeTo(Path path, Charset charset) throws IOException {
        writeToPath(path, charset);
    }

    public Path writeToPath(Path path) throws IOException {
        return writeToPath(path, StandardCharsets.UTF_8);
    }

    public Path writeToPath(Path path, Charset charset) throws IOException {
        Util.checkArgument(Files.notExists(path, new LinkOption[0]) || Files.isDirectory(path, new LinkOption[0]), "path %s exists but is not a directory.", path);
        if (!this.packageName.isEmpty()) {
            for (String str : this.packageName.split("\\.")) {
                path = path.resolve(str);
            }
            Files.createDirectories(path, new FileAttribute[0]);
        }
        Path resolve = path.resolve(this.typeSpec.name + ".java");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(resolve, new OpenOption[0]), charset);
        try {
            writeTo(outputStreamWriter);
            $closeResource(null, outputStreamWriter);
            return resolve;
        } finally {
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th == null) {
            autoCloseable.close();
            return;
        }
        try {
            autoCloseable.close();
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    public void writeTo(File file) throws IOException {
        writeTo(file.toPath());
    }

    public File writeToFile(File file) throws IOException {
        return writeToPath(file.toPath()).toFile();
    }

    public void writeTo(Filer filer) throws IOException {
        String str;
        if (this.packageName.isEmpty()) {
            str = this.typeSpec.name;
        } else {
            str = this.packageName + "." + this.typeSpec.name;
        }
        List<Element> list = this.typeSpec.originatingElements;
        JavaFileObject createSourceFile = filer.createSourceFile(str, (Element[]) list.toArray(new Element[list.size()]));
        try {
            Writer openWriter = createSourceFile.openWriter();
            writeTo(openWriter);
            if (openWriter != null) {
                $closeResource(null, openWriter);
            }
        } catch (Exception e) {
            try {
                createSourceFile.delete();
            } catch (Exception unused) {
            }
            throw e;
        }
    }

    private void emit(CodeWriter codeWriter) throws IOException {
        codeWriter.pushPackage(this.packageName);
        if (!this.fileComment.isEmpty()) {
            codeWriter.emitComment(this.fileComment);
        }
        if (!this.packageName.isEmpty()) {
            codeWriter.emit("package $L;\n", this.packageName);
            codeWriter.emit("\n");
        }
        if (!this.staticImports.isEmpty()) {
            for (String str : this.staticImports) {
                codeWriter.emit("import static $L;\n", str);
            }
            codeWriter.emit("\n");
        }
        Iterator it = new TreeSet(codeWriter.importedTypes().values()).iterator();
        int i = 0;
        while (it.hasNext()) {
            ClassName className = (ClassName) it.next();
            if (!this.skipJavaLangImports || !className.packageName().equals("java.lang") || this.alwaysQualify.contains(className.simpleName)) {
                codeWriter.emit("import $L;\n", className.withoutAnnotations());
                i++;
            }
        }
        if (i > 0) {
            codeWriter.emit("\n");
        }
        this.typeSpec.emit(codeWriter, null, Collections.emptySet());
        codeWriter.popPackage();
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
        try {
            StringBuilder sb = new StringBuilder();
            writeTo(sb);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public JavaFileObject toJavaFileObject() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.packageName.isEmpty()) {
            str = this.typeSpec.name;
        } else {
            str = this.packageName.replace('.', '/') + '/' + this.typeSpec.name;
        }
        return new SimpleJavaFileObject(URI.create(sb.append(str).append(JavaFileObject.Kind.SOURCE.extension).toString()), JavaFileObject.Kind.SOURCE) { // from class: com.squareup.javapoet.JavaFile.2
            private final long lastModified = System.currentTimeMillis();

            public String getCharContent(boolean z) {
                return JavaFile.this.toString();
            }

            public InputStream openInputStream() throws IOException {
                return new ByteArrayInputStream(getCharContent(true).getBytes(StandardCharsets.UTF_8));
            }

            public long getLastModified() {
                return this.lastModified;
            }
        };
    }

    public static Builder builder(String str, TypeSpec typeSpec) {
        Util.checkNotNull(str, "packageName == null", new Object[0]);
        Util.checkNotNull(typeSpec, "typeSpec == null", new Object[0]);
        return new Builder(str, typeSpec);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.packageName, this.typeSpec);
        builder.fileComment.add(this.fileComment);
        builder.skipJavaLangImports = this.skipJavaLangImports;
        builder.indent = this.indent;
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final CodeBlock.Builder fileComment;
        private String indent;
        private final String packageName;
        private boolean skipJavaLangImports;
        public final Set<String> staticImports;
        private final TypeSpec typeSpec;

        private Builder(String str, TypeSpec typeSpec) {
            this.fileComment = CodeBlock.builder();
            this.indent = "  ";
            this.staticImports = new TreeSet();
            this.packageName = str;
            this.typeSpec = typeSpec;
        }

        public Builder addFileComment(String str, Object... objArr) {
            this.fileComment.add(str, objArr);
            return this;
        }

        public Builder addStaticImport(Enum<?> r2) {
            return addStaticImport(ClassName.get(r2.getDeclaringClass()), r2.name());
        }

        public Builder addStaticImport(Class<?> cls, String... strArr) {
            return addStaticImport(ClassName.get(cls), strArr);
        }

        public Builder addStaticImport(ClassName className, String... strArr) {
            Util.checkArgument(className != null, "className == null", new Object[0]);
            Util.checkArgument(strArr != null, "names == null", new Object[0]);
            Util.checkArgument(strArr.length > 0, "names array is empty", new Object[0]);
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                Util.checkArgument(str != null, "null entry in names array: %s", Arrays.toString(strArr));
                this.staticImports.add(className.canonicalName + "." + str);
            }
            return this;
        }

        public Builder skipJavaLangImports(boolean z) {
            this.skipJavaLangImports = z;
            return this;
        }

        public Builder indent(String str) {
            this.indent = str;
            return this;
        }

        public JavaFile build() {
            return new JavaFile(this);
        }
    }
}
