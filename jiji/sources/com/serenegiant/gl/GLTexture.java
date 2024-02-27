package com.serenegiant.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;

/* loaded from: classes2.dex */
public class GLTexture implements GLConst {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_ADJUST_POWER2 = false;
    private static final String TAG = "GLTexture";
    private final boolean ADJUST_POWER2;
    private final int FILTER_PARAM;
    private final int TEX_TARGET;
    private final int TEX_UNIT;
    private int mHeight;
    private int mTexHeight;
    private int mTexWidth;
    private int mTextureId;
    private int mWidth;
    private final boolean mWrappedTexture;
    private int viewPortHeight;
    private int viewPortWidth;
    private int viewPortX;
    private int viewPortY;
    private final float[] mTexMatrix = new float[16];
    private final float[] mResultMatrix = new float[16];

    public int getHeight() {
        return 0;
    }

    public int getWidth() {
        return 0;
    }

    public static GLTexture newInstance(int i, int i2) {
        return new GLTexture(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, false, 9729);
    }

    public static GLTexture newInstance(int i, int i2, int i3) {
        return new GLTexture(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, false, 9729);
    }

    public static GLTexture newInstance(int i, int i2, int i3, int i4) {
        return new GLTexture(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, false, i4);
    }

    public static GLTexture newInstance(int i, int i2, int i3, boolean z, int i4) {
        return new GLTexture(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, z, i4);
    }

    public static GLTexture wrap(int i, int i2, int i3, int i4, int i5) {
        return new GLTexture(i, i2, i3, i4, i5, false, 9729);
    }

    protected GLTexture(int i, int i2, int i3, int i4, int i5, boolean z, int i6) {
        this.TEX_TARGET = i;
        this.TEX_UNIT = i2;
        boolean z2 = true;
        this.mWrappedTexture = i3 > -1;
        this.mTextureId = i3;
        this.FILTER_PARAM = i6;
        this.ADJUST_POWER2 = (!z || i3 > -1) ? false : z2;
        createTexture(i4, i5);
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void release() {
        releaseTexture();
    }

    public void bind() {
        GLES20.glActiveTexture(this.TEX_UNIT);
        GLES20.glBindTexture(this.TEX_TARGET, this.mTextureId);
        setViewPort(this.viewPortX, this.viewPortY, this.viewPortWidth, this.viewPortHeight);
    }

    public void bindTexture() {
        GLES20.glActiveTexture(this.TEX_UNIT);
        GLES20.glBindTexture(this.TEX_TARGET, this.mTextureId);
    }

    public void setViewPort(int i, int i2, int i3, int i4) {
        this.viewPortX = i;
        this.viewPortY = i2;
        this.viewPortWidth = i3;
        this.viewPortHeight = i4;
        GLES20.glViewport(i, i2, i3, i4);
    }

    public void unbind() {
        GLES20.glActiveTexture(this.TEX_UNIT);
        GLES20.glBindTexture(this.TEX_TARGET, 0);
    }

    public boolean isValid() {
        return this.mTextureId > -1;
    }

    public boolean isOES() {
        return this.TEX_TARGET == 36197;
    }

    public int getTexTarget() {
        return this.TEX_TARGET;
    }

    public int getTexUnit() {
        return this.TEX_UNIT;
    }

    public int getTexId() {
        return this.mTextureId;
    }

    public float[] copyTexMatrix() {
        System.arraycopy(this.mTexMatrix, 0, this.mResultMatrix, 0, 16);
        return this.mResultMatrix;
    }

    public void copyTexMatrix(float[] fArr, int i) {
        float[] fArr2 = this.mTexMatrix;
        System.arraycopy(fArr2, 0, fArr, i, fArr2.length);
    }

    public float[] getTexMatrix() {
        return this.mTexMatrix;
    }

    public int getTexWidth() {
        return this.mTexWidth;
    }

    public int getTexHeight() {
        return this.mTexHeight;
    }

    public void loadBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if ((!this.mWrappedTexture && width > this.mTexWidth) || height > this.mTexHeight) {
            releaseTexture();
            createTexture(width, height);
        }
        bindTexture();
        android.opengl.GLUtils.texImage2D(this.TEX_TARGET, 0, bitmap, 0);
        GLES20.glBindTexture(this.TEX_TARGET, 0);
        Matrix.setIdentityM(this.mTexMatrix, 0);
        float[] fArr = this.mTexMatrix;
        fArr[0] = width / this.mTexWidth;
        fArr[5] = height / this.mTexHeight;
    }

    private void createTexture(int i, int i2) {
        if (this.mTextureId <= -1) {
            if (this.ADJUST_POWER2) {
                int i3 = 1;
                int i4 = 1;
                while (i4 < i) {
                    i4 <<= 1;
                }
                while (i3 < i2) {
                    i3 <<= 1;
                }
                if (this.mTexWidth != i4 || this.mTexHeight != i3) {
                    this.mTexWidth = i4;
                    this.mTexHeight = i3;
                }
            } else {
                this.mTexWidth = i;
                this.mTexHeight = i2;
            }
            this.mWidth = i;
            this.mHeight = i2;
            this.mTextureId = GLUtils.initTex(this.TEX_TARGET, this.TEX_UNIT, this.FILTER_PARAM);
            GLES20.glTexImage2D(this.TEX_TARGET, 0, 6408, this.mTexWidth, this.mTexHeight, 0, 6408, 5121, null);
        } else {
            this.mTexWidth = i;
            this.mWidth = i;
            this.mTexHeight = i2;
            this.mHeight = i2;
        }
        Matrix.setIdentityM(this.mTexMatrix, 0);
        float[] fArr = this.mTexMatrix;
        int i5 = this.mWidth;
        fArr[0] = i5 / this.mTexWidth;
        int i6 = this.mHeight;
        fArr[5] = i6 / this.mTexHeight;
        setViewPort(0, 0, i5, i6);
    }

    private void releaseTexture() {
        int i;
        if (this.mWrappedTexture || (i = this.mTextureId) <= -1) {
            return;
        }
        GLUtils.deleteTex(i);
        this.mTextureId = -1;
    }
}
