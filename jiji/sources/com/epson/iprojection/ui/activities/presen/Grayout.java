package com.epson.iprojection.ui.activities.presen;

import android.app.Activity;
import android.widget.TextView;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public class Grayout {
    private final Activity _activity;
    private TouchControlFrameLayout _layout = null;
    private boolean _isNowGrayout = false;

    public Grayout(Activity activity) {
        this._activity = activity;
    }

    public void initialize() {
        this._layout = (TouchControlFrameLayout) this._activity.findViewById(R.id.layout_presen_grayout);
        update();
    }

    public void hideLoading() {
        ((TextView) this._activity.findViewById(R.id.text_presen_loading)).setVisibility(8);
    }

    public void showLoading() {
        ((TextView) this._activity.findViewById(R.id.text_presen_loading)).setVisibility(0);
    }

    public void show() {
        this._layout.untouchable();
        fadeIn();
    }

    public void delete() {
        this._layout.touchable();
        fadeOut();
        showLoading();
    }

    public void fadeIn() {
        this._layout.setVisibility(0);
        this._isNowGrayout = true;
    }

    public void fadeOut() {
        this._layout.setVisibility(4);
        this._isNowGrayout = false;
    }

    public boolean isNowGrayout() {
        return this._isNowGrayout;
    }

    public void update() {
        if (this._isNowGrayout) {
            show();
        } else {
            delete();
        }
    }
}
