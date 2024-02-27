package com.epson.iprojection.ui.activities.pjselect.registed;

import android.app.Activity;
import androidx.core.content.ContextCompat;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.common.exception.UnknownSsidException;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SsidChecker {
    private final Activity _activity;
    private final ArrayList<D_PjInfo> _foundPjList = new ArrayList<>();
    private final IPjManualSearchResultListener _impl;
    private final IOnCaughtSsidWarningListener _implSsidWarn;

    public SsidChecker(Activity activity, IPjManualSearchResultListener iPjManualSearchResultListener, IOnCaughtSsidWarningListener iOnCaughtSsidWarningListener) {
        this._activity = activity;
        this._impl = iPjManualSearchResultListener;
        this._implSsidWarn = iOnCaughtSsidWarningListener;
    }

    public void start() {
        this._foundPjList.clear();
        ArrayList arrayList = new ArrayList();
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            arrayList.add(NetUtils.cvtIPAddr(it.next().getPjInfo().IPAddr));
        }
        Pj.getIns().manualSearch(this._impl, (List<String>) arrayList, false);
    }

    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        this._foundPjList.add(d_PjInfo);
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (Arrays.equals(next.getPjInfo().UniqInfo, d_PjInfo.UniqInfo)) {
                next.getPjInfo().Status = d_PjInfo.Status;
                next.getPjInfo().nDispStatus = d_PjInfo.nDispStatus;
                return;
            }
        }
    }

    public void onEndSearchPj() {
        String ssidWhenRegistered;
        try {
            if (Pj.getIns().isSameSsidWhenRegistered() || (ssidWhenRegistered = Pj.getIns().getSsidWhenRegistered()) == null || ssidWhenRegistered.equals(CommonDefine.UNKNOWN_SSID) || this._foundPjList.size() != 0 || !isAvailablePermission()) {
                return;
            }
            this._implSsidWarn.onCaughtSsidWarning();
        } catch (UnknownSsidException unused) {
        }
    }

    private boolean isAvailablePermission() {
        return ContextCompat.checkSelfPermission(this._activity, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }
}
