package com.epson.iprojection.ui.activities.support.howto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.epson.iprojection.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InflaterListAdapter extends BaseAdapter {
    public static final int ID_DELIVERIES = 6;
    public static final int ID_MIRRORING = 3;
    public static final int ID_MODERATOR = 7;
    public static final int ID_NOT_FIND = 1;
    public static final int ID_SPACE1 = 0;
    public static final int ID_SPACE2 = 4;
    public static final int ID_TOUCHPAD = 2;
    public static final int ID_USER_CTRL = 5;
    public static final int ID_WARNING = 8;
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_ListItem> _listDtoInflater;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterListAdapter(Context context, ArrayList<D_ListItem> arrayList) {
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (i) {
            case 0:
            case 4:
                view = this._layoutInflater.inflate(R.layout.row_support_unclickable, (ViewGroup) null);
                break;
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                view = this._layoutInflater.inflate(R.layout.row_support_clickable, (ViewGroup) null);
                break;
            case 8:
                view = this._layoutInflater.inflate(R.layout.row_support_unclickable_warning, (ViewGroup) null);
                break;
        }
        ((TextView) view.findViewById(R.id.txt_name)).setText(this._listDtoInflater.get(i)._txt);
        return view;
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
