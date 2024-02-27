package com.epson.iprojection.ui.activities.drawermenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InflaterListAdapter extends BaseAdapter {
    private static final int DISABLE_ALPHA = 64;
    private final Context _context;
    private final DrawerDataSet _dataSet;
    private final View.OnClickListener _impl;
    private final boolean _isSdMounted;
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_Inflater> _listDtoInflater;
    private SwitchMaterial _mirroringSwitch;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterListAdapter(Context context, ArrayList<D_Inflater> arrayList, boolean z, View.OnClickListener onClickListener, DrawerDataSet drawerDataSet) {
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
        this._isSdMounted = z;
        this._context = context;
        this._impl = onClickListener;
        this._dataSet = drawerDataSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:157:0x005f, code lost:
        if (r6.get(0).getPjInfo().isMppDelivery != false) goto L49;
     */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View getView(int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            Method dump skipped, instructions count: 1076
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.drawermenu.InflaterListAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void updateMirroringSwitch() {
        SwitchMaterial switchMaterial = this._mirroringSwitch;
        if (switchMaterial == null) {
            return;
        }
        boolean isChecked = switchMaterial.isChecked();
        boolean z = this._mirroringSwitch.getVisibility() == 0;
        if (!Pj.getIns().isConnected()) {
            if (z) {
                this._mirroringSwitch.setVisibility(8);
                return;
            }
            return;
        }
        if (!z) {
            this._mirroringSwitch.setVisibility(0);
        }
        if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            if (isChecked) {
                return;
            }
            this._mirroringSwitch.setChecked(true);
        } else if (isChecked) {
            this._mirroringSwitch.setChecked(false);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this._listDtoInflater.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._listDtoInflater.get(i);
    }

    public void switchOffMirroring() {
        SwitchMaterial switchMaterial = this._mirroringSwitch;
        if (switchMaterial != null) {
            switchMaterial.setChecked(false);
        }
    }
}
