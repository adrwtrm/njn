package com.epson.iprojection.ui.common.editableList;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.exception.FullException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class BaseEditableList implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private BaseAdapter _adapter;
    protected SavedDataList _savedDataList = null;
    protected Activity _activity = null;
    private int _maxNum = 0;
    private int _compareN = 0;
    private int _listId = 0;
    protected long _selectedN = 0;
    private boolean _isVisible = false;
    private boolean _isEditable = false;
    private String[] _tagList = null;
    private IOnSelected _onSelectedListener = null;
    private D_CustomDialog _dialogData = null;

    public abstract BaseAdapter getListAdapter();

    public BaseEditableList initialize(Activity activity, boolean z, IOnSelected iOnSelected, int i, String[] strArr, int i2, boolean z2, int i3, D_CustomDialog d_CustomDialog) {
        this._activity = activity;
        this._isVisible = z;
        this._onSelectedListener = iOnSelected;
        this._maxNum = i;
        this._tagList = strArr;
        this._listId = i2;
        this._isEditable = z2;
        this._compareN = i3;
        this._dialogData = d_CustomDialog;
        recreate();
        return this;
    }

    public int size() {
        return this._savedDataList.size();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SaveData read(int i) {
        return this._savedDataList.get(i);
    }

    public int getDataID(String str, int i) {
        return this._savedDataList.getDataID(str, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void add(ArrayList<String> arrayList) throws FullException {
        this._savedDataList.add(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void overwrite(String str, int i, int i2) {
        this._savedDataList.overwrite(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void insertFront(ArrayList<String> arrayList) {
        this._savedDataList.insertFront(arrayList);
    }

    protected void insert(ArrayList<String> arrayList, int i) {
        this._savedDataList.insert(arrayList, i);
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        Lg.d("Long Tap = " + j);
        this._selectedN = j;
        new CustomDialog(this._dialogData, (int) j).show();
        return true;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        IOnSelected iOnSelected = this._onSelectedListener;
        if (iOnSelected == null) {
            Lg.d("非表示にも関わらずボタンイベントが呼ばれた");
        } else {
            iOnSelected.onSelected(this._savedDataList.get((int) j));
        }
    }

    public void delete() {
        this._savedDataList.delete((int) this._selectedN);
        recreate();
    }

    public void deleteAll() {
        this._savedDataList.deleteAll();
        recreate();
    }

    public void recreate() {
        this._savedDataList = new SavedDataList(this._activity, this._tagList, this._maxNum, this._compareN);
        if (this._isVisible) {
            layout();
        }
    }

    private void layout() {
        this._adapter = getListAdapter();
        ListView listView = (ListView) this._activity.findViewById(this._listId);
        listView.setScrollingCacheEnabled(false);
        listView.setAdapter((ListAdapter) this._adapter);
        listView.setOnItemClickListener(this);
        listView.setScrollingCacheEnabled(false);
        if (this._isEditable) {
            listView.setOnItemLongClickListener(this);
        }
    }

    public void updateAdapter() {
        this._adapter.notifyDataSetChanged();
    }
}
