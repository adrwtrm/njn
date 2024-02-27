package com.epson.iprojection.ui.activities.support.intro.moderator;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.PagerAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_IntroModerator extends BaseIntroActivity {
    protected static final int[] IMG_ID = {R.id.img_proc00, R.id.img_proc01};
    private CustomOkCancelDialog _dialog;
    private boolean _isCalledFromMarker = false;
    protected final Intent _intent = new Intent();

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Bundle extras;
        super.onCreate(bundle);
        super.initialize(R.layout.main_intro_moderator, IMG_ID);
        Intent intent = getIntent();
        if (intent != null && (extras = intent.getExtras()) != null && extras.getString(Activity_Marker.IntentTagMarker) != null) {
            this._isCalledFromMarker = true;
        }
        this._intent.putExtra(Activity_Marker.IntentTagRefresh, false);
        setResult(-1, this._intent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Lg.d("onBackPressed");
        setResult(-1, this._intent);
        super.onBackPressed();
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity
    protected PagerAdapter createPageAdapter(Activity activity) {
        return new CustomPagerAdapter(this);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDeliverImage(final String str, final D_DeliveryPermission d_DeliveryPermission) {
        if (Pj.getIns().isModerator()) {
            return;
        }
        if (!this._isCalledFromMarker) {
            super.onDeliverImage(str, d_DeliveryPermission);
        } else if (PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1) {
            this._dialog = new CustomOkCancelDialog(this, getString(R.string._AutoDisplayReceivedImageWantOpen_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator$$ExternalSyntheticLambda0
                {
                    Activity_IntroModerator.this = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Activity_IntroModerator.this.m181xe7a346ae(str, d_DeliveryPermission, dialogInterface, i);
                }
            });
        }
    }

    /* renamed from: lambda$onDeliverImage$0$com-epson-iprojection-ui-activities-support-intro-moderator-Activity_IntroModerator */
    public /* synthetic */ void m181xe7a346ae(String str, D_DeliveryPermission d_DeliveryPermission, DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this._intent.putExtra(Activity_Marker.IntentTagRefresh, true);
            startDeliveryActivity(str, d_DeliveryPermission);
        } else if (i == -2) {
            this._intent.putExtra(Activity_Marker.IntentTagRefresh, false);
        }
        super.updateScreenLockView();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeScreenLockStatus(boolean z) {
        CustomOkCancelDialog customOkCancelDialog = this._dialog;
        if (customOkCancelDialog == null || !customOkCancelDialog.isShowing()) {
            super.onChangeScreenLockStatus(z);
        }
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        setResult(-1, this._intent);
        super.onClick(view);
    }

    public boolean isCalledFromMarker() {
        return this._isCalledFromMarker;
    }

    public void cancelClose() {
        setResult(0, this._intent);
        finish();
    }
}
