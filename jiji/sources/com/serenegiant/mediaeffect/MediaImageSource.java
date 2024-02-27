package com.serenegiant.mediaeffect;

import android.graphics.Bitmap;
import com.serenegiant.gl.GLSurface;

/* loaded from: classes2.dex */
public class MediaImageSource extends MediaSource {
    private boolean isReset;
    private GLSurface mImageOffscreen;

    public MediaImageSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        this.mImageOffscreen = GLSurface.newInstance(false, this.mWidth, this.mHeight, false);
        setSource(bitmap);
    }

    public ISource setSource(Bitmap bitmap) {
        this.mImageOffscreen.loadBitmap(bitmap);
        reset();
        return this;
    }

    @Override // com.serenegiant.mediaeffect.MediaSource, com.serenegiant.mediaeffect.ISource
    public ISource reset() {
        super.reset();
        this.isReset = true;
        this.mSrcTexIds[0] = this.mImageOffscreen.getTexId();
        return this;
    }

    @Override // com.serenegiant.mediaeffect.MediaSource, com.serenegiant.mediaeffect.ISource
    public ISource apply(IEffect iEffect) {
        if (this.mSourceScreen != null) {
            if (this.isReset) {
                this.isReset = false;
                this.needSwap = true;
            } else {
                if (this.needSwap) {
                    GLSurface gLSurface = this.mSourceScreen;
                    this.mSourceScreen = this.mOutputScreen;
                    this.mOutputScreen = gLSurface;
                    this.mSrcTexIds[0] = this.mSourceScreen.getTexId();
                }
                this.needSwap = !this.needSwap;
            }
            iEffect.apply(this.mSrcTexIds, this.mOutputScreen.getTexWidth(), this.mOutputScreen.getTexHeight(), this.mOutputScreen.getTexId());
        }
        return this;
    }
}
