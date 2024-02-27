package com.epson.iprojection.ui.activities.pjselect.connected;

import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.StateMachine;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class PjCyclicSearchThread extends Thread implements IPjManualSearchResultListener {
    private final ArrayList<ConnectPjInfo> _list;
    private boolean _isAvailable = true;
    private boolean _isSearching = true;
    private final ArrayList<String> _ipList = new ArrayList<>();

    public PjCyclicSearchThread() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        this._list = nowConnectingPJList;
        if (nowConnectingPJList == null) {
            return;
        }
        Iterator<ConnectPjInfo> it = nowConnectingPJList.iterator();
        while (it.hasNext()) {
            this._ipList.add(NetUtils.cvtIPAddr(it.next().getPjInfo().IPAddr));
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this._ipList.size() == 0) {
            return;
        }
        while (this._isAvailable) {
            if (Pj.getIns().getConnectState() == StateMachine.ConnectState.NowConnecting) {
                this._isSearching = true;
                Pj.getIns().manualSearch((IPjManualSearchResultListener) this, (List<String>) this._ipList, false);
                while (this._isAvailable && this._isSearching) {
                    Sleeper.sleep(1000L);
                }
                Sleeper.sleep(4000L);
            } else {
                Sleeper.sleep(5000L);
            }
        }
    }

    public void finish() {
        this._isAvailable = false;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        Iterator<ConnectPjInfo> it = this._list.iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (Arrays.equals(d_PjInfo.UniqInfo, next.getPjInfo().UniqInfo)) {
                next.setPjInfo(d_PjInfo);
                return;
            }
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        this._isSearching = false;
    }
}
