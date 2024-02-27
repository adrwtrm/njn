package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DisplayInfoUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FragmentPjControlOld extends FragmentPjControlTouchPad implements View.OnClickListener {
    private static final double HI = 0.676470588235294d;
    private static final int ID_BTN_COM = 2131230887;
    private static final int ID_BTN_FREEZE = 2131230882;
    private static final int ID_BTN_LAN = 2131230883;
    private static final int ID_BTN_MUTE = 2131230884;
    private static final int ID_BTN_POWER = 2131230888;
    private static final int ID_BTN_QR = 2131230891;
    private static final int ID_BTN_VIDEO = 2131230892;
    private static final int ID_BTN_VOLDOWN = 2131230893;
    private static final int ID_BTN_VOLUP = 2131230894;
    private static final String KEY_PJ_INFO = "key_pj_info";
    private final ArrayList<ImageButton> _btnList = new ArrayList<>();

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    protected int getLayoutId() {
        return R.layout.main_conpj_pjcontrol_old;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public boolean isAuthenticated() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Size {
        public int h;
        public int w;

        public Size(int i, int i2) {
            this.w = i;
            this.h = i2;
        }
    }

    public static FragmentPjControlOld newInstance(D_HistoryInfo d_HistoryInfo) {
        FragmentPjControlOld fragmentPjControlOld = new FragmentPjControlOld();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PJ_INFO, d_HistoryInfo);
        fragmentPjControlOld.setArguments(bundle);
        return fragmentPjControlOld;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public D_HistoryInfo getPjInfo() {
        return (D_HistoryInfo) getArguments().getSerializable(KEY_PJ_INFO);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlTouchPad, com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        initialize(view);
        super.onViewCreated(view, bundle);
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
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_power));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_pc));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_video));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_lan));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_mute));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_freeze));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_volumeplus));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_volumeminus));
        this._btnList.add((ImageButton) view.findViewById(R.id.btn_pjctl_qr));
        Iterator<ImageButton> it = this._btnList.iterator();
        while (it.hasNext()) {
            it.next().setOnClickListener(this);
        }
        setButtonLayoutParams();
    }

    private void setButtonLayoutParams() {
        Size buttonSize = getButtonSize();
        int margin = getMargin();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(buttonSize.w, buttonSize.h);
        layoutParams.setMargins(margin, margin, margin, margin);
        Iterator<ImageButton> it = this._btnList.iterator();
        while (it.hasNext()) {
            it.next().setLayoutParams(layoutParams);
        }
    }

    private void setStateButtons(boolean z) {
        Iterator<ImageButton> it = this._btnList.iterator();
        while (it.hasNext()) {
            ImageButton next = it.next();
            if (it.hasNext()) {
                next.setEnabled(z);
            } else if (isSupportQR()) {
                next.setEnabled(z);
            } else {
                next.setEnabled(false);
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlTouchPad, com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_pjctl_freeze /* 2131230882 */:
                onClickFreezeButton();
                return;
            case R.id.btn_pjctl_lan /* 2131230883 */:
                onClickLanButton();
                return;
            case R.id.btn_pjctl_mute /* 2131230884 */:
                onClickMuteButton();
                return;
            case R.id.btn_pjctl_mute_off /* 2131230885 */:
            case R.id.btn_pjctl_mute_on /* 2131230886 */:
            case R.id.btn_pjctl_power_off /* 2131230889 */:
            case R.id.btn_pjctl_power_on /* 2131230890 */:
            default:
                Lg.e("対応するボタンが見つかりませんでした");
                return;
            case R.id.btn_pjctl_pc /* 2131230887 */:
                onClickComputerButton();
                return;
            case R.id.btn_pjctl_power /* 2131230888 */:
                onClickPowerButton();
                return;
            case R.id.btn_pjctl_qr /* 2131230891 */:
                onClickDisplayQRCodeButton();
                return;
            case R.id.btn_pjctl_video /* 2131230892 */:
                onClickVideoButton();
                return;
            case R.id.btn_pjctl_volumeminus /* 2131230893 */:
                onClickVolumeDonwButton();
                return;
            case R.id.btn_pjctl_volumeplus /* 2131230894 */:
                onClickVolumeUpButton();
                return;
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setButtonLayoutParams();
    }

    private void onClickPowerButton() {
        Lg.d("パワーボタンが押されました");
        Pj.getIns().pjcontrol_power(getPjInfo().pjID);
    }

    private void onClickComputerButton() {
        Lg.d("コンピュータボタンが押されました");
        Pj.getIns().pjcontrol_changeSourceComputer(getPjInfo().pjID);
    }

    private void onClickVideoButton() {
        Lg.d("ビデオボタンが押されました");
        Pj.getIns().pjcontrol_changeSourceVideo(getPjInfo().pjID);
    }

    private void onClickLanButton() {
        Lg.d("LANボタンが押されました");
        Pj.getIns().pjcontrol_changeSourceLAN(getPjInfo().pjID);
    }

    private void onClickFreezeButton() {
        Lg.d("フリーズが押されました");
        Pj.getIns().pjcontrol_freeze(getPjInfo().pjID);
    }

    private void onClickMuteButton() {
        Lg.d("ミュートが押されました");
        Pj.getIns().pjcontrol_avMute(getPjInfo().pjID);
    }

    private void onClickVolumeUpButton() {
        Lg.d("ボリュームアップボタンが押されました");
        Pj.getIns().pjcontrol_volumeUp(getPjInfo().pjID);
    }

    private void onClickVolumeDonwButton() {
        Lg.d("ボリュームダウンボタンが押されました");
        Pj.getIns().pjcontrol_volumeDown(getPjInfo().pjID);
    }

    private void onClickDisplayQRCodeButton() {
        Lg.d("QRコード表示・非表示ボタンがが押されました");
        Pj.getIns().pjcontrol_displayQRCode(getPjInfo().pjID);
    }

    private Size getButtonSize() {
        int width = DisplayInfoUtils.getWidth();
        int i = (width - (width / 10)) / 4;
        return new Size(i, (int) (i * HI));
    }

    private int getMargin() {
        return ((DisplayInfoUtils.getWidth() / 10) / 4) / 2;
    }

    private boolean isSupportQR() {
        return getPjInfo().isSupportQR;
    }
}
