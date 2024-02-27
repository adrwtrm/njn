package com.epson.iprojection.ui.activities.pjselect.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.engine_wrapper.Pj;
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
        CheckBox _chkBox;
        TextView _date;
        TextView _ipAddr;
        TextView _pjName;

        private ViewHolder() {
            this._chkBox = null;
            this._pjName = null;
            this._ipAddr = null;
            this._date = null;
        }
    }

    private boolean shouldBeDisabled(boolean z) {
        return Pj.getIns().getNumSelectedPjFromHistory() >= 4 && !z;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.layoutInflater.inflate(R.layout.inflater_conpj_other_history, (ViewGroup) null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder._chkBox = (CheckBox) inflate.findViewById(R.id.ckb_history_list);
        viewHolder._pjName = (TextView) inflate.findViewById(R.id.txt_conPj_other_history_pjName);
        viewHolder._ipAddr = (TextView) inflate.findViewById(R.id.txt_conPj_other_history_ipAddress);
        viewHolder._date = (TextView) inflate.findViewById(R.id.txt_conPj_other_history_date);
        inflate.setTag(viewHolder);
        viewHolder._pjName.setText(this.listDtoInflater.get(i).getPjName());
        viewHolder._ipAddr.setText(this.listDtoInflater.get(i).getipAddr());
        viewHolder._date.setText(this.listDtoInflater.get(i).getDate());
        viewHolder._chkBox.setChecked(Pj.getIns().isConnPjFromHistory(this.listDtoInflater.get(i).getipAddr(), this.listDtoInflater.get(i).getPjName()));
        if (new ConnectConfig(this._context).isSelectMultiple()) {
            viewHolder._chkBox.setVisibility(0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder._pjName.getLayoutParams();
            marginLayoutParams.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftMult), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            viewHolder._pjName.setLayoutParams(marginLayoutParams);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewHolder._ipAddr.getLayoutParams();
            marginLayoutParams2.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.IpAddrMarginLeftMult), marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
            viewHolder._ipAddr.setLayoutParams(marginLayoutParams2);
        } else {
            viewHolder._chkBox.setVisibility(8);
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) viewHolder._pjName.getLayoutParams();
            marginLayoutParams3.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftSingle), marginLayoutParams3.topMargin, marginLayoutParams3.rightMargin, marginLayoutParams3.bottomMargin);
            viewHolder._pjName.setLayoutParams(marginLayoutParams3);
            ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) viewHolder._ipAddr.getLayoutParams();
            marginLayoutParams4.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.IpAddrMarginLeftSingle), marginLayoutParams4.topMargin, marginLayoutParams4.rightMargin, marginLayoutParams4.bottomMargin);
            viewHolder._ipAddr.setLayoutParams(marginLayoutParams4);
        }
        if (shouldBeDisabled(viewHolder._chkBox.isChecked())) {
            viewHolder._pjName.setEnabled(false);
            viewHolder._pjName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
            viewHolder._ipAddr.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
            viewHolder._date.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
        } else {
            viewHolder._pjName.setEnabled(true);
            viewHolder._pjName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
            viewHolder._ipAddr.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
            viewHolder._date.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
        }
        return inflate;
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
