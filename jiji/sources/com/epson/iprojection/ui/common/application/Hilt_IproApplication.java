package com.epson.iprojection.ui.common.application;

import android.app.Application;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.android.internal.managers.ComponentSupplier;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;

/* loaded from: classes.dex */
abstract class Hilt_IproApplication extends Application implements GeneratedComponentManagerHolder {
    private boolean injected = false;
    private final ApplicationComponentManager componentManager = new ApplicationComponentManager(new ComponentSupplier() { // from class: com.epson.iprojection.ui.common.application.Hilt_IproApplication.1
        @Override // dagger.hilt.android.internal.managers.ComponentSupplier
        public Object get() {
            return DaggerIproApplication_HiltComponents_SingletonC.builder().applicationContextModule(new ApplicationContextModule(Hilt_IproApplication.this)).build();
        }
    });

    @Override // dagger.hilt.internal.GeneratedComponentManagerHolder
    public final ApplicationComponentManager componentManager() {
        return this.componentManager;
    }

    @Override // dagger.hilt.internal.GeneratedComponentManager
    public final Object generatedComponent() {
        return componentManager().generatedComponent();
    }

    @Override // android.app.Application
    public void onCreate() {
        hiltInternalInject();
        super.onCreate();
    }

    protected void hiltInternalInject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        ((IproApplication_GeneratedInjector) generatedComponent()).injectIproApplication((IproApplication) UnsafeCasts.unsafeCast(this));
    }
}
