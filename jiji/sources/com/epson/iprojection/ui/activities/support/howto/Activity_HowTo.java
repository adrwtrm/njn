package com.epson.iprojection.ui.activities.support.howto;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.activities.support.intro.delivery.Activity_IntroDelivery;
import com.epson.iprojection.ui.activities.support.intro.gesturemenu.Activity_IntroGestureMenu;
import com.epson.iprojection.ui.activities.support.intro.mirroring.Activity_IntroMirroring;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.activities.support.intro.notfindpj.Activity_IntroNotFindPj;
import com.epson.iprojection.ui.activities.support.intro.userctrl.Activity_IntroUserCtrl;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Activity_HowTo extends PjConnectableActivity implements AdapterView.OnItemClickListener {
    public static final String TAG_INTENT_FROM_HOWTO = "tag_intent_from_howto";

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._baseActionBar = new CustomActionBar(this);
        setContentView(R.layout.main_howto);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new D_ListItem(""));
        arrayList.add(new D_ListItem(getString(R.string._FindProjector_)));
        arrayList.add(new D_ListItem(getString(R.string._GestureOperation_)));
        arrayList.add(new D_ListItem(getString(R.string._UseMirroring_)));
        arrayList.add(new D_ListItem(""));
        arrayList.add(new D_ListItem(getString(R.string._UserControl_) + "*"));
        arrayList.add(new D_ListItem(getString(R.string._DeliveryImage_) + "*"));
        arrayList.add(new D_ListItem(getString(R.string._UseModeratorFunction_) + "*"));
        arrayList.add(new D_ListItem(getString(R.string._NotSupportedModels_)));
        ListView listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter((ListAdapter) new InflaterListAdapter(this, arrayList));
        listView.setOnItemClickListener(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarHowto));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (!isFinishing() || ChromeOSUtils.isChromeOS()) {
            return;
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent;
        if (i == 1) {
            intent = new Intent(this, Activity_IntroNotFindPj.class);
        } else if (i == 2) {
            intent = new Intent(this, Activity_IntroGestureMenu.class);
        } else if (i == 3) {
            intent = new Intent(this, Activity_IntroMirroring.class);
            intent.putExtra(TAG_INTENT_FROM_HOWTO, "empty message");
        } else if (i == 5) {
            intent = new Intent(this, Activity_IntroUserCtrl.class);
        } else if (i == 6) {
            intent = new Intent(this, Activity_IntroDelivery.class);
        } else if (i != 7) {
            return;
        } else {
            intent = new Intent(this, Activity_IntroModerator.class);
        }
        startActivity(intent);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
