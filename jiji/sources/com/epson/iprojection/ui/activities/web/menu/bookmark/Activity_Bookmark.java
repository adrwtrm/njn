package com.epson.iprojection.ui.activities.web.menu.bookmark;

import android.os.Bundle;
import android.view.View;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.editableList.BaseEditableListActivity;
import com.epson.iprojection.ui.common.editableList.SaveData;

/* loaded from: classes.dex */
public class Activity_Bookmark extends BaseEditableListActivity implements IOnDataChangedListener {
    @Override // com.epson.iprojection.ui.common.editableList.IOnSelected
    public void onSelected(SaveData saveData, View view, int i) {
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableListActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.main_web_bkmk_iproid);
        createInstance();
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableListActivity
    public String getSendString(SaveData saveData) {
        return saveData.get(1);
    }

    @Override // com.epson.iprojection.ui.activities.web.menu.bookmark.IOnDataChangedListener
    public void onDataChanged() {
        createInstance();
    }

    public void createInstance() {
        new Startpage().initialize(this, this, true);
        new Bookmark(this).initialize(this, this, true);
    }
}
