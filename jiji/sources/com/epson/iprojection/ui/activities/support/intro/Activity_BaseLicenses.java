package com.epson.iprojection.ui.activities.support.intro;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class Activity_BaseLicenses extends PjConnectableActivity {
    static final String JAPAN = "ja";

    protected abstract int getLayoutID();

    protected abstract String getTextFileName();

    protected abstract int getTitleStringID();

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(getLayoutID());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLicence);
        toolbar.setTitle(getTitleStringID());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        if (ChromeOSUtils.isChromeOS()) {
            return;
        }
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        setContentText();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void finish() {
        super.finish();
        if (ChromeOSUtils.isChromeOS()) {
            return;
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setContentText() {
        String str;
        InputStream inputStream;
        if (Locale.getDefault().getLanguage().equals(JAPAN)) {
            str = getTextFileName() + "_ja.txt";
        } else {
            str = getTextFileName() + ".txt";
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                inputStream = getResources().getAssets().open(str);
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine).append("\n");
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    ((TextView) findViewById(R.id.text_content)).setText(sb.toString());
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
            }
        } catch (IOException e) {
            Lg.e("IOException : " + e.getMessage());
        }
    }
}
