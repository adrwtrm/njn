package com.epson.iprojection.ui.engine_wrapper.interfaces;

import com.epson.iprojection.engine.common.D_DeliveryError;
import com.epson.iprojection.engine.common.D_ImageProcTime;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_ThumbnailError;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import java.util.ArrayList;

/* loaded from: classes.dex */
public interface IOnConnectListener {

    /* loaded from: classes.dex */
    public enum DisconedReason {
        Nothing,
        Default,
        WifiOff,
        Interrupt,
        NetworkErr,
        PjPowerOff,
        FailedToConnect,
        DisconnOther,
        DisconnAdmin,
        NoSet,
        EngineError
    }

    /* loaded from: classes.dex */
    public enum FailReason {
        Default,
        NpVersionError,
        IlligalKeyword,
        DiffCombiPj,
        MppMaxUser,
        Standby,
        IncludeUnavailable
    }

    /* loaded from: classes.dex */
    public enum MppControlMode {
        NoUse,
        CollaborationOld,
        Collaboration,
        ModeratorAdmin,
        ModeratorEntry
    }

    void onChangeDeliveryPermission(boolean z, boolean z2, boolean z3);

    void onChangeMPPControlMode(MppControlMode mppControlMode);

    void onChangeMPPLayout(ArrayList<D_MppLayoutInfo> arrayList);

    void onChangeScreenLockStatus(boolean z);

    void onChangedScreenLockByMe(boolean z);

    void onConnectCanceled();

    void onConnectFail(int i, FailReason failReason);

    void onConnectSucceed();

    void onDeliverImage(String str, D_DeliveryPermission d_DeliveryPermission);

    void onDeliveryError(D_DeliveryError d_DeliveryError);

    void onDisconnect(int i, DisconedReason disconedReason, boolean z);

    void onDisconnectOne(int i, DisconedReason disconedReason);

    void onEndDelivery();

    void onFinishDelivery();

    void onNotifyImageProcTime(D_ImageProcTime d_ImageProcTime);

    void onRegisterSucceed();

    void onStartDelivery();

    void onThumbnailError(D_ThumbnailError d_ThumbnailError);

    void onThumbnailInfo(D_ThumbnailInfo d_ThumbnailInfo);

    void onUnregistered();

    void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2);
}
