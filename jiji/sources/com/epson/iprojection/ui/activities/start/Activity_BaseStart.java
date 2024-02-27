package com.epson.iprojection.ui.activities.start;

import android.content.Intent;
import android.os.Bundle;
import com.epson.iprojection.ui.activities.pjselect.nfc.NfcDataManager;
import com.epson.iprojection.ui.common.activity.base.IproBaseActivity;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.singleton.AppStartActivityManager;

/* loaded from: classes.dex */
public abstract class Activity_BaseStart extends IproBaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AppStartActivityManager.getIns().clearStartActivity();
        NfcDataManager.getIns().clearBinaryData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startNextActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(67108864);
        intent.putExtra(IntentTagDefine.ROOT_TAG, "empty message");
        startActivity(intent);
    }
}
