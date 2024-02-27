package com.epson.iprojection.service.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.epson.iprojection.service.wifi.IWifiServiceCallback;

/* loaded from: classes.dex */
public interface IWifiService extends IInterface {
    public static final String DESCRIPTOR = "com.epson.iprojection.service.wifi.IWifiService";

    /* loaded from: classes.dex */
    public static class Default implements IWifiService {
        @Override // com.epson.iprojection.service.wifi.IWifiService
        public boolean addCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.epson.iprojection.service.wifi.IWifiService
        public void changeWifi(String str, String str2) throws RemoteException {
        }

        @Override // com.epson.iprojection.service.wifi.IWifiService
        public boolean removeCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException {
            return false;
        }

        @Override // com.epson.iprojection.service.wifi.IWifiService
        public void restoreWifi() throws RemoteException {
        }
    }

    boolean addCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException;

    void changeWifi(String str, String str2) throws RemoteException;

    boolean removeCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException;

    void restoreWifi() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IWifiService {
        static final int TRANSACTION_addCallback = 1;
        static final int TRANSACTION_changeWifi = 3;
        static final int TRANSACTION_removeCallback = 2;
        static final int TRANSACTION_restoreWifi = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IWifiService.DESCRIPTOR);
        }

        public static IWifiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWifiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWifiService)) {
                return (IWifiService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWifiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWifiService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean addCallback = addCallback(IWifiServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(addCallback ? 1 : 0);
            } else if (i == 2) {
                boolean removeCallback = removeCallback(IWifiServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(removeCallback ? 1 : 0);
            } else if (i == 3) {
                changeWifi(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
            } else if (i == 4) {
                restoreWifi();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IWifiService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return IWifiService.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.epson.iprojection.service.wifi.IWifiService
            public boolean addCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iWifiServiceCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.wifi.IWifiService
            public boolean removeCallback(IWifiServiceCallback iWifiServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iWifiServiceCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.wifi.IWifiService
            public void changeWifi(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.wifi.IWifiService
            public void restoreWifi() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWifiService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
