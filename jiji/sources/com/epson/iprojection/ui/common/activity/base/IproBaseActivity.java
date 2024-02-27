package com.epson.iprojection.ui.common.activity.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameUtils;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class IproBaseActivity extends AppCompatActivity {
    public String _screenName = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Analytics.getIns().isSetuped()) {
            return;
        }
        Analytics.getIns().setup(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this._screenName == null) {
            this._screenName = ScreenNameUtils.Companion.createScreenName((String) Objects.requireNonNull(getClass().getCanonicalName()));
        }
        Analytics.getIns().sendScreenEvent(this._screenName);
    }
}
