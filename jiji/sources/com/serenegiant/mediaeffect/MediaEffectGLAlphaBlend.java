package com.serenegiant.mediaeffect;

import android.opengl.GLES20;
import com.serenegiant.gl.ShaderConst;

/* loaded from: classes2.dex */
public class MediaEffectGLAlphaBlend extends MediaEffectGLBase {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaEffectGLAlphaBlend";
    private static final String FRAGMENT_SHADER_BASE = "#version 100\n%sprecision highp float;\nvarying       vec2 vTextureCoord;\nuniform %s    sTexture;\nuniform %s    sTexture2;\nuniform float uMixRate;\nvoid main() {\n    highp vec4 tex1 = texture2D(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture2D(sTexture2, vTextureCoord);\n    gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a * uMixRate), tex1.a);\n}\n";
    private static final String FRAGMENT_SHADER = String.format(FRAGMENT_SHADER_BASE, "", ShaderConst.SAMPLER_2D, ShaderConst.SAMPLER_2D);
    private static final String FRAGMENT_SHADER_EXT = String.format(FRAGMENT_SHADER_BASE, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class MediaEffectAlphaBlendDrawer extends MediaEffectDrawer {
        private float mMixRate;
        private final int muMixRate;

        protected MediaEffectAlphaBlendDrawer(float f) {
            this(f, false);
        }

        protected MediaEffectAlphaBlendDrawer(float f, boolean z) {
            super(2, z, z ? MediaEffectGLAlphaBlend.FRAGMENT_SHADER_EXT : MediaEffectGLAlphaBlend.FRAGMENT_SHADER);
            int glGetUniformLocation = GLES20.glGetUniformLocation(getProgram(), "uMixRate");
            this.muMixRate = glGetUniformLocation < 0 ? -1 : glGetUniformLocation;
            setMixRate(f);
        }

        public void setMixRate(float f) {
            synchronized (this.mSync) {
                this.mMixRate = f;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.mediaeffect.MediaEffectDrawer
        public void preDraw(int[] iArr, float[] fArr, int i) {
            super.preDraw(iArr, fArr, i);
            if (this.muMixRate >= 0) {
                synchronized (this.mSync) {
                    GLES20.glUniform1f(this.muMixRate, this.mMixRate);
                }
            }
        }
    }

    public MediaEffectGLAlphaBlend() {
        this(0.5f);
    }

    public MediaEffectGLAlphaBlend(float f) {
        super(new MediaEffectAlphaBlendDrawer(f));
        setParameter(f);
    }

    public MediaEffectGLAlphaBlend setParameter(float f) {
        setEnable(true);
        ((MediaEffectAlphaBlendDrawer) this.mDrawer).setMixRate(f);
        return this;
    }
}
