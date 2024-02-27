package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.ui.activities.presen.Selector;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.interfaces.IClickThumbListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IFatalErrorListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnFinishedCreatingThumb;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ThumbMgr {
    public static final int FIRST_LIMIT = 100;
    private static final int THUMB_NUM_IN_WINDOW = 5;
    private final Context _context;
    private final IFiler _filer;
    private IFatalErrorListener _implFatalErrorListener;
    private final IOnClickMenuButtonListener _implMenuListener;
    private final IOnFinishedCreatingThumb _implOnFinishedCreatingThumb;
    private final boolean _isDelivery;
    private final IClickThumbListener _listenerImple;
    private final ScrollViewMgr _scrollViewMgr;
    private final Selector _selector;
    private int _startPos;
    private ThumbLoader _thumbLoader;
    private int _totalNum;
    private final ArrayList<LinearLayout> _thumbList = new ArrayList<>();
    private final Bitmap _blankBmp = null;
    private int _focusedImageN = 0;
    private LinearLayout _targetLayout = null;
    private ScrollPosChecker _scrollPosChecker = null;
    private boolean _isEditMode = false;
    private final Handler _handler = new Handler();
    private boolean _isAvairableThread = true;
    private final D_Prms _dPrms = new D_Prms();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class D_Prms {
        LayoutInflater inflater;
        LinearLayout.LayoutParams prml;
        LinearLayout.LayoutParams prmr;
        FrameLayout.LayoutParams prmt;

        D_Prms() {
        }
    }

    public ThumbMgr(IFiler iFiler, Selector selector, Context context, IClickThumbListener iClickThumbListener, ScrollViewMgr scrollViewMgr, boolean z, IOnClickMenuButtonListener iOnClickMenuButtonListener, IOnFinishedCreatingThumb iOnFinishedCreatingThumb) {
        this._filer = iFiler;
        this._selector = selector;
        this._context = context;
        this._listenerImple = iClickThumbListener;
        this._scrollViewMgr = scrollViewMgr;
        this._isDelivery = z;
        this._implMenuListener = iOnClickMenuButtonListener;
        this._implOnFinishedCreatingThumb = iOnFinishedCreatingThumb;
    }

    public void initialize(int i, int i2, IFatalErrorListener iFatalErrorListener) throws BitmapMemoryException {
        this._startPos = i;
        this._implFatalErrorListener = iFatalErrorListener;
        this._totalNum = i2;
        createPrms();
        int min = Math.min(i2, 100);
        for (int i3 = 0; i3 < min; i3++) {
            this._thumbList.add(createThumbParts(null, i3));
        }
        if (i2 < 100) {
            this._thumbLoader = new ThumbLoader(this._context, this._filer, this._thumbList, this._startPos, this._blankBmp, this._implFatalErrorListener);
            this._scrollPosChecker = new ScrollPosChecker(this._scrollViewMgr, this._thumbLoader);
            return;
        }
        startCreating100Over();
    }

    private void createPrms() {
        int thumbWidth = getThumbWidth((Activity) this._context);
        int thumbHeight = getThumbHeight((Activity) this._context);
        this._dPrms.inflater = (LayoutInflater) this._context.getSystemService("layout_inflater");
        int dimensionPixelSize = this._context.getResources().getDimensionPixelSize(R.dimen.PresenTumbText);
        this._dPrms.prml = new LinearLayout.LayoutParams(thumbWidth, dimensionPixelSize + thumbHeight);
        this._dPrms.prmr = new LinearLayout.LayoutParams(thumbWidth, thumbHeight);
        int dimensionPixelSize2 = this._context.getResources().getDimensionPixelSize(R.dimen.PresenThumbMargin);
        this._dPrms.prmt = new FrameLayout.LayoutParams(thumbWidth - (dimensionPixelSize2 * 2), thumbHeight - dimensionPixelSize2);
        this._dPrms.prmt.setMargins(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, 0);
    }

    private LinearLayout createThumbParts(Bitmap bitmap, int i) {
        LinearLayout linearLayout = (LinearLayout) this._dPrms.inflater.inflate(R.layout.inflater_presen_thumb, (ViewGroup) null);
        linearLayout.setLayoutParams(this._dPrms.prml);
        ((FrameLayout) linearLayout.findViewById(R.id.layout_thumb_frame)).setLayoutParams(this._dPrms.prmr);
        ThumbImageView thumbImageView = (ThumbImageView) linearLayout.findViewById(R.id.img_thumb);
        thumbImageView.setLayoutParams(this._dPrms.prmt);
        linearLayout.setOnClickListener(new ImgClickListener());
        if (this._isDelivery) {
            linearLayout.setOnLongClickListener(new ImgLongClickListener());
        }
        ((TextView) linearLayout.findViewById(R.id.txt_num)).setText("" + (i + 1));
        if (bitmap != null) {
            thumbImageView.setImageBitmap(bitmap);
        } else {
            thumbImageView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        return linearLayout;
    }

    public boolean is100Over() {
        return this._totalNum >= 100;
    }

    public void startCreating100Over() {
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ThumbMgr.this.m166xb6300cd2();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$startCreating100Over$1$com-epson-iprojection-ui-activities-presen-thumbnails-ThumbMgr  reason: not valid java name */
    public /* synthetic */ void m166xb6300cd2() {
        for (int i = 100; i < this._totalNum && this._isAvairableThread; i++) {
            this._thumbList.add(createThumbParts(null, i));
        }
        if (this._isAvairableThread) {
            this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ThumbMgr.this.m165x37cf08f3();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$startCreating100Over$0$com-epson-iprojection-ui-activities-presen-thumbnails-ThumbMgr  reason: not valid java name */
    public /* synthetic */ void m165x37cf08f3() {
        this._thumbLoader = new ThumbLoader(this._context, this._filer, this._thumbList, this._startPos, this._blankBmp, this._implFatalErrorListener);
        this._scrollPosChecker = new ScrollPosChecker(this._scrollViewMgr, this._thumbLoader);
        this._implOnFinishedCreatingThumb.onFinishedCreatingThumb();
        Lg.e("終了");
    }

    public void destroy() {
        this._isAvairableThread = false;
    }

    public LinearLayout getTargetLayout() {
        return this._targetLayout;
    }

    public void startLoad() {
        ThumbLoader thumbLoader = this._thumbLoader;
        if (thumbLoader == null || thumbLoader.isLoading()) {
            return;
        }
        this._thumbLoader.start();
        this._scrollPosChecker.start();
    }

    public void stopLoad(boolean z) {
        ThumbLoader thumbLoader = this._thumbLoader;
        if (thumbLoader == null || !thumbLoader.isLoading()) {
            return;
        }
        this._thumbLoader.stop(z);
        this._scrollPosChecker.stop();
    }

    public boolean isLoading() {
        return this._thumbLoader.isLoading();
    }

    public boolean isEditMode() {
        return this._isEditMode;
    }

    public void refreshListView() {
        int size = this._thumbList.size();
        this._targetLayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            this._targetLayout.addView(this._thumbList.get(i));
        }
        this._targetLayout.invalidate();
    }

    public static int getThumbWidth(Activity activity) {
        return (int) (DipUtils.getWinW(activity) / 5.0f);
    }

    public static int getThumbHeight(Activity activity) {
        return (int) (((DipUtils.getWinW(activity) / 5.0f) * 3.0f) / 4.0f);
    }

    public void setTargetLayout(LinearLayout linearLayout) {
        this._targetLayout = linearLayout;
        this._focusedImageN = 0;
    }

    public Bitmap getImageThumb(int i) {
        BitmapDrawable bitmapDrawable;
        if (i < this._thumbList.size() && (bitmapDrawable = (BitmapDrawable) ((ThumbImageView) this._thumbList.get(i).findViewById(R.id.img_thumb)).getDrawable()) != null) {
            return bitmapDrawable.getBitmap();
        }
        return null;
    }

    public CheckBox getThumbCheckBox(int i) {
        if (i >= this._thumbList.size()) {
            return null;
        }
        return (CheckBox) this._thumbList.get(i).findViewById(R.id.check_thumb);
    }

    public int getThumbListSize() {
        return this._thumbList.size();
    }

    public String getFileName() throws UnavailableException {
        return this._filer.getFileName(this._selector.getNowSelectNum());
    }

    public Bitmap getBlankBitmap() {
        return this._blankBmp;
    }

    public void requestFocus(int i) {
        int size = this._thumbList.size();
        int i2 = this._focusedImageN;
        if (size > i2) {
            this._thumbList.get(i2).setBackgroundColor(-1);
        }
        this._focusedImageN = i;
        if (this._thumbList.size() > i) {
            this._thumbList.get(i).setVisibility(0);
            this._thumbList.get(i).clearAnimation();
            this._thumbList.get(i).setBackgroundResource(R.drawable.shape_thumbselect);
            this._targetLayout.requestChildFocus(null, this._thumbList.get(i));
        }
        ThumbLoader thumbLoader = this._thumbLoader;
        if (thumbLoader != null) {
            thumbLoader.setCurrentPos(this._focusedImageN);
        }
    }

    public void interrupt(boolean z) {
        ThumbLoader thumbLoader = this._thumbLoader;
        if (thumbLoader != null) {
            thumbLoader.interrupt(z);
        }
    }

    public void startEditMode() {
        this._isEditMode = true;
        Iterator<LinearLayout> it = this._thumbList.iterator();
        while (it.hasNext()) {
            CheckBox checkBox = (CheckBox) it.next().findViewById(R.id.check_thumb);
            checkBox.setVisibility(0);
            checkBox.setChecked(false);
        }
    }

    public void stopEditMode() {
        this._isEditMode = false;
        Iterator<LinearLayout> it = this._thumbList.iterator();
        while (it.hasNext()) {
            ((CheckBox) it.next().findViewById(R.id.check_thumb)).setVisibility(8);
        }
    }

    public void checkAll() {
        Iterator<LinearLayout> it = this._thumbList.iterator();
        while (it.hasNext()) {
            ((CheckBox) it.next().findViewById(R.id.check_thumb)).setChecked(true);
        }
    }

    public void uncheckAll() {
        Iterator<LinearLayout> it = this._thumbList.iterator();
        while (it.hasNext()) {
            ((CheckBox) it.next().findViewById(R.id.check_thumb)).setChecked(false);
        }
    }

    public boolean isCheckedAll() {
        Iterator<LinearLayout> it = this._thumbList.iterator();
        while (it.hasNext()) {
            if (!((CheckBox) it.next().findViewById(R.id.check_thumb)).isChecked()) {
                return false;
            }
        }
        return true;
    }

    public boolean existsChecked() {
        int size = this._thumbList.size();
        for (int i = 0; i < size; i++) {
            if (((CheckBox) this._thumbList.get(i).findViewById(R.id.check_thumb)).isChecked()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ImgClickListener implements View.OnClickListener {
        ImgClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CheckBox checkBox;
            Iterator it = ThumbMgr.this._thumbList.iterator();
            int i = 0;
            while (it.hasNext()) {
                LinearLayout linearLayout = (LinearLayout) it.next();
                if (linearLayout == view) {
                    ((CheckBox) linearLayout.findViewById(R.id.check_thumb)).setChecked(!checkBox.isChecked());
                    if (ThumbMgr.this._selector.getNowSelectNum() == i) {
                        return;
                    }
                    ThumbMgr.this._listenerImple.onClickThumb(i, false);
                    return;
                }
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ImgLongClickListener implements View.OnLongClickListener {
        ImgLongClickListener() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            ThumbMgr.this.startEditMode();
            ThumbMgr.this._implMenuListener.onClickStartEdit();
            return false;
        }
    }
}
