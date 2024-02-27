package com.epson.iprojection.ui.activities.marker;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.StopWatch;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public class SendEventWatcher {
    private static final int SEND_ITVL = 100;
    private final IOnSendEvent _impl;
    private boolean _isSendEventOccured = false;
    private boolean _isThreadWorking = true;

    public SendEventWatcher(IOnSendEvent iOnSendEvent) {
        this._impl = iOnSendEvent;
        new SendImageThread().start();
    }

    public void destroy() {
        this._isThreadWorking = false;
    }

    public void register() {
        setEventInf(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setEventInf(boolean z) {
        this._isSendEventOccured = z;
    }

    /* loaded from: classes.dex */
    private class SendImageThread extends Thread {
        private SendImageThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                StopWatch stopWatch = new StopWatch();
                while (SendEventWatcher.this._isThreadWorking) {
                    stopWatch.start();
                    if (SendEventWatcher.this._isSendEventOccured) {
                        SendEventWatcher.this.setEventInf(false);
                        SendEventWatcher.this._impl.onSendEvent();
                    }
                    Sleeper.sleep(100 - stopWatch.getDiff());
                }
                Lg.i("監視スレッド終了");
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
            }
        }
    }
}
