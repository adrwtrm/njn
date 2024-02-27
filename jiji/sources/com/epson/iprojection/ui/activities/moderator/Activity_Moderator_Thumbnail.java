package com.epson.iprojection.ui.activities.moderator;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.moderator.ButtonMgrThumbnail;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeViewBtn;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickUserListener;
import com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Activity_Moderator_Thumbnail extends Deliverable_Activity implements IOnClickButtonListener, IOnClickUserListener, IOnChangeViewBtn {
    private MPPThumbnailManager _thumbMgr = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this._isThumbnail = true;
        super.onCreate(bundle);
        MultiProjectionDisplaySettings.INSTANCE.setThumb(true);
        renewLayout(false, getIntent().getIntExtra(Deliverable_Activity.INTENT_TAG_WINDOW_POSITION, 0));
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (Pj.getIns().isSelfProjection()) {
            this._thumbMgr.resume();
            updateLayout(Pj.getIns().getMppLayout());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        renewLayout(true, 0);
        this._thumbMgr.resumeWithRotate();
        updateLayout(Pj.getIns().getMppLayout());
        super.updateActionBar();
        super.recreateDrawerList();
    }

    protected void renewLayout(boolean z, int i) {
        setContentView(R.layout.main_mpp_moderator_thumbnail);
        ActionBarModerator actionBarModerator = (ActionBarModerator) this._baseActionBar;
        actionBarModerator.layoutBtn();
        actionBarModerator.visible();
        actionBarModerator.setOnClickAppIconButton(this);
        actionBarModerator.setOnClickChangeViewButton(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarmoderator));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        MPPThumbnailManager mPPThumbnailManager = new MPPThumbnailManager(this, this, this, getResources().getConfiguration().orientation == 1, z, this);
        this._thumbMgr = mPPThumbnailManager;
        mPPThumbnailManager.setModerator(Pj.getIns().isModerator());
        if (i == 0 && this._windowMgr != null) {
            i = this._windowMgr.getSelectingWindowID();
        }
        this._windowMgr = new WindowMgr(this, this, this._me, this, true, i, this._continuousOperationPreventer);
        ButtonMgrThumbnail.eSize esize = MultiProjectionDisplaySettings.INSTANCE.isSmall() ? ButtonMgrThumbnail.eSize.SMALL : ButtonMgrThumbnail.eSize.LARGE;
        if (this._buttonMgr != null) {
            esize = ((ButtonMgrThumbnail) this._buttonMgr).getSizeType();
        }
        this._buttonMgr = new ButtonMgrThumbnail(this, this, this, Pj.getIns().isEnableChangeMppControlMode());
        this._buttonMgr.setMeName(this._me.userName);
        if (esize != ButtonMgrThumbnail.eSize.NONE) {
            ((ButtonMgrThumbnail) this._buttonMgr).setSizeType(esize);
        }
        this._meButton = (DraggableButton) findViewById(R.id.btn_multictrl_me);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener
    public void onClickMeOnlyButton() {
        boolean z;
        boolean z2;
        ToastMgr.getIns().show(this, ToastMgr.Type.ProjectMe);
        int i = 0;
        int i2 = 0;
        while (true) {
            z = true;
            if (i2 >= 4) {
                z2 = false;
                break;
            } else if (this._windowMgr.getUniqueID(i2) == this._me.uniqueId) {
                this._windowMgr.onClickWindow(i2);
                this._windowMgr.changeWindow(1);
                z2 = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z2) {
            while (true) {
                if (i >= 4) {
                    z = z2;
                    break;
                } else if (!this._windowMgr.existsContents(i)) {
                    activateWindowMe(i);
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                activateWindowMe(3);
            }
        }
        updateLayout();
    }

    private void activateWindowMe(int i) {
        this._windowMgr.onClickWindow(i);
        this._windowMgr.changeWindow(1);
        this._windowMgr.setData(i, this._me.uniqueId, this._me.userName, convertStateToBtnKind(Pj.getIns().getProjectionMode()), true, null);
        this._windowMgr.visiblePlayBtn(i);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickUserListener
    public void onClickUser(D_MppUserInfo d_MppUserInfo) {
        if (d_MppUserInfo.disconnected) {
            return;
        }
        int selectingWindowID = this._windowMgr.getSelectingWindowID();
        long uniqueID = this._windowMgr.getUniqueID(selectingWindowID);
        if (uniqueID == d_MppUserInfo.uniqueId) {
            this._windowMgr.remove(uniqueID);
            if (uniqueID == this._me.uniqueId) {
                Pj.getIns().setProjectionMode(1);
            }
        } else {
            this._windowMgr.setData(selectingWindowID, d_MppUserInfo.uniqueId, d_MppUserInfo.userName, convertStateToBtnKind(d_MppUserInfo.status), false, null);
            if (this._windowMgr.isActive(selectingWindowID) && Pj.getIns().isModerator()) {
                this._windowMgr.visiblePlayBtn(selectingWindowID);
            } else {
                this._windowMgr.invisiblePlayBtn(selectingWindowID);
            }
        }
        updateLayout();
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
        Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        super.onChangeMPPControlMode(mppControlMode);
        if (mppControlMode == IOnConnectListener.MppControlMode.Collaboration) {
            super.finish();
            this._isKillMyself = true;
            startActivity(new Intent(getApplicationContext(), Activity_Moderator.class));
            overridePendingTransition(0, 0);
            MultiProjectionDisplaySettings.INSTANCE.setThumb(false);
        }
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        super.onUpdateMPPUserList(arrayList, arrayList2);
        this._thumbMgr.onUpdateMPPUserList(arrayList, arrayList2);
        updateLayout(arrayList2);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity
    protected void updateLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        this._windowMgr.removeAll();
        if (arrayList == null) {
            Lg.e("レイアウトが空です");
            return;
        }
        Iterator<D_MppLayoutInfo> it = arrayList.iterator();
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            if (it.next().active) {
                i2++;
                if (i == -1) {
                    i = i3;
                }
            }
            i3++;
        }
        Iterator<D_MppLayoutInfo> it2 = arrayList.iterator();
        int i4 = 0;
        while (it2.hasNext()) {
            D_MppLayoutInfo next = it2.next();
            if (!next.empty) {
                if (next.uniqueId == this._me.uniqueId) {
                    this._windowMgr.setData(i4, next.uniqueId, this._me.userName, convertStateToBtnKind(Pj.getIns().getProjectionMode()), true, this._thumbMgr.getBitmap(0));
                } else {
                    D_MppUserInfo mppUserInfoByUniqueID = Pj.getIns().getMppUserInfoByUniqueID(next.uniqueId);
                    this._windowMgr.setData(i4, next.uniqueId, mppUserInfoByUniqueID.userName, convertStateToBtnKind(mppUserInfoByUniqueID.status), false, this._thumbMgr.getBitmap(mppUserInfoByUniqueID));
                }
            } else {
                this._windowMgr.remove(i4);
            }
            i4++;
        }
        if (i != -1) {
            this._windowMgr.changeWindow(i2, i);
        }
        this._meButton.setStatus(this._windowMgr.getMeStatus());
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeViewBtn
    public void onChangeThumbnailAndList() {
        this._isKillMyself = true;
        finish();
        Intent intent = new Intent(getApplicationContext(), Activity_Moderator.class);
        if (this._windowMgr != null) {
            intent.putExtra(Deliverable_Activity.INTENT_TAG_WINDOW_POSITION, this._windowMgr.getSelectingWindowID());
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
        MultiProjectionDisplaySettings.INSTANCE.setThumb(false);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener
    public void onClickThumbnailLargeButton() {
        this._thumbMgr.setThumbnailSize(false);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener
    public void onClickThumbnailSmallButton() {
        this._thumbMgr.setThumbnailSize(true);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener
    public void onClickThumbnailReloadButton() {
        this._thumbMgr.requestThumbnail();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onThumbnailInfo(D_ThumbnailInfo d_ThumbnailInfo) {
        if (d_ThumbnailInfo.bufferSize > 0 && d_ThumbnailInfo.bufByte.length > 0) {
            int i = 0;
            while (true) {
                if (i >= 4) {
                    break;
                } else if (this._windowMgr.getUniqueID(i) == d_ThumbnailInfo.userUniqueId) {
                    this._windowMgr.setBitmap(i, BitmapFactory.decodeByteArray(d_ThumbnailInfo.bufByte, 0, d_ThumbnailInfo.bufByte.length));
                    break;
                } else {
                    i++;
                }
            }
        }
        this._thumbMgr.onThumbnailInfo(d_ThumbnailInfo);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener
    public void onDropListItem(int i, int i2) {
        D_MppUserInfo d_MppUserInfo;
        super.onDropListItem(i, i2);
        ArrayList<D_MppUserInfo> mppUserList = Pj.getIns().getMppUserList();
        if (mppUserList.size() <= i || (d_MppUserInfo = mppUserList.get(i)) == null || d_MppUserInfo.disconnected) {
            return;
        }
        this._windowMgr.setData(i2, d_MppUserInfo.uniqueId, d_MppUserInfo.userName, convertStateToBtnKind(d_MppUserInfo.status), false, null);
        if (this._windowMgr.isActive(i2) && (Pj.getIns().isModerator() || this._windowMgr.getWindow(i2).isMe())) {
            this._windowMgr.visiblePlayBtn(i2);
        } else {
            this._windowMgr.invisiblePlayBtn(i2);
        }
        updateLayout();
    }
}
