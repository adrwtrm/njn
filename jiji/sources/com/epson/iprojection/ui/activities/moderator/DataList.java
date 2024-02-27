package com.epson.iprojection.ui.activities.moderator;

import com.epson.iprojection.engine.common.D_MppUserInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class DataList {
    public static final int ME_ID = Integer.MIN_VALUE;
    private final ArrayList<D_MppUserInfo> _dataList = new ArrayList<>();
    private D_MppUserInfo _me;

    public DataList(ArrayList<D_MppUserInfo> arrayList) {
        Iterator<D_MppUserInfo> it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            D_MppUserInfo next = it.next();
            if (i == 0) {
                this._me = next;
            }
            this._dataList.add(next);
            i++;
        }
    }

    public int size() {
        return this._dataList.size();
    }

    public D_MppUserInfo get(int i) {
        if (i == Integer.MIN_VALUE) {
            return this._me;
        }
        return this._dataList.get(i);
    }

    public D_MppUserInfo get(long j) {
        if (j == this._me.uniqueId) {
            return this._me;
        }
        Iterator<D_MppUserInfo> it = this._dataList.iterator();
        while (it.hasNext()) {
            D_MppUserInfo next = it.next();
            if (next.uniqueId == j) {
                return next;
            }
        }
        return null;
    }
}
