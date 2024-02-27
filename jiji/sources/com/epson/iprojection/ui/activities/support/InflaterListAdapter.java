package com.epson.iprojection.ui.activities.support;

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
    public static final int POS_ABOUT_APP = 6;
    public static final int POS_ABOUT_PROJECTORS = 3;
    public static final int POS_COPYRIGHT = 8;
    public static final int POS_HOWTO_USE_TITLE = 0;
    public static final int POS_LISENCE = 7;
    public static final int POS_MANUAL = 2;
    public static final int POS_PRIVACY = 10;
    public static final int POS_SETUP_NAVI = 5;
    public static final int POS_SUPPORTED_PROJECTORS_LIST = 4;
    public static final int POS_TRADEMARK = 9;
    public static final int POS_USAGE_TIPS = 1;
    public static final int POS_VERSION = 11;
    private final Context _context;
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_ListItem> _listDtoInflater;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterListAdapter(Context context, ArrayList<D_ListItem> arrayList) {
        this._context = context;
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (i) {
            case 0:
            case 3:
            case 6:
                view = this._layoutInflater.inflate(R.layout.row_support_unclickable, (ViewGroup) null);
                break;
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                view = this._layoutInflater.inflate(R.layout.row_support_clickable, (ViewGroup) null);
                break;
        }
        TextView textView = (TextView) view.findViewById(R.id.txt_name);
        if (textView != null) {
            textView.setText(this._listDtoInflater.get(i)._txt);
        }
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
