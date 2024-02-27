package com.serenegiant.mediaeffect;

import com.serenegiant.gl.ShaderConst;

/* loaded from: classes2.dex */
public class MediaEffectGLMaskedAlphaBlend extends MediaEffectGLBase {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaEffectGLMaskedAlphaBlend";
    private static final String FRAGMENT_SHADER_BASE = "#version 100\n%sprecision highp float;\nvarying       vec2 vTextureCoord;\nuniform %s    sTexture;\nuniform %s    sTexture2;\nuniform %s    sTexture3;\nvoid main() {\n    highp vec4 tex1 = texture2D(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture2D(sTexture2, vTextureCoord);\n    highp float alpha = texture2D(sTexture3, vTextureCoord).a;\n    gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a * alpha), tex1.a);\n}\n";
    private static final String FRAGMENT_SHADER = String.format(FRAGMENT_SHADER_BASE, "", ShaderConst.SAMPLER_2D, ShaderConst.SAMPLER_2D, ShaderConst.SAMPLER_2D);
    private static final String FRAGMENT_SHADER_EXT = String.format(FRAGMENT_SHADER_BASE, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);

    public MediaEffectGLMaskedAlphaBlend() {
        this(false);
    }

    public MediaEffectGLMaskedAlphaBlend(boolean z) {
        super(MediaEffectDrawer.newInstance(3, z, z ? FRAGMENT_SHADER_EXT : FRAGMENT_SHADER));
    }
}
