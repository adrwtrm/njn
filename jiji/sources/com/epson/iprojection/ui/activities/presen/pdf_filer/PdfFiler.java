package com.epson.iprojection.ui.activities.presen.pdf_filer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.ui.activities.presen.Defines;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public class PdfFiler implements IFiler, IOnReadyListener {
    private Context _context;
    private IOnReadyListener _readyImpl;
    private PdfActivity _actAIDL = null;
    private boolean _isConnected = false;
    private String _fileName = null;
    private int _totalPages = 0;

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean exists(String str) {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public String getFilePath(int i) throws UnavailableException {
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public Bitmap getImage(int i, int i2, int i3) throws BitmapMemoryException, UnavailableException {
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getPos(String str) {
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public Uri getUri(int i) throws UnavailableException {
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isFileChanged(String str) {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isScalable() {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void showOpenError(Context context) {
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean Initialize(Context context, String str, IOnReadyListener iOnReadyListener, boolean z) {
        this._context = context;
        this._readyImpl = iOnReadyListener;
        this._fileName = str;
        this._isConnected = false;
        PdfActivity pdfActivity = new PdfActivity(this, context);
        this._actAIDL = pdfActivity;
        return pdfActivity.bindService();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean initFile() throws UnavailableException {
        if (this._actAIDL.call_openPDF(this._fileName)) {
            this._totalPages = this._actAIDL.call_getTotalPages();
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener
    public boolean onReady() {
        this._isConnected = true;
        this._readyImpl.onReady();
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void destroy() {
        this._actAIDL.destroy();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getTotalPages() {
        int i = this._totalPages;
        if (i == 0) {
            return 1;
        }
        return i;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized Bitmap getImage(int i, int i2, int i3, int i4) throws BitmapMemoryException, UnavailableException {
        Bitmap drawPage;
        if (this._isConnected) {
            int i5 = Defines.PDF_MAX_W;
            if (i2 <= 1280) {
                i5 = i2;
            }
            int i6 = Defines.PDF_MAX_H;
            if (i3 <= 800) {
                i6 = i3;
            }
            if (i5 < 0) {
                Lg.e("Invalid width size[" + i5 + "]");
            }
            if (i6 < 0) {
                Lg.e("Invalid height size[" + i6 + "]");
            }
            int realW = getRealW(i);
            int realH = getRealH(i);
            if (realW == 0) {
                realW = 1;
            }
            if (realH == 0) {
                realH = 1;
            }
            ResRect resRect = new ResRect(0, 0, realW, realH);
            if (i4 == 1) {
                this._actAIDL.call_setCurrentPageNo(i);
                double rectFitWithIn = FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, i5, i6));
                drawPage = drawPage(i, 0, 0, (int) ((realW * rectFitWithIn) + 0.01d), (int) ((realH * rectFitWithIn) + 0.01d), rectFitWithIn, i4);
            } else {
                drawPage = drawPage(i, 0, 0, resRect.w, resRect.h, FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, ThumbMgr.getThumbWidth((Activity) this._context), ThumbMgr.getThumbHeight((Activity) this._context))), i4);
            }
            return drawPage;
        }
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public Bitmap getExtendImage(int i, int i2, int i3, int i4, int i5, double d, int i6) throws BitmapMemoryException, UnavailableException {
        if (this._isConnected) {
            return drawPage(i, i2, i3, i4, i5, d, i6);
        }
        return null;
    }

    private synchronized Bitmap drawPage(int i, int i2, int i3, int i4, int i5, double d, int i6) throws BitmapMemoryException, UnavailableException {
        return this._actAIDL.call_drawPage(i, i2, i3, i4, i5, d, i6);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getCurrentPage() throws UnavailableException {
        return this._actAIDL.call_getCurrentPageNo();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized int getRealW(int i) throws UnavailableException {
        return (int) this._actAIDL.call_getPageWidth(i);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized int getRealH(int i) throws UnavailableException {
        return (int) this._actAIDL.call_getPageHeight(i);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public String getFileName(int i) {
        return this._fileName;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void kill() {
        this._actAIDL.kill();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isAvailable() throws UnavailableException {
        return this._actAIDL.call_isAvailable();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean shouldReInit() throws UnavailableException {
        String call_getFileName = this._actAIDL.call_getFileName();
        return (call_getFileName == null || call_getFileName.compareTo(this._fileName) == 0) ? false : true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isDisconnectOccured() {
        return this._actAIDL.isDisconnectOccured();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isRendering() throws UnavailableException {
        return this._actAIDL.isRendering();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isThumbRendering() throws UnavailableException {
        return this._actAIDL.isThumbRendering();
    }
}
