package com.epson.iprojection.ui.activities.pjselect;

/* loaded from: classes.dex */
public class D_ProfileItem {
    public String _comment;
    public relation _depth;
    public String _ipAddress;
    public boolean _isFoler;
    public String _nodeName;
    public String _projName;
    public int _uniqueNum;

    /* loaded from: classes.dex */
    static class relation {
        int parent = 0;
        int current = 0;

        relation() {
        }
    }

    public D_ProfileItem(boolean z, int i, int i2, String str, String str2, String str3, String str4, int i3) {
        relation relationVar = new relation();
        this._depth = relationVar;
        relationVar.parent = i;
        this._depth.current = i2;
        this._isFoler = z;
        this._nodeName = str;
        this._projName = str2;
        this._ipAddress = str3;
        this._uniqueNum = i3;
        this._comment = str4;
    }
}
