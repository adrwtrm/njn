package com.epson.iprojection.engine.jni;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.engine.common.D_AddFixedSearchInfo;
import com.epson.iprojection.engine.common.D_AddPjInfo;
import com.epson.iprojection.engine.common.D_ClientResolutionInfo;
import com.epson.iprojection.engine.common.D_ConnectPjInfo;
import com.epson.iprojection.engine.common.D_DeliveryError;
import com.epson.iprojection.engine.common.D_DeliveryInfo;
import com.epson.iprojection.engine.common.D_ImageProcTime;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.D_ResolutionInfo;
import com.epson.iprojection.engine.common.D_ThumbnailError;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.engine.common.EngineException;
import com.epson.iprojection.engine.common.LgNP;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.engine.common.OnPjEventListener;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.engine.common.eSrcType;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.serenegiant.common.BuildConfig;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class EngineJni {
    private static final int MPP_CONTROL_MODE_COLLABORATION = 2;
    private static final int MPP_CONTROL_MODE_COLLABORATION_OLD = 1;
    private static final int MPP_CONTROL_MODE_CONNECTED = 0;
    private static final int MPP_CONTROL_MODE_MODERATOR_ADMIN = 4;
    private static final int MPP_CONTROL_MODE_MODERATOR_ENTRY = 3;
    private static final int MPP_CONTROL_MODE_NOT_CONNECT = -1;
    private Context m_context;
    private OnFindPjListener m_findPjListener = null;
    private OnPjEventListener m_pjEventListener = null;
    private final Handler m_handler = new Handler(Looper.getMainLooper());
    private final int m_nKeepAliveTimeout = 10000;
    private final int m_nKeepAliveTimeoutMdlHi4 = 20000;
    private final int m_nKeepAliveIntervalUDP = 1000;
    private final int m_nKeepAliveIntervalTCP = 5000;
    private int m_nMPPControlMode = -1;
    private Bitmap m_pTempImageFull = null;
    private Canvas m_canvasMute = null;
    private Bitmap m_bmpMuteFull = null;
    private Bitmap m_bmpIcon = null;
    private int m_nFixW = 0;
    private int m_nFixH = 0;
    private int m_nMyProjectionState = 1;
    private ResRect m_resRect = null;
    private final int PARAM_ENCRYPTEDCOMMUNICATION = 3;
    private final int ENCRYPTION_NONE = 0;
    private final int ENCRYPTION_AES = 1;
    private final int PARAM_UPLOAD_PROJECTOR_LOG = 25;
    private final int PROJECTOR_LOG_UPLOAD_ON = 1;
    private final int PROJECTOR_LOG_UPLOAD_OFF = 0;
    private final int PARAM_BAND_CONTROL = 22;
    private final int PARAM_SOUNDFORWARD = 5;
    private final int AUDIOTRANSFER_ON = 1;
    private final int AUDIOTRANSFER_OFF = 0;
    private final int PARAM_IMAGE_PROC_TIME_CALLBACK_INTERVAL = 24;

    private static native int NEngAddSearchProjector(D_AddFixedSearchInfo d_AddFixedSearchInfo);

    private static native int NEngChangeDeliveryMethod(boolean z);

    private static native int NEngChangeProtocol(boolean z);

    private static native int NEngClearThumbnail();

    private static native int NEngConnectProjector(ArrayList<D_ConnectPjInfo> arrayList, String str);

    private static native int NEngControlMPPOtherUser(long j, int i);

    private static native int NEngCtlAVMute(int i);

    private static native int NEngCtlChangeSource(int i, int i2);

    private static native int NEngCtlDispQRCode(int i);

    private static native int NEngCtlFreeze(int i);

    private static native int NEngCtlVolumeDown(int i);

    private static native int NEngCtlVolumeUP(int i);

    private static native int NEngDeleteProjectorInfo(int i);

    private static native int NEngDeleteSearchProjector();

    private static native void NEngDestory();

    private static native int NEngDisconnectMPPOtherUser(long j);

    private static native int NEngDisconnectProjector(int i, boolean z);

    private static native int NEngEndSearchPj();

    private static native int NEngGetParameter(int i);

    private static native int NEngGetProjectorStatus(int i);

    private static native int NEngGetResolution(int i, D_ResolutionInfo d_ResolutionInfo);

    private static native int NEngGetSearchStatus();

    private static native int NEngGetSendImageBufferSize(int[] iArr, int[] iArr2);

    private static native int NEngInitialize(EngineJni engineJni, boolean z, String str);

    private static native boolean NEngIsAlivePj();

    private static native boolean NEngIsPlay();

    private static native boolean NEngIsRequestedScreenLock();

    private static native int NEngProjectionMyself();

    private static native int NEngRequestDelivery(boolean z, boolean z2, boolean z3, boolean z4);

    private static native int NEngRequestDeliveryWhitePaper(boolean z, boolean z2, boolean z3, boolean z4);

    private static native int NEngRequestDisplayKeyword(int i);

    private static native int NEngRequestScreenLock(boolean z);

    private static native int NEngRequestThumbnail(int i, int i2, int i3);

    private static native int NEngSendAndReceiveEscvpCommandWithIp(int i, byte[] bArr, int i2, char[] cArr, boolean z);

    private static native int NEngSendEscvp(int i, byte[] bArr, int i2, byte[] bArr2, int i3);

    private static native int NEngSendEscvpCommand(int i, byte[] bArr, int i2);

    private static native int NEngSendEscvpCommandWithIp(int i, byte[] bArr, int i2);

    private static native int NEngSendImage(Bitmap bitmap, Rect[] rectArr, ResRect resRect);

    private static native int NEngSendU2UCommandKeyEmulation(int i, byte[] bArr, int i2);

    private static native int NEngSendU2UCommandNWStandbyON(int i);

    private static native int NEngSetAllowModeratorMonitering(boolean z);

    private static native int NEngSetDeliveryParmission(boolean z, boolean z2, boolean z3);

    private static native int NEngSetMPPControlMode(int i, byte[] bArr, int i2);

    private static native int NEngSetParameter(int i, int i2);

    private static native int NEngSetProjectionMode(int i);

    private static native int NEngSetResolution(int i, boolean z);

    private static native int NEngStartSearchPj(int i);

    private static native int NEngStopThumbnail();

    private static native int NEngUpdateMPPLayout(ArrayList<D_MppLayoutInfo> arrayList);

    private static native int NEngUpdateThumbnail(Bitmap bitmap, Rect[] rectArr);

    /* renamed from: CallBackSendKeySub */
    public void m68x87b6fa79(int i) {
    }

    public boolean IsJNI() {
        return true;
    }

    public void SetCommonRect(int i, int i2) {
    }

    static {
        System.loadLibrary(BuildConfig.STL_NAME);
        System.loadLibrary("engine");
    }

    public EngineJni(Context context, boolean z, String str) {
        this.m_context = context;
        NEngInitialize(this, z, str);
    }

    public int Destroy() {
        LgNP.d("start");
        if (this.m_context != null) {
            this.m_findPjListener = null;
            this.m_pjEventListener = null;
            NEngDestory();
            this.m_context = null;
        }
        LgNP.d("end");
        this.m_nMPPControlMode = -1;
        return 0;
    }

    public int Destroy(int i) {
        LgNP.d("start");
        NEngDisconnectProjector(i, false);
        LgNP.d("end");
        return 0;
    }

    public int StartSearchPj(int i, OnFindPjListener onFindPjListener) {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        if (NEngGetSearchStatus() == -17) {
            throw new EngineException("", -17);
        }
        GetErrorCode = NEngStartSearchPj(0);
        if (GetErrorCode == 0) {
            this.m_findPjListener = onFindPjListener;
        }
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public boolean isEqualFindPjListener(OnFindPjListener onFindPjListener) {
        return this.m_findPjListener == onFindPjListener;
    }

    public int EndSearchPj() {
        int GetErrorCode;
        LgNP.i("EndSearch start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        GetErrorCode = NEngEndSearchPj();
        LgNP.i("EndSearch end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public int AddSearchProjector(D_AddFixedSearchInfo d_AddFixedSearchInfo, D_AddPjInfo d_AddPjInfo) {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        if (NEngGetSearchStatus() == -17) {
            throw new EngineException("", -17);
        }
        GetErrorCode = NEngAddSearchProjector(d_AddFixedSearchInfo);
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public int DeleteProjectorInfo() {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        if (NEngGetSearchStatus() == -17) {
            throw new EngineException("", -17);
        }
        if (NEngGetProjectorStatus(-1) != 0) {
            throw new EngineException("", -18);
        }
        GetErrorCode = NEngDeleteProjectorInfo(-1);
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public void ClearManualSearchFlag() {
        NEngDeleteSearchProjector();
    }

    public void ClearCurrentManualSearchFlag() {
        NEngDeleteSearchProjector();
    }

    public boolean IsAlivePj() {
        return NEngIsAlivePj();
    }

    public int GetSendImageBufferSize(ArrayList<D_ConnectPjInfo> arrayList, int[] iArr) {
        int[] iArr2 = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr2[i] = arrayList.get(i).ProjectorID;
        }
        int NEngGetSendImageBufferSize = NEngGetSendImageBufferSize(iArr2, iArr);
        this.m_nFixW = iArr[0];
        this.m_nFixH = iArr[1];
        return NEngGetSendImageBufferSize;
    }

    public int Connect(ArrayList<D_ConnectPjInfo> arrayList, OnPjEventListener onPjEventListener, String str) {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        if (NEngGetProjectorStatus(-1) != 0) {
            throw new EngineException("", -18);
        }
        GetErrorCode = NEngConnectProjector(arrayList, str);
        if (GetErrorCode == 0) {
            this.m_pjEventListener = onPjEventListener;
        }
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public int Disconnect() {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        GetErrorCode = NEngDisconnectProjector(-1, false);
        this.m_nMPPControlMode = -1;
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public int TerminateSession() {
        int GetErrorCode;
        LgNP.d("start");
        try {
        } catch (EngineException e) {
            GetErrorCode = e.GetErrorCode();
        }
        if (this.m_context == null) {
            throw new EngineException("", -9);
        }
        GetErrorCode = NEngDisconnectProjector(-1, true);
        this.m_nMPPControlMode = -1;
        LgNP.d("end(" + GetErrorCode + ")");
        return GetErrorCode;
    }

    public int GetResolution(int i, D_ResolutionInfo d_ResolutionInfo) {
        return NEngGetResolution(i, d_ResolutionInfo);
    }

    public boolean isEnablePJControl() {
        int i = this.m_nMPPControlMode;
        return (i == -1 || i == 3) ? false : true;
    }

    /* renamed from: com.epson.iprojection.engine.jni.EngineJni$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$engine$common$eSrcType;

        static {
            int[] iArr = new int[eSrcType.values().length];
            $SwitchMap$com$epson$iprojection$engine$common$eSrcType = iArr;
            try {
                iArr[eSrcType.eSourceType_Computer.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$engine$common$eSrcType[eSrcType.eSourceType_Video.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$engine$common$eSrcType[eSrcType.eSourceType_LAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public int Ctl_ChangeSource(int i, eSrcType esrctype) {
        int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$engine$common$eSrcType[esrctype.ordinal()];
        int i3 = 1;
        if (i2 == 1) {
            i3 = 0;
        } else if (i2 != 2) {
            if (i2 != 3) {
                return -5;
            }
            i3 = 2;
        }
        return NEngCtlChangeSource(i, i3);
    }

    public int Ctl_AVMute(int i) {
        return NEngCtlAVMute(i);
    }

    public int Ctl_Freeze(int i) {
        return NEngCtlFreeze(i);
    }

    public int Ctl_VolumeUP(int i) {
        return NEngCtlVolumeUP(i);
    }

    public int Ctl_VolumeDown(int i) {
        return NEngCtlVolumeDown(i);
    }

    public int Ctl_DispQRCode(int i) {
        return NEngCtlDispQRCode(i);
    }

    public int SendU2UCommandNWStandbyON(int i) {
        return NEngSendU2UCommandNWStandbyON(i);
    }

    public int SendU2UCommandKeyEmulation(String str, int i) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            return NEngSendU2UCommandKeyEmulation(i, bytes, bytes.length);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int SendEscvpCommand(int i, String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            return NEngSendEscvpCommand(i, bytes, bytes.length);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int SendEscvpCommandWithIp(int i, String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            return NEngSendEscvpCommandWithIp(i, bytes, bytes.length);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int SendEscvp(int i, String str, String str2) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] bytes2 = str2.getBytes(StandardCharsets.UTF_8);
            return NEngSendEscvp(i, bytes, bytes.length, bytes2, bytes2.length);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int SendAndReceiveEscvpCommandWithIp(int i, String str, char[] cArr, boolean z) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            return NEngSendAndReceiveEscvpCommandWithIp(i, bytes, bytes.length, cArr, z);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int SendImage(Bitmap bitmap, Rect[] rectArr, ResRect resRect) throws BitmapMemoryException {
        this.m_pTempImageFull = bitmap;
        if (NEngIsPlay() && this.m_nMyProjectionState == 1) {
            int NEngSendImage = NEngSendImage(bitmap, rectArr, resRect);
            this.m_resRect = resRect;
            return NEngSendImage;
        }
        int i = this.m_nMPPControlMode;
        if (i == 3 || i == 4) {
            NEngUpdateThumbnail(bitmap, rectArr);
        }
        return 0;
    }

    public int SetResolution(int i, boolean z) {
        return NEngSetResolution(i, z);
    }

    public int SetEncryption(boolean z) {
        return NEngSetParameter(3, z ? 1 : 0);
    }

    public int SetProjectorLogUpload(boolean z) {
        return NEngSetParameter(25, z ? 1 : 0);
    }

    public int SetBandWidth(eBandWidth ebandwidth) {
        return NEngSetParameter(22, ebandwidth.ordinal());
    }

    public int SetAudioTransfer(boolean z) {
        return NEngSetParameter(5, z ? 1 : 0);
    }

    public int SetImageProcNotifyInterval(int i) {
        return NEngSetParameter(24, i);
    }

    public int ProjectionMyself() {
        return NEngProjectionMyself();
    }

    public boolean IsEnableChangeMPPControlMode() {
        int i = this.m_nMPPControlMode;
        return i == 4 || i == 2 || i == 3;
    }

    public int SetMPPControlMode(int i, byte[] bArr) {
        return NEngSetMPPControlMode(i, bArr, bArr != null ? bArr.length : 0);
    }

    public int ControlMPPOtherUser(long j, int i) {
        return NEngControlMPPOtherUser(j, i);
    }

    public int DisconnectMPPOtherUser(long j) {
        return NEngDisconnectMPPOtherUser(j);
    }

    public int SetScreenLockStatus(boolean z) {
        return NEngRequestScreenLock(z);
    }

    public boolean IsSetScreenLock() {
        return NEngIsRequestedScreenLock();
    }

    public void SetProjectionMode(int i) {
        if (i == 2) {
            try {
                SendMuteImage();
                Bitmap bitmap = this.m_pTempImageFull;
                if (bitmap != null) {
                    NEngUpdateThumbnail(bitmap, null);
                }
            } catch (BitmapMemoryException unused) {
            }
            this.m_nMyProjectionState = 2;
        } else if (i == 4) {
            if (this.m_nMyProjectionState == 2) {
                Bitmap bitmap2 = this.m_pTempImageFull;
                if (bitmap2 != null) {
                    NEngSendImage(bitmap2, null, getResRect());
                }
                NEngClearThumbnail();
            }
            this.m_nMyProjectionState = 4;
        } else {
            Bitmap bitmap3 = this.m_pTempImageFull;
            if (bitmap3 != null) {
                NEngSendImage(bitmap3, null, getResRect());
            }
            NEngClearThumbnail();
            this.m_nMyProjectionState = 1;
        }
        NEngSetProjectionMode(i);
    }

    public int GetProjectionMode() {
        return this.m_nMyProjectionState;
    }

    public int UpdateMPPLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        return NEngUpdateMPPLayout(arrayList);
    }

    public int RequestDelivery(boolean z, boolean z2, boolean z3, boolean z4) {
        return NEngRequestDelivery(z, z2, z3, z4);
    }

    public int RequestDeliveryWhitePaper(boolean z, boolean z2, boolean z3, boolean z4) {
        return NEngRequestDeliveryWhitePaper(z, z2, z3, z4);
    }

    public int RequestDeliveryMethod(boolean z) {
        return NEngChangeDeliveryMethod(z);
    }

    public int SetDeliveryParmission(boolean z, boolean z2, boolean z3) {
        return NEngSetDeliveryParmission(z, z2, z3);
    }

    public int RequestThumbnail(int i, int i2, int i3) {
        return NEngRequestThumbnail(i, i2, i3);
    }

    public int StopThumbnail() {
        return NEngStopThumbnail();
    }

    public int SetAllowModeratorMonitering(boolean z) {
        return NEngSetAllowModeratorMonitering(z);
    }

    public int ChangeProtocol(boolean z) {
        return NEngChangeProtocol(z);
    }

    public boolean isMPP() {
        int i = this.m_nMPPControlMode;
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    private void CreateMuteResource() {
        try {
            this.m_bmpMuteFull = BitmapUtils.createBitmap(this.m_nFixW, this.m_nFixH, CommonDefine.BITMAP_CONFIG);
            this.m_bmpIcon = BitmapUtils.decodeResource(this.m_context.getResources(), R.drawable.wait);
            this.m_canvasMute = new Canvas(this.m_bmpMuteFull);
        } catch (BitmapMemoryException unused) {
            Lg.w("BitmapMemoryException occured.");
        }
    }

    private void DeleteMuteResource() {
        this.m_canvasMute = null;
        Bitmap bitmap = this.m_bmpMuteFull;
        if (bitmap != null) {
            bitmap.recycle();
            this.m_bmpMuteFull = null;
        }
    }

    private void SendMuteImage() throws BitmapMemoryException {
        if (this.m_bmpMuteFull == null) {
            CreateMuteResource();
            Canvas canvas = this.m_canvasMute;
            if (canvas != null) {
                canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
            }
            this.m_canvasMute.drawBitmap(this.m_bmpIcon, (this.m_bmpMuteFull.getWidth() - 32) - this.m_bmpIcon.getWidth(), (this.m_bmpMuteFull.getHeight() - 32) - this.m_bmpIcon.getHeight(), (Paint) null);
        }
        NEngSendImage(this.m_bmpMuteFull, null, new ResRect(this.m_bmpMuteFull.getWidth(), this.m_bmpMuteFull.getHeight()));
    }

    public void CallBackResult(final int i, final int i2, final int i3) {
        LgNP.d("start");
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m66x5711ac70(i, i2, i3);
            }
        });
        LgNP.d("end");
    }

    /* renamed from: CallBackResultSub */
    public void m66x5711ac70(int i, int i2, int i3) {
        LgNP.d("start");
        if (this.m_nMPPControlMode == -1 && i2 == 1 && (i3 == 0 || i3 == 2)) {
            this.m_nMPPControlMode = 0;
        }
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onConnectRet(i2, i, i3);
        }
        LgNP.d("end");
    }

    public void CallBackSendKey(final int i) {
        LgNP.d("start");
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m68x87b6fa79(i);
            }
        });
        LgNP.d("end");
    }

    public void CallBackRequestResendImage(final int i) {
        LgNP.d("start");
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m65x1a3beefa(i);
            }
        });
        LgNP.d("end");
    }

    /* renamed from: CallBackRequestResendImageSub */
    public void m65x1a3beefa(int i) {
        Bitmap bitmap = this.m_pTempImageFull;
        if (bitmap != null) {
            NEngSendImage(bitmap, null, getResRect());
        }
        if (i == 2) {
            Bitmap bitmap2 = this.m_pTempImageFull;
            if (bitmap2 != null) {
                NEngUpdateThumbnail(bitmap2, null);
            }
            this.m_nMyProjectionState = 2;
        } else if (i == 4) {
            if (this.m_nMyProjectionState == 2) {
                NEngClearThumbnail();
            }
            this.m_nMyProjectionState = 4;
        } else {
            NEngClearThumbnail();
            this.m_nMyProjectionState = 1;
        }
    }

    public void CallBackRequestWaitImage() {
        LgNP.d("start");
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.CallBackRequestWaitImageSub();
            }
        });
        LgNP.d("end");
    }

    public void CallBackRequestWaitImageSub() {
        if (this.m_pjEventListener != null) {
            try {
                this.m_nMyProjectionState = 2;
                Bitmap bitmap = this.m_pTempImageFull;
                if (bitmap != null) {
                    NEngUpdateThumbnail(bitmap, null);
                }
                SendMuteImage();
            } catch (Exception unused) {
            }
        }
    }

    public void RejectClientResolution(final int i, final ArrayList<D_ClientResolutionInfo> arrayList) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m74x73627259(i, arrayList);
            }
        });
    }

    /* renamed from: RejectClientResolutionSub */
    public void m74x73627259(int i, ArrayList<D_ClientResolutionInfo> arrayList) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onRejectClientResolution(i, arrayList);
        }
    }

    public void AcceptClientResolution(final int i) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m57x82402ef(i);
            }
        });
    }

    /* renamed from: AcceptClientResolutionSub */
    public void m57x82402ef(int i) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onAcceptClientResolution(i);
        }
    }

    public void NotifyUpdatePjInfo(final D_PjInfo d_PjInfo) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m73xffc953d3(d_PjInfo);
            }
        });
    }

    /* renamed from: NotifyUpdatePjInfoSub */
    public void m73xffc953d3(D_PjInfo d_PjInfo) {
        if (this.m_findPjListener == null || d_PjInfo.Status == 9) {
            return;
        }
        this.m_findPjListener.onPjInfo(d_PjInfo);
    }

    public void NotifyUpdateProjectorList() {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.NotifyUpdateProjectorListSub();
            }
        });
    }

    public void NotifyUpdateProjectorListSub() {
        OnFindPjListener onFindPjListener = this.m_findPjListener;
        if (onFindPjListener != null) {
            onFindPjListener.onUpdateProjectorList();
        }
    }

    public void NotifySearchEnd(final int i) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m72xaf12511c(i);
            }
        });
    }

    /* renamed from: NotifySearchEndSub */
    public void m72xaf12511c(int i) {
        OnFindPjListener onFindPjListener = this.m_findPjListener;
        if (onFindPjListener != null) {
            onFindPjListener.onSearchEnd(i);
        }
    }

    public void CallBackMPPControlMode(final int i, final D_MppUserInfo d_MppUserInfo) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m62xb9e950b3(i, d_MppUserInfo);
            }
        });
    }

    /* renamed from: CallBackMPPControlModeSub */
    public void m62xb9e950b3(int i, D_MppUserInfo d_MppUserInfo) {
        Bitmap bitmap;
        if (this.m_nMPPControlMode != i) {
            if (this.m_nMyProjectionState != 1 && (bitmap = this.m_pTempImageFull) != null) {
                NEngSendImage(bitmap, null, getResRect());
            }
            this.m_nMyProjectionState = 1;
            NEngClearThumbnail();
        }
        this.m_nMPPControlMode = i;
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onChangeMppControlMode(i, d_MppUserInfo);
        }
    }

    public void CallBackMPPUserList(final ArrayList<D_MppUserInfo> arrayList, final ArrayList<D_MppLayoutInfo> arrayList2) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m63xbacafdd3(arrayList, arrayList2);
            }
        });
    }

    /* renamed from: CallBackMPPUserListSub */
    public void m63xbacafdd3(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onUpdateMppUserList(arrayList, arrayList2);
        }
    }

    public void CallBackMPPChangedLayout(final ArrayList<D_MppLayoutInfo> arrayList) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m61x514af13(arrayList);
            }
        });
    }

    /* renamed from: CallBackMPPChangedLayoutSub */
    public void m61x514af13(ArrayList<D_MppLayoutInfo> arrayList) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onChangeMppLayout(arrayList);
        }
    }

    public void CallBackScreenLockStatus(final boolean z) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m67x9cd08a3(z);
            }
        });
    }

    /* renamed from: CallBackScreenLockStatusSub */
    public void m67x9cd08a3(boolean z) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onChangeScreenLockStatus(z);
        }
    }

    public void CallBackDelivery(D_DeliveryInfo d_DeliveryInfo) {
        final D_DeliveryInfo d_DeliveryInfo2 = new D_DeliveryInfo(d_DeliveryInfo);
        d_DeliveryInfo.buffer = null;
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m58x6742922d(d_DeliveryInfo2);
            }
        });
    }

    /* renamed from: CallBackDeliverySub */
    public void m58x6742922d(D_DeliveryInfo d_DeliveryInfo) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onReceiveDelivery(d_DeliveryInfo);
        }
    }

    public void CallBackDeliveryError(final D_DeliveryError d_DeliveryError) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m59x3ef03c88(d_DeliveryError);
            }
        });
    }

    /* renamed from: CallBackDeliveryErrorSub */
    public void m59x3ef03c88(D_DeliveryError d_DeliveryError) {
        if (this.m_pjEventListener != null) {
            LgNP.e("in");
            this.m_pjEventListener.onReceiveDeliveryError(d_DeliveryError);
        }
    }

    public void CallBackStartDelivery() {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.CallBackStartDeliverySub();
            }
        });
    }

    public void CallBackStartDeliverySub() {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onStartDelivery();
        }
    }

    public void CallBackEndDelivery() {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.CallBackEndDeliverySub();
            }
        });
    }

    public void CallBackEndDeliverySub() {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onEndDelivery();
        }
    }

    public void CallBackThumbnail(final D_ThumbnailInfo d_ThumbnailInfo) {
        if (d_ThumbnailInfo.buffer != null) {
            d_ThumbnailInfo.bufByte = new byte[d_ThumbnailInfo.buffer.limit()];
            d_ThumbnailInfo.buffer.get(d_ThumbnailInfo.bufByte);
            d_ThumbnailInfo.buffer = null;
            this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    EngineJni.this.m70x5ad2e55f(d_ThumbnailInfo);
                }
            });
            return;
        }
        LgNP.e("buffer is null.");
    }

    /* renamed from: CallBackThumbnailSub */
    public void m70x5ad2e55f(D_ThumbnailInfo d_ThumbnailInfo) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onReceiveThumbnail(d_ThumbnailInfo);
        }
    }

    public void CallBackThumbnailError(final D_ThumbnailError d_ThumbnailError) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m71x83b3b752(d_ThumbnailError);
            }
        });
    }

    /* renamed from: CallBackThumbnailErrorSub */
    public void m71x83b3b752(D_ThumbnailError d_ThumbnailError) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onReceiveThumbnailError(d_ThumbnailError);
        }
    }

    public void CallBackSharedWbPin(final int i, final byte[] bArr) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m69x6816c956(i, bArr);
            }
        });
    }

    /* renamed from: CallBackSharedWbPinSub */
    public void m69x6816c956(int i, byte[] bArr) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onNotifySharedWbPin(i, bArr);
        }
    }

    public void CallBackModeratorPassword(final int i, final boolean z) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m64x3b72ee50(i, z);
            }
        });
    }

    /* renamed from: CallBackModeratorPasswordSub */
    public void m64x3b72ee50(int i, boolean z) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onNotifyModeratorPassword(i, z);
        }
    }

    public void CallBackImageProcTime(final D_ImageProcTime d_ImageProcTime) {
        this.m_handler.post(new Runnable() { // from class: com.epson.iprojection.engine.jni.EngineJni$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                EngineJni.this.m60xad364d2d(d_ImageProcTime);
            }
        });
    }

    /* renamed from: CallBackImageProcTimeSub */
    public void m60xad364d2d(D_ImageProcTime d_ImageProcTime) {
        OnPjEventListener onPjEventListener = this.m_pjEventListener;
        if (onPjEventListener != null) {
            onPjEventListener.onNotifyImageProcTime(d_ImageProcTime);
        }
    }

    public int RequestDisplayKeyword(int i) {
        return NEngRequestDisplayKeyword(i);
    }

    public int GetSearchStatus() {
        return NEngGetSearchStatus();
    }

    private ResRect getResRect() {
        ResRect resRect = this.m_resRect;
        return resRect != null ? resRect : new ResRect(this.m_pTempImageFull.getWidth(), this.m_pTempImageFull.getHeight());
    }
}
