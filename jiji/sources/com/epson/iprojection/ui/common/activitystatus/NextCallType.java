package com.epson.iprojection.ui.common.activitystatus;

/* loaded from: classes.dex */
public class NextCallType {
    private static final NextCallType _inst = new NextCallType();
    private eContentsType _type = eContentsType.None;
    private int _requestCode = 0;

    public void set(eContentsType econtentstype) {
        this._type = econtentstype;
        if (econtentstype == eContentsType.None) {
            this._requestCode = 0;
        }
    }

    public eContentsType get() {
        return this._type;
    }

    public void setRequestCode(int i) {
        this._requestCode = i;
    }

    public int getRequestCode() {
        return this._requestCode;
    }

    private NextCallType() {
    }

    public static NextCallType getIns() {
        return _inst;
    }
}
