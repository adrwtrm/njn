package com.epson.iprojection.ui.activities.web.menu.history;

import android.app.Activity;
import android.widget.BaseAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.editableList.BaseEditableList;
import com.epson.iprojection.ui.common.editableList.D_CustomDialog;
import com.epson.iprojection.ui.common.editableList.FactoryListAdapter;
import com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener;
import com.epson.iprojection.ui.common.editableList.IOnSelected;
import com.epson.iprojection.ui.common.exception.FullException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class WebHistory extends BaseEditableList implements IOnDialogItemClickListener {
    private static final int COMPARE_ID = 1;
    private static final int ID_DELETE = 0;
    private static final int ID_DELETE_ALL = 1;
    private static final int MAX_NUM = 30;
    private static final boolean _isEditable = true;
    private static final String[] _tagList = {PrefTagDefine.WEB_HISTORY_TITLE_TAG, PrefTagDefine.WEB_HISTORY_URL_TAG};

    public WebHistory initialize(Activity activity, IOnSelected iOnSelected, boolean z) {
        super.initialize(activity, z, iOnSelected, 30, _tagList, R.id.list_Web_History, true, 1, new D_CustomDialog(activity, activity.getString(R.string._Edit_), new CharSequence[]{activity.getString(R.string._Delete_), activity.getString(R.string._DeleteAll_)}, this));
        return this;
    }

    public void insertFront(String str, String str2) throws FullException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        arrayList.add(str2);
        super.insertFront(arrayList);
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public BaseAdapter getListAdapter() {
        return FactoryListAdapter.oneLine(this._activity, 30, this._savedDataList, 0);
    }

    @Override // com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener
    public void onClickDialogItem(int i, int i2) {
        if (i2 == 0) {
            delete();
        } else if (i2 == 1) {
            deleteAll();
        } else {
            Lg.e("対応するcaseがありません");
        }
    }
}
