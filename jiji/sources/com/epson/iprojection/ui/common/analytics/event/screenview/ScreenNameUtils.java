package com.epson.iprojection.ui.common.analytics.event.screenview;

import android.os.Bundle;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.drawermenu.DrawerSelectStatus;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: ScreenNameUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/screenview/ScreenNameUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ScreenNameUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: ScreenNameUtils.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\b\u0010\u0007\u001a\u00020\u0004H\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/screenview/ScreenNameUtils$Companion;", "", "()V", "createScreenName", "", "className", "categoryName", "createStateMenuName", "send", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "screenName", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String createScreenName(String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format("%s_%s", Arrays.copyOf(new Object[]{createStateMenuName(), ScreenNameGetter.getScreenName(className)}, 2));
            Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
            return format;
        }

        public final String createScreenName(String categoryName, String className) {
            Intrinsics.checkNotNullParameter(categoryName, "categoryName");
            Intrinsics.checkNotNullParameter(className, "className");
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format("%s_%s", Arrays.copyOf(new Object[]{categoryName, ScreenNameGetter.getScreenName(className)}, 2));
            Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
            return format;
        }

        private final String createStateMenuName() {
            if (DrawerSelectStatus.getIns().get() == null) {
                return "なし";
            }
            String menuName = DrawerSelectStatus.getIns().get().getMenuName();
            Intrinsics.checkNotNullExpressionValue(menuName, "getIns().get().menuName");
            return menuName;
        }

        public final void send(FirebaseAnalytics firebaseAnalytics, String screenName) {
            Intrinsics.checkNotNullParameter(firebaseAnalytics, "firebaseAnalytics");
            Intrinsics.checkNotNullParameter(screenName, "screenName");
            Bundle bundle = new Bundle();
            Lg.d("firebase _screenName = " + screenName);
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
            bundle.putString(eCustomDimension.SCREEN_NAME.getDimensionName(), screenName);
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        }
    }
}
