package com.epson.iprojection.ui;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface MirroringAidlActivity extends IInterface {
    public static final String DESCRIPTOR = "com.epson.iprojection.ui.MirroringAidlActivity";

    /* loaded from: classes.dex */
    public static class Default implements MirroringAidlActivity {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.epson.iprojection.ui.MirroringAidlActivity
        public void callActivityFunc() throws RemoteException {
        }

        @Override // com.epson.iprojection.ui.MirroringAidlActivity
        public boolean isPaused() throws RemoteException {
            return false;
        }

        @Override // com.epson.iprojection.ui.MirroringAidlActivity
        public void onRotated(int i) throws RemoteException {
        }

        @Override // com.epson.iprojection.ui.MirroringAidlActivity
        public void pause() throws RemoteException {
        }

        @Override // com.epson.iprojection.ui.MirroringAidlActivity
        public void resume() throws RemoteException {
        }
    }

    void callActivityFunc() throws RemoteException;

    boolean isPaused() throws RemoteException;

    void onRotated(int i) throws RemoteException;

    void pause() throws RemoteException;

    void resume() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements MirroringAidlActivity {
        static final int TRANSACTION_callActivityFunc = 1;
        static final int TRANSACTION_isPaused = 2;
        static final int TRANSACTION_onRotated = 5;
        static final int TRANSACTION_pause = 3;
        static final int TRANSACTION_resume = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, MirroringAidlActivity.DESCRIPTOR);
        }

        public static MirroringAidlActivity asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(MirroringAidlActivity.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof MirroringAidlActivity)) {
                return (MirroringAidlActivity) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(MirroringAidlActivity.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(MirroringAidlActivity.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                callActivityFunc();
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean isPaused = isPaused();
                parcel2.writeNoException();
                parcel2.writeInt(isPaused ? 1 : 0);
            } else if (i == 3) {
                pause();
                parcel2.writeNoException();
            } else if (i == 4) {
                resume();
                parcel2.writeNoException();
            } else if (i == 5) {
                onRotated(parcel.readInt());
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements MirroringAidlActivity {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return MirroringAidlActivity.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.epson.iprojection.ui.MirroringAidlActivity
            public void callActivityFunc() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(MirroringAidlActivity.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.ui.MirroringAidlActivity
            public boolean isPaused() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(MirroringAidlActivity.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.ui.MirroringAidlActivity
            public void pause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(MirroringAidlActivity.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.ui.MirroringAidlActivity
            public void resume() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(MirroringAidlActivity.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.ui.MirroringAidlActivity
            public void onRotated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(MirroringAidlActivity.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
