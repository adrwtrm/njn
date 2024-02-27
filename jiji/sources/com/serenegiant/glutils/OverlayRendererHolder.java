package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.ShaderConst;
import com.serenegiant.glutils.AbstractRendererHolder;
import com.serenegiant.glutils.IRendererHolder;

/* loaded from: classes2.dex */
public class OverlayRendererHolder extends AbstractRendererHolder {
    private static final boolean DEBUG = false;
    private static final int REQUEST_UPDATE_OVERLAY = 100;
    private static final String TAG = "OverlayRendererHolder";
    private static final String FRAGMENT_SHADER_BASE_ES2 = "#version 100\n%sprecision highp float;\nvarying vec2 vTextureCoord;\nuniform %s sTexture;\nuniform %s sTexture2;\nvoid main() {\n    highp vec4 tex1 = texture2D(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture2D(sTexture2, vTextureCoord);\n    gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_EXT_ES2 = String.format(FRAGMENT_SHADER_BASE_ES2, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);
    private static final String FRAGMENT_SHADER_BASE_ES3 = "#version 300 es\n%sprecision highp float;\nin vec2 vTextureCoord;\nuniform %s sTexture;\nuniform %s sTexture2;\nlayout(location = 0) out vec4 o_FragColor;\nvoid main() {\n    highp vec4 tex1 = texture(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture(sTexture2, vTextureCoord);\n    o_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_EXT_ES3 = String.format(FRAGMENT_SHADER_BASE_ES3, ShaderConst.HEADER_OES_ES3, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);

    public OverlayRendererHolder(int i, int i2, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        this(i, i2, 3, null, 2, renderHolderCallback);
    }

    public OverlayRendererHolder(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        super(i, i2, i3, iContext, i4, renderHolderCallback);
        setOverlay(0, null);
    }

    @Override // com.serenegiant.glutils.AbstractRendererHolder
    protected AbstractRendererHolder.BaseRendererTask createRendererTask(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4) {
        return new OverlayRendererTask(this, i, i2, i3, iContext, i4, null);
    }

    public void setOverlay(int i, Bitmap bitmap) {
        ((OverlayRendererTask) this.mRendererTask).setOverlay(i, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class OverlayRendererTask extends AbstractRendererHolder.BaseRendererTask {
        private Surface mOverlaySurface;
        private int mOverlayTexId;
        private SurfaceTexture mOverlayTexture;
        private final float[] mTexMatrixOverlay;

        public OverlayRendererTask(AbstractRendererHolder abstractRendererHolder, int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, GLDrawer2D.DrawerFactory drawerFactory) {
            super(abstractRendererHolder, i, i2, i3, iContext, i4, drawerFactory);
            this.mTexMatrixOverlay = new float[16];
        }

        public void setOverlay(int i, Bitmap bitmap) {
            checkFinished();
            offer(100, i, 0, bitmap);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void internalOnStart() {
            super.internalOnStart();
            GLDrawer2D drawer = getDrawer();
            if (drawer != null) {
                if (isGLES3()) {
                    internalOnStartES3(drawer);
                } else {
                    internalOnStartES2(drawer);
                }
            }
        }

        private void internalOnStartES2(GLDrawer2D gLDrawer2D) {
            gLDrawer2D.updateShader(OverlayRendererHolder.MY_FRAGMENT_SHADER_EXT_ES2);
            GLES20.glUniform1i(gLDrawer2D.glGetUniformLocation("sTexture"), 0);
            int glGetUniformLocation = gLDrawer2D.glGetUniformLocation("sTexture2");
            this.mOverlayTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33985, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mOverlayTexId);
            this.mOverlayTexture = surfaceTexture;
            surfaceTexture.setDefaultBufferSize(width(), height());
            this.mOverlaySurface = new Surface(this.mOverlayTexture);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mOverlayTexId);
            GLES20.glUniform1i(glGetUniformLocation, 1);
        }

        private void internalOnStartES3(GLDrawer2D gLDrawer2D) {
            gLDrawer2D.updateShader(OverlayRendererHolder.MY_FRAGMENT_SHADER_EXT_ES3);
            GLES30.glUniform1i(gLDrawer2D.glGetUniformLocation("sTexture"), 0);
            int glGetUniformLocation = gLDrawer2D.glGetUniformLocation("sTexture2");
            this.mOverlayTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33985, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mOverlayTexId);
            this.mOverlayTexture = surfaceTexture;
            surfaceTexture.setDefaultBufferSize(width(), height());
            this.mOverlaySurface = new Surface(this.mOverlayTexture);
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mOverlayTexId);
            GLES30.glUniform1i(glGetUniformLocation, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void internalOnStop() {
            SurfaceTexture surfaceTexture = this.mOverlayTexture;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.mOverlayTexture = null;
            }
            this.mOverlaySurface = null;
            int i = this.mOverlayTexId;
            if (i >= 0) {
                GLUtils.deleteTex(i);
                this.mOverlayTexId = -1;
            }
            super.internalOnStop();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public Object handleRequest(int i, int i2, int i3, Object obj) {
            if (i == 100) {
                handleUpdateOverlay(i2, (Bitmap) obj);
                return null;
            }
            return super.handleRequest(i, i2, i3, obj);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractRendererHolder.BaseRendererTask, com.serenegiant.glutils.AbstractDistributeTask
        public void handleUpdateTexture() {
            super.handleUpdateTexture();
            this.mOverlayTexture.updateTexImage();
            this.mOverlayTexture.getTransformMatrix(this.mTexMatrixOverlay);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractRendererHolder.BaseRendererTask, com.serenegiant.glutils.AbstractDistributeTask
        public void handleResize(int i, int i2) {
            super.handleResize(i, i2);
            SurfaceTexture surfaceTexture = this.mOverlayTexture;
            if (surfaceTexture != null) {
                surfaceTexture.setDefaultBufferSize(width(), height());
            }
        }

        private void handleUpdateOverlay(int i, Bitmap bitmap) {
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mOverlayTexId);
            try {
                Canvas lockCanvas = this.mOverlaySurface.lockCanvas(null);
                if (bitmap != null) {
                    lockCanvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                } else {
                    lockCanvas.drawColor(0);
                }
                this.mOverlaySurface.unlockCanvasAndPost(lockCanvas);
            } catch (Exception e) {
                Log.w(OverlayRendererHolder.TAG, e);
            }
            requestFrame(true, getTexId(), getTexMatrix());
        }
    }
}
