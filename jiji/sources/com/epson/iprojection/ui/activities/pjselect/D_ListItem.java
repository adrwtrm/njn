package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;

/* loaded from: classes.dex */
public class D_ListItem {
    private final Context _context;
    private boolean _isChecked;
    private boolean _isConnected;
    private final listType _listType;
    private D_PjInfo _pjInfo;
    private dispPriority _priority = dispPriority.DP_NORMAL;
    private usingState _usingState;

    /* loaded from: classes.dex */
    public enum dispPriority {
        DP_NORMAL,
        DP_2ND,
        DP_1ST
    }

    /* loaded from: classes.dex */
    public enum listType {
        PROJECTOR,
        PROFILE,
        PROFILE_LOCAL,
        PROFILE_SHARED
    }

    /* loaded from: classes.dex */
    public enum usingState {
        US_NOUSE,
        US_USING,
        US_MIRROR,
        US_BUSY,
        US_DISABLE
    }

    public D_ListItem(Context context, listType listtype, D_PjInfo d_PjInfo, boolean z) {
        this._context = context;
        this._pjInfo = d_PjInfo;
        this._listType = listtype;
        if (listtype == listType.PROJECTOR) {
            this._isConnected = z;
            this._isChecked = d_PjInfo.bSelected;
            this._usingState = convState(d_PjInfo.nDispStatus);
            return;
        }
        this._isConnected = false;
        this._isChecked = false;
        this._usingState = usingState.US_NOUSE;
    }

    public void update(D_PjInfo d_PjInfo) {
        this._pjInfo = d_PjInfo;
        this._usingState = convState(d_PjInfo.nDispStatus);
    }

    public boolean isChecked() {
        return this._isChecked;
    }

    public boolean isConnected() {
        return this._isConnected;
    }

    public D_PjInfo getPjInfo() {
        return this._pjInfo;
    }

    public usingState getUsingState() {
        return this._usingState;
    }

    public listType getListType() {
        return this._listType;
    }

    public dispPriority getPriority() {
        return this._priority;
    }

    public void setPriority(dispPriority disppriority) {
        this._priority = disppriority;
    }

    public String getDisplayText() {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[this._listType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? "" : this._context.getString(R.string._SharedProfile_);
                }
                return this._context.getString(R.string._LocalProfile_);
            }
            return this._context.getString(R.string._Profile_);
        }
        return this._pjInfo.PrjName;
    }

    public String getIpAddress() {
        return AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[this._listType.ordinal()] != 1 ? "" : NetUtils.cvtIPAddr(this._pjInfo.IPAddr);
    }

    public void uncheck() {
        this._isConnected = false;
        this._isChecked = false;
    }

    public boolean isSelectable() {
        return isSelectable(this._usingState);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.D_ListItem$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState;

        static {
            int[] iArr = new int[usingState.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState = iArr;
            try {
                iArr[usingState.US_NOUSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState[usingState.US_USING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState[usingState.US_MIRROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState[usingState.US_BUSY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState[usingState.US_DISABLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[listType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType = iArr2;
            try {
                iArr2[listType.PROJECTOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[listType.PROFILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[listType.PROFILE_LOCAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[listType.PROFILE_SHARED.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private static boolean isSelectable(usingState usingstate) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$usingState[usingstate.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return true;
        }
        return (i == 4 || i == 5) ? false : true;
    }

    public static boolean isSelectable(int i) {
        return isSelectable(convState(i));
    }

    private static usingState convState(int i) {
        if (i != 1) {
            if (i != 3) {
                if (i != 4) {
                    if (i == 5) {
                        return usingState.US_MIRROR;
                    }
                    return usingState.US_NOUSE;
                }
                return usingState.US_BUSY;
            }
            return usingState.US_USING;
        }
        return usingState.US_DISABLE;
    }
}
