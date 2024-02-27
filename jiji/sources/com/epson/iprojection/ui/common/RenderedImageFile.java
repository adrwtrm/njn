package com.epson.iprojection.ui.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.ImgFileUtils;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public class RenderedImageFile {
    protected String _fileName;

    public RenderedImageFile() {
        Activity frontActivity = ActivityGetter.getIns().getFrontActivity();
        frontActivity = -1 == frontActivity.getTaskId() ? frontActivity.getParent() : frontActivity;
        if (frontActivity == null) {
            this._fileName = null;
        } else {
            this._fileName = "RenderedImage" + frontActivity.getTaskId() + ".jpg";
        }
    }

    public void save(Context context, Bitmap bitmap) {
        if (context == null || this._fileName == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        ImgFileUtils.writeToAppData(context, bitmap, this._fileName, 0);
    }

    public Bitmap load(Context context) throws BitmapMemoryException {
        String str;
        if (context == null || (str = this._fileName) == null) {
            return null;
        }
        return ImgFileUtils.readFromAppData(context, str);
    }

    public void delete(Context context) {
        String str;
        if (context == null || (str = this._fileName) == null) {
            return;
        }
        FileUtils.deleteAppDataFile(context, str);
    }

    public boolean exists(Context context) {
        String str;
        if (context == null || (str = this._fileName) == null) {
            return false;
        }
        return FileUtils.existsAppDataFile(context, str);
    }
}
