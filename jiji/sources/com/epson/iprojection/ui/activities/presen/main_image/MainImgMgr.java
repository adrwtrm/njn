package com.epson.iprojection.ui.activities.presen.main_image;

import android.content.Context;
import android.graphics.Bitmap;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IThumbThreadManageable;
import com.epson.iprojection.ui.activities.presen.main_image.gesture.IOnFlickListener;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class MainImgMgr {
    private final Context _context;
    private final IFiler _filer;
    private MatrixImageView _imgView = null;
    private final ILoadingStateListener _implLoadingStateListener;
    private final IOnFlickListener _implOnFlickListener;
    private final IThumbThreadManageable _implThumbThreadManageable;

    public MainImgMgr(IFiler iFiler, Context context, IOnFlickListener iOnFlickListener, ILoadingStateListener iLoadingStateListener, IThumbThreadManageable iThumbThreadManageable) {
        this._filer = iFiler;
        this._implOnFlickListener = iOnFlickListener;
        this._context = context;
        this._implLoadingStateListener = iLoadingStateListener;
        this._implThumbThreadManageable = iThumbThreadManageable;
    }

    public void activate() {
        this._imgView.activate();
    }

    public void disactivate() {
        this._imgView.disactivate();
    }

    public void start() throws BitmapMemoryException {
        this._imgView.start();
    }

    public void stop() {
        this._imgView.stop();
    }

    public boolean exists() {
        return getSetImageBitmap() != null;
    }

    public boolean load() throws BitmapMemoryException {
        return this._imgView.load();
    }

    public void save() {
        this._imgView.save();
    }

    public void initialize() {
        this._imgView.initialize();
    }

    public MatrixImageView getImageView() {
        return this._imgView;
    }

    public void setImageView(MatrixImageView matrixImageView) {
        this._imgView = matrixImageView;
        matrixImageView.setLoadingStateListener(this._implLoadingStateListener);
        this._imgView.setFlickListener(this._implOnFlickListener);
        this._imgView.setFiler(this._filer);
        this._imgView.setThumbThreadManageable(this._implThumbThreadManageable);
    }

    public Bitmap getImage(int i) throws BitmapMemoryException, UnavailableException {
        this._imgView.setPageN(i);
        return this._filer.getImage(i, (int) (Pj.getIns().getShadowResWidth() * 1.0f), (int) (Pj.getIns().getShadowResHeight() * 1.0f), 1);
    }

    public Bitmap getSetImageBitmap() {
        return this._imgView.getSetImageBitmap();
    }

    public Bitmap getShadowBitmap() throws BitmapMemoryException {
        return this._imgView.getShadowBitmapBiCubic(false);
    }

    public void setImage(Bitmap bitmap, boolean z) throws BitmapMemoryException {
        this._imgView.setBitmap(bitmap, z);
    }

    public void sendImage() throws BitmapMemoryException {
        MatrixImageView matrixImageView = this._imgView;
        if (matrixImageView == null || matrixImageView.isTemp()) {
            return;
        }
        this._imgView.sendImage();
    }

    public boolean resetRenderedImage() {
        try {
            Bitmap load = new RenderedImageFile().load(this._context);
            if (load == null) {
                return false;
            }
            setImage(BitmapUtils.createBitmapFitWithIn(load, (int) (Pj.getIns().getShadowResWidth() * 1.0f), (int) (Pj.getIns().getShadowResHeight() * 1.0f)), false);
            return true;
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
            return true;
        }
    }

    public void invalidate() {
        MatrixImageView matrixImageView = this._imgView;
        if (matrixImageView != null) {
            matrixImageView.invalidate();
        }
    }
}
