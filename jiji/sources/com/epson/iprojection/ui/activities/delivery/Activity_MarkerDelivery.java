package com.epson.iprojection.ui.activities.delivery;

import android.graphics.Bitmap;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;

/* loaded from: classes.dex */
public class Activity_MarkerDelivery extends Activity_Marker {
    @Override // com.epson.iprojection.ui.activities.marker.Activity_Marker
    protected void saveImage(Bitmap bitmap, boolean z) {
        this._saver.save(bitmap, true);
    }
}
