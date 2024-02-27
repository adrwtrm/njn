package com.epson.iprojection.ui.activities.support.intro.wifi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.PagerAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.LinkageDataConnector;
import com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity;
import com.epson.iprojection.ui.common.uiparts.OKDialog;

/* loaded from: classes.dex */
public class Activity_IntroWifi extends BaseIntroActivity {
    protected static final int[] IMG_ID = {R.id.img_proc00};
    public static final String INTENT_TAG_DIALOG_STRING = "INTENT_TAG_DIALOG_STRING";

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.initialize(R.layout.main_intro_wifi, IMG_ID);
        Intent intent = getIntent();
        boolean booleanExtra = intent.getBooleanExtra(LinkageDataConnector.FAIL_TO_CHANGE_WIFIPROFILE, false);
        String stringExtra = intent.getStringExtra(LinkageDataConnector.TRYING_CONNECT_SSID);
        if (booleanExtra) {
            new OKDialog(this, String.format(getString(R.string._FailToSwitchWifi_), stringExtra, stringExtra));
        }
        String stringExtra2 = intent.getStringExtra(INTENT_TAG_DIALOG_STRING);
        if (stringExtra2 == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        new OKDialog(this, stringExtra2);
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity
    protected PagerAdapter createPageAdapter(Activity activity) {
        return new CustomPagerAdapter(this);
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.equals(this._btnOK)) {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            intent.setFlags(268435456);
            if (getPackageManager().resolveActivity(intent, 0) != null) {
                startActivity(intent);
            }
        }
        super.finish();
    }
}
