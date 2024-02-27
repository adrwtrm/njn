package com.epson.iprojection.common.utils;

import android.content.Context;
import android.provider.Settings;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OverlayUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/common/utils/OverlayUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OverlayUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: OverlayUtils.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/common/utils/OverlayUtils$Companion;", "", "()V", "shouldShowBlinkPixelViewOnOverlay", "", "context", "Landroid/content/Context;", "shouldShowOverlayTutorial", "shouldShowOverlayTutorialForOpenGL", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean shouldShowOverlayTutorial(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return PrefUtils.readInt(context, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG) == 1 && Pj.getIns().isConnected() && Pj.getIns().isEnableMppDelivery() && Pj.getIns().isModerator() && !Settings.canDrawOverlays(context);
        }

        public final boolean shouldShowOverlayTutorialForOpenGL(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (Settings.canDrawOverlays(context)) {
                return false;
            }
            return shouldShowBlinkPixelViewOnOverlay(context);
        }

        public final boolean shouldShowBlinkPixelViewOnOverlay(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return MirroringUtils.Companion.shouldUseOpenGL(context);
        }
    }
}
