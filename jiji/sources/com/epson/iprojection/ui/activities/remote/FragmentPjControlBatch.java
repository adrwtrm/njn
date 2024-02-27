package com.epson.iprojection.ui.activities.remote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.CommandSender;
import com.epson.iprojection.ui.activities.remote.commandsend.Contract;
import com.epson.iprojection.ui.activities.remote.commandsend.D_SendCommand;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FragmentPjControlBatch extends FragmentPjControlBase implements View.OnClickListener, Contract.IOnFinishedEscvpSending {
    public static final String ANALYTICS_CONTROLALL = "FragmentPjControl_all";
    private static final int ID_BTN_MUTE_OFF = 2131230885;
    private static final int ID_BTN_MUTE_ON = 2131230886;
    private static final int ID_BTN_POWER_OFF = 2131230889;
    private static final int ID_BTN_POWER_ON = 2131230890;
    private static final String KEY_PJ_INFO = "key_pj_info";
    private static final String KEY_PJ_LIST = "key_pj_list";
    private final ArrayList<Button> _btnList = new ArrayList<>();
    private final CommandSender _commandSender = new CommandSender();
    private boolean _isBatchProcessing;

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    protected int getLayoutId() {
        return R.layout.main_conpj_pjcontrol_all;
    }

    public static FragmentPjControlBatch newInstance(D_HistoryInfo d_HistoryInfo, ArrayList<D_HistoryInfo> arrayList) {
        FragmentPjControlBatch fragmentPjControlBatch = new FragmentPjControlBatch();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PJ_INFO, d_HistoryInfo);
        bundle.putSerializable(KEY_PJ_LIST, arrayList);
        fragmentPjControlBatch.setArguments(bundle);
        return fragmentPjControlBatch;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public D_HistoryInfo getPjInfo() {
        return (D_HistoryInfo) getArguments().getSerializable(KEY_PJ_INFO);
    }

    public ArrayList<D_HistoryInfo> getPjList() {
        return (ArrayList) getArguments().getSerializable(KEY_PJ_LIST);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        initialize(onCreateView);
        return onCreateView;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void enable() {
        setStateButtons(true);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void disable() {
        setStateButtons(false);
    }

    private void initialize(View view) {
        setButtonList(view);
        setStateButtons(!Pj.getIns().isConnected() || Pj.getIns().isEnablePJControl());
    }

    private void setButtonList(View view) {
        this._btnList.clear();
        this._btnList.add((Button) view.findViewById(R.id.btn_pjctl_power_on));
        this._btnList.add((Button) view.findViewById(R.id.btn_pjctl_power_off));
        this._btnList.add((Button) view.findViewById(R.id.btn_pjctl_mute_on));
        this._btnList.add((Button) view.findViewById(R.id.btn_pjctl_mute_off));
        Iterator<Button> it = this._btnList.iterator();
        while (it.hasNext()) {
            it.next().setOnClickListener(this);
        }
    }

    private void setStateButtons(boolean z) {
        Iterator<Button> it = this._btnList.iterator();
        while (it.hasNext()) {
            it.next().setEnabled(z);
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, android.view.View.OnClickListener
    public void onClick(View view) {
        disable();
        new Handler().postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.FragmentPjControlBatch$$ExternalSyntheticLambda2
            {
                FragmentPjControlBatch.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FragmentPjControlBatch.this.enable();
            }
        }, 500L);
        switch (view.getId()) {
            case R.id.btn_pjctl_mute_off /* 2131230885 */:
                onClickMuteOffButton();
                return;
            case R.id.btn_pjctl_mute_on /* 2131230886 */:
                onClickMuteOnButton();
                return;
            case R.id.btn_pjctl_pc /* 2131230887 */:
            case R.id.btn_pjctl_power /* 2131230888 */:
            default:
                Lg.e("対応するボタンが見つかりませんでした");
                return;
            case R.id.btn_pjctl_power_off /* 2131230889 */:
                onClickPowerOffButton();
                return;
            case R.id.btn_pjctl_power_on /* 2131230890 */:
                onClickPowerOnButton();
                return;
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void changeFlipper(boolean z, boolean z2) {
        sendGoogleAnalyticsScreenName(ANALYTICS_CONTROLALL);
    }

    @Override // com.epson.iprojection.ui.activities.remote.commandsend.Contract.IOnFinishedEscvpSending
    public void onFinishedEscvpSending() {
        this._isBatchProcessing = false;
    }

    private void onClickPowerOnButton() {
        new AlertDialog.Builder(getActivity()).setMessage(getActivity().getString(R.string._TurnOffProjectors_)).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.remote.FragmentPjControlBatch$$ExternalSyntheticLambda3
            {
                FragmentPjControlBatch.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FragmentPjControlBatch.this.m176x76400a92(dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onClickPowerOnButton$0$com-epson-iprojection-ui-activities-remote-FragmentPjControlBatch  reason: not valid java name */
    public /* synthetic */ void m176x76400a92(DialogInterface dialogInterface, int i) {
        sendBatchEscvpCommand("PWR ON");
    }

    private void onClickPowerOffButton() {
        new AlertDialog.Builder(getActivity()).setMessage(getActivity().getString(R.string._TurnOnProjectors_)).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.remote.FragmentPjControlBatch$$ExternalSyntheticLambda1
            {
                FragmentPjControlBatch.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FragmentPjControlBatch.this.m175x8d28a111(dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onClickPowerOffButton$1$com-epson-iprojection-ui-activities-remote-FragmentPjControlBatch  reason: not valid java name */
    public /* synthetic */ void m175x8d28a111(DialogInterface dialogInterface, int i) {
        sendBatchEscvpCommand("PWR OFF");
    }

    private void onClickMuteOnButton() {
        sendBatchEscvpCommand("MUTE ON");
    }

    private void onClickMuteOffButton() {
        sendBatchEscvpCommand("MUTE OFF");
    }

    private void sendBatchEscvpCommand(final String str) {
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.FragmentPjControlBatch$$ExternalSyntheticLambda0
            {
                FragmentPjControlBatch.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FragmentPjControlBatch.this.m177x2ffb321b(str);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$sendBatchEscvpCommand$2$com-epson-iprojection-ui-activities-remote-FragmentPjControlBatch  reason: not valid java name */
    public /* synthetic */ void m177x2ffb321b(String str) {
        if (this._isBatchProcessing) {
            return;
        }
        this._isBatchProcessing = true;
        ArrayList<D_SendCommand> arrayList = new ArrayList<>();
        Iterator<D_HistoryInfo> it = getPjList().iterator();
        while (it.hasNext()) {
            D_HistoryInfo next = it.next();
            String read = PrefUtils.read(getContext(), RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(next.macAddr));
            if (read == null) {
                read = "";
            }
            arrayList.add(new D_SendCommand(read, next));
        }
        this._commandSender.send(str, this._activity, Pj.getIns(), arrayList, true, this);
    }
}
