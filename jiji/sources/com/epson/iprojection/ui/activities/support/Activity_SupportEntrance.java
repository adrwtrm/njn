package com.epson.iprojection.ui.activities.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.support.howto.Activity_HowTo;
import com.epson.iprojection.ui.activities.support.intro.CopyrightActivity;
import com.epson.iprojection.ui.activities.support.intro.LisenceActivity;
import com.epson.iprojection.ui.activities.support.intro.TrademarkActivity;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Activity_SupportEntrance extends PjConnectableActivity implements AdapterView.OnItemClickListener {
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        pushDrawerStatus(eDrawerMenuItem.Support);
        CustomActionBar customActionBar = new CustomActionBar(this);
        this._baseActionBar = customActionBar;
        setContentView(R.layout.main_support_entrance);
        customActionBar.layout(R.layout.toolbar_support_entrance);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new D_ListItem(getString(R.string._HowToUseTitle_)));
        arrayList.add(new D_ListItem(getString(R.string._UsageTips_)));
        arrayList.add(new D_ListItem(getString(R.string._Manual_)));
        arrayList.add(new D_ListItem(getString(R.string._AboutProjectors_)));
        arrayList.add(new D_ListItem(getString(R.string._SupportedProjectorList_)));
        arrayList.add(new D_ListItem(getString(R.string._EpsonSetupNavi_)));
        arrayList.add(new D_ListItem(getString(R.string._About_)));
        arrayList.add(new D_ListItem(getString(R.string._SoftwareLicense_Short_)));
        arrayList.add(new D_ListItem(getString(R.string._CopyrightInformation_)));
        arrayList.add(new D_ListItem(getString(R.string._Trademarks_)));
        arrayList.add(new D_ListItem(getString(R.string._PrivacyStatement_)));
        arrayList.add(new D_ListItem(getString(R.string._VersionInfo_)));
        ListView listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter((ListAdapter) new InflaterListAdapter(this, arrayList));
        listView.setOnItemClickListener(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarSupportEntrance));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        if (getIntent().getStringExtra(IntentTagDefine.INTRO_SUPPORT_INFOMATION_TAG) != null) {
            Analytics.getIns().setSupportEventType(eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_HOME);
            Analytics.getIns().sendEvent(eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_HOME);
            return;
        }
        Analytics.getIns().setSupportEventType(eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_DRAWER);
        Analytics.getIns().sendEvent(eCustomEvent.SUPPORT_SCREEN_DISPLAY_FROM_DRAWER);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        popDrawerStatus();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent;
        switch (i) {
            case 1:
                intent = new Intent(this, Activity_HowTo.class);
                Analytics.getIns().setSupportEventType(eCustomEvent.USAGE_TIPS);
                Analytics.getIns().sendEvent(eCustomEvent.USAGE_TIPS);
                break;
            case 2:
                Intent intent2 = new Intent("android.intent.action.VIEW", getManualUri());
                if (getPackageManager().queryIntentActivities(intent2, 0).size() > 0) {
                    Analytics.getIns().setSupportEventType(eCustomEvent.MANUAL);
                    Analytics.getIns().sendEvent(eCustomEvent.MANUAL);
                    startActivity(intent2);
                }
                intent = null;
                break;
            case 3:
            case 6:
            default:
                intent = null;
                break;
            case 4:
                Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string._SupportPjListUrl_)));
                if (getPackageManager().queryIntentActivities(intent3, 0).size() > 0) {
                    Analytics.getIns().setSupportEventType(eCustomEvent.SUPPORTED_PROJECTORS_LIST);
                    Analytics.getIns().sendEvent(eCustomEvent.SUPPORTED_PROJECTORS_LIST);
                    startActivity(intent3);
                }
                intent = null;
                break;
            case 5:
                Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string._SetupNaviUrl_)));
                if (getPackageManager().queryIntentActivities(intent4, 0).size() > 0) {
                    Analytics.getIns().setSupportEventType(eCustomEvent.EPSON_SETUP_NAVI);
                    Analytics.getIns().sendEvent(eCustomEvent.EPSON_SETUP_NAVI);
                    startActivity(intent4);
                }
                intent = null;
                break;
            case 7:
                intent = new Intent(this, LisenceActivity.class);
                break;
            case 8:
                intent = new Intent(this, CopyrightActivity.class);
                break;
            case 9:
                intent = new Intent(this, TrademarkActivity.class);
                break;
            case 10:
                Intent intent5 = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string._PrivacyStatementRrl_)));
                if (getPackageManager().queryIntentActivities(intent5, 0).size() > 0) {
                    startActivity(intent5);
                }
                intent = null;
                break;
            case 11:
                intent = new Intent(this, Activity_AppVersion.class);
                break;
        }
        if (intent == null || getPackageManager().resolveActivity(intent, 0) == null) {
            return;
        }
        startActivity(intent);
        if (ChromeOSUtils.isChromeOS()) {
            return;
        }
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private Uri getManualUri() {
        if (ChromeOSUtils.INSTANCE.isChromeOS(this)) {
            return Uri.parse(getString(R.string._UsersGuideUrlForChromebook_));
        }
        return Uri.parse(getString(R.string._UsersGuideUrl_));
    }
}
