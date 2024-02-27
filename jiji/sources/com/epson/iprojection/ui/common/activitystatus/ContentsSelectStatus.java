package com.epson.iprojection.ui.common.activitystatus;

/* loaded from: classes.dex */
public class ContentsSelectStatus {
    private static final ContentsSelectStatus _inst = new ContentsSelectStatus();
    private long _id;
    private eContentsType _type = eContentsType.None;

    public void set(long j, eContentsType econtentstype) {
        this._id = j;
        this._type = econtentstype;
    }

    public eContentsType get() {
        return this._type;
    }

    public void clear(long j) {
        if (j == this._id) {
            this._type = eContentsType.None;
        }
    }

    public void clearForce() {
        this._type = eContentsType.None;
    }

    private ContentsSelectStatus() {
    }

    public static ContentsSelectStatus getIns() {
        return _inst;
    }
}
