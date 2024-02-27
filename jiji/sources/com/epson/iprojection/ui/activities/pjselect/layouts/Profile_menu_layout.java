package com.epson.iprojection.ui.activities.pjselect.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.IProjListListener;
import com.epson.iprojection.ui.activities.pjselect.ProfileMenuList;
import com.epson.iprojection.ui.activities.pjselect.profile.Activity_Profile;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.io.File;

/* loaded from: classes.dex */
public class Profile_menu_layout extends RemovableView implements IProjListListener {
    private ProfileMenuList _profileList;
    private final Activity_Profile parent;

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickFolder(int i) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickItem(D_PjInfo d_PjInfo, View view) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public boolean onClickProjectorDetail(D_PjInfo d_PjInfo) {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView
    public boolean pauseView(boolean z) {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void updateList(boolean z) {
    }

    public Profile_menu_layout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.parent = (Activity_Profile) getContext();
        this._profileList = null;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this._profileList = new ProfileMenuList(this.parent, (ListView) findViewById(R.id.list_Profile_menu), this);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView
    public void resumeView() {
        TextView textView = (TextView) this.parent.findViewById(R.id.txt_titlebar_filename);
        if (textView != null) {
            textView.setText(this.parent.getString(R.string._Profile_));
        }
        if (!Pj.getIns().isAvailablePjFinder() || !isProfileAvailable()) {
            this.parent.backToTopView();
            return;
        }
        this._profileList.updateListener();
        this.parent.disableDrawerList();
    }

    private boolean isProfileAvailable() {
        return new File(new StringBuilder().append(PathGetter.getIns().getAppsDirPath()).append("/.mplist_local_master").toString()).exists() && new File(new StringBuilder().append(PathGetter.getIns().getAppsDirPath()).append("/.mplist_shared_master").toString()).exists();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile() {
        this.parent.pushContentView(R.layout.main_conpj_profile);
        this.parent.getCurrentChildView().resumeView();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile(XmlUtils.mplistType mplisttype) {
        this.parent.setSelectedProfileType(mplisttype);
        onClickProfile();
    }
}
