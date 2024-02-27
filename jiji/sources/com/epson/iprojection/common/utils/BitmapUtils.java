package com.epson.iprojection.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.ui.common.ResRect;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public final class BitmapUtils {
    public static Bitmap createBitmapFitWithIn(Bitmap bitmap, int i, int i2) throws BitmapMemoryException {
        return drawBitmapFitWithIn(bitmap, createBitmap(i, i2, Bitmap.Config.RGB_565));
    }

    public static Bitmap createBitmapFitWithIn(Bitmap bitmap, Rect rect, int i, int i2, int i3, int i4) throws BitmapMemoryException {
        Bitmap createBitmap = createBitmap(i, i2, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), rect, paint);
        return drawBitmapFitWithIn(createBitmap, createBitmap(i3, i4, Bitmap.Config.RGB_565));
    }

    public static Bitmap drawBitmapFitWithIn(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap != null && bitmap2 != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int width2 = bitmap2.getWidth();
            int height2 = bitmap2.getHeight();
            Canvas canvas = new Canvas(bitmap2);
            ResRect resRect = new ResRect(0, 0, width, height);
            FitResolution.getRectFitWithIn(resRect, new ResRect(0, 0, width2, height2));
            Rect rect = new Rect(resRect.x, resRect.y, resRect.x + resRect.w, resRect.y + resRect.h);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
        }
        return bitmap2;
    }

    public static Bitmap copyBitmap(Bitmap bitmap, Rect rect, Bitmap bitmap2, Rect rect2) {
        if (rect.width() == rect2.width() && rect.height() == rect2.height()) {
            Canvas canvas = new Canvas(bitmap2);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmap, rect, rect2, paint);
            return bitmap2;
        }
        return null;
    }

    public static Bitmap createWhiteBitmap() throws BitmapMemoryException {
        return createWhiteBitmap(32, 24);
    }

    public static Bitmap createWhiteBitmap(int i, int i2) throws BitmapMemoryException {
        Bitmap createBitmap = createBitmap(i, i2, Bitmap.Config.RGB_565);
        new Canvas(createBitmap).drawColor(-1);
        return createBitmap;
    }

    public static Bitmap createBlackBitmap(int i, int i2) throws BitmapMemoryException {
        return createBitmap(i, i2, Bitmap.Config.RGB_565);
    }

    public static Bitmap createBitmap(int i, int i2, Bitmap.Config config) throws BitmapMemoryException {
        try {
            return Bitmap.createBitmap(i, i2, config);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return Bitmap.createBitmap(i, i2, config);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) throws BitmapMemoryException {
        try {
            return Bitmap.createBitmap(bitmap, i, i2, i3, i4);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return Bitmap.createBitmap(bitmap, i, i2, i3, i4);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4, Matrix matrix) throws BitmapMemoryException {
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, i3, i4, matrix, true);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return Bitmap.createBitmap(bitmap, 0, 0, i3, i4, matrix, true);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap copy(Bitmap bitmap, Bitmap.Config config, boolean z) throws BitmapMemoryException {
        try {
            return bitmap.copy(config, z);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return bitmap.copy(config, z);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int i2, boolean z) throws BitmapMemoryException {
        if (bitmap == null || i2 == 0 || i == 0) {
            return null;
        }
        try {
            return Bitmap.createScaledBitmap(bitmap, i, i2, z);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return Bitmap.createScaledBitmap(bitmap, i, i2, z);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap decodeFile(String str, BitmapFactory.Options options) throws BitmapMemoryException {
        try {
            return BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return BitmapFactory.decodeFile(str, options);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap decodeResource(Resources resources, int i) throws BitmapMemoryException {
        try {
            return BitmapFactory.decodeResource(resources, i);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return BitmapFactory.decodeResource(resources, i);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap decodeStream(FileInputStream fileInputStream) throws BitmapMemoryException {
        try {
            return BitmapFactory.decodeStream(fileInputStream);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return BitmapFactory.decodeStream(fileInputStream);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static Bitmap decodeStream(InputStream inputStream) throws BitmapMemoryException {
        try {
            return BitmapFactory.decodeStream(inputStream);
        } catch (OutOfMemoryError unused) {
            System.gc();
            try {
                return BitmapFactory.decodeStream(inputStream);
            } catch (OutOfMemoryError unused2) {
                throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
            }
        }
    }

    public static MappedByteBuffer createBitmapBuffer(String str, long j) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(str), "rw");
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, j);
            channel.close();
            randomAccessFile.close();
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap loadBitmapBuffer(String str, int i, int i2) throws BitmapMemoryException {
        MappedByteBuffer createBitmapBuffer = createBitmapBuffer(str, i2 * i * 2);
        createBitmapBuffer.position(0);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.RGB_565);
            createBitmap.copyPixelsFromBuffer(createBitmapBuffer);
            new File(str).delete();
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            System.gc();
            throw new BitmapMemoryException("Bitmap生成時にOutofMemoryが検出されました！！");
        }
    }

    private BitmapUtils() {
    }
}
