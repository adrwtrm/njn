package com.epson.iprojection.ui.activities.web.menu.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InflaterListAdapter extends BaseAdapter {
    private final Context _context;
    private final LayoutInflater layoutInflater;
    private final ArrayList<DtoInflater> listDtoInflater;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterListAdapter(Context context, ArrayList<DtoInflater> arrayList) {
        this._context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.listDtoInflater = arrayList;
    }

    /* loaded from: classes.dex */
    private static class ViewHolder {
        ImageView _icon;
        TextView _title;

        private ViewHolder() {
            this._icon = null;
            this._title = null;
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.inflater_web_bkmk_iproj, (ViewGroup) null);
        }
        viewHolder._icon = (ImageView) view.findViewById(R.id.img_web_bkmk_iproj_bkmkname);
        viewHolder._title = (TextView) view.findViewById(R.id.txt_web_bkmk_iproj_bkmkname);
        view.setTag(viewHolder);
        viewHolder._icon.setImageResource(this.listDtoInflater.get(i).getIconResourceID());
        viewHolder._title.setText(this.listDtoInflater.get(i).getTitle());
        viewHolder._title.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
        return view;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.listDtoInflater.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.listDtoInflater.get(i);
    }
}
