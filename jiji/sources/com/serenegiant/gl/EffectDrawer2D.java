package com.serenegiant.gl;

import android.opengl.GLES20;
import android.util.SparseArray;

/* loaded from: classes2.dex */
public class EffectDrawer2D extends GLDrawer2D {
    private static final boolean DEBUG = false;
    private static final String TAG = "EffectDrawer2D";
    private float[] mCurrentParams;
    private int mEffect;
    private final EffectListener mEffectListener;
    private final SparseArray<float[]> mParams;
    private int muParamsLoc;

    /* loaded from: classes2.dex */
    public interface EffectListener {
        boolean onChangeEffect(int i, GLDrawer2D gLDrawer2D);
    }

    public EffectDrawer2D(boolean z, boolean z2) {
        this(z, z2, null, null, null, null, null);
    }

    public EffectDrawer2D(boolean z, boolean z2, EffectListener effectListener) {
        this(z, z2, null, null, null, null, effectListener);
    }

    public EffectDrawer2D(boolean z, float[] fArr, float[] fArr2, boolean z2) {
        this(z, z2, fArr, fArr2, null, null, null);
    }

    public EffectDrawer2D(boolean z, float[] fArr, float[] fArr2, boolean z2, EffectListener effectListener) {
        this(z, z2, fArr, fArr2, null, null, effectListener);
    }

    protected EffectDrawer2D(boolean z, boolean z2, float[] fArr, float[] fArr2, String str, String str2, EffectListener effectListener) {
        super(z, z2, fArr, fArr2, str, str2);
        this.mParams = new SparseArray<>();
        this.mEffectListener = effectListener;
        resetShader();
    }

    @Override // com.serenegiant.gl.GLDrawer2D
    public void resetShader() {
        super.resetShader();
        this.mParams.clear();
        this.mParams.put(9, new float[]{0.17f, 0.85f, 0.5f, 1.0f, 0.4f, 1.0f, 1.0f, 1.0f, 5.0f, 1.0f, 1.0f, 1.0f});
        this.mParams.put(10, new float[]{0.17f, 0.85f, 0.5f, 1.0f, 0.4f, 1.0f, 1.0f, 1.0f, 5.0f, 1.0f, 1.0f, 1.0f});
        this.mParams.put(11, new float[]{0.1f, 0.19f, 0.3f, 1.0f, 0.3f, 1.0f, 1.0f, 1.0f, 5.0f, 1.0f, 0.8f, 0.8f, 0.15f, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f});
        this.mEffect = 0;
    }

    public void resetEffect() {
        resetShader();
    }

    public void setEffect(int i) {
        if (this.mEffect != i) {
            this.mEffect = i;
            boolean z = false;
            try {
                EffectListener effectListener = this.mEffectListener;
                if (effectListener != null) {
                    if (effectListener.onChangeEffect(i, this)) {
                        z = true;
                    }
                }
            } catch (Exception unused) {
            }
            if (!z) {
                switch (i) {
                    case 0:
                        updateShader(this.isGLES3 ? ShaderConst.FRAGMENT_SHADER_EXT_ES3 : ShaderConst.FRAGMENT_SHADER_EXT_ES2);
                        break;
                    case 1:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_GRAY_ES3 : GLEffect.FRAGMENT_SHADER_EXT_GRAY_ES2);
                        break;
                    case 2:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_GRAY_EXT_REVERSE_ES3 : GLEffect.FRAGMENT_SHADER_GRAY_EXT_REVERSE_ES2);
                        break;
                    case 3:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_ES2);
                        break;
                    case 4:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_YELLOW_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_YELLOW_ES2);
                        break;
                    case 5:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_GREEN_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_GREEN_ES2);
                        break;
                    case 6:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_ES2);
                        break;
                    case 7:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_YELLOW_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_YELLOW_ES2);
                        break;
                    case 8:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_GREEN_ES3 : GLEffect.FRAGMENT_SHADER_EXT_BIN_REVERSE_GREEN_ES2);
                        break;
                    case 9:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_RED_YELLOWS_ES3 : GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_RED_YELLOWS_ES2);
                        break;
                    case 10:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_RED_YELLOW_WHITE_ES3 : GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_RED_YELLOW_WHITE_ES2);
                        break;
                    case 11:
                        updateShader(this.isGLES3 ? GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_YELLOW_WHITE_ES3 : GLEffect.FRAGMENT_SHADER_EXT_EMPHASIZE_YELLOW_WHITE_ES2);
                        break;
                    default:
                        resetShader();
                        break;
                }
            }
            this.muParamsLoc = glGetUniformLocation("uParams");
            this.mCurrentParams = this.mParams.get(i);
            updateParams();
        }
    }

    public int getCurrentEffect() {
        return this.mEffect;
    }

    public void setParams(float[] fArr) {
        setParams(this.mEffect, fArr);
    }

    public void setParams(int i, float[] fArr) throws IllegalArgumentException {
        if (i < 0 || this.mEffect == i) {
            this.mCurrentParams = fArr;
            this.mParams.put(this.mEffect, fArr);
            updateParams();
            return;
        }
        this.mParams.put(i, fArr);
    }

    private void updateParams() {
        float[] fArr = this.mCurrentParams;
        int min = Math.min(fArr != null ? fArr.length : 0, 18);
        if (this.muParamsLoc < 0 || min <= 0) {
            return;
        }
        glUseProgram();
        GLES20.glUniform1fv(this.muParamsLoc, min, this.mCurrentParams, 0);
    }
}
