package com.serenegiant.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ProgressBar;

/* loaded from: classes2.dex */
public class AnimationProgressBar extends ProgressBar implements IAnimationView {
    private Animation.AnimationListener mListener;

    public AnimationProgressBar(Context context) {
        super(context);
    }

    public AnimationProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AnimationProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.serenegiant.widget.IAnimationView
    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    @Override // android.view.View
    public void onAnimationStart() {
        super.onAnimationStart();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationStart(getAnimation());
        }
    }

    @Override // android.view.View
    public void onAnimationEnd() {
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationEnd(getAnimation());
        }
        super.onAnimationEnd();
    }
}
