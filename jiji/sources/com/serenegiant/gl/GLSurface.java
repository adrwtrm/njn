package com.serenegiant.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

/* loaded from: classes2.dex */
public abstract class GLSurface implements IGLSurface {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_ADJUST_POWER2 = false;
    private static final String TAG = "GLSurface";
    protected final int TEX_TARGET;
    protected final int TEX_UNIT;
    protected final boolean isGLES3;
    protected final boolean mAdjustPower2;
    protected int mDepthBufferObj;
    protected int mFBOTexId;
    protected int mFrameBufferObj;
    protected final boolean mHasDepthBuffer;
    protected int mHeight;
    private final float[] mResultMatrix;
    protected int mTexHeight;
    protected final float[] mTexMatrix;
    protected int mTexWidth;
    protected int mWidth;
    protected boolean mWrappedTexture;
    protected int viewPortHeight;
    protected int viewPortWidth;
    protected int viewPortX;
    protected int viewPortY;

    public abstract void assignTexture(int i, int i2, int i3);

    public abstract void bindTexture();

    protected abstract void createFrameBuffer(int i, int i2);

    protected abstract int genTexture(int i, int i2, int i3, int i4);

    protected abstract void releaseFrameBuffer();

    public static GLSurface newInstance(boolean z, int i, int i2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, false, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, false, false);
    }

    public static GLSurface newInstance(boolean z, int i, int i2, int i3) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, false, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, false, false);
    }

    public static GLSurface newInstance(boolean z, int i, int i2, boolean z2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, z2, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, z2, false);
    }

    public static GLSurface newInstance(boolean z, int i, int i2, int i3, boolean z2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, z2, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, z2, false);
    }

    public static GLSurface newInstance(boolean z, int i, int i2, boolean z2, boolean z3) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, z2, z3);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, 33984, -1, i, i2, z2, z3);
    }

    public static GLSurface newInstance(boolean z, int i, int i2, int i3, boolean z2, boolean z3) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, z2, z3);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, i, -1, i2, i3, z2, z3);
    }

    public static GLSurface wrap(boolean z, int i, int i2, int i3, int i4) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, i, i2, i3, i4, false, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, i, i2, i3, i4, false, false);
    }

    public static GLSurface wrap(boolean z, int i, int i2, int i3, int i4, boolean z2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(GLConst.GL_TEXTURE_2D, i, i2, i3, i4, z2, false);
        }
        return new GLSurfaceES2(GLConst.GL_TEXTURE_2D, i, i2, i3, i4, z2, false);
    }

    public static GLSurface wrap(boolean z, int i, int i2, int i3, int i4, int i5, boolean z2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLSurfaceES3(i, i2, i3, i4, i5, z2, false);
        }
        return new GLSurfaceES2(i, i2, i3, i4, i5, z2, false);
    }

    private GLSurface(boolean z, int i, int i2, int i3, int i4, int i5, boolean z2, boolean z3) {
        this.mFBOTexId = -1;
        this.mDepthBufferObj = -1;
        this.mFrameBufferObj = -1;
        this.mTexMatrix = new float[16];
        this.mResultMatrix = new float[16];
        this.isGLES3 = z;
        this.TEX_TARGET = i;
        this.TEX_UNIT = i2;
        this.mHasDepthBuffer = z2;
        this.mAdjustPower2 = z3;
        createFrameBuffer(i4, i5);
        assignTexture(i3 < 0 ? genTexture(i, i2, this.mTexWidth, this.mTexHeight) : i3, i4, i5);
        this.mWrappedTexture = i3 > -1;
        setViewPort(0, 0, this.mWidth, this.mHeight);
    }

    @Override // com.serenegiant.gl.ISurface
    public void release() {
        releaseFrameBuffer();
    }

    @Override // com.serenegiant.gl.ISurface
    public boolean isValid() {
        return this.mFrameBufferObj >= 0;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public boolean isOES() {
        return this.TEX_TARGET == 36197;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public int getTexTarget() {
        return this.TEX_TARGET;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public int getTexUnit() {
        return this.TEX_UNIT;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public int getTexId() {
        return this.mFBOTexId;
    }

    @Override // com.serenegiant.gl.ISurface
    public int getWidth() {
        return this.mWidth;
    }

    @Override // com.serenegiant.gl.ISurface
    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public int getTexWidth() {
        return this.mTexWidth;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public int getTexHeight() {
        return this.mTexHeight;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public float[] copyTexMatrix() {
        System.arraycopy(this.mTexMatrix, 0, this.mResultMatrix, 0, 16);
        return this.mResultMatrix;
    }

    @Override // com.serenegiant.gl.IGLSurface
    public void copyTexMatrix(float[] fArr, int i) {
        float[] fArr2 = this.mTexMatrix;
        System.arraycopy(fArr2, 0, fArr, i, fArr2.length);
    }

    @Override // com.serenegiant.gl.IGLSurface
    public float[] getTexMatrix() {
        return this.mTexMatrix;
    }

    /* loaded from: classes2.dex */
    private static class GLSurfaceES2 extends GLSurface {
        private GLSurfaceES2(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            super(false, i, i2, i3, i4, i5, z, z2);
        }

        @Override // com.serenegiant.gl.GLSurface
        public void bindTexture() {
            GLES20.glActiveTexture(this.TEX_UNIT);
            GLES20.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
        }

        @Override // com.serenegiant.gl.ISurface
        public void makeCurrent() {
            GLES20.glActiveTexture(this.TEX_UNIT);
            GLES20.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
            GLES20.glBindFramebuffer(36160, this.mFrameBufferObj);
            setViewPort(this.viewPortX, this.viewPortY, this.viewPortWidth, this.viewPortHeight);
        }

        @Override // com.serenegiant.gl.ISurface
        public void setViewPort(int i, int i2, int i3, int i4) {
            this.viewPortX = i;
            this.viewPortY = i2;
            this.viewPortWidth = i3;
            this.viewPortHeight = i4;
            GLES20.glViewport(i, i2, i3, i4);
        }

        @Override // com.serenegiant.gl.ISurface
        public void swap() {
            GLES20.glBindFramebuffer(36160, 0);
            GLES20.glActiveTexture(this.TEX_UNIT);
            GLES20.glBindTexture(this.TEX_TARGET, 0);
        }

        @Override // com.serenegiant.gl.GLSurface
        public void assignTexture(int i, int i2, int i3) {
            if (i2 > this.mTexWidth || i3 > this.mTexHeight) {
                releaseFrameBuffer();
                createFrameBuffer(i2, i3);
            }
            if (!this.mWrappedTexture && this.mFBOTexId > -1) {
                GLUtils.deleteTex(this.mFBOTexId);
            }
            this.mWrappedTexture = true;
            this.mFBOTexId = i;
            GLES20.glActiveTexture(this.TEX_UNIT);
            GLES20.glBindFramebuffer(36160, this.mFrameBufferObj);
            GLUtils.checkGlError("glBindFramebuffer " + this.mFrameBufferObj);
            GLES20.glFramebufferTexture2D(36160, 36064, this.TEX_TARGET, this.mFBOTexId, 0);
            GLUtils.checkGlError("glFramebufferTexture2D");
            if (this.mHasDepthBuffer) {
                GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.mDepthBufferObj);
                GLUtils.checkGlError("glFramebufferRenderbuffer");
            }
            int glCheckFramebufferStatus = GLES20.glCheckFramebufferStatus(36160);
            if (glCheckFramebufferStatus != 36053) {
                throw new RuntimeException("Framebuffer not complete, status=" + glCheckFramebufferStatus);
            }
            GLES20.glBindFramebuffer(36160, 0);
            Matrix.setIdentityM(this.mTexMatrix, 0);
            this.mTexMatrix[0] = i2 / this.mTexWidth;
            this.mTexMatrix[5] = i3 / this.mTexHeight;
        }

        @Override // com.serenegiant.gl.IGLSurface
        public void loadBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > this.mTexWidth || height > this.mTexHeight) {
                releaseFrameBuffer();
                createFrameBuffer(width, height);
            }
            GLES20.glActiveTexture(this.TEX_UNIT);
            GLES20.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
            android.opengl.GLUtils.texImage2D(this.TEX_TARGET, 0, bitmap, 0);
            GLES20.glBindTexture(this.TEX_TARGET, 0);
            Matrix.setIdentityM(this.mTexMatrix, 0);
            this.mTexMatrix[0] = width / this.mTexWidth;
            this.mTexMatrix[5] = height / this.mTexHeight;
        }

        @Override // com.serenegiant.gl.GLSurface
        protected void createFrameBuffer(int i, int i2) {
            int[] iArr = new int[1];
            if (this.mAdjustPower2) {
                int i3 = 1;
                while (i3 < i) {
                    i3 <<= 1;
                }
                int i4 = 1;
                while (i4 < i2) {
                    i4 <<= 1;
                }
                if (this.mTexWidth != i3 || this.mTexHeight != i4) {
                    this.mTexWidth = i3;
                    this.mTexHeight = i4;
                }
            } else {
                this.mTexWidth = i;
                this.mTexHeight = i2;
            }
            this.mWidth = i;
            this.mHeight = i2;
            if (this.mHasDepthBuffer) {
                GLES20.glGenRenderbuffers(1, iArr, 0);
                this.mDepthBufferObj = iArr[0];
                GLES20.glBindRenderbuffer(36161, this.mDepthBufferObj);
                GLES20.glRenderbufferStorage(36161, 33189, this.mTexWidth, this.mTexHeight);
            }
            GLES20.glGenFramebuffers(1, iArr, 0);
            GLUtils.checkGlError("glGenFramebuffers");
            this.mFrameBufferObj = iArr[0];
            GLES20.glBindFramebuffer(36160, this.mFrameBufferObj);
            GLUtils.checkGlError("glBindFramebuffer " + this.mFrameBufferObj);
            GLES20.glBindFramebuffer(36160, 0);
        }

        @Override // com.serenegiant.gl.GLSurface
        protected void releaseFrameBuffer() {
            int[] iArr = new int[1];
            if (this.mDepthBufferObj > -1) {
                iArr[0] = this.mDepthBufferObj;
                GLES20.glDeleteRenderbuffers(1, iArr, 0);
                this.mDepthBufferObj = -1;
            }
            if (!this.mWrappedTexture && this.mFBOTexId > -1) {
                GLUtils.deleteTex(this.mFBOTexId);
                this.mFBOTexId = -1;
            }
            if (this.mFrameBufferObj > -1) {
                iArr[0] = this.mFrameBufferObj;
                GLES20.glDeleteFramebuffers(1, iArr, 0);
                this.mFrameBufferObj = -1;
            }
        }

        @Override // com.serenegiant.gl.GLSurface
        protected int genTexture(int i, int i2, int i3, int i4) {
            int initTex = GLUtils.initTex(i, i2, 9729, 9729, 33071);
            GLES20.glTexImage2D(i, 0, 6408, i3, i4, 0, 6408, 5121, null);
            GLUtils.checkGlError("glTexImage2D");
            this.mWrappedTexture = false;
            return initTex;
        }
    }

    /* loaded from: classes2.dex */
    private static class GLSurfaceES3 extends GLSurface {
        private GLSurfaceES3(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            super(true, i, i2, i3, i4, i5, z, z2);
        }

        @Override // com.serenegiant.gl.GLSurface
        public void bindTexture() {
            GLES30.glActiveTexture(this.TEX_UNIT);
            GLES30.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
        }

        @Override // com.serenegiant.gl.ISurface
        public void makeCurrent() {
            GLES30.glActiveTexture(this.TEX_UNIT);
            GLES30.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
            GLES30.glBindFramebuffer(36160, this.mFrameBufferObj);
            setViewPort(this.viewPortX, this.viewPortY, this.viewPortWidth, this.viewPortHeight);
        }

        @Override // com.serenegiant.gl.ISurface
        public void setViewPort(int i, int i2, int i3, int i4) {
            this.viewPortX = i;
            this.viewPortY = i2;
            this.viewPortWidth = i3;
            this.viewPortHeight = i4;
            GLES30.glViewport(i, i2, i3, i4);
        }

        @Override // com.serenegiant.gl.ISurface
        public void swap() {
            GLES30.glBindFramebuffer(36160, 0);
            GLES30.glActiveTexture(this.TEX_UNIT);
            GLES30.glBindTexture(this.TEX_TARGET, 0);
        }

        @Override // com.serenegiant.gl.GLSurface
        public void assignTexture(int i, int i2, int i3) {
            if (i2 > this.mTexWidth || i3 > this.mTexHeight) {
                releaseFrameBuffer();
                createFrameBuffer(i2, i3);
            }
            if (!this.mWrappedTexture && this.mFBOTexId > -1) {
                GLUtils.deleteTex(this.mFBOTexId);
            }
            this.mWrappedTexture = true;
            this.mFBOTexId = i;
            GLES30.glActiveTexture(this.TEX_UNIT);
            GLES30.glBindFramebuffer(36160, this.mFrameBufferObj);
            GLUtils.checkGlError("glBindFramebuffer " + this.mFrameBufferObj);
            GLES30.glFramebufferTexture2D(36160, 36064, this.TEX_TARGET, this.mFBOTexId, 0);
            GLUtils.checkGlError("glFramebufferTexture2D");
            if (this.mHasDepthBuffer) {
                GLES30.glFramebufferRenderbuffer(36160, 36096, 36161, this.mDepthBufferObj);
                GLUtils.checkGlError("glFramebufferRenderbuffer");
            }
            int glCheckFramebufferStatus = GLES30.glCheckFramebufferStatus(36160);
            if (glCheckFramebufferStatus != 36053) {
                throw new RuntimeException("Framebuffer not complete, status=" + glCheckFramebufferStatus);
            }
            GLES30.glBindFramebuffer(36160, 0);
            Matrix.setIdentityM(this.mTexMatrix, 0);
            this.mTexMatrix[0] = i2 / this.mTexWidth;
            this.mTexMatrix[5] = i3 / this.mTexHeight;
        }

        @Override // com.serenegiant.gl.IGLSurface
        public void loadBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > this.mTexWidth || height > this.mTexHeight) {
                releaseFrameBuffer();
                createFrameBuffer(width, height);
            }
            GLES30.glActiveTexture(this.TEX_UNIT);
            GLES30.glBindTexture(this.TEX_TARGET, this.mFBOTexId);
            android.opengl.GLUtils.texImage2D(this.TEX_TARGET, 0, bitmap, 0);
            GLES30.glBindTexture(this.TEX_TARGET, 0);
            Matrix.setIdentityM(this.mTexMatrix, 0);
            this.mTexMatrix[0] = width / this.mTexWidth;
            this.mTexMatrix[5] = height / this.mTexHeight;
        }

        @Override // com.serenegiant.gl.GLSurface
        protected void createFrameBuffer(int i, int i2) {
            int[] iArr = new int[1];
            if (this.mAdjustPower2) {
                int i3 = 1;
                while (i3 < i) {
                    i3 <<= 1;
                }
                int i4 = 1;
                while (i4 < i2) {
                    i4 <<= 1;
                }
                if (this.mTexWidth != i3 || this.mTexHeight != i4) {
                    this.mTexWidth = i3;
                    this.mTexHeight = i4;
                }
            } else {
                this.mTexWidth = i;
                this.mTexHeight = i2;
            }
            this.mWidth = i;
            this.mHeight = i2;
            if (this.mHasDepthBuffer) {
                GLES30.glGenRenderbuffers(1, iArr, 0);
                this.mDepthBufferObj = iArr[0];
                GLES30.glBindRenderbuffer(36161, this.mDepthBufferObj);
                GLES30.glRenderbufferStorage(36161, 33189, this.mTexWidth, this.mTexHeight);
            }
            GLES30.glGenFramebuffers(1, iArr, 0);
            GLUtils.checkGlError("glGenFramebuffers");
            this.mFrameBufferObj = iArr[0];
            GLES30.glBindFramebuffer(36160, this.mFrameBufferObj);
            GLUtils.checkGlError("glBindFramebuffer " + this.mFrameBufferObj);
            GLES30.glBindFramebuffer(36160, 0);
        }

        @Override // com.serenegiant.gl.GLSurface
        protected void releaseFrameBuffer() {
            int[] iArr = new int[1];
            if (this.mDepthBufferObj > -1) {
                iArr[0] = this.mDepthBufferObj;
                GLES30.glDeleteRenderbuffers(1, iArr, 0);
                this.mDepthBufferObj = -1;
            }
            if (!this.mWrappedTexture && this.mFBOTexId > -1) {
                GLUtils.deleteTex(this.mFBOTexId);
                this.mFBOTexId = -1;
            }
            if (this.mFrameBufferObj > -1) {
                iArr[0] = this.mFrameBufferObj;
                GLES30.glDeleteFramebuffers(1, iArr, 0);
                this.mFrameBufferObj = -1;
            }
        }

        @Override // com.serenegiant.gl.GLSurface
        protected int genTexture(int i, int i2, int i3, int i4) {
            int initTex = GLUtils.initTex(i, i2, 9729, 9729, 33071);
            GLES30.glTexImage2D(i, 0, 6408, i3, i4, 0, 6408, 5121, null);
            GLUtils.checkGlError("glTexImage2D");
            this.mWrappedTexture = false;
            return initTex;
        }
    }
}
