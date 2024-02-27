package com.epson.iprojection.ui.activities.pjselect.registed;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.activities.pjselect.common.BasePanelButtons;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;

/* loaded from: classes.dex */
public class PanelButtons extends BasePanelButtons {
    public PanelButtons(Activity activity, View view, DrawerList drawerList) {
        super(activity, view, drawerList);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_mirroring);
        if (imageView == null || !ChromeOSUtils.INSTANCE.isChromeOS(activity)) {
            return;
        }
        imageView.setImageResource(R.drawable.home_mirroring_chromebook);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnRemote) {
            if (this._drawerList.askToConnect(2)) {
                return;
            }
            this._drawerList.callNextActivity(2);
        } else if (view == this._btnPhoto) {
            this._drawerList.startNextActivity(eContentsType.Photo);
        } else if (view == this._btnPdf) {
            this._drawerList.startNextActivity(eContentsType.Pdf);
        } else if (view == this._btnWeb) {
            this._drawerList.startNextActivity(eContentsType.Web);
        } else if (view == this._btnCamera) {
            this._drawerList.startNextActivity(eContentsType.Camera);
        } else if (view == this._btnMirroring) {
            this._drawerList.callNextActivity(6);
        }
    }
}
