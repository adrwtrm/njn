package com.epson.iprojection.ui.activities.presen.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class SortingDialog {
    private final Activity _activity;
    RadioGroup _displayGroup;
    private final IOnClickDialogOkListener _impl;
    RadioGroup _sortGroup;

    public SortingDialog(Activity activity, final IOnClickDialogOkListener iOnClickDialogOkListener) {
        this._activity = activity;
        this._impl = iOnClickDialogOkListener;
        final View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_sort, (ViewGroup) null);
        this._displayGroup = (RadioGroup) inflate.findViewById(R.id.Group_Display);
        this._sortGroup = (RadioGroup) inflate.findViewById(R.id.Group_Sort);
        loadPrefData();
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, (int) R.style.CustomAlertDialog));
        builder.setTitle(activity.getString(R.string._Sort_));
        builder.setView(inflate);
        builder.setPositiveButton(activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.presen.menu.SortingDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SortingDialog.this.m162xbe734dcf(inflate, iOnClickDialogOkListener, dialogInterface, i);
            }
        });
        builder.setNegativeButton(activity.getString(R.string._Cancel_), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-presen-menu-SortingDialog  reason: not valid java name */
    public /* synthetic */ void m162xbe734dcf(View view, IOnClickDialogOkListener iOnClickDialogOkListener, DialogInterface dialogInterface, int i) {
        onClickOk(view, iOnClickDialogOkListener);
    }

    private void onClickOk(View view, IOnClickDialogOkListener iOnClickDialogOkListener) {
        int i = this._displayGroup.getCheckedRadioButtonId() == R.id.Sort_Ascend ? 0 : 1;
        int i2 = this._sortGroup.getCheckedRadioButtonId() != R.id.Sort_Name ? 1 : 0;
        PrefUtils.writeInt(this._activity, PrefTagDefine.PRESEN_DISPLAY_ORDER_TAG, i, (SharedPreferences.Editor) null);
        PrefUtils.writeInt(this._activity, PrefTagDefine.PRESEN_SORT_ORDER_TAG, i2, (SharedPreferences.Editor) null);
        this._impl.onClickDialogOk();
    }

    private void loadPrefData() {
        int readInt = PrefUtils.readInt(this._activity, PrefTagDefine.PRESEN_DISPLAY_ORDER_TAG);
        int readInt2 = PrefUtils.readInt(this._activity, PrefTagDefine.PRESEN_SORT_ORDER_TAG);
        if (readInt == 0) {
            this._displayGroup.check(R.id.Sort_Ascend);
        } else {
            this._displayGroup.check(R.id.Sort_Decend);
        }
        if (readInt2 == 0) {
            this._sortGroup.check(R.id.Sort_Name);
        } else {
            this._sortGroup.check(R.id.Sort_Date);
        }
    }
}
