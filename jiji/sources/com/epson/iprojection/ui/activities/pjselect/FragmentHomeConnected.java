package com.epson.iprojection.ui.activities.pjselect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.fragment.app.FragmentActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.common.IntentDefine;
import com.epson.iprojection.common.utils.BroadcastReceiverUtils;
import com.epson.iprojection.ui.activities.pjselect.connected.PanelButtons;
import com.epson.iprojection.ui.activities.pjselect.connected.PjCyclicSearchThread;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class FragmentHomeConnected extends FragmentHomeBaseSelected {
    protected LinearLayout _layoutInterrupt;
    protected LinearLayout _layoutModerator;
    protected LinearLayout _layoutProhibit;
    protected PanelButtons _panelButtons;
    private final BroadcastReceiver _receiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeConnected.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            FragmentActivity activity;
            Switch r1;
            try {
                if (!Objects.equals(intent.getAction(), IntentDefine.INTENT_ACTION_MIRRORING_OFF) || (activity = FragmentHomeConnected.this.getActivity()) == null || (r1 = (Switch) activity.findViewById(R.id.switch_mirroring)) == null) {
                    return;
                }
                r1.setChecked(false);
            } catch (Exception unused) {
            }
        }
    };
    private PjCyclicSearchThread _searchThread;

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    protected int getLayoutId() {
        return R.layout.main_home_connected;
    }

    public static FragmentHomeBase newInstance() {
        FragmentHomeConnected fragmentHomeConnected = new FragmentHomeConnected();
        fragmentHomeConnected.setArguments(new Bundle());
        return fragmentHomeConnected;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected, com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        setupViews(viewGroup2);
        this._panelRootLayout = (LinearLayout) viewGroup2.findViewById(R.id.layout_panel_root);
        addPanelViews((LinearLayout) viewGroup2.findViewById(R.id.layout_panel_root));
        BroadcastReceiverUtils.INSTANCE.registerMirroingOff(requireContext(), this._receiver);
        return viewGroup2;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Context context = getContext();
        if (context != null) {
            try {
                context.unregisterReceiver(this._receiver);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        PjCyclicSearchThread pjCyclicSearchThread = this._searchThread;
        if (pjCyclicSearchThread != null) {
            pjCyclicSearchThread.finish();
        }
        PjCyclicSearchThread pjCyclicSearchThread2 = new PjCyclicSearchThread();
        this._searchThread = pjCyclicSearchThread2;
        pjCyclicSearchThread2.start();
        this._panelButtons.update(this._panelRootLayout);
        updateModeratorDisplayStatus();
        updateSpoilerView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        PjCyclicSearchThread pjCyclicSearchThread = this._searchThread;
        if (pjCyclicSearchThread != null) {
            pjCyclicSearchThread.finish();
            this._searchThread = null;
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    protected void addPanelViews(LinearLayout linearLayout) {
        if (Pj.getIns().isAllPjTypeBusiness()) {
            this._panelViews = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.inflater_home_connected_panels_business, (ViewGroup) null);
        } else if (Pj.getIns().isAllPjTypeHome()) {
            this._panelViews = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.inflater_home_connected_panels_home, (ViewGroup) null);
        } else {
            this._panelViews = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.inflater_home_connected_panels_signage, (ViewGroup) null);
        }
        linearLayout.addView(this._panelViews);
        this._panelButtons = new PanelButtons(getActivity(), linearLayout, ((Activity_PjSelect) getActivity()).getDrawerList());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    public void setupViews(View view) {
        super.setupViews(view);
        this._layoutInterrupt = (LinearLayout) view.findViewById(R.id.layout_display_interrupt);
        this._layoutModerator = (LinearLayout) view.findViewById(R.id.layout_display_moderator);
        this._layoutProhibit = (LinearLayout) view.findViewById(R.id.layout_display_prohibit);
        updateModeratorDisplayStatus();
        updateListVisibility();
    }

    protected void updateModeratorDisplayStatus() {
        LinearLayout linearLayout = this._layoutInterrupt;
        if (linearLayout == null || this._layoutModerator == null || this._layoutProhibit == null) {
            return;
        }
        linearLayout.setVisibility(8);
        this._layoutModerator.setVisibility(8);
        this._layoutProhibit.setVisibility(8);
        if (Pj.getIns().isNoInterrupt()) {
            this._layoutInterrupt.setVisibility(0);
        } else if (Pj.getIns().isMppLockedByMe()) {
            this._layoutProhibit.setVisibility(0);
        } else if (Pj.getIns().isModerator()) {
            this._layoutModerator.setVisibility(0);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        super.onChangeMPPControlMode(mppControlMode);
        updateModeratorDisplayStatus();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onChangedScreenLockByMe(boolean z) {
        super.onChangeScreenLockStatus(z);
        updateModeratorDisplayStatus();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason) {
        super.onDisconnect(i, disconedReason);
        this._panelButtons.update(this._panelRootLayout);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
        super.onDisconnectOne(i, disconedReason);
        updateSpoilerView();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onConnectSucceed() {
        super.onConnectSucceed();
        PanelButtons panelButtons = this._panelButtons;
        if (panelButtons != null) {
            panelButtons.update(this._panelRootLayout);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    protected ArrayList<ConnectPjInfo> getPjList() {
        return Pj.getIns().getNowConnectingPJList();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onChangeMirroringSwitch() {
        this._panelButtons.update(this._panelRootLayout);
    }
}
