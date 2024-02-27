package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes.dex */
public class ThumbImageView extends AppCompatImageView {
    private static final float alphaEnd = 1.0f;
    private static final float alphaStart = 0.0f;
    private static final int animTime = 500;
    private boolean _isFinished;

    public ThumbImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._isFinished = false;
    }

    public void finished(boolean z) {
        this._isFinished = z;
    }

    public boolean isFinished() {
        return this._isFinished;
    }

    public void setAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500L);
        alphaAnimation.setFillAfter(true);
        super.startAnimation(alphaAnimation);
    }
}
