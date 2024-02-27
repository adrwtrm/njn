package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConnectPjInfo {
    private boolean _isNoInterrupt;
    private D_PjInfo _pjInfo;
    private String _keyword = "";
    private eStatus _status = eStatus.normal;

    /* loaded from: classes.dex */
    public enum eStatus {
        normal,
        unavailable,
        standby
    }

    public ConnectPjInfo(D_PjInfo d_PjInfo, boolean z) {
        this._pjInfo = d_PjInfo;
        this._isNoInterrupt = z;
    }

    public void setStatus(eStatus estatus) {
        this._status = estatus;
    }

    public eStatus getStatus() {
        return this._status;
    }

    public boolean isNoInterrupt() {
        return this._isNoInterrupt;
    }

    public void setNoInterrupt(boolean z) {
        this._isNoInterrupt = z;
    }

    public void setPjInfo(D_PjInfo d_PjInfo) {
        this._pjInfo = d_PjInfo;
    }

    public D_PjInfo getPjInfo() {
        return this._pjInfo;
    }

    public void setKeyword(String str) {
        this._keyword = str;
    }

    public final String getKeyword() {
        return this._keyword;
    }

    public static ArrayList<D_PjInfo> createPjInfoList(ArrayList<ConnectPjInfo> arrayList) {
        ArrayList<D_PjInfo> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            Iterator<ConnectPjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().getPjInfo());
            }
        }
        return arrayList2;
    }

    public static ArrayList<String> createUniqeInfoList(ArrayList<ConnectPjInfo> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            Iterator<ConnectPjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(NetUtils.toHexString((byte[]) it.next().getPjInfo().UniqInfo.clone()));
            }
        }
        return arrayList2;
    }
}
