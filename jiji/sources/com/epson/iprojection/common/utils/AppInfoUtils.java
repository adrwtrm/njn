package com.epson.iprojection.common.utils;

import android.content.Context;
import com.epson.iprojection.common.DataStoreRepository;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;

/* compiled from: AppInfoUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/common/utils/AppInfoUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AppInfoUtils {
    public static final Companion Companion = new Companion(null);
    private static String launchMethod = "";

    /* compiled from: AppInfoUtils.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004J\u001e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004J\u0016\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/common/utils/AppInfoUtils$Companion;", "", "()V", "launchMethod", "", "getLaunchMethod", "()Ljava/lang/String;", "setLaunchMethod", "(Ljava/lang/String;)V", "containsPAorPBin", "", "str", "extractFrontNumber", "getAppVersion", "context", "Landroid/content/Context;", "getAppVersionInStore", "repository", "Lcom/epson/iprojection/common/DataStoreRepository;", "getInstallInfo", "isNewInstall", "storeVersion", "isThisVersionFC", "isUpdateInstall", "myVersion", "updateAppVersion", "", "appVersion", "updateInstallInfo", "installInfo", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getLaunchMethod() {
            return AppInfoUtils.launchMethod;
        }

        public final void setLaunchMethod(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            AppInfoUtils.launchMethod = str;
        }

        public final String getAppVersion(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 128).versionName;
            Intrinsics.checkNotNullExpressionValue(str, "context.packageManager.g…            ).versionName");
            return str;
        }

        public final String extractFrontNumber(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            String replace$default = StringsKt.replace$default(StringsKt.replace$default(str, ".", "", false, 4, (Object) null), " ", "", false, 4, (Object) null);
            int indexOf$default = StringsKt.indexOf$default((CharSequence) replace$default, "(", 0, false, 6, (Object) null);
            if (indexOf$default == -1) {
                return replace$default;
            }
            String substring = replace$default.substring(0, indexOf$default);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
            return substring;
        }

        public final boolean isThisVersionFC(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return !containsPAorPBin(getAppVersion(context));
        }

        public final boolean containsPAorPBin(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
            String upperCase = str.toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(locale)");
            String str2 = upperCase;
            return StringsKt.contains$default((CharSequence) str2, (CharSequence) "PA", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "PB", false, 2, (Object) null);
        }

        public final boolean isNewInstall(String storeVersion) {
            Intrinsics.checkNotNullParameter(storeVersion, "storeVersion");
            return storeVersion.length() == 0;
        }

        public final boolean isUpdateInstall(String storeVersion, String myVersion) {
            Intrinsics.checkNotNullParameter(storeVersion, "storeVersion");
            Intrinsics.checkNotNullParameter(myVersion, "myVersion");
            return (isNewInstall(storeVersion) || StringsKt.split$default((CharSequence) storeVersion, new String[]{":"}, false, 0, 6, (Object) null).contains(myVersion)) ? false : true;
        }

        public final void updateAppVersion(DataStoreRepository repository, String appVersion, String storeVersion) {
            Intrinsics.checkNotNullParameter(repository, "repository");
            Intrinsics.checkNotNullParameter(appVersion, "appVersion");
            Intrinsics.checkNotNullParameter(storeVersion, "storeVersion");
            BuildersKt__BuildersKt.runBlocking$default(null, new AppInfoUtils$Companion$updateAppVersion$1(repository, appVersion, storeVersion, null), 1, null);
        }

        public final String getAppVersionInStore(DataStoreRepository repository) {
            Object runBlocking$default;
            Intrinsics.checkNotNullParameter(repository, "repository");
            runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new AppInfoUtils$Companion$getAppVersionInStore$1(repository, null), 1, null);
            return (String) runBlocking$default;
        }

        public final void updateInstallInfo(DataStoreRepository repository, String installInfo) {
            Intrinsics.checkNotNullParameter(repository, "repository");
            Intrinsics.checkNotNullParameter(installInfo, "installInfo");
            BuildersKt__BuildersKt.runBlocking$default(null, new AppInfoUtils$Companion$updateInstallInfo$1(repository, installInfo, null), 1, null);
        }

        public final String getInstallInfo(DataStoreRepository repository) {
            Object runBlocking$default;
            Intrinsics.checkNotNullParameter(repository, "repository");
            runBlocking$default = BuildersKt__BuildersKt.runBlocking$default(null, new AppInfoUtils$Companion$getInstallInfo$1(repository, null), 1, null);
            return (String) runBlocking$default;
        }
    }
}
