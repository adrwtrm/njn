package com.epson.iprojection.ui.activities.presen.pdf_filer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.ImgFileUtils;
import com.epson.iprojection.service.pdf.PdfAidlService;
import com.epson.iprojection.ui.PdfAidlActivity;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public class PdfActivity {
    private final Context _context;
    private final IOnReadyListener _readyImpl;
    private PdfAidlService _srvAIDL = null;
    private ServiceConnection _srvConn = null;
    private int _serviceProcessID = -1;
    private boolean _isDisconnectOccured = false;
    private boolean _isAlreadyConnected = false;
    private final PdfAidlActivity bindactivityIf = new PdfAidlActivity.Stub() { // from class: com.epson.iprojection.ui.activities.presen.pdf_filer.PdfActivity.1
        @Override // com.epson.iprojection.ui.PdfAidlActivity
        public void callActivityFunc() throws RemoteException {
        }
    };

    public PdfActivity(IOnReadyListener iOnReadyListener, Context context) {
        this._readyImpl = iOnReadyListener;
        this._context = context;
    }

    public boolean bindService() {
        try {
            Intent intent = new Intent(PdfAidlService.class.getName());
            intent.setPackage(this._context.getPackageName());
            PdfConnectService pdfConnectService = new PdfConnectService();
            this._srvConn = pdfConnectService;
            if (this._context.bindService(intent, pdfConnectService, 1)) {
                Lg.d("bind Service OK");
                return true;
            }
            Lg.e("bind Service NG");
            return false;
        } catch (SecurityException unused) {
            Lg.e("StartService SecurityException");
            return false;
        }
    }

    public void destroy() {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService != null) {
            try {
                pdfAidlService.call_closePDF();
            } catch (RemoteException unused) {
            }
            this._srvAIDL = null;
        }
        try {
            ServiceConnection serviceConnection = this._srvConn;
            if (serviceConnection != null) {
                this._context.unbindService(serviceConnection);
                Intent intent = new Intent(PdfAidlService.class.getName());
                intent.setPackage(this._context.getPackageName());
                this._context.stopService(intent);
                this._srvConn = null;
            }
        } catch (SecurityException unused2) {
        }
    }

    /* loaded from: classes.dex */
    class PdfConnectService implements ServiceConnection {
        PdfConnectService() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Lg.d("onServiceConnected");
            if (PdfActivity.this._isAlreadyConnected) {
                return;
            }
            PdfActivity.this._isAlreadyConnected = true;
            try {
                PdfActivity.this._srvAIDL = PdfAidlService.Stub.asInterface(iBinder);
                PdfActivity.this._srvAIDL.Initialize();
                PdfActivity pdfActivity = PdfActivity.this;
                pdfActivity._serviceProcessID = pdfActivity._srvAIDL.getProcessID();
                Lg.d("サービス pid = " + PdfActivity.this._serviceProcessID);
                PdfActivity.this._readyImpl.onReady();
            } catch (RemoteException e) {
                Lg.e(e.getMessage());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            PdfActivity.this._srvAIDL = null;
            PdfActivity.this._isDisconnectOccured = true;
        }
    }

    boolean IsConneced() {
        return this._srvAIDL != null;
    }

    public Bitmap call_drawPage(int i, int i2, int i3, int i4, int i5, double d, int i6) throws BitmapMemoryException, UnavailableException {
        int i7;
        int i8;
        int i9;
        int i10;
        String call_drawThumbPage;
        try {
            PdfAidlService pdfAidlService = this._srvAIDL;
            if (pdfAidlService == null) {
                return null;
            }
            if (i4 < 1) {
                i8 = i5;
                i7 = 1;
            } else {
                i7 = i4;
                i8 = i5;
            }
            if (i8 < 1) {
                i10 = i6;
                i9 = 1;
            } else {
                i9 = i8;
                i10 = i6;
            }
            if (i10 == 1) {
                call_drawThumbPage = pdfAidlService.call_drawPage(i, i2, i3, i7, i9, d);
            } else {
                call_drawThumbPage = pdfAidlService.call_drawThumbPage(i, i7, i9);
            }
            if (call_drawThumbPage == null) {
                return null;
            }
            Bitmap read = ImgFileUtils.read(call_drawThumbPage);
            FileUtils.deleteFile(call_drawThumbPage);
            return read;
        } catch (RemoteException unused) {
            Lg.e("drawPage error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public void call_setCurrentPageNo(int i) throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return;
        }
        try {
            pdfAidlService.call_setCurrentPageNo(i);
        } catch (RemoteException unused) {
            Lg.e("call_setCurrentPageNo error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public int call_getCurrentPageNo() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return 0;
        }
        try {
            return pdfAidlService.call_getCurrentPageNo();
        } catch (RemoteException unused) {
            Lg.e("call_getCurrentPageNo error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public int call_getTotalPages() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return 0;
        }
        try {
            return pdfAidlService.call_getTotalPages();
        } catch (RemoteException unused) {
            Lg.e("call_getTotalPages error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public double call_getPageWidth(int i) throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return 0.0d;
        }
        try {
            return pdfAidlService.call_getPageWidth(i);
        } catch (RemoteException unused) {
            Lg.e("call_getPageWidth error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public double call_getPageHeight(int i) throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return 0.0d;
        }
        try {
            return pdfAidlService.call_getPageHeight(i);
        } catch (RemoteException unused) {
            Lg.e("call_getPageHeight error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public void call_closePDF() {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return;
        }
        try {
            pdfAidlService.call_closePDF();
        } catch (RemoteException unused) {
            Lg.e("call_closePDF error");
        }
    }

    public boolean call_openPDF(String str) throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return false;
        }
        try {
            return pdfAidlService.call_openPDF(str, "");
        } catch (RemoteException unused) {
            Lg.e("call_openPDF error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public void kill() {
        int i = this._serviceProcessID;
        if (i != -1) {
            Process.killProcess(i);
            this._serviceProcessID = -1;
        }
    }

    public boolean call_isAvailable() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return false;
        }
        try {
            return pdfAidlService.isAvailable();
        } catch (RemoteException unused) {
            Lg.e("isAvailable error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public String call_getFileName() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return null;
        }
        try {
            return pdfAidlService.call_getFileName();
        } catch (RemoteException unused) {
            Lg.e("call_getFileName error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public boolean isDisconnectOccured() {
        return this._isDisconnectOccured;
    }

    public boolean isRendering() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return false;
        }
        try {
            return pdfAidlService.isRendering();
        } catch (RemoteException unused) {
            Lg.e("isRendering error");
            throw new UnavailableException("サービスが利用できません");
        }
    }

    public boolean isThumbRendering() throws UnavailableException {
        PdfAidlService pdfAidlService = this._srvAIDL;
        if (pdfAidlService == null) {
            return false;
        }
        try {
            return pdfAidlService.isThumbRendering();
        } catch (RemoteException unused) {
            Lg.e("isThumbRendering error");
            throw new UnavailableException("サービスが利用できません");
        }
    }
}
