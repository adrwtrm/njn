package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;

/* compiled from: IPj.kt */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0007H&J\u001a\u0010\t\u001a\u00020\u00072\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bH&J\"\u0010\t\u001a\u00020\u00072\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000e\u001a\u00020\u0007H&J\b\u0010\u000f\u001a\u00020\u0007H&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0003H&J\b\u0010\u0015\u001a\u00020\u0007H&J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H&J\b\u0010\u0018\u001a\u00020\u0003H&J\b\u0010\u0019\u001a\u00020\u0003H&J \u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0003H&J&\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u001e2\u0006\u0010\u001f\u001a\u00020\u0003H&J\u0012\u0010 \u001a\u00020\u00112\b\u0010!\u001a\u0004\u0018\u00010\"H&J\u0012\u0010#\u001a\u00020\u00112\b\u0010!\u001a\u0004\u0018\u00010\"H&J\b\u0010$\u001a\u00020\u0007H&J(\u0010%\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0003H&J \u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020&2\u0006\u0010.\u001a\u00020\u0017H&J\b\u0010/\u001a\u00020\u0007H&J\b\u00100\u001a\u00020\u0003H&J\u0018\u00101\u001a\u00020\u00112\u0006\u0010'\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020&H&J\u0018\u00102\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u0011H&J\u0010\u00104\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u0011H&J\b\u00105\u001a\u00020\u0007H&J\u001a\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u00010&H&J\u0010\u00109\u001a\u00020\u00112\u0006\u0010:\u001a\u00020\u0003H&J\u0010\u0010;\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020<H&J\u0010\u0010=\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020>H&J\u0012\u0010?\u001a\u00020\u00072\b\u0010\u0004\u001a\u0004\u0018\u00010@H&J\b\u0010A\u001a\u00020\u0007H&¨\u0006B"}, d2 = {"Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "", "autoSearch", "", "impl", "Lcom/epson/iprojection/engine/common/OnFindPjListener;", "clearDeliveredImagePath", "", "clearOnMinConnectListener", "connect", "inf", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/engine_wrapper/ConnectPjInfo;", "noNeedPreSearch", "disableManualSearchResultListener", "disableNeedSelectProjection", "disconnect", "", "reason", "Lcom/epson/iprojection/ui/engine_wrapper/DisconReason;", "shouldRestoreWifi", "finalizeEngine", "getDeliveredImagePath", "", "isConnected", "isModerator", "manualSearch", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IPjManualSearchResultListener;", "ipAddr", "showDialog", "", "forceDo", "pjcontrol_scomport", "pj", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "pjcontrol_spoweron", "projectionMyself", "sendAndReceiveEscvpCommandWithIp", "", "cmd", "response", "Ljava/lang/StringBuffer;", "isSecure", "sendDigestEscvp", "command", "ipAddress", "password", "sendImageOfMirroringOff", "sendImageWhenConnected", "sendOpenEscvp", "sendU2UCommandKeyEmulation", "projectorID", "sendU2UCommandNWStandbyON", "sendWaitImage", "setMppControlMode", "mode", "moderatorPassword", "setMppScreenLock", "isLock", "setOnConnectListener", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnConnectListener;", "setOnMinConnectListener", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnMinConnectListener;", "setupPjFinder", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IPjSearchStatusListener;", "stopSearch", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IPj {
    boolean autoSearch(OnFindPjListener onFindPjListener);

    void clearDeliveredImagePath();

    void clearOnMinConnectListener();

    void connect(ArrayList<ConnectPjInfo> arrayList);

    void connect(ArrayList<ConnectPjInfo> arrayList, boolean z);

    void disableManualSearchResultListener();

    void disableNeedSelectProjection();

    int disconnect(DisconReason disconReason);

    int disconnect(DisconReason disconReason, boolean z);

    void finalizeEngine();

    String getDeliveredImagePath();

    boolean isConnected();

    boolean isModerator();

    int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, String str, boolean z);

    int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, List<String> list, boolean z);

    int pjcontrol_scomport(D_PjInfo d_PjInfo);

    int pjcontrol_spoweron(D_PjInfo d_PjInfo);

    void projectionMyself();

    int sendAndReceiveEscvpCommandWithIp(byte[] bArr, String str, StringBuffer stringBuffer, boolean z);

    int sendDigestEscvp(String str, byte[] bArr, String str2);

    void sendImageOfMirroringOff();

    boolean sendImageWhenConnected();

    int sendOpenEscvp(String str, byte[] bArr);

    int sendU2UCommandKeyEmulation(String str, int i);

    int sendU2UCommandNWStandbyON(int i);

    void sendWaitImage();

    int setMppControlMode(int i, byte[] bArr);

    int setMppScreenLock(boolean z);

    void setOnConnectListener(IOnConnectListener iOnConnectListener);

    void setOnMinConnectListener(IOnMinConnectListener iOnMinConnectListener);

    void setupPjFinder(IPjSearchStatusListener iPjSearchStatusListener);

    void stopSearch();
}
