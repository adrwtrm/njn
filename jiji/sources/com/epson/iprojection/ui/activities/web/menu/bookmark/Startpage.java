package com.epson.iprojection.ui.activities.web.menu.bookmark;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.editableList.BaseEditableList;
import com.epson.iprojection.ui.common.editableList.D_CustomDialog;
import com.epson.iprojection.ui.common.editableList.FactoryListAdapter;
import com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener;
import com.epson.iprojection.ui.common.editableList.IOnSelected;
import com.epson.iprojection.ui.common.editableList.SaveData;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Startpage extends BaseEditableList implements IOnDialogItemClickListener {
    private static final int COMPARE_ID = 1;
    private static final int ID_DELETE = 0;
    private static final int MAX_NUM = 1;
    public static final int TITLE_ID = 0;
    public static final int URL_ID = 1;
    private static final boolean _isEditable = true;
    private static final String[] _tagList = {PrefTagDefine.WEB_STARTPAGE_TITLE_TAG, PrefTagDefine.WEB_STARTPAGE_URL_TAG};

    public Startpage initialize(Activity activity, IOnSelected iOnSelected, boolean z) {
        super.initialize(activity, z, iOnSelected, 1, _tagList, R.id.list_web_bkmk_iproid_startpage, true, 1, new D_CustomDialog(activity, activity.getString(R.string._Edit_), new CharSequence[]{activity.getString(R.string._Delete_)}, this));
        return this;
    }

    public void set(String str, String str2) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        arrayList.add(str2);
        super.insertFront(arrayList);
    }

    public String getUrl() {
        SaveData read = read(0);
        if (read == null) {
            return null;
        }
        return read.get(1);
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public BaseAdapter getListAdapter() {
        ArrayAdapter<String> oneLine = FactoryListAdapter.oneLine(this._activity, 1, this._savedDataList, 0);
        if (oneLine.getCount() == 0) {
            oneLine.add("");
        }
        return oneLine;
    }

    @Override // com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener
    public void onClickDialogItem(int i, int i2) {
        if (i2 == 0) {
            delete();
        } else {
            Lg.e("対応するcaseがありません");
        }
    }
}
