package com.epson.iprojection.ui.activities.moderator;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.activities.moderator.ListNameAdapter;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeViewBtn;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnWindowsChangedListener;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.common.uiparts.MakeUnpressableLayer;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Activity_Moderator extends Deliverable_Activity implements IOnClickWindowButtonListener, IOnClickButtonListener, AdapterView.OnItemClickListener, IOnChangeViewBtn, IOnWindowsChangedListener {
    private ListNameAdapter _adapter;
    private ListView _listView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this._isThumbnail = false;
        super.onCreate(bundle);
        if (MultiProjectionDisplaySettings.INSTANCE.isThumb()) {
            onChangeThumbnailAndList();
        }
        renewLayout(getIntent().getIntExtra(Deliverable_Activity.INTENT_TAG_WINDOW_POSITION, 0));
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this._adapter.updateDataList(Pj.getIns().getMppUserList());
        updateLayout(Pj.getIns().getMppLayout());
        ((MakeUnpressableLayer) findViewById(R.id.layout_make_unpressable)).update(this, Pj.getIns().getMppControlMode(), Pj.getIns().getModeratorUserInfo());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        renewLayout(0);
        this._adapter.updateDataList(Pj.getIns().getMppUserList());
        updateLayout(Pj.getIns().getMppLayout());
        ((MakeUnpressableLayer) findViewById(R.id.layout_make_unpressable)).update(this, Pj.getIns().getMppControlMode(), Pj.getIns().getModeratorUserInfo());
        super.updateActionBar();
    }

    protected void renewLayout(int i) {
        setContentView(R.layout.main_moderator);
        this._listView = (ListView) findViewById(R.id.list_mpp_menber);
        setDrag(this._listView, (LinearLayout) findViewById(R.id.layout_root));
        mySetOnDragListener(findViewById(R.id.view_empty));
        ActionBarModerator actionBarModerator = (ActionBarModerator) this._baseActionBar;
        actionBarModerator.layoutBtn();
        actionBarModerator.visible();
        actionBarModerator.setOnClickAppIconButton(this);
        actionBarModerator.setOnClickChangeViewButton(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarmoderator));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ArrayList<D_MppUserInfo> mppUserList = Pj.getIns().getMppUserList();
        if (mppUserList == null || mppUserList.size() == 0) {
            finish();
            return;
        }
        if (i == 0 && this._windowMgr != null) {
            i = this._windowMgr.getSelectingWindowID();
        }
        this._windowMgr = new WindowMgr(this, this, this._me, this, false, i, this._continuousOperationPreventer);
        this._windowMgr.setOnWindowChangedListener(this);
        ListNameAdapter listNameAdapter = new ListNameAdapter(this, this._windowMgr, this, this);
        this._adapter = listNameAdapter;
        listNameAdapter.updateDataList(mppUserList);
        this._listView.setAdapter((ListAdapter) this._adapter);
        this._listView.setOnItemClickListener(this);
        this._buttonMgr = new ButtonMgr(this, this, this, Pj.getIns().isEnableChangeMppControlMode());
        this._buttonMgr.setMeName(this._me.userName);
        this._meButton = (DraggableButton) findViewById(R.id.btn_multictrl_me);
    }

    private void setDrag(ListView listView, View view) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.Activity_Moderator.1
            {
                Activity_Moderator.this = this;
            }

            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view2, int i, long j) {
                ListNameAdapter.ViewHolder viewHolder = (ListNameAdapter.ViewHolder) view2.getTag();
                if (viewHolder.isDisconnected) {
                    return true;
                }
                final int i2 = (int) (viewHolder.lastTouchedX + 0.5f);
                final int i3 = (int) (viewHolder.lastTouchedY + 0.5f);
                if (Activity_Moderator.this._vib != null) {
                    MethodUtil.compatVibrate(Activity_Moderator.this._vib, 100L);
                }
                MethodUtil.compatStartDragAndDrop(null, new View.DragShadowBuilder(view2) { // from class: com.epson.iprojection.ui.activities.moderator.Activity_Moderator.1.1
                    {
                        AnonymousClass1.this = this;
                    }

                    @Override // android.view.View.DragShadowBuilder
                    public void onProvideShadowMetrics(Point point, Point point2) {
                        super.onProvideShadowMetrics(point, point2);
                        point2.x = i2;
                        point2.y = i3;
                    }

                    @Override // android.view.View.DragShadowBuilder
                    public void onDrawShadow(Canvas canvas) {
                        super.onDrawShadow(canvas);
                    }
                }, view2, 0);
                return true;
            }
        });
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
            } else if (this._windowMgr.getUniqueID(i2) == ((D_MppUserInfo) this._adapter.getItem(Integer.MIN_VALUE)).uniqueId) {
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
        D_MppUserInfo d_MppUserInfo = (D_MppUserInfo) this._adapter.getItem(Integer.MIN_VALUE);
        this._windowMgr.onClickWindow(i);
        this._windowMgr.changeWindow(1);
        this._windowMgr.setData(i, d_MppUserInfo.uniqueId, d_MppUserInfo.userName, convertStateToBtnKind(Pj.getIns().getProjectionMode()), true, null);
        this._windowMgr.visiblePlayBtn(i);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        D_MppUserInfo d_MppUserInfo = (D_MppUserInfo) this._adapter.getItem(i);
        SelectableLinearLayout selectableLinearLayout = (SelectableLinearLayout) view;
        ListView listView = (ListView) adapterView;
        if (d_MppUserInfo.disconnected) {
            return;
        }
        int selectingWindowID = this._windowMgr.getSelectingWindowID();
        long uniqueID = this._windowMgr.getUniqueID(selectingWindowID);
        boolean isActive = this._windowMgr.isActive(selectingWindowID);
        int i2 = 0;
        while (true) {
            if (i2 >= listView.getChildCount()) {
                break;
            }
            SelectableLinearLayout selectableLinearLayout2 = (SelectableLinearLayout) listView.getChildAt(i2);
            if (selectableLinearLayout2.getUniqueID() == uniqueID) {
                selectableLinearLayout2.cancelDisplay();
                break;
            }
            i2++;
        }
        if (uniqueID == d_MppUserInfo.uniqueId) {
            this._windowMgr.remove(uniqueID);
            if (uniqueID == ((D_MppUserInfo) this._adapter.getItem(Integer.MIN_VALUE)).uniqueId) {
                Pj.getIns().setProjectionMode(1);
            }
        } else {
            this._windowMgr.setData(selectingWindowID, d_MppUserInfo.uniqueId, d_MppUserInfo.userName, convertStateToBtnKind(d_MppUserInfo.status), false, null);
            selectableLinearLayout.setDisplay(d_MppUserInfo.disconnected, isActive);
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
        updateLayout(Pj.getIns().getMppLayout());
        updateToolBar(mppControlMode);
        ((MakeUnpressableLayer) findViewById(R.id.layout_make_unpressable)).update(this, Pj.getIns().getMppControlMode(), Pj.getIns().getModeratorUserInfo());
    }

    /* renamed from: com.epson.iprojection.ui.activities.moderator.Activity_Moderator$2 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$MppControlMode;

        static {
            int[] iArr = new int[IOnConnectListener.MppControlMode.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$MppControlMode = iArr;
            try {
                iArr[IOnConnectListener.MppControlMode.ModeratorAdmin.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$MppControlMode[IOnConnectListener.MppControlMode.ModeratorEntry.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void updateToolBar(IOnConnectListener.MppControlMode mppControlMode) {
        int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$interfaces$IOnConnectListener$MppControlMode[mppControlMode.ordinal()];
        if (i == 1) {
            Pj.getIns().stopThumbnail();
            this._baseActionBar.enable();
            this._menuMgr.enable();
        } else if (i == 2) {
            this._baseActionBar.disable();
            this._menuMgr.disable();
        } else {
            this._baseActionBar.disable();
            this._menuMgr.enable();
        }
        invalidateOptionsMenu();
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        super.onUpdateMPPUserList(arrayList, arrayList2);
        this._adapter.updateDataList(arrayList);
        updateLayout(arrayList2);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity
    protected void updateLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        D_MppUserInfo d_MppUserInfo;
        for (int i = 0; i < this._adapter.getCount(); i++) {
            long itemId = this._adapter.getItemId(i);
            if (itemId != -1) {
                int i2 = 0;
                while (true) {
                    if (i2 < this._listView.getChildCount()) {
                        SelectableLinearLayout selectableLinearLayout = (SelectableLinearLayout) this._listView.getChildAt(i2);
                        if (selectableLinearLayout.getUniqueID() == itemId) {
                            selectableLinearLayout.cancelDisplay();
                            break;
                        }
                        i2++;
                    }
                }
            }
        }
        this._windowMgr.removeAll();
        if (arrayList == null) {
            Lg.e("レイアウトが空です");
            return;
        }
        Iterator<D_MppLayoutInfo> it = arrayList.iterator();
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        while (it.hasNext()) {
            if (it.next().active) {
                i4++;
                if (i3 == -1) {
                    i3 = i5;
                }
            }
            i5++;
        }
        Iterator<D_MppLayoutInfo> it2 = arrayList.iterator();
        int i6 = 0;
        while (it2.hasNext()) {
            D_MppLayoutInfo next = it2.next();
            if (!next.empty) {
                D_MppUserInfo d_MppUserInfo2 = (D_MppUserInfo) this._adapter.getItem(next.uniqueId);
                if (d_MppUserInfo2 != null) {
                    if (d_MppUserInfo2.uniqueId == ((D_MppUserInfo) this._adapter.getItem(Integer.MIN_VALUE)).uniqueId) {
                        d_MppUserInfo = d_MppUserInfo2;
                        this._windowMgr.setData(i6, d_MppUserInfo2.uniqueId, d_MppUserInfo2.userName, convertStateToBtnKind(Pj.getIns().getProjectionMode()), true, null);
                    } else {
                        d_MppUserInfo = d_MppUserInfo2;
                        this._windowMgr.setData(i6, d_MppUserInfo.uniqueId, d_MppUserInfo.userName, convertStateToBtnKind(d_MppUserInfo.status), false, null);
                    }
                    int i7 = 0;
                    while (true) {
                        if (i7 >= this._listView.getChildCount()) {
                            break;
                        }
                        SelectableLinearLayout selectableLinearLayout2 = (SelectableLinearLayout) this._listView.getChildAt(i7);
                        if (selectableLinearLayout2.getUniqueID() == next.uniqueId) {
                            selectableLinearLayout2.setDisplay(d_MppUserInfo.disconnected, next.active);
                            break;
                        }
                        i7++;
                    }
                }
            }
            i6++;
        }
        if (i3 != -1) {
            this._windowMgr.changeWindow(i4, i3);
        }
        this._meButton.setStatus(this._windowMgr.getMeStatus());
        updateToolBar(Pj.getIns().getMppControlMode());
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeViewBtn
    public void onChangeThumbnailAndList() {
        this._isKillMyself = true;
        finish();
        Intent intent = new Intent(getApplicationContext(), Activity_Moderator_Thumbnail.class);
        if (this._windowMgr != null) {
            intent.putExtra(Deliverable_Activity.INTENT_TAG_WINDOW_POSITION, this._windowMgr.getSelectingWindowID());
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void mySetOnDragListener(View view) {
        view.setOnDragListener(new View.OnDragListener() { // from class: com.epson.iprojection.ui.activities.moderator.Activity_Moderator$$ExternalSyntheticLambda0
            {
                Activity_Moderator.this = this;
            }

            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                return Activity_Moderator.this.m97x649bae43(view2, dragEvent);
            }
        });
    }

    /* renamed from: lambda$mySetOnDragListener$0$com-epson-iprojection-ui-activities-moderator-Activity_Moderator */
    public /* synthetic */ boolean m97x649bae43(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 1) {
            onStartDrag();
            return true;
        } else if (action != 2) {
            if (action != 3) {
                if (action == 4) {
                    onEndDrag();
                }
                return false;
            }
            boolean isEnabledDrop = isEnabledDrop();
            onEndDrag();
            if (isEnabledDrop) {
                try {
                    onDropFromWindowToList(((TouchPosGettableImageView) dragEvent.getLocalState()).getWindID());
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener
    public void onDropListItem(int i, int i2) {
        super.onDropListItem(i, i2);
        D_MppUserInfo d_MppUserInfo = (D_MppUserInfo) this._adapter.getItem(i);
        if (d_MppUserInfo.disconnected) {
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

    @Override // com.epson.iprojection.ui.activities.moderator.Deliverable_Activity, com.epson.iprojection.ui.activities.moderator.interfaces.IOnWindowsChangedListener
    public void onWindowChanged() {
        SelectableLinearLayout selectableLinearLayout;
        for (int i = 0; i < 4; i++) {
            long uniqueID = this._windowMgr.getUniqueID(i);
            if (uniqueID != -1) {
                boolean isActive = this._windowMgr.isActive(i);
                D_MppUserInfo userInfoFromUniqueID = this._adapter.getUserInfoFromUniqueID(uniqueID);
                if (userInfoFromUniqueID != null) {
                    boolean z = userInfoFromUniqueID.disconnected;
                    int positionFromUniqueID = this._adapter.getPositionFromUniqueID(uniqueID);
                    if (positionFromUniqueID != -1 && (selectableLinearLayout = (SelectableLinearLayout) this._listView.getChildAt(positionFromUniqueID)) != null) {
                        selectableLinearLayout.setDisplay(z, isActive);
                    }
                }
            }
        }
    }
}
