package com.epson.iprojection.ui.common.application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.common.analytics.Analytics;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
/* loaded from: classes.dex */
public class IproApplication extends Hilt_IproApplication implements LifecycleEventObserver {
    public boolean mIsNFCEventHappenedDuringQRConnect = false;

    @Override // com.epson.iprojection.ui.common.application.Hilt_IproApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /* renamed from: com.epson.iprojection.ui.common.application.IproApplication$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_STOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_ANY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        switch (AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()]) {
            case 1:
                Lg.i("onCreate");
                Analytics.getIns().enableLaunchTimeEvent();
                return;
            case 2:
                Lg.i("onStart");
                if (ChromeOSUtils.INSTANCE.isChromeOS(this)) {
                    ChromeOSUtils.INSTANCE.setMulticastLock(this, true);
                }
                Analytics.getIns().resetLaunchTime();
                Analytics.getIns().resetDisconnectedTime();
                return;
            case 3:
                Lg.i("onResume");
                return;
            case 4:
                Lg.i("onPause");
                return;
            case 5:
                Lg.i("onStop");
                if (ChromeOSUtils.INSTANCE.isChromeOS(this)) {
                    ChromeOSUtils.INSTANCE.setMulticastLock(this, false);
                    return;
                }
                return;
            case 6:
                Lg.i("onDestroy");
                return;
            case 7:
                Lg.i("onAny");
                return;
            default:
                return;
        }
    }
}
