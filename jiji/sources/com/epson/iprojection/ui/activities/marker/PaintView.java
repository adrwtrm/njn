package com.epson.iprojection.ui.activities.marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.common.utils.FrameForUltraWideUtils;
import com.epson.iprojection.ui.activities.marker.UndoRedoMgr;
import com.epson.iprojection.ui.activities.marker.config.IToolConfig;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activitystatus.ContentsSelectStatus;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePenUsageSituationDimension;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class PaintView extends SurfaceView implements SurfaceHolder.Callback {
    private final AlphaLayer _alphaLayer;
    private Bitmap _bitmap;
    private Rect _cameraViewRect;
    private final Context _context;
    private IGettableUpdateUndoRedoButton _implUpdateUndoRedoButton;
    private boolean _isCameraView;
    private boolean _isDrawingNoAddHistory;
    private boolean _isEnabled;
    private boolean _isFullScreen;
    private boolean _isLock;
    private Rect _maskRect;
    private SendEventWatcher _sendEventWatcher;
    private IBaseTool _tool;
    private IToolConfig _toolConfig;
    private Bitmap _touchedUpBmp;
    private final UndoRedoMgr _undoRedoMgr;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public PaintView(Context context) {
        super(context);
        this._bitmap = null;
        this._touchedUpBmp = null;
        this._alphaLayer = new AlphaLayer();
        this._tool = null;
        this._toolConfig = null;
        this._maskRect = null;
        this._cameraViewRect = null;
        this._isFullScreen = false;
        this._isCameraView = false;
        this._sendEventWatcher = null;
        this._isEnabled = true;
        this._isLock = false;
        this._isDrawingNoAddHistory = false;
        this._undoRedoMgr = new UndoRedoMgr();
        getHolder().addCallback(this);
        this._context = context;
    }

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._bitmap = null;
        this._touchedUpBmp = null;
        this._alphaLayer = new AlphaLayer();
        this._tool = null;
        this._toolConfig = null;
        this._maskRect = null;
        this._cameraViewRect = null;
        this._isFullScreen = false;
        this._isCameraView = false;
        this._sendEventWatcher = null;
        this._isEnabled = true;
        this._isLock = false;
        this._isDrawingNoAddHistory = false;
        this._undoRedoMgr = new UndoRedoMgr();
        getHolder().addCallback(this);
        this._context = context;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        doDraw(surfaceHolder);
    }

    public void setFullScreenMode(boolean z) {
        this._isFullScreen = z;
        Lg.d(z ? "full screen" : "not full screen");
    }

    public void setCameraViewMode(boolean z, Rect rect) {
        this._isCameraView = z;
        this._cameraViewRect = rect;
        Lg.d(z ? "camera view" : "not camera view");
    }

    public void setUpdateUndoRedoButtonImpl(IGettableUpdateUndoRedoButton iGettableUpdateUndoRedoButton) {
        this._implUpdateUndoRedoButton = iGettableUpdateUndoRedoButton;
    }

    public void Initialize(IOnSendEvent iOnSendEvent, IToolConfig iToolConfig) throws BitmapMemoryException {
        this._toolConfig = iToolConfig;
        getHolder().setFormat(-3);
        setZOrderOnTop(true);
        this._sendEventWatcher = new SendEventWatcher(iOnSendEvent);
    }

    public void lock(boolean z) {
        this._isLock = z;
        if (z) {
            onActionUp(null);
            this._isEnabled = true;
            this._tool.clear();
        }
    }

    public void destroy() {
        this._undoRedoMgr.clear();
        this._alphaLayer.destroy();
        SendEventWatcher sendEventWatcher = this._sendEventWatcher;
        if (sendEventWatcher != null) {
            sendEventWatcher.destroy();
        }
        this._bitmap = null;
    }

    public void clear() {
        doDrawAllClear();
        doDraw(getHolder());
        addBitmapToHistory();
        updateUndoRedoButton();
    }

    private void doDrawAllClear() {
        Bitmap bitmap = this._bitmap;
        if (bitmap != null) {
            bitmap.eraseColor(Color.argb(0, 0, 0, 0));
        }
        Bitmap bitmap2 = this._touchedUpBmp;
        if (bitmap2 != null) {
            bitmap2.eraseColor(Color.argb(0, 0, 0, 0));
        }
        this._alphaLayer.clear();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        ResRect resRect;
        Lg.d("onSizeChanged()");
        try {
            super.onSizeChanged(i, i2, i3, i4);
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            this._bitmap = createBitmap;
            BitmapUtils.drawBitmapFitWithIn(this._touchedUpBmp, createBitmap);
            this._undoRedoMgr.clear();
            addBitmapToHistory();
            this._alphaLayer.changeSize(i, i2);
            if (this._isCameraView) {
                resRect = new ResRect(0, 0, this._cameraViewRect.width(), this._cameraViewRect.height());
            } else {
                resRect = new ResRect(0, 0, Pj.getIns().getShadowResWidth(), Pj.getIns().getShadowResHeight());
            }
            FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, i, i2));
            this._maskRect = new Rect(resRect.x, resRect.y, resRect.x + resRect.w, resRect.y + resRect.h);
            IToolConfig iToolConfig = this._toolConfig;
            if (iToolConfig != null) {
                setTool(iToolConfig);
            }
            new Handler().postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.marker.PaintView$$ExternalSyntheticLambda0
                {
                    PaintView.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    PaintView.this.m92x4dd9e1df();
                }
            }, 1L);
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    /* renamed from: lambda$onSizeChanged$0$com-epson-iprojection-ui-activities-marker-PaintView */
    public /* synthetic */ void m92x4dd9e1df() {
        doDraw(getHolder());
    }

    public void setTool(IToolConfig iToolConfig) throws BitmapMemoryException {
        this._toolConfig = iToolConfig;
        IBaseTool create = ToolFactory.create(iToolConfig.getKind());
        this._tool = create;
        create.setColor(this._toolConfig.getColor());
        this._tool.setWidth(DipUtils.dp2px(this._context, this._toolConfig.getWidth()));
        this._alphaLayer.setAlpha(this._toolConfig.getAlpha());
    }

    private void doDraw(SurfaceHolder surfaceHolder) {
        try {
            Canvas lockCanvas = surfaceHolder.lockCanvas();
            onDraw(lockCanvas);
            surfaceHolder.unlockCanvasAndPost(lockCanvas);
        } catch (Exception unused) {
            Lg.e("doDraw()内でException発生！");
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this._bitmap != null) {
            if (!this._isFullScreen) {
                canvas.clipRect(this._maskRect.left, this._maskRect.top, this._maskRect.right, this._maskRect.bottom);
            }
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(this._bitmap, 0.0f, 0.0f, (Paint) null);
            if (this._alphaLayer.isEnabled()) {
                this._alphaLayer.draw(canvas);
            }
        }
        if (shouldDrawFrame()) {
            drawFrame(canvas);
        }
    }

    @Override // android.view.View
    public synchronized boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap = this._alphaLayer.isEnabled() ? this._alphaLayer.getBitmap() : this._bitmap;
        if (bitmap != null && !this._isLock) {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this._isEnabled = true;
                onActionDown(motionEvent, bitmap);
            } else if (action == 1) {
                onActionUp(motionEvent);
                this._isEnabled = true;
                this._touchedUpBmp = Bitmap.createBitmap(this._bitmap, this._maskRect.left, this._maskRect.top, this._maskRect.width(), this._maskRect.height());
            } else if (action != 2) {
                if (action == 5) {
                    this._isEnabled = false;
                }
            } else if (this._isEnabled) {
                if (this._toolConfig.getBtnId() == 0) {
                    Analytics.getIns().setPenUsageSituationEvent(ePenUsageSituationDimension.usePen1);
                }
                if (this._toolConfig.getBtnId() == 1) {
                    Analytics.getIns().setPenUsageSituationEvent(ePenUsageSituationDimension.usePen2);
                }
                onActionMove(motionEvent, bitmap);
            }
            doDraw(getHolder());
            return true;
        }
        return true;
    }

    private void drawFrame(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(DipUtils.dp2px(getContext(), 2));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(getContext().getColor(R.color.DrawerHighLightBackground));
        ResRect resRect = ContentRectHolder.INSTANCE.get();
        FitResolution.getRectFitWithIn(resRect, new ResRect(this._maskRect.width(), this._maskRect.height()));
        canvas.drawRect(new Rect(resRect.x + this._maskRect.left, resRect.y + this._maskRect.top, resRect.x + resRect.w + this._maskRect.left, resRect.y + resRect.h + this._maskRect.top), paint);
    }

    private boolean shouldDrawFrame() {
        if (FrameForUltraWideUtils.Companion.shouldDrawFrame(getContext())) {
            int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[ContentsSelectStatus.getIns().get().ordinal()];
            return i == 1 || i == 2 || i == 3;
        }
        return false;
    }

    /* renamed from: com.epson.iprojection.ui.activities.marker.PaintView$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType;

        static {
            int[] iArr = new int[eContentsType.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType = iArr;
            try {
                iArr[eContentsType.Photo.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Pdf.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Delivery.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void onActionDown(MotionEvent motionEvent, Bitmap bitmap) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        this._tool.onFingerDown(pointF.x, pointF.y, bitmap);
        this._isDrawingNoAddHistory = true;
    }

    private void onActionMove(MotionEvent motionEvent, Bitmap bitmap) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        for (int i = 0; i < motionEvent.getHistorySize(); i++) {
            this._tool.onFingerMove(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), bitmap);
        }
        this._tool.onFingerMove(pointF.x, pointF.y, bitmap);
        this._isDrawingNoAddHistory = true;
    }

    private void onActionUp(MotionEvent motionEvent) {
        drawAlphaLayer();
        if (this._isDrawingNoAddHistory) {
            addBitmapToHistory();
            this._isDrawingNoAddHistory = false;
        }
        requestPjSendImage();
    }

    private void drawAlphaLayer() {
        if (this._alphaLayer.isEnabled()) {
            this._alphaLayer.draw(this._bitmap);
            this._alphaLayer.clear();
        }
    }

    private void requestPjSendImage() {
        SendEventWatcher sendEventWatcher = this._sendEventWatcher;
        if (sendEventWatcher != null) {
            sendEventWatcher.register();
        }
    }

    public final synchronized Bitmap getImageBitmap() throws BitmapMemoryException {
        Rect rect;
        if (this._isFullScreen) {
            Bitmap bitmap = this._bitmap;
            if (bitmap != null) {
                return BitmapUtils.copy(bitmap, Bitmap.Config.ARGB_8888, true);
            }
            return null;
        }
        Bitmap bitmap2 = this._bitmap;
        if (bitmap2 == null || (rect = this._maskRect) == null) {
            return null;
        }
        return BitmapUtils.createBitmap(bitmap2, rect.left, this._maskRect.top, this._maskRect.right - this._maskRect.left, this._maskRect.bottom - this._maskRect.top);
    }

    private void addBitmapToHistory() {
        try {
            this._undoRedoMgr.add(this._bitmap);
            updateUndoRedoButton();
        } catch (UndoRedoMgr.CopyBitmapAllocationException unused) {
            errorUndoRedoBitmap();
        }
    }

    public void undoRedo(boolean z) {
        Bitmap bitmap;
        try {
            if (z) {
                bitmap = this._undoRedoMgr.undo();
            } else {
                bitmap = this._undoRedoMgr.redo();
            }
        } catch (UndoRedoMgr.CopyBitmapAllocationException unused) {
            errorUndoRedoBitmap();
            bitmap = null;
        }
        if (bitmap == null) {
            return;
        }
        this._bitmap = bitmap;
        this._touchedUpBmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        doDraw(getHolder());
        requestPjSendImage();
    }

    private void updateUndoRedoButton() {
        IGettableUpdateUndoRedoButton iGettableUpdateUndoRedoButton = this._implUpdateUndoRedoButton;
        if (iGettableUpdateUndoRedoButton != null) {
            iGettableUpdateUndoRedoButton.updateUndoRedoButton();
        }
    }

    private void errorUndoRedoBitmap() {
        IGettableUpdateUndoRedoButton iGettableUpdateUndoRedoButton = this._implUpdateUndoRedoButton;
        if (iGettableUpdateUndoRedoButton != null) {
            iGettableUpdateUndoRedoButton.errorUndoRedoBitmap();
        }
    }

    public IGettableUndoRedo getGettableRedoUndoImpl() {
        return this._undoRedoMgr;
    }
}
