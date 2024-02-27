package com.epson.iprojection.ui.activities.pjselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class InflaterListProfileAdapter extends BaseAdapter implements Filterable {
    public static final Object tagMoreInfo = "inflater_pjselect_list_profile.more_info";
    private final LayoutInflater _layoutInflater;
    private ArrayList<D_ProfileItem> _listDtoInflater = arrayFilter("0");
    private final LinkedList<D_PjInfo> _listPjInfo;
    private final ArrayList<D_ProfileItem> _masterDtoInflater;
    private final RemovableView _rView;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return true;
    }

    public InflaterListProfileAdapter(RemovableView removableView, ArrayList<D_ProfileItem> arrayList, LinkedList<D_PjInfo> linkedList) {
        this._rView = removableView;
        this._layoutInflater = (LayoutInflater) removableView.getContext().getSystemService("layout_inflater");
        this._masterDtoInflater = new ArrayList<>(arrayList);
        this._listPjInfo = linkedList;
    }

    /* loaded from: classes.dex */
    private static class ViewHolder {
        CheckBox _checkBox;
        TextView _ipAddress;
        ImageButton _moreInfo;
        ImageView _nodeIcon;
        TextView _nodeName;
        TextView _projName;

        private ViewHolder() {
            this._nodeIcon = null;
            this._checkBox = null;
            this._nodeName = null;
            this._projName = null;
            this._ipAddress = null;
            this._moreInfo = null;
        }
    }

    public int getMasterParentDepth(int i) {
        for (int i2 = 0; i2 < this._masterDtoInflater.size(); i2++) {
            if (this._masterDtoInflater.get(i2)._depth.current == i) {
                return this._masterDtoInflater.get(i2)._depth.parent;
            }
        }
        return -1;
    }

    public String getParentNodeName(int i) {
        for (int i2 = 0; i2 < this._masterDtoInflater.size(); i2++) {
            if (this._masterDtoInflater.get(i2)._depth.current == i && this._masterDtoInflater.get(i2)._isFoler) {
                return this._masterDtoInflater.get(i2)._nodeName;
            }
        }
        return null;
    }

    public ArrayList<D_ProfileItem> arrayFilter(String str) {
        ArrayList<D_ProfileItem> arrayList = new ArrayList<>();
        for (int i = 0; i < this._masterDtoInflater.size(); i++) {
            if (this._masterDtoInflater.get(i)._depth.parent == Integer.parseInt(str)) {
                arrayList.add(this._masterDtoInflater.get(i));
            }
        }
        return arrayList;
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this._layoutInflater.inflate(R.layout.inflater_pjselect_list_profile, (ViewGroup) null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder._nodeIcon = (ImageView) view.findViewById(R.id.nodeImage);
        viewHolder._checkBox = (CheckBox) view.findViewById(R.id.ckb_list);
        viewHolder._nodeName = (TextView) view.findViewById(R.id.txt_nodename);
        viewHolder._projName = (TextView) view.findViewById(R.id.txt_pjname);
        viewHolder._ipAddress = (TextView) view.findViewById(R.id.txt_ipaddress);
        viewHolder._moreInfo = (ImageButton) view.findViewById(R.id.more_info);
        viewHolder._moreInfo.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.InflaterListProfileAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                InflaterListProfileAdapter.lambda$getView$0(viewGroup, i, view2);
            }
        });
        if (this._listDtoInflater.get(i)._isFoler) {
            viewHolder._nodeIcon.setVisibility(0);
            viewHolder._checkBox.setVisibility(4);
            viewHolder._nodeName.setVisibility(0);
            viewHolder._projName.setVisibility(4);
            viewHolder._ipAddress.setVisibility(4);
            viewHolder._moreInfo.setVisibility(4);
            viewHolder._nodeName.setText(this._listDtoInflater.get(i)._nodeName);
        } else {
            viewHolder._nodeIcon.setVisibility(4);
            viewHolder._checkBox.setVisibility(0);
            viewHolder._nodeName.setVisibility(4);
            viewHolder._projName.setVisibility(0);
            viewHolder._ipAddress.setVisibility(0);
            viewHolder._moreInfo.setVisibility(0);
            viewHolder._projName.setText(this._listDtoInflater.get(i)._nodeName + " (" + this._listDtoInflater.get(i)._projName + ")");
            viewHolder._ipAddress.setText(this._listDtoInflater.get(i)._ipAddress);
            viewHolder._checkBox.setChecked(Pj.getIns().isManualSearchPj(this._listPjInfo.get(this._listDtoInflater.get(i)._uniqueNum)));
        }
        return view;
    }

    public static /* synthetic */ void lambda$getView$0(ViewGroup viewGroup, int i, View view) {
        view.setTag(tagMoreInfo);
        ((ListView) viewGroup).performItemClick(view, i, view.getId());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this._listDtoInflater.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._listDtoInflater.get(i);
    }

    public void wrapNotifyDataSetChanged(boolean z) {
        RemovableView removableView;
        notifyDataSetChanged();
        if (!z || (removableView = this._rView) == null) {
            return;
        }
        removableView.invalidate();
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        return new Filter() { // from class: com.epson.iprojection.ui.activities.pjselect.InflaterListProfileAdapter.1
            {
                InflaterListProfileAdapter.this = this;
            }

            @Override // android.widget.Filter
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                if (filterResults != null) {
                    InflaterListProfileAdapter.this.wrapNotifyDataSetChanged(true);
                }
            }

            @Override // android.widget.Filter
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence == null) {
                    return null;
                }
                String obj = charSequence.toString();
                Filter.FilterResults filterResults = new Filter.FilterResults();
                InflaterListProfileAdapter inflaterListProfileAdapter = InflaterListProfileAdapter.this;
                inflaterListProfileAdapter._listDtoInflater = inflaterListProfileAdapter.arrayFilter(obj);
                filterResults.values = InflaterListProfileAdapter.this._listDtoInflater;
                filterResults.count = InflaterListProfileAdapter.this._listDtoInflater.size();
                return filterResults;
            }
        };
    }
}
