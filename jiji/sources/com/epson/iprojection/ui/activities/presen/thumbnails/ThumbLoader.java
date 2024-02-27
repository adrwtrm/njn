package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.LinearLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.common.utils.SynchronousCounter;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IFatalErrorListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbLoader;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ThumbLoader {
    private final Bitmap _blankBmp;
    private final Context _context;
    private final ThumbEncPos _encPos;
    private final IFiler _filer;
    private final IFatalErrorListener _implFatalError;
    private final ArrayList<LinearLayout> _thumbViewList;
    public boolean _isThreadWork = true;
    private Thread _thread = null;
    private int _createdN = 0;
    public boolean _isInterrupted = true;
    private final SynchronousCounter _callInterruptCounter = new SynchronousCounter();
    private final Handler _handler = new Handler();

    public ThumbLoader(Context context, IFiler iFiler, ArrayList<LinearLayout> arrayList, int i, Bitmap bitmap, IFatalErrorListener iFatalErrorListener) {
        this._context = context;
        this._filer = iFiler;
        this._thumbViewList = arrayList;
        this._encPos = new ThumbEncPos(arrayList, i);
        this._blankBmp = bitmap;
        this._implFatalError = iFatalErrorListener;
    }

    public synchronized void start() {
        Lg.d("start");
        if (this._thread == null) {
            Thread thread = new Thread(new LoadThumbnailThread());
            this._thread = thread;
            this._isThreadWork = true;
            thread.setPriority(1);
            this._thread.start();
        }
    }

    public synchronized void stop(boolean z) {
        Lg.d("stop");
        if (this._thread != null) {
            this._isThreadWork = false;
            try {
                if (!z) {
                    Lg.d("最大2000[ms]停止します");
                    this._thread.join(2000L);
                    Lg.d("join完了");
                } else {
                    Lg.d("即時停止します");
                }
            } catch (InterruptedException unused) {
            }
        }
        this._thread = null;
    }

    public boolean isLoading() {
        return this._thread != null;
    }

    public void setCurrentPos(int i) {
        clearOverCache(i);
        this._createdN = this._encPos.setCurrentPos(i);
    }

    public void clearOverCache(int i) {
        int size = this._thumbViewList.size();
        for (int i2 = 0; i2 < i - 100 && i2 < size; i2++) {
            ThumbImageView thumbImageView = (ThumbImageView) this._thumbViewList.get(i2).findViewById(R.id.img_thumb);
            if (thumbImageView.isFinished()) {
                thumbImageView.setImageBitmap(this._blankBmp);
                thumbImageView.finished(false);
            }
        }
        for (int i3 = i + 100; i3 < size; i3++) {
            ThumbImageView thumbImageView2 = (ThumbImageView) this._thumbViewList.get(i3).findViewById(R.id.img_thumb);
            if (thumbImageView2.isFinished()) {
                thumbImageView2.setImageBitmap(this._blankBmp);
                thumbImageView2.finished(false);
            }
        }
    }

    public synchronized void interrupt(boolean z) {
        Lg.d("サムネ生成 : ".concat(z ? "中断" : "中断解除"));
        if (z) {
            this._callInterruptCounter.add();
            if (this._callInterruptCounter.get() != 1) {
                Lg.i("コールが溜まっているので何もせずに抜けます");
                return;
            }
        } else {
            this._callInterruptCounter.subtract();
            if (this._callInterruptCounter.get() != 0) {
                Lg.i("コールが溜まっているので何もせずに抜けます");
                return;
            }
        }
        this._isInterrupted = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class LoadThumbnailThread implements Runnable {
        private LoadThumbnailThread() {
        }

        @Override // java.lang.Runnable
        public void run() {
            int pos;
            while (ThumbLoader.this._isThreadWork) {
                try {
                    while (ThumbLoader.this._isThreadWork && ThumbLoader.this._createdN <= 20 && ThumbLoader.this._createdN <= ThumbLoader.this._filer.getTotalPages() && (pos = ThumbLoader.this._encPos.getPos()) != -1) {
                        while (ThumbLoader.this._isInterrupted) {
                            Sleeper.sleep(1L);
                            if (!ThumbLoader.this._isThreadWork) {
                                return;
                            }
                        }
                        int thumbWidth = ThumbMgr.getThumbWidth((Activity) ThumbLoader.this._context);
                        int thumbHeight = ThumbMgr.getThumbHeight((Activity) ThumbLoader.this._context);
                        Bitmap image = ThumbLoader.this._filer.getImage(pos, thumbWidth, thumbHeight, 0);
                        if (image == null) {
                            Lg.e("サムネイル画像が取得できませんでした");
                            image = BitmapUtils.createWhiteBitmap(thumbWidth, thumbHeight);
                        } else {
                            Lg.d("Get thumb bmp.w:" + image.getWidth() + "h:" + image.getHeight());
                        }
                        if (image.getConfig() != Bitmap.Config.RGB_565) {
                            Bitmap copy = image.copy(Bitmap.Config.RGB_565, true);
                            if (copy != null) {
                                image.recycle();
                            }
                            register(copy, pos);
                        } else {
                            register(image, pos);
                        }
                        ((ThumbImageView) ((LinearLayout) ThumbLoader.this._thumbViewList.get(pos)).findViewById(R.id.img_thumb)).finished(true);
                        Sleeper.sleep(1L);
                        ThumbLoader thumbLoader = ThumbLoader.this;
                        thumbLoader._createdN = thumbLoader._encPos.next();
                    }
                    Sleeper.sleep(200L);
                } catch (UnavailableException unused) {
                    Lg.e("サービスが利用できません");
                    ThumbLoader.this._implFatalError.onFatalErrorOccured();
                    return;
                } catch (BitmapMemoryException unused2) {
                    ActivityGetter.getIns().killMyProcess();
                    return;
                }
            }
        }

        private void register(final Bitmap bitmap, final int i) {
            ThumbLoader.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.thumbnails.ThumbLoader$LoadThumbnailThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ThumbLoader.LoadThumbnailThread.this.m164xf0b80f3d(i, bitmap);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$register$0$com-epson-iprojection-ui-activities-presen-thumbnails-ThumbLoader$LoadThumbnailThread  reason: not valid java name */
        public /* synthetic */ void m164xf0b80f3d(int i, Bitmap bitmap) {
            ThumbImageView thumbImageView = (ThumbImageView) ((LinearLayout) ThumbLoader.this._thumbViewList.get(i)).findViewById(R.id.img_thumb);
            thumbImageView.setImageBitmap(bitmap);
            thumbImageView.setAnimation();
        }
    }
}
