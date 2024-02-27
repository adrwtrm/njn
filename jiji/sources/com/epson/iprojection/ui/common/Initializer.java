package com.epson.iprojection.ui.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.epson.iprojection.common.AppDataStore;
import com.epson.iprojection.common.DataStoreRepository;
import com.epson.iprojection.common.EInstall;
import com.epson.iprojection.common.utils.AppInfoUtils;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.UUID;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.ui.activities.delivery.DeliveryFileIO9orLess;
import com.epson.iprojection.ui.activities.start.IntentStreamFile;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;

/* loaded from: classes.dex */
public class Initializer {
    public static void initialize(Context context) {
        UUID.INSTANCE.setUUID(context);
        PathGetter.getIns().initialize(context);
        removeTempFiles(context);
        registerDeviceName(context);
        createDeliveryImageDirectory(context);
        initAppInfo(context);
        initUseBandWidthOfPreferenceForGA(context);
        initUsePjLog(context);
        initOptimiseUltraWideSPS2(context);
        initOverlayDeliveryButtonSettings(context);
        initAudioTransferButtonSettings(context);
        initAutoDisplayDeliveryImage(context);
        initIsReversingMirroringProperty(context);
    }

    private static void createDeliveryImageDirectory(Context context) {
        if (Build.VERSION.SDK_INT < 29) {
            FileUtils.mkDirectory(new DeliveryFileIO9orLess().getPath(context));
        }
    }

    public static void removeTempFiles(Context context) {
        String[] dirPathAll;
        if (PathGetter.getIns().isAvailableExternalStorage(context)) {
            if (!FileUtils.mkDirectory(PathGetter.getIns().getCacheDirPath())) {
                FileUtils.cleanUpDirs(PathGetter.getIns().getCacheDirPath());
            }
            for (String str : IntentStreamFile.getDirPathAll()) {
                if (!FileUtils.mkDirectory(str)) {
                    FileUtils.cleanUpDirs(str);
                }
            }
            if (FileUtils.mkDirectory(PathGetter.getIns().getUserDirPath())) {
                return;
            }
            FileUtils.deleteListData(XmlUtils.listFilter(XmlUtils.filterType.IGNORE, XmlUtils.place.APPS));
        }
    }

    public static void importFiles(Context context) {
        XmlUtils.transportXmlUserToApps();
        XmlUtils.transportSettingsUserToApps();
    }

    public static void importSharedProfile(Context context) {
        String read = PrefUtils.read(context, PrefTagDefine.conPJ_CONFIG_SHAREDPROFILEPATH_TAG);
        if (read != null && !read.equals("")) {
            XmlUtils.transportXmlHttpToApps(read);
        } else {
            XmlUtils.deleteProfileMasterData(XmlUtils.filterType.SHMPLIST);
        }
    }

    public static void registerDeviceName(Context context) {
        String read = PrefUtils.read(context, PrefTagDefine.conPJ_CONFIG_USERNAME_TAG);
        if (read == null || read.equals("")) {
            String str = Build.MODEL;
            PrefUtils.write(context, PrefTagDefine.conPJ_CONFIG_USERNAME_TAG, str.substring(0, Math.min(str.length(), 32)), (SharedPreferences.Editor) null);
        }
    }

    private static void initAppInfo(final Context context) {
        final DataStoreRepository dataStoreRepository = new DataStoreRepository(AppDataStore.Companion.provideDataStore(context));
        new Runnable() { // from class: com.epson.iprojection.ui.common.Initializer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Initializer.lambda$initAppInfo$0(dataStoreRepository, context);
            }
        }.run();
    }

    public static /* synthetic */ void lambda$initAppInfo$0(DataStoreRepository dataStoreRepository, Context context) {
        String appVersionInStore = AppInfoUtils.Companion.getAppVersionInStore(dataStoreRepository);
        if (AppInfoUtils.Companion.isNewInstall(appVersionInStore)) {
            AppInfoUtils.Companion.updateInstallInfo(dataStoreRepository, EInstall.FIRST.getValue());
            AppInfoUtils.Companion.updateAppVersion(dataStoreRepository, AppInfoUtils.Companion.getAppVersion(context), appVersionInStore);
            AppInfoUtils.Companion.setLaunchMethod(EInstall.FIRST.getValue());
        } else if (AppInfoUtils.Companion.isUpdateInstall(appVersionInStore, AppInfoUtils.Companion.getAppVersion(context))) {
            AppInfoUtils.Companion.updateInstallInfo(dataStoreRepository, EInstall.UPDATE.getValue());
            AppInfoUtils.Companion.updateAppVersion(dataStoreRepository, AppInfoUtils.Companion.getAppVersion(context), appVersionInStore);
            AppInfoUtils.Companion.setLaunchMethod(EInstall.UPDATE.getValue());
        } else {
            AppInfoUtils.Companion.setLaunchMethod("");
        }
        Analytics.getIns().updateAnalyticsCollectionEnabled();
    }

    private static void initUseBandWidthOfPreferenceForGA(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_BAND_WIDTH) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_BAND_WIDTH, eBandWidth.e15M.ordinal(), (SharedPreferences.Editor) null);
        }
    }

    private static void initUsePjLog(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_UPLOAD_PJ_LOG) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_UPLOAD_PJ_LOG, 1);
        }
    }

    private static void initOverlayDeliveryButtonSettings(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG, 1);
        }
    }

    private static void initAudioTransferButtonSettings(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG, 1);
        }
    }

    private static void initOptimiseUltraWideSPS2(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_OPTIMASE_ULTRAWIDE_ASPECT_SPS2) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_OPTIMASE_ULTRAWIDE_ASPECT_SPS2, 1, (SharedPreferences.Editor) null);
        }
    }

    private static void initAutoDisplayDeliveryImage(Context context) {
        if (PrefUtils.readInt(context, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == Integer.MIN_VALUE) {
            PrefUtils.writeInt(context, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG, 1, (SharedPreferences.Editor) null);
        }
    }

    private static void initIsReversingMirroringProperty(Context context) {
        MirroringEntrance.INSTANCE.setIsReversingMirroringProperty(context, PrefUtils.readBoolean(context, PrefTagDefine.IS_REVERSING_MIRRORING, false));
    }

    private Initializer() {
    }
}
