package com.serenegiant.widget;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface IScaledView {
    public static final int SCALE_MODE_CROP = 2;
    public static final int SCALE_MODE_KEEP_ASPECT = 0;
    public static final int SCALE_MODE_STRETCH_TO_FIT = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ScaleMode {
    }

    double getAspectRatio();

    int getScaleMode();

    void setAspectRatio(double d);

    void setAspectRatio(int i, int i2);

    void setNeedResizeToKeepAspect(boolean z);

    void setScaleMode(int i);
}
