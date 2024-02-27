package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.RectUtils;
import com.epson.iprojection.engine.Engine;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.utils.SleepWarningBoardDrawer;

/* loaded from: classes.dex */
public class ImageSender {
    private static final int DELAY_SEND_TIME = 60000;
    public static final int PROJECTION_FONTSIZE_DIV = 50;
    private static final int WAIT_IMG_MARGIN = 32;
    private final Context _context;
    private final Engine _engine;
    private final Resolution _res;
    private Bitmap _bmp = null;
    private Rect[] _rect = null;
    private Bitmap _bmpWait = null;
    private Canvas _canvas = null;
    private boolean _isWaitMode = false;
    private final Handler _handler = new Handler(Looper.getMainLooper());
    private final Runnable _delaySender = new DelaySender();
    private boolean _isSendable = true;
    private boolean _isSleeping = false;

    public ImageSender(Engine engine, Resolution resolution, Context context) {
        this._engine = engine;
        this._res = resolution;
        this._context = context;
    }

    public void lock() {
        this._isSendable = false;
    }

    public void unlock() {
        this._isSendable = true;
    }

    public void notifyToSleep() {
        this._isSleeping = true;
    }

    public void notifyToWakeUp() {
        this._isSleeping = false;
    }

    public synchronized void sendImage(Bitmap bitmap, Bitmap bitmap2) throws BitmapMemoryException {
        this._handler.removeCallbacks(this._delaySender);
        this._isWaitMode = false;
        clear();
        if (bitmap != null) {
            BitmapUtils.drawBitmapFitWithIn(bitmap, this._bmp);
        }
        if (bitmap2 != null) {
            BitmapUtils.drawBitmapFitWithIn(bitmap2, this._bmp);
        }
        sendImage(this._bmp);
    }

    public synchronized void sendImageWithConvertingBitmapConfig(Bitmap bitmap) {
        if (this._canvas == null) {
            return;
        }
        this._handler.removeCallbacks(this._delaySender);
        this._isWaitMode = false;
        clear();
        if (MirroringEntrance.INSTANCE.isReversingMirroring()) {
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, -1.0f);
            matrix.postTranslate((this._bmp.getWidth() - bitmap.getWidth()) / 2.0f, ((this._bmp.getHeight() - bitmap.getHeight()) / 2.0f) + bitmap.getHeight());
            this._canvas.drawBitmap(bitmap, matrix, null);
        } else {
            Rect centeringRect = RectUtils.INSTANCE.getCenteringRect(bitmap.getWidth(), bitmap.getHeight(), this._bmp.getWidth(), this._bmp.getHeight());
            this._canvas.drawBitmap(bitmap, centeringRect.left, centeringRect.top, (Paint) null);
        }
        try {
            sendImage(this._bmp);
        } catch (BitmapMemoryException unused) {
        }
    }

    public synchronized void sendImageDirectly(Bitmap bitmap) {
        this._handler.removeCallbacks(this._delaySender);
        this._isWaitMode = false;
        try {
            sendImage(bitmap);
        } catch (BitmapMemoryException unused) {
        }
    }

    public synchronized void sendImageForWarningSleep(Context context) {
        Bitmap copy = this._bmp.copy(CommonDefine.BITMAP_CONFIG, true);
        SleepWarningBoardDrawer.draw(context, new Canvas(copy));
        try {
            sendImage(copy);
        } catch (BitmapMemoryException unused) {
        }
    }

    public synchronized void resendImage() {
        try {
            sendImage(this._bmp);
        } catch (BitmapMemoryException unused) {
        }
    }

    public synchronized void sendWaitImage(Context context) throws BitmapMemoryException {
        if (this._isWaitMode) {
            Lg.i("既に待機画面なので何もしない");
            return;
        }
        Lg.i("待機画面送信");
        this._isWaitMode = true;
        ContentRectHolder.INSTANCE.clear();
        this._handler.removeCallbacks(this._delaySender);
        clear();
        this._canvas.drawBitmap(this._bmpWait, (this._bmp.getWidth() - 32) - this._bmpWait.getWidth(), (this._bmp.getHeight() - 32) - this._bmpWait.getHeight(), (Paint) null);
        sendImage(this._bmp);
        this._handler.postDelayed(this._delaySender, 60000L);
        Lg.d("送信完了");
    }

    public synchronized void sendWaitImageWhenConnected(Context context) throws BitmapMemoryException {
        if (this._isWaitMode) {
            Lg.i("既に待機画面なので何もしない");
            return;
        }
        Lg.i("待機画面送信");
        this._isWaitMode = true;
        ContentRectHolder.INSTANCE.clear();
        this._handler.removeCallbacks(this._delaySender);
        clear();
        this._canvas.drawBitmap(this._bmpWait, (this._bmp.getWidth() - 32) - this._bmpWait.getWidth(), (this._bmp.getHeight() - 32) - this._bmpWait.getHeight(), (Paint) null);
        Paint paint = new Paint();
        paint.setTextSize(30.0f);
        paint.setColor(-1);
        this._canvas.drawText(context.getString(R.string._iProConnected_), 32.0f, (this._bmp.getHeight() - 32) - 30, paint);
        sendImage(this._bmp);
        this._handler.postDelayed(this._delaySender, 60000L);
        Lg.d("送信完了");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void sendWaitImageAllBlack(Context context) throws BitmapMemoryException {
        this._handler.removeCallbacks(this._delaySender);
        clear();
        sendImage(this._bmp);
        Lg.d("完全黒画像送信完了");
    }

    public void resetWaitMode() {
        this._isWaitMode = false;
    }

    private void sendImage(Bitmap bitmap) throws BitmapMemoryException {
        if (this._isSendable && this._bmp != null) {
            ResRect resRect = new ResRect(this._bmp.getWidth(), this._bmp.getHeight());
            if (this._res.isAspectRatioUltraWide() && !this._isSleeping && Pj.getIns().getNowConnectingPJList().size() == 1 && isUltraWideSPS2OptimiseSettingChecked().booleanValue() && !ContentRectHolder.INSTANCE.isEmpty()) {
                resRect = ContentRectHolder.INSTANCE.get();
            }
            int SendImage = this._engine.SendImage(bitmap, this._rect, resRect);
            if (SendImage != 0) {
                Lg.e("送信失敗 = " + SendImage);
            }
        }
    }

    private Boolean isUltraWideSPS2OptimiseSettingChecked() {
        return Boolean.valueOf(PrefUtils.readInt(this._context, PrefTagDefine.conPJ_OPTIMASE_ULTRAWIDE_ASPECT_SPS2) == 1);
    }

    public void create(Context context) throws BitmapMemoryException {
        if (this._bmp != null || this._bmpWait != null) {
            delete();
        }
        this._rect = r1;
        Rect[] rectArr = {new Rect(0, 0, this._res.getWidth(), this._res.getHeight())};
        this._bmp = BitmapUtils.createBitmap(this._res.getWidth(), this._res.getHeight(), CommonDefine.BITMAP_CONFIG);
        this._bmpWait = BitmapUtils.decodeResource(context.getResources(), R.drawable.wait);
        this._canvas = new Canvas(this._bmp);
        this._isSendable = true;
    }

    public void delete() {
        this._handler.removeCallbacks(this._delaySender);
        this._rect = null;
        this._canvas = null;
        Bitmap bitmap = this._bmp;
        if (bitmap != null) {
            bitmap.recycle();
            this._bmp = null;
        }
        Bitmap bitmap2 = this._bmpWait;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this._bmpWait = null;
        }
    }

    private void clear() {
        this._canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
    }

    /* loaded from: classes.dex */
    class DelaySender implements Runnable {
        DelaySender() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                ImageSender imageSender = ImageSender.this;
                imageSender.sendWaitImageAllBlack(imageSender._context.getApplicationContext());
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
            }
        }
    }
}
