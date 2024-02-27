package com.epson.iprojection.ui.activities.moderator;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowButtonListener;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;

/* loaded from: classes.dex */
public class ButtonMgrThumbnail extends ButtonMgr {
    private final ImageButton _buttonReload;
    private final ZoomButton _buttonScale;
    private eSize _eSize;

    /* loaded from: classes.dex */
    public enum eSize {
        LARGE,
        SMALL,
        NONE
    }

    public ButtonMgrThumbnail(Activity activity, IOnClickWindowButtonListener iOnClickWindowButtonListener, IOnClickButtonListener iOnClickButtonListener, boolean z) {
        super(activity, iOnClickWindowButtonListener, iOnClickButtonListener, z);
        this._eSize = eSize.SMALL;
        ZoomButton zoomButton = (ZoomButton) activity.findViewById(R.id.btn_multictrl_scale);
        this._buttonScale = zoomButton;
        ImageButton imageButton = (ImageButton) activity.findViewById(R.id.btn_multictrl_reload);
        this._buttonReload = imageButton;
        zoomButton.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    public eSize getSizeType() {
        return this._eSize;
    }

    public void setSizeType(eSize esize) {
        this._eSize = esize;
        if (this._buttonScale == null) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$moderator$ButtonMgrThumbnail$eSize[this._eSize.ordinal()];
        if (i == 1) {
            this._buttonScale.setSize(eSize.LARGE);
        } else if (i != 2) {
        } else {
            this._buttonScale.setSize(eSize.SMALL);
        }
    }

    /* renamed from: com.epson.iprojection.ui.activities.moderator.ButtonMgrThumbnail$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$moderator$ButtonMgrThumbnail$eSize;

        static {
            int[] iArr = new int[eSize.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$moderator$ButtonMgrThumbnail$eSize = iArr;
            try {
                iArr[eSize.LARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$moderator$ButtonMgrThumbnail$eSize[eSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ButtonMgr, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (this._implBtn == null || view == null) {
            return;
        }
        if (view == this._buttonScale) {
            int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$moderator$ButtonMgrThumbnail$eSize[this._eSize.ordinal()];
            if (i == 1) {
                eSize esize = eSize.SMALL;
                this._eSize = esize;
                this._buttonScale.setSize(esize);
                this._implBtn.onClickThumbnailSmallButton();
            } else if (i == 2) {
                eSize esize2 = eSize.LARGE;
                this._eSize = esize2;
                this._buttonScale.setSize(esize2);
                this._implBtn.onClickThumbnailLargeButton();
            }
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.THUMBNAIL_SCALE);
            Analytics.getIns().sendEvent(eCustomEvent.THUMBNAIL_SCALE);
        } else if (view == this._buttonReload) {
            this._implBtn.onClickThumbnailReloadButton();
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.THUMBNAIL_RELOAD);
            Analytics.getIns().sendEvent(eCustomEvent.THUMBNAIL_RELOAD);
        }
    }
}
