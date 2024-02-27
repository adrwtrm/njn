package com.epson.iprojection.ui.activities.web.menu.bookmark;

import android.app.Activity;
import android.widget.BaseAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.web.menu.bookmark.EditBookmarkDialog;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.editableList.BaseEditableList;
import com.epson.iprojection.ui.common.editableList.D_CustomDialog;
import com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener;
import com.epson.iprojection.ui.common.editableList.IOnSelected;
import com.epson.iprojection.ui.common.editableList.SaveData;
import com.epson.iprojection.ui.common.exception.FullException;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Bookmark extends BaseEditableList implements IOnDialogItemClickListener, IOnClickDialogOkListener {
    private static final int COMPARE_ID = 1;
    private static final int ID_DELETE = 1;
    private static final int ID_EDIT = 0;
    private static final int MAX_NUM = 30;
    private static final int TITLE_ID = 0;
    public static final int URL_ID = 1;
    private static final boolean _isEditable = true;
    private static final String[] _tagList = {PrefTagDefine.WEB_BOOKMARK_TITLE_TAG, PrefTagDefine.WEB_BOOKMARK_URL_TAG};
    private Activity _activity = null;
    private final IOnDataChangedListener _impl;

    public Bookmark(IOnDataChangedListener iOnDataChangedListener) {
        this._impl = iOnDataChangedListener;
    }

    public Bookmark initialize(Activity activity, IOnSelected iOnSelected, boolean z) {
        String string = activity.getString(R.string._EditBookmark_);
        CharSequence[] charSequenceArr = {activity.getString(R.string._Edit_), activity.getString(R.string._Delete_)};
        this._activity = activity;
        super.initialize(activity, z, iOnSelected, 30, _tagList, R.id.list_web_bkmk_iproid_bkmk, true, 1, new D_CustomDialog(activity, string, charSequenceArr, this));
        return this;
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public BaseAdapter getListAdapter() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 30 && this._savedDataList.get(i, 0) != null; i++) {
            arrayList.add(new DtoInflater(R.drawable.bookmark_petit, this._savedDataList.get(i, 0)));
        }
        return new InflaterListAdapter(this._activity, arrayList);
    }

    @Override // com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener
    public void onClickDialogItem(int i, int i2) {
        if (i2 == 0) {
            SaveData read = super.read(i);
            new EditBookmarkDialog(this._activity, read.get(0), read.get(1), this, EditBookmarkDialog.Mode.Edit);
        } else if (i2 == 1) {
            delete();
        } else {
            Lg.e("対応するcaseがありません");
        }
    }

    @Override // com.epson.iprojection.ui.activities.web.menu.bookmark.IOnClickDialogOkListener
    public void onClickDialogOk() {
        IOnDataChangedListener iOnDataChangedListener = this._impl;
        if (iOnDataChangedListener == null) {
            Lg.e("リスナーがnull");
        } else {
            iOnDataChangedListener.onDataChanged();
        }
        ToastMgr.getIns().show(this._activity, ToastMgr.Type.RegisteredBookmark);
    }

    public void add(String str, String str2) throws FullException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        arrayList.add(str2);
        super.add(arrayList);
    }

    public void modifyTitle(String str, int i) {
        super.overwrite(str, i, 0);
    }

    public String readTitle(int i) {
        return super.read(i).get(0);
    }

    public String readUrl(int i) {
        return super.read(i).get(1);
    }

    public boolean isAddable() {
        return size() < 30;
    }
}
