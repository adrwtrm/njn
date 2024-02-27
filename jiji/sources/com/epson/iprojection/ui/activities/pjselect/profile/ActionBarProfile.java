package com.epson.iprojection.ui.activities.pjselect.profile;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class ActionBarProfile extends CustomActionBar {
    private static final int ID_BTN_SEARCH = 2131230842;
    private Button _btnSearch;

    public ActionBarProfile(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        layout(R.layout.toolbar_profile);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar
    public void setListener() {
        super.setListener();
        Button button = (Button) this._activity.findViewById(R.id.btnTitleSearch);
        this._btnSearch = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btnTitleSearch) {
            onClickSearchButton();
        } else {
            super.onClick(view);
        }
    }

    private void onClickSearchButton() {
        Pj.getIns().startManualSearchPj(null);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar, com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
        super.update();
        updateSearchButton();
    }

    private void updateSearchButton() {
        if (this._btnSearch == null) {
            return;
        }
        int dimensionPixelSize = this._activity.getResources().getDimensionPixelSize(R.dimen.TopBarButtonPadSize);
        this._btnSearch.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        this._btnSearch.setEnabled(Pj.getIns().hasManualSearchPj());
    }
}
