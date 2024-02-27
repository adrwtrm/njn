package javassist;

/* compiled from: ClassPoolTail.java */
/* loaded from: classes2.dex */
final class ClassPathList {
    ClassPathList next;
    ClassPath path;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassPathList(ClassPath classPath, ClassPathList classPathList) {
        this.next = classPathList;
        this.path = classPath;
    }
}
