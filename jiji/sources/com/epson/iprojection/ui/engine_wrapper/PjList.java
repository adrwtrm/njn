package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class PjList {
    private final ArrayList<D_PjInfo> _pjList = new ArrayList<>();
    private final ArrayList<D_PjInfo> _pjList_justNowFound = new ArrayList<>();
    private boolean _isInterruped = false;

    public void add(D_PjInfo d_PjInfo) {
        this._pjList.add(d_PjInfo);
    }

    public void addToJustNowFoundList(D_PjInfo d_PjInfo) {
        this._pjList_justNowFound.add(d_PjInfo);
    }

    public void clearJustNowFoundList() {
        this._pjList_justNowFound.clear();
        this._isInterruped = false;
    }

    public void interrupted() {
        this._isInterruped = true;
    }

    public ArrayList<D_PjInfo> getPJList() {
        return this._pjList;
    }

    public D_PjInfo get(int i) {
        return this._pjList.get(i);
    }

    public void set(int i, D_PjInfo d_PjInfo) {
        if (i >= this._pjList.size()) {
            Lg.e("illigal id");
        }
        this._pjList.set(i, d_PjInfo);
    }

    public D_PjInfo getPJByID(int i) {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (i == next.ProjectorID) {
                return next;
            }
        }
        return null;
    }

    private D_PjInfo getPJByID_fromJustNowFoundList(int i) {
        Iterator<D_PjInfo> it = this._pjList_justNowFound.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (i == next.ProjectorID) {
                return next;
            }
        }
        return null;
    }

    public D_PjInfo getPJByIP(String str) {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (str.compareTo(NetUtils.cvtIPAddr(next.IPAddr)) == 0) {
                return next;
            }
        }
        return null;
    }

    public D_PjInfo getPJByIPandName(String str, String str2) {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (str.compareTo(NetUtils.cvtIPAddr(next.IPAddr)) == 0 && str2.equals(next.PrjName)) {
                return next;
            }
        }
        return null;
    }

    public int getIndexOfPjInfoList(D_PjInfo d_PjInfo) {
        for (int i = 0; i < this._pjList.size(); i++) {
            if (d_PjInfo.ProjectorID == this._pjList.get(i).ProjectorID) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this._pjList.size();
    }

    public void deleteNotFoundPj(IPjSearchStatusListener iPjSearchStatusListener, int[] iArr) {
        if (this._isInterruped) {
            return;
        }
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            int i = it.next().ProjectorID;
            if (!shouldExclude(i, iArr) && getPJByID_fromJustNowFoundList(i) == null) {
                if (iPjSearchStatusListener != null) {
                    iPjSearchStatusListener.onPjLose(i);
                }
                it.remove();
            }
        }
    }

    private boolean shouldExclude(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (i2 != -1 && i2 == i) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlreadyRegistered(String str) {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            if (NetUtils.cvtIPAddr(it.next().IPAddr).compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }

    public void clearAllCurrentManualFoundFlag() {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            it.next().bCurrentManualFound = false;
        }
    }

    public void clearAllManualFoundFlag() {
        Iterator<D_PjInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            it.next().bManualFound = false;
        }
    }
}
