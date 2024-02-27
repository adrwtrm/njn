package com.serenegiant.mediaeffect;

import android.opengl.GLES20;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.ShaderConst;
import com.serenegiant.mediaeffect.MediaEffectDrawer;

/* loaded from: classes2.dex */
public class MediaEffectGLDilation extends MediaEffectGLBase {
    private static final boolean DEBUG = false;
    public static final String FRAGMENT_SHADER_1 = "precision lowp float;\nvarying       vec2 vTextureCoord;\nuniform vec2  uTexOffset[41];\nuniform sampler2D sTexture;\n\nvoid main()\n{\nvec4 maxValue = texture2D(sTexture, vTextureCoord + uTexOffset[0] );\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[1] ));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[2] ));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[3] ));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[4] ));\n\ngl_FragColor = vec4(maxValue.rgb, 1.0);\n}\n";
    public static final String FRAGMENT_SHADER_2 = "precision lowp float;\n\nvarying       vec2 vTextureCoord;\nuniform vec2  uTexOffset[41];\nuniform sampler2D sTexture;\n\nvoid main()\n{\nvec4 maxValue = texture2D(sTexture, vTextureCoord + uTexOffset[0]);\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[1]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[2]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[3]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[4]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[5]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[6]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[7]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[8]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[9]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[10]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[11]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[12]));\n\ngl_FragColor = vec4(maxValue.rgb, 1.0);\n}\n";
    public static final String FRAGMENT_SHADER_3 = "precision lowp float;\nvarying       vec2 vTextureCoord;\nuniform vec2  uTexOffset[41];\nuniform sampler2D sTexture;\n\nvoid main()\n{\nvec4 maxValue = texture2D(sTexture, vTextureCoord + uTexOffset[0]);\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[1]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[2]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[3]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[4]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[5]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[6]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[7]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[8]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[9]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[10]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[11]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[12]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[13]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[14]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[15]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[16]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[17]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[18]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[19]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[20]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[21]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[22]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[23]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[24]));\n\ngl_FragColor = vec4(maxValue.rgb, 1.0);\n}\n";
    public static final String FRAGMENT_SHADER_4 = "precision lowp float;\nvarying       vec2 vTextureCoord;\nuniform vec2  uTexOffset[41];\nuniform sampler2D sTexture;\n\nvoid main()\n{\nvec4 maxValue = texture2D(sTexture, vTextureCoord + uTexOffset[0]);\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[1]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[2]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[3]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[4]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[5]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[6]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[7]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[8]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[9]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[10]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[11]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[12]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[13]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[14]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[15]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[16]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[17]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[18]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[19]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[20]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[21]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[22]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[23]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[24]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[25]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[26]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[27]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[28]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[29]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[30]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[31]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[32]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[33]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[34]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[35]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[36]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[37]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[38]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[39]));\nmaxValue = max(maxValue, texture2D(sTexture, vTextureCoord + uTexOffset[40]));\n\ngl_FragColor = vec4(maxValue.rgb, 1.0);\n}\n";
    private static final String TAG = "MediaEffectGLDilation";

    private static String getFragmentShader(int i) {
        return (i == 0 || i == 1) ? FRAGMENT_SHADER_1 : i != 2 ? i != 3 ? FRAGMENT_SHADER_4 : FRAGMENT_SHADER_3 : FRAGMENT_SHADER_2;
    }

    /* loaded from: classes2.dex */
    private static class MediaEffectDilationDrawer extends MediaEffectDrawer.MediaEffectSingleDrawer {
        private float mTexHeight;
        private final float[] mTexOffset;
        private float mTexWidth;
        private final int muTexOffsetLoc;

        public MediaEffectDilationDrawer(String str) {
            super(false, ShaderConst.VERTEX_SHADER_ES2, str);
            this.mTexOffset = new float[82];
            int glGetUniformLocation = GLES20.glGetUniformLocation(getProgram(), "uTexOffset");
            this.muTexOffsetLoc = glGetUniformLocation;
            GLUtils.checkLocation(glGetUniformLocation, "uTexOffset");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.mediaeffect.MediaEffectDrawer
        public void preDraw(int[] iArr, float[] fArr, int i) {
            super.preDraw(iArr, fArr, i);
            int i2 = this.muTexOffsetLoc;
            if (i2 >= 0) {
                GLES20.glUniform2fv(i2, 41, this.mTexOffset, 0);
            }
        }

        @Override // com.serenegiant.mediaeffect.MediaEffectDrawer
        public void setTexSize(int i, int i2) {
            synchronized (this.mSync) {
                float f = i2;
                this.mTexHeight = f;
                float f2 = i;
                this.mTexWidth = f2;
                float f3 = 1.0f / f2;
                float f4 = 1.0f / f;
                float[] fArr = this.mTexOffset;
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                fArr[2] = 0.0f;
                float f5 = -f4;
                fArr[3] = f5;
                fArr[4] = 0.0f;
                fArr[5] = f4;
                float f6 = -f3;
                fArr[6] = f6;
                fArr[7] = 0.0f;
                fArr[8] = f3;
                fArr[9] = 0.0f;
                fArr[10] = 0.0f;
                float f7 = f5 * 2.0f;
                fArr[11] = f7;
                fArr[12] = 0.0f;
                float f8 = f4 * 2.0f;
                fArr[13] = f8;
                float f9 = f6 * 2.0f;
                fArr[14] = f9;
                fArr[15] = 0.0f;
                float f10 = 2.0f * f3;
                fArr[16] = f10;
                fArr[17] = 0.0f;
                fArr[18] = f6;
                fArr[19] = f5;
                fArr[20] = f6;
                fArr[21] = f4;
                fArr[22] = f3;
                fArr[23] = f5;
                fArr[24] = f3;
                fArr[25] = f4;
                fArr[26] = 0.0f;
                float f11 = f5 * 3.0f;
                fArr[27] = f11;
                fArr[28] = 0.0f;
                float f12 = f4 * 3.0f;
                fArr[29] = f12;
                float f13 = f6 * 3.0f;
                fArr[30] = f13;
                fArr[31] = 0.0f;
                float f14 = 3.0f * f3;
                fArr[32] = f14;
                fArr[33] = 0.0f;
                fArr[34] = f9;
                fArr[35] = f5;
                fArr[36] = f9;
                fArr[37] = f4;
                fArr[38] = f10;
                fArr[39] = f5;
                fArr[40] = f10;
                fArr[41] = f4;
                fArr[42] = f6;
                fArr[43] = f7;
                fArr[44] = f6;
                fArr[45] = f8;
                fArr[46] = f3;
                fArr[47] = f7;
                fArr[48] = f3;
                fArr[49] = f8;
                fArr[50] = 0.0f;
                fArr[51] = f5 * 4.0f;
                fArr[52] = 0.0f;
                fArr[53] = f4 * 4.0f;
                fArr[54] = f6 * 4.0f;
                fArr[55] = 0.0f;
                fArr[56] = 4.0f * f3;
                fArr[57] = 0.0f;
                fArr[58] = f13;
                fArr[59] = f5;
                fArr[60] = f13;
                fArr[61] = f4;
                fArr[62] = f14;
                fArr[63] = f5;
                fArr[64] = f14;
                fArr[65] = f4;
                fArr[66] = f9;
                fArr[67] = f7;
                fArr[68] = f9;
                fArr[69] = f8;
                fArr[70] = f10;
                fArr[71] = f7;
                fArr[72] = f10;
                fArr[73] = f8;
                fArr[74] = f6;
                fArr[75] = f11;
                fArr[76] = f6;
                fArr[77] = f12;
                fArr[78] = f3;
                fArr[79] = f11;
                fArr[80] = f3;
                fArr[81] = f12;
            }
        }
    }

    public MediaEffectGLDilation() {
        this(1);
    }

    public MediaEffectGLDilation(int i) {
        super(new MediaEffectDilationDrawer(getFragmentShader(i)));
        setTexSize(256, 256);
    }

    @Override // com.serenegiant.mediaeffect.MediaEffectGLBase, com.serenegiant.mediaeffect.IEffect
    public MediaEffectGLDilation resize(int i, int i2) {
        super.resize(i, i2);
        setTexSize(i, i2);
        return this;
    }

    public void setTexSize(int i, int i2) {
        this.mDrawer.setTexSize(i, i2);
    }
}
