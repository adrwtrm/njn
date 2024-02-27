package com.epson.iprojection.ui.common.singleton.mirroring;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.ui.common.ResRect;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/MirroringUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: MirroringUtils.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/MirroringUtils$Companion;", "", "()V", "getPermissionIntent", "Landroid/content/Intent;", "activity", "Landroid/app/Activity;", "getScreenResolution", "Lcom/epson/iprojection/ui/common/ResRect;", "context", "Landroid/content/Context;", "getVirtualDisplayResolution", "Landroid/util/Size;", "pjResolution", "shouldUseOpenGL", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Intent getPermissionIntent(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Object systemService = activity.getSystemService("media_projection");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
            return ((MediaProjectionManager) systemService).createScreenCaptureIntent();
        }

        public final Size getVirtualDisplayResolution(Context context, Size pjResolution) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(pjResolution, "pjResolution");
            ResRect m191clone = getScreenResolution(context).m191clone();
            Intrinsics.checkNotNullExpressionValue(m191clone, "screenResolution.clone()");
            FitResolution.getRectFitWithIn(m191clone, new ResRect(0, 0, pjResolution.getWidth(), pjResolution.getHeight()));
            return new Size(m191clone.w, m191clone.h);
        }

        public final ResRect getScreenResolution(Context context) {
            int i;
            int i2;
            Intrinsics.checkNotNullParameter(context, "context");
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (Build.VERSION.SDK_INT >= 30) {
                Intrinsics.checkNotNull(windowManager);
                WindowMetrics maximumWindowMetrics = windowManager.getMaximumWindowMetrics();
                Intrinsics.checkNotNullExpressionValue(maximumWindowMetrics, "wm!!.maximumWindowMetrics");
                i = maximumWindowMetrics.getBounds().width();
                i2 = maximumWindowMetrics.getBounds().height();
            } else {
                Intrinsics.checkNotNull(windowManager);
                Display defaultDisplay = windowManager.getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                int i3 = point.x;
                int i4 = point.y;
                i = i3;
                i2 = i4;
            }
            return new ResRect(i, i2);
        }

        public final boolean shouldUseOpenGL(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return ChromeOSUtils.INSTANCE.isChromeOS(context) && Build.VERSION.SDK_INT >= 30;
        }
    }
}
