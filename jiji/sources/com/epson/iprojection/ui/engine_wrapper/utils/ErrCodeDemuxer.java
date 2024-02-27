package com.epson.iprojection.ui.engine_wrapper.utils;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;

/* loaded from: classes.dex */
public class ErrCodeDemuxer {
    public static IOnConnectListener.DisconedReason getReason(int i) {
        Lg.d(String.format("切断します…理由[%s]", MsgGetterUtils.getErrMsg(i)));
        if (i != -450) {
            if (i == -438) {
                return IOnConnectListener.DisconedReason.DisconnAdmin;
            }
            if (i == -424) {
                return IOnConnectListener.DisconedReason.DisconnOther;
            }
            if (i != -421 && i != -406) {
                if (i != -401) {
                    if (i != -404) {
                        if (i == -403) {
                            return IOnConnectListener.DisconedReason.PjPowerOff;
                        }
                        return IOnConnectListener.DisconedReason.Default;
                    }
                    return IOnConnectListener.DisconedReason.Interrupt;
                }
                return IOnConnectListener.DisconedReason.FailedToConnect;
            }
        }
        return IOnConnectListener.DisconedReason.NetworkErr;
    }
}
