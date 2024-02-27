package com.epson.iprojection.ui.activities.web.menu.bookmark;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.exception.FullException;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class EditBookmarkDialog {
    private final Activity _activity;
    private final Bookmark _bookmark;
    private final IOnClickDialogOkListener _impl;
    private final Startpage _startpage;

    /* loaded from: classes.dex */
    public enum Mode {
        Add,
        Edit
    }

    public EditBookmarkDialog(Activity activity, String str, final String str2, final IOnClickDialogOkListener iOnClickDialogOkListener, Mode mode) {
        this._activity = activity;
        this._impl = iOnClickDialogOkListener;
        this._bookmark = new Bookmark(null).initialize(activity, null, false);
        this._startpage = new Startpage().initialize(activity, null, false);
        final View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_addbkmk, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$web$menu$bookmark$EditBookmarkDialog$Mode[mode.ordinal()];
        if (i == 1) {
            builder.setTitle(activity.getString(R.string._AddToBookmark_));
        } else if (i == 2) {
            builder.setTitle(activity.getString(R.string._EditBookmark_));
        }
        builder.setView(inflate);
        ((CheckBox) inflate.findViewById(R.id.chk_dlg_addbkmk_setstartpage)).setChecked(isStartpage(str2));
        ((EditText) inflate.findViewById(R.id.edt_dlg_addbkmk_title)).setText(str);
        ((EditText) inflate.findViewById(R.id.edt_dlg_addbkmk_url)).setText(str2);
        builder.setPositiveButton(activity.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.web.menu.bookmark.EditBookmarkDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                EditBookmarkDialog.this.m190xa3329a2d(inflate, str2, iOnClickDialogOkListener, dialogInterface, i2);
            }
        });
        builder.setNegativeButton(activity.getString(R.string._Cancel_), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* renamed from: com.epson.iprojection.ui.activities.web.menu.bookmark.EditBookmarkDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$web$menu$bookmark$EditBookmarkDialog$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$web$menu$bookmark$EditBookmarkDialog$Mode = iArr;
            try {
                iArr[Mode.Add.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$web$menu$bookmark$EditBookmarkDialog$Mode[Mode.Edit.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-web-menu-bookmark-EditBookmarkDialog  reason: not valid java name */
    public /* synthetic */ void m190xa3329a2d(View view, String str, IOnClickDialogOkListener iOnClickDialogOkListener, DialogInterface dialogInterface, int i) {
        onClickOk(view, str, iOnClickDialogOkListener);
    }

    private void onClickOk(View view, String str, IOnClickDialogOkListener iOnClickDialogOkListener) {
        String obj = ((EditText) view.findViewById(R.id.edt_dlg_addbkmk_title)).getText().toString();
        if (obj.length() == 0) {
            obj = this._activity.getString(R.string._Untitled_);
        }
        try {
            this._bookmark.add(obj, str);
        } catch (FullException unused) {
            Lg.e("fullじゃ無い時ここに来るので、fullはありえない");
        }
        String url = this._startpage.getUrl();
        if (((CheckBox) view.findViewById(R.id.chk_dlg_addbkmk_setstartpage)).isChecked()) {
            this._startpage.set(obj, str);
        } else if (url != null && str.compareTo(url) == 0) {
            this._startpage.deleteAll();
        }
        this._impl.onClickDialogOk();
    }

    private boolean isStartpage(String str) {
        return this._startpage.getDataID(str, 1) != -1;
    }
}
