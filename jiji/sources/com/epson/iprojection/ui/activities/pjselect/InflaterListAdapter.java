package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.D_ListItem;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class InflaterListAdapter extends BaseAdapter {
    public static final Object tagMoreInfo = "inflater_pjselect_list.more_info";
    private final Context _context;
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_ListItem> _listDtoInflater;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public InflaterListAdapter(Context context, ArrayList<D_ListItem> arrayList) {
        this._context = context;
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
    }

    /* loaded from: classes.dex */
    public static class ViewHolder {
        CheckBox _checkBox;
        TextView _ipAddress;
        ImageButton _moreInfo;
        TextView _pjName;
        TextView _txtIsUsing;

        private ViewHolder() {
            this._checkBox = null;
            this._pjName = null;
            this._ipAddress = null;
            this._txtIsUsing = null;
            this._moreInfo = null;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        D_ListItem d_ListItem = this._listDtoInflater.get(i);
        if (d_ListItem.getListType() != D_ListItem.listType.PROJECTOR) {
            return true;
        }
        boolean isSelectable = d_ListItem.getPjInfo().isPjTypeBusiness() ? d_ListItem.isSelectable() : true;
        if (Pj.getIns().isAllPjTypeBusinessSelectHome()) {
            if (!d_ListItem.getPjInfo().isPjTypeBusiness()) {
                isSelectable = d_ListItem.isSelectable();
            }
        } else if (Pj.getIns().isAllPjTypeHomeSelectHome() || Pj.getIns().isAllPjTypeSignageSelectHome()) {
            if (d_ListItem.getPjInfo().isPjTypeBusiness() && Pj.getIns().cannotConnectPjInSelectHome()) {
                isSelectable = false;
            }
            if (isSelectedNpOnlyAndMppOnly() && d_ListItem.getPjInfo().isPjTypeBusiness()) {
                isSelectable = false;
            }
        }
        if (Pj.getIns().isAllPjTypeBusinessSelectHome() || d_ListItem.getPjInfo().isPjTypeBusiness()) {
            if (isSelectedNpOnly() && d_ListItem.getPjInfo().isNotSupportNP) {
                isSelectable = false;
            }
            if (isSelectedMppOnly() && d_ListItem.getPjInfo().isNotSupportMPP()) {
                isSelectable = false;
            }
        }
        if (Pj.getIns().getSelectedPj().size() < 16 || d_ListItem.isChecked()) {
            return isSelectable;
        }
        return false;
    }

    private boolean isSelectedNpOnly() {
        if (Pj.getIns().getSelectedPj().size() == 0) {
            return false;
        }
        Iterator<ConnectPjInfo> it = Pj.getIns().getSelectedPj().iterator();
        while (it.hasNext()) {
            if (!it.next().getPjInfo().bSupportedMPP) {
                return true;
            }
        }
        return false;
    }

    private boolean isSelectedMppOnly() {
        if (Pj.getIns().getSelectedPj().size() == 0) {
            return false;
        }
        Iterator<ConnectPjInfo> it = Pj.getIns().getSelectedPj().iterator();
        while (it.hasNext()) {
            if (it.next().getPjInfo().isNotSupportNP) {
                return true;
            }
        }
        return false;
    }

    private boolean isSelectedNpOnlyAndMppOnly() {
        Iterator<ConnectPjInfo> it = Pj.getIns().getSelectedPj().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (next.getPjInfo().isNotSupportNP) {
                z2 = true;
            } else if (!next.getPjInfo().bSupportedMPP) {
                z = true;
            }
        }
        return z && z2;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (this._listDtoInflater.get(i).getListType() == D_ListItem.listType.PROJECTOR) {
            return createPjItem(i, viewGroup);
        }
        return createFixedItem(i);
    }

    private View createPjItem(final int i, final ViewGroup viewGroup) {
        D_ListItem d_ListItem = this._listDtoInflater.get(i);
        D_PjInfo pjInfoByIpAndName = Pj.getIns().getPjInfoByIpAndName(d_ListItem.getPjInfo().PrjName, NetUtils.cvtIPAddr(d_ListItem.getPjInfo().IPAddr));
        if (pjInfoByIpAndName != null) {
            d_ListItem.update(pjInfoByIpAndName);
        }
        View inflate = this._layoutInflater.inflate(R.layout.inflater_pjselect_list, (ViewGroup) null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder._checkBox = (CheckBox) inflate.findViewById(R.id.ckb_list);
        viewHolder._pjName = (TextView) inflate.findViewById(R.id.txt_pjname);
        viewHolder._ipAddress = (TextView) inflate.findViewById(R.id.txt_ipaddress);
        viewHolder._txtIsUsing = (TextView) inflate.findViewById(R.id.txt_isUsing);
        viewHolder._moreInfo = (ImageButton) inflate.findViewById(R.id.more_info);
        viewHolder._moreInfo.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.InflaterListAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InflaterListAdapter.lambda$createPjItem$0(viewGroup, i, view);
            }
        });
        inflate.setTag(viewHolder);
        viewHolder._pjName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
        viewHolder._ipAddress.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
        viewHolder._txtIsUsing.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
        viewHolder._checkBox.setChecked(d_ListItem.isChecked());
        if (new ConnectConfig(this._context).isSelectMultiple()) {
            viewHolder._checkBox.setVisibility(0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder._pjName.getLayoutParams();
            marginLayoutParams.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftMult), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            viewHolder._pjName.setLayoutParams(marginLayoutParams);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewHolder._ipAddress.getLayoutParams();
            marginLayoutParams2.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.IpAddrMarginLeftMult), marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
            viewHolder._ipAddress.setLayoutParams(marginLayoutParams2);
        } else {
            viewHolder._checkBox.setVisibility(8);
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) viewHolder._pjName.getLayoutParams();
            marginLayoutParams3.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftSingle), marginLayoutParams3.topMargin, marginLayoutParams3.rightMargin, marginLayoutParams3.bottomMargin);
            viewHolder._pjName.setLayoutParams(marginLayoutParams3);
            ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) viewHolder._ipAddress.getLayoutParams();
            marginLayoutParams4.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.IpAddrMarginLeftSingle), marginLayoutParams4.topMargin, marginLayoutParams4.rightMargin, marginLayoutParams4.bottomMargin);
            viewHolder._ipAddress.setLayoutParams(marginLayoutParams4);
        }
        viewHolder._pjName.setText(d_ListItem.getDisplayText());
        viewHolder._ipAddress.setText(d_ListItem.getIpAddress());
        viewHolder._txtIsUsing.setText(d_ListItem.getPjInfo().getStatusString(this._context));
        if (!isEnabled(i)) {
            inflate.setEnabled(false);
            viewHolder._pjName.setEnabled(false);
            viewHolder._pjName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
            viewHolder._txtIsUsing.setEnabled(false);
            viewHolder._txtIsUsing.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
            viewHolder._ipAddress.setEnabled(false);
            viewHolder._ipAddress.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
        } else {
            inflate.setEnabled(true);
            viewHolder._pjName.setEnabled(true);
            viewHolder._pjName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
            viewHolder._txtIsUsing.setEnabled(true);
            viewHolder._txtIsUsing.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
            viewHolder._ipAddress.setEnabled(true);
            viewHolder._ipAddress.setTextColor(MethodUtil.compatGetColor(this._context, R.color.FontSub));
        }
        return inflate;
    }

    public static /* synthetic */ void lambda$createPjItem$0(ViewGroup viewGroup, int i, View view) {
        view.setTag(tagMoreInfo);
        ((ListView) viewGroup).performItemClick(view, i, view.getId());
    }

    private View createFixedItem(int i) {
        View inflate = this._layoutInflater.inflate(R.layout.inflater_pjselect_list_other, (ViewGroup) null);
        inflate.setTag(inflate.findViewById(R.id.fixed_list_txt));
        TextView textView = (TextView) inflate.findViewById(R.id.fixed_list_txt);
        textView.setText(this._listDtoInflater.get(i).getDisplayText());
        View findViewById = inflate.findViewById(R.id.adjust_checkbox_view);
        if (new ConnectConfig(this._context).isSelectMultiple()) {
            findViewById.setVisibility(0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            marginLayoutParams.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftMult), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            textView.setLayoutParams(marginLayoutParams);
        } else {
            findViewById.setVisibility(8);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            marginLayoutParams2.setMargins(this._context.getResources().getDimensionPixelSize(R.dimen.PjNameMarginLeftSingle), marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
            textView.setLayoutParams(marginLayoutParams2);
        }
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
