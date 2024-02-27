package com.google.android.gms.oss.licenses;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class OssLicensesActivity extends AppCompatActivity {
    private com.google.android.gms.internal.oss_licenses.zzc zzh;
    private String zzi = "";
    private ScrollView zzj = null;
    private TextView zzk = null;
    private int zzl = 0;
    private Task<String> zzm;
    private Task<String> zzn;
    private zzc zzo;
    zze zzp;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.libraries_social_licenses_license_loading);
        this.zzo = zzc.zza(this);
        this.zzh = (com.google.android.gms.internal.oss_licenses.zzc) getIntent().getParcelableExtra("license");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(this.zzh.toString());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setLogo((Drawable) null);
        }
        ArrayList arrayList = new ArrayList();
        zzh zzb = this.zzo.zzb();
        Task doRead = zzb.doRead(new zzl(zzb, this.zzh));
        this.zzm = doRead;
        arrayList.add(doRead);
        zzh zzb2 = this.zzo.zzb();
        Task doRead2 = zzb2.doRead(new zzj(zzb2, getPackageName()));
        this.zzn = doRead2;
        arrayList.add(doRead2);
        Tasks.whenAll(arrayList).addOnCompleteListener(new zzf(this));
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        TextView textView = this.zzk;
        if (textView == null || this.zzj == null) {
            return;
        }
        bundle.putInt("scroll_pos", this.zzk.getLayout().getLineStart(textView.getLayout().getLineForVertical(this.zzj.getScrollY())));
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.zzl = bundle.getInt("scroll_pos");
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
