package com.epson.iprojection.common;

import androidx.datastore.preferences.core.Preferences;
import kotlin.Metadata;

/* compiled from: PreferencesKeys.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/common/PreferencesKeys;", "", "()V", "APP_VERSION", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getAPP_VERSION", "()Landroidx/datastore/preferences/core/Preferences$Key;", "INSTALL", "getINSTALL", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PreferencesKeys {
    public static final PreferencesKeys INSTANCE = new PreferencesKeys();
    private static final Preferences.Key<String> APP_VERSION = androidx.datastore.preferences.core.PreferencesKeys.stringKey("app_version");
    private static final Preferences.Key<String> INSTALL = androidx.datastore.preferences.core.PreferencesKeys.stringKey("install");

    private PreferencesKeys() {
    }

    public final Preferences.Key<String> getAPP_VERSION() {
        return APP_VERSION;
    }

    public final Preferences.Key<String> getINSTALL() {
        return INSTALL;
    }
}
