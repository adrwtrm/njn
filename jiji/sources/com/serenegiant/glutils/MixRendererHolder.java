package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import androidx.core.view.ViewCompat;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.ShaderConst;
import com.serenegiant.glutils.AbstractRendererHolder;
import com.serenegiant.glutils.IRendererHolder;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;

/* loaded from: classes2.dex */
public class MixRendererHolder extends AbstractRendererHolder {
    private static final boolean DEBUG = false;
    private static final int REQUEST_SET_MASK = 10;
    private static final String TAG = "MixRendererHolder";
    private static final String FRAGMENT_SHADER_BASE_ES2 = "#version 100\n%sprecision highp float;\nvarying       vec2 vTextureCoord;\nuniform %s    sTexture;\nuniform %s    sTexture2;\nuniform %s    sTexture3;\nvoid main() {\n    highp vec4 tex1 = texture2D(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture2D(sTexture2, vTextureCoord);\n    highp float alpha = texture2D(sTexture3, vTextureCoord).a;\n    gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a * alpha), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_EXT_ES2 = String.format(FRAGMENT_SHADER_BASE_ES2, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);
    private static final String FRAGMENT_SHADER_BASE_ES3 = "#version 300 es\n%sprecision highp float;\nin vec2 vTextureCoord;\nuniform %s sTexture;\nuniform %s sTexture2;\nuniform %s sTexture3;\nlayout(location = 0) out vec4 o_FragColor;\nvoid main() {\n    highp vec4 tex1 = texture(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture(sTexture2, vTextureCoord);\n    highp float alpha = texture(sTexture3, vTextureCoord).a;\n    o_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a * alpha), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_EXT_ES3 = String.format(FRAGMENT_SHADER_BASE_ES3, ShaderConst.HEADER_OES_ES3, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_OES);

    public MixRendererHolder(int i, int i2, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        this(i, i2, 3, null, 2, renderHolderCallback);
    }

    public MixRendererHolder(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        super(i, i2, i3, iContext, i4, renderHolderCallback);
    }

    public Surface getSurface2() {
        return ((MixRendererTask) this.mRendererTask).getSurface2();
    }

    public SurfaceTexture getSurfaceTexture2() {
        return ((MixRendererTask) this.mRendererTask).getSurfaceTexture2();
    }

    public void setMask(Bitmap bitmap) {
        ((MixRendererTask) this.mRendererTask).setMask(bitmap);
    }

    @Override // com.serenegiant.glutils.AbstractRendererHolder
    protected AbstractRendererHolder.BaseRendererTask createRendererTask(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4) {
        return new MixRendererTask(this, i, i2, i3, iContext, i4, null);
    }

    /* loaded from: classes2.dex */
    private final class MixRendererTask extends AbstractRendererHolder.BaseRendererTask {
        private int cnt;
        private Handler mAsyncHandler;
        private Surface mMaskSurface;
        private int mMaskTexId;
        private final float[] mMaskTexMatrix;
        private SurfaceTexture mMaskTexture;
        private Surface mMasterSurface2;
        private SurfaceTexture mMasterTexture2;
        private final SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener;
        private int mTexId2;
        private final float[] mTexMatrix2;

        public MixRendererTask(AbstractRendererHolder abstractRendererHolder, int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, GLDrawer2D.DrawerFactory drawerFactory) {
            super(abstractRendererHolder, i, i2, i3, iContext, i4, drawerFactory);
            this.mTexMatrix2 = new float[16];
            this.mMaskTexMatrix = new float[16];
            this.mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.serenegiant.glutils.MixRendererHolder.MixRendererTask.1
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    MixRendererTask mixRendererTask = MixRendererTask.this;
                    mixRendererTask.requestFrame(true, mixRendererTask.getTexId(), MixRendererTask.this.getTexMatrix());
                }
            };
            if (BuildCheck.isAndroid5()) {
                this.mAsyncHandler = HandlerThreadHandler.createHandler("OnFrameAvailable");
            }
        }

        public Surface getSurface2() {
            checkMasterSurface();
            return this.mMasterSurface2;
        }

        public SurfaceTexture getSurfaceTexture2() {
            checkMasterSurface();
            return this.mMasterTexture2;
        }

        public void setMask(Bitmap bitmap) {
            checkFinished();
            offer(10, 0, 0, bitmap);
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
            gLDrawer2D.updateShader(MixRendererHolder.MY_FRAGMENT_SHADER_EXT_ES2);
            GLES20.glUniform1i(gLDrawer2D.glGetUniformLocation("sTexture"), 0);
            int glGetUniformLocation = gLDrawer2D.glGetUniformLocation("sTexture2");
            this.mTexId2 = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33985, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTexId2);
            this.mMasterTexture2 = surfaceTexture;
            surfaceTexture.setDefaultBufferSize(width(), height());
            this.mMasterSurface2 = new Surface(this.mMasterTexture2);
            if (BuildCheck.isAndroid5()) {
                this.mMasterTexture2.setOnFrameAvailableListener(this.mOnFrameAvailableListener, this.mAsyncHandler);
            } else {
                this.mMasterTexture2.setOnFrameAvailableListener(this.mOnFrameAvailableListener);
            }
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mTexId2);
            GLES20.glUniform1i(glGetUniformLocation, 1);
            int glGetUniformLocation2 = gLDrawer2D.glGetUniformLocation("sTexture3");
            this.mMaskTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33986, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture2 = new SurfaceTexture(this.mMaskTexId);
            this.mMaskTexture = surfaceTexture2;
            surfaceTexture2.setDefaultBufferSize(width(), height());
            this.mMaskSurface = new Surface(this.mMaskTexture);
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mMaskTexId);
            GLES20.glUniform1i(glGetUniformLocation2, 2);
        }

        private void internalOnStartES3(GLDrawer2D gLDrawer2D) {
            gLDrawer2D.updateShader(MixRendererHolder.MY_FRAGMENT_SHADER_EXT_ES3);
            GLES30.glUniform1i(gLDrawer2D.glGetUniformLocation("sTexture"), 0);
            int glGetUniformLocation = gLDrawer2D.glGetUniformLocation("sTexture2");
            this.mTexId2 = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33985, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTexId2);
            this.mMasterTexture2 = surfaceTexture;
            surfaceTexture.setDefaultBufferSize(width(), height());
            this.mMasterSurface2 = new Surface(this.mMasterTexture2);
            if (BuildCheck.isAndroid5()) {
                this.mMasterTexture2.setOnFrameAvailableListener(this.mOnFrameAvailableListener, this.mAsyncHandler);
            } else {
                this.mMasterTexture2.setOnFrameAvailableListener(this.mOnFrameAvailableListener);
            }
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mTexId2);
            GLES30.glUniform1i(glGetUniformLocation, 1);
            int glGetUniformLocation2 = gLDrawer2D.glGetUniformLocation("sTexture3");
            this.mMaskTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33986, 9729, 9729, 33071);
            SurfaceTexture surfaceTexture2 = new SurfaceTexture(this.mMaskTexId);
            this.mMaskTexture = surfaceTexture2;
            surfaceTexture2.setDefaultBufferSize(width(), height());
            this.mMaskSurface = new Surface(this.mMaskTexture);
            GLES30.glActiveTexture(33986);
            GLES30.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mMaskTexId);
            GLES30.glUniform1i(glGetUniformLocation2, 2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public void internalOnStop() {
            synchronized (MixRendererHolder.this) {
                Handler handler = this.mAsyncHandler;
                if (handler != null) {
                    try {
                        handler.removeCallbacksAndMessages(null);
                    } catch (Exception e) {
                        Log.w(MixRendererHolder.TAG, e);
                    }
                }
            }
            SurfaceTexture surfaceTexture = this.mMasterTexture2;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.mMasterTexture2 = null;
            }
            this.mMasterSurface2 = null;
            int i = this.mTexId2;
            if (i >= 0) {
                GLUtils.deleteTex(i);
                this.mTexId2 = -1;
            }
            SurfaceTexture surfaceTexture2 = this.mMaskTexture;
            if (surfaceTexture2 != null) {
                surfaceTexture2.release();
                this.mMaskTexture = null;
            }
            this.mMaskSurface = null;
            int i2 = this.mMaskTexId;
            if (i2 >= 0) {
                GLUtils.deleteTex(i2);
                this.mMaskTexId = -1;
            }
            Handler handler2 = this.mAsyncHandler;
            if (handler2 != null) {
                HandlerUtils.NoThrowQuit(handler2);
                this.mAsyncHandler = null;
            }
            super.internalOnStop();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractRendererHolder.BaseRendererTask, com.serenegiant.glutils.AbstractDistributeTask
        public void handleUpdateTexture() {
            super.handleUpdateTexture();
            this.mMasterTexture2.updateTexImage();
            this.mMasterTexture2.getTransformMatrix(this.mTexMatrix2);
            this.mMaskTexture.updateTexImage();
            this.mMaskTexture.getTransformMatrix(this.mMaskTexMatrix);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractRendererHolder.BaseRendererTask, com.serenegiant.glutils.AbstractDistributeTask
        public void handleResize(int i, int i2) {
            super.handleResize(i, i2);
            SurfaceTexture surfaceTexture = this.mMasterTexture2;
            if (surfaceTexture != null) {
                surfaceTexture.setDefaultBufferSize(width(), height());
            }
            SurfaceTexture surfaceTexture2 = this.mMaskTexture;
            if (surfaceTexture2 != null) {
                surfaceTexture2.setDefaultBufferSize(width(), height());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public Object handleRequest(int i, int i2, int i3, Object obj) {
            if (i == 10) {
                handleSetMask((Bitmap) obj);
                return null;
            }
            return super.handleRequest(i, i2, i3, obj);
        }

        protected void handleSetMask(Bitmap bitmap) {
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(GLConst.GL_TEXTURE_EXTERNAL_OES, this.mMaskTexId);
            try {
                Canvas lockCanvas = this.mMaskSurface.lockCanvas(null);
                if (bitmap != null) {
                    lockCanvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                } else {
                    lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                }
                this.mMaskSurface.unlockCanvasAndPost(lockCanvas);
            } catch (Exception e) {
                Log.w(MixRendererHolder.TAG, e);
            }
            requestFrame(true, getTexId(), getTexMatrix());
        }
    }
}
