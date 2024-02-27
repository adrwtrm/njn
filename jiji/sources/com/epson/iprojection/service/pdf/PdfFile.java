package com.epson.iprojection.service.pdf;

import android.content.Context;
import com.adobe.mps.MPS;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PathGetter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class PdfFile {
    private final Context mContext;
    private final int COLOR_SPACE_ARGB = 1;
    private String mFilename = "";
    private String mPathPageName = "";
    private int mCurrentPageNum = -1;
    private double mCurrentWidth = 0.0d;
    private double mCurrentHeight = 0.0d;
    private int mTotalPages = -1;
    private final MPS mMPS = new MPS();

    public boolean isPasswardLocked() {
        return false;
    }

    protected int toIndexPage(int i) {
        return i - 1;
    }

    protected int toInternalPage(int i) {
        return i + 1;
    }

    public PdfFile(Context context) {
        this.mContext = context;
    }

    public boolean loadDocument(String str, String str2) {
        this.mMPS.MPSInit("com/adobe/mps/ARAESCryptor", "com/adobe/mps/ARSHADigest");
        this.mFilename = str;
        this.mCurrentWidth = 0.0d;
        this.mCurrentHeight = 0.0d;
        this.mTotalPages = -1;
        PathGetter.getIns().initialize(this.mContext);
        this.mPathPageName = PathGetter.getIns().getCacheDirPath() + "/%s-page%04d.jpg";
        int PDFDocInit = this.mMPS.PDFDocInit(str);
        this.mTotalPages = PDFDocInit;
        if (PDFDocInit > 0) {
            Lg.d("Succeeded to load document. [" + this.mFilename + "]");
            return true;
        }
        Lg.e("Failed to load document. [" + this.mFilename + "]");
        return false;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:(2:25|(15:27|28|(1:80)|32|(1:34)|(1:36)(1:79)|37|(1:39)|(1:41)(1:78)|42|43|44|45|46|(2:48|49)(5:50|51|52|53|54)))|43|44|45|46|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01e3, code lost:
        java.lang.System.gc();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01e7, code lost:
        if (r3 > 1) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01e9, code lost:
        r3 = r3 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01eb, code lost:
        if (r11 > 1) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01ed, code lost:
        r11 = r11 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01ef, code lost:
        r4 = java.nio.ByteBuffer.allocateDirect((r3 * r11) * 4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01f7, code lost:
        r16 = 2.0d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01f9, code lost:
        r12 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01fb, code lost:
        java.lang.System.gc();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01ff, code lost:
        if (r3 > 1) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0201, code lost:
        r3 = r3 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0203, code lost:
        if (r11 > 1) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0205, code lost:
        r11 = r11 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0207, code lost:
        r4 = java.nio.ByteBuffer.allocateDirect((r3 * r11) * 4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x020f, code lost:
        r16 = 4.0d;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x023b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String renderPage(int r33, int r34, int r35, int r36, int r37, double r38) {
        /*
            Method dump skipped, instructions count: 865
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.service.pdf.PdfFile.renderPage(int, int, int, int, int, double):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0240, code lost:
        if (r3 == r0) goto L56;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0207  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String renderPageScaleDown(int r20, int r21, int r22, int r23, int r24, double r25) {
        /*
            Method dump skipped, instructions count: 637
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.service.pdf.PdfFile.renderPageScaleDown(int, int, int, int, int, double):java.lang.String");
    }

    public String renderPageThumb(int i, int i2, int i3) {
        int internalPage = toInternalPage(i);
        String createFileName = createFileName(i);
        this.mMPS.PDFPagetoImage(internalPage, createFileName, i2, i3, 1);
        return createFileName;
    }

    public double getPageWidth(int i) {
        int[] PDFGetPageAttributes;
        double d = this.mMPS.PDFGetPageAttributes(toInternalPage(i))[0];
        this.mCurrentWidth = d;
        this.mCurrentHeight = PDFGetPageAttributes[1];
        return d;
    }

    public double getPageHeight(int i) {
        int[] PDFGetPageAttributes = this.mMPS.PDFGetPageAttributes(toInternalPage(i));
        this.mCurrentWidth = PDFGetPageAttributes[0];
        double d = PDFGetPageAttributes[1];
        this.mCurrentHeight = d;
        return d;
    }

    public void setCurrentPageNo(int i) {
        int internalPage = toInternalPage(i);
        this.mCurrentPageNum = internalPage;
        int[] PDFGetPageAttributes = this.mMPS.PDFGetPageAttributes(internalPage);
        this.mCurrentWidth = PDFGetPageAttributes[0];
        this.mCurrentHeight = PDFGetPageAttributes[1];
    }

    public int getCurrentPageNo() {
        return toIndexPage(this.mCurrentPageNum);
    }

    public int getCountPages() {
        return this.mTotalPages;
    }

    private String createFileName(int i) {
        String name = new File(this.mFilename).getName();
        return String.format(this.mPathPageName, name.substring(0, name.lastIndexOf(".")), Integer.valueOf(i));
    }

    public byte[] getAssetBytes(String str) {
        try {
            InputStream open = this.mContext.getResources().getAssets().open(str);
            if (open == null) {
                Lg.e("Book2pngCannot locate asset: " + str);
                if (open != null) {
                    open.close();
                }
                return null;
            }
            byte[] streamToBytes = streamToBytes(open);
            if (open != null) {
                open.close();
            }
            return streamToBytes;
        } catch (IOException e) {
            Lg.e("Book2pnggetAssetBytes IOException: " + e);
            return null;
        }
    }

    private byte[] streamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException e) {
                Lg.e("Book2pngstreamToBytes IOException: " + e);
            }
        }
        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
