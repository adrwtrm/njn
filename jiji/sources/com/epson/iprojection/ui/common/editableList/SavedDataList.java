package com.epson.iprojection.ui.common.editableList;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.exception.FullException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SavedDataList {
    private final int _compareN;
    private final Context _context;
    private final SharedPreferences.Editor _editor;
    private final int _maxNum;
    private final ArrayList<SaveData> _dataList = new ArrayList<>();
    private final ArrayList<String> _tagList = new ArrayList<>();

    public SavedDataList(Context context, String[] strArr, int i, int i2) {
        String read;
        this._context = context;
        this._compareN = i2;
        this._editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        for (String str : strArr) {
            this._tagList.add(str);
        }
        this._maxNum = i;
        for (int i3 = 0; i3 < this._maxNum; i3++) {
            ArrayList arrayList = null;
            int i4 = 0;
            while (i4 < this._tagList.size() && (read = PrefUtils.read(context, this._tagList.get(i4), i3)) != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(read);
                i4++;
            }
            if (arrayList == null) {
                return;
            }
            if (i4 != this._tagList.size()) {
                Lg.e("load error");
            }
            this._dataList.add(new SaveData(arrayList));
        }
    }

    public final SaveData get(int i) {
        if (i >= this._dataList.size()) {
            return null;
        }
        return this._dataList.get(i);
    }

    public final String get(int i, int i2) {
        if (i >= this._dataList.size()) {
            return null;
        }
        return this._dataList.get(i).get(i2);
    }

    public int getDataID(String str, int i) {
        String read;
        for (int i2 = 0; i2 < this._maxNum && (read = PrefUtils.read(this._context, this._tagList.get(i), i2)) != null; i2++) {
            if (str.compareTo(read) == 0) {
                return i2;
            }
        }
        return -1;
    }

    public void insertFront(ArrayList<String> arrayList) {
        String read;
        int i = 0;
        while (true) {
            if (i >= this._maxNum || (read = PrefUtils.read(this._context, this._tagList.get(this._compareN), i)) == null) {
                break;
            } else if (read.compareTo(arrayList.get(this._compareN)) == 0) {
                delete(i);
                break;
            } else {
                i++;
            }
        }
        insert(arrayList, 0);
    }

    public void insert(ArrayList<String> arrayList, int i) {
        for (int i2 = 0; i2 < this._tagList.size(); i2++) {
            PrefUtils.insert(this._context, this._tagList.get(i2), arrayList.get(i2), i);
        }
        int size = PrefUtils.getSize(this._context, this._tagList.get(0));
        int i3 = this._maxNum;
        if (size >= i3) {
            delete(i3);
        }
    }

    public void set(ArrayList<String> arrayList, int i) {
        for (int i2 = 0; i2 < this._tagList.size(); i2++) {
            PrefUtils.write(this._context, this._tagList.get(i2), arrayList.get(i2), i, null);
        }
    }

    public void add(ArrayList<String> arrayList) throws FullException {
        String read;
        int i = 0;
        while (i < this._maxNum && (read = PrefUtils.read(this._context, this._tagList.get(this._compareN), i)) != null) {
            if (arrayList.get(this._compareN).compareTo(read) == 0) {
                set(arrayList, i);
                return;
            }
            i++;
        }
        if (i >= this._maxNum) {
            throw new FullException("full bookmark");
        }
        for (int i2 = 0; i2 < this._tagList.size(); i2++) {
            PrefUtils.write(this._context, this._tagList.get(i2), arrayList.get(i2), i, null);
        }
    }

    public void add(ArrayList<String> arrayList, int i, boolean z) throws FullException {
        if (i >= this._maxNum) {
            throw new FullException("full bookmark");
        }
        for (int i2 = 0; i2 < this._tagList.size(); i2++) {
            PrefUtils.write(this._context, this._tagList.get(i2), arrayList.get(i2), i, this._editor, z);
        }
    }

    public void delete(int i) {
        for (int i2 = 0; i2 < this._tagList.size(); i2++) {
            PrefUtils.delete(this._context, this._tagList.get(i2), i);
        }
    }

    public void deleteAll() {
        for (int i = 0; i < this._tagList.size(); i++) {
            PrefUtils.deleteAll(this._context, this._tagList.get(i));
        }
    }

    public void overwrite(String str, int i, int i2) {
        PrefUtils.write(this._context, this._tagList.get(i2), str, i, null);
    }

    public int size() {
        return PrefUtils.getSize(this._context, this._tagList.get(0));
    }
}
