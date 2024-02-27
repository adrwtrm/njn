package com.epson.iprojection.engine.common;

import java.util.ArrayList;

/* loaded from: classes.dex */
public interface OnPjEventListener {
    void onAcceptClientResolution(int i);

    void onChangeMppControlMode(int i, D_MppUserInfo d_MppUserInfo);

    void onChangeMppLayout(ArrayList<D_MppLayoutInfo> arrayList);

    void onChangeScreenLockStatus(boolean z);

    void onConnectRet(int i, int i2, int i3);

    void onDisconnectFromAdmin();

    void onEndDelivery();

    void onNotifyImageProcTime(D_ImageProcTime d_ImageProcTime);

    void onNotifyModeratorPassword(int i, boolean z);

    void onNotifySharedWbPin(int i, byte[] bArr);

    void onReceiveDelivery(D_DeliveryInfo d_DeliveryInfo);

    void onReceiveDeliveryError(D_DeliveryError d_DeliveryError);

    void onReceiveThumbnail(D_ThumbnailInfo d_ThumbnailInfo);

    void onReceiveThumbnailError(D_ThumbnailError d_ThumbnailError);

    void onRejectClientResolution(int i, ArrayList<D_ClientResolutionInfo> arrayList);

    void onStartDelivery();

    void onUpdateMppUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2);
}
