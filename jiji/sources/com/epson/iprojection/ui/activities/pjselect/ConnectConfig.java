package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.content.SharedPreferences;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;

/* loaded from: classes.dex */
public class ConnectConfig {
    private static final int FALSE = 0;
    private static final int TRUE = 1;
    private final Context _context;

    public ConnectConfig(Context context) {
        this._context = context;
    }

    public boolean getInterruptSetting() {
        int readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_CONFIG_NOINTURRUPT_TAG);
        return readInt != Integer.MIN_VALUE && readInt == 1;
    }

    public void setInterruptSetting(boolean z) {
        PrefUtils.writeInt(this._context, PrefTagDefine.conPJ_CONFIG_NOINTURRUPT_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
    }

    public boolean isSelectMultiple() {
        return PrefUtils.readInt(this._context, PrefTagDefine.conPJ_CONFIG_SELECT_MULTIPLE_TAG) == 1;
    }

    public void setSelectMultiple(boolean z) {
        PrefUtils.writeInt(this._context, PrefTagDefine.conPJ_CONFIG_SELECT_MULTIPLE_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
    }

    public static boolean isModeratorModeOn(Context context) {
        return PrefUtils.readInt(context, PrefTagDefine.conPJ_CONFIG_NOINTURRUPT_TAG) == 1;
    }
}
