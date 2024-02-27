package com.epson.iprojection.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.StopWatch;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class ImgFileUtils {
    public static Bitmap read(String str) throws BitmapMemoryException {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            Bitmap decodeStream = BitmapUtils.decodeStream(fileInputStream);
            try {
                fileInputStream.close();
                return decodeStream;
            } catch (IOException unused) {
                return null;
            }
        } catch (FileNotFoundException unused2) {
            Lg.i("[" + str + "]が開けませんでした。");
            return null;
        }
    }

    public static Bitmap readFromAppData(Context context, String str) throws BitmapMemoryException {
        Bitmap bitmap = null;
        try {
            FileInputStream openFileInput = context.openFileInput(str);
            bitmap = BitmapUtils.decodeStream(openFileInput);
            if (openFileInput != null) {
                openFileInput.close();
            }
        } catch (IOException unused) {
            Lg.e("file error");
        }
        return bitmap;
    }

    public static Bitmap readFromOtherAppData(Context context, String str, String str2) throws BitmapMemoryException {
        try {
            try {
                FileInputStream openFileInput = context.createPackageContext(str, 0).openFileInput(str2);
                Bitmap decodeStream = BitmapUtils.decodeStream((InputStream) openFileInput);
                try {
                    openFileInput.close();
                    return decodeStream;
                } catch (IOException unused) {
                    return null;
                }
            } catch (FileNotFoundException unused2) {
                Lg.e("ファイルが開けませんでした。");
                return null;
            }
        } catch (PackageManager.NameNotFoundException unused3) {
            Lg.e("パッケージが見つかりませんでした");
            return null;
        }
    }

    public static Bitmap readFromCache(String str) throws BitmapMemoryException {
        return read(PathGetter.getIns().getCacheDirPath() + "/" + str);
    }

    public static boolean write(Bitmap bitmap, String str, int i, int i2) throws BitmapMemoryException {
        Bitmap createBitmapFitWithIn = BitmapUtils.createBitmapFitWithIn(bitmap, i, i2);
        boolean write = write(createBitmapFitWithIn, str);
        if (bitmap != createBitmapFitWithIn) {
            createBitmapFitWithIn.recycle();
        }
        return write;
    }

    public static boolean write(Bitmap bitmap, String str) {
        boolean compress;
        if (bitmap == null || str == null) {
            Lg.e("bmpかpathがnullです");
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            if (str.endsWith(".PNG") || str.endsWith(ImageSaver.FILE_TYPE)) {
                compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } else if (str.endsWith(".JPG") || str.endsWith(".jpg") || str.endsWith(".JPEG") || str.endsWith(".jpeg")) {
                compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } else {
                Lg.e("ファイル拡張子の指定が間違っています");
                compress = true;
            }
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
                return compress;
            } catch (IOException unused) {
                Lg.e("fos.flush(); fos.close();");
                return false;
            }
        } catch (FileNotFoundException unused2) {
            Lg.e("openFileOutput()");
            return false;
        }
    }

    public static boolean writeToAppData(Context context, Bitmap bitmap, String str, int i, int i2, int i3) throws BitmapMemoryException {
        StopWatch stopWatch = new StopWatch();
        Bitmap createBitmapFitWithIn = BitmapUtils.createBitmapFitWithIn(bitmap, i, i2);
        boolean writeToAppData = writeToAppData(context, createBitmapFitWithIn, str, i3);
        Lg.d("ファイル書き出し = " + stopWatch.getDiff() + " [ms]");
        if (bitmap != createBitmapFitWithIn) {
            createBitmapFitWithIn.recycle();
        }
        return writeToAppData;
    }

    public static boolean writeToAppData(Context context, Bitmap bitmap, String str, int i) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, i);
            if (str.endsWith(".PNG") || str.endsWith(ImageSaver.FILE_TYPE)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput);
            } else if (str.endsWith(".JPG") || str.endsWith(".jpg") || str.endsWith(".JPEG") || str.endsWith(".jpeg")) {
                StopWatch stopWatch = new StopWatch();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, openFileOutput);
                Lg.d("エンコード書き出し = " + stopWatch.getDiff() + " [ms]");
            } else {
                Lg.e("ファイル拡張子の指定が間違っています");
            }
            try {
                openFileOutput.flush();
                openFileOutput.close();
                return true;
            } catch (IOException unused) {
                Lg.e("fos.flush(); fos.close();");
                return false;
            }
        } catch (FileNotFoundException unused2) {
            Lg.e("openFileOutput()");
            return false;
        }
    }

    public static boolean writeToCache(Bitmap bitmap, String str) {
        return write(bitmap, PathGetter.getIns().getCacheDirPath() + "/" + str);
    }

    public static byte[] imageToByteArray(Image image) {
        if (image.getFormat() == 256) {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bArr = new byte[buffer.capacity()];
            buffer.get(bArr);
            return bArr;
        } else if (image.getFormat() == 35) {
            return NV21toJPEG(YUV_420_888toNV21(image), image.getWidth(), image.getHeight());
        } else {
            return null;
        }
    }

    private static byte[] YUV_420_888toNV21(Image image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        ByteBuffer buffer2 = image.getPlanes()[1].getBuffer();
        ByteBuffer buffer3 = image.getPlanes()[2].getBuffer();
        int remaining = buffer.remaining();
        int remaining2 = buffer2.remaining();
        int remaining3 = buffer3.remaining();
        byte[] bArr = new byte[remaining + remaining2 + remaining3];
        buffer.get(bArr, 0, remaining);
        buffer3.get(bArr, remaining, remaining3);
        buffer2.get(bArr, remaining + remaining3, remaining2);
        return bArr;
    }

    private static byte[] NV21toJPEG(byte[] bArr, int i, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new YuvImage(bArr, 17, i, i2, null).compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private ImgFileUtils() {
    }
}
