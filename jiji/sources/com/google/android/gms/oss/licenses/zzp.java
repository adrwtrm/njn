package com.google.android.gms.oss.licenses;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzp implements OnCompleteListener<String> {
    final /* synthetic */ OssLicensesMenuActivity zzac;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzp(OssLicensesMenuActivity ossLicensesMenuActivity) {
        this.zzac = ossLicensesMenuActivity;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task<String> task) {
        ListView listView;
        ArrayAdapter arrayAdapter;
        ListView listView2;
        String packageName = this.zzac.getPackageName();
        if (this.zzac.isDestroyed() || this.zzac.isFinishing()) {
            return;
        }
        if (task.isSuccessful()) {
            packageName = task.getResult();
        }
        OssLicensesMenuActivity ossLicensesMenuActivity = this.zzac;
        ossLicensesMenuActivity.zzp = zzc.zza(ossLicensesMenuActivity, packageName);
        OssLicensesMenuActivity ossLicensesMenuActivity2 = this.zzac;
        zzc unused = ossLicensesMenuActivity2.zzo;
        LayoutInflater layoutInflater = this.zzac.getLayoutInflater();
        zze zzeVar = this.zzac.zzp;
        ossLicensesMenuActivity2.setContentView(layoutInflater.inflate((XmlPullParser) zzeVar.zzg.getXml(zzeVar.zzg.getIdentifier("libraries_social_licenses_license_menu_activity", "layout", zzeVar.packageName)), (ViewGroup) null, false));
        OssLicensesMenuActivity ossLicensesMenuActivity3 = this.zzac;
        zzc unused2 = ossLicensesMenuActivity3.zzo;
        zze zzeVar2 = this.zzac.zzp;
        ossLicensesMenuActivity3.zzy = (ListView) ossLicensesMenuActivity3.findViewById(zzeVar2.zzg.getIdentifier("license_list", "id", zzeVar2.packageName));
        OssLicensesMenuActivity ossLicensesMenuActivity4 = this.zzac;
        OssLicensesMenuActivity ossLicensesMenuActivity5 = this.zzac;
        ossLicensesMenuActivity4.zzz = new OssLicensesMenuActivity.zza(ossLicensesMenuActivity5);
        listView = this.zzac.zzy;
        arrayAdapter = this.zzac.zzz;
        listView.setAdapter((ListAdapter) arrayAdapter);
        listView2 = this.zzac.zzy;
        listView2.setOnItemClickListener(new zzq(this));
    }
}
