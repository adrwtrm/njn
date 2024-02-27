package com.serenegiant.graphics;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.serenegiant.utils.BitsHelper;
import com.serenegiant.utils.UriHelper;
import com.serenegiant.widget.ProgressView;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class BitmapHelper {
    private static final float[] COLOR_MATRIX_INVERT_ALPHA = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 255.0f};
    private static final boolean DEBUG = false;
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    private static final int OPTIONS_SCALE_UP = 1;
    private static final String TAG = "BitmapHelper";

    private BitmapHelper() {
    }

    public static byte[] BitmapToByteArray(Bitmap bitmap) {
        return BitmapToByteArray(bitmap, Bitmap.CompressFormat.PNG, 100);
    }

    public static byte[] BitmapToByteArray(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap.compress(compressFormat, i, byteArrayOutputStream)) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static Bitmap asBitmap(byte[] bArr) {
        if (bArr != null) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public static Bitmap asBitmap(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = calcSampleSize(options, i, i2);
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }
        return null;
    }

    public static Bitmap asBitmapStrictSize(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int calcSampleSize = calcSampleSize(options, i, i2);
            int MSB = 1 << BitsHelper.MSB(calcSampleSize);
            options.inSampleSize = MSB;
            options.inJustDecodeBounds = false;
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (MSB == calcSampleSize && decodeByteArray.getWidth() == i && decodeByteArray.getHeight() == i2) {
                return decodeByteArray;
            }
            Bitmap scaleBitmap = scaleBitmap(decodeByteArray, i, i2);
            decodeByteArray.recycle();
            return scaleBitmap;
        }
        return null;
    }

    public static Bitmap asBitmap(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        int orientation = getOrientation(str);
        if (orientation != 0) {
            Bitmap rotateBitmap = rotateBitmap(decodeFile, orientation);
            decodeFile.recycle();
            return rotateBitmap;
        }
        return decodeFile;
    }

    public static Bitmap asBitmap(String str, int i, int i2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calcSampleSize(options, i, i2);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        int orientation = getOrientation(str);
        if (orientation != 0) {
            Bitmap rotateBitmap = rotateBitmap(decodeFile, orientation);
            decodeFile.recycle();
            return rotateBitmap;
        }
        return decodeFile;
    }

    public static Bitmap asBitmapStrictSize(String str, int i, int i2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int calcSampleSize = calcSampleSize(options, i, i2);
        int MSB = 1 << BitsHelper.MSB(calcSampleSize);
        options.inSampleSize = MSB;
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        int orientation = getOrientation(str);
        if (MSB == calcSampleSize && orientation == 0 && decodeFile.getWidth() == i && decodeFile.getHeight() == i2) {
            return decodeFile;
        }
        Bitmap scaleRotateBitmap = scaleRotateBitmap(decodeFile, i, i2, orientation);
        decodeFile.recycle();
        return scaleRotateBitmap;
    }

    public static Bitmap asBitmap(FileDescriptor fileDescriptor) {
        if (fileDescriptor == null || !fileDescriptor.valid()) {
            return null;
        }
        Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        int orientation = getOrientation(fileDescriptor);
        if (orientation != 0) {
            Bitmap rotateBitmap = rotateBitmap(decodeFileDescriptor, orientation);
            decodeFileDescriptor.recycle();
            return rotateBitmap;
        }
        return decodeFileDescriptor;
    }

    public static Bitmap asBitmap(FileDescriptor fileDescriptor, int i, int i2) {
        if (fileDescriptor == null || !fileDescriptor.valid()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calcSampleSize(options, i, i2);
        Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        int orientation = getOrientation(fileDescriptor);
        if (orientation != 0) {
            Bitmap rotateBitmap = rotateBitmap(decodeFileDescriptor, orientation);
            decodeFileDescriptor.recycle();
            return rotateBitmap;
        }
        return decodeFileDescriptor;
    }

    public static Bitmap asBitmapStrictSize(FileDescriptor fileDescriptor, int i, int i2) {
        if (fileDescriptor == null || !fileDescriptor.valid()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        int calcSampleSize = calcSampleSize(options, i, i2);
        int MSB = 1 << BitsHelper.MSB(calcSampleSize);
        options.inSampleSize = MSB;
        options.inJustDecodeBounds = false;
        Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        int orientation = getOrientation(fileDescriptor);
        if (MSB == calcSampleSize && orientation == 0 && decodeFileDescriptor.getWidth() == i && decodeFileDescriptor.getHeight() == i2) {
            return decodeFileDescriptor;
        }
        Bitmap scaleRotateBitmap = scaleRotateBitmap(decodeFileDescriptor, i, i2, orientation);
        decodeFileDescriptor.recycle();
        return scaleRotateBitmap;
    }

    public static Bitmap asBitmap(ContentResolver contentResolver, long j) throws IOException {
        ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, j), "r");
        if (openFileDescriptor != null) {
            return asBitmap(openFileDescriptor.getFileDescriptor());
        }
        return null;
    }

    public static Bitmap asBitmap(ContentResolver contentResolver, long j, int i, int i2) throws IOException {
        ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, j), "r");
        if (openFileDescriptor != null) {
            return asBitmap(openFileDescriptor.getFileDescriptor(), i, i2);
        }
        return null;
    }

    public static Bitmap asBitmap(ContentResolver contentResolver, Uri uri) throws IOException {
        ParcelFileDescriptor openFileDescriptor;
        if (uri == null || (openFileDescriptor = contentResolver.openFileDescriptor(uri, "r")) == null) {
            return null;
        }
        return asBitmap(openFileDescriptor.getFileDescriptor());
    }

    public static Bitmap asBitmap(ContentResolver contentResolver, Uri uri, int i, int i2) throws IOException {
        ParcelFileDescriptor openFileDescriptor;
        if (uri == null || (openFileDescriptor = contentResolver.openFileDescriptor(uri, "r")) == null) {
            return null;
        }
        return asBitmap(openFileDescriptor.getFileDescriptor(), i, i2);
    }

    public static Bitmap asBitmapStrictSize(ContentResolver contentResolver, Uri uri, int i, int i2) throws IOException {
        ParcelFileDescriptor openFileDescriptor;
        if (uri == null || (openFileDescriptor = contentResolver.openFileDescriptor(uri, "r")) == null) {
            return null;
        }
        return asBitmapStrictSize(openFileDescriptor.getFileDescriptor(), i, i2);
    }

    public static Bitmap fromDrawable(Drawable drawable, int i, int i2) {
        drawable.setBounds(0, 0, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        drawable.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap fromDrawable(Context context, int i) {
        Bitmap bitmap;
        if (i != 0) {
            Drawable drawable = ContextCompat.getDrawable(context, i);
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                drawable.setBounds(0, 0, 72, 72);
            }
            bitmap = fromDrawable(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        } else {
            bitmap = null;
        }
        if (bitmap != null) {
            return bitmap;
        }
        throw new IllegalArgumentException("failed to load from resource " + i);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(width / i, height / i2);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return null;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postRotate(i);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return null;
    }

    public static Bitmap scaleRotateBitmap(Bitmap bitmap, int i, int i2, int i3) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(width / i, height / i2);
            matrix.postRotate(i3);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return null;
    }

    public static Bitmap extractBitmap(Bitmap bitmap, int i, int i2) {
        float f;
        int height;
        if (bitmap != null) {
            if (bitmap.getWidth() < bitmap.getHeight()) {
                f = i;
                height = bitmap.getWidth();
            } else {
                f = i2;
                height = bitmap.getHeight();
            }
            float f2 = f / height;
            Matrix matrix = new Matrix();
            matrix.setScale(f2, f2);
            return transform(matrix, bitmap, i, i2, 3);
        }
        return null;
    }

    private static Bitmap transform(Matrix matrix, Bitmap bitmap, int i, int i2, int i3) {
        Matrix matrix2;
        boolean z = (i3 & 1) != 0;
        boolean z2 = (i3 & 2) != 0;
        int width = bitmap.getWidth() - i;
        int height = bitmap.getHeight() - i2;
        if (!z && (width < 0 || height < 0)) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            int max = Math.max(0, width / 2);
            int max2 = Math.max(0, height / 2);
            Rect rect = new Rect(max, max2, Math.min(i, bitmap.getWidth()) + max, Math.min(i2, bitmap.getHeight()) + max2);
            int width2 = (i - rect.width()) / 2;
            int height2 = (i2 - rect.height()) / 2;
            canvas.drawBitmap(bitmap, rect, new Rect(width2, height2, i - width2, i2 - height2), (Paint) null);
            if (z2) {
                bitmap.recycle();
            }
            canvas.setBitmap(null);
            return createBitmap;
        }
        float width3 = bitmap.getWidth();
        float height3 = bitmap.getHeight();
        float f = i;
        float f2 = i2;
        float f3 = width3 / height3 > f / f2 ? f2 / height3 : f / width3;
        if (f3 < 0.9f || f3 > 1.0f) {
            matrix.setScale(f3, f3);
            matrix2 = matrix;
        } else {
            matrix2 = null;
        }
        Bitmap createBitmap2 = matrix2 != null ? Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true) : bitmap;
        if (z2 && createBitmap2 != bitmap) {
            bitmap.recycle();
        }
        Bitmap createBitmap3 = Bitmap.createBitmap(createBitmap2, Math.max(0, createBitmap2.getWidth() - i) / 2, Math.max(0, createBitmap2.getHeight() - i2) / 2, i, i2);
        if (createBitmap3 != createBitmap2 && (z2 || createBitmap2 != bitmap)) {
            createBitmap2.recycle();
        }
        return createBitmap3;
    }

    public static int calcSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        int i5 = i <= 0 ? i2 > 0 ? (int) ((i3 * i2) / i4) : i3 : i;
        if (i2 <= 0) {
            i2 = i > 0 ? (int) ((i * i4) / i4) : i4;
        }
        if (i4 > i2 || i3 > i5) {
            if (i3 > i4) {
                return Math.round(i4 / i2);
            }
            return Math.round(i3 / i5);
        }
        return 1;
    }

    public static Bitmap copyBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap2 == null) {
            return Bitmap.createBitmap(bitmap);
        }
        if (bitmap.equals(bitmap2)) {
            return bitmap2;
        }
        new Canvas(bitmap2).setBitmap(bitmap);
        return bitmap2;
    }

    public static Bitmap applyMirror(Bitmap bitmap, int i) {
        int i2 = i % 4;
        if (i2 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        if (i2 == 1) {
            matrix.preScale(-1.0f, 1.0f);
        } else if (i2 == 2) {
            matrix.preScale(1.0f, -1.0f);
        } else if (i2 == 3) {
            matrix.preScale(-1.0f, -1.0f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap makeCheckBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        Paint paint = new Paint();
        paint.setColor(-3355444);
        canvas.drawRect(0.0f, 0.0f, 20.0f, 20.0f, paint);
        canvas.drawRect(20.0f, 20.0f, 40.0f, 40.0f, paint);
        return createBitmap;
    }

    public static Bitmap makeCheckBitmap(int i, int i2, int i3, int i4, Bitmap.Config config) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        Paint paint = new Paint();
        paint.setColor(-3355444);
        int i5 = 0;
        while (i5 < i) {
            int i6 = 0;
            while (i6 < i2) {
                float f = i3;
                float f2 = i4;
                canvas.drawRect(i5, i6, f, f2, paint);
                canvas.drawRect(i5 + i3, i6 + i4, f, f2, paint);
                i6 += i4 * 2;
            }
            i5 += i3 * 2;
        }
        return createBitmap;
    }

    public static Bitmap genMaskImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return getMaskImage0(i2, i3, i4, i5, i6, i7);
    }

    public static Bitmap getMaskImage0(int i, int i2, int i3, int i4, int i5, int i6) {
        float min = (Math.min(i, i2) * i3) / 200.0f;
        Paint paint = new Paint();
        paint.setDither(true);
        int red = Color.red(i4);
        int blue = Color.blue(i4);
        int green = Color.green(i4);
        float f = i / 2;
        float f2 = i2 / 2;
        paint.setShader(new RadialGradient(f, f2, min, new int[]{Color.argb(i6, red, green, blue), Color.argb(i6, red, green, blue), Color.argb((int) (i6 * 0.6f), red, green, blue), Color.argb(i5, red, green, blue)}, new float[]{0.0f, 0.6f, 0.8f, 1.0f}, Shader.TileMode.CLAMP));
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawCircle(f, f2, min, paint);
        return createBitmap;
    }

    public static Bitmap invertAlpha(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(COLOR_MATRIX_INVERT_ALPHA));
        new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static int getOrientation(String str) {
        try {
            return getOrientation(new ExifInterface(str));
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int getOrientation(FileDescriptor fileDescriptor) {
        try {
            return getOrientation(new ExifInterface(fileDescriptor));
        } catch (Exception unused) {
            return 0;
        }
    }

    private static int getOrientation(ExifInterface exifInterface) {
        int attributeInt = exifInterface.getAttributeInt("Orientation", 0);
        if (attributeInt != 3) {
            if (attributeInt != 6) {
                if (attributeInt != 8) {
                    return 0;
                }
                return ProgressView.DIRECTION_TOP_TO_BOTTOM;
            }
            return 90;
        }
        return 180;
    }

    public static int getOrientation(ContentResolver contentResolver, long j) {
        return getOrientation(contentResolver, ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, j));
    }

    public static int getOrientation(ContentResolver contentResolver, Uri uri) {
        try {
            ExifInterface exifInterface = new ExifInterface(UriHelper.getAbsolutePath(contentResolver, uri));
            if (exifInterface.getAttributeInt("Orientation", 0) == 0) {
                return getOrientationFromMediaStore(contentResolver, uri);
            }
            return getOrientation(exifInterface);
        } catch (Exception unused) {
            return getOrientationFromMediaStore(contentResolver, uri);
        }
    }

    public static int getOrientationFromMediaStore(ContentResolver contentResolver, Uri uri) {
        int i;
        String[] strArr = {"_data", "orientation"};
        Cursor query = contentResolver.query(uri, strArr, null, null, null);
        if (query != null) {
            try {
                query.moveToFirst();
                i = query.getInt(query.getColumnIndex(strArr[1]));
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        } else {
            i = 0;
        }
        return i;
    }

    /* renamed from: com.serenegiant.graphics.BitmapHelper$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            $SwitchMap$android$graphics$Bitmap$Config = iArr;
            try {
                iArr[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGBA_F16.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static int getPixelBytes(Bitmap.Config config) {
        int i = AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2 && i != 3) {
                i2 = 4;
                if (i != 4) {
                    if (i == 5) {
                        return 8;
                    }
                    throw new IllegalArgumentException("Unexpected config type" + config);
                }
            }
        }
        return i2;
    }

    private static int sat(int i, int i2, int i3) {
        return i < i2 ? i2 : Math.min(i, i3);
    }

    private static int sat(float f, float f2, float f3) {
        if (f >= f2) {
            f2 = Math.min(f, f3);
        }
        return (int) f2;
    }
}
