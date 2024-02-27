package com.epson.iprojection.ui.common.singleton.mirroring;

import android.content.Context;
import android.content.Intent;
import com.epson.iprojection.common.IntentDefine;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.mirroring.Contract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringEntrance.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0006J\u0006\u0010\u0010\u001a\u00020\u000bJ\u0010\u0010\u0011\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0016\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0006J\u0016\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/MirroringEntrance;", "", "()V", "_commander", "Lcom/epson/iprojection/ui/common/singleton/mirroring/Contract$IMirroringServiceCommander;", "<set-?>", "", "isMirroringSwitchOn", "()Z", "isReversingMirroring", "finish", "", "context", "Landroid/content/Context;", "onChangeAudioSettings", "shouldCaptureAudio", "onChangeMPPControlMode", "onDisconnect", "setIsReversingMirroringProperty", "start", "captureIntent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringEntrance {
    public static final MirroringEntrance INSTANCE = new MirroringEntrance();
    private static Contract.IMirroringServiceCommander _commander;
    private static boolean isMirroringSwitchOn;
    private static boolean isReversingMirroring;

    private MirroringEntrance() {
    }

    public final boolean isMirroringSwitchOn() {
        return _commander != null;
    }

    public final boolean isReversingMirroring() {
        return isReversingMirroring;
    }

    public final void start(Context context, Intent captureIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        if (_commander == null) {
            MirroringServiceCommander mirroringServiceCommander = new MirroringServiceCommander();
            _commander = mirroringServiceCommander;
            mirroringServiceCommander.start(context, captureIntent);
            context.sendBroadcast(new Intent(IntentDefine.INTENT_ACTION_MIRRORING_ON));
        }
    }

    public final void finish(Context context) {
        Contract.IMirroringServiceCommander iMirroringServiceCommander = _commander;
        if (iMirroringServiceCommander != null) {
            iMirroringServiceCommander.finish();
        }
        if (_commander != null) {
            Intent intent = new Intent(IntentDefine.INTENT_ACTION_MIRRORING_OFF);
            if (context != null) {
                context.sendBroadcast(intent);
            }
        }
        _commander = null;
    }

    public final void onDisconnect(Context context) {
        finish(context);
    }

    public final void onChangeMPPControlMode() {
        Contract.IMirroringServiceCommander iMirroringServiceCommander = _commander;
        if (iMirroringServiceCommander != null) {
            iMirroringServiceCommander.onChangeMPPControlMode();
        }
    }

    public final void onChangeAudioSettings(boolean z) {
        Contract.IMirroringServiceCommander iMirroringServiceCommander = _commander;
        if (iMirroringServiceCommander != null) {
            iMirroringServiceCommander.onChangeAudioSettings(z);
        }
    }

    public final void setIsReversingMirroringProperty(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        isReversingMirroring = z;
        PrefUtils.write(context, PrefTagDefine.IS_REVERSING_MIRRORING, z);
    }
}
