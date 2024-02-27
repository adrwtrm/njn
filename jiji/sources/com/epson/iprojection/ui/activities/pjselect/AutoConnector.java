package com.epson.iprojection.ui.activities.pjselect;

import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class AutoConnector {
    private final D_LinkageData _data;
    private D_PjInfo _secondaryPjInfo = null;
    private boolean _isPrimary = true;
    private boolean _isSearching = false;

    public AutoConnector(D_LinkageData d_LinkageData) {
        this._data = d_LinkageData;
    }

    public void onPjFind(D_PjInfo d_PjInfo, boolean z) {
        Pj.getIns().selectConnPJ(new ConnectPjInfo(d_PjInfo, z));
        Pj.getIns().setWhiteboardConnect(this._data.isWhiteboard());
        LinkageDataInfoStacker.getIns().set(this._data, d_PjInfo.IPAddr);
        Pj.getIns().onClickConnectEventButton();
    }

    public boolean isConnectPj(D_PjInfo d_PjInfo) {
        if (this._data.hasMacAddr()) {
            return this._data.isMyMacAddr(d_PjInfo.UniqInfo);
        }
        if (d_PjInfo.PrjName.equals(this._data.pjName)) {
            if (this._data.hasIp()) {
                return checkIPAddr(d_PjInfo);
            }
            return true;
        }
        return false;
    }

    public D_PjInfo getSecondaryPjInfo() {
        return this._secondaryPjInfo;
    }

    private boolean checkIPAddr(D_PjInfo d_PjInfo) {
        if (this._data.isMyPrimaryIp(d_PjInfo.IPAddr)) {
            return true;
        }
        if (this._data.isMyIp(d_PjInfo.IPAddr)) {
            this._secondaryPjInfo = d_PjInfo;
        }
        return false;
    }

    public void setIsPrimary(boolean z) {
        this._isPrimary = z;
    }

    public String getIpAddress() {
        D_LinkageData d_LinkageData = this._data;
        if (d_LinkageData != null) {
            if (this._isPrimary) {
                return d_LinkageData.getPrimaryIpAddress();
            }
            return d_LinkageData.getSecondaryIpAddress();
        }
        return null;
    }

    public boolean isSearching() {
        return this._isSearching;
    }

    public void setSearching(boolean z) {
        this._isSearching = z;
    }
}
