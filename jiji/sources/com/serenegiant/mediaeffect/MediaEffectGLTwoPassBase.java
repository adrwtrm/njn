package com.serenegiant.mediaeffect;

import com.serenegiant.gl.GLSurface;

/* loaded from: classes2.dex */
public class MediaEffectGLTwoPassBase extends MediaEffectGLBase {
    protected final MediaEffectKernel3x3Drawer mDrawer2;
    protected GLSurface mOutputOffscreen2;

    public MediaEffectGLTwoPassBase(int i, boolean z, String str) {
        super(i, z, str);
        this.mDrawer2 = null;
    }

    public MediaEffectGLTwoPassBase(int i, String str, String str2) {
        super(i, false, str, str2);
        this.mDrawer2 = null;
    }

    public MediaEffectGLTwoPassBase(int i, boolean z, String str, String str2) {
        super(i, z, str, str2);
        this.mDrawer2 = null;
    }

    public MediaEffectGLTwoPassBase(int i, boolean z, String str, String str2, String str3, String str4) {
        super(i, z, str, str2);
        if (!str.equals(str3) || !str2.equals(str4)) {
            this.mDrawer2 = new MediaEffectKernel3x3Drawer(z, str3, str4);
        } else {
            this.mDrawer2 = null;
        }
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public void release() {
        MediaEffectKernel3x3Drawer mediaEffectKernel3x3Drawer = this.mDrawer2;
        if (mediaEffectKernel3x3Drawer != null) {
            mediaEffectKernel3x3Drawer.release();
        }
        GLSurface gLSurface = this.mOutputOffscreen2;
        if (gLSurface != null) {
            gLSurface.release();
            this.mOutputOffscreen2 = null;
        }
        super.release();
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public MediaEffectGLBase resize(int i, int i2) {
        super.resize(i, i2);
        MediaEffectKernel3x3Drawer mediaEffectKernel3x3Drawer = this.mDrawer2;
        if (mediaEffectKernel3x3Drawer != null) {
            mediaEffectKernel3x3Drawer.setTexSize(i, i2);
        }
        return this;
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public void apply(int[] iArr, int i, int i2, int i3) {
        if (this.mEnabled) {
            if (this.mOutputOffscreen == null) {
                this.mOutputOffscreen = GLSurface.newInstance(false, i, i2, false);
            }
            this.mOutputOffscreen.makeCurrent();
            try {
                this.mDrawer.apply(iArr, this.mOutputOffscreen.copyTexMatrix(), 0);
                this.mOutputOffscreen.swap();
                if (this.mOutputOffscreen2 == null) {
                    this.mOutputOffscreen2 = GLSurface.newInstance(false, i, i2, false);
                }
                if (i3 != this.mOutputOffscreen2.getTexId() || i != this.mOutputOffscreen2.getWidth() || i2 != this.mOutputOffscreen2.getHeight()) {
                    this.mOutputOffscreen2.assignTexture(i3, i, i2);
                }
                this.mOutputOffscreen2.makeCurrent();
                int[] iArr2 = {this.mOutputOffscreen.getTexId()};
                try {
                    MediaEffectKernel3x3Drawer mediaEffectKernel3x3Drawer = this.mDrawer2;
                    if (mediaEffectKernel3x3Drawer != null) {
                        mediaEffectKernel3x3Drawer.apply(iArr2, this.mOutputOffscreen2.copyTexMatrix(), 0);
                    } else {
                        this.mDrawer.apply(iArr2, this.mOutputOffscreen2.copyTexMatrix(), 0);
                    }
                } finally {
                    this.mOutputOffscreen2.swap();
                }
            } catch (Throwable th) {
                this.mOutputOffscreen.swap();
                throw th;
            }
        }
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public void apply(int[] iArr, GLSurface gLSurface) {
        if (this.mEnabled) {
            if (this.mOutputOffscreen == null) {
                this.mOutputOffscreen = GLSurface.newInstance(false, gLSurface.getWidth(), gLSurface.getHeight(), false);
            }
            this.mOutputOffscreen.makeCurrent();
            try {
                this.mDrawer.apply(iArr, this.mOutputOffscreen.copyTexMatrix(), 0);
                this.mOutputOffscreen.swap();
                gLSurface.makeCurrent();
                int[] iArr2 = {this.mOutputOffscreen.getTexId()};
                try {
                    MediaEffectKernel3x3Drawer mediaEffectKernel3x3Drawer = this.mDrawer2;
                    if (mediaEffectKernel3x3Drawer != null) {
                        mediaEffectKernel3x3Drawer.apply(iArr2, gLSurface.copyTexMatrix(), 0);
                    } else {
                        this.mDrawer.apply(iArr2, gLSurface.copyTexMatrix(), 0);
                    }
                } finally {
                    gLSurface.swap();
                }
            } catch (Throwable th) {
                this.mOutputOffscreen.swap();
                throw th;
            }
        }
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public void apply(ISource iSource) {
        if (this.mEnabled) {
            GLSurface outputTexture = iSource.getOutputTexture();
            int[] sourceTexId = iSource.getSourceTexId();
            int width = iSource.getWidth();
            int height = iSource.getHeight();
            if (this.mOutputOffscreen == null) {
                this.mOutputOffscreen = GLSurface.newInstance(false, width, height, false);
            }
            this.mOutputOffscreen.makeCurrent();
            try {
                this.mDrawer.apply(sourceTexId, this.mOutputOffscreen.copyTexMatrix(), 0);
                this.mOutputOffscreen.swap();
                outputTexture.makeCurrent();
                int[] iArr = {this.mOutputOffscreen.getTexId()};
                try {
                    MediaEffectKernel3x3Drawer mediaEffectKernel3x3Drawer = this.mDrawer2;
                    if (mediaEffectKernel3x3Drawer != null) {
                        mediaEffectKernel3x3Drawer.apply(iArr, outputTexture.copyTexMatrix(), 0);
                    } else {
                        this.mDrawer.apply(iArr, outputTexture.copyTexMatrix(), 0);
                    }
                } finally {
                    outputTexture.swap();
                }
            } catch (Throwable th) {
                this.mOutputOffscreen.swap();
                throw th;
            }
        }
    }
}
