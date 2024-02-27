package com.epson.iprojection.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.epson.iprojection.engine.common.D_AddFixedSearchInfo;
import com.epson.iprojection.engine.common.D_AddPjInfo;
import com.epson.iprojection.engine.common.D_ConnectPjInfo;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_ResolutionInfo;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.engine.common.OnPjEventListener;
import com.epson.iprojection.engine.common.ProtocolType;
import com.epson.iprojection.engine.common.Tool;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.engine.common.eSrcType;
import com.epson.iprojection.engine.jni.EngineJni;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Engine {
    public static final int ENGINE_ERROR_ACCESS_DENIED = -16;
    public static final int ENGINE_ERROR_ALREADY_DELIVERY_OTHER_CMD = -801;
    public static final int ENGINE_ERROR_ALREADY_OTHERTYPE_SEARCHINFO = -203;
    public static final int ENGINE_ERROR_ALREADY_RUN_SEARCHING = -207;
    public static final int ENGINE_ERROR_ALREADY_SEARCH_INFO = -204;
    public static final int ENGINE_ERROR_CANNOT_CONNECT_STATUS = -212;
    public static final int ENGINE_ERROR_CONNECTING = -205;
    public static final int ENGINE_ERROR_CONNECTING_OTHER_SSID = -210;
    public static final int ENGINE_ERROR_CONNECT_MAX = -415;
    public static final int ENGINE_ERROR_CONNECT_MIRRORING = -416;
    public static final int ENGINE_ERROR_CREATE_THREAD = -208;
    public static final int ENGINE_ERROR_DEVICE_ACCESS = -501;
    public static final int ENGINE_ERROR_DISCONNECT_DEVICE = -502;
    public static final int ENGINE_ERROR_DISCONNECT_MYSELFONLY = -432;
    public static final int ENGINE_ERROR_DISPLAY_COLOR_NUMBER = -425;
    public static final int ENGINE_ERROR_DUPLEX_USER_ID = -418;
    public static final int ENGINE_ERROR_END_SEARCH_THREAD = -209;
    public static final int ENGINE_ERROR_ENUMESSID = -200;
    public static final int ENGINE_ERROR_ESCVP_WEB_PASSWORD = -441;
    public static final int ENGINE_ERROR_FCN_CAN_NOT_USED = -703;
    public static final int ENGINE_ERROR_FCN_CLIENT_NOT_FOUND = -702;
    public static final int ENGINE_ERROR_FCN_NOT_REMOVE_MSG_FILTER = -701;
    public static final int ENGINE_ERROR_FCN_NOT_SUPPORT_VERSION = -705;
    public static final int ENGINE_ERROR_FCN_UNKNOWN = -704;
    public static final int ENGINE_ERROR_FILE_NOT_FOUND = -12;
    public static final int ENGINE_ERROR_FORWARD_COMPATIBILITY = -427;
    public static final int ENGINE_ERROR_FW_BACKUP = -104;
    public static final int ENGINE_ERROR_FW_GET_INFOMATION = -102;
    public static final int ENGINE_ERROR_FW_INITIALIZE = -101;
    public static final int ENGINE_ERROR_FW_LOAD_MODULE = -103;
    public static final int ENGINE_ERROR_FW_RECOVERY = -105;
    public static final int ENGINE_ERROR_FW_SET = -106;
    public static final int ENGINE_ERROR_GETERSSI = -211;
    public static final int ENGINE_ERROR_GETREGVALUE = -7;
    public static final int ENGINE_ERROR_INF_NOT_FOUND = -11;
    public static final int ENGINE_ERROR_LOCK_MEMORY = -6;
    public static final int ENGINE_ERROR_MOVIE_FAIL_CONNECT = -606;
    public static final int ENGINE_ERROR_MOVIE_FAIL_CONTROL = -605;
    public static final int ENGINE_ERROR_MOVIE_FAIL_SENDCOMMAND = -607;
    public static final int ENGINE_ERROR_MOVIE_NOERROR = -604;
    public static final int ENGINE_ERROR_MOVIE_NOTOPEN_FILE = -603;
    public static final int ENGINE_ERROR_MOVIE_NOTSUPPORT_PROJECTOR = -602;
    public static final int ENGINE_ERROR_MOVIE_UNINITIALIZE = -601;
    public static final int ENGINE_ERROR_MPP_VERSION = -419;
    public static final int ENGINE_ERROR_NETWORK_ESSID_ERROR = -431;
    public static final int ENGINE_ERROR_NIC_IP_0 = -202;
    public static final int ENGINE_ERROR_NIC_NOT_FOUND = -201;
    public static final int ENGINE_ERROR_NON_CONNECTING = -439;
    public static final int ENGINE_ERROR_NOTSUPPORTED = -460;
    public static final int ENGINE_ERROR_NOT_EASY_MODE = -10;
    public static final int ENGINE_ERROR_NOT_SEARCHING = -440;
    public static final int ENGINE_ERROR_NOT_SUPPORT_OS = -3;
    public static final int ENGINE_ERROR_NOW_MODE = -434;
    public static final int ENGINE_ERROR_NOW_UPDATE_LAYOUT_OTHER_USER = -417;
    public static final int ENGINE_ERROR_OUTOF_INDEX = -4;
    public static final int ENGINE_ERROR_PASSWORD = -802;
    public static final int ENGINE_ERROR_RECV_DATA = -504;
    public static final int ENGINE_ERROR_SCSI_STATUS = -503;
    public static final int ENGINE_ERROR_SEND_ECON_CMD = -206;
    public static final int ENGINE_ERROR_SEND_ESCVP_CMD = -430;
    public static final int ENGINE_ERROR_SERVICE_ACCESS = -15;
    public static final int ENGINE_ERROR_SETREGVALUE = -8;
    public static final int ENGINE_ERROR_SOCKET = -13;
    public static final int ENGINE_ERROR_SOCKET_FINISH = -14;
    public static final int ENGINE_ERROR_TARGET_MYSELF = -436;
    public static final int ENGINE_ERROR_UD_ACCESS_SHARE_MEMORY = -505;
    public static final int ENGINE_ERROR_UD_CREATE_SHARE_MEMORY = -506;
    public static final int ENGINE_ERROR_USER_DISCONNECT = -437;
    public static final int ENGINE_ERROR_USER_NOTHING = -435;
    public static final int ENGINE_ERROR_USER_NOT_AUTHORITY = -433;
    public static final int ENGINE_INFO_DISCONNECTED_ADMIN = -438;
    public static final int ENGINE_INFO_DISPLAYCHANGE_ERROR = -429;
    public static final int ENGINE_INFO_DISPLAYCHANGE_OLDTYPE = -428;
    public static final int ENGINE_INFO_TERMINATE_OTHER_USER = -424;
    public static final int ERROR_ALREADY_NOW_CONNECTING = -18;
    public static final int ERROR_ALREADY_NOW_SEARCHING = -17;
    public static final int ERROR_ALREADY_NOW_UPDATE_CLIENT_RESOLUTION = -453;
    public static final int ERROR_APPLICATION_VERSION = -405;
    public static final int ERROR_CANCEL = 1;
    public static final int ERROR_DISCONNECT_CLIENT = -403;
    public static final int ERROR_DISCONNECT_TIMEOUT = -450;
    public static final int ERROR_ILLEGAL_PROJECTORKEYWORD = -402;
    public static final int ERROR_INTERNAL = -2;
    public static final int ERROR_INTERRUPT = -404;
    public static final int ERROR_NETWORK_ERROR = -406;
    public static final int ERROR_NIC_DISABLE = -213;
    public static final int ERROR_NIC_IP_0 = -202;
    public static final int ERROR_NIC_NOT_CONNECT = -214;
    public static final int ERROR_NIC_NOT_FOUND = -201;
    public static final int ERROR_NON_CONNECTING = -439;
    public static final int ERROR_NOTCONNECT_PROJECTOR = -401;
    public static final int ERROR_NOTDISCONNECT_PROJECTOR = -409;
    public static final int ERROR_NOT_INITIALIZE = -9;
    public static final int ERROR_NOT_RESPONSE = -421;
    public static final int ERROR_NOT_SEARCHING = -440;
    public static final int ERROR_NOT_SELECT_RESOLUTION_IMAGE = -452;
    public static final int ERROR_NOT_SUPPORT_RESOLUTION = -451;
    public static final int ERROR_PARAMETER = -5;
    public static final int ERROR_PRJINFO_NOT_RECV = -426;
    public static final int ERROR_PROJECTION = -408;
    public static final int ERROR_PROJECTIONMODE = -411;
    public static final int ERROR_SEND_ESCVP_CMD = -430;
    public static final int ERROR_SUCCESS = 0;
    public static final int ERROR_SUCCESS_NEED_SELECT_PROJECTION = 2;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ESCVP_COMMAND_AVMUTE = 1;
    public static final int ESCVP_COMMAND_FREEZE = 7;
    public static final int ESCVP_COMMAND_LAN = 6;
    public static final int ESCVP_COMMAND_SRC_PC = 3;
    public static final int ESCVP_COMMAND_SRC_VIDEO = 2;
    public static final int ESCVP_COMMAND_VOLUME_DOWN = 5;
    public static final int ESCVP_COMMAND_VOLUME_UP = 4;
    public static final int PARAMETER_INFO_JPEG_QUALITY = 0;
    public static final int PARAMETER_INFO_KEEPALIVE_INTERVAL_TCP = 3;
    public static final int PARAMETER_INFO_KEEPALIVE_INTERVAL_UDP = 4;
    public static final int PARAMETER_INFO_KEEPALIVE_TIMEOUT = 1;
    public static final int PARAMETER_INFO_KEEPALIVE_TIMEOUT_MPP = 5;
    public static final int PARAMETER_INFO_KEEPALIVE_TIME_OUT_MDLHI4 = 2;
    public static final int SEARCH_MODE_AUTO = 0;
    public static final int SEARCH_MODE_UPDATE = 1;
    public static final int WAIT_IMG_MARGIN = 32;
    EngineJni m_Engine;

    private Engine(Context context, boolean z, String str) {
        this.m_Engine = new EngineJni(context, z, str);
    }

    public static Engine Initialize(Context context, boolean z, String str) {
        if (context != null) {
            return new Engine(context, z, str);
        }
        return null;
    }

    public int Destroy() {
        return this.m_Engine.Destroy();
    }

    public void Destroy(int i) {
        this.m_Engine.Destroy(i);
    }

    public int StartSearchPj(int i, OnFindPjListener onFindPjListener) {
        return this.m_Engine.StartSearchPj(i, onFindPjListener);
    }

    public boolean isEqualFindPjListener(OnFindPjListener onFindPjListener) {
        return this.m_Engine.isEqualFindPjListener(onFindPjListener);
    }

    public int EndSearchPj() {
        return this.m_Engine.EndSearchPj();
    }

    public int AddSearchProjector(D_AddFixedSearchInfo d_AddFixedSearchInfo, D_AddPjInfo d_AddPjInfo) {
        return this.m_Engine.AddSearchProjector(d_AddFixedSearchInfo, d_AddPjInfo);
    }

    public int DeleteProjectorInfo() {
        return this.m_Engine.DeleteProjectorInfo();
    }

    public void ClearManualSearchFlag() {
        this.m_Engine.ClearManualSearchFlag();
    }

    public void ClearCurrentManualSearchFlag() {
        this.m_Engine.ClearCurrentManualSearchFlag();
    }

    public void SetCommonRect(int i, int i2) {
        this.m_Engine.SetCommonRect(i, i2);
    }

    public boolean IsAlivePj() {
        return this.m_Engine.IsAlivePj();
    }

    public int GetSendImageBufferSize(ArrayList<D_ConnectPjInfo> arrayList, int[] iArr) {
        return this.m_Engine.GetSendImageBufferSize(arrayList, iArr);
    }

    public int Connect(ArrayList<D_ConnectPjInfo> arrayList, OnPjEventListener onPjEventListener, String str) {
        return this.m_Engine.Connect(arrayList, onPjEventListener, str);
    }

    public int Disconnect() {
        return this.m_Engine.Disconnect();
    }

    public int TerminateSession() {
        return this.m_Engine.TerminateSession();
    }

    public int GetResolution(int i, D_ResolutionInfo d_ResolutionInfo) {
        return this.m_Engine.GetResolution(i, d_ResolutionInfo);
    }

    public boolean isEnablePJControl() {
        return this.m_Engine.isEnablePJControl();
    }

    public int Ctl_ChangeSource(int i, eSrcType esrctype) {
        return this.m_Engine.Ctl_ChangeSource(i, esrctype);
    }

    public int Ctl_AVMute(int i) {
        return this.m_Engine.Ctl_AVMute(i);
    }

    public int Ctl_Freeze(int i) {
        return this.m_Engine.Ctl_Freeze(i);
    }

    public int Ctl_VolumeUP(int i) {
        return this.m_Engine.Ctl_VolumeUP(i);
    }

    public int Ctl_VolumeDown(int i) {
        return this.m_Engine.Ctl_VolumeDown(i);
    }

    public int Ctl_DispQRCode(int i) {
        return this.m_Engine.Ctl_DispQRCode(i);
    }

    public int SendU2UCommandNWStandbyON(int i) {
        return this.m_Engine.SendU2UCommandNWStandbyON(i);
    }

    public int SendU2UCommandKeyEmulation(String str, int i) {
        return this.m_Engine.SendU2UCommandKeyEmulation(str, i);
    }

    public int SendEscvpCommand(int i, String str) {
        return this.m_Engine.SendEscvpCommand(i, str);
    }

    public int SendEscvpCommandWithIp(byte[] bArr, String str) {
        return this.m_Engine.SendEscvpCommandWithIp(Tool.BinarryArrayToInt(bArr, 0), str);
    }

    public int SendEscvp(byte[] bArr, String str, String str2) {
        return this.m_Engine.SendEscvp(Tool.BinarryArrayToInt(bArr, 0), str, str2);
    }

    public int SendAndReceiveEscvpCommandWithIp(byte[] bArr, String str, char[] cArr, boolean z) {
        return this.m_Engine.SendAndReceiveEscvpCommandWithIp(Tool.BinarryArrayToInt(bArr, 0), str, cArr, z);
    }

    public int SendImage(Bitmap bitmap, Rect[] rectArr, ResRect resRect) throws BitmapMemoryException {
        return this.m_Engine.SendImage(bitmap, rectArr, resRect);
    }

    public int SetResolution(int i, boolean z) {
        return this.m_Engine.SetResolution(i, z);
    }

    public int SetEncryption(boolean z) {
        return this.m_Engine.SetEncryption(z);
    }

    public int SetProjectorLogUpload(boolean z) {
        return this.m_Engine.SetProjectorLogUpload(z);
    }

    public int SetBandWidth(eBandWidth ebandwidth) {
        return this.m_Engine.SetBandWidth(ebandwidth);
    }

    public int SetAudioTransfer(boolean z) {
        return this.m_Engine.SetAudioTransfer(z);
    }

    public int SetImageProcNotifyInterval(int i) {
        return this.m_Engine.SetImageProcNotifyInterval(i);
    }

    public int ProjectionMyself() {
        return this.m_Engine.ProjectionMyself();
    }

    public boolean IsEnableChangeMppControlMode() {
        return this.m_Engine.IsEnableChangeMPPControlMode();
    }

    public int SetMppControlMode(int i, byte[] bArr) {
        return this.m_Engine.SetMPPControlMode(i, bArr);
    }

    public int ControlMppOtherUser(long j, int i) {
        return this.m_Engine.ControlMPPOtherUser(j, i);
    }

    public int SetScreenLockStatus(boolean z) {
        return this.m_Engine.SetScreenLockStatus(z);
    }

    public boolean IsSetScreenLock() {
        return this.m_Engine.IsSetScreenLock();
    }

    public void SetProjectionMode(int i) {
        this.m_Engine.SetProjectionMode(i);
    }

    public int GetProjectionMode() {
        return this.m_Engine.GetProjectionMode();
    }

    public int DisconnectMppOtherUser(long j) {
        return this.m_Engine.DisconnectMPPOtherUser(j);
    }

    public int UpdateMppLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        return this.m_Engine.UpdateMPPLayout(arrayList);
    }

    public boolean IsJNI() {
        return this.m_Engine.IsJNI();
    }

    public boolean isMpp() {
        return this.m_Engine.isMPP();
    }

    public int RequestDelivery(boolean z, boolean z2, boolean z3, boolean z4) {
        return this.m_Engine.RequestDelivery(z, z2, z3, z4);
    }

    public int RequestDeliveryWhitePaper(boolean z, boolean z2, boolean z3, boolean z4) {
        return this.m_Engine.RequestDeliveryWhitePaper(z, z2, z3, z4);
    }

    public int RequestDeliveryMethod(boolean z) {
        return this.m_Engine.RequestDeliveryMethod(z);
    }

    public int SetDeliveryParmission(boolean z, boolean z2, boolean z3) {
        return this.m_Engine.SetDeliveryParmission(z, z2, z3);
    }

    public int RequestThumbnail(int i, int i2, int i3) {
        return this.m_Engine.RequestThumbnail(i, i2, i3);
    }

    public int StopThumbnail() {
        return this.m_Engine.StopThumbnail();
    }

    public int SetAllowModeratorMonitering(boolean z) {
        return this.m_Engine.SetAllowModeratorMonitering(z);
    }

    public int ChangeProtocol(ProtocolType protocolType) {
        return this.m_Engine.ChangeProtocol(protocolType == ProtocolType.WebRTC);
    }

    public int RequestDisplayKeyword(int i) {
        return this.m_Engine.RequestDisplayKeyword(i);
    }

    public boolean IsSearching() {
        return this.m_Engine.GetSearchStatus() == -207;
    }
}
