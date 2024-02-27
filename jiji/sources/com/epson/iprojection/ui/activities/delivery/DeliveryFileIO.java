package com.epson.iprojection.ui.activities.delivery;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.epson.iprojection.engine.common.D_DeliveryInfo;

/* loaded from: classes.dex */
public abstract class DeliveryFileIO {
    protected static final String DIR_NAME = "EPSON/iProjection/SharedImages";
    protected static final String FILE_TYPE = ".jpg";
    protected static final int TRY_N = Integer.MAX_VALUE;
    protected static DeliveryFileIO _inst;

    public abstract String createWhitePaper(Context context, short s, short s2);

    public abstract boolean delete(Context context, Uri uri);

    public abstract boolean exists(Context context);

    public abstract int getFileNum(Context context);

    public abstract String getNewestFileName(Context context);

    public abstract String save(Context context, D_DeliveryInfo d_DeliveryInfo);

    public static DeliveryFileIO getIns() {
        if (_inst == null) {
            if (Build.VERSION.SDK_INT >= 29) {
                _inst = new DeliveryFileIO10orMore();
            } else {
                _inst = new DeliveryFileIO9orLess();
            }
        }
        return _inst;
    }
}
