package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.NetworkInfoUtilsKt;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;

/* loaded from: classes.dex */
public class MessageDialog extends BaseHaveButtonDialog {
    private static boolean _isModeratorMenu = false;

    /* loaded from: classes.dex */
    public enum MessageType {
        ConnectNG,
        IlligalIP,
        Disconnect,
        DisconnectFromPj,
        NotFound,
        Interrupted,
        NetworkErr,
        NpVersionErr,
        DisconnectOnlyOne,
        MppMaxUser,
        DisconnectOther,
        DiffCombiPJ,
        DisconnectAdmin,
        NotFoundAnything,
        AlreadyModerator,
        Nfc_AlreadyConnect,
        Nfc_NoWirelessLanUnit,
        Nfc_WirelessModeOff,
        Nfc_CheckNetworkSetting,
        Standby,
        IncludeUnavailable
    }

    private static String getTitle(Context context, MessageType messageType) {
        return null;
    }

    public MessageDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, getTitle(context, messageType), getMessage(context, messageType), new String[]{context.getString(R.string._OK_)}, resultAction);
    }

    public MessageDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction, String str) {
        super(context, iOnDialogEventListener, getTitle(context, messageType), getMessage(context, messageType, str), new String[]{context.getString(R.string._OK_)}, resultAction);
    }

    public MessageDialog(Context context, String str, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, str, new String[]{context.getString(R.string._OK_)}, resultAction);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType = iArr;
            try {
                iArr[MessageType.ConnectNG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.IlligalIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Disconnect.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.DisconnectFromPj.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.NotFound.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Interrupted.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.NetworkErr.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.NpVersionErr.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.MppMaxUser.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.DisconnectOther.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.DiffCombiPJ.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.DisconnectAdmin.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.NotFoundAnything.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.AlreadyModerator.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Nfc_AlreadyConnect.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Nfc_NoWirelessLanUnit.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Nfc_WirelessModeOff.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Nfc_CheckNetworkSetting.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.Standby.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[MessageType.IncludeUnavailable.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    public static String getMessage(Context context, MessageType messageType) {
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$MessageDialog$MessageType[messageType.ordinal()]) {
            case 1:
                if (NetworkInfoUtilsKt.isActiveNetworkMobile(context)) {
                    return context.getString(R.string._ConnectFail_) + context.getString(R.string._ConnectNGForMobile_);
                }
                return context.getString(R.string._ConnectFail_);
            case 2:
                return context.getString(R.string._IllegalIPAddress_);
            case 3:
                return context.getString(R.string._DisconnectFromiProjection_);
            case 4:
                return context.getString(R.string._DisconnectEvent_);
            case 5:
                return context.getString(R.string._NoPjFound_);
            case 6:
                return context.getString(R.string._InterruptedAnotherUser_);
            case 7:
                return context.getString(R.string._NetworkError_);
            case 8:
                return context.getString(R.string._EasyMPNotSupportVer_);
            case 9:
                return context.getString(R.string._ErrMaxUser_);
            case 10:
                return context.getString(R.string._DisconnectedOther_);
            case 11:
                return context.getString(R.string._ErrMirroringAnother_);
            case 12:
                return context.getString(R.string._DisconnectedAdmin_);
            case 13:
                return context.getString(R.string._NotFoundAnything_);
            case 14:
                if (_isModeratorMenu) {
                    return context.getString(R.string._CannotBecomeModerator_);
                }
                return context.getString(R.string._AlreadyExistModerator_);
            case 15:
                return context.getString(R.string._NFCAlreadyConnected_);
            case 16:
                return context.getString(R.string._NFCNoWirelessLanUnit_);
            case 17:
                return context.getString(R.string._NFCWirelessModeOff_);
            case 18:
                return context.getString(R.string._NFCCheckNetworkSetting_);
            case 19:
                if (NetworkInfoUtilsKt.isActiveNetworkMobile(context)) {
                    return context.getString(R.string._ConnectNGPre_) + context.getString(R.string._ConnectNGForMobile_);
                }
                return context.getString(R.string._ConnectNGPre_) + context.getString(R.string._ConnectNGPowerOff_);
            case 20:
                if (NetworkInfoUtilsKt.isActiveNetworkMobile(context)) {
                    return context.getString(R.string._ConnectNGPre_) + context.getString(R.string._ConnectNGForMobile_);
                }
                return context.getString(R.string._ConnectNGPre_) + context.getString(R.string._ConnectNGIncludeUnavailable_);
            default:
                return null;
        }
    }

    private static String getMessage(Context context, MessageType messageType, String str) {
        if (messageType == MessageType.DisconnectOnlyOne) {
            return context.getString(R.string._DisconnectEventWithName_) + " " + str;
        }
        return getMessage(context, messageType);
    }

    public static void setIsModeratorMenu(boolean z) {
        _isModeratorMenu = z;
    }

    public static boolean isModeratorMenu() {
        return _isModeratorMenu;
    }
}
