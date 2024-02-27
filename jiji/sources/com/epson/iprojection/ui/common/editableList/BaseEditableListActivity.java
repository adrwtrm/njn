package com.epson.iprojection.ui.common.editableList;

import android.content.Intent;
import android.os.Bundle;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;

/* loaded from: classes.dex */
public abstract class BaseEditableListActivity extends PjConnectableActivity implements IOnSelected {
    public abstract String getSendString(SaveData saveData);

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.epson.iprojection.ui.common.editableList.IOnSelected
    public void onSelected(SaveData saveData) {
        if (saveData == null) {
            return;
        }
        Intent intent = new Intent();
        String sendString = getSendString(saveData);
        if (sendString == null) {
            return;
        }
        intent.putExtra(IntentTagDefine.RESULT_TAG, sendString);
        setResult(-1, intent);
        finish();
    }
}
