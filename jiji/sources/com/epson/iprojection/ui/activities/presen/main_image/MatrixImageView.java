package com.epson.iprojection.ui.activities.presen.main_image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IThumbThreadManageable;
import com.epson.iprojection.ui.activities.presen.main_image.gesture.FlickEventCtrlr;
import com.epson.iprojection.ui.activities.presen.main_image.gesture.IOnFlickListener;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class MatrixImageView extends View implements View.OnTouchListener {
    private static final String CACHE_FILE_NAME_EXTEND = "ExtendCache";
    private static final String CACHE_FILE_NAME_ORIGIN = "OriginCache";
    private Bitmap _bmpEXT;
    private Bitmap _bmpORG;
    private final CacheFile _cacheFileExtend;
    private final CacheFile _cacheFileOrigin;
    private BitmapDrawable _drawEXT;
    private BitmapDrawable _drawORG;
    private Rect _drawableRect;
    private IFiler _filer;
    private FlickEventCtrlr _flickEventCtrlr;
    private HandlerThread _hThread;
    private ILoadingStateListener _implLoadingStateGettable;
    private IThumbThreadManageable _implThumbThreadManageable;
    private boolean _isAborted;
    private boolean _isActivated;
    private boolean _isRenderingExtendImage;
    private boolean _isTemp;
    private boolean _isTouching;
    private int _lastPageN;
    private final ImageMover _mover;
    private int _pageN;
    private final SynchronousCounter0AndUp queueCounter;
    private Handler uiHandler;

    public MatrixImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._bmpORG = null;
        this._bmpEXT = null;
        this._drawORG = null;
        this._drawEXT = null;
        this._drawableRect = null;
        this._flickEventCtrlr = null;
        this._isTemp = false;
        this.queueCounter = new SynchronousCounter0AndUp();
        this._filer = null;
        this._pageN = 0;
        this._lastPageN = 0;
        this._isTouching = false;
        this._isAborted = false;
        this._isRenderingExtendImage = false;
        this._isActivated = true;
        setOnTouchListener(this);
        this._mover = new ImageMover(this);
        createExtendThread();
        int taskId = ((Activity) context).getTaskId();
        this._cacheFileOrigin = new CacheFile(taskId, CACHE_FILE_NAME_ORIGIN);
        this._cacheFileExtend = new CacheFile(taskId, CACHE_FILE_NAME_EXTEND);
    }

    public void start() throws BitmapMemoryException {
        createExtendThread();
        load();
        invalidate();
    }

    public void stop() {
        destroyExtendThread();
        save();
        renderCleanupUIThread();
        this._isTouching = false;
    }

    public void activate() {
        this._isActivated = true;
    }

    public void disactivate() {
        this._isActivated = false;
    }

    public void initialize() {
        Bitmap bitmap = this._bmpORG;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this._bmpORG = null;
        Bitmap bitmap2 = this._bmpEXT;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this._bmpEXT = null;
    }

    public synchronized void Abort() {
        this._isAborted = true;
        destroyExtendThread();
        Bitmap bitmap = this._bmpEXT;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this._bmpEXT = null;
    }

    private void createExtendThread() {
        this.uiHandler = new Handler();
        if (this._hThread == null) {
            HandlerThread handlerThread = new HandlerThread("PageRenderer");
            this._hThread = handlerThread;
            handlerThread.setPriority(10);
            this._hThread.start();
        }
    }

    private void destroyExtendThread() {
        HandlerThread handlerThread = this._hThread;
        if (handlerThread != null) {
            if (handlerThread.isAlive()) {
                this._hThread.getLooper().quit();
                if (this._isRenderingExtendImage && this._bmpEXT != null) {
                    Lg.i("拡大画像取得中にスレッドが殺されました");
                    this._bmpEXT.recycle();
                    this._bmpEXT = null;
                }
            }
            this._hThread = null;
        }
    }

    public void setPageN(int i) {
        this._pageN = i;
    }

    public void setFlickListener(IOnFlickListener iOnFlickListener) {
        this._flickEventCtrlr = new FlickEventCtrlr(iOnFlickListener, getContext());
    }

    public void setFiler(IFiler iFiler) {
        this._filer = iFiler;
        try {
            this._pageN = iFiler.getCurrentPage();
        } catch (UnavailableException unused) {
            this._pageN = 0;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        setMeasuredDimension(size, size2);
        this._mover.setViewSize(size, size2);
        Bitmap bitmap = this._bmpORG;
        if (bitmap == null || bitmap.isRecycled() || !this._mover.isSizeChanged() || this._mover.getZoomRatio() >= 1.01f) {
            return;
        }
        resizeViewImage(size, size2);
    }

    private void resizeViewImage(int i, int i2) {
        ResRect resRect = new ResRect(this._bmpORG.getWidth(), this._bmpORG.getHeight());
        FitResolution.get(resRect, new ResRect(i, i2));
        if (this._bmpORG.isRecycled()) {
            Lg.e("既にリサイクルされています！");
            return;
        }
        this._mover.initialize(resRect);
        this._drawableRect = new Rect(resRect.x, resRect.y, resRect.x + resRect.w, resRect.y + resRect.h);
    }

    public void setBitmap(Bitmap bitmap, boolean z) throws BitmapMemoryException {
        setBitmap(bitmap, z, false);
    }

    public void setBitmap(Bitmap bitmap, boolean z, boolean z2) throws BitmapMemoryException {
        Bitmap bitmap2 = this._bmpORG;
        if (bitmap2 != null && bitmap2 != bitmap) {
            bitmap2.recycle();
        }
        if (bitmap == null) {
            Lg.w("bm is null");
            return;
        }
        this._bmpORG = bitmap;
        this._bmpEXT = null;
        this._isTemp = z;
        this._mover.setShadowImageSize(bitmap.getWidth(), this._bmpORG.getHeight());
        if (!z2) {
            resizeViewImage(getWidth(), getHeight());
        }
        setDrawableBitmap();
        invalidate();
        if (z || !this._isActivated) {
            return;
        }
        sendImage();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDraw(android.graphics.Canvas r6) {
        /*
            r5 = this;
            android.graphics.Bitmap r0 = r5._bmpORG
            if (r0 == 0) goto L94
            boolean r0 = r0.isRecycled()
            if (r0 != 0) goto L94
            int r0 = r6.getSaveCount()
            r6.save()
            android.graphics.Rect r1 = r5._drawableRect
            if (r1 == 0) goto L26
            int r1 = r1.left
            android.graphics.Rect r2 = r5._drawableRect
            int r2 = r2.top
            android.graphics.Rect r3 = r5._drawableRect
            int r3 = r3.right
            android.graphics.Rect r4 = r5._drawableRect
            int r4 = r4.bottom
            r6.clipRect(r1, r2, r3, r4)
        L26:
            android.graphics.Bitmap r1 = r5._bmpEXT     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            if (r1 == 0) goto L42
            com.epson.iprojection.ui.activities.presen.interfaces.IFiler r1 = r5._filer     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            boolean r1 = r1.isRendering()     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            if (r1 == 0) goto L3a
            com.epson.iprojection.ui.activities.presen.interfaces.IFiler r1 = r5._filer     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            boolean r1 = r1.isThumbRendering()     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            if (r1 == 0) goto L42
        L3a:
            boolean r1 = r5._isTouching     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            if (r1 == 0) goto L3f
            goto L42
        L3f:
            android.graphics.drawable.BitmapDrawable r1 = r5._drawEXT     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            goto L4a
        L42:
            android.graphics.drawable.BitmapDrawable r1 = r5._drawORG     // Catch: com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException -> L45
            goto L4a
        L45:
            r1 = move-exception
            r1.printStackTrace()
            r1 = 0
        L4a:
            android.graphics.drawable.BitmapDrawable r2 = r5._drawORG
            if (r1 != r2) goto L6c
            android.graphics.Bitmap r2 = r1.getBitmap()
            int r2 = r2.getWidth()
            android.graphics.Bitmap r3 = r1.getBitmap()
            int r3 = r3.getHeight()
            r4 = 0
            r1.setBounds(r4, r4, r2, r3)
            com.epson.iprojection.ui.activities.presen.main_image.ImageMover r2 = r5._mover
            android.graphics.Matrix r2 = r2.getViewImageMatrix()
            r6.concat(r2)
            goto L71
        L6c:
            android.graphics.Rect r2 = r5._drawableRect
            r1.setBounds(r2)
        L71:
            android.graphics.Bitmap r2 = r5._bmpEXT
            if (r2 == 0) goto L7f
            boolean r2 = r2.isRecycled()
            if (r2 != 0) goto L82
            r1.draw(r6)
            goto L82
        L7f:
            r1.draw(r6)
        L82:
            r6.restoreToCount(r0)
            com.epson.iprojection.common.utils.FrameForUltraWideUtils$Companion r0 = com.epson.iprojection.common.utils.FrameForUltraWideUtils.Companion
            android.content.Context r1 = r5.getContext()
            boolean r0 = r0.shouldDrawFrame(r1)
            if (r0 == 0) goto L94
            r5.drawFrame(r6)
        L94:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.presen.main_image.MatrixImageView.onDraw(android.graphics.Canvas):void");
    }

    public Bitmap getSetImageBitmap() {
        return this._bmpORG;
    }

    public boolean isTemp() {
        return this._isTemp;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this._implLoadingStateGettable.isNowLoading()) {
            return true;
        }
        try {
            boolean onTouch = this._mover.onTouch(view, motionEvent);
            if (!this._filer.isRendering() && this.queueCounter.get() == 0) {
                this._bmpEXT = null;
            }
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this._isTouching = true;
            } else if (action == 1 || action == 3) {
                this._isTouching = false;
                if (!this._filer.isScalable()) {
                    this.queueCounter.add();
                    this._lastPageN = this._pageN;
                    displayExtendImageUIThread();
                } else {
                    getExtendImageThread();
                }
                onTouch = true;
            }
            this._flickEventCtrlr.onTouch(onTouch, motionEvent, this._mover.getEdgeInfo());
            return true;
        } catch (UnavailableException unused) {
            this._bmpEXT = null;
            return false;
        }
    }

    private void drawFrame(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(DipUtils.dp2px(getContext(), 2));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(getContext().getColor(R.color.DrawerHighLightBackground));
        ResRect resRect = ContentRectHolder.INSTANCE.get();
        FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, this._drawableRect.width(), this._drawableRect.height()));
        canvas.drawRect(new Rect(resRect.x, resRect.y, resRect.x + resRect.w, resRect.y + resRect.h), paint);
    }

    private void getExtendImageThread() {
        this.queueCounter.add();
        int i = this._lastPageN;
        int i2 = this._pageN;
        if (i != i2) {
            this._lastPageN = i2;
        }
        if (this._hThread == null) {
            this.queueCounter.subtract();
            return;
        }
        this._implLoadingStateGettable.onRenderingStart();
        new Handler(this._hThread.getLooper()).post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.main_image.MatrixImageView$$ExternalSyntheticLambda2
            {
                MatrixImageView.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MatrixImageView.this.m160xc6ecafc7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$getExtendImageThread$0$com-epson-iprojection-ui-activities-presen-main_image-MatrixImageView  reason: not valid java name */
    public /* synthetic */ void m160xc6ecafc7() {
        if (3 <= this.queueCounter.get() || this._isTouching) {
            this.queueCounter.subtract();
            return;
        }
        try {
            this._isRenderingExtendImage = true;
            this._implThumbThreadManageable.interruptThumbLoadThread();
            D_ExtendInfo extendInfo = this._mover.getExtendInfo(this._filer.getRealW(this._pageN), this._filer.getRealH(this._pageN), 1.0f);
            if (extendInfo == null) {
                this._implThumbThreadManageable.releaseInterruptThumbLoadThread();
                throw new UnavailableException(null);
            }
            Bitmap extendImage = this._filer.getExtendImage(this._pageN, extendInfo.srcClip.left, extendInfo.srcClip.top, extendInfo.srcClip.width(), extendInfo.srcClip.height(), extendInfo.scale, 1);
            if (extendImage == null) {
                this._implThumbThreadManageable.releaseInterruptThumbLoadThread();
                throw new UnavailableException(null);
            }
            if (this._bmpORG != null && !this._isTouching) {
                this._implThumbThreadManageable.releaseInterruptThumbLoadThread();
                if (this._lastPageN != this._pageN) {
                    this.queueCounter.subtract();
                    if (extendImage != null) {
                        extendImage.recycle();
                    }
                    this._isRenderingExtendImage = false;
                    return;
                }
                if (extendImage != null && this.queueCounter.get() == 1) {
                    this._bmpEXT = BitmapUtils.createBitmapFitWithIn(extendImage, extendInfo.dstClip, this._bmpORG.getWidth(), this._bmpORG.getHeight(), Pj.getIns().getShadowResWidth(), Pj.getIns().getShadowResHeight());
                }
                if (extendImage != null) {
                    extendImage.recycle();
                }
                displayExtendImageUIThread();
                this._isRenderingExtendImage = false;
                return;
            }
            this._implThumbThreadManageable.releaseInterruptThumbLoadThread();
            if (extendImage != null) {
                extendImage.recycle();
            }
            throw new UnavailableException(null);
        } catch (UnavailableException unused) {
            Lg.i("UnavailableException QueueCount :" + this.queueCounter.get());
            this.queueCounter.subtract();
            this._bmpEXT = null;
            try {
                renderCleanupUIThread();
                sendImage();
            } catch (BitmapMemoryException unused2) {
                ActivityGetter.getIns().killMyProcess();
            }
        } catch (BitmapMemoryException unused3) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    private void renderCleanupUIThread() {
        if (this.queueCounter.get() != 0) {
            return;
        }
        this.uiHandler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.main_image.MatrixImageView$$ExternalSyntheticLambda0
            {
                MatrixImageView.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MatrixImageView.this.m161x699ebd05();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$renderCleanupUIThread$1$com-epson-iprojection-ui-activities-presen-main_image-MatrixImageView  reason: not valid java name */
    public /* synthetic */ void m161x699ebd05() {
        if (this._isAborted || this._isTouching) {
            return;
        }
        this._implLoadingStateGettable.onRenderingEnd();
    }

    private void displayExtendImageUIThread() {
        this.uiHandler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.main_image.MatrixImageView$$ExternalSyntheticLambda1
            {
                MatrixImageView.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MatrixImageView.this.m159x93027071();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$displayExtendImageUIThread$2$com-epson-iprojection-ui-activities-presen-main_image-MatrixImageView  reason: not valid java name */
    public /* synthetic */ void m159x93027071() {
        try {
            this.queueCounter.subtract();
            if (this._lastPageN == this._pageN && this.queueCounter.get() == 0 && !this._isAborted) {
                setDrawableBitmap();
                this._implLoadingStateGettable.onRenderingEnd();
                invalidate();
                sendImage();
            }
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    public Bitmap getShadowBitmapBiCubic(boolean z) throws BitmapMemoryException {
        int shadowResWidth;
        int shadowResHeight;
        Bitmap createBitmapFitWithIn;
        Bitmap bitmap = this._bmpORG;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        if (z) {
            shadowResWidth = Pj.getIns().getPjResWidth();
            shadowResHeight = Pj.getIns().getPjResHeight();
        } else {
            shadowResWidth = Pj.getIns().getShadowResWidth();
            shadowResHeight = Pj.getIns().getShadowResHeight();
        }
        Bitmap bitmap2 = this._bmpEXT;
        if (bitmap2 == null || (createBitmapFitWithIn = BitmapUtils.createBitmapFitWithIn(bitmap2, shadowResWidth, shadowResHeight)) == null) {
            float width = shadowResWidth / this._bmpORG.getWidth();
            Matrix matrix = new Matrix(this._mover.getShadowImageMatrix());
            Bitmap createBitmap = BitmapUtils.createBitmap(shadowResWidth, shadowResHeight, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), this._bmpORG);
            bitmapDrawable.setBounds(0, 0, bitmapDrawable.getBitmap().getWidth(), bitmapDrawable.getBitmap().getHeight());
            matrix.postScale(width, width);
            canvas.concat(matrix);
            bitmapDrawable.draw(canvas);
            return createBitmap;
        }
        return createBitmapFitWithIn;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void sendImage() throws BitmapMemoryException {
        if (this.queueCounter.get() == 0 && ((!this._isTouching || this._flickEventCtrlr.isFlicked()) && !this._isAborted)) {
            if (Pj.getIns().isConnected() && !this._isTemp) {
                Lg.d("sendImage");
                Bitmap shadowBitmapBiCubic = getShadowBitmapBiCubic(true);
                if (shadowBitmapBiCubic == null) {
                    return;
                }
                Pj.getIns().sendImage(shadowBitmapBiCubic, null);
            }
        }
    }

    private void setDrawableBitmap() {
        this._drawORG = new BitmapDrawable(getResources(), this._bmpORG);
        this._drawEXT = new BitmapDrawable(getResources(), this._bmpEXT);
    }

    public void setLoadingStateListener(ILoadingStateListener iLoadingStateListener) {
        this._implLoadingStateGettable = iLoadingStateListener;
    }

    public void setThumbThreadManageable(IThumbThreadManageable iThumbThreadManageable) {
        this._implThumbThreadManageable = iThumbThreadManageable;
    }

    public void save() {
        Bitmap bitmap = this._bmpORG;
        if (bitmap != null && !this._isTemp) {
            this._cacheFileOrigin.save(bitmap);
        } else {
            this._cacheFileOrigin.delete();
        }
        Bitmap bitmap2 = this._bmpEXT;
        if (bitmap2 != null) {
            this._cacheFileExtend.save(bitmap2);
        } else {
            this._cacheFileExtend.delete();
        }
    }

    public boolean load() throws BitmapMemoryException {
        this._bmpORG = this._cacheFileOrigin.load();
        this._bmpEXT = this._cacheFileExtend.load();
        setDrawableBitmap();
        if (this._bmpORG == null) {
            return false;
        }
        if (this._bmpEXT == null && !this._mover.isExcludedExtendRatio() && this._filer.isScalable()) {
            getExtendImageThread();
            return true;
        }
        this.queueCounter.reset();
        return true;
    }
}
