package com.epson.iprojection.customer_satisfaction.gateways;

import androidx.datastore.preferences.core.Preferences;
import kotlin.Metadata;

/* compiled from: PreferencesKeys.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/gateways/PreferencesKeys;", "", "()V", "COLLECTED_LAST_DATE", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getCOLLECTED_LAST_DATE", "()Landroidx/datastore/preferences/core/Preferences$Key;", "COLLECTED_SATISFACTION", "", "getCOLLECTED_SATISFACTION", "COLLECTED_STORE_REVIEW", "getCOLLECTED_STORE_REVIEW", "SESSION_START_TIME", "", "getSESSION_START_TIME", "TRY_COLLECT_COUNT", "getTRY_COLLECT_COUNT", "USED_COUNT", "getUSED_COUNT", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PreferencesKeys {
    public static final PreferencesKeys INSTANCE = new PreferencesKeys();
    private static final Preferences.Key<Integer> USED_COUNT = androidx.datastore.preferences.core.PreferencesKeys.intKey("USED_COUNT");
    private static final Preferences.Key<Integer> TRY_COLLECT_COUNT = androidx.datastore.preferences.core.PreferencesKeys.intKey("TRY_COLLECT_COUNT");
    private static final Preferences.Key<Boolean> COLLECTED_STORE_REVIEW = androidx.datastore.preferences.core.PreferencesKeys.booleanKey("COLLECTED_STORE_REVIEW");
    private static final Preferences.Key<Boolean> COLLECTED_SATISFACTION = androidx.datastore.preferences.core.PreferencesKeys.booleanKey("COLLECTED_SATISFACTION");
    private static final Preferences.Key<String> COLLECTED_LAST_DATE = androidx.datastore.preferences.core.PreferencesKeys.stringKey("COLLECTED_LAST_DATE");
    private static final Preferences.Key<Integer> SESSION_START_TIME = androidx.datastore.preferences.core.PreferencesKeys.intKey("SESSION_START_TIME");

    private PreferencesKeys() {
    }

    public final Preferences.Key<Integer> getUSED_COUNT() {
        return USED_COUNT;
    }

    public final Preferences.Key<Integer> getTRY_COLLECT_COUNT() {
        return TRY_COLLECT_COUNT;
    }

    public final Preferences.Key<Boolean> getCOLLECTED_STORE_REVIEW() {
        return COLLECTED_STORE_REVIEW;
    }

    public final Preferences.Key<Boolean> getCOLLECTED_SATISFACTION() {
        return COLLECTED_SATISFACTION;
    }

    public final Preferences.Key<String> getCOLLECTED_LAST_DATE() {
        return COLLECTED_LAST_DATE;
    }

    public final Preferences.Key<Integer> getSESSION_START_TIME() {
        return SESSION_START_TIME;
    }
}
