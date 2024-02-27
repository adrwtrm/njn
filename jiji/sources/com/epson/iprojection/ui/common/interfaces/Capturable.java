package com.epson.iprojection.ui.common.interfaces;

import android.graphics.Bitmap;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public interface Capturable {
    Bitmap capture() throws BitmapMemoryException;
}
