package com.epson.iprojection.ui.common.actionbar.base;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public abstract class BaseCustomActionBar {
    protected AppCompatActivity _activity;

    public abstract void disable();

    public abstract void enable();

    public abstract void setFlag_sendsImgWhenConnect();

    public abstract void setOnClickAppIconButton(IOnClickAppIconButton iOnClickAppIconButton);

    public abstract void update();

    public abstract void updateTopBarGroup();

    public BaseCustomActionBar(AppCompatActivity appCompatActivity) {
        this._activity = appCompatActivity;
    }

    public void layout(int i) {
        ActionBar supportActionBar = this._activity.getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setDisplayOptions(16, 16);
        supportActionBar.setDisplayShowHomeEnabled(false);
        supportActionBar.setCustomView(i);
        if (Pj.getIns().isConnected()) {
            setColorConnected();
        } else {
            setColorDisconnected();
        }
    }

    public void setColorConnected() {
        setColorConnected(R.color.action_bar_background_connect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setColorConnected(int i) {
        ActionBar supportActionBar = this._activity.getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setBackgroundDrawable(this._activity.getApplicationContext().getDrawable(i));
    }

    public void setColorDisconnected() {
        setColorDisconnected(R.color.action_bar_background);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setColorDisconnected(int i) {
        ActionBar supportActionBar = this._activity.getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setBackgroundDrawable(this._activity.getApplicationContext().getDrawable(i));
    }
}
