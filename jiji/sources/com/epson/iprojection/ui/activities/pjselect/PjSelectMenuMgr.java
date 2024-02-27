package com.epson.iprojection.ui.activities.pjselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class PjSelectMenuMgr extends ModeratorMenuMgr {
    protected static final int ID_MODERATOR = 2131231280;
    protected static final int ID_MODERATOR_INFO = 2131231281;
    private static final int[] IDs = {R.id.menu_moderator, R.id.menu_moderator_info};

    public PjSelectMenuMgr(PjConnectableActivity pjConnectableActivity) {
        super(pjConnectableActivity);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.pjselect_menu, menu);
        super.onCreateOptionsMenu(activity, menu);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean onPrepareOptionsMenu = super.onPrepareOptionsMenu(menu);
        for (int i : IDs) {
            menu.findItem(i).setVisible(false);
        }
        if (PrefUtils.isClientMode(this._activity) || !this._isAvailable || Pj.getIns().isConnected()) {
            return onPrepareOptionsMenu;
        }
        setVisibleModeratorMenu(menu);
        return true;
    }

    private void setVisibleModeratorMenu(Menu menu) {
        ConnectConfig connectConfig = new ConnectConfig(this._activity);
        MenuItem findItem = menu.findItem(R.id.menu_moderator);
        findItem.setVisible(true);
        findItem.setChecked(connectConfig.getInterruptSetting());
        menu.findItem(R.id.menu_moderator_info).setVisible(true);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case R.id.menu_moderator /* 2131231280 */:
                if (menuItem.isChecked()) {
                    changeModeratorChecked(menuItem);
                    return false;
                }
                showChangeModeratorDialog(menuItem);
                return false;
            case R.id.menu_moderator_info /* 2131231281 */:
                startInfoModerator();
                return false;
            default:
                return false;
        }
    }

    private void changeModeratorChecked(MenuItem menuItem) {
        menuItem.setChecked(!menuItem.isChecked());
        boolean isChecked = menuItem.isChecked();
        new ConnectConfig(this._activity).setInterruptSetting(isChecked);
        Pj.getIns().setNotInterruptUI(isChecked);
    }

    private void startInfoModerator() {
        this._activity.startActivityForResult(new Intent(this._activity, Activity_IntroModerator.class), CommonDefine.REQUEST_CODE_INTRO_MODERATOR);
    }

    private void showChangeModeratorDialog(final MenuItem menuItem) {
        AlertDialog create = new AlertDialog.Builder(this._activity).setMessage(this._activity.getString(R.string._AsModeratorOn_)).setPositiveButton(this._activity.getString(R.string._Enable_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.PjSelectMenuMgr$$ExternalSyntheticLambda0
            {
                PjSelectMenuMgr.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PjSelectMenuMgr.this.m130xa5dc592f(menuItem, dialogInterface, i);
            }
        }).setNeutralButton(this._activity.getString(R.string._Cancel_), (DialogInterface.OnClickListener) null).create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$showChangeModeratorDialog$0$com-epson-iprojection-ui-activities-pjselect-PjSelectMenuMgr  reason: not valid java name */
    public /* synthetic */ void m130xa5dc592f(MenuItem menuItem, DialogInterface dialogInterface, int i) {
        changeModeratorChecked(menuItem);
    }
}
