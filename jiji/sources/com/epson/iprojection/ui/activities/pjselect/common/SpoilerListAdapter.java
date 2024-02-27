package com.epson.iprojection.ui.activities.pjselect.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class SpoilerListAdapter extends BaseAdapter {
    private final Context _context;
    private final boolean _isOpened;

    @Override // android.widget.Adapter
    public int getCount() {
        return 1;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public SpoilerListAdapter(Context context, boolean z) {
        this._isOpened = z;
        this._context = context;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        String format;
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.row_home_list_spoiler, viewGroup, false);
        }
        if (Pj.getIns().isConnected()) {
            format = String.format(this._context.getString(R.string._ConnectedMultiProjectors_), Integer.valueOf(Pj.getIns().getNowConnectingPJList().size()));
        } else {
            format = String.format(this._context.getString(R.string._RegisteredMultiProjectors_), Integer.valueOf(Pj.getIns().getRegisteredPjList().size()));
        }
        ((TextView) view.findViewById(R.id.txt_explanation)).setText(format);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_spoiler);
        if (this._isOpened) {
            imageView.setImageResource(R.drawable.ic_expand_less);
        } else {
            imageView.setImageResource(R.drawable.ic_expand_more);
        }
        return view;
    }
}
