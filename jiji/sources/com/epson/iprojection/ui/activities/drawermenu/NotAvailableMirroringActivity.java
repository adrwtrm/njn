package com.epson.iprojection.ui.activities.drawermenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.activitystatus.ActivityKillStatus;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotAvailableMirroringActivity.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\b\u001a\u00020\u00052\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/NotAvailableMirroringActivity;", "Lcom/epson/iprojection/ui/common/activity/base/PjConnectableActivity;", "Landroid/view/View$OnClickListener;", "()V", "onClick", "", "p0", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class NotAvailableMirroringActivity extends PjConnectableActivity implements View.OnClickListener {
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_notavailable_mirroring);
        ((Button) findViewById(R.id.linkText)).setOnClickListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarNotavailableMirroring));
        if (getSupportActionBar() != null) {
            ActionBar supportActionBar = getSupportActionBar();
            Intrinsics.checkNotNull(supportActionBar);
            supportActionBar.setDisplayShowTitleEnabled(false);
            ActionBar supportActionBar2 = getSupportActionBar();
            Intrinsics.checkNotNull(supportActionBar2);
            supportActionBar2.setDisplayHomeAsUpEnabled(true);
            ActionBar supportActionBar3 = getSupportActionBar();
            Intrinsics.checkNotNull(supportActionBar3);
            supportActionBar3.setHomeButtonEnabled(true);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ActivityKillStatus.getIns().startKill();
        finish();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
