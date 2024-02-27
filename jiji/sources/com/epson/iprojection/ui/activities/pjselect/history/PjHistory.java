package com.epson.iprojection.ui.activities.pjselect.history;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.editableList.BaseEditableList;
import com.epson.iprojection.ui.common.editableList.D_CustomDialog;
import com.epson.iprojection.ui.common.editableList.IOnDialogItemClickListener;
import com.epson.iprojection.ui.common.editableList.IOnSelected;
import com.epson.iprojection.ui.common.editableList.SaveData;
import com.epson.iprojection.ui.common.exception.FullException;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/* loaded from: classes.dex */
public class PjHistory extends BaseEditableList implements AdapterView.OnItemClickListener, IOnDialogItemClickListener {
    private static final int COMPARE_ID = 0;
    private static final int ID_DELETE = 0;
    private static final int ID_DELETE_ALL = 1;
    private static final int MAX_NUM = 20;
    private static final boolean _isEditable = true;
    private static final String[] _tagList = {PrefTagDefine.ConPJ_HISTORY_PJNAME_TAG, PrefTagDefine.ConPJ_HISTORY_IPADDRESS_TAG, PrefTagDefine.ConPJ_HISTORY_TIME_TAG};
    private IOnSelected _impl;
    private final IUpdatableActionbar _implActionbarUpdater;
    private boolean _isVisibility;

    public PjHistory() {
        this(null);
    }

    public PjHistory(IUpdatableActionbar iUpdatableActionbar) {
        this._impl = null;
        this._isVisibility = false;
        this._implActionbarUpdater = iUpdatableActionbar;
    }

    public PjHistory initialize(Activity activity, IOnSelected iOnSelected, boolean z) {
        String string = activity.getString(R.string._Edit_);
        CharSequence[] charSequenceArr = {activity.getString(R.string._Delete_), activity.getString(R.string._DeleteAll_)};
        this._isVisibility = z;
        this._impl = iOnSelected;
        super.initialize(activity, z, iOnSelected, 20, _tagList, R.id.list_Pj_History, true, 0, new D_CustomDialog(activity, string, charSequenceArr, this));
        return this;
    }

    public void updateConnectedHistory(Activity activity) {
        Calendar calendar = Calendar.getInstance();
        String format = String.format("%02d/%02d/%02d %02d:%02d", Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)));
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList != null) {
            int size = nowConnectingPJList.size();
            for (int i = 0; i < size; i++) {
                try {
                    insertFront(nowConnectingPJList.get(i).getPjInfo().PrjName, NetUtils.cvtIPAddr(nowConnectingPJList.get(i).getPjInfo().IPAddr), format);
                } catch (FullException unused) {
                    Lg.d("履歴が上限に達しました。");
                    return;
                }
            }
            return;
        }
        Lg.e("Connecting projector list is empty.");
    }

    public void insertFront(String str, String str2, String str3) throws FullException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        super.insertFront(arrayList);
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public BaseAdapter getListAdapter() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 20 && this._savedDataList.get(i, 0) != null; i++) {
            arrayList.add(new DtoInflater(this._savedDataList.get(i, 0), this._savedDataList.get(i, 1), this._savedDataList.get(i, 2)));
        }
        return new InflaterListAdapter(this._activity, arrayList);
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList, android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        IOnSelected iOnSelected = this._impl;
        if (iOnSelected == null) {
            Lg.d("非表示にも関わらずボタンイベントが呼ばれた");
        } else {
            iOnSelected.onSelected(this._savedDataList.get((int) j), view, i);
        }
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

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public void delete() {
        SaveData saveData = this._savedDataList.get((int) this._selectedN);
        Pj ins = Pj.getIns();
        Objects.requireNonNull(saveData);
        String str = saveData.get(1);
        Objects.requireNonNull(saveData);
        D_PjInfo pjInfoByIpAndName = ins.getPjInfoByIpAndName(str, saveData.get(0));
        if (pjInfoByIpAndName == null) {
            pjInfoByIpAndName = new D_PjInfo();
            Objects.requireNonNull(saveData);
            pjInfoByIpAndName.IPAddr = NetUtils.convertIpStringToBytes(saveData.get(1));
            Objects.requireNonNull(saveData);
            pjInfoByIpAndName.PrjName = saveData.get(0);
        }
        ConnectPjInfo connectPjInfo = new ConnectPjInfo(pjInfoByIpAndName, new ConnectConfig(this._activity).getInterruptSetting());
        connectPjInfo.setPjInfo(pjInfoByIpAndName);
        Pj.getIns().removeConnPjFromHistory(connectPjInfo);
        IUpdatableActionbar iUpdatableActionbar = this._implActionbarUpdater;
        if (iUpdatableActionbar != null) {
            iUpdatableActionbar.updateActionbar();
        }
        super.delete();
        update();
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableList
    public void deleteAll() {
        Pj.getIns().clearConnPjFromHistory();
        IUpdatableActionbar iUpdatableActionbar = this._implActionbarUpdater;
        if (iUpdatableActionbar != null) {
            iUpdatableActionbar.updateActionbar();
        }
        super.deleteAll();
        update();
    }

    public void setvisivility(boolean z) {
        this._isVisibility = z;
    }

    public void update() {
        updateAdapter();
    }
}
