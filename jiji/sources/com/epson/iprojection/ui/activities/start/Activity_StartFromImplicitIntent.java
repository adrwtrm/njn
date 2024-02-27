package com.epson.iprojection.ui.activities.start;

import android.content.Intent;
import android.os.Bundle;
import com.epson.iprojection.ui.activities.terms.Activity_TermsToMain;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.singleton.AppStartActivityManager;
import com.epson.iprojection.ui.common.singleton.ProhibitFinalizingOnDestroyFlag;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_StartFromImplicitIntent extends Activity_BaseStart {
    public static final String TAG_IMPLICIT = "tag_implicit";

    @Override // com.epson.iprojection.ui.activities.start.Activity_BaseStart, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startFromImplicitIntent(getIntent());
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.start.Activity_BaseStart
    public void startNextActivity(Class cls) {
        ProhibitFinalizingOnDestroyFlag.INSTANCE.enable();
        Intent intent = new Intent(this, cls);
        intent.setFlags(872415232);
        intent.putExtra(IntentTagDefine.ROOT_TAG, "empty message");
        startActivity(intent);
    }

    private void startFromImplicitIntent(Intent intent) {
        init();
        AppStartActivityManager.getIns().setStartActivity(this, intent);
        startNextActivity(Activity_TermsToMain.class);
    }

    private void init() {
        if (Pj.getIns().isAlreadyInited()) {
            return;
        }
        Initializer.initialize(this);
        Pj.getIns().initialize(this);
    }
}
