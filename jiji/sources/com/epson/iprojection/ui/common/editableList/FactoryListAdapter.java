package com.epson.iprojection.ui.common.editableList;

import android.app.Activity;
import android.widget.ArrayAdapter;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public final class FactoryListAdapter {
    public static ArrayAdapter<String> oneLine(Activity activity, int i, SavedDataList savedDataList, int i2) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.list_customitem);
        for (int i3 = 0; i3 < i; i3++) {
            String str = savedDataList.get(i3, i2);
            if (str == null) {
                break;
            }
            arrayAdapter.add(str);
        }
        return arrayAdapter;
    }

    private FactoryListAdapter() {
    }
}
