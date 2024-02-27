package com.epson.iprojection.ui.activities.moderator;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ColorUtils;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangePlayBtn;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeableWebRTCConnectStatusListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowListener;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Window implements View.OnClickListener, IOnChangePlayBtn {
    private static final int ALPHA_DISABLE = 64;
    private Bitmap _bitmap;
    private final ImageView _btnBack;
    private final ImageView _btnFrame;
    private final IOnClickWindowListener _impl;
    private IOnChangeableWebRTCConnectStatusListener _onChaneableWebRTCConnectStatusListener;
    private final PlayBtnMgr _playBtnMgr;
    private final TextView _txtName;
    private final int _windID;
    private long _uniqueID = -1;
    private boolean _isActive = false;
    private boolean _isMe = false;
    private boolean _isSetFrame = false;
    private boolean _isSetBitmap = false;

    public Window(int i, ViewGroup viewGroup, ImageView imageView, ImageView imageView2, TextView textView, IOnClickWindowListener iOnClickWindowListener, IOnChangeableWebRTCConnectStatusListener iOnChangeableWebRTCConnectStatusListener) {
        this._playBtnMgr = new PlayBtnMgr(viewGroup, this);
        this._windID = i;
        this._btnBack = imageView;
        this._btnFrame = imageView2;
        this._txtName = textView;
        this._impl = iOnClickWindowListener;
        imageView2.setOnClickListener(this);
        this._onChaneableWebRTCConnectStatusListener = iOnChangeableWebRTCConnectStatusListener;
    }

    public boolean existsContents() {
        return this._uniqueID != -1;
    }

    public void visibleBtns() {
        this._playBtnMgr.visible();
    }

    public void invisibleBtns() {
        this._playBtnMgr.invisible();
    }

    public void activate() {
        Bitmap bitmap = this._bitmap;
        if (bitmap != null) {
            this._btnBack.setImageBitmap(bitmap);
            this._isSetBitmap = true;
        } else {
            this._btnBack.setBackgroundColor(ColorUtils.get(R.color.thumb_active));
            this._isSetBitmap = false;
        }
        this._btnBack.setImageAlpha(255);
        this._isActive = true;
    }

    public void inactivate() {
        this._btnBack.setBackgroundColor(ColorUtils.get(R.color.thumb_inactive));
        if (this._isSetBitmap) {
            this._btnBack.setImageAlpha(63);
        }
        this._isActive = false;
    }

    public boolean isActive() {
        return this._isActive;
    }

    public void setFrame() {
        this._isSetFrame = true;
        this._btnFrame.setImageResource(R.drawable.mpp_frame);
    }

    public void clearFrame() {
        this._isSetFrame = false;
        this._btnFrame.setImageBitmap(null);
    }

    public boolean isSetFrame() {
        return this._isSetFrame;
    }

    public void setUniqueID(long j) {
        this._uniqueID = j;
    }

    public long getUniqueID() {
        return this._uniqueID;
    }

    public void clearUniqueID() {
        this._uniqueID = -1L;
    }

    public void setText(String str) {
        this._txtName.setText(str);
    }

    public String getText() {
        return (String) this._txtName.getText();
    }

    public void setIsMe(boolean z) {
        this._isMe = z;
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            this._isSetBitmap = false;
            this._btnBack.setImageBitmap(null);
            if (isActive()) {
                this._btnBack.setBackgroundColor(ColorUtils.get(R.color.thumb_active));
            } else {
                this._btnBack.setBackgroundColor(ColorUtils.get(R.color.thumb_inactive));
            }
        } else {
            this._isSetBitmap = true;
            if (isActive()) {
                this._btnBack.setImageAlpha(255);
            } else {
                this._btnBack.setImageAlpha(64);
            }
        }
        this._btnBack.setImageBitmap(bitmap);
        this._bitmap = bitmap;
        if (this._isActive) {
            activate();
        }
    }

    public void setSelecting(int i) {
        this._playBtnMgr.setSelecting(i);
        if (this._isMe) {
            int i2 = 1;
            if (i != 0) {
                if (i == 1) {
                    i2 = 2;
                } else if (i != 2) {
                    return;
                } else {
                    i2 = 4;
                }
            }
            Pj.getIns().setProjectionMode(i2);
        }
    }

    public int getSelecting() {
        return this._playBtnMgr.getSelecting();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this._impl.onClickWindow(this._windID);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangePlayBtn
    public void onChangePlayBtn(int i) {
        int i2;
        if (i != 0) {
            i2 = 2;
            if (i == 1) {
                Analytics.getIns().setMultiProjectionEventType(eCustomEvent.STOP);
                Analytics.getIns().sendEvent(eCustomEvent.STOP);
            } else if (i != 2) {
                return;
            } else {
                Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PAUSE);
                Analytics.getIns().sendEvent(eCustomEvent.PAUSE);
                i2 = 4;
            }
        } else {
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PLAY);
            Analytics.getIns().sendEvent(eCustomEvent.PLAY);
            i2 = 1;
        }
        if (isMe()) {
            Pj.getIns().setProjectionMode(i2);
            if (i2 == 1) {
                Pj.getIns().enableAudioTransfer(-1);
            } else {
                Pj.getIns().disableAudioTransfer();
            }
        } else {
            Pj.getIns().controlMppOtherUser(this._uniqueID, i2);
        }
        this._onChaneableWebRTCConnectStatusListener.onChangeableWebRTCConnectStatus();
    }

    public boolean isMe() {
        return this._isMe;
    }

    public Bitmap getBitmap() {
        return this._bitmap;
    }
}
