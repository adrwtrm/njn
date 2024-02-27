package com.epson.iprojection.ui.activities.drawermenu;

import com.epson.iprojection.ui.common.activitystatus.eContentsType;

/* loaded from: classes.dex */
public class DrawerDataSet {
    eContentsType contentsType;
    eDrawerMenuItem selectType;

    public DrawerDataSet(eDrawerMenuItem edrawermenuitem, eContentsType econtentstype) {
        this.selectType = edrawermenuitem;
        this.contentsType = econtentstype;
    }
}
