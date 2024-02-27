package com.epson.iprojection.ui.activities.pjselect.dialogs.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;

/* loaded from: classes.dex */
public class BaseHaveButtonDialog extends BaseDialog {
    protected String[] _itemNames;
    protected eKind _kind;
    protected String _message;
    protected String _title;

    /* loaded from: classes.dex */
    public enum eKind {
        Nomal,
        Alert
    }

    public BaseHaveButtonDialog(Context context, IOnDialogEventListener iOnDialogEventListener, String str, String str2, String[] strArr, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, resultAction);
        this._kind = eKind.Nomal;
        this._title = str;
        this._message = str2;
        this._itemNames = strArr;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void create(Context context) {
        super.create(context);
        setConfig(this._context, this._builder);
    }

    public void setKindAlert() {
        this._kind = eKind.Alert;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseHaveButtonDialog$eKind;

        static {
            int[] iArr = new int[eKind.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseHaveButtonDialog$eKind = iArr;
            try {
                iArr[eKind.Nomal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseHaveButtonDialog$eKind[eKind.Alert.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void setConfig(Context context, AlertDialog.Builder builder) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$base$BaseHaveButtonDialog$eKind[this._kind.ordinal()];
        if (i == 1) {
            builder.setTitle(this._title);
        } else if (i == 2) {
            builder.setIconAttribute(16843605);
            String str = this._title;
            if (str == null) {
                builder.setTitle(R.string._ApplicationName_);
            } else {
                builder.setTitle(str);
            }
        }
        builder.setMessage(this._message);
        String[] strArr = this._itemNames;
        int length = strArr.length;
        if (length != 1) {
            if (length == 2) {
                builder.setNegativeButton(strArr[1], new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        BaseHaveButtonDialog.this.m147x818344b5(dialogInterface, i2);
                    }
                });
            } else {
                Lg.e("ボタンの数が不正です");
                return;
            }
        }
        builder.setPositiveButton(this._itemNames[0], new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                BaseHaveButtonDialog.this.m148x5f76aa94(dialogInterface, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-base-BaseHaveButtonDialog  reason: not valid java name */
    public /* synthetic */ void m147x818344b5(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._impl.onClickDialogNG(this._action);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-pjselect-dialogs-base-BaseHaveButtonDialog  reason: not valid java name */
    public /* synthetic */ void m148x5f76aa94(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._impl.onClickDialogOK("", this._action);
        }
    }
}
