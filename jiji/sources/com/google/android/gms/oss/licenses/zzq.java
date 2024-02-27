package com.google.android.gms.oss.licenses;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/* loaded from: classes.dex */
final class zzq implements AdapterView.OnItemClickListener {
    private final /* synthetic */ zzp zzad;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzq(zzp zzpVar) {
        this.zzad = zzpVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent = new Intent(this.zzad.zzac, OssLicensesActivity.class);
        intent.putExtra("license", (com.google.android.gms.internal.oss_licenses.zzc) adapterView.getItemAtPosition(i));
        this.zzad.zzac.startActivity(intent);
    }
}
