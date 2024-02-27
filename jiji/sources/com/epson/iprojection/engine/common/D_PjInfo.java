package com.epson.iprojection.engine.common;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class D_PjInfo {
    public static final byte[] e_null_unique_info = {0, 0, 0, 0, 0, 0};
    public static final int e_proj_disp_busy = 4;
    public static final int e_proj_disp_disable = 1;
    public static final int e_proj_disp_enable = 2;
    public static final int e_proj_disp_in_mirror = 5;
    public static final int e_proj_disp_in_use = 3;
    public static final int e_proj_disp_notfound = 0;
    public int ProjectorID = -1;
    public boolean bNew = false;
    public String PrjName = "";
    public byte[] IPAddr = new byte[4];
    public byte[] UniqInfo = new byte[6];
    public int Status = 0;
    public boolean isNeededPjKeyword = false;
    public boolean isSupportedScreenType = false;
    public int screenTypeAspectRatioW = -1;
    public int screenTypeAspectRatioH = -1;
    public boolean bSupportedMPP = false;
    public int nSupportMinMPPVersion = 0;
    public int nSupportMaxMPPVersion = 0;
    public int nSupportMaxUserNum = 0;
    public boolean bEnableMPPInterruptToNP = false;
    public int nCurrentMPPVersion = 0;
    public int nCurrentMPPMaxUserNum = 0;
    public int nCurrentMPPCurrentUserNum = 0;
    public boolean bQRConnect = false;
    public boolean isSmartphoneRemote = false;
    public boolean isSupportWebRemote = false;
    public boolean isNotSupportNP = false;
    public boolean isSupportDisplayKeyword = false;
    public boolean isSupportConnectionIdentifier = false;
    public boolean hasScomport = false;
    public boolean isSignageMode = false;
    public boolean isStandbySetting = false;
    public boolean isNetworkPathWireless = false;
    public boolean isMppThumbnail = false;
    public boolean isMppDelivery = false;
    public boolean hasModeratorPassword = false;
    public boolean isSupportSharedWB = false;
    public byte[] sharedWbPin = null;
    public boolean isSupportedSecuredEscvp = false;
    public boolean isSupportedAudio = false;
    public boolean isSupportedAudioQuantize_16bit = false;
    public boolean isSupportedAudioChannel_Monaural = false;
    public boolean isSupportedAudioFrequency_22050 = false;
    public boolean isSupportedWebRTC = false;
    public ArrayList<D_MirrorPjInfo> aMirrorPjList = new ArrayList<>();
    public int nDispStatus = 2;
    public boolean bSelected = false;
    public boolean bManualFound = false;
    public boolean bCurrentManualFound = false;

    /* loaded from: classes.dex */
    public enum PjListType implements Serializable {
        LIST_PJ_TYPE_NONE,
        LIST_PJ_TYPE_BUSINESS,
        LIST_PJ_TYPE_HOME,
        LIST_PJ_TYPE_SIGNAGE
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum PjType implements Serializable {
        PJ_TYPE_BUSINESS,
        PJ_TYPE_HOME,
        PJ_TYPE_SIGNAGE
    }

    public String statusDescription(int i) {
        return i == 0 ? "プロジェクタが見つからない" : i == 1 ? "プロジェクタ未使用" : i == 2 ? "NP接続中" : i == 3 ? "割り込み禁止状態で使用中" : i == 4 ? "MPC接続中" : i == 5 ? "MPC接続中(ミラーリング中)" : i == 6 ? "MPC接続中(最大数参加中)" : i == 7 ? "MPC接続中(ミラーリング最大数参加中)" : i == 8 ? "他アプリ使用中" : i == 9 ? "検索中" : "N/A";
    }

    public String getStatusDescription() {
        return statusDescription(this.Status);
    }

    public String getDisplayStatus() {
        int i = this.nDispStatus;
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "？？？" : "ミラーリング中(選択可)" : "使用中(選択不可)" : "使用中(選択可)" : "選択可能" : "選択不可" : "ない";
    }

    public String toString() {
        return ((((((("" + String.format("ProjectorID=%d, ", Integer.valueOf(this.ProjectorID))) + String.format("PrjName=%s, ", this.PrjName)) + String.format("IPAddr=%03d.%03d.%03d.%03d, ", Byte.valueOf(this.IPAddr[0]), Byte.valueOf(this.IPAddr[1]), Byte.valueOf(this.IPAddr[2]), Byte.valueOf(this.IPAddr[3]))) + String.format("Status=%s, ", statusDescription(this.Status))) + String.format("isNeededPjKeyword=%b, ", Boolean.valueOf(this.isNeededPjKeyword))) + String.format("bNew=%b, ", Boolean.valueOf(this.bNew))) + String.format("select=%b, ", Boolean.valueOf(this.bSelected))) + String.format("display=%s, ", getDisplayStatus());
    }

    public boolean isShowable() {
        return this.Status != 0;
    }

    public void setNoInterruptStatus() {
        this.Status = 3;
    }

    public boolean SetDispInfo(ArrayList<D_PjInfo> arrayList, int i, boolean z, boolean z2) {
        boolean z3;
        int i2;
        boolean z4;
        boolean z5;
        int i3 = this.nDispStatus;
        boolean z6 = this.bSelected;
        this.bSelected = false;
        if (arrayList == null || arrayList.size() == 0) {
            i2 = -1;
            z3 = true;
        } else {
            z3 = true;
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                D_PjInfo d_PjInfo = arrayList.get(i4);
                int i5 = this.ProjectorID;
                if (i5 == -1) {
                    boolean equals = Arrays.equals(this.IPAddr, d_PjInfo.IPAddr);
                    boolean equals2 = d_PjInfo.PrjName.equals(this.PrjName);
                    if (Arrays.equals(e_null_unique_info, this.UniqInfo)) {
                        z5 = false;
                        z4 = true;
                    } else if (Arrays.equals(d_PjInfo.UniqInfo, this.UniqInfo)) {
                        z4 = false;
                        z5 = true;
                    } else {
                        z4 = false;
                        z5 = false;
                    }
                    if (equals2 && equals && (z4 || z5)) {
                        this.bSelected = true;
                    }
                } else if (i5 == d_PjInfo.ProjectorID) {
                    this.bSelected = true;
                }
                if (!d_PjInfo.isEnableModerator()) {
                    z3 = false;
                }
            }
            i2 = i;
        }
        switch (this.Status) {
            case 1:
                if (i2 != -1 && i2 != 1) {
                    if (i2 == 2) {
                        if (this.isNotSupportNP && !z3) {
                            this.nDispStatus = 1;
                            break;
                        } else {
                            this.nDispStatus = 2;
                            break;
                        }
                    } else {
                        this.nDispStatus = 1;
                        break;
                    }
                } else {
                    this.nDispStatus = 2;
                    break;
                }
            case 2:
                if (i2 == -1 || i2 == 1 || i2 == 2) {
                    this.nDispStatus = 3;
                } else {
                    this.nDispStatus = 4;
                }
                if (containNotSupportNpPj(arrayList) && !isEnableModerator()) {
                    this.nDispStatus = 1;
                    break;
                }
                break;
            case 3:
            case 8:
                this.nDispStatus = 4;
                break;
            case 4:
            case 6:
                if (!z || isSomeoneEnableModerator(arrayList)) {
                    if (i2 != -1) {
                        if (i2 != 1) {
                            if (i2 == 4 || i2 == 6) {
                                if (this.bSelected) {
                                    this.nDispStatus = 3;
                                    break;
                                } else {
                                    this.nDispStatus = 4;
                                    break;
                                }
                            } else {
                                this.nDispStatus = 4;
                                break;
                            }
                        } else {
                            this.nDispStatus = 4;
                            break;
                        }
                    } else {
                        this.nDispStatus = 3;
                        break;
                    }
                } else {
                    this.nDispStatus = 4;
                    break;
                }
                break;
            case 5:
            case 7:
                if (!z || isSomeoneEnableModerator(arrayList)) {
                    if (i2 != -1) {
                        if (i2 != 1) {
                            if (i2 == 5 || i2 == 7) {
                                if (this.bSelected) {
                                    this.nDispStatus = 5;
                                    break;
                                } else if (arrayList.get(0).isSameMirroringMPP(this)) {
                                    this.nDispStatus = 5;
                                    if (z2 || Pj.getIns().isAllPjTypeBusiness()) {
                                        this.bSelected = true;
                                        break;
                                    }
                                } else {
                                    this.nDispStatus = 4;
                                    break;
                                }
                            } else {
                                this.nDispStatus = 4;
                                break;
                            }
                        } else {
                            this.nDispStatus = 4;
                            break;
                        }
                    } else {
                        this.nDispStatus = 5;
                        break;
                    }
                } else {
                    this.nDispStatus = 4;
                    break;
                }
                break;
            case 9:
                this.nDispStatus = 1;
                break;
            default:
                this.nDispStatus = 0;
                break;
        }
        if (!this.bSelected) {
            if (!isPjListTypeBusiness(arrayList) && !isPjTypeBusiness()) {
                if (arrayList.size() >= 16) {
                    this.nDispStatus = 1;
                }
            } else if (arrayList.size() >= 4) {
                this.nDispStatus = 1;
            }
        }
        if (isPjListTypeBusiness(arrayList)) {
            if (isDisableCombination(arrayList)) {
                this.nDispStatus = 1;
                this.bSelected = false;
            }
            int i6 = this.nDispStatus;
            if (i6 == 1 || i6 == 4 || i6 == 0) {
                this.bSelected = false;
            }
        }
        if (isPjTypeBusiness()) {
            Iterator<D_PjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                int i7 = it.next().nDispStatus;
                if (i7 == 1 || i7 == 4) {
                    this.nDispStatus = 1;
                    this.bSelected = false;
                } else if (i7 == 0) {
                    this.nDispStatus = 1;
                    this.bSelected = false;
                }
            }
        }
        return (i3 == this.nDispStatus && z6 == this.bSelected) ? false : true;
    }

    private boolean containNotSupportNpPj(ArrayList<D_PjInfo> arrayList) {
        Iterator<D_PjInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if (it.next().isNotSupportNP) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameMirroringMPP(D_PjInfo d_PjInfo) {
        ArrayList<D_MirrorPjInfo> arrayList = d_PjInfo.aMirrorPjList;
        if (arrayList == null && this.aMirrorPjList == null) {
            return true;
        }
        if (arrayList == null || this.aMirrorPjList != null) {
            if ((arrayList != null || this.aMirrorPjList == null) && arrayList.size() == this.aMirrorPjList.size()) {
                if (d_PjInfo.aMirrorPjList.size() == 0) {
                    return true;
                }
                for (int i = 0; i < d_PjInfo.aMirrorPjList.size(); i++) {
                    D_MirrorPjInfo d_MirrorPjInfo = d_PjInfo.aMirrorPjList.get(i);
                    D_MirrorPjInfo d_MirrorPjInfo2 = this.aMirrorPjList.get(i);
                    if (!Arrays.equals(d_MirrorPjInfo.ipAddress, d_MirrorPjInfo2.ipAddress) || !d_MirrorPjInfo.prjName.equals(d_MirrorPjInfo2.prjName) || !Arrays.equals(d_MirrorPjInfo.uniqInfo, d_MirrorPjInfo2.uniqInfo)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isMPP() {
        int i = this.Status;
        return 4 == i || 6 == i || 5 == i || 7 == i;
    }

    public boolean isMppMirror() {
        int i = this.Status;
        return 5 == i || 7 == i;
    }

    public boolean isEnableModerator() {
        if (this.bSupportedMPP) {
            return this.nSupportMinMPPVersion > 0 || this.nCurrentMPPVersion > 0;
        }
        return false;
    }

    public boolean isDisableCombination(ArrayList<D_PjInfo> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            if (this.isNotSupportNP) {
                Iterator<D_PjInfo> it = arrayList.iterator();
                while (it.hasNext()) {
                    if (!it.next().bSupportedMPP) {
                        return true;
                    }
                }
            } else if (!this.bSupportedMPP) {
                Iterator<D_PjInfo> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    if (it2.next().isNotSupportNP) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isSomeoneEnableModerator(ArrayList<D_PjInfo> arrayList) {
        if (isEnableModerator()) {
            return true;
        }
        if (arrayList != null) {
            Iterator<D_PjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().isEnableModerator()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isPjTypeSignage() {
        return getPjType() == PjType.PJ_TYPE_SIGNAGE;
    }

    public boolean isPjTypeHome() {
        return getPjType() == PjType.PJ_TYPE_HOME;
    }

    public boolean isPjTypeBusiness() {
        return getPjType() == PjType.PJ_TYPE_BUSINESS;
    }

    private PjType getPjType() {
        if (this.isSignageMode) {
            return PjType.PJ_TYPE_SIGNAGE;
        }
        if (this.isSmartphoneRemote) {
            return PjType.PJ_TYPE_HOME;
        }
        return PjType.PJ_TYPE_BUSINESS;
    }

    public static boolean isPjListTypeNone(ArrayList<D_PjInfo> arrayList) {
        return getPjListType(arrayList) == PjListType.LIST_PJ_TYPE_NONE;
    }

    public static boolean isPjListTypeSignage(ArrayList<D_PjInfo> arrayList) {
        return getPjListType(arrayList) == PjListType.LIST_PJ_TYPE_SIGNAGE;
    }

    public static boolean isPjListTypeHome(ArrayList<D_PjInfo> arrayList) {
        return getPjListType(arrayList) == PjListType.LIST_PJ_TYPE_HOME;
    }

    public static boolean isPjListTypeBusiness(ArrayList<D_PjInfo> arrayList) {
        return getPjListType(arrayList) == PjListType.LIST_PJ_TYPE_BUSINESS;
    }

    public static PjListType getPjListType(ArrayList<D_PjInfo> arrayList) {
        PjListType pjListType = PjListType.LIST_PJ_TYPE_NONE;
        if (arrayList != null) {
            Iterator<D_PjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                PjType pjType = it.next().getPjType();
                if (pjType == PjType.PJ_TYPE_BUSINESS) {
                    return PjListType.LIST_PJ_TYPE_BUSINESS;
                }
                if (pjType == PjType.PJ_TYPE_HOME) {
                    pjListType = PjListType.LIST_PJ_TYPE_HOME;
                } else if (pjType == PjType.PJ_TYPE_SIGNAGE && pjListType == PjListType.LIST_PJ_TYPE_NONE) {
                    pjListType = PjListType.LIST_PJ_TYPE_SIGNAGE;
                }
            }
            return pjListType;
        }
        return pjListType;
    }

    public String getStatusString(Context context) {
        if (isBusy()) {
            return context.getString(R.string._Busy_);
        }
        if (isInUse()) {
            return context.getString(R.string._InUse_);
        }
        return isMirroring() ? context.getString(R.string._Mirroring_) : "";
    }

    public boolean isBusy() {
        int i = this.Status;
        return i == 3 || i == 10 || i == 6 || i == 7 || i == 8;
    }

    public boolean isInUse() {
        int i = this.Status;
        return i == 2 || i == 4;
    }

    public boolean isMirroring() {
        return this.Status == 5;
    }

    public boolean isNotSupportMPP() {
        return !this.bSupportedMPP;
    }
}
