package com.epson.iprojection.ui.common.editableList;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class SaveData {
    private final ArrayList<String> _list;
    public final int SAVEDATA_PJNAME = 0;
    public final int SAVEDATA_IPADDRESS = 1;
    public final int SAVEDATA_DATE = 2;

    public SaveData(ArrayList<String> arrayList) {
        this._list = arrayList;
    }

    public String get(int i) {
        if (i >= this._list.size()) {
            return null;
        }
        return this._list.get(i);
    }
}
