package com.epson.iprojection.ui.activities.pjselect.connected;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.activities.pjselect.common.BasePanelButtons;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;

/* loaded from: classes.dex */
public class PanelButtons extends BasePanelButtons implements CompoundButton.OnCheckedChangeListener {
    public static final int MIRRORING_SWITCH_UNAVAILABLE_TIME = 500;
    private boolean _isMirroringSwitchClickable;
    private final Switch _mirroringSwitch;

    public PanelButtons(Activity activity, View view, DrawerList drawerList) {
        super(activity, view, drawerList);
        this._isMirroringSwitchClickable = true;
        Switch r0 = (Switch) view.findViewById(R.id.switch_mirroring);
        this._mirroringSwitch = r0;
        if (r0 != null) {
            r0.setOnCheckedChangeListener(this);
            if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                r0.setChecked(true);
            }
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.img_mirroring);
        if (imageView == null || !ChromeOSUtils.INSTANCE.isChromeOS(activity)) {
            return;
        }
        imageView.setImageResource(R.drawable.home_mirroring_chromebook);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnRemote) {
            if (this._drawerList.askToConnect(2)) {
                return;
            }
            this._drawerList.callNextActivity(2);
        } else if (view == this._btnPhoto) {
            this._drawerList.startNextActivity(eContentsType.Photo);
        } else if (view == this._btnPdf) {
            this._drawerList.startNextActivity(eContentsType.Pdf);
        } else if (view == this._btnWeb) {
            this._drawerList.startNextActivity(eContentsType.Web);
        } else if (view == this._btnCamera) {
            this._drawerList.startNextActivity(eContentsType.Camera);
        } else if (view == this._btnMirroring) {
            if (this._isMirroringSwitchClickable) {
                Switch r2 = this._mirroringSwitch;
                r2.setChecked(!r2.isChecked());
            }
        } else if (view == this._btnMultiprojection) {
            this._drawerList.callNextActivity(3);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.switch_mirroring) {
            if (z) {
                if (!MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                    this._drawerList.callNextActivity(6);
                }
            } else {
                MirroringEntrance.INSTANCE.finish(this._activity);
            }
            beUnclickableForAWhile(compoundButton);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.common.BasePanelButtons
    public void update(View view) {
        super.update(view);
        Switch r2 = this._mirroringSwitch;
        if (r2 != null) {
            r2.setChecked(MirroringEntrance.INSTANCE.isMirroringSwitchOn());
        }
    }

    private void beUnclickableForAWhile(final View view) {
        view.setClickable(false);
        this._isMirroringSwitchClickable = false;
        new Handler().postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.connected.PanelButtons$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PanelButtons.this.m133x5604eea2(view);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$beUnclickableForAWhile$0$com-epson-iprojection-ui-activities-pjselect-connected-PanelButtons  reason: not valid java name */
    public /* synthetic */ void m133x5604eea2(View view) {
        view.setClickable(true);
        this._isMirroringSwitchClickable = true;
    }
}
