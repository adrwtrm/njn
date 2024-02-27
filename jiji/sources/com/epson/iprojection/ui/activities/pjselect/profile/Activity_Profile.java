package com.epson.iprojection.ui.activities.pjselect.profile;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import java.util.Stack;

/* loaded from: classes.dex */
public class Activity_Profile extends PjConnectableActivity {
    private Stack<RemovableView> _childView;
    private XmlUtils.mplistType _selectedProfileType = XmlUtils.mplistType.UNKNOWN;

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Stack<RemovableView> stack = new Stack<>();
        this._childView = stack;
        stack.clear();
        XmlUtils.mplistType existProfileTypes = XmlUtils.existProfileTypes();
        setSelectedProfileType(existProfileTypes);
        if (XmlUtils.mplistType.BOTH != existProfileTypes) {
            pushContentView(R.layout.main_conpj_profile);
        } else {
            pushContentView(R.layout.main_conpj_profile_menu);
        }
        createToolBar();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private void createToolBar() {
        this._baseActionBar = new ActionBarProfile(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarProfile));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getCurrentChildView().resumeView();
        updatePjButtonState();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z;
        if (i == 4 && popContentView()) {
            updatePjButtonState();
            z = true;
        } else {
            z = false;
        }
        if (existsContentView() || z) {
            return false;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private boolean existsContentView() {
        return this._childView.size() > 1;
    }

    public synchronized boolean popContentView() {
        if (getCurrentChildView().pauseView(true)) {
            if (this._childView.size() > 1) {
                this._childView.pop();
                setContentView(this._childView.peek());
                getCurrentChildView().resumeView();
                return true;
            }
            return false;
        }
        return true;
    }

    public RemovableView getCurrentChildView() {
        Stack<RemovableView> stack = this._childView;
        if (stack != null && !stack.empty()) {
            return this._childView.peek();
        }
        Lg.e("Child view is lost!");
        return null;
    }

    public void pushContentView(RemovableView removableView) {
        this._childView.push(removableView);
        super.setContentView(this._childView.peek());
    }

    public synchronized void pushContentView(int i) {
        pushContentView((RemovableView) ((LayoutInflater) getSystemService("layout_inflater")).inflate(i, (ViewGroup) null));
    }

    public void setSelectedProfileType(XmlUtils.mplistType mplisttype) {
        this._selectedProfileType = mplisttype;
    }

    public XmlUtils.mplistType getSelectedProfileType() {
        return this._selectedProfileType;
    }

    public void backToTopView() {
        finish();
    }

    public void onPjStatusChanged() {
        updatePjButtonState();
    }

    public void disableBarButton() {
        updatePjButtonState();
    }
}
