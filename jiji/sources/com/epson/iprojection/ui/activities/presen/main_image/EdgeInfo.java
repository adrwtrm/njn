package com.epson.iprojection.ui.activities.presen.main_image;

/* loaded from: classes.dex */
public class EdgeInfo {
    private boolean _isBottomEdge;
    private boolean _isLeftEdge;
    private boolean _isRightEdge;
    private boolean _isTopEdge;

    /* loaded from: classes.dex */
    public enum eDirection {
        eTop,
        eRight,
        eLeft,
        eBottom
    }

    public EdgeInfo() {
        reset();
    }

    public void reset() {
        this._isTopEdge = false;
        this._isRightEdge = false;
        this._isLeftEdge = false;
        this._isBottomEdge = false;
    }

    /* renamed from: com.epson.iprojection.ui.activities.presen.main_image.EdgeInfo$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection;

        static {
            int[] iArr = new int[eDirection.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection = iArr;
            try {
                iArr[eDirection.eTop.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection[eDirection.eRight.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection[eDirection.eLeft.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection[eDirection.eBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void set(eDirection edirection) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection[edirection.ordinal()];
        if (i == 1) {
            this._isTopEdge = true;
        } else if (i == 2) {
            this._isRightEdge = true;
        } else if (i == 3) {
            this._isLeftEdge = true;
        } else if (i != 4) {
        } else {
            this._isBottomEdge = true;
        }
    }

    public boolean isTouching(eDirection edirection) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$presen$main_image$EdgeInfo$eDirection[edirection.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return false;
                    }
                    return this._isBottomEdge;
                }
                return this._isLeftEdge;
            }
            return this._isRightEdge;
        }
        return this._isTopEdge;
    }
}
