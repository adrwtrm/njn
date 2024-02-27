package com.epson.iprojection.common.utils;

import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PjUtils {
    public static boolean isAvailableWhiteboard() {
        if (Pj.getIns().isConnected()) {
            ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
            if (CollectionUtils.isEmpty(nowConnectingPJList)) {
                return false;
            }
            return nowConnectingPJList.get(0).getPjInfo().isSupportSharedWB;
        }
        return false;
    }
}
