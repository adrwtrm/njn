package com.epson.iprojection.common;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes.dex */
public final class DataStoreRepository_Factory implements Factory<DataStoreRepository> {
    private final Provider<DataStore<Preferences>> dataStoreProvider;

    public DataStoreRepository_Factory(Provider<DataStore<Preferences>> provider) {
        this.dataStoreProvider = provider;
    }

    @Override // javax.inject.Provider
    public DataStoreRepository get() {
        return newInstance(this.dataStoreProvider.get());
    }

    public static DataStoreRepository_Factory create(Provider<DataStore<Preferences>> provider) {
        return new DataStoreRepository_Factory(provider);
    }

    public static DataStoreRepository newInstance(DataStore<Preferences> dataStore) {
        return new DataStoreRepository(dataStore);
    }
}
