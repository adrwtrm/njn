package com.google.android.gms.oss.licenses;

import android.text.Layout;
import android.widget.ScrollView;
import android.widget.TextView;

/* loaded from: classes.dex */
final class zzg implements Runnable {
    private final /* synthetic */ zzf zzr;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzg(zzf zzfVar) {
        this.zzr = zzfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TextView textView;
        int i;
        TextView textView2;
        ScrollView scrollView;
        textView = this.zzr.zzq.zzk;
        Layout layout = textView.getLayout();
        i = this.zzr.zzq.zzl;
        int lineForOffset = layout.getLineForOffset(i);
        textView2 = this.zzr.zzq.zzk;
        int lineTop = textView2.getLayout().getLineTop(lineForOffset);
        scrollView = this.zzr.zzq.zzj;
        scrollView.scrollTo(0, lineTop);
    }
}
