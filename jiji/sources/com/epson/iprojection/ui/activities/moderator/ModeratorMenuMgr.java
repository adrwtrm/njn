package com.epson.iprojection.ui.activities.moderator;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;

/* loaded from: classes.dex */
public class ModeratorMenuMgr {
    protected static final int ID_ALLOW = 2131231265;
    protected static final int ID_DELIVER = 2131231272;
    protected static final int ID_DISCONNECT = 2131231277;
    protected static final int ID_PROHIBIT = 2131231283;
    protected static final int ID_START_MODERATOR = 2131231284;
    protected static final int ID_STOP_MODERATOR = 2131231285;
    protected static final int ID_WHATS_MODERATOR = 2131231286;
    protected static final int[] IDs = {R.id.menu_start_moderator, R.id.menu_stop_moderator, R.id.menu_disconnect_all, R.id.menu_prohibit, R.id.menu_allow, R.id.menu_deliver, R.id.menu_whats_moderator};
    protected PjConnectableActivity _activity;
    protected boolean _isAvailable = true;

    public ModeratorMenuMgr(PjConnectableActivity pjConnectableActivity) {
        this._activity = pjConnectableActivity;
    }

    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.moderator_menu, menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu == null) {
            return false;
        }
        boolean isClientMode = PrefUtils.isClientMode(this._activity);
        for (int i : IDs) {
            menu.findItem(i).setVisible(false);
        }
        if (this._isAvailable && Pj.getIns().isConnected()) {
            if (Pj.getIns().isModerator()) {
                if (!isClientMode) {
                    menu.findItem(R.id.menu_stop_moderator).setVisible(true);
                    menu.findItem(R.id.menu_whats_moderator).setVisible(true);
                }
                menu.findItem(R.id.menu_disconnect_all).setVisible(true);
                if (Pj.getIns().isSetMppScreenLock()) {
                    menu.findItem(R.id.menu_allow).setVisible(true);
                } else if (Pj.getIns().isEnableUserLock()) {
                    menu.findItem(R.id.menu_prohibit).setVisible(true);
                }
                if (Pj.getIns().isEnableMppDelivery()) {
                    menu.findItem(R.id.menu_deliver).setVisible(true);
                }
            } else if (!Pj.getIns().isEnableModeration() || Pj.getIns().getMppControlMode() == IOnConnectListener.MppControlMode.ModeratorEntry || isClientMode) {
                return false;
            } else {
                menu.findItem(R.id.menu_start_moderator).setVisible(true);
                menu.findItem(R.id.menu_whats_moderator).setVisible(true);
            }
            return true;
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_allow) {
            onClickAllowToOperate();
            return true;
        } else if (itemId == R.id.menu_deliver) {
            onClickDeliver();
            return true;
        } else if (itemId == R.id.menu_disconnect_all) {
            onClickAllDisconnect();
            return true;
        } else {
            switch (itemId) {
                case R.id.menu_prohibit /* 2131231283 */:
                    onClickProhibitToOperate();
                    return true;
                case R.id.menu_start_moderator /* 2131231284 */:
                    onClickStartModerator();
                    return true;
                case R.id.menu_stop_moderator /* 2131231285 */:
                    onClickStopModerator();
                    return true;
                case R.id.menu_whats_moderator /* 2131231286 */:
                    onClickWhatsModerator();
                    return true;
                default:
                    return true;
            }
        }
    }

    public void enable() {
        this._isAvailable = true;
    }

    public void disable() {
        this._isAvailable = false;
    }

    private void onClickStartModerator() {
        Pj.getIns().createStartModeratorDialog();
    }

    private void onClickStopModerator() {
        PjConnectableActivity pjConnectableActivity = this._activity;
        new OkCancelDialog(pjConnectableActivity, pjConnectableActivity.getString(R.string._WantQuitModerator_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorMenuMgr.lambda$onClickStopModerator$0(dialogInterface, i);
            }
        });
    }

    public static /* synthetic */ void lambda$onClickStopModerator$0(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Analytics.getIns().sendEvent(eCustomEvent.MODERATOR_END);
            Pj.getIns().setMppControlMode(0);
        }
    }

    private void onClickAllDisconnect() {
        PjConnectableActivity pjConnectableActivity = this._activity;
        new OkCancelDialog(pjConnectableActivity, pjConnectableActivity.getString(R.string._DisconnectAllUsers_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorMenuMgr.lambda$onClickAllDisconnect$1(dialogInterface, i);
            }
        });
    }

    public static /* synthetic */ void lambda$onClickAllDisconnect$1(DialogInterface dialogInterface, int i) {
        if (i == -1 && Pj.getIns().isConnected()) {
            Pj.getIns().disconnect(DisconReason.TerminateUI);
        }
    }

    private void onClickProhibitToOperate() {
        PjConnectableActivity pjConnectableActivity = this._activity;
        new OkCancelDialog(pjConnectableActivity, pjConnectableActivity.getString(R.string._DenyToUserOperateMsg_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr$$ExternalSyntheticLambda1
            {
                ModeratorMenuMgr.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorMenuMgr.this.m101xd2efe522(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$onClickProhibitToOperate$2$com-epson-iprojection-ui-activities-moderator-ModeratorMenuMgr */
    public /* synthetic */ void m101xd2efe522(DialogInterface dialogInterface, int i) {
        if (i == -1 && Pj.getIns().isEnableUserLock() && !Pj.getIns().isSetMppScreenLock()) {
            Pj.getIns().setMppScreenLock(true);
            this._activity.updateActionBar();
        }
    }

    private void onClickAllowToOperate() {
        PjConnectableActivity pjConnectableActivity = this._activity;
        new OkCancelDialog(pjConnectableActivity, pjConnectableActivity.getString(R.string._AllowToUserOperateMsg_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr$$ExternalSyntheticLambda0
            {
                ModeratorMenuMgr.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ModeratorMenuMgr.this.m100xb448a913(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$onClickAllowToOperate$3$com-epson-iprojection-ui-activities-moderator-ModeratorMenuMgr */
    public /* synthetic */ void m100xb448a913(DialogInterface dialogInterface, int i) {
        if (i == -1 && Pj.getIns().isEnableUserLock() && Pj.getIns().isSetMppScreenLock()) {
            Pj.getIns().setMppScreenLock(false);
            this._activity.updateActionBar();
        }
    }

    private void onClickDeliver() {
        Pj.getIns().createDeliveryDialog();
    }

    private void onClickWhatsModerator() {
        Intent intent = new Intent(this._activity, Activity_IntroModerator.class);
        if (this._activity instanceof Activity_Marker) {
            intent.putExtra(Activity_Marker.IntentTagMarker, "empty message");
        }
        this._activity.startActivityForResult(intent, CommonDefine.REQUEST_CODE_INTRO_MODERATOR);
    }
}
