package com.epson.iprojection.ui.activities.start;

import android.os.Bundle;
import com.epson.iprojection.ui.activities.splash.Activity_Splash;

/* loaded from: classes.dex */
public class Activity_StartSplash extends Activity_BaseStart {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.start.Activity_BaseStart, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isTaskRoot()) {
            startNextActivity(Activity_Splash.class);
        }
        finish();
    }
}
