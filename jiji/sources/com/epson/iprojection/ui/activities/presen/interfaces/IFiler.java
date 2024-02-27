package com.epson.iprojection.ui.activities.presen.interfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;

/* loaded from: classes.dex */
public interface IFiler {
    boolean Initialize(Context context, String str, IOnReadyListener iOnReadyListener, boolean z);

    void destroy();

    boolean exists(String str);

    int getCurrentPage() throws UnavailableException;

    Bitmap getExtendImage(int i, int i2, int i3, int i4, int i5, double d, int i6) throws BitmapMemoryException, UnavailableException;

    String getFileName(int i) throws UnavailableException;

    String getFilePath(int i) throws UnavailableException;

    Bitmap getImage(int i, int i2, int i3) throws BitmapMemoryException, UnavailableException;

    Bitmap getImage(int i, int i2, int i3, int i4) throws BitmapMemoryException, UnavailableException;

    int getPos(String str) throws UnavailableException;

    int getRealH(int i) throws UnavailableException;

    int getRealW(int i) throws UnavailableException;

    int getTotalPages() throws UnavailableException;

    Uri getUri(int i) throws UnavailableException;

    boolean initFile() throws UnavailableException;

    boolean isAvailable() throws UnavailableException;

    boolean isDisconnectOccured();

    boolean isFileChanged(String str);

    boolean isRendering() throws UnavailableException;

    boolean isScalable();

    boolean isThumbRendering() throws UnavailableException;

    void kill() throws UnavailableException;

    boolean shouldReInit() throws UnavailableException;

    void showOpenError(Context context);
}
