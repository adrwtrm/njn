package com.squareup.javapoet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public final class ClassName extends TypeName implements Comparable<ClassName> {
    private static final String NO_PACKAGE = "";
    public static final ClassName OBJECT = get((Class<?>) Object.class);
    final String canonicalName;
    final ClassName enclosingClassName;
    final String packageName;
    final String simpleName;
    private List<String> simpleNames;

    @Override // com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    private ClassName(String str, ClassName className, String str2) {
        this(str, className, str2, Collections.emptyList());
    }

    private ClassName(String str, ClassName className, String str2, List<AnnotationSpec> list) {
        super(list);
        this.packageName = (String) Objects.requireNonNull(str, "packageName == null");
        this.enclosingClassName = className;
        this.simpleName = str2;
        if (className != null) {
            str2 = className.canonicalName + '.' + str2;
        } else if (!str.isEmpty()) {
            str2 = str + '.' + str2;
        }
        this.canonicalName = str2;
    }

    @Override // com.squareup.javapoet.TypeName
    public ClassName annotated(List<AnnotationSpec> list) {
        return new ClassName(this.packageName, this.enclosingClassName, this.simpleName, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.TypeName
    public ClassName withoutAnnotations() {
        if (isAnnotated()) {
            ClassName className = this.enclosingClassName;
            return new ClassName(this.packageName, className != null ? className.withoutAnnotations() : null, this.simpleName);
        }
        return this;
    }

    @Override // com.squareup.javapoet.TypeName
    public boolean isAnnotated() {
        ClassName className;
        return super.isAnnotated() || ((className = this.enclosingClassName) != null && className.isAnnotated());
    }

    public String packageName() {
        return this.packageName;
    }

    public ClassName enclosingClassName() {
        return this.enclosingClassName;
    }

    public ClassName topLevelClassName() {
        ClassName className = this.enclosingClassName;
        return className != null ? className.topLevelClassName() : this;
    }

    public String reflectionName() {
        if (this.enclosingClassName != null) {
            return this.enclosingClassName.reflectionName() + Typography.dollar + this.simpleName;
        }
        return this.packageName.isEmpty() ? this.simpleName : this.packageName + '.' + this.simpleName;
    }

    public List<String> simpleNames() {
        List<String> list = this.simpleNames;
        if (list != null) {
            return list;
        }
        if (this.enclosingClassName == null) {
            this.simpleNames = Collections.singletonList(this.simpleName);
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(enclosingClassName().simpleNames());
            arrayList.add(this.simpleName);
            this.simpleNames = Collections.unmodifiableList(arrayList);
        }
        return this.simpleNames;
    }

    public ClassName peerClass(String str) {
        return new ClassName(this.packageName, this.enclosingClassName, str);
    }

    public ClassName nestedClass(String str) {
        return new ClassName(this.packageName, this, str);
    }

    public String simpleName() {
        return this.simpleName;
    }

    public String canonicalName() {
        return this.canonicalName;
    }

    public static ClassName get(Class<?> cls) {
        Util.checkNotNull(cls, "clazz == null", new Object[0]);
        Util.checkArgument(!cls.isPrimitive(), "primitive types cannot be represented as a ClassName", new Object[0]);
        Util.checkArgument(!Void.TYPE.equals(cls), "'void' type cannot be represented as a ClassName", new Object[0]);
        Util.checkArgument(!cls.isArray(), "array types cannot be represented as a ClassName", new Object[0]);
        String str = "";
        while (cls.isAnonymousClass()) {
            str = cls.getName().substring(cls.getName().lastIndexOf(36)) + str;
            cls = cls.getEnclosingClass();
        }
        String str2 = cls.getSimpleName() + str;
        if (cls.getEnclosingClass() == null) {
            int lastIndexOf = cls.getName().lastIndexOf(46);
            return new ClassName(lastIndexOf != -1 ? cls.getName().substring(0, lastIndexOf) : "", null, str2);
        }
        return get(cls.getEnclosingClass()).nestedClass(str2);
    }

    public static ClassName bestGuess(String str) {
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= str.length() || !Character.isLowerCase(str.codePointAt(i))) {
                break;
            }
            i = str.indexOf(46, i) + 1;
            if (i == 0) {
                z = false;
            }
            Util.checkArgument(z, "couldn't make a guess for %s", str);
        }
        String substring = i == 0 ? "" : str.substring(0, i - 1);
        String[] split = str.substring(i).split("\\.", -1);
        int length = split.length;
        ClassName className = null;
        int i2 = 0;
        while (i2 < length) {
            String str2 = split[i2];
            Util.checkArgument(!str2.isEmpty() && Character.isUpperCase(str2.codePointAt(0)), "couldn't make a guess for %s", str);
            i2++;
            className = new ClassName(substring, className, str2);
        }
        return className;
    }

    public static ClassName get(String str, String str2, String... strArr) {
        ClassName className = new ClassName(str, null, str2);
        for (String str3 : strArr) {
            className = className.nestedClass(str3);
        }
        return className;
    }

    public static ClassName get(final TypeElement typeElement) {
        Util.checkNotNull(typeElement, "element == null", new Object[0]);
        final String obj = typeElement.getSimpleName().toString();
        return (ClassName) typeElement.getEnclosingElement().accept(new SimpleElementVisitor8<ClassName, Void>() { // from class: com.squareup.javapoet.ClassName.1
            public ClassName visitPackage(PackageElement packageElement, Void r4) {
                return new ClassName(packageElement.getQualifiedName().toString(), (ClassName) null, obj);
            }

            public ClassName visitType(TypeElement typeElement2, Void r2) {
                return ClassName.get(typeElement2).nestedClass(obj);
            }

            public ClassName visitUnknown(Element element, Void r3) {
                return ClassName.get("", obj, new String[0]);
            }

            public ClassName defaultAction(Element element, Void r3) {
                throw new IllegalArgumentException("Unexpected type nesting: " + typeElement);
            }
        }, (Object) null);
    }

    @Override // java.lang.Comparable
    public int compareTo(ClassName className) {
        return this.canonicalName.compareTo(className.canonicalName);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        String str;
        boolean z = false;
        for (ClassName className : enclosingClasses()) {
            if (z) {
                codeWriter.emit(".");
                str = className.simpleName;
            } else if (className.isAnnotated() || className == this) {
                str = codeWriter.lookupName(className);
                int lastIndexOf = str.lastIndexOf(46);
                if (lastIndexOf != -1) {
                    int i = lastIndexOf + 1;
                    codeWriter.emitAndIndent(str.substring(0, i));
                    str = str.substring(i);
                    z = true;
                }
            }
            if (className.isAnnotated()) {
                if (z) {
                    codeWriter.emit(" ");
                }
                className.emitAnnotations(codeWriter);
            }
            codeWriter.emit(str);
            z = true;
        }
        return codeWriter;
    }

    private List<ClassName> enclosingClasses() {
        ArrayList arrayList = new ArrayList();
        for (ClassName className = this; className != null; className = className.enclosingClassName) {
            arrayList.add(className);
        }
        Collections.reverse(arrayList);
        return arrayList;
    }
}
