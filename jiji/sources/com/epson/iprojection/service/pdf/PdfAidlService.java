package com.epson.iprojection.service.pdf;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface PdfAidlService extends IInterface {
    public static final String DESCRIPTOR = "com.epson.iprojection.service.pdf.PdfAidlService";

    /* loaded from: classes.dex */
    public static class Default implements PdfAidlService {
        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public void Initialize() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public void call_closePDF() throws RemoteException {
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_drawPage(int i, int i2, int i3, int i4, int i5, double d) throws RemoteException {
            return null;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_drawThumbPage(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public int call_getCurrentPageNo() throws RemoteException {
            return 0;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_getFileName() throws RemoteException {
            return null;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public double call_getPageHeight(int i) throws RemoteException {
            return 0.0d;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public double call_getPageWidth(int i) throws RemoteException {
            return 0.0d;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public int call_getTotalPages() throws RemoteException {
            return 0;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean call_openPDF(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public void call_setCurrentPageNo(int i) throws RemoteException {
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public int getProcessID() throws RemoteException {
            return 0;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isAvailable() throws RemoteException {
            return false;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isRendering() throws RemoteException {
            return false;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isThumbRendering() throws RemoteException {
            return false;
        }
    }

    void Initialize() throws RemoteException;

    void call_closePDF() throws RemoteException;

    String call_drawPage(int i, int i2, int i3, int i4, int i5, double d) throws RemoteException;

    String call_drawThumbPage(int i, int i2, int i3) throws RemoteException;

    int call_getCurrentPageNo() throws RemoteException;

    String call_getFileName() throws RemoteException;

    double call_getPageHeight(int i) throws RemoteException;

    double call_getPageWidth(int i) throws RemoteException;

    int call_getTotalPages() throws RemoteException;

    boolean call_openPDF(String str, String str2) throws RemoteException;

    void call_setCurrentPageNo(int i) throws RemoteException;

    int getProcessID() throws RemoteException;

    boolean isAvailable() throws RemoteException;

    boolean isRendering() throws RemoteException;

    boolean isThumbRendering() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements PdfAidlService {
        static final int TRANSACTION_Initialize = 1;
        static final int TRANSACTION_call_closePDF = 7;
        static final int TRANSACTION_call_drawPage = 10;
        static final int TRANSACTION_call_drawThumbPage = 9;
        static final int TRANSACTION_call_getCurrentPageNo = 14;
        static final int TRANSACTION_call_getFileName = 15;
        static final int TRANSACTION_call_getPageHeight = 12;
        static final int TRANSACTION_call_getPageWidth = 11;
        static final int TRANSACTION_call_getTotalPages = 13;
        static final int TRANSACTION_call_openPDF = 6;
        static final int TRANSACTION_call_setCurrentPageNo = 8;
        static final int TRANSACTION_getProcessID = 2;
        static final int TRANSACTION_isAvailable = 3;
        static final int TRANSACTION_isRendering = 4;
        static final int TRANSACTION_isThumbRendering = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, PdfAidlService.DESCRIPTOR);
        }

        public static PdfAidlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(PdfAidlService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof PdfAidlService)) {
                return (PdfAidlService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(PdfAidlService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(PdfAidlService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Initialize();
                    parcel2.writeNoException();
                    break;
                case 2:
                    int processID = getProcessID();
                    parcel2.writeNoException();
                    parcel2.writeInt(processID);
                    break;
                case 3:
                    boolean isAvailable = isAvailable();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvailable ? 1 : 0);
                    break;
                case 4:
                    boolean isRendering = isRendering();
                    parcel2.writeNoException();
                    parcel2.writeInt(isRendering ? 1 : 0);
                    break;
                case 5:
                    boolean isThumbRendering = isThumbRendering();
                    parcel2.writeNoException();
                    parcel2.writeInt(isThumbRendering ? 1 : 0);
                    break;
                case 6:
                    boolean call_openPDF = call_openPDF(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(call_openPDF ? 1 : 0);
                    break;
                case 7:
                    call_closePDF();
                    parcel2.writeNoException();
                    break;
                case 8:
                    call_setCurrentPageNo(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 9:
                    String call_drawThumbPage = call_drawThumbPage(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(call_drawThumbPage);
                    break;
                case 10:
                    String call_drawPage = call_drawPage(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readDouble());
                    parcel2.writeNoException();
                    parcel2.writeString(call_drawPage);
                    break;
                case 11:
                    double call_getPageWidth = call_getPageWidth(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeDouble(call_getPageWidth);
                    break;
                case 12:
                    double call_getPageHeight = call_getPageHeight(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeDouble(call_getPageHeight);
                    break;
                case 13:
                    int call_getTotalPages = call_getTotalPages();
                    parcel2.writeNoException();
                    parcel2.writeInt(call_getTotalPages);
                    break;
                case 14:
                    int call_getCurrentPageNo = call_getCurrentPageNo();
                    parcel2.writeNoException();
                    parcel2.writeInt(call_getCurrentPageNo);
                    break;
                case 15:
                    String call_getFileName = call_getFileName();
                    parcel2.writeNoException();
                    parcel2.writeString(call_getFileName);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        /* loaded from: classes.dex */
        private static class Proxy implements PdfAidlService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return PdfAidlService.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public void Initialize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public int getProcessID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public boolean isAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public boolean isRendering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public boolean isThumbRendering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public boolean call_openPDF(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public void call_closePDF() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public void call_setCurrentPageNo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public String call_drawThumbPage(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public String call_drawPage(int i, int i2, int i3, int i4, int i5, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeDouble(d);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public double call_getPageWidth(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readDouble();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public double call_getPageHeight(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readDouble();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public int call_getTotalPages() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public int call_getCurrentPageNo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.epson.iprojection.service.pdf.PdfAidlService
            public String call_getFileName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(PdfAidlService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
