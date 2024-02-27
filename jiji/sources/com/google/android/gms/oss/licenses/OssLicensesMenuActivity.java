package com.google.android.gms.oss.licenses;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public final class OssLicensesMenuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<com.google.android.gms.internal.oss_licenses.zzc>> {
    private static String zzx;
    private boolean zzaa;
    private Task<String> zzab;
    private zzc zzo;
    private zze zzp;
    private ListView zzy;
    private ArrayAdapter<com.google.android.gms.internal.oss_licenses.zzc> zzz;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzo = zzc.zza(this);
        this.zzaa = fileExistsAndNotEmpty(this, "third_party_licenses") && fileExistsAndNotEmpty(this, "third_party_license_metadata");
        if (zzx == null) {
            Intent intent = getIntent();
            if (intent.hasExtra("title")) {
                zzx = intent.getStringExtra("title");
                Log.w("OssLicensesMenuActivity", "The intent based title is deprecated. Use OssLicensesMenuActivity.setActivityTitle(title) instead.");
            }
        }
        String str = zzx;
        if (str != null) {
            setTitle(str);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (this.zzaa) {
            zzh zzb = zzc.zza(this).zzb();
            this.zzab = zzb.doRead(new zzk(zzb, getPackageName()));
            getSupportLoaderManager().initLoader(54321, null, this);
            this.zzab.addOnCompleteListener(new zzp(this));
            return;
        }
        setContentView(R.layout.license_menu_activity_no_licenses);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class zza extends ArrayAdapter<com.google.android.gms.internal.oss_licenses.zzc> {
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public zza(android.content.Context r4) {
            /*
                r2 = this;
                com.google.android.gms.oss.licenses.OssLicensesMenuActivity.this = r3
                com.google.android.gms.oss.licenses.OssLicensesMenuActivity.zzb(r3)
                com.google.android.gms.oss.licenses.zze r0 = com.google.android.gms.oss.licenses.OssLicensesMenuActivity.zza(r3)
                int r0 = com.google.android.gms.oss.licenses.zzc.zza(r0)
                com.google.android.gms.oss.licenses.OssLicensesMenuActivity.zzb(r3)
                com.google.android.gms.oss.licenses.zze r3 = com.google.android.gms.oss.licenses.OssLicensesMenuActivity.zza(r3)
                int r3 = com.google.android.gms.oss.licenses.zzc.zzb(r3)
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                r2.<init>(r4, r0, r3, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.oss.licenses.OssLicensesMenuActivity.zza.<init>(com.google.android.gms.oss.licenses.OssLicensesMenuActivity, android.content.Context):void");
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                zzc unused = OssLicensesMenuActivity.this.zzo;
                LayoutInflater layoutInflater = OssLicensesMenuActivity.this.getLayoutInflater();
                zze zzeVar = OssLicensesMenuActivity.this.zzp;
                view = layoutInflater.inflate((XmlPullParser) zzeVar.zzg.getXml(zzc.zza(zzeVar)), viewGroup, false);
            }
            zzc unused2 = OssLicensesMenuActivity.this.zzo;
            ((TextView) view.findViewById(zzc.zzb(OssLicensesMenuActivity.this.zzp))).setText(getItem(i).toString());
            return view;
        }
    }

    public static void setActivityTitle(String str) {
        zzx = str;
    }

    static boolean fileExistsAndNotEmpty(Context context, String str) {
        InputStream inputStream = null;
        try {
            Resources resources = context.getResources();
            inputStream = resources.openRawResource(resources.getIdentifier(str, "raw", resources.getResourcePackageName(R.id.license_list)));
            boolean z = inputStream.available() > 0;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
            return z;
        } catch (Resources.NotFoundException | IOException unused2) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
            }
            return false;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused4) {
                }
            }
            throw th;
        }
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        getSupportLoaderManager().destroyLoader(54321);
        super.onDestroy();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader<List<com.google.android.gms.internal.oss_licenses.zzc>> onCreateLoader(int i, Bundle bundle) {
        if (this.zzaa) {
            return new zzo(this, zzc.zza(this));
        }
        return null;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader<List<com.google.android.gms.internal.oss_licenses.zzc>> loader, List<com.google.android.gms.internal.oss_licenses.zzc> list) {
        this.zzz.clear();
        this.zzz.addAll(list);
        this.zzz.notifyDataSetChanged();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader<List<com.google.android.gms.internal.oss_licenses.zzc>> loader) {
        this.zzz.clear();
        this.zzz.notifyDataSetChanged();
    }
}
