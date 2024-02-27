package com.serenegiant.view.animation;

import android.view.View;

/* loaded from: classes2.dex */
public class DepthPageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float f) {
        int width = view.getWidth();
        if (f < -1.0f) {
            view.setAlpha(0.0f);
        } else if (f <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setTranslationZ(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        } else if (f <= 1.0f) {
            view.setAlpha(1.0f - f);
            view.setTranslationX(width * (-f));
            view.setTranslationZ(-1.0f);
            float abs = ((1.0f - Math.abs(f)) * 0.25f) + 0.75f;
            view.setScaleX(abs);
            view.setScaleY(abs);
        } else {
            view.setAlpha(0.0f);
        }
    }
}
