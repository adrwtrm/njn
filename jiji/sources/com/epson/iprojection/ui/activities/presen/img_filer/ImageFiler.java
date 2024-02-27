package com.epson.iprojection.ui.activities.presen.img_filer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.serenegiant.widget.ProgressView;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class ImageFiler implements IFiler {
    protected Context _context;
    protected boolean _isDelivery;
    protected LinkedList<ImageFileInfo> _imgFileList = null;
    protected final ResRect _srcRect = new ResRect();
    protected final ResRect _dstRect = new ResRect();

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void destroy() {
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getCurrentPage() throws UnavailableException {
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getRealH(int i) {
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getRealW(int i) {
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean initFile() {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isAvailable() {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isDisconnectOccured() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isRendering() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isScalable() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isThumbRendering() throws UnavailableException {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void kill() {
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean shouldReInit() {
        return false;
    }

    public ImageFiler(Context context, boolean z) {
        this._context = context;
        this._isDelivery = z;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean Initialize(Context context, String str, IOnReadyListener iOnReadyListener, boolean z) {
        LinkedList<ImageFileInfo> imageFileList = getImageFileList(str);
        this._imgFileList = imageFileList;
        if (imageFileList == null) {
            Lg.e("画像ファイルリストが取得できませんでした");
            return false;
        }
        SortFileList(context);
        if (z) {
            iOnReadyListener.onReady();
            return true;
        }
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getPos(String str) {
        int size = this._imgFileList.size();
        for (int i = 0; i < size; i++) {
            if (this._imgFileList.get(i).getPath().compareTo(str) == 0) {
                return i;
            }
        }
        Lg.e("対応するファイルが見つかりませんでした");
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public int getTotalPages() {
        return this._imgFileList.size();
    }

    public int getRefreshedTotalPages(String str) {
        LinkedList<ImageFileInfo> imageFileList = getImageFileList(str);
        if (imageFileList == null) {
            return 0;
        }
        return imageFileList.size();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized Bitmap getImage(int i, int i2, int i3) throws BitmapMemoryException {
        LinkedList<ImageFileInfo> linkedList = this._imgFileList;
        if (linkedList == null) {
            Lg.e("is not initialized yet.");
            return null;
        }
        if (i >= 0 && linkedList.size() > i) {
            String path = this._imgFileList.get(i).getPath();
            if (path == null) {
                return null;
            }
            Bitmap imageTmp = getImageTmp(path, i2 * 2, i3 * 2);
            if (imageTmp == null) {
                return null;
            }
            Bitmap fitImage = getFitImage(getJpegRotation(path), imageTmp, i2, i3);
            if (fitImage.getConfig() == Bitmap.Config.ARGB_8888) {
                Bitmap copy = BitmapUtils.copy(fitImage, Bitmap.Config.RGB_565, true);
                fitImage.recycle();
                return copy;
            }
            if (fitImage != imageTmp) {
                imageTmp.recycle();
            }
            return fitImage;
        }
        return null;
    }

    public LinkedList<ImageFileInfo> getImageFileList(String str) {
        if (str == null) {
            return null;
        }
        LinkedList<ImageFileInfo> linkedList = new LinkedList<>();
        File[] listFiles = new File(str.substring(0, str.lastIndexOf("/"))).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (FileUtils.isImageFile(file.getName())) {
                    linkedList.add(new ImageFileInfo(file.getAbsolutePath(), file.lastModified()));
                }
            }
        } else if (str.startsWith(Environment.getDataDirectory().getPath())) {
            File file2 = new File(str);
            if (FileUtils.isImageFile(str)) {
                linkedList.add(new ImageFileInfo(file2.getAbsolutePath(), file2.lastModified()));
            }
        }
        if (linkedList.size() == 0) {
            return null;
        }
        return linkedList;
    }

    private Bitmap getImageTmp(String str, int i, int i2) throws BitmapMemoryException {
        if (str == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int max = Math.max(((options.outWidth - 1) / i) + 1, ((options.outHeight - 1) / i2) + 1);
        options.inJustDecodeBounds = false;
        options.inSampleSize = max;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapUtils.decodeFile(str, options);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Bitmap getFitImage(int i, Bitmap bitmap, int i2, int i3) throws BitmapMemoryException {
        if (i == 90 || i == 270) {
            this._srcRect.h = bitmap.getWidth();
            this._srcRect.w = bitmap.getHeight();
        } else {
            this._srcRect.w = bitmap.getWidth();
            this._srcRect.h = bitmap.getHeight();
        }
        this._dstRect.w = i2;
        this._dstRect.h = i3;
        float f = (float) FitResolution.get(this._srcRect, this._dstRect);
        Matrix matrix = new Matrix();
        matrix.setRotate(i);
        matrix.postScale(f, f);
        RectF rectF = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        matrix.mapRect(rectF);
        matrix.postTranslate(-rectF.left, -rectF.top);
        if (matrix.isIdentity() && bitmap.getConfig() == Bitmap.Config.RGB_565) {
            return bitmap;
        }
        int round = Math.round(rectF.width());
        int round2 = Math.round(rectF.height());
        if (round < 1.0d) {
            round = 1;
        }
        if (round2 < 1.0d) {
            round2 = 1;
        }
        Bitmap createBitmap = Bitmap.createBitmap(round, round2, Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas();
        canvas.setBitmap(createBitmap);
        canvas.drawBitmap(bitmap, matrix, paint);
        if (bitmap != createBitmap) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public String getFileName(int i) {
        if (i >= this._imgFileList.size()) {
            Lg.e("取得位置が不正です");
        }
        return FileUtils.getFileName(this._imgFileList.get(i).getPath());
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public void showOpenError(Context context) {
        ToastMgr.getIns().show(context, ToastMgr.Type.FileOpenError);
    }

    public static int getJpegRotation(String str) {
        if (str == null) {
            return 0;
        }
        if (str.endsWith(".jpg") || str.endsWith(".JPG")) {
            try {
                String attribute = new ExifInterface(str).getAttribute("Orientation");
                if (attribute != null && attribute.length() > 0) {
                    int parseInt = Integer.parseInt(attribute);
                    if (parseInt == 3) {
                        return 180;
                    }
                    if (parseInt == 6) {
                        return 90;
                    }
                    if (parseInt == 8) {
                        return ProgressView.DIRECTION_TOP_TO_BOTTOM;
                    }
                }
            } catch (Exception unused) {
                Lg.e("JPEG Eixf情報が読み込めませんでした。");
            }
            return 0;
        }
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized Bitmap getExtendImage(int i, int i2, int i3, int i4, int i5, double d, int i6) throws BitmapMemoryException, UnavailableException {
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public Bitmap getImage(int i, int i2, int i3, int i4) throws BitmapMemoryException, UnavailableException {
        return getImage(i, i2, i3);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean exists(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).exists();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean isFileChanged(String str) {
        LinkedList<ImageFileInfo> imageFileList = getImageFileList(str);
        if (imageFileList != null && this._imgFileList != null) {
            if (imageFileList.size() != this._imgFileList.size()) {
                return true;
            }
            Iterator<ImageFileInfo> it = this._imgFileList.iterator();
            while (it.hasNext()) {
                if (!contains(it.next(), imageFileList)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean contains(ImageFileInfo imageFileInfo, LinkedList<ImageFileInfo> linkedList) {
        Iterator<ImageFileInfo> it = linkedList.iterator();
        while (it.hasNext()) {
            if (imageFileInfo.getPath().equals(it.next().getPath())) {
                return true;
            }
        }
        return false;
    }

    public void SortFileList(Context context) {
        int readInt = PrefUtils.readInt(context, PrefTagDefine.PRESEN_DISPLAY_ORDER_TAG);
        if (PrefUtils.readInt(context, PrefTagDefine.PRESEN_SORT_ORDER_TAG) == 0 || this._isDelivery) {
            this._imgFileList.sort(new FilenameSort());
        } else {
            this._imgFileList.sort(new DateSort());
        }
        if (readInt != 0 || this._isDelivery) {
            Collections.reverse(this._imgFileList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class DateSort implements Comparator<ImageFileInfo> {
        DateSort() {
        }

        @Override // java.util.Comparator
        public int compare(ImageFileInfo imageFileInfo, ImageFileInfo imageFileInfo2) {
            if (imageFileInfo.getLastModified() == 0 || imageFileInfo2.getLastModified() == 0) {
                return -1;
            }
            int i = ((imageFileInfo.getLastModified() - imageFileInfo2.getLastModified()) > 0L ? 1 : ((imageFileInfo.getLastModified() - imageFileInfo2.getLastModified()) == 0L ? 0 : -1));
            if (i == 0) {
                return 0;
            }
            return i > 0 ? 1 : -1;
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public String getFilePath(int i) throws UnavailableException {
        return this._imgFileList.get(i).getPath();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public Uri getUri(int i) throws UnavailableException {
        return this._imgFileList.get(i).getUri();
    }
}
