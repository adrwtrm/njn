package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.os.Handler;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.presen.thumbnails.ScrollPosChecker;

/* loaded from: classes.dex */
public class ScrollPosChecker {
    private final ScrollViewMgr _scrollViewMgr;
    private final ThumbLoader _thumbLoader;
    private Thread _thread = null;
    private boolean _able = false;
    private final Handler _handler = new Handler();
    private CheckThread _checkThread = null;

    public ScrollPosChecker(ScrollViewMgr scrollViewMgr, ThumbLoader thumbLoader) {
        this._scrollViewMgr = scrollViewMgr;
        this._thumbLoader = thumbLoader;
    }

    public void start() {
        if (this._able) {
            return;
        }
        this._able = true;
        this._checkThread = new CheckThread();
        Thread thread = new Thread(this._checkThread);
        this._thread = thread;
        thread.start();
    }

    public void stop() {
        this._able = false;
        this._checkThread._able = false;
        this._thread = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class CheckThread implements Runnable {
        public boolean _able;
        int preID;

        private CheckThread() {
            this._able = true;
            this.preID = -1;
        }

        @Override // java.lang.Runnable
        public void run() {
            while (this._able) {
                final int visualCenterViewID = ScrollPosChecker.this._scrollViewMgr.getVisualCenterViewID();
                if (this.preID != visualCenterViewID) {
                    ScrollPosChecker.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.thumbnails.ScrollPosChecker$CheckThread$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScrollPosChecker.CheckThread.this.m163x95b310a6(visualCenterViewID);
                        }
                    });
                }
                Sleeper.sleep(1000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$run$0$com-epson-iprojection-ui-activities-presen-thumbnails-ScrollPosChecker$CheckThread  reason: not valid java name */
        public /* synthetic */ void m163x95b310a6(int i) {
            ScrollPosChecker.this._thumbLoader.setCurrentPos(i);
            this.preID = i;
        }
    }
}
