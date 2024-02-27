package com.epson.iprojection.customer_satisfaction.gateways;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes.dex */
public final class CSRepository_Factory implements Factory<CSRepository> {
    private final Provider<DataStore<Preferences>> dataStoreProvider;

    public CSRepository_Factory(Provider<DataStore<Preferences>> provider) {
        this.dataStoreProvider = provider;
    }

    @Override // javax.inject.Provider
    public CSRepository get() {
        return newInstance(this.dataStoreProvider.get());
    }

    public static CSRepository_Factory create(Provider<DataStore<Preferences>> provider) {
        return new CSRepository_Factory(provider);
    }

    public static CSRepository newInstance(DataStore<Preferences> dataStore) {
        return new CSRepository(dataStore);
    }
}
