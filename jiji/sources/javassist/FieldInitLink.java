package javassist;

import javassist.CtField;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CtClassType.java */
/* loaded from: classes2.dex */
public class FieldInitLink {
    CtField field;
    CtField.Initializer init;
    FieldInitLink next = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FieldInitLink(CtField ctField, CtField.Initializer initializer) {
        this.field = ctField;
        this.init = initializer;
    }
}
