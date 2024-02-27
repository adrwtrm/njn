package com.serenegiant.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Handler;
import android.util.Log;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* loaded from: classes2.dex */
public class ConnectivityHelper {
    private static final String ACTION_GLOBAL_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    private static final boolean DEBUG = false;
    public static final int NETWORK_TYPE_BLUETOOTH = 128;
    public static final int NETWORK_TYPE_ETHERNET = 512;
    public static final int NETWORK_TYPE_MOBILE = 1;
    public static final int NETWORK_TYPE_NON = 0;
    private static final int NETWORK_TYPE_UNKNOWN = -1;
    public static final int NETWORK_TYPE_WIFI = 2;
    private static final String TAG = "ConnectivityHelper";
    private final Handler mAsyncHandler;
    private final ConnectivityCallback mCallback;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private BroadcastReceiver mNetworkChangedReceiver;
    private ConnectivityManager.OnNetworkActiveListener mOnNetworkActiveListener;
    private final Handler mUIHandler;
    private final WeakReference<Context> mWeakContext;
    private final Object mSync = new Object();
    private int mActiveNetworkType = -1;
    private volatile boolean mIsReleased = false;

    /* loaded from: classes2.dex */
    public interface ConnectivityCallback {
        void onError(Throwable th);

        void onNetworkChanged(int i, int i2);
    }

    public ConnectivityHelper(Context context, ConnectivityCallback connectivityCallback) {
        this.mWeakContext = new WeakReference<>(context);
        this.mCallback = connectivityCallback;
        Handler handler = new Handler(context.getMainLooper());
        this.mUIHandler = handler;
        this.mAsyncHandler = HandlerThreadHandler.createHandler(TAG);
        handler.post(new Runnable() { // from class: com.serenegiant.net.ConnectivityHelper.1
            @Override // java.lang.Runnable
            public void run() {
                ConnectivityHelper.this.initAsync();
            }
        });
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void release() {
        this.mIsReleased = true;
        updateActiveNetwork(0);
        Context context = this.mWeakContext.get();
        if (context != null) {
            this.mWeakContext.clear();
            if (BuildCheck.isAPI21()) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtils.requireSystemService(context, ConnectivityManager.class);
                ConnectivityManager.OnNetworkActiveListener onNetworkActiveListener = this.mOnNetworkActiveListener;
                if (onNetworkActiveListener != null) {
                    try {
                        connectivityManager.removeDefaultNetworkActiveListener(onNetworkActiveListener);
                    } catch (Exception e) {
                        Log.w(TAG, e);
                    }
                    this.mOnNetworkActiveListener = null;
                }
                ConnectivityManager.NetworkCallback networkCallback = this.mNetworkCallback;
                if (networkCallback != null) {
                    try {
                        connectivityManager.unregisterNetworkCallback(networkCallback);
                    } catch (Exception e2) {
                        Log.w(TAG, e2);
                    }
                    this.mNetworkCallback = null;
                }
            }
            BroadcastReceiver broadcastReceiver = this.mNetworkChangedReceiver;
            if (broadcastReceiver != null) {
                try {
                    context.unregisterReceiver(broadcastReceiver);
                } catch (Exception e3) {
                    Log.w(TAG, e3);
                }
                this.mNetworkChangedReceiver = null;
            }
        }
        synchronized (this.mSync) {
            this.mUIHandler.removeCallbacksAndMessages(null);
            try {
                this.mAsyncHandler.removeCallbacksAndMessages(null);
                HandlerUtils.NoThrowQuit(this.mAsyncHandler);
            } catch (Exception e4) {
                Log.w(TAG, e4);
            }
        }
    }

    public boolean isValid() {
        try {
            requireConnectivityManager();
            return !this.mIsReleased;
        } catch (IllegalStateException unused) {
            return false;
        }
    }

    public int getActiveNetworkType() throws IllegalStateException {
        int i;
        synchronized (this.mSync) {
            if (this.mIsReleased) {
                throw new IllegalStateException("already released!");
            }
            i = this.mActiveNetworkType;
        }
        return i;
    }

    public boolean isNetworkReachable() throws IllegalStateException {
        return getActiveNetworkType() != 0;
    }

    public boolean isWifiNetworkReachable() throws IllegalStateException {
        int activeNetworkType = getActiveNetworkType();
        return activeNetworkType == 2 || activeNetworkType == 512;
    }

    public boolean isMobileNetworkReachable() throws IllegalStateException {
        return getActiveNetworkType() == 1;
    }

    public boolean isBluetoothNetworkReachable() throws IllegalStateException {
        return getActiveNetworkType() == 128;
    }

    public void refresh() throws IllegalStateException {
        synchronized (this.mSync) {
            if (this.mIsReleased) {
                throw new IllegalStateException("already released!");
            }
            this.mActiveNetworkType = 0;
            this.mUIHandler.post(new Runnable() { // from class: com.serenegiant.net.ConnectivityHelper.2
                @Override // java.lang.Runnable
                public void run() {
                    ConnectivityHelper.this.refreshAsync();
                }
            });
        }
    }

    private Context requireContext() throws IllegalStateException {
        Context context = this.mWeakContext.get();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("context is already released");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ConnectivityManager requireConnectivityManager() throws IllegalStateException {
        return (ConnectivityManager) ContextUtils.requireSystemService(requireContext(), ConnectivityManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAsync() {
        ConnectivityManager requireConnectivityManager = requireConnectivityManager();
        if (BuildCheck.isAPI21()) {
            MyOnNetworkActiveListener myOnNetworkActiveListener = new MyOnNetworkActiveListener();
            this.mOnNetworkActiveListener = myOnNetworkActiveListener;
            requireConnectivityManager.addDefaultNetworkActiveListener(myOnNetworkActiveListener);
            this.mNetworkCallback = new MyNetworkCallback();
            if (BuildCheck.isAPI26()) {
                requireConnectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback, this.mAsyncHandler);
            } else if (BuildCheck.isAPI24()) {
                requireConnectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
            } else {
                requireConnectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), this.mNetworkCallback);
            }
        } else {
            this.mNetworkChangedReceiver = new NetworkChangedReceiver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_GLOBAL_CONNECTIVITY_CHANGE);
            requireContext().registerReceiver(this.mNetworkChangedReceiver, intentFilter);
        }
        refreshAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshAsync() {
        Network[] allNetworks;
        ConnectivityManager requireConnectivityManager = requireConnectivityManager();
        if (BuildCheck.isAPI23()) {
            updateActiveNetwork(requireConnectivityManager.getActiveNetwork(), null, null);
        } else if (BuildCheck.isAPI21()) {
            for (Network network : requireConnectivityManager.getAllNetworks()) {
                NetworkCapabilities networkCapabilities = requireConnectivityManager.getNetworkCapabilities(network);
                LinkProperties linkProperties = requireConnectivityManager.getLinkProperties(network);
                if (networkCapabilities != null && linkProperties != null) {
                    requireConnectivityManager.getLinkProperties(network);
                    updateActiveNetwork(network, networkCapabilities, linkProperties);
                    return;
                }
            }
            updateActiveNetwork(0);
        } else {
            updateActiveNetwork(requireConnectivityManager.getActiveNetworkInfo());
        }
    }

    private void callOnNetworkChanged(final int i, final int i2) {
        synchronized (this.mSync) {
            if (HandlerUtils.isActive(this.mAsyncHandler)) {
                this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.net.ConnectivityHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectivityHelper.this.m276x8bbb5d5d(i, i2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$callOnNetworkChanged$0$com-serenegiant-net-ConnectivityHelper  reason: not valid java name */
    public /* synthetic */ void m276x8bbb5d5d(int i, int i2) {
        try {
            this.mCallback.onNetworkChanged(i, i2);
        } catch (Exception e) {
            callOnError(e);
        }
    }

    private void callOnError(final Throwable th) {
        synchronized (this.mSync) {
            if (HandlerUtils.isActive(this.mAsyncHandler)) {
                this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.net.ConnectivityHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectivityHelper.this.m275lambda$callOnError$1$comserenegiantnetConnectivityHelper(th);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$callOnError$1$com-serenegiant-net-ConnectivityHelper  reason: not valid java name */
    public /* synthetic */ void m275lambda$callOnError$1$comserenegiantnetConnectivityHelper(Throwable th) {
        try {
            this.mCallback.onError(th);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveNetwork(Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        int i = 0;
        if (network != null) {
            ConnectivityManager requireConnectivityManager = requireConnectivityManager();
            if (networkCapabilities == null) {
                networkCapabilities = requireConnectivityManager.getNetworkCapabilities(network);
            }
            if (linkProperties == null) {
                linkProperties = requireConnectivityManager.getLinkProperties(network);
            }
            if (networkCapabilities != null && linkProperties != null) {
                if (isWifiNetworkReachable(requireConnectivityManager, network, networkCapabilities, linkProperties)) {
                    i = 2;
                } else if (isMobileNetworkReachable(requireConnectivityManager, network, networkCapabilities, linkProperties)) {
                    i = 1;
                } else if (isBluetoothNetworkReachable(requireConnectivityManager, network, networkCapabilities, linkProperties)) {
                    i = 128;
                } else if (isNetworkReachable(requireConnectivityManager, network, networkCapabilities, linkProperties)) {
                    i = 512;
                }
            }
            updateActiveNetwork(i);
            return;
        }
        updateActiveNetwork(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveNetwork(NetworkInfo networkInfo) {
        int type = (networkInfo == null || !networkInfo.isConnectedOrConnecting()) ? -1 : networkInfo.getType();
        updateActiveNetwork(type != 0 ? type != 1 ? type != 9 ? 0 : 512 : 2 : 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveNetwork(int i) {
        synchronized (this.mSync) {
            int i2 = this.mActiveNetworkType;
            if (i2 != i) {
                this.mActiveNetworkType = i;
                callOnNetworkChanged(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MyOnNetworkActiveListener implements ConnectivityManager.OnNetworkActiveListener {
        private final String TAG = MyOnNetworkActiveListener.class.getSimpleName();

        public MyOnNetworkActiveListener() {
        }

        @Override // android.net.ConnectivityManager.OnNetworkActiveListener
        public void onNetworkActive() {
            try {
                if (BuildCheck.isAPI23()) {
                    ConnectivityHelper connectivityHelper = ConnectivityHelper.this;
                    connectivityHelper.updateActiveNetwork(connectivityHelper.requireConnectivityManager().getActiveNetwork(), null, null);
                } else {
                    ConnectivityHelper connectivityHelper2 = ConnectivityHelper.this;
                    connectivityHelper2.updateActiveNetwork(connectivityHelper2.requireConnectivityManager().getActiveNetworkInfo());
                }
            } catch (Exception e) {
                Log.w(this.TAG, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MyNetworkCallback extends ConnectivityManager.NetworkCallback {
        private final String TAG = MyNetworkCallback.class.getSimpleName();

        public MyNetworkCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            ConnectivityHelper.this.updateActiveNetwork(network, null, null);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            ConnectivityHelper.this.updateActiveNetwork(network, networkCapabilities, null);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            ConnectivityHelper.this.updateActiveNetwork(network, null, linkProperties);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLosing(Network network, int i) {
            super.onLosing(network, i);
            ConnectivityHelper.this.updateActiveNetwork(network, null, null);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            super.onLost(network);
            ConnectivityHelper.this.updateActiveNetwork(network, null, null);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onUnavailable() {
            super.onUnavailable();
            ConnectivityHelper.this.updateActiveNetwork(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class NetworkChangedReceiver extends BroadcastReceiver {
        private static final String TAG = "NetworkChangedReceiver";
        private final ConnectivityHelper mParent;

        public NetworkChangedReceiver(ConnectivityHelper connectivityHelper) {
            this.mParent = connectivityHelper;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityHelper.ACTION_GLOBAL_CONNECTIVITY_CHANGE.equals(intent != null ? intent.getAction() : null)) {
                this.mParent.updateActiveNetwork(((ConnectivityManager) ContextUtils.requireSystemService(context, ConnectivityManager.class)).getActiveNetworkInfo());
            }
        }
    }

    public static boolean isWifiNetworkReachable(Context context) {
        Network[] allNetworks;
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtils.requireSystemService(context, ConnectivityManager.class);
        if (BuildCheck.isAPI23()) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            LinkProperties linkProperties = connectivityManager.getLinkProperties(activeNetwork);
            return (networkCapabilities == null || linkProperties == null || !isWifiNetworkReachable(connectivityManager, activeNetwork, networkCapabilities, linkProperties)) ? false : true;
        } else if (BuildCheck.isAPI21()) {
            for (Network network : connectivityManager.getAllNetworks()) {
                NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities(network);
                LinkProperties linkProperties2 = connectivityManager.getLinkProperties(network);
                if (networkCapabilities2 != null && linkProperties2 != null && isWifiNetworkReachable(connectivityManager, network, networkCapabilities2, linkProperties2)) {
                    return true;
                }
            }
            return false;
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                return false;
            }
            int type = activeNetworkInfo.getType();
            return type == 1 || type == 6 || type == 7 || type == 9;
        }
    }

    public static boolean isMobileNetworkReachable(Context context) {
        Network[] allNetworks;
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtils.requireSystemService(context, ConnectivityManager.class);
        if (BuildCheck.isAPI23()) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            LinkProperties linkProperties = connectivityManager.getLinkProperties(activeNetwork);
            return (networkCapabilities == null || linkProperties == null || !isMobileNetworkReachable(connectivityManager, activeNetwork, networkCapabilities, linkProperties)) ? false : true;
        }
        if (BuildCheck.isAPI21()) {
            for (Network network : connectivityManager.getAllNetworks()) {
                NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities(network);
                LinkProperties linkProperties2 = connectivityManager.getLinkProperties(network);
                if (networkCapabilities2 != null && linkProperties2 != null && isMobileNetworkReachable(connectivityManager, network, networkCapabilities2, linkProperties2)) {
                    return true;
                }
            }
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting() && activeNetworkInfo.getType() == 0;
        }
        return false;
    }

    public static boolean isNetworkReachable(Context context) {
        Network[] allNetworks;
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtils.requireSystemService(context, ConnectivityManager.class);
        if (BuildCheck.isAPI23()) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            LinkProperties linkProperties = connectivityManager.getLinkProperties(activeNetwork);
            return (networkCapabilities == null || linkProperties == null || !isNetworkReachable(connectivityManager, activeNetwork, networkCapabilities, linkProperties)) ? false : true;
        } else if (BuildCheck.isAPI21()) {
            for (Network network : connectivityManager.getAllNetworks()) {
                NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities(network);
                LinkProperties linkProperties2 = connectivityManager.getLinkProperties(network);
                if (networkCapabilities2 != null && linkProperties2 != null && isNetworkReachable(connectivityManager, network, networkCapabilities2, linkProperties2)) {
                    return true;
                }
            }
            return false;
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }
    }

    private static boolean isWifiNetworkReachable(ConnectivityManager connectivityManager, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        return (!BuildCheck.isAPI26() ? networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(3) : networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(3)) && isNetworkReachable(connectivityManager, network, networkCapabilities, linkProperties);
    }

    private static boolean isMobileNetworkReachable(ConnectivityManager connectivityManager, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        boolean hasTransport;
        if (BuildCheck.isAPI27()) {
            hasTransport = networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(6);
        } else {
            hasTransport = networkCapabilities.hasTransport(0);
        }
        return hasTransport && isNetworkReachable(connectivityManager, network, networkCapabilities, linkProperties);
    }

    private static boolean isBluetoothNetworkReachable(ConnectivityManager connectivityManager, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        return networkCapabilities.hasTransport(2) && isNetworkReachable(connectivityManager, network, networkCapabilities, linkProperties);
    }

    private static boolean isNetworkReachable(ConnectivityManager connectivityManager, Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties) {
        boolean z = !linkProperties.getLinkAddresses().isEmpty();
        if (BuildCheck.isAPI29()) {
            if (z && networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && (networkCapabilities.hasCapability(21) || networkCapabilities.hasCapability(19))) {
                return true;
            }
        } else {
            boolean z2 = z && connectivityManager.getNetworkInfo(network).isConnectedOrConnecting();
            if (BuildCheck.isAPI28()) {
                if (z2 && networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16) && (networkCapabilities.hasCapability(21) || networkCapabilities.hasCapability(19))) {
                    return true;
                }
            } else if (BuildCheck.isAPI23()) {
                if (z2 && networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16)) {
                    return true;
                }
            } else if (z2 && networkCapabilities.hasCapability(12)) {
                return true;
            }
        }
        return false;
    }

    public static String getNetworkTypeString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 128 ? i != 512 ? String.format(Locale.US, "UNKNOWN(%d)", Integer.valueOf(i)) : "ETHERNET" : "BLUETOOTH" : "WIFI" : "MOBILE" : "NON";
    }
}
