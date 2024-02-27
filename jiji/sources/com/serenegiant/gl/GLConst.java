package com.serenegiant.gl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface GLConst {
    public static final int GL_NO_BUFFER = -1;
    public static final int GL_NO_PROGRAM = -1;
    public static final int GL_NO_TEXTURE = -1;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_TEXTURE_EXTERNAL_OES = 36197;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MinMagFilter {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TexTarget {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TexUnit {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Wrap {
    }
}
