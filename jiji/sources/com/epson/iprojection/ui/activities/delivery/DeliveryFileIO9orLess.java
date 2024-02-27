package com.epson.iprojection.ui.activities.delivery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.engine.common.D_DeliveryInfo;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.google.android.material.timepicker.TimeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

/* loaded from: classes.dex */
public class DeliveryFileIO9orLess extends DeliveryFileIO {
    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String save(Context context, D_DeliveryInfo d_DeliveryInfo) {
        if (d_DeliveryInfo.bufferSize <= 0 || d_DeliveryInfo.buffer == null) {
            Lg.e("size:" + d_DeliveryInfo.bufferSize + " buffer:" + d_DeliveryInfo.buffer);
            return null;
        }
        String newFileName = getNewFileName(context);
        if (newFileName == null) {
            return null;
        }
        File file = new File(newFileName);
        try {
            d_DeliveryInfo.buffer.order(ByteOrder.nativeOrder());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.getChannel().write(d_DeliveryInfo.buffer);
            d_DeliveryInfo.buffer = null;
            fileOutputStream.close();
            return newFileName;
        } catch (IOException e) {
            Lg.e("IOException発生 : " + e.getMessage());
            return null;
        }
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String createWhitePaper(Context context, short s, short s2) {
        String str = null;
        try {
            Bitmap createWhiteBitmap = BitmapUtils.createWhiteBitmap(s, s2);
            if (createWhiteBitmap == null) {
                Lg.e("failed to createWhiteBitmap.");
                return null;
            }
            String newFileName = getNewFileName(context);
            try {
                if (newFileName == null) {
                    Lg.e("failed to getNewFileName.");
                    createWhiteBitmap.recycle();
                    return null;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(new File(newFileName));
                if (createWhiteBitmap.compress(Bitmap.CompressFormat.JPEG, 0, fileOutputStream)) {
                    fileOutputStream.close();
                    createWhiteBitmap.recycle();
                    return newFileName;
                }
                fileOutputStream.close();
                createWhiteBitmap.recycle();
                Lg.e("failed to compress.");
                return null;
            } catch (BitmapMemoryException e) {
                str = newFileName;
                e = e;
                Lg.e("catched BitmapMemoryException:" + e);
                return str;
            } catch (FileNotFoundException e2) {
                str = newFileName;
                e = e2;
                Lg.e("catched FileNotFoundException:" + e);
                return str;
            } catch (IOException e3) {
                str = newFileName;
                e = e3;
                Lg.e("catched IOException:" + e);
                return str;
            }
        } catch (BitmapMemoryException e4) {
            e = e4;
        } catch (FileNotFoundException e5) {
            e = e5;
        } catch (IOException e6) {
            e = e6;
        }
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public boolean delete(Context context, Uri uri) {
        File file = new File(uri.getPath());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String getNewestFileName(Context context) {
        File[] listFiles;
        try {
            File file = new File(getPath(context));
            if (file.exists()) {
                if (file.listFiles() != null) {
                    ArrayList arrayList = new ArrayList();
                    for (File file2 : file.listFiles()) {
                        if (FileUtils.isImageFile(file2.getPath())) {
                            arrayList.add(file2);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        return null;
                    }
                    arrayList.sort(new Comparator() { // from class: com.epson.iprojection.ui.activities.delivery.DeliveryFileIO9orLess$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return DeliveryFileIO9orLess.lambda$getNewestFileName$0((File) obj, (File) obj2);
                        }
                    });
                    return getPath(context) + "/" + ((File) arrayList.get(0)).getName();
                }
            }
        } catch (NullPointerException unused) {
        }
        return null;
    }

    public static /* synthetic */ int lambda$getNewestFileName$0(File file, File file2) {
        return file2.getName().compareTo(file.getName());
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public boolean exists(Context context) {
        return getFileNum(context) > 0;
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public int getFileNum(Context context) {
        File[] listFiles;
        File file = new File(getPath(context));
        if (!file.exists() || (listFiles = file.listFiles()) == null || listFiles.length == 0) {
            return 0;
        }
        int i = 0;
        for (File file2 : listFiles) {
            if (FileUtils.isImageFile(file2.getPath())) {
                i++;
            }
        }
        return i;
    }

    protected String getNewFileName(Context context) {
        String timeFileName = getTimeFileName(context);
        if (timeFileName == null) {
            return null;
        }
        if (new File(timeFileName + ".jpg").exists()) {
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                String str = timeFileName + "(" + i + ").jpg";
                if (!new File(str).exists()) {
                    return str;
                }
            }
            return null;
        }
        return timeFileName + ".jpg";
    }

    protected String getTimeFileName(Context context) {
        Calendar calendar = Calendar.getInstance();
        String format = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(1)));
        String format2 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(2) + 1));
        String format3 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(5)));
        String format4 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(11)));
        String format5 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(12)));
        return getPath(context) + "/" + format + "-" + format2 + "-" + format3 + "-" + format4 + "-" + format5 + "-" + String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(13)));
    }

    public String getPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/SharedImages";
    }
}
