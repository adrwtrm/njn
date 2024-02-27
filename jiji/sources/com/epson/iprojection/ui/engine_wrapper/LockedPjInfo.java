package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.engine.common.D_PjInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class LockedPjInfo {
    ArrayList<ConnectPjInfo> _tryConnectingPjInf = null;
    ArrayList<ConnectPjInfo> _nowConnectingPjInf = null;

    public void setTryConnectingPjInfo(ConnectPjInfo connectPjInfo) {
        ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
        this._tryConnectingPjInf = arrayList;
        arrayList.add(connectPjInfo);
    }

    public void setTryConnectingPjInfo(ArrayList<ConnectPjInfo> arrayList) {
        this._tryConnectingPjInf = new ArrayList<>(arrayList);
    }

    public ArrayList<ConnectPjInfo> getTryConnectingPjInfo() {
        return this._tryConnectingPjInf;
    }

    public void setNowConnectingPjInfo() {
        ArrayList<ConnectPjInfo> arrayList = this._tryConnectingPjInf;
        if (arrayList == null) {
            return;
        }
        this._nowConnectingPjInf = arrayList;
        this._tryConnectingPjInf = null;
    }

    public int getNowConnectingPjNum() {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public ConnectPjInfo getNowConnectingPjInfo() {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(0);
    }

    public ArrayList<ConnectPjInfo> getNowConnectingPjArray() {
        return this._nowConnectingPjInf;
    }

    public int getTryConnectingPjNum() {
        ArrayList<ConnectPjInfo> arrayList = this._tryConnectingPjInf;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public int getTryConnectingPjID() {
        ArrayList<ConnectPjInfo> arrayList = this._tryConnectingPjInf;
        if (arrayList == null) {
            return -1;
        }
        return arrayList.get(0).getPjInfo().ProjectorID;
    }

    public int getTryConnectingPjID(int i) {
        ArrayList<ConnectPjInfo> arrayList = this._tryConnectingPjInf;
        if (arrayList != null && i < arrayList.size()) {
            return this._tryConnectingPjInf.get(i).getPjInfo().ProjectorID;
        }
        return -1;
    }

    public boolean isInTryConnectingPjID(int i) {
        if (-1 != i && this._tryConnectingPjInf != null) {
            for (int i2 = 0; i2 < this._tryConnectingPjInf.size(); i2++) {
                if (i == this._tryConnectingPjInf.get(i2).getPjInfo().ProjectorID) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getNowConnectingPjID() {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList == null) {
            return -1;
        }
        return arrayList.get(0).getPjInfo().ProjectorID;
    }

    public int getNowConnectingPjID(int i) {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList != null && i < arrayList.size()) {
            return this._nowConnectingPjInf.get(i).getPjInfo().ProjectorID;
        }
        return -1;
    }

    public D_PjInfo getNowConnectingPjInfo(int i) {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList != null && i < arrayList.size()) {
            return this._nowConnectingPjInf.get(i).getPjInfo();
        }
        return null;
    }

    public void removeNowConnectingPjID(int i) {
        if (this._nowConnectingPjInf == null) {
            return;
        }
        for (int i2 = 0; i2 < this._nowConnectingPjInf.size(); i2++) {
            if (i == this._nowConnectingPjInf.get(i2).getPjInfo().ProjectorID) {
                this._nowConnectingPjInf.remove(i2);
                return;
            }
        }
    }

    public boolean isInNowConnectingPjID(int i) {
        if (-1 != i && this._nowConnectingPjInf != null) {
            for (int i2 = 0; i2 < this._nowConnectingPjInf.size(); i2++) {
                if (i == this._nowConnectingPjInf.get(i2).getPjInfo().ProjectorID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        this._nowConnectingPjInf = null;
        this._tryConnectingPjInf = null;
    }

    public boolean isNowMpp() {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        return this._nowConnectingPjInf.get(0).getPjInfo().isMPP();
    }

    public int getMPPVersion() {
        ArrayList<ConnectPjInfo> arrayList = this._nowConnectingPjInf;
        int i = -1;
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<ConnectPjInfo> it = this._nowConnectingPjInf.iterator();
            while (it.hasNext()) {
                ConnectPjInfo next = it.next();
                if (i < next.getPjInfo().nSupportMinMPPVersion) {
                    i = next.getPjInfo().nSupportMinMPPVersion;
                }
            }
        }
        return i;
    }
}
