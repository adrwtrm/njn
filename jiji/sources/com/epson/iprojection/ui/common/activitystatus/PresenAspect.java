package com.epson.iprojection.ui.common.activitystatus;

import java.util.LinkedList;

/* loaded from: classes.dex */
public class PresenAspect {
    public static final int ERROR = -1;
    private static final PresenAspect _inst = new PresenAspect();
    private final LinkedList<D> _list = new LinkedList<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class D {
        int _h;
        int _taskID;
        int _w;

        D(int i, int i2, int i3) {
            this._taskID = i;
            this._w = i2;
            this._h = i3;
        }
    }

    public void set(int i, int i2, int i3) {
        D d = getD(i);
        if (d == null) {
            this._list.add(new D(i, i2, i3));
            return;
        }
        d._w = i2;
        d._h = i3;
    }

    public int getW(int i) {
        D d = getD(i);
        if (d == null) {
            return -1;
        }
        return d._w;
    }

    public int getH(int i) {
        D d = getD(i);
        if (d == null) {
            return -1;
        }
        return d._h;
    }

    public void clear(int i) {
        for (int i2 = 0; i2 < this._list.size(); i2++) {
            if (this._list.get(i2)._taskID == i) {
                this._list.remove(i2);
                return;
            }
        }
    }

    public void clearAll() {
        this._list.clear();
    }

    private D getD(int i) {
        for (int i2 = 0; i2 < this._list.size(); i2++) {
            if (this._list.get(i2)._taskID == i) {
                return this._list.get(i2);
            }
        }
        return null;
    }

    private PresenAspect() {
    }

    public static PresenAspect getIns() {
        return _inst;
    }
}
