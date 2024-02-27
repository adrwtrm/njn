package com.epson.iprojection.service.pdf;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.pdf.PdfAidlService;
import java.io.File;

/* loaded from: classes.dex */
public class PdfService extends Service {
    private PdfFile _pdfFile = null;
    private boolean _isOpenedDoc = false;
    private boolean _isMainDrawing = false;
    private boolean _isThumbDrawing = false;
    private String _fileName = null;
    private boolean _isProceedingPdfFile = false;
    private final PdfAidlService.Stub bindserviceIf = new PdfAidlService.Stub() { // from class: com.epson.iprojection.service.pdf.PdfService.1
        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public void Initialize() {
            if (PdfService.this._pdfFile == null) {
                try {
                    PdfService.this._pdfFile = new PdfFile(PdfService.this.getApplicationContext());
                } catch (Exception unused) {
                    Lg.e("Cannot create PDF Renderer object");
                }
            }
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public int getProcessID() {
            return Process.myPid();
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean call_openPDF(String str, String str2) {
            if (PdfService.this._pdfFile == null) {
                return false;
            }
            if (PdfService.this._isMainDrawing) {
                Lg.i("[pdf service]レンダリング中です");
                return false;
            }
            try {
                boolean parsePDF = parsePDF(str, str2);
                PdfService.this._fileName = str;
                PdfService.this._isOpenedDoc = parsePDF;
                return parsePDF;
            } catch (Exception e) {
                Lg.e("GOT ERROR WHEN CONVERT: " + e);
                return false;
            }
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_drawThumbPage(int i, int i2, int i3) {
            PdfService.this._isThumbDrawing = true;
            String renderPageThumb = PdfService.this._pdfFile.renderPageThumb(i, i2, i3);
            PdfService.this._isThumbDrawing = false;
            return renderPageThumb;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_drawPage(int i, int i2, int i3, int i4, int i5, double d) {
            PdfService.this._isMainDrawing = true;
            String renderPage = PdfService.this._pdfFile.renderPage(i, i2, i3, i4, i5, d);
            PdfService.this._isMainDrawing = false;
            return renderPage;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public synchronized double call_getPageWidth(int i) {
            double pageWidth;
            PdfService.this._isProceedingPdfFile = true;
            pageWidth = PdfService.this._pdfFile.getPageWidth(i);
            PdfService.this._isProceedingPdfFile = false;
            return pageWidth;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public synchronized double call_getPageHeight(int i) {
            double pageHeight;
            PdfService.this._isProceedingPdfFile = true;
            pageHeight = PdfService.this._pdfFile.getPageHeight(i);
            PdfService.this._isProceedingPdfFile = false;
            return pageHeight;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public void call_closePDF() {
            if (PdfService.this._isOpenedDoc) {
                PdfService.this._isOpenedDoc = false;
            }
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public synchronized int call_getCurrentPageNo() {
            int currentPageNo;
            PdfService.this._isProceedingPdfFile = true;
            currentPageNo = PdfService.this._pdfFile.getCurrentPageNo();
            PdfService.this._isProceedingPdfFile = false;
            return currentPageNo;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public synchronized int call_getTotalPages() {
            int countPages;
            PdfService.this._isProceedingPdfFile = true;
            countPages = PdfService.this._pdfFile.getCountPages();
            PdfService.this._isProceedingPdfFile = false;
            return countPages;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isAvailable() {
            return PdfService.this._isOpenedDoc;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public String call_getFileName() {
            return PdfService.this._fileName;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isRendering() {
            return PdfService.this._isMainDrawing;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public boolean isThumbRendering() {
            return PdfService.this._isThumbDrawing;
        }

        @Override // com.epson.iprojection.service.pdf.PdfAidlService
        public synchronized void call_setCurrentPageNo(int i) {
            PdfService.this._isProceedingPdfFile = true;
            PdfService.this._pdfFile.setCurrentPageNo(i);
            PdfService.this._isProceedingPdfFile = false;
        }

        private synchronized boolean parsePDF(String str, String str2) {
            try {
                if (new File(str).exists()) {
                    PdfService.this._isProceedingPdfFile = true;
                    boolean loadDocument = PdfService.this._pdfFile.loadDocument(str, str2);
                    PdfService.this._isProceedingPdfFile = false;
                    if (PdfService.this._pdfFile.isPasswardLocked()) {
                        Lg.d("Password Locked.");
                        return false;
                    }
                    return loadDocument;
                }
                return false;
            } catch (Throwable th) {
                th.printStackTrace();
                return false;
            }
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Lg.d("onCreate");
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Lg.d("onUnbind");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Lg.d("onBind");
        if (PdfAidlService.class.getName().equals(intent.getAction())) {
            return this.bindserviceIf;
        }
        return null;
    }

    public void onDestory() {
        Lg.d("onDestroy()");
        if (this._pdfFile != null) {
            this._pdfFile = null;
        }
        super.onDestroy();
    }
}
