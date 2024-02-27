package com.serenegiant.glpipeline;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLSurface;
import com.serenegiant.gl.GLTexture;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.gl.ShaderConst;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class MaskPipeline extends ProxyPipeline implements GLSurfacePipeline {
    private static final boolean DEBUG = true;
    private static final String TAG = "MaskPipeline";
    private int cnt;
    private GLDrawer2D mDrawer;
    private final GLManager mManager;
    private Bitmap mMaskBitmap;
    private volatile boolean mMaskOnly;
    private GLTexture mMaskTexture;
    private RendererTarget mRendererTarget;
    private volatile boolean mRequestUpdateMask;
    private final Object mSync;
    private GLSurface work;
    private static final String FRAGMENT_SHADER_BASE_ES2 = "#version 100\n%sprecision highp float;\nvarying vec2 vTextureCoord;\nuniform %s sTexture;\nuniform %s sTexture2;\nvoid main() {\n    highp vec4 tex1 = texture2D(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture2D(sTexture2, vTextureCoord);\n    gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_ES2 = String.format(FRAGMENT_SHADER_BASE_ES2, "", ShaderConst.SAMPLER_2D, ShaderConst.SAMPLER_2D);
    private static final String MY_FRAGMENT_SHADER_EXT_ES2 = String.format(FRAGMENT_SHADER_BASE_ES2, ShaderConst.HEADER_OES_ES2, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_2D);
    private static final String FRAGMENT_SHADER_BASE_ES3 = "#version 300 es\n%sprecision highp float;\nin vec2 vTextureCoord;\nuniform %s sTexture;\nuniform %s sTexture2;\nlayout(location = 0) out vec4 o_FragColor;\nvoid main() {\n    highp vec4 tex1 = texture(sTexture, vTextureCoord);\n    highp vec4 tex2 = texture(sTexture2, vTextureCoord);\n    o_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a), tex1.a);\n}\n";
    private static final String MY_FRAGMENT_SHADER_ES3 = String.format(FRAGMENT_SHADER_BASE_ES3, "", ShaderConst.SAMPLER_2D, ShaderConst.SAMPLER_2D);
    private static final String MY_FRAGMENT_SHADER_EXT_ES3 = String.format(FRAGMENT_SHADER_BASE_ES3, ShaderConst.HEADER_OES_ES3, ShaderConst.SAMPLER_OES, ShaderConst.SAMPLER_2D);

    public MaskPipeline(GLManager gLManager) throws IllegalStateException, IllegalArgumentException {
        this(gLManager, null, null);
    }

    public MaskPipeline(final GLManager gLManager, final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        this.mSync = new Object();
        Log.v(TAG, "コンストラクタ:");
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager = gLManager;
        gLManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.MaskPipeline.1
            @Override // java.lang.Runnable
            public void run() {
                gLManager.isGLES3();
                MaskPipeline.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        Log.v(TAG, "internalRelease:");
        if (isValid()) {
            synchronized (this.mSync) {
                this.mMaskBitmap = null;
            }
            releaseAll();
        }
        super.internalRelease();
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public void setSurface(Object obj) throws IllegalStateException, IllegalArgumentException {
        setSurface(obj, null);
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public void setSurface(final Object obj, final Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        Log.v(TAG, "setSurface:" + obj);
        if (!isValid()) {
            throw new IllegalStateException("already released?");
        }
        if (obj != null && !GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Unsupported surface type!," + obj);
        }
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.MaskPipeline.2
            @Override // java.lang.Runnable
            public void run() {
                MaskPipeline.this.createTargetOnGL(obj, fraction);
            }
        });
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public boolean hasSurface() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mRendererTarget != null;
        }
        return z;
    }

    @Override // com.serenegiant.glpipeline.GLSurfacePipeline
    public int getId() {
        int id;
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            id = rendererTarget != null ? rendererTarget.getId() : 0;
        }
        return id;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return super.isValid() && this.mManager.isValid();
    }

    public boolean isMaskOnly() {
        return this.mMaskOnly;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        RendererTarget rendererTarget;
        Bitmap bitmap;
        GLDrawer2D gLDrawer2D = this.mDrawer;
        if (gLDrawer2D == null || z != gLDrawer2D.isOES()) {
            releaseDrawerOnGL();
            Log.v(TAG, "onFrameAvailable:create GLDrawer2D");
            this.mDrawer = createDrawerOnGL(this.mManager.isGLES3(), z);
        }
        GLDrawer2D gLDrawer2D2 = this.mDrawer;
        synchronized (this.mSync) {
            rendererTarget = this.mRendererTarget;
            bitmap = this.mMaskBitmap;
        }
        if (this.mRequestUpdateMask) {
            this.mRequestUpdateMask = false;
            if (bitmap != null) {
                createMaskTextureOnGL(bitmap);
            } else {
                releaseMaskOnGL();
            }
        }
        if (rendererTarget != null && rendererTarget.canDraw()) {
            GLTexture gLTexture = this.mMaskTexture;
            if (gLTexture != null) {
                gLTexture.bindTexture();
            }
            rendererTarget.draw(gLDrawer2D2, 33984, i, fArr);
        }
        if (this.mMaskOnly && this.work != null) {
            int i2 = this.cnt + 1;
            this.cnt = i2;
            if (i2 % 100 == 0) {
                Log.v(TAG, "onFrameAvailable:effectOnly," + this.cnt);
            }
            super.onFrameAvailable(this.work.isOES(), this.work.getTexId(), this.work.getTexMatrix());
            return;
        }
        int i3 = this.cnt + 1;
        this.cnt = i3;
        if (i3 % 100 == 0) {
            Log.v(TAG, "onFrameAvailable:" + this.cnt);
        }
        super.onFrameAvailable(z, i, fArr);
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        super.refresh();
        Log.v(TAG, "refresh:");
        if (isValid()) {
            this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.MaskPipeline.3
                @Override // java.lang.Runnable
                public void run() {
                    Log.v(MaskPipeline.TAG, "refresh#run:release drawer");
                    MaskPipeline.this.releaseDrawerOnGL();
                }
            });
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void resize(int i, int i2) throws IllegalStateException {
        super.resize(i, i2);
        Log.v(TAG, String.format("resize:(%dx%d)", Integer.valueOf(i), Integer.valueOf(i2)));
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.MaskPipeline.4
            @Override // java.lang.Runnable
            public void run() {
                Log.v(MaskPipeline.TAG, "resize#run:");
                MaskPipeline.this.releaseMaskOnGL();
            }
        });
    }

    public void setMask(Bitmap bitmap) {
        Log.v(TAG, "setMask:");
        synchronized (this.mSync) {
            this.mMaskBitmap = bitmap;
            this.mRequestUpdateMask = true;
        }
    }

    private void releaseAll() {
        String str = TAG;
        Log.v(str, "releaseAll:");
        if (this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.MaskPipeline.5
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.v(MaskPipeline.TAG, "releaseAll#run:");
                        synchronized (MaskPipeline.this.mSync) {
                            if (MaskPipeline.this.mRendererTarget != null) {
                                Log.v(MaskPipeline.TAG, "releaseAll:release target");
                                MaskPipeline.this.mRendererTarget.release();
                                MaskPipeline.this.mRendererTarget = null;
                            }
                            if (MaskPipeline.this.work != null) {
                                Log.v(MaskPipeline.TAG, "releaseAll:release work");
                                MaskPipeline.this.work.release();
                                MaskPipeline.this.work = null;
                            }
                        }
                        MaskPipeline.this.releaseDrawerOnGL();
                    }
                });
                return;
            } catch (Exception e) {
                Log.w(TAG, e);
                return;
            }
        }
        Log.w(str, "releaseAll:unexpectedly GLManager is already released!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTargetOnGL(Object obj, Fraction fraction) {
        String str = TAG;
        Log.v(str, "createTarget:" + obj);
        synchronized (this.mSync) {
            RendererTarget rendererTarget = this.mRendererTarget;
            if (rendererTarget == null || rendererTarget.getSurface() != obj) {
                RendererTarget rendererTarget2 = this.mRendererTarget;
                if (rendererTarget2 != null) {
                    rendererTarget2.release();
                    this.mRendererTarget = null;
                }
                GLSurface gLSurface = this.work;
                if (gLSurface != null) {
                    gLSurface.release();
                    this.work = null;
                }
                if (GLUtils.isSupportedSurface(obj)) {
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), obj, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mMaskOnly = false;
                } else if (isValid()) {
                    Log.v(str, "createTarget:create GLSurface as work texture");
                    this.work = GLSurface.newInstance(this.mManager.isGLES3(), getWidth(), getHeight());
                    this.mRendererTarget = RendererTarget.newInstance(this.mManager.getEgl(), this.work, fraction != null ? fraction.asFloat() : 0.0f);
                    this.mMaskOnly = true;
                }
                RendererTarget rendererTarget3 = this.mRendererTarget;
                if (rendererTarget3 != null) {
                    rendererTarget3.setMirror(2);
                }
            }
        }
    }

    private static GLDrawer2D createDrawerOnGL(boolean z, boolean z2) {
        String str;
        Log.v(TAG, "createDrawerOnGL:isGLES3=" + z + ",isOES=" + z2);
        if (z2) {
            str = z ? MY_FRAGMENT_SHADER_EXT_ES3 : MY_FRAGMENT_SHADER_EXT_ES2;
        } else {
            str = z ? MY_FRAGMENT_SHADER_ES3 : MY_FRAGMENT_SHADER_ES2;
        }
        return GLDrawer2D.create(z, z2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseDrawerOnGL() {
        Log.v(TAG, "releaseDrawerOnGL:");
        GLDrawer2D gLDrawer2D = this.mDrawer;
        if (gLDrawer2D != null) {
            gLDrawer2D.release();
            this.mDrawer = null;
        }
        releaseMaskOnGL();
    }

    private void createMaskTextureOnGL(Bitmap bitmap) {
        String str = TAG;
        Log.v(str, "createMaskTextureOnGL:");
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        GLTexture gLTexture = this.mMaskTexture;
        if (gLTexture == null || gLTexture.getWidth() != width || this.mMaskTexture.getHeight() != height) {
            releaseMaskOnGL();
            GLTexture newInstance = GLTexture.newInstance(33985, width, height);
            this.mMaskTexture = newInstance;
            newInstance.bindTexture();
            GLES20.glUniform1i(this.mDrawer.glGetUniformLocation("sTexture2"), GLUtils.gLTextureUnit2Index(33985));
        }
        Log.v(str, "createMaskTextureOnGL:loadBitmap");
        this.mMaskTexture.loadBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseMaskOnGL() {
        Log.v(TAG, "releaseMaskOnGL:");
        GLTexture gLTexture = this.mMaskTexture;
        if (gLTexture != null) {
            gLTexture.release();
            this.mMaskTexture = null;
        }
    }
}
