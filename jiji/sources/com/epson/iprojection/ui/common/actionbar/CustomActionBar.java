package com.epson.iprojection.ui.common.actionbar;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;

/* loaded from: classes.dex */
public class CustomActionBar extends BaseCustomActionBar implements View.OnClickListener {
    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void disable() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void enable() {
    }

    public void onClick(View view) {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setFlag_sendsImgWhenConnect() {
    }

    public void setListener() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setOnClickAppIconButton(IOnClickAppIconButton iOnClickAppIconButton) {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void updateTopBarGroup() {
    }

    public CustomActionBar(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void layout(int i) {
        super.layout(i);
        setListener();
        update();
    }
}
