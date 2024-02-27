package com.epson.iprojection.service.mirroring;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IMirroringService extends IInterface {
    public static final String DESCRIPTOR = "com.epson.iprojection.service.mirroring.IMirroringService";

    /* loaded from: classes.dex */
    public static class Default implements IMirroringService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void finish() throws RemoteException {
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void onChangeAudioSettings(boolean z) throws RemoteException {
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void onChangeMPPControlMode() throws RemoteException {
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void start(Bundle bundle) throws RemoteException {
        }
    }

    void finish() throws RemoteException;

    void onChangeAudioSettings(boolean z) throws RemoteException;

    void onChangeMPPControlMode() throws RemoteException;

    void start(Bundle bundle) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IMirroringService {
        static final int TRANSACTION_finish = 2;
        static final int TRANSACTION_onChangeAudioSettings = 4;
        static final int TRANSACTION_onChangeMPPControlMode = 3;
        static final int TRANSACTION_start = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMirroringService.DESCRIPTOR);
        }

        public static IMirroringService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMirroringService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMirroringService)) {
                return (IMirroringService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMirroringService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMirroringService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                start((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
            } else if (i == 2) {
                finish();
                parcel2.writeNoException();
            } else if (i == 3) {
                onChangeMPPControlMode();
                parcel2.writeNoException();
            } else if (i == 4) {
                onChangeAudioSettings(parcel.readInt() != 0);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IMirroringService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return IMirroringService.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.epson.iprojection.service.mirroring.IMirroringService
            public void start(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMirroringService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.mirroring.IMirroringService
            public void finish() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMirroringService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.mirroring.IMirroringService
            public void onChangeMPPControlMode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMirroringService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.mirroring.IMirroringService
            public void onChangeAudioSettings(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMirroringService.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
                return;
            }
            parcel.writeInt(0);
        }
    }
}
