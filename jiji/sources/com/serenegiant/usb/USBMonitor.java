package com.serenegiant.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import com.serenegiant.app.PendingIntentCompat;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.utils.BufferHelper;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes2.dex */
public final class USBMonitor implements Const {
    private static final String ACTION_USB_PERMISSION_BASE = "com.serenegiant.USB_PERMISSION.";
    private static final boolean DEBUG = false;
    private static final String TAG = "USBMonitor";
    private final OnDeviceConnectListener mOnDeviceConnectListener;
    private final UsbManager mUsbManager;
    private final WeakReference<Context> mWeakContext;
    private final String ACTION_USB_PERMISSION = ACTION_USB_PERMISSION_BASE + hashCode();
    private final List<UsbControlBlock> mCtrlBlocks = new ArrayList();
    private PendingIntent mPermissionIntent = null;
    private final List<DeviceFilter> mDeviceFilters = new ArrayList();
    private final Set<UsbDevice> mAttachedDevices = new HashSet();
    private boolean mEnablePolling = !BuildCheck.isAndroid5();
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() { // from class: com.serenegiant.usb.USBMonitor.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (USBMonitor.this.destroyed) {
                return;
            }
            USBMonitor.this.onReceive(context, intent);
        }
    };
    private final Runnable mDeviceCheckRunnable = new Runnable() { // from class: com.serenegiant.usb.USBMonitor.3
        @Override // java.lang.Runnable
        public void run() {
            HashSet hashSet;
            if (USBMonitor.this.destroyed) {
                return;
            }
            USBMonitor.this.mAsyncHandler.removeCallbacks(USBMonitor.this.mDeviceCheckRunnable);
            List<UsbDevice> deviceList = USBMonitor.this.getDeviceList();
            ArrayList arrayList = new ArrayList();
            synchronized (USBMonitor.this.mAttachedDevices) {
                hashSet = new HashSet(USBMonitor.this.mAttachedDevices);
                USBMonitor.this.mAttachedDevices.clear();
                USBMonitor.this.mAttachedDevices.addAll(deviceList);
            }
            for (UsbDevice usbDevice : deviceList) {
                if (!hashSet.contains(usbDevice)) {
                    arrayList.add(usbDevice);
                }
            }
            int size = arrayList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    final UsbDevice usbDevice2 = (UsbDevice) arrayList.get(i);
                    USBMonitor.this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            USBMonitor.this.mOnDeviceConnectListener.onAttach(usbDevice2);
                        }
                    });
                }
            }
            if (USBMonitor.this.mEnablePolling) {
                USBMonitor.this.mAsyncHandler.postDelayed(USBMonitor.this.mDeviceCheckRunnable, 1000L);
            }
        }
    };
    private final Handler mAsyncHandler = HandlerThreadHandler.createHandler(TAG);
    private volatile boolean destroyed = false;

    /* loaded from: classes2.dex */
    public interface OnDeviceConnectListener {
        void onAttach(UsbDevice usbDevice);

        void onCancel(UsbDevice usbDevice);

        void onConnected(UsbDevice usbDevice, UsbControlBlock usbControlBlock);

        void onDetach(UsbDevice usbDevice);

        void onDisconnect(UsbDevice usbDevice);

        void onError(UsbDevice usbDevice, Throwable th);

        void onPermission(UsbDevice usbDevice);
    }

    public USBMonitor(Context context, OnDeviceConnectListener onDeviceConnectListener) {
        this.mWeakContext = new WeakReference<>(context);
        this.mUsbManager = (UsbManager) ContextUtils.requireSystemService(context, UsbManager.class);
        this.mOnDeviceConnectListener = onDeviceConnectListener;
    }

    public void release() {
        ArrayList<UsbControlBlock> arrayList;
        unregister();
        if (this.destroyed) {
            return;
        }
        this.destroyed = true;
        this.mAsyncHandler.removeCallbacksAndMessages(null);
        synchronized (this.mCtrlBlocks) {
            arrayList = new ArrayList(this.mCtrlBlocks);
            this.mCtrlBlocks.clear();
        }
        for (UsbControlBlock usbControlBlock : arrayList) {
            try {
                usbControlBlock.close();
            } catch (Exception e) {
                Log.e(TAG, "release:", e);
            }
        }
        HandlerUtils.NoThrowQuit(this.mAsyncHandler);
    }

    public void destroy() {
        release();
    }

    public synchronized void register() throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        if (this.mPermissionIntent == null) {
            Context context = this.mWeakContext.get();
            if (context != null) {
                this.mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(this.ACTION_USB_PERMISSION), BuildCheck.isAPI31() ? PendingIntentCompat.FLAG_MUTABLE : 0);
                context.registerReceiver(this.mUsbReceiver, createIntentFilter());
                this.mAsyncHandler.postDelayed(this.mDeviceCheckRunnable, 500L);
            } else {
                throw new IllegalStateException("context already released");
            }
        }
    }

    public synchronized void unregister() throws IllegalStateException {
        if (!this.destroyed) {
            this.mAsyncHandler.removeCallbacksAndMessages(null);
        }
        if (this.mPermissionIntent != null) {
            Context context = this.mWeakContext.get();
            if (context != null) {
                try {
                    context.unregisterReceiver(this.mUsbReceiver);
                } catch (Exception unused) {
                }
            }
            this.mPermissionIntent = null;
        }
        synchronized (this.mAttachedDevices) {
            this.mAttachedDevices.clear();
        }
    }

    public synchronized boolean isRegistered() {
        boolean z;
        if (!this.destroyed) {
            z = this.mPermissionIntent != null;
        }
        return z;
    }

    public void setDeviceFilter(DeviceFilter deviceFilter) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.clear();
        if (deviceFilter != null) {
            this.mDeviceFilters.add(deviceFilter);
        }
    }

    public void addDeviceFilter(DeviceFilter deviceFilter) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.add(deviceFilter);
    }

    public void removeDeviceFilter(DeviceFilter deviceFilter) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.remove(deviceFilter);
    }

    public void setDeviceFilter(List<DeviceFilter> list) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.clear();
        if (list != null) {
            this.mDeviceFilters.addAll(list);
        }
    }

    public void addDeviceFilter(List<DeviceFilter> list) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.addAll(list);
    }

    public void removeDeviceFilter(List<DeviceFilter> list) throws IllegalStateException {
        if (this.destroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mDeviceFilters.removeAll(list);
    }

    public int getDeviceCount() {
        return getDeviceList().size();
    }

    public List<UsbDevice> getDeviceList() {
        HashMap<String, UsbDevice> deviceList;
        ArrayList arrayList = new ArrayList();
        if (!this.destroyed && (deviceList = this.mUsbManager.getDeviceList()) != null) {
            if (this.mDeviceFilters.isEmpty()) {
                arrayList.addAll(deviceList.values());
            } else {
                for (UsbDevice usbDevice : deviceList.values()) {
                    if (matches(usbDevice)) {
                        arrayList.add(usbDevice);
                    }
                }
            }
        }
        return arrayList;
    }

    private boolean matches(UsbDevice usbDevice) {
        if (this.mDeviceFilters.isEmpty()) {
            return true;
        }
        for (DeviceFilter deviceFilter : this.mDeviceFilters) {
            if (deviceFilter != null && deviceFilter.matches(usbDevice)) {
                return !deviceFilter.isExclude;
            }
        }
        return false;
    }

    public UsbDevice findDevice(String str) {
        return UsbUtils.findDevice(getDeviceList(), str);
    }

    public void refreshDevices() {
        for (final UsbDevice usbDevice : getDeviceList()) {
            this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.1
                @Override // java.lang.Runnable
                public void run() {
                    USBMonitor.this.mOnDeviceConnectListener.onAttach(usbDevice);
                }
            });
        }
    }

    public boolean isEnablePolling() {
        return this.mEnablePolling;
    }

    public synchronized void setEnablePolling(boolean z) {
        if (this.mEnablePolling != z) {
            this.mEnablePolling = z;
            this.mAsyncHandler.removeCallbacks(this.mDeviceCheckRunnable);
            if (z && isRegistered()) {
                this.mAsyncHandler.postDelayed(this.mDeviceCheckRunnable, 500L);
            }
        }
    }

    public boolean hasPermission(UsbDevice usbDevice) {
        return (this.destroyed || usbDevice == null || !this.mUsbManager.hasPermission(usbDevice)) ? false : true;
    }

    public synchronized boolean requestPermission(UsbDevice usbDevice) throws IllegalStateException {
        boolean z;
        if (!isRegistered()) {
            throw new IllegalStateException("USBMonitor not registered or already destroyed");
        }
        z = true;
        if (usbDevice != null) {
            if (this.mUsbManager.hasPermission(usbDevice)) {
                processPermission(usbDevice);
            } else {
                try {
                    this.mUsbManager.requestPermission(usbDevice, this.mPermissionIntent);
                } catch (Exception e) {
                    Log.w(TAG, e);
                    processCancel(usbDevice);
                }
            }
            z = false;
        } else {
            callOnError(usbDevice, new UsbPermissionException("device is null"));
        }
        return z;
    }

    public UsbControlBlock openDevice(UsbDevice usbDevice) throws IOException {
        if (hasPermission(usbDevice)) {
            return new UsbControlBlock(usbDevice);
        }
        throw new IOException("has no permission or invalid UsbDevice(already disconnected?)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (this.ACTION_USB_PERMISSION.equals(action)) {
            synchronized (this) {
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                if (usbDevice != null && (hasPermission(usbDevice) || intent.getBooleanExtra("permission", false))) {
                    processPermission(usbDevice);
                } else if (usbDevice != null) {
                    processCancel(usbDevice);
                } else {
                    callOnError(usbDevice, new UsbPermissionException("device is null"));
                }
            }
        } else if (Const.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
            if (usbDevice2 != null) {
                processAttach(usbDevice2);
            } else {
                callOnError(usbDevice2, new UsbAttachException("device is null"));
            }
        } else if (Const.ACTION_USB_DEVICE_DETACHED.equals(action)) {
            UsbDevice usbDevice3 = (UsbDevice) intent.getParcelableExtra("device");
            if (usbDevice3 != null) {
                processDettach(usbDevice3);
            } else {
                callOnError(usbDevice3, new UsbDetachException("device is null"));
            }
        }
    }

    private IntentFilter createIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(this.ACTION_USB_PERMISSION);
        if (BuildCheck.isAndroid5()) {
            intentFilter.addAction(Const.ACTION_USB_DEVICE_ATTACHED);
        }
        intentFilter.addAction(Const.ACTION_USB_DEVICE_DETACHED);
        return intentFilter;
    }

    private void processPermission(UsbDevice usbDevice) {
        this.mOnDeviceConnectListener.onPermission(usbDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processConnect(final UsbDevice usbDevice, final UsbControlBlock usbControlBlock) {
        if (this.destroyed) {
            return;
        }
        synchronized (this.mCtrlBlocks) {
            this.mCtrlBlocks.add(usbControlBlock);
        }
        if (hasPermission(usbDevice)) {
            this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.4
                @Override // java.lang.Runnable
                public void run() {
                    USBMonitor.this.mOnDeviceConnectListener.onConnected(usbDevice, usbControlBlock);
                }
            });
        }
    }

    private void processCancel(final UsbDevice usbDevice) {
        if (this.destroyed) {
            return;
        }
        this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.5
            @Override // java.lang.Runnable
            public void run() {
                USBMonitor.this.mOnDeviceConnectListener.onCancel(usbDevice);
            }
        });
    }

    private void processAttach(final UsbDevice usbDevice) {
        if (!this.destroyed && matches(usbDevice)) {
            hasPermission(usbDevice);
            this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.6
                @Override // java.lang.Runnable
                public void run() {
                    USBMonitor.this.mOnDeviceConnectListener.onAttach(usbDevice);
                }
            });
        }
    }

    private void processDettach(final UsbDevice usbDevice) {
        if (!this.destroyed && matches(usbDevice)) {
            removeAll(usbDevice);
            this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.7
                @Override // java.lang.Runnable
                public void run() {
                    USBMonitor.this.mOnDeviceConnectListener.onDetach(usbDevice);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnDisconnect(UsbDevice usbDevice, final UsbControlBlock usbControlBlock) {
        if (this.destroyed) {
            return;
        }
        synchronized (this.mCtrlBlocks) {
            this.mCtrlBlocks.remove(usbControlBlock);
        }
        this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.8
            @Override // java.lang.Runnable
            public void run() {
                USBMonitor.this.mOnDeviceConnectListener.onDisconnect(usbControlBlock.getDevice());
            }
        });
    }

    private void callOnError(final UsbDevice usbDevice, final Throwable th) {
        this.mAsyncHandler.post(new Runnable() { // from class: com.serenegiant.usb.USBMonitor.9
            @Override // java.lang.Runnable
            public void run() {
                USBMonitor.this.mOnDeviceConnectListener.onError(usbDevice, th);
            }
        });
    }

    private List<UsbControlBlock> findCtrlBlocks(UsbDevice usbDevice) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCtrlBlocks) {
            for (UsbControlBlock usbControlBlock : this.mCtrlBlocks) {
                if (usbControlBlock.getDevice().equals(usbDevice)) {
                    arrayList.add(usbControlBlock);
                }
            }
        }
        return arrayList;
    }

    private void removeAll(UsbDevice usbDevice) {
        List<UsbControlBlock> findCtrlBlocks = findCtrlBlocks(usbDevice);
        synchronized (this.mCtrlBlocks) {
            this.mCtrlBlocks.removeAll(findCtrlBlocks);
        }
        for (UsbControlBlock usbControlBlock : findCtrlBlocks) {
            usbControlBlock.close();
        }
    }

    /* loaded from: classes2.dex */
    public static final class UsbControlBlock implements Cloneable {
        private UsbDeviceConnection mConnection;
        private final UsbDeviceInfo mInfo;
        private final SparseArray<SparseArray<UsbInterface>> mInterfaces;
        private final WeakReference<UsbDevice> mWeakDevice;
        private final WeakReference<USBMonitor> mWeakMonitor;

        private UsbControlBlock(USBMonitor uSBMonitor, UsbDevice usbDevice) throws IOException {
            this.mInterfaces = new SparseArray<>();
            this.mWeakMonitor = new WeakReference<>(uSBMonitor);
            this.mWeakDevice = new WeakReference<>(usbDevice);
            try {
                this.mConnection = uSBMonitor.mUsbManager.openDevice(usbDevice);
                String deviceName = usbDevice.getDeviceName();
                UsbDeviceConnection usbDeviceConnection = this.mConnection;
                if (usbDeviceConnection != null) {
                    Log.i(USBMonitor.TAG, String.format(Locale.US, "name=%s,fd=%d,rawDesc=", deviceName, Integer.valueOf(usbDeviceConnection.getFileDescriptor())) + BufferHelper.toHexString(this.mConnection.getRawDescriptors(), 0, 16));
                    this.mInfo = UsbDeviceInfo.getDeviceInfo(uSBMonitor.mUsbManager, usbDevice, (UsbDeviceInfo) null);
                    uSBMonitor.processConnect(usbDevice, this);
                    return;
                }
                throw new IOException("could not connect to device " + deviceName);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        private UsbControlBlock(UsbControlBlock usbControlBlock) throws IllegalStateException {
            this.mInterfaces = new SparseArray<>();
            USBMonitor monitor = usbControlBlock.getMonitor();
            UsbDevice device = usbControlBlock.getDevice();
            if (device != null) {
                UsbDeviceConnection openDevice = monitor.mUsbManager.openDevice(device);
                this.mConnection = openDevice;
                if (openDevice != null) {
                    this.mInfo = UsbDeviceInfo.getDeviceInfo(monitor.mUsbManager, device, (UsbDeviceInfo) null);
                    this.mWeakMonitor = new WeakReference<>(monitor);
                    this.mWeakDevice = new WeakReference<>(device);
                    monitor.processConnect(device, this);
                    return;
                }
                throw new IllegalStateException("device may already be removed or have no permission");
            }
            throw new IllegalStateException("device may already be removed");
        }

        protected void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        public synchronized boolean isValid() {
            return this.mConnection != null;
        }

        /* renamed from: clone */
        public UsbControlBlock m277clone() throws CloneNotSupportedException {
            try {
                return new UsbControlBlock(this);
            } catch (IllegalStateException e) {
                throw new CloneNotSupportedException(e.getMessage());
            }
        }

        public USBMonitor getMonitor() {
            return this.mWeakMonitor.get();
        }

        public UsbDevice getDevice() {
            return this.mWeakDevice.get();
        }

        public UsbDeviceInfo getInfo() {
            return this.mInfo;
        }

        public String getDeviceName() {
            UsbDevice usbDevice = this.mWeakDevice.get();
            return usbDevice != null ? usbDevice.getDeviceName() : "";
        }

        public int getDeviceId() {
            UsbDevice usbDevice = this.mWeakDevice.get();
            if (usbDevice != null) {
                return usbDevice.getDeviceId();
            }
            return 0;
        }

        public synchronized UsbDeviceConnection getConnection() {
            return this.mConnection;
        }

        public synchronized UsbDeviceConnection requireConnection() throws IllegalStateException {
            checkConnection();
            return this.mConnection;
        }

        public synchronized int getFileDescriptor() {
            UsbDeviceConnection usbDeviceConnection;
            usbDeviceConnection = this.mConnection;
            return usbDeviceConnection != null ? usbDeviceConnection.getFileDescriptor() : 0;
        }

        public synchronized int requireFileDescriptor() throws IllegalStateException {
            checkConnection();
            return this.mConnection.getFileDescriptor();
        }

        public synchronized byte[] getRawDescriptors() {
            UsbDeviceConnection usbDeviceConnection;
            checkConnection();
            usbDeviceConnection = this.mConnection;
            return usbDeviceConnection != null ? usbDeviceConnection.getRawDescriptors() : null;
        }

        public synchronized byte[] requireRawDescriptors() throws IllegalStateException {
            checkConnection();
            return this.mConnection.getRawDescriptors();
        }

        public int getVenderId() {
            UsbDevice usbDevice = this.mWeakDevice.get();
            if (usbDevice != null) {
                return usbDevice.getVendorId();
            }
            return 0;
        }

        public int getProductId() {
            UsbDevice usbDevice = this.mWeakDevice.get();
            if (usbDevice != null) {
                return usbDevice.getProductId();
            }
            return 0;
        }

        public String getUsbVersion() {
            return this.mInfo.usb_version;
        }

        public String getManufacture() {
            return this.mInfo.manufacturer;
        }

        public String getProductName() {
            return this.mInfo.product;
        }

        public String getVersion() {
            return this.mInfo.version;
        }

        public String getSerial() {
            return this.mInfo.serial;
        }

        public synchronized UsbInterface getInterface(int i) throws IllegalStateException {
            return getInterface(i, 0);
        }

        public synchronized UsbInterface getInterface(int i, int i2) throws IllegalStateException {
            UsbInterface usbInterface;
            checkConnection();
            SparseArray<UsbInterface> sparseArray = this.mInterfaces.get(i);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                this.mInterfaces.put(i, sparseArray);
            }
            usbInterface = sparseArray.get(i2);
            if (usbInterface == null) {
                UsbDevice usbDevice = this.mWeakDevice.get();
                int interfaceCount = usbDevice.getInterfaceCount();
                int i3 = 0;
                while (true) {
                    if (i3 >= interfaceCount) {
                        break;
                    }
                    UsbInterface usbInterface2 = usbDevice.getInterface(i3);
                    if (usbInterface2.getId() == i && usbInterface2.getAlternateSetting() == i2) {
                        usbInterface = usbInterface2;
                        break;
                    }
                    i3++;
                }
                if (usbInterface != null) {
                    sparseArray.append(i2, usbInterface);
                }
            }
            return usbInterface;
        }

        public synchronized void claimInterface(UsbInterface usbInterface) throws IllegalStateException {
            claimInterface(usbInterface, true);
        }

        public synchronized void claimInterface(UsbInterface usbInterface, boolean z) throws IllegalStateException {
            checkConnection();
            this.mConnection.claimInterface(usbInterface, z);
        }

        public synchronized void releaseInterface(UsbInterface usbInterface) throws IllegalStateException {
            checkConnection();
            SparseArray<UsbInterface> sparseArray = this.mInterfaces.get(usbInterface.getId());
            if (sparseArray != null) {
                sparseArray.removeAt(sparseArray.indexOfValue(usbInterface));
                if (sparseArray.size() == 0) {
                    this.mInterfaces.remove(usbInterface.getId());
                }
            }
            this.mConnection.releaseInterface(usbInterface);
        }

        public synchronized int bulkTransfer(UsbEndpoint usbEndpoint, byte[] bArr, int i, int i2, int i3) throws IllegalStateException {
            checkConnection();
            return this.mConnection.bulkTransfer(usbEndpoint, bArr, i, i2, i3);
        }

        public synchronized int bulkTransfer(UsbEndpoint usbEndpoint, byte[] bArr, int i, int i2) throws IllegalStateException {
            checkConnection();
            return this.mConnection.bulkTransfer(usbEndpoint, bArr, i, i2);
        }

        public synchronized int controlTransfer(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7) throws IllegalStateException {
            checkConnection();
            return this.mConnection.controlTransfer(i, i2, i3, i4, bArr, i5, i6, i7);
        }

        public synchronized int controlTransfer(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6) throws IllegalStateException {
            checkConnection();
            return this.mConnection.controlTransfer(i, i2, i3, i4, bArr, i5, i6);
        }

        public void close() {
            UsbDeviceConnection usbDeviceConnection;
            synchronized (this) {
                usbDeviceConnection = this.mConnection;
                this.mConnection = null;
            }
            if (usbDeviceConnection != null) {
                int size = this.mInterfaces.size();
                for (int i = 0; i < size; i++) {
                    SparseArray<UsbInterface> valueAt = this.mInterfaces.valueAt(i);
                    if (valueAt != null) {
                        int size2 = valueAt.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            usbDeviceConnection.releaseInterface(valueAt.valueAt(i2));
                        }
                        valueAt.clear();
                    }
                }
                this.mInterfaces.clear();
                usbDeviceConnection.close();
                USBMonitor monitor = getMonitor();
                UsbDevice device = getDevice();
                if (monitor == null || device == null) {
                    return;
                }
                monitor.callOnDisconnect(device, this);
            }
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof UsbControlBlock) {
                UsbDevice device = ((UsbControlBlock) obj).getDevice();
                if (device == null) {
                    return this.mWeakDevice.get() == null;
                }
                return device.equals(this.mWeakDevice.get());
            } else if (obj instanceof UsbDevice) {
                return obj.equals(this.mWeakDevice.get());
            } else {
                return super.equals(obj);
            }
        }

        private synchronized void checkConnection() throws IllegalStateException {
            if (this.mConnection == null) {
                throw new IllegalStateException("already closed");
            }
        }
    }
}
