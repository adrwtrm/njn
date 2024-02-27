package com.epson.iprojection.ui.activities.pjselect.layouts;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.IProjListListener;
import com.epson.iprojection.ui.activities.pjselect.ProjList_profile;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.pjselect.profile.Activity_Profile;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;

/* loaded from: classes.dex */
public class Profile_layout extends RemovableView implements IProjListListener, AdapterView.OnItemLongClickListener, IPjManualSearchResultListener, IOnDialogEventListener {
    private TextView _filterEditText;
    private ProjList_profile _profileList;
    private findResult _result;
    private final Activity_Profile parent;

    /* loaded from: classes.dex */
    public enum findResult {
        NOT_FOUND,
        SOME_FOUND,
        ALL_FOUND
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile() {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile(XmlUtils.mplistType mplisttype) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public boolean onClickProjectorDetail(D_PjInfo d_PjInfo) {
        return false;
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void updateList(boolean z) {
    }

    public Profile_layout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.parent = (Activity_Profile) getContext();
        this._filterEditText = null;
        this._profileList = null;
        this._result = findResult.NOT_FOUND;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this._profileList = new ProjList_profile(this.parent, (ListView) findViewById(R.id.list_Pj_Profile), this, this.parent.getSelectedProfileType());
        TextView textView = (TextView) findViewById(R.id.listFilet_text);
        this._filterEditText = textView;
        textView.setVisibility(8);
        this._filterEditText.addTextChangedListener(new TextWatcher() { // from class: com.epson.iprojection.ui.activities.pjselect.layouts.Profile_layout.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Profile_layout.this._profileList.getAdapter() == null || charSequence == null) {
                    return;
                }
                Profile_layout.this._profileList.getAdapter().getFilter().filter(charSequence.toString());
            }
        });
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView
    public void resumeView() {
        ((TextView) this.parent.findViewById(R.id.txt_titlebar_filename)).setText(getCurrentProfileTitle());
        if (!Pj.getIns().isAvailablePjFinder() || !isProfileAvailable()) {
            this.parent.backToTopView();
            return;
        }
        ProjList_profile projList_profile = this._profileList;
        if (projList_profile != null) {
            projList_profile.redrawList(true);
        }
        this.parent.disableDrawerList();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView
    public boolean pauseView(boolean z) {
        int parentDepth = this._profileList.getParentDepth();
        if (parentDepth > -1 && z) {
            this._profileList.clear();
            this._profileList.setCurrentDepth(parentDepth);
            this._filterEditText.setText(String.valueOf(parentDepth));
            return false;
        }
        Pj.getIns().cancelSearch();
        Pj.getIns().disableManualSearchResultListener();
        return true;
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        ListView listView = (ListView) findViewById(R.id.list_Pj_Profile);
        if (listView != null) {
            listView.setSelection(0);
        }
    }

    private boolean isProfileAvailable() {
        XmlUtils.mplistType existProfileTypes = XmlUtils.existProfileTypes();
        XmlUtils.mplistType selectedProfileType = this.parent.getSelectedProfileType();
        if (XmlUtils.mplistType.LOST == existProfileTypes) {
            return false;
        }
        return XmlUtils.mplistType.BOTH == existProfileTypes || existProfileTypes == selectedProfileType;
    }

    private String getCurrentProfileTitle() {
        int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType[this.parent.getSelectedProfileType().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return this.parent.getString(R.string._SharedProfile_);
            }
            return this.parent.getString(R.string._Profile_);
        }
        return this.parent.getString(R.string._LocalProfile_);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickItem(D_PjInfo d_PjInfo, View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_list);
        if (!checkBox.isChecked()) {
            if (!Pj.getIns().addManualSearchPj(this, d_PjInfo)) {
                return;
            }
        } else {
            Pj.getIns().deleteManualSearchPj(d_PjInfo);
        }
        checkBox.setChecked(!checkBox.isChecked());
        this.parent.onPjStatusChanged();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickFolder(int i) {
        this._profileList.clear();
        this._profileList.setCurrentDepth(i);
        this._filterEditText.setText(String.valueOf(i));
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        if (z) {
            Lg.d("指定検索結果：全て見つかりました！");
            this._result = findResult.ALL_FOUND;
        } else {
            Lg.d("指定検索結果：1つ見つかりました！");
            this._result = findResult.SOME_FOUND;
        }
        Analytics.getIns().setConnectEvent(eSearchRouteDimension.profile, d_PjInfo.UniqInfo, null, null);
        Analytics.getIns().setRegisteredEvent(eRegisteredDimension.profile);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        Lg.d("指定検索：終わりました");
        Pj.getIns().endManualSearch();
        if (this.parent != null) {
            int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$Profile_layout$findResult[this._result.ordinal()];
            if (i == 1) {
                this.parent.backToTopView();
            } else if (i == 2) {
                Pj.getIns().showMsgDialog(MessageDialog.MessageType.NotFoundAnything, this, BaseDialog.ResultAction.GOTOTOP);
            } else if (i == 3) {
                Pj.getIns().showMsgDialog(MessageDialog.MessageType.NotFound, this, BaseDialog.ResultAction.NOACTION);
            } else {
                Lg.e("Invalid search result.");
            }
            this._result = findResult.NOT_FOUND;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.layouts.Profile_layout$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$Profile_layout$findResult;

        static {
            int[] iArr = new int[findResult.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$Profile_layout$findResult = iArr;
            try {
                iArr[findResult.ALL_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$Profile_layout$findResult[findResult.SOME_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$Profile_layout$findResult[findResult.NOT_FOUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[XmlUtils.mplistType.values().length];
            $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType = iArr2;
            try {
                iArr2[XmlUtils.mplistType.LOCAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType[XmlUtils.mplistType.SHARED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
        if (resultAction == BaseDialog.ResultAction.GOTOTOP) {
            this.parent.backToTopView();
            return;
        }
        Pj.getIns().clearManualSearchPj();
        ProjList_profile projList_profile = this._profileList;
        if (projList_profile != null) {
            projList_profile.redrawList(false);
        }
        this.parent.disableBarButton();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
        Pj.getIns().clearDialog();
    }
}
