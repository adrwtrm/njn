package com.epson.iprojection.ui.activities.web.menu;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr;
import com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public class WebMenu extends ModeratorMenuMgr {
    private static final int ID_ADD_BKMK = 2131231266;
    private static final int ID_BOOKMARK = 2131231267;
    private static final int ID_FORWARD = 2131231268;
    private static final int ID_HISTORY = 2131231269;
    private static final int ID_RELOAD = 2131231270;
    private static final int ID_STOP = 2131231271;
    private final IOnClickBtnListener _impl;

    public WebMenu(PjConnectableActivity pjConnectableActivity, IOnClickBtnListener iOnClickBtnListener) {
        super(pjConnectableActivity);
        this._impl = iOnClickBtnListener;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.web_menu, menu);
        super.onCreateOptionsMenu(activity, menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu, boolean z, boolean z2, boolean z3) {
        menu.findItem(R.id.menu_browser_forward).setVisible(true);
        menu.findItem(R.id.menu_browser_reload).setVisible(true);
        menu.findItem(R.id.menu_browser_stop).setVisible(true);
        menu.findItem(R.id.menu_browser_history).setVisible(true);
        menu.findItem(R.id.menu_browser_bookmark).setVisible(true);
        menu.findItem(R.id.menu_browser_add_bookmark).setVisible(true);
        menu.findItem(R.id.menu_browser_forward).setEnabled(z);
        menu.findItem(R.id.menu_browser_add_bookmark).setEnabled(z3);
        if (z2) {
            menu.findItem(R.id.menu_browser_reload).setVisible(false);
        } else {
            menu.findItem(R.id.menu_browser_stop).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case R.id.menu_browser_add_bookmark /* 2131231266 */:
                this._impl.onClick_AddBookMarkBtn();
                return true;
            case R.id.menu_browser_bookmark /* 2131231267 */:
                this._impl.onClick_BookMarkBtn();
                return true;
            case R.id.menu_browser_forward /* 2131231268 */:
                this._impl.onClick_ForwardBtn();
                return true;
            case R.id.menu_browser_history /* 2131231269 */:
                this._impl.onClick_HistoryBtn();
                return true;
            case R.id.menu_browser_reload /* 2131231270 */:
                this._impl.onClick_ReloadBtn();
                return true;
            case R.id.menu_browser_stop /* 2131231271 */:
                this._impl.onClick_StopBtn();
                return true;
            default:
                return true;
        }
    }
}
