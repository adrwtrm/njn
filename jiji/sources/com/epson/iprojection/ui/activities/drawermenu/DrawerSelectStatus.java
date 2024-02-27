package com.epson.iprojection.ui.activities.drawermenu;

import java.util.LinkedList;

/* loaded from: classes.dex */
public class DrawerSelectStatus {
    private static final DrawerSelectStatus _inst = new DrawerSelectStatus();
    private final LinkedList<DataSet> _list = new LinkedList<>();

    /* loaded from: classes.dex */
    class DataSet {
        long id;
        eDrawerMenuItem type;

        DataSet(long j, eDrawerMenuItem edrawermenuitem) {
            this.id = j;
            this.type = edrawermenuitem;
        }
    }

    public void push(long j, eDrawerMenuItem edrawermenuitem) {
        this._list.push(new DataSet(j, edrawermenuitem));
    }

    public void pop(long j) {
        for (int i = 0; i < this._list.size(); i++) {
            if (this._list.get(i).id == j) {
                this._list.remove(i);
                return;
            }
        }
    }

    public void popForce() {
        this._list.pop();
    }

    public eDrawerMenuItem get() {
        try {
            return this._list.getFirst().type;
        } catch (Exception unused) {
            return null;
        }
    }

    public void clear() {
        this._list.clear();
    }

    private DrawerSelectStatus() {
    }

    public static DrawerSelectStatus getIns() {
        return _inst;
    }
}
