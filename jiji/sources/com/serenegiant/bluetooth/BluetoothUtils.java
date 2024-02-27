package com.serenegiant.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes2.dex */
public class BluetoothUtils {
    private static final String ACTION_REQUEST_DISABLE = "android.bluetooth.adapter.action.REQUEST_DISABLE";
    private static final boolean DEBUG = false;
    private static final String TAG = "BluetoothUtils";
    private final ActivityResultLauncher<Intent> mLauncher;
    private boolean requestBluetooth;

    /* loaded from: classes2.dex */
    public interface BluetoothEnableCallback {
        void onChanged(boolean z);
    }

    public BluetoothUtils(ComponentActivity componentActivity, final BluetoothEnableCallback bluetoothEnableCallback) {
        this.mLauncher = componentActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.serenegiant.bluetooth.BluetoothUtils$$ExternalSyntheticLambda0
            {
                BluetoothUtils.this = this;
            }

            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BluetoothUtils.this.m261lambda$new$0$comserenegiantbluetoothBluetoothUtils(bluetoothEnableCallback, (ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-serenegiant-bluetooth-BluetoothUtils  reason: not valid java name */
    public /* synthetic */ void m261lambda$new$0$comserenegiantbluetoothBluetoothUtils(BluetoothEnableCallback bluetoothEnableCallback, ActivityResult activityResult) {
        this.requestBluetooth = false;
        bluetoothEnableCallback.onChanged(isEnabled());
    }

    public BluetoothUtils(Fragment fragment, final BluetoothEnableCallback bluetoothEnableCallback) {
        this.mLauncher = fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.serenegiant.bluetooth.BluetoothUtils$$ExternalSyntheticLambda1
            {
                BluetoothUtils.this = this;
            }

            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BluetoothUtils.this.m262lambda$new$1$comserenegiantbluetoothBluetoothUtils(bluetoothEnableCallback, (ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-serenegiant-bluetooth-BluetoothUtils  reason: not valid java name */
    public /* synthetic */ void m262lambda$new$1$comserenegiantbluetoothBluetoothUtils(BluetoothEnableCallback bluetoothEnableCallback, ActivityResult activityResult) {
        this.requestBluetooth = false;
        bluetoothEnableCallback.onChanged(isEnabled());
    }

    public boolean requestEnable() {
        if (isAvailable() && !isEnabled() && !this.requestBluetooth) {
            this.requestBluetooth = true;
            this.mLauncher.launch(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
        }
        return isEnabled();
    }

    public boolean requestDisable() {
        if (isEnabled()) {
            this.requestBluetooth = true;
            this.mLauncher.launch(new Intent(ACTION_REQUEST_DISABLE));
        }
        return isEnabled();
    }

    public static boolean isAvailable() {
        try {
            return BluetoothAdapter.getDefaultAdapter() != null;
        } catch (Exception e) {
            Log.w(TAG, e);
            return false;
        }
    }

    public static boolean isEnabled() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                return defaultAdapter.isEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.w(TAG, e);
            return false;
        }
    }

    public static Set<BluetoothDevice> getBondedDevices() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return (defaultAdapter == null || !defaultAdapter.isEnabled()) ? Collections.emptySet() : defaultAdapter.getBondedDevices();
    }

    public static boolean requestDiscoverable(Activity activity, int i) throws IllegalStateException {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            throw new IllegalStateException("bluetoothに対応していないか無効になっている");
        }
        if (defaultAdapter.getScanMode() != 23) {
            Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
            intent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", i);
            activity.startActivity(intent);
        }
        return defaultAdapter.getScanMode() == 23;
    }

    public static boolean requestDiscoverable(Fragment fragment, int i) throws IllegalStateException {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            throw new IllegalStateException("bluetoothに対応していないか無効になっている");
        }
        if (defaultAdapter.getScanMode() != 23) {
            Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
            if (i > 0 && i <= 300) {
                intent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", i);
            }
            fragment.startActivity(intent);
        }
        return defaultAdapter.getScanMode() == 23;
    }
}
