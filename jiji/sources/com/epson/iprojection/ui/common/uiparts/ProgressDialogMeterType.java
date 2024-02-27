package com.epson.iprojection.ui.common.uiparts;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.epson.iprojection.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ProgressDialogMeterType.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\b¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/common/uiparts/ProgressDialogMeterType;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "setMax", "", "max", "", "setMessage", NotificationCompat.CATEGORY_MESSAGE, "", "setProgress", "progress", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ProgressDialogMeterType extends Dialog {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProgressDialogMeterType(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        requestWindowFeature(1);
        setContentView(R.layout.dialog_progress_meter);
    }

    public final void setMessage(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ((TextView) findViewById(R.id.text)).setText(msg);
    }

    public final void setMax(int i) {
        ((ProgressBar) findViewById(R.id.progress)).setMax(i);
    }

    public final void setProgress(int i) {
        ((ProgressBar) findViewById(R.id.progress)).setProgress(i);
    }
}
