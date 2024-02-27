package com.epson.iprojection.ui.common;

import android.app.Activity;
import android.graphics.Bitmap;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ImgFileUtils;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.io.File;

/* loaded from: classes.dex */
public class ScaledImage {
    private static final String FILE_NAME = "SCALED_IMAGE_";
    private final Activity _activity;

    public ScaledImage(Activity activity) {
        this._activity = activity;
    }

    public void save(Bitmap bitmap) {
        if (bitmap == null) {
            Lg.e("error!");
        }
        ImgFileUtils.write(bitmap, getFileName());
    }

    public Bitmap read() {
        try {
            return ImgFileUtils.read(getFileName());
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
            return null;
        }
    }

    public void delete() {
        File file = new File(getFileName());
        if (file.exists()) {
            file.delete();
        }
    }

    private String getFileName() {
        return this._activity.getCacheDir() + "/SCALED_IMAGE_" + ((PjConnectableActivity) this._activity).getTaskId() + ".jpg";
    }
}
