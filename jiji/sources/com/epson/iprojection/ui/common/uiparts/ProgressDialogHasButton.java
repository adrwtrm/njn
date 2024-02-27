package com.epson.iprojection.ui.common.uiparts;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.epson.iprojection.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ProgressDialogHasButton.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/common/uiparts/ProgressDialogHasButton;", "Landroid/app/AlertDialog;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "mView", "Landroid/view/View;", "setMessage", "", NotificationCompat.CATEGORY_MESSAGE, "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ProgressDialogHasButton extends AlertDialog {
    private View mView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProgressDialogHasButton(Activity activity) {
        super(activity);
        Intrinsics.checkNotNullParameter(activity, "activity");
        requestWindowFeature(1);
        View inflate = activity.getLayoutInflater().inflate(R.layout.dialog_progress_hasbutton, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "activity.layoutInflater.…progress_hasbutton, null)");
        this.mView = inflate;
        setView(inflate);
    }

    public final void setMessage(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ((TextView) this.mView.findViewById(R.id.text)).setText(msg);
    }
}
