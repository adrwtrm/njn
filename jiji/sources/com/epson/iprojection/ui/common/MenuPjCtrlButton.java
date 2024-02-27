package com.epson.iprojection.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.ui.activities.remote.Activity_Remote;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class MenuPjCtrlButton {
    private static final int BTN_ID = 101;

    public static void onCreateOptionsMenu(Activity activity, Menu menu) {
    }

    public static boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(101).setVisible(true);
        menu.findItem(101).setEnabled(Pj.getIns().isConnected());
        return true;
    }

    public static boolean onOptionsItemSelected(Activity activity, MenuItem menuItem) {
        if (menuItem.getItemId() == 101) {
            activity.startActivity(new Intent(activity.getApplicationContext(), Activity_Remote.class));
            return true;
        }
        return true;
    }
}
