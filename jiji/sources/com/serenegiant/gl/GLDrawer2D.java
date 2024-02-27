package com.serenegiant.gl;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.utils.BufferHelper;
import java.nio.FloatBuffer;

/* loaded from: classes2.dex */
public class GLDrawer2D implements GLConst {
    private static final boolean DEBUG = false;
    protected static final int FLOAT_SZ = 4;
    private static final String TAG = "GLDrawer2D";
    protected static final boolean USE_VBO = true;
    protected final int VERTEX_NUM;
    protected final int VERTEX_SZ;
    private int errCnt;
    protected int hProgram;
    public final boolean isGLES3;
    protected final float[] mMvpMatrix;
    protected final int mTexTarget;
    protected int maPositionLoc;
    protected int maTextureCoordLoc;
    protected int muMVPMatrixLoc;
    protected int muTexMatrixLoc;
    protected int muTextureLoc;
    protected final FloatBuffer pTexCoord;
    protected final FloatBuffer pVertex;
    public static DrawerFactory DEFAULT_FACTORY = new DrawerFactory() { // from class: com.serenegiant.gl.GLDrawer2D.1
        @Override // com.serenegiant.gl.GLDrawer2D.DrawerFactory
        public GLDrawer2D create(boolean z, boolean z2) {
            return GLDrawer2D.create(z, z2);
        }
    };
    protected static final float[] DEFAULT_VERTICES = {1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f};
    protected static final float[] DEFAULT_TEXCOORD = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    protected static final float[] DEFAULT_TEXCOORD_FLIP_VERTICAL = {1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
    private int mBufVertex = -1;
    private int mBufTexCoord = -1;
    private final int[] status = new int[1];

    /* loaded from: classes2.dex */
    public interface DrawerFactory {
        GLDrawer2D create(boolean z, boolean z2);
    }

    public static GLDrawer2D create(boolean z, boolean z2) {
        return create(z, z2, null, null, null, null);
    }

    public static GLDrawer2D create(boolean z, boolean z2, String str) {
        return create(z, z2, null, null, null, str);
    }

    public static GLDrawer2D create(boolean z, boolean z2, String str, String str2) {
        return create(z, z2, null, null, str, str2);
    }

    public static GLDrawer2D create(boolean z, boolean z2, float[] fArr, float[] fArr2) {
        return create(z, z2, fArr, fArr2, null, null);
    }

    public static GLDrawer2D create(boolean z, boolean z2, float[] fArr, float[] fArr2, String str) {
        return create(z, z2, fArr, fArr2, null, str);
    }

    public static GLDrawer2D create(boolean z, boolean z2, float[] fArr, float[] fArr2, String str, String str2) {
        if (z && GLUtils.getSupportedGLVersion() > 2) {
            return new GLDrawer2D(true, z2, fArr, fArr2, str, str2);
        }
        return new GLDrawer2D(false, z2, fArr, fArr2, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public GLDrawer2D(boolean z, boolean z2, float[] fArr, float[] fArr2, String str, String str2) {
        float[] fArr3 = new float[16];
        this.mMvpMatrix = fArr3;
        this.isGLES3 = z;
        fArr = (fArr == null || fArr.length < 2) ? DEFAULT_VERTICES : fArr;
        fArr2 = (fArr2 == null || fArr2.length < 2) ? DEFAULT_TEXCOORD : fArr2;
        int min = Math.min(fArr.length, fArr2.length) / 2;
        this.VERTEX_NUM = min;
        this.VERTEX_SZ = min * 2;
        str = TextUtils.isEmpty(str) ? z ? ShaderConst.VERTEX_SHADER_ES3 : ShaderConst.VERTEX_SHADER_ES2 : str;
        if (TextUtils.isEmpty(str2)) {
            if (z) {
                str2 = z2 ? ShaderConst.FRAGMENT_SHADER_EXT_ES3 : ShaderConst.FRAGMENT_SHADER_ES3;
            } else {
                str2 = z2 ? ShaderConst.FRAGMENT_SHADER_EXT_ES2 : ShaderConst.FRAGMENT_SHADER_ES2;
            }
        }
        this.mTexTarget = z2 ? GLConst.GL_TEXTURE_EXTERNAL_OES : GLConst.GL_TEXTURE_2D;
        this.pVertex = BufferHelper.createFloatBuffer(fArr);
        this.pTexCoord = BufferHelper.createFloatBuffer(fArr2);
        Matrix.setIdentityM(fArr3, 0);
        updateShader(str, str2);
    }

    public void release() {
        releaseShader();
    }

    public boolean isOES() {
        return this.mTexTarget == 36197;
    }

    public float[] getMvpMatrix() {
        return this.mMvpMatrix;
    }

    public GLDrawer2D setMvpMatrix(float[] fArr, int i) {
        System.arraycopy(fArr, i, this.mMvpMatrix, 0, 16);
        return this;
    }

    public void copyMvpMatrix(float[] fArr, int i) {
        System.arraycopy(this.mMvpMatrix, 0, fArr, i, 16);
    }

    public void setMirror(int i) {
        GLUtils.setMirror(this.mMvpMatrix, i);
    }

    public void rotate(int i) {
        GLUtils.rotate(this.mMvpMatrix, i);
    }

    public void setRotation(int i) {
        GLUtils.setRotation(this.mMvpMatrix, i);
    }

    public void draw(IGLSurface iGLSurface) {
        draw(iGLSurface.getTexUnit(), iGLSurface.getTexId(), iGLSurface.getTexMatrix(), 0, this.mMvpMatrix, 0);
    }

    public void draw(GLTexture gLTexture) {
        draw(gLTexture.getTexUnit(), gLTexture.getTexId(), gLTexture.getTexMatrix(), 0, this.mMvpMatrix, 0);
    }

    public synchronized void draw(int i, int i2, float[] fArr, int i3) {
        draw(i, i2, fArr, i3, this.mMvpMatrix, 0);
    }

    public synchronized void draw(int i, int i2, float[] fArr, int i3, float[] fArr2, int i4) {
        if (this.hProgram < 0) {
            return;
        }
        glUseProgram();
        if (fArr != null) {
            updateTexMatrix(fArr, i3);
        }
        if (fArr2 != null) {
            updateMvpMatrix(fArr2, i4);
        }
        bindTexture(i, i2);
        if (validateProgram(this.hProgram)) {
            drawVertices();
            this.errCnt = 0;
        } else {
            int i5 = this.errCnt;
            this.errCnt = i5 + 1;
            if (i5 == 0) {
                Log.w(TAG, "draw:invalid program");
                resetShader();
            }
        }
        finishDraw();
    }

    protected void updateTexMatrix(float[] fArr, int i) {
        GLES20.glUniformMatrix4fv(this.muTexMatrixLoc, 1, false, fArr, i);
    }

    protected void updateMvpMatrix(float[] fArr, int i) {
        GLES20.glUniformMatrix4fv(this.muMVPMatrixLoc, 1, false, fArr, i);
    }

    protected void bindTexture(int i, int i2) {
        GLES20.glActiveTexture(i);
        GLES20.glBindTexture(this.mTexTarget, i2);
        GLES20.glUniform1i(this.muTextureLoc, GLUtils.gLTextureUnit2Index(i));
    }

    protected void updateVertices() {
        if (this.mBufVertex <= -1) {
            this.pVertex.clear();
            this.mBufVertex = GLUtils.createBuffer(34962, this.pVertex, 35044);
        }
        if (this.mBufTexCoord <= -1) {
            this.pTexCoord.clear();
            this.mBufTexCoord = GLUtils.createBuffer(34962, this.pTexCoord, 35044);
        }
        GLES20.glBindBuffer(34962, this.mBufVertex);
        GLES20.glVertexAttribPointer(this.maPositionLoc, 2, 5126, false, 0, 0);
        GLES20.glEnableVertexAttribArray(this.maPositionLoc);
        GLES20.glBindBuffer(34962, this.mBufTexCoord);
        GLES20.glVertexAttribPointer(this.maTextureCoordLoc, 2, 5126, false, 0, 0);
        GLES20.glEnableVertexAttribArray(this.maTextureCoordLoc);
    }

    protected void drawVertices() {
        GLES20.glDrawArrays(5, 0, this.VERTEX_NUM);
    }

    protected void finishDraw() {
        GLES20.glBindTexture(this.mTexTarget, 0);
        GLES20.glUseProgram(0);
    }

    public int initTex(int i) {
        return GLUtils.initTex(this.mTexTarget, i, 9728);
    }

    public int initTex(int i, int i2) {
        return GLUtils.initTex(this.mTexTarget, i, i2);
    }

    public void deleteTex(int i) {
        GLUtils.deleteTex(i);
    }

    public synchronized void updateShader(String str, String str2) {
        releaseShader();
        this.hProgram = loadShader(str, str2);
        init();
    }

    public void updateShader(String str) {
        updateShader(this.isGLES3 ? ShaderConst.VERTEX_SHADER_ES3 : ShaderConst.VERTEX_SHADER_ES2, str);
    }

    public void resetShader() {
        releaseShader();
        if (this.isGLES3) {
            this.hProgram = loadShader(ShaderConst.VERTEX_SHADER_ES3, isOES() ? ShaderConst.FRAGMENT_SHADER_EXT_ES3 : ShaderConst.FRAGMENT_SHADER_ES3);
        } else {
            this.hProgram = loadShader(ShaderConst.VERTEX_SHADER_ES2, isOES() ? ShaderConst.FRAGMENT_SHADER_EXT_ES2 : ShaderConst.FRAGMENT_SHADER_ES2);
        }
        init();
    }

    protected void releaseShader() {
        int i = this.hProgram;
        if (i > -1) {
            internalReleaseShader(i);
        }
        this.hProgram = -1;
    }

    protected int loadShader(String str, String str2) {
        return GLUtils.loadShader(str, str2);
    }

    protected void internalReleaseShader(int i) {
        int i2 = this.mBufVertex;
        if (i2 > -1) {
            GLUtils.deleteBuffer(i2);
            this.mBufVertex = -1;
        }
        int i3 = this.mBufTexCoord;
        if (i3 > -1) {
            GLUtils.deleteBuffer(i3);
            this.mBufTexCoord = -1;
        }
        GLES20.glDeleteProgram(i);
    }

    public int glGetAttribLocation(String str) {
        GLES20.glUseProgram(this.hProgram);
        return GLES20.glGetAttribLocation(this.hProgram, str);
    }

    public int glGetUniformLocation(String str) {
        GLES20.glUseProgram(this.hProgram);
        return GLES20.glGetUniformLocation(this.hProgram, str);
    }

    public void glUseProgram() {
        GLES20.glUseProgram(this.hProgram);
    }

    protected void init() {
        GLES20.glUseProgram(this.hProgram);
        this.maPositionLoc = GLES20.glGetAttribLocation(this.hProgram, "aPosition");
        this.maTextureCoordLoc = GLES20.glGetAttribLocation(this.hProgram, "aTextureCoord");
        this.muTextureLoc = GLES20.glGetAttribLocation(this.hProgram, "sTexture");
        this.muMVPMatrixLoc = GLES20.glGetUniformLocation(this.hProgram, "uMVPMatrix");
        this.muTexMatrixLoc = GLES20.glGetUniformLocation(this.hProgram, "uTexMatrix");
        GLES20.glUniformMatrix4fv(this.muMVPMatrixLoc, 1, false, this.mMvpMatrix, 0);
        GLES20.glUniformMatrix4fv(this.muTexMatrixLoc, 1, false, this.mMvpMatrix, 0);
        updateVertices();
    }

    protected boolean validateProgram(int i) {
        if (i >= 0) {
            GLES20.glValidateProgram(i);
            GLES20.glGetProgramiv(i, 35715, this.status, 0);
            return this.status[0] == 1;
        }
        return false;
    }
}
