package com.epson.iprojection.ui.activities.marker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.google.android.material.timepicker.TimeModel;
import java.util.Calendar;

/* loaded from: classes.dex */
public abstract class ImageSaver {
    public static final String FILE_TYPE = ".png";
    public static final String PATH = "EPSON/iProjection";
    public static final String PATH_DEL = "EPSON/iProjection/SharedImages";
    public static final int TRY_N = Integer.MAX_VALUE;
    protected Context _context;
    protected ISaveFinishCallback _saveFinishCallback = null;

    /* loaded from: classes.dex */
    public interface ISaveFinishCallback {
        void callbackSaveFail();

        void callbackSaveSucceed();
    }

    public abstract void save(Bitmap bitmap, boolean z);

    public ImageSaver(Context context) {
        this._context = context;
    }

    public void registerCallback(ISaveFinishCallback iSaveFinishCallback) {
        this._saveFinishCallback = iSaveFinishCallback;
    }

    public void unregisterCallback() {
        this._saveFinishCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showSaveSucceed(String str) {
        if (this._saveFinishCallback != null) {
            Context context = this._context;
            new OKDialog((Activity) context, String.format(context.getString(R.string._Saved_), str));
            return;
        }
        Lg.d("Don't show dialog because unregister callback.");
    }

    public void showSaveFailed() {
        if (this._saveFinishCallback != null) {
            Context context = this._context;
            new OKDialog((Activity) context, context.getString(R.string._FailToSave_));
            return;
        }
        Lg.d("Don't show dialog because unregister callback.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getTimeFileName() {
        Calendar calendar = Calendar.getInstance();
        String format = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(1)));
        String format2 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(2) + 1));
        String format3 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(5)));
        String format4 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(11)));
        String format5 = String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(12)));
        return format + "-" + format2 + "-" + format3 + "-" + format4 + "-" + format5 + "-" + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(calendar.get(13)));
    }
}
