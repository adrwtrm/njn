package com.epson.iprojection.ui.activities.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.presen.Defines;
import com.epson.iprojection.ui.activities.splash.Activity_Splash;
import com.epson.iprojection.ui.activities.terms.Activity_TermsToMain;
import com.epson.iprojection.ui.common.activity.base.IproBaseActivity;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;

/* loaded from: classes.dex */
public class Activity_Splash extends IproBaseActivity {
    private static final int SLEEP_TIME = 1000;
    private final Handler _handler = new Handler();
    private boolean _callEnabled = true;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_splash);
        findViewById(16908290).setSystemUiVisibility(Defines.PDF_MAX_W);
        resetImage();
        new Thread(new ShowLogoThread()).start();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this._callEnabled = false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this._callEnabled = false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        resetImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ShowLogoThread implements Runnable {
        private ShowLogoThread() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Sleeper.sleep(1000L);
            if (Activity_Splash.this._callEnabled) {
                Activity_Splash.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.splash.Activity_Splash$ShowLogoThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Activity_Splash.ShowLogoThread.this.m180x6321c8cd();
                    }
                });
            } else {
                Activity_Splash.this.finish();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$run$0$com-epson-iprojection-ui-activities-splash-Activity_Splash$ShowLogoThread  reason: not valid java name */
        public /* synthetic */ void m180x6321c8cd() {
            Intent intent = new Intent(Activity_Splash.this.getApplicationContext(), Activity_TermsToMain.class);
            intent.setFlags(67108864);
            intent.putExtra(IntentTagDefine.LAUNCH_ROUTE_TAG, "empty message");
            intent.putExtra(IntentTagDefine.ROOT_TAG, "empty message");
            Activity_Splash.this.startActivity(intent);
            Activity_Splash.this.finish();
        }
    }

    private void resetImage() {
        ImageView imageView = (ImageView) findViewById(R.id.img_splash_main);
        if (getResources().getConfiguration().orientation == 2) {
            imageView.setImageResource(R.drawable.logo_horizontal);
        } else {
            imageView.setImageResource(R.drawable.logo_vertial);
        }
    }
}
