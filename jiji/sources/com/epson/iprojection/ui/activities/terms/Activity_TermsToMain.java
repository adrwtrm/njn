package com.epson.iprojection.ui.activities.terms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.activities.support.intro.CopyrightActivity;
import com.epson.iprojection.ui.activities.support.intro.LisenceActivity;
import com.epson.iprojection.ui.common.activity.base.IproBaseActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.AppStartActivityManager;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class Activity_TermsToMain extends IproBaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Button _btnOk;

    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (!isAvailableWithoutSD() && !PathGetter.getIns().isAvailableExternalStorage(this)) {
            ToastMgr.getIns().show(this, ToastMgr.Type.InsertSDCard);
            finish();
        } else if (isConsented()) {
            callNextActivity();
        } else {
            setContentView(R.layout.main_eula);
            Button button = (Button) findViewById(R.id.btn_ok);
            this._btnOk = button;
            button.setOnClickListener(this);
            setupLinkedText();
            setupLinkedTextOfCopyright();
            ((CheckBox) findViewById(R.id.check_agree)).setOnCheckedChangeListener(this);
        }
    }

    private void setupLinkedText() {
        HashMap hashMap = new HashMap();
        hashMap.put(getString(R.string._PrivacyStatement_), new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string._PrivacyStatementRrl_))));
        hashMap.put(getString(R.string._SwLicenseAgreement_), new Intent(this, LisenceActivity.class));
        setLinkedText((TextView) findViewById(R.id.txt_eula), getString(R.string._AgreementForEula_), hashMap);
        setLinkedText((TextView) findViewById(R.id.txt_ga), getString(R.string._AgreementForGa_), hashMap);
    }

    private void setupLinkedTextOfCopyright() {
        HashMap hashMap = new HashMap();
        hashMap.put(getString(R.string._CopyrightInformation_), new Intent(this, CopyrightActivity.class));
        setLinkedText((TextView) findViewById(R.id.txt_copyright), getString(R.string._CopyrightInformation_), hashMap);
    }

    private void setLinkedText(TextView textView, String str, Map<String, Intent> map) {
        textView.setText(createSpannableString(str, map));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString createSpannableString(String str, Map<String, Intent> map) {
        SpannableString spannableString = new SpannableString(str);
        for (final Map.Entry<String, Intent> entry : map.entrySet()) {
            Matcher matcher = Pattern.compile(entry.getKey()).matcher(str);
            while (matcher.find()) {
                spannableString.setSpan(new ClickableSpan() { // from class: com.epson.iprojection.ui.activities.terms.Activity_TermsToMain.1
                    {
                        Activity_TermsToMain.this = this;
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        Intent intent = (Intent) entry.getValue();
                        if (Activity_TermsToMain.this.getPackageManager().resolveActivity(intent, 0) != null) {
                            Activity_TermsToMain.this.startActivity(intent);
                        }
                    }
                }, matcher.start(), matcher.end(), 18);
            }
        }
        return spannableString;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnOk) {
            onAgree();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this._btnOk.setEnabled(z);
    }

    private void onAgree() {
        PrefUtils.writeInt(this, PrefTagDefine.conPJ_USED_ANALYTICS, ((CheckBox) findViewById(R.id.check_eula_used_analytics)).isChecked() ? 1 : 0, (SharedPreferences.Editor) null);
        Analytics.getIns().updateAnalyticsCollectionEnabled();
        PrefUtils.recordConsented(this);
        PrefUtils.writeInt(this, PrefTagDefine.conPJ_CONFIG_NOINTURRUPT_TAG, 0, (SharedPreferences.Editor) null);
        callNextActivity();
    }

    private boolean isConsented() {
        return PrefUtils.isConsented(this);
    }

    private void callNextActivity() {
        Intent intent = new Intent(this, Activity_PjSelect.class);
        intent.setFlags(67108864);
        intent.putExtra(IntentTagDefine.ROOT_TAG, "empty message");
        intent.putExtra(IntentTagDefine.LAUNCH_ROUTE_TAG, getIntent().getStringExtra(IntentTagDefine.LAUNCH_ROUTE_TAG));
        startActivity(intent);
        finish();
    }

    private boolean isAvailableWithoutSD() {
        return AppStartActivityManager.getIns().isNextActivityAvailableWithoutSD();
    }
}
