package dagger.hilt.android.internal.managers;

import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager;
import dagger.internal.Factory;

/* loaded from: classes2.dex */
public final class ActivityRetainedComponentManager_Lifecycle_Factory implements Factory<ActivityRetainedComponentManager.Lifecycle> {
    @Override // javax.inject.Provider
    public ActivityRetainedComponentManager.Lifecycle get() {
        return newInstance();
    }

    public static ActivityRetainedComponentManager_Lifecycle_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActivityRetainedComponentManager.Lifecycle newInstance() {
        return new ActivityRetainedComponentManager.Lifecycle();
    }

    /* loaded from: classes2.dex */
    private static final class InstanceHolder {
        private static final ActivityRetainedComponentManager_Lifecycle_Factory INSTANCE = new ActivityRetainedComponentManager_Lifecycle_Factory();

        private InstanceHolder() {
        }
    }
}
