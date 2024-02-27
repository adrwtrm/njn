package com.serenegiant.db;

import java.util.ArrayList;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("/");
    private static final int TEXT = 2;
    private final ArrayList<UriMatcher> mChildren;
    private int mCode;
    private String mText;
    private int mWhich;

    public UriMatcher(int i) {
        this.mCode = i;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    private UriMatcher() {
        this.mCode = -1;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    public void addURI(String str, String str2, int i) {
        String[] strArr;
        if (i < 0) {
            throw new IllegalArgumentException("code " + i + " is invalid: it must be positive");
        }
        if (str2 != null) {
            if (str2.length() > 0 && str2.charAt(0) == '/') {
                str2 = str2.substring(1);
            }
            strArr = PATH_SPLIT_PATTERN.split(str2);
        } else {
            strArr = null;
        }
        int length = strArr != null ? strArr.length : 0;
        int i2 = -1;
        UriMatcher uriMatcher = this;
        while (i2 < length) {
            String str3 = i2 < 0 ? str : strArr[i2];
            ArrayList<UriMatcher> arrayList = uriMatcher.mChildren;
            int size = arrayList.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                UriMatcher uriMatcher2 = arrayList.get(i3);
                if (str3.equals(uriMatcher2.mText)) {
                    uriMatcher = uriMatcher2;
                    break;
                }
                i3++;
            }
            if (i3 == size) {
                UriMatcher uriMatcher3 = new UriMatcher();
                if (str3.equals("#")) {
                    uriMatcher3.mWhich = 1;
                } else if (str3.equals("*")) {
                    uriMatcher3.mWhich = 2;
                } else {
                    uriMatcher3.mWhich = 0;
                }
                uriMatcher3.mText = str3;
                uriMatcher.mChildren.add(uriMatcher3);
                uriMatcher = uriMatcher3;
            }
            i2++;
        }
        uriMatcher.mCode = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0045, code lost:
        if (r12 != 2) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0071, code lost:
        if (r11.mText.equals(r6) != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int match(android.net.Uri r17) {
        /*
            r16 = this;
            java.util.List r0 = r17.getPathSegments()
            int r1 = r0.size()
            if (r1 != 0) goto L15
            java.lang.String r2 = r17.getAuthority()
            if (r2 != 0) goto L15
            r2 = r16
            int r0 = r2.mCode
            return r0
        L15:
            r2 = r16
            r3 = -1
            r5 = r2
            r4 = r3
        L1a:
            if (r4 >= r1) goto L81
            if (r4 >= 0) goto L23
            java.lang.String r6 = r17.getAuthority()
            goto L29
        L23:
            java.lang.Object r6 = r0.get(r4)
            java.lang.String r6 = (java.lang.String) r6
        L29:
            java.util.ArrayList<com.serenegiant.db.UriMatcher> r7 = r5.mChildren
            if (r7 != 0) goto L2e
            goto L81
        L2e:
            int r5 = r7.size()
            r8 = 0
            r9 = 0
            r10 = r8
        L35:
            if (r10 >= r5) goto L7a
            java.lang.Object r11 = r7.get(r10)
            com.serenegiant.db.UriMatcher r11 = (com.serenegiant.db.UriMatcher) r11
            int r12 = r11.mWhich
            if (r12 == 0) goto L6b
            r13 = 1
            if (r12 == r13) goto L48
            r13 = 2
            if (r12 == r13) goto L73
            goto L74
        L48:
            int r12 = r6.length()
            r13 = r8
        L4d:
            if (r13 >= r12) goto L73
            char r14 = r6.charAt(r13)
            r15 = 45
            if (r14 == r15) goto L5b
            r15 = 43
            if (r14 != r15) goto L5d
        L5b:
            if (r13 != 0) goto L74
        L5d:
            if (r13 <= 0) goto L68
            r15 = 48
            if (r14 < r15) goto L74
            r15 = 57
            if (r14 <= r15) goto L68
            goto L74
        L68:
            int r13 = r13 + 1
            goto L4d
        L6b:
            java.lang.String r12 = r11.mText
            boolean r12 = r12.equals(r6)
            if (r12 == 0) goto L74
        L73:
            r9 = r11
        L74:
            if (r9 == 0) goto L77
            goto L7a
        L77:
            int r10 = r10 + 1
            goto L35
        L7a:
            r5 = r9
            if (r5 != 0) goto L7e
            return r3
        L7e:
            int r4 = r4 + 1
            goto L1a
        L81:
            int r0 = r5.mCode
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.db.UriMatcher.match(android.net.Uri):int");
    }
}
