package com.epson.iprojection.ui.activities.support;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.UUID;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import kotlin.Metadata;

/* compiled from: DebugSettingActivity.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/activities/support/DebugSettingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", "()V", "onCheckedChanged", "", "view", "Landroid/widget/CompoundButton;", "isChecked", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DebugSettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_debug_setting);
        ((TextView) findViewById(R.id.txt_uuid)).setText("AgentID:\n" + UUID.INSTANCE.getUuid());
        boolean readBoolean = PrefUtils.readBoolean(this, PrefTagDefine.SEND_PJLOG_TO_TESTSERVER, false);
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_pjlog);
        switchCompat.setChecked(readBoolean);
        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        PrefUtils.write(this, PrefTagDefine.SEND_PJLOG_TO_TESTSERVER, z);
    }
}
