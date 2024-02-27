package com.epson.iprojection.ui.activities.support;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public class Activity_AppVersion extends PjConnectableActivity implements View.OnClickListener {
    private int _clickCounter;

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.main_app_version);
        findViewById(R.id.txt_copyright).setOnClickListener(this);
        try {
            ((TextView) findViewById(R.id.version)).setText(getString(R.string._Version_) + getPackageManager().getPackageInfo(getPackageName(), 128).versionName);
            ((TextView) findViewById(R.id.txt_copyright)).setText(String.format(getString(R.string._Copyright_), getString(R.string.release_year)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (!isFinishing() || ChromeOSUtils.isChromeOS()) {
            return;
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int i = this._clickCounter + 1;
        this._clickCounter = i;
        if (i >= 10) {
            startActivity(new Intent(this, DebugSettingActivity.class));
        }
    }
}
