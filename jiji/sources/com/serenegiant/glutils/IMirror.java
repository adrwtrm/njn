package com.serenegiant.glutils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface IMirror {
    public static final int MIRROR_BOTH = 3;
    public static final int MIRROR_HORIZONTAL = 1;
    public static final int MIRROR_NORMAL = 0;
    public static final int MIRROR_NUM = 4;
    public static final int MIRROR_VERTICAL = 2;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MirrorMode {
    }

    static int flipVertical(int i) {
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? 2 : 1;
            }
            return 0;
        }
        return 3;
    }

    int getMirror();

    void setMirror(int i);
}
