package com.serenegiant.system;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Looper;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public abstract class ContextHolder<T extends Context> {
    private volatile boolean mReleased;
    private final WeakReference<T> mWeakContext;

    protected abstract void internalRelease();

    public ContextHolder(T t) {
        this.mWeakContext = new WeakReference<>(t);
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public final void release() {
        if (this.mReleased) {
            return;
        }
        this.mReleased = true;
        internalRelease();
        this.mWeakContext.clear();
    }

    public T getContext() {
        return this.mWeakContext.get();
    }

    public T requireContext() throws IllegalStateException {
        T context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException();
    }

    public AssetManager getAssets() {
        T context = getContext();
        if (context != null) {
            return context.getAssets();
        }
        return null;
    }

    public AssetManager requireAssets() throws IllegalStateException {
        AssetManager assets = requireContext().getAssets();
        if (assets != null) {
            return assets;
        }
        throw new IllegalStateException();
    }

    public ContentResolver getContentResolver() {
        T context = getContext();
        if (context != null) {
            return context.getContentResolver();
        }
        return null;
    }

    public ContentResolver requireContentResolver() throws IllegalStateException {
        ContentResolver contentResolver = requireContext().getContentResolver();
        if (contentResolver != null) {
            return contentResolver;
        }
        throw new IllegalStateException();
    }

    public LocalBroadcastManager requireLocalBroadcastManager() throws IllegalStateException {
        return LocalBroadcastManager.getInstance(requireContext());
    }

    public Looper getMainLooper() {
        T context = getContext();
        if (context != null) {
            return context.getMainLooper();
        }
        return null;
    }

    public Looper requireMainLooper() throws IllegalStateException {
        Looper mainLooper = requireContext().getMainLooper();
        if (mainLooper != null) {
            return mainLooper;
        }
        throw new IllegalStateException();
    }

    public PackageManager getPackageManager() {
        T context = getContext();
        if (context != null) {
            return context.getPackageManager();
        }
        return null;
    }

    public PackageManager requirePackageManager() {
        PackageManager packageManager = requireContext().getPackageManager();
        if (packageManager != null) {
            return packageManager;
        }
        throw new IllegalStateException();
    }

    public String getPackageName() {
        T context = getContext();
        if (context != null) {
            return context.getPackageName();
        }
        return null;
    }

    public String requirePackageName() throws IllegalStateException {
        String packageName = requireContext().getPackageName();
        if (packageName != null) {
            return packageName;
        }
        throw new IllegalStateException();
    }

    public Resources getResources() {
        T context = getContext();
        if (context != null) {
            return context.getResources();
        }
        return null;
    }

    public Resources requireResources() throws IllegalStateException {
        Resources resources = requireContext().getResources();
        if (resources != null) {
            return resources;
        }
        throw new IllegalStateException();
    }

    public SharedPreferences getSharedPreferences(String str, int i) {
        T context = getContext();
        if (context != null) {
            return context.getSharedPreferences(str, i);
        }
        return null;
    }

    public SharedPreferences requireSharedPreferences(String str, int i) throws IllegalStateException {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(str, i);
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        throw new IllegalStateException();
    }

    public String getString(int i) throws IllegalStateException {
        return requireContext().getString(i);
    }

    public String getString(int i, Object... objArr) throws IllegalStateException {
        return requireContext().getString(i, objArr);
    }

    public CharSequence getText(int i) throws IllegalStateException {
        return requireContext().getText(i);
    }

    public void sendLocalBroadcast(Intent intent) throws IllegalStateException {
        requireLocalBroadcastManager().sendBroadcast(intent);
    }
}
