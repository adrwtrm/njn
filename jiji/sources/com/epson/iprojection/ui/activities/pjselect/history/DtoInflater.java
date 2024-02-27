package com.epson.iprojection.ui.activities.pjselect.history;

/* loaded from: classes.dex */
public class DtoInflater {
    private final String _date;
    private final String _ipAddr;
    private final String _pjName;

    public DtoInflater(String str, String str2, String str3) {
        this._pjName = str;
        this._ipAddr = str2;
        this._date = str3;
    }

    public String getPjName() {
        return this._pjName;
    }

    public String getipAddr() {
        return this._ipAddr;
    }

    public String getDate() {
        return this._date;
    }
}
