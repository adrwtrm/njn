package org.objectweb.asm.signature;

/* loaded from: classes.dex */
public class SignatureReader {
    private final String signatureValue;

    public SignatureReader(String str) {
        this.signatureValue = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void accept(org.objectweb.asm.signature.SignatureVisitor r7) {
        /*
            r6 = this;
            java.lang.String r0 = r6.signatureValue
            int r1 = r0.length()
            r2 = 0
            char r3 = r0.charAt(r2)
            r4 = 60
            if (r3 != r4) goto L4e
            r2 = 2
        L10:
            r3 = 58
            int r4 = r0.indexOf(r3, r2)
            int r2 = r2 + (-1)
            java.lang.String r2 = r0.substring(r2, r4)
            r7.visitFormalTypeParameter(r2)
            int r4 = r4 + 1
            char r2 = r0.charAt(r4)
            r5 = 76
            if (r2 == r5) goto L31
            r5 = 91
            if (r2 == r5) goto L31
            r5 = 84
            if (r2 != r5) goto L39
        L31:
            org.objectweb.asm.signature.SignatureVisitor r2 = r7.visitClassBound()
            int r4 = parseType(r0, r4, r2)
        L39:
            int r2 = r4 + 1
            char r4 = r0.charAt(r4)
            if (r4 != r3) goto L4a
            org.objectweb.asm.signature.SignatureVisitor r4 = r7.visitInterfaceBound()
            int r4 = parseType(r0, r2, r4)
            goto L39
        L4a:
            r3 = 62
            if (r4 != r3) goto L10
        L4e:
            char r3 = r0.charAt(r2)
            r4 = 40
            if (r3 != r4) goto L80
            int r2 = r2 + 1
        L58:
            char r3 = r0.charAt(r2)
            r4 = 41
            if (r3 == r4) goto L69
            org.objectweb.asm.signature.SignatureVisitor r3 = r7.visitParameterType()
            int r2 = parseType(r0, r2, r3)
            goto L58
        L69:
            int r2 = r2 + 1
            org.objectweb.asm.signature.SignatureVisitor r3 = r7.visitReturnType()
            int r2 = parseType(r0, r2, r3)
        L73:
            if (r2 >= r1) goto L93
            int r2 = r2 + 1
            org.objectweb.asm.signature.SignatureVisitor r3 = r7.visitExceptionType()
            int r2 = parseType(r0, r2, r3)
            goto L73
        L80:
            org.objectweb.asm.signature.SignatureVisitor r3 = r7.visitSuperclass()
            int r2 = parseType(r0, r2, r3)
        L88:
            if (r2 >= r1) goto L93
            org.objectweb.asm.signature.SignatureVisitor r3 = r7.visitInterface()
            int r2 = parseType(r0, r2, r3)
            goto L88
        L93:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.signature.SignatureReader.accept(org.objectweb.asm.signature.SignatureVisitor):void");
    }

    public void acceptType(SignatureVisitor signatureVisitor) {
        parseType(this.signatureValue, 0, signatureVisitor);
    }

    private static int parseType(String str, int i, SignatureVisitor signatureVisitor) {
        int i2;
        char charAt;
        int i3 = i + 1;
        char charAt2 = str.charAt(i);
        if (charAt2 != 'F') {
            if (charAt2 == 'L') {
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    int i4 = i3;
                    while (true) {
                        i2 = i3 + 1;
                        charAt = str.charAt(i3);
                        if (charAt == '.' || charAt == ';') {
                            break;
                        } else if (charAt == '<') {
                            String substring = str.substring(i4, i2 - 1);
                            if (z2) {
                                signatureVisitor.visitInnerClassType(substring);
                            } else {
                                signatureVisitor.visitClassType(substring);
                            }
                            i3 = i2;
                            while (true) {
                                char charAt3 = str.charAt(i3);
                                if (charAt3 == '>') {
                                    break;
                                } else if (charAt3 == '*') {
                                    i3++;
                                    signatureVisitor.visitTypeArgument();
                                } else if (charAt3 == '+' || charAt3 == '-') {
                                    i3 = parseType(str, i3 + 1, signatureVisitor.visitTypeArgument(charAt3));
                                } else {
                                    i3 = parseType(str, i3, signatureVisitor.visitTypeArgument(SignatureVisitor.INSTANCEOF));
                                }
                            }
                            z = true;
                        } else {
                            i3 = i2;
                        }
                    }
                    if (!z) {
                        String substring2 = str.substring(i4, i2 - 1);
                        if (z2) {
                            signatureVisitor.visitInnerClassType(substring2);
                        } else {
                            signatureVisitor.visitClassType(substring2);
                        }
                    }
                    if (charAt == ';') {
                        signatureVisitor.visitEnd();
                        return i2;
                    }
                    z = false;
                    z2 = true;
                    i3 = i2;
                }
            } else if (charAt2 != 'V' && charAt2 != 'I' && charAt2 != 'J' && charAt2 != 'S') {
                if (charAt2 == 'T') {
                    int indexOf = str.indexOf(59, i3);
                    signatureVisitor.visitTypeVariable(str.substring(i3, indexOf));
                    return indexOf + 1;
                } else if (charAt2 != 'Z') {
                    if (charAt2 == '[') {
                        return parseType(str, i3, signatureVisitor.visitArrayType());
                    }
                    switch (charAt2) {
                        case 'B':
                        case 'C':
                        case 'D':
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                }
            }
        }
        signatureVisitor.visitBaseType(charAt2);
        return i3;
    }
}
