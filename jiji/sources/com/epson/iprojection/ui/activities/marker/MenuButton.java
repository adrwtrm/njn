package com.epson.iprojection.ui.activities.marker;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public class MenuButton extends ModeratorMenuMgr {
    public static final int MENU_ID_SAVE = 2131231279;
    private boolean _enabledMenuSave;
    private final IPaintMenuButtonClickListener _impl;

    public MenuButton(PjConnectableActivity pjConnectableActivity, IPaintMenuButtonClickListener iPaintMenuButtonClickListener) {
        super(pjConnectableActivity);
        this._enabledMenuSave = true;
        this._impl = iPaintMenuButtonClickListener;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.painter_menu, menu);
        super.onCreateOptionsMenu(activity, menu);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem findItem = menu.findItem(R.id.menu_marker_save);
        findItem.setVisible(true);
        findItem.setEnabled(this._enabledMenuSave);
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        if (menuItem.getItemId() == R.id.menu_marker_save) {
            this._impl.onClickMenuSaveButton();
            return true;
        }
        return true;
    }

    public void setSaveMenuEnabled(boolean z) {
        this._enabledMenuSave = z;
    }
}
