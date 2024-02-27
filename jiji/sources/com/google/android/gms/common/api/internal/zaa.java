package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0010zaa> zacl;

    public zaa(Activity activity) {
        this(C0010zaa.zaa(activity));
    }

    private zaa(C0010zaa c0010zaa) {
        this.zacl = new WeakReference<>(c0010zaa);
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0010zaa c0010zaa = this.zacl.get();
        if (c0010zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c0010zaa.zaa(runnable);
        return this;
    }

    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static class C0010zaa extends LifecycleCallback {
        private List<Runnable> zacm;

        /* JADX INFO: Access modifiers changed from: private */
        public static C0010zaa zaa(Activity activity) {
            C0010zaa c0010zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                c0010zaa = (C0010zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0010zaa.class);
                if (c0010zaa == null) {
                    c0010zaa = new C0010zaa(fragment);
                }
            }
            return c0010zaa;
        }

        private C0010zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacm = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacm.add(runnable);
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacm;
                this.zacm = new ArrayList();
            }
            for (Runnable runnable : list) {
                runnable.run();
            }
        }
    }
}
