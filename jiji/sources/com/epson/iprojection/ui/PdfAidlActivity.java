package com.epson.iprojection.ui;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface PdfAidlActivity extends IInterface {
    public static final String DESCRIPTOR = "com.epson.iprojection.ui.PdfAidlActivity";

    /* loaded from: classes.dex */
    public static class Default implements PdfAidlActivity {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.epson.iprojection.ui.PdfAidlActivity
        public void callActivityFunc() throws RemoteException {
        }
    }

    void callActivityFunc() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements PdfAidlActivity {
        static final int TRANSACTION_callActivityFunc = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, PdfAidlActivity.DESCRIPTOR);
        }

        public static PdfAidlActivity asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(PdfAidlActivity.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof PdfAidlActivity)) {
                return (PdfAidlActivity) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(PdfAidlActivity.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(PdfAidlActivity.DESCRIPTOR);
                return true;
            } else if (i == 1) {
                callActivityFunc();
                parcel2.writeNoException();
                return true;
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements PdfAidlActivity {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return PdfAidlActivity.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.epson.iprojection.ui.PdfAidlActivity
            public void callActivityFunc() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlActivity.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
