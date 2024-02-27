package com.epson.iprojection.ui.common.singleton;

import android.net.Network;
import com.epson.iprojection.linkagedata.data.D_LinkageData;

/* loaded from: classes.dex */
public class LinkageDataInfoStacker {
    private static final LinkageDataInfoStacker _inst = new LinkageDataInfoStacker();
    private D_LinkageData _data;
    private Network _network;
    private byte[] _pjIPAddr;

    public void set(D_LinkageData d_LinkageData, byte[] bArr) {
        this._data = d_LinkageData;
        this._pjIPAddr = bArr;
    }

    public void setNetwork(Network network) {
        this._network = network;
    }

    public Network getNetwork() {
        return this._network;
    }

    public D_LinkageData get() {
        return this._data;
    }

    public void clear() {
        this._data = null;
        this._pjIPAddr = null;
    }

    public int[] getConnectedIpAddr() {
        if (this._data.isSameWirelessIp(this._pjIPAddr)) {
            return this._data.wirelessIP;
        }
        return this._data.wiredIP;
    }

    public boolean isEasyConnect() {
        D_LinkageData d_LinkageData = this._data;
        if (d_LinkageData == null) {
            return false;
        }
        return d_LinkageData.isEasyConnect;
    }

    private LinkageDataInfoStacker() {
    }

    public static LinkageDataInfoStacker getIns() {
        return _inst;
    }
}
