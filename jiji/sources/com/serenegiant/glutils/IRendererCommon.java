package com.serenegiant.glutils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
/* loaded from: classes2.dex */
public interface IRendererCommon {
    public static final int MIRROR_BOTH = 3;
    public static final int MIRROR_HORIZONTAL = 1;
    public static final int MIRROR_NORMAL = 0;
    public static final int MIRROR_NUM = 4;
    public static final int MIRROR_VERTICAL = 2;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MirrorMode {
    }

    int getMirror();

    void setMirror(int i);
}
