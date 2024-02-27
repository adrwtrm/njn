package com.epson.iprojection.ui.activities.presen;

/* loaded from: classes.dex */
public class Selector {
    private int _nowSelectNum = 0;
    private final int _totalPages;

    public Selector(int i) {
        this._totalPages = i;
    }

    public int getNowSelectNum() {
        return this._nowSelectNum;
    }

    public void setNowSelectNum(int i) {
        this._nowSelectNum = i;
    }

    public int getTotalPages() {
        return this._totalPages;
    }

    /* renamed from: com.epson.iprojection.ui.activities.presen.Selector$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek;

        static {
            int[] iArr = new int[eSeek.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek = iArr;
            try {
                iArr[eSeek.eSeek_Start.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek[eSeek.eSeek_Prev.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek[eSeek.eSeek_Next.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek[eSeek.eSeek_End.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public boolean Seek(eSeek eseek) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$presen$eSeek[eseek.ordinal()];
        if (i != 1) {
            if (i == 2) {
                int i2 = this._nowSelectNum;
                if (i2 == 0) {
                    return false;
                }
                this._nowSelectNum = i2 - 1;
            } else if (i == 3) {
                int i3 = this._nowSelectNum;
                if (i3 == this._totalPages - 1) {
                    return false;
                }
                this._nowSelectNum = i3 + 1;
            } else if (i == 4) {
                int i4 = this._nowSelectNum;
                int i5 = this._totalPages;
                if (i4 == i5 - 1) {
                    return false;
                }
                this._nowSelectNum = i5 - 1;
            }
        } else if (this._nowSelectNum == 0) {
            return false;
        } else {
            this._nowSelectNum = 0;
        }
        return true;
    }
}
