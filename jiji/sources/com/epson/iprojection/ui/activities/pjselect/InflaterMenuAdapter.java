package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.epson.iprojection.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InflaterMenuAdapter extends BaseAdapter {
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_ListItem> _listDtoInflater;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterMenuAdapter(Context context, ArrayList<D_ListItem> arrayList) {
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this._layoutInflater.inflate(R.layout.inflater_pjselect_list_profile_menu, (ViewGroup) null);
        inflate.setTag(inflate.findViewById(R.id.menu_list_txt));
        ((TextView) inflate.findViewById(R.id.menu_list_txt)).setText(this._listDtoInflater.get(i).getDisplayText());
        return inflate;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this._listDtoInflater.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._listDtoInflater.get(i);
    }
}
