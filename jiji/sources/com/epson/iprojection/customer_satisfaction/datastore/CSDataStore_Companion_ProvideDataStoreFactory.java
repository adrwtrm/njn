package com.epson.iprojection.customer_satisfaction.datastore;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes.dex */
public final class CSDataStore_Companion_ProvideDataStoreFactory implements Factory<DataStore<Preferences>> {
    private final Provider<Context> applicationContextProvider;

    public CSDataStore_Companion_ProvideDataStoreFactory(Provider<Context> provider) {
        this.applicationContextProvider = provider;
    }

    @Override // javax.inject.Provider
    public DataStore<Preferences> get() {
        return provideDataStore(this.applicationContextProvider.get());
    }

    public static CSDataStore_Companion_ProvideDataStoreFactory create(Provider<Context> provider) {
        return new CSDataStore_Companion_ProvideDataStoreFactory(provider);
    }

    public static DataStore<Preferences> provideDataStore(Context context) {
        return (DataStore) Preconditions.checkNotNullFromProvides(CSDataStore.Companion.provideDataStore(context));
    }
}
