package com.epson.iprojection.ui.activities.moderator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.moderator.Deliverable_Activity;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeableWebRTCConnectStatusListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import com.epson.iprojection.ui.activities.support.intro.userctrl.Activity_IntroUserCtrl;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Deliverable_Activity extends PjConnectableActivity implements IOnClickButtonListener, IOnClickWindowButtonListener, IOnDropListener, IOnDragStateListener {
    public static final String INTENT_TAG_WINDOW_POSITION = "IntentTagWindowPosition";
    public static final String KEY_ENABLECHANGEVIEW = "enableChangeView";
    public static final String KEY_EVER_SEEN_USERCTRL = "key_ever_seen_userctrl";
    public static final String KEY_ISWHITE = "isWhite";
    public static final int REQUEST_CODE_INTRO = 2002;
    public static final int RESULT_CODE = 50;
    protected ButtonMgr _buttonMgr;
    protected D_MppUserInfo _me;
    protected DraggableButton _meButton;
    protected ModeratorMenuMgr _menuMgr;
    protected Vibrator _vib;
    protected WindowMgr _windowMgr = null;
    protected boolean _isFinishMe = false;
    protected boolean _isThumbnail = false;
    protected boolean _isKillMyself = false;
    protected boolean _isDragging = false;
    protected boolean _isChangedLayoutOnDragging = false;
    protected ContinuousOperationPreventer _continuousOperationPreventer = new ContinuousOperationPreventer();

    /* JADX INFO: Access modifiers changed from: protected */
    public int convertStateToBtnKind(int i) {
        if (i != 2) {
            return i != 4 ? 0 : 2;
        }
        return 1;
    }

    public void onClickMeOnlyButton() {
    }

    public void onClickThumbnailLargeButton() {
    }

    public void onClickThumbnailReloadButton() {
    }

    public void onClickThumbnailSmallButton() {
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity
    protected boolean shouldDisplaySatisfactionViewWhenDisconnect() {
        return false;
    }

    protected void updateLayout(ArrayList<D_MppLayoutInfo> arrayList) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        pushDrawerStatus(eDrawerMenuItem.UserCtl);
        this._baseActionBar = new ActionBarModerator(this, this._isThumbnail);
        this._menuMgr = new ModeratorMenuMgr(this);
        D_MppUserInfo myUserInfo = Pj.getIns().getMyUserInfo();
        this._me = myUserInfo;
        if (myUserInfo == null) {
            finish();
            return;
        }
        if (!isAlreadySeen()) {
            startActivityForResult(new Intent(this, Activity_IntroUserCtrl.class), 2002);
            setAlreadySeen();
        }
        this._vib = (Vibrator) getSystemService("vibrator");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (!Pj.getIns().isConnected()) {
            super.finish();
        }
        this._meButton.setStatus(this._windowMgr.getMeStatus());
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        popDrawerStatus();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void finish() {
        super.finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2002 && i2 == 0) {
            finish();
        }
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowButtonListener
    public void onClickWindowButton(int i) {
        if (i == 0) {
            this._windowMgr.changeWindow(1);
        } else if (i == 1) {
            this._windowMgr.changeWindow(2);
        } else if (i == 2) {
            this._windowMgr.changeWindow(4);
        }
        updateLayout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateLayout() {
        ArrayList<D_MppLayoutInfo> arrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            D_MppLayoutInfo d_MppLayoutInfo = new D_MppLayoutInfo();
            if (this._windowMgr.isActive(i)) {
                d_MppLayoutInfo.active = true;
            }
            if (this._windowMgr.existsContents(i)) {
                d_MppLayoutInfo.uniqueId = this._windowMgr.getUniqueID(i);
            } else {
                d_MppLayoutInfo.empty = true;
            }
            arrayList.add(d_MppLayoutInfo);
        }
        Pj.getIns().updateMppLayout(arrayList);
        this._meButton.setStatus(this._windowMgr.getMeStatus());
        this._continuousOperationPreventer.onChangeableWebRTCConnectStatus();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        super.onDisconnect(i, disconedReason, z);
        setDragging(false);
        setChangedLayoutOnDragging(false);
        super.finish();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        super.onChangeMPPControlMode(mppControlMode);
        setDragging(false);
        setChangedLayoutOnDragging(false);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        super.onUpdateMPPUserList(arrayList, arrayList2);
        if (isDragging()) {
            setChangedLayoutOnDragging(true);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeMPPLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        super.onChangeMPPLayout(arrayList);
        if (isDragging()) {
            setChangedLayoutOnDragging(true);
        }
        updateLayout(arrayList);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener
    public void onDropMe(int i) {
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
        Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
        this._windowMgr.setData(i, this._me.uniqueId, this._me.userName, convertStateToBtnKind(this._me.status), true, null);
        if (this._windowMgr.isActive(i)) {
            this._windowMgr.visiblePlayBtn(i);
        } else {
            this._windowMgr.invisiblePlayBtn(i);
        }
        updateLayout();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        this._menuMgr.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean onPrepareOptionsMenu = this._menuMgr.onPrepareOptionsMenu(menu);
        super.onPrepareOptionsMenu(menu);
        return onPrepareOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this._menuMgr.onOptionsItemSelected(menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

    private boolean isAlreadySeen() {
        return PrefUtils.read(this, KEY_EVER_SEEN_USERCTRL) != null;
    }

    private void setAlreadySeen() {
        PrefUtils.write(this, KEY_EVER_SEEN_USERCTRL, "empty_message", (SharedPreferences.Editor) null);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener
    public void onDropFromWindowToList(int i) {
        this._windowMgr.remove(i);
        updateLayout(Pj.getIns().getMppLayout());
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
        Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
    }

    public void onWindowChanged() {
        this._meButton.setStatus(this._windowMgr.getMeStatus());
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener
    public void onClickMe() {
        int selectingWindowID = this._windowMgr.getSelectingWindowID();
        long uniqueID = this._windowMgr.getUniqueID(selectingWindowID);
        if (uniqueID == this._me.uniqueId) {
            this._windowMgr.remove(uniqueID);
            if (uniqueID == Pj.getIns().getMyUserInfo().uniqueId) {
                Pj.getIns().setProjectionMode(1);
            }
        } else {
            this._windowMgr.setData(selectingWindowID, this._me.uniqueId, this._me.userName, convertStateToBtnKind(this._me.status), true, null);
            if (this._windowMgr.isActive(selectingWindowID) && Pj.getIns().isModerator()) {
                this._windowMgr.visiblePlayBtn(selectingWindowID);
            } else {
                this._windowMgr.invisiblePlayBtn(selectingWindowID);
            }
        }
        updateLayout();
    }

    public void onDropListItem(int i, int i2) {
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
        Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener
    public void onStartDrag() {
        setDragging(true);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener
    public void onEndDrag() {
        setDragging(false);
        setChangedLayoutOnDragging(false);
        this._continuousOperationPreventer.onChangeableWebRTCConnectStatus();
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener
    public boolean isEnabledDrop() {
        return !isChangedLayoutOnDragging();
    }

    protected boolean isDragging() {
        return this._isDragging;
    }

    protected void setDragging(boolean z) {
        this._isDragging = z;
    }

    protected boolean isChangedLayoutOnDragging() {
        return this._isChangedLayoutOnDragging;
    }

    protected void setChangedLayoutOnDragging(boolean z) {
        this._isChangedLayoutOnDragging = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ContinuousOperationPreventer implements IOnChangeableWebRTCConnectStatusListener {
        private final Runnable _goneUnpressableLayer;
        private final Handler _handler;

        private ContinuousOperationPreventer() {
            this._handler = new Handler(Looper.getMainLooper());
            this._goneUnpressableLayer = new Runnable() { // from class: com.epson.iprojection.ui.activities.moderator.Deliverable_Activity$ContinuousOperationPreventer$$ExternalSyntheticLambda0
                {
                    Deliverable_Activity.ContinuousOperationPreventer.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Deliverable_Activity.ContinuousOperationPreventer.this.m98x228159d7();
                }
            };
        }

        @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeableWebRTCConnectStatusListener
        public void onChangeableWebRTCConnectStatus() {
            Deliverable_Activity.this.findViewById(R.id.layout_unpressable_after_command).setVisibility(0);
            this._handler.removeCallbacks(this._goneUnpressableLayer);
            this._handler.postDelayed(this._goneUnpressableLayer, 1000L);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-moderator-Deliverable_Activity$ContinuousOperationPreventer  reason: not valid java name */
        public /* synthetic */ void m98x228159d7() {
            Deliverable_Activity.this.findViewById(R.id.layout_unpressable_after_command).setVisibility(8);
        }
    }
}
