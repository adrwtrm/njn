package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.ui.engine_wrapper.StateMachine;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class PjCyclicSearchThreadForRegisted extends Thread implements IPjManualSearchResultListener, OnFindPjListener {
    private boolean _foundBz;
    private boolean _isAvailable = true;
    private boolean _isManualSearching = false;
    private boolean _isAutoSearching = false;

    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    public void onUpdateProjectorList() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this._foundBz = false;
        while (this._isAvailable) {
            if (Pj.getIns().getConnectState() == StateMachine.ConnectState.Default) {
                manualSearch();
                autoSearch();
            }
            Sleeper.sleep(5000L);
        }
    }

    private void manualSearch() {
        if (this._isAvailable && !Pj.getIns().isSearchingInEngine()) {
            this._isManualSearching = true;
            Pj.getIns().manualSearch((IPjManualSearchResultListener) this, (List<String>) getIpList(), false);
            while (this._isAvailable && this._isManualSearching) {
                Sleeper.sleep(100L);
                if (!Pj.getIns().isEqualFindPjListener(this)) {
                    return;
                }
            }
        }
    }

    private ArrayList<String> getIpList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            arrayList.add(NetUtils.cvtIPAddr(it.next().getPjInfo().IPAddr));
        }
        return arrayList;
    }

    private void autoSearch() {
        if (this._isAvailable && !Pj.getIns().isSearchingInEngine()) {
            this._isAutoSearching = true;
            Pj.getIns().autoSearch(this);
            while (this._isAvailable && this._isAutoSearching) {
                Sleeper.sleep(100L);
                if (!Pj.getIns().isEqualFindPjListener(this)) {
                    return;
                }
            }
        }
    }

    public boolean finish() {
        this._isAvailable = false;
        if (this._isAutoSearching || this._isManualSearching) {
            Pj.getIns().endSearchDirect();
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        update(d_PjInfo);
        if (d_PjInfo.isPjTypeBusiness()) {
            this._foundBz = true;
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        this._isManualSearching = false;
        Pj.getIns().onSearchThreadEnd();
        if (this._foundBz) {
            Pj.getIns().clearRegisteredPjInf();
        }
    }

    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    public void onPjInfo(D_PjInfo d_PjInfo) {
        update(d_PjInfo);
    }

    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    public void onSearchEnd(int i) {
        this._isAutoSearching = false;
        Pj.getIns().onSearchThreadEnd();
        if (this._foundBz) {
            Pj.getIns().clearRegisteredPjInf();
        }
    }

    private void update(D_PjInfo d_PjInfo) {
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (d_PjInfo.Status != 0 && Arrays.equals(d_PjInfo.UniqInfo, next.getPjInfo().UniqInfo)) {
                next.setPjInfo(d_PjInfo);
                if (d_PjInfo.isPjTypeBusiness()) {
                    this._foundBz = true;
                    return;
                }
                return;
            }
        }
    }
}
