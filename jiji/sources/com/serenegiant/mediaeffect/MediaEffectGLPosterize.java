package com.serenegiant.mediaeffect;

import com.serenegiant.gl.ShaderConst;

/* loaded from: classes2.dex */
public class MediaEffectGLPosterize extends MediaEffectGLBase {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaEffectGLBrightness";
    private static final String FRAGMENT_SHADER_BASE = "#version 100\n%sprecision highp float;\nvarying       vec2 vTextureCoord;\nuniform %s    sTexture;\nuniform float uColorAdjust;\nvoid main() {\n    vec4 tex = texture2D(sTexture, vTextureCoord);\n    gl_FragColor = floor((tex * uColorAdjust) + vec4(0.5)) / uColorAdjust;\n}\n";
    private static final String FRAGMENT_SHADER = String.format(FRAGMENT_SHADER_BASE, "", ShaderConst.SAMPLER_2D);
    private static final String FRAGMENT_SHADER_EXT = String.format(FRAGMENT_SHADER_BASE, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES);

    public MediaEffectGLPosterize() {
        this(10.0f);
    }

    public MediaEffectGLPosterize(float f) {
        super(new MediaEffectColorAdjustDrawer(FRAGMENT_SHADER));
        setParameter(f);
    }

    public MediaEffectGLPosterize setParameter(float f) {
        ((MediaEffectColorAdjustDrawer) this.mDrawer).setColorAdjust(f);
        return this;
    }
}
