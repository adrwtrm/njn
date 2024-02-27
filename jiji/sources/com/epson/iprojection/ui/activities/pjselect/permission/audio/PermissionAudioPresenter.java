package com.epson.iprojection.ui.activities.pjselect.permission.audio;

import android.content.Context;
import android.os.Build;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.permision.PermissionContract;
import com.epson.iprojection.ui.common.permision.PermissionPresenter;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionAudioPresenter.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006H\u0016J\u0013\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\nJ\u001b\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/permission/audio/PermissionAudioPresenter;", "Lcom/epson/iprojection/ui/common/permision/PermissionPresenter;", "view", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "(Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;)V", "getNextActivityClass", "Ljava/lang/Class;", "getPermissions", "", "", "()[Ljava/lang/String;", "onDeniedWithNeverAsk", "", "permissions", "([Ljava/lang/String;)V", "shouldStartSettingsActivityOnRefusedNeverAsk", "", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionAudioPresenter extends PermissionPresenter {
    public static final Companion Companion = new Companion(null);
    public static final String permission = "android.permission.RECORD_AUDIO";

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public Class<?> getNextActivityClass() {
        return null;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public boolean shouldStartSettingsActivityOnRefusedNeverAsk() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionAudioPresenter(PermissionContract.View view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public String[] getPermissions() {
        return new String[]{permission};
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter, com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onDeniedWithNeverAsk(String[] permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        PrefUtils.write(getMView().getContext(), PrefTagDefine.PERMITTION_NEVER_ASK_RECORD_AUDIO, "hoge");
        super.onDeniedWithNeverAsk(permissions);
    }

    /* compiled from: PermissionAudioPresenter.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/permission/audio/PermissionAudioPresenter$Companion;", "", "()V", "permission", "", "haveDeniedWithNeverAsk", "", "context", "Landroid/content/Context;", "shouldGetPermission", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean shouldGetPermission(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (Build.VERSION.SDK_INT >= 29 && PrefUtils.readInt(context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG) == 1) {
                int readInt = PrefUtils.readInt(context, PrefTagDefine.conPJ_BAND_WIDTH);
                if (readInt == Integer.MIN_VALUE || !(eBandWidth.values()[readInt] == eBandWidth.e256K || eBandWidth.values()[readInt] == eBandWidth.e512K)) {
                    if ((Pj.getIns().getNowConnectingPJList() == null || Pj.getIns().getNowConnectingPJList().size() <= 1) && context.checkSelfPermission(PermissionAudioPresenter.permission) != 0) {
                        return !haveDeniedWithNeverAsk(context);
                    }
                    return false;
                }
                return false;
            }
            return false;
        }

        private final boolean haveDeniedWithNeverAsk(Context context) {
            return PrefUtils.read(context, PrefTagDefine.PERMITTION_NEVER_ASK_RECORD_AUDIO) != null;
        }
    }
}
