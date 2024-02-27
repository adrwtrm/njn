package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.common.exception.UnknownSsidException;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class PjStatusOfRegistedUpdateThread extends Thread {
    private final Context _context;
    private boolean _isAvailable = true;

    public PjStatusOfRegistedUpdateThread(Context context) {
        this._context = context;
    }

    public void finish() {
        this._isAvailable = false;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        ArrayList<ConnectPjInfo> registeredPjList;
        while (this._isAvailable && (registeredPjList = Pj.getIns().getRegisteredPjList()) != null && registeredPjList.size() != 0) {
            Iterator<ConnectPjInfo> it = registeredPjList.iterator();
            while (it.hasNext()) {
                final ConnectPjInfo next = it.next();
                new Thread(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.PjStatusOfRegistedUpdateThread$$ExternalSyntheticLambda0
                    {
                        PjStatusOfRegistedUpdateThread.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        PjStatusOfRegistedUpdateThread.this.m213x47c1167a(next);
                    }
                }).start();
            }
            Sleeper.sleep(5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$run$0$com-epson-iprojection-ui-engine_wrapper-PjStatusOfRegistedUpdateThread  reason: not valid java name */
    public /* synthetic */ void m213x47c1167a(ConnectPjInfo connectPjInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        if (PermissionUtilsKt.isUsablePermission(this._context, "android.permission.ACCESS_FINE_LOCATION")) {
            try {
                if (!Pj.getIns().isSameSsidWhenRegistered()) {
                    connectPjInfo.setStatus(ConnectPjInfo.eStatus.unavailable);
                    return;
                }
            } catch (UnknownSsidException unused) {
                Lg.w("It may have been an SSID that can’t be registered.");
            }
        }
        Pj.getIns().sendAndReceiveEscvpCommandWithIp(connectPjInfo.getPjInfo().IPAddr, "PWR?", stringBuffer, connectPjInfo.getPjInfo().isSupportedSecuredEscvp);
        String stringBuffer2 = stringBuffer.toString();
        stringBuffer2.hashCode();
        char c = 65535;
        switch (stringBuffer2.hashCode()) {
            case -1921785869:
                if (stringBuffer2.equals("PWR=01")) {
                    c = 0;
                    break;
                }
                break;
            case -1921785866:
                if (stringBuffer2.equals("PWR=04")) {
                    c = 1;
                    break;
                }
                break;
            case -1921785861:
                if (stringBuffer2.equals("PWR=09")) {
                    c = 2;
                    break;
                }
                break;
            case 1537:
                if (stringBuffer2.equals("01")) {
                    c = 3;
                    break;
                }
                break;
            case 1540:
                if (stringBuffer2.equals("04")) {
                    c = 4;
                    break;
                }
                break;
            case 1545:
                if (stringBuffer2.equals("09")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 3:
                connectPjInfo.setStatus(ConnectPjInfo.eStatus.normal);
                return;
            case 1:
            case 2:
            case 4:
            case 5:
                connectPjInfo.setStatus(ConnectPjInfo.eStatus.standby);
                return;
            default:
                connectPjInfo.setStatus(ConnectPjInfo.eStatus.unavailable);
                return;
        }
    }
}
