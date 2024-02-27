package com.epson.iprojection.ui.activities.support.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.epson.iprojection.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CopyrightActivity.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0004H\u0014J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/ui/activities/support/intro/CopyrightActivity;", "Lcom/epson/iprojection/ui/activities/support/intro/Activity_BaseLicenses;", "()V", "getLayoutID", "", "getTextFileName", "", "getTitleStringID", "onCreate", "", "b", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CopyrightActivity extends Activity_BaseLicenses {
    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected int getLayoutID() {
        return R.layout.main_copyright;
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected String getTextFileName() {
        return "copyright";
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected int getTitleStringID() {
        return R.string._CopyrightInformation_;
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        findViewById(R.id.btn_other_oss).setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.support.intro.CopyrightActivity$$ExternalSyntheticLambda0
            {
                CopyrightActivity.this = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CopyrightActivity.onCreate$lambda$0(CopyrightActivity.this, view);
            }
        });
    }

    public static final void onCreate$lambda$0(CopyrightActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, OssLicensesMenuActivity.class);
        intent.putExtra("title", this$0.getString(R.string._OSS_));
        this$0.startActivity(intent);
    }
}
