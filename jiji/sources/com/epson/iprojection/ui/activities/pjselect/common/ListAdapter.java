package com.epson.iprojection.ui.activities.pjselect.common;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.dialogs.ProjectorDetailDialog;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ListAdapter extends BaseAdapter {
    private final Context _context;
    private final ArrayList<ConnectPjInfo> _list;
    private final Handler _handler = new Handler();
    private ProjectorDetailDialog _detailDialog = null;

    public ListAdapter(Context context, ArrayList<ConnectPjInfo> arrayList) {
        this._context = context;
        this._list = arrayList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<ConnectPjInfo> arrayList = this._list;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._list.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return this._list.get(i).getPjInfo().ProjectorID;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.row_home_unregisted_pjlist, viewGroup, false);
        }
        final ConnectPjInfo pj = getPj(i);
        TextView textView = (TextView) view.findViewById(R.id.txt_pjname);
        if (pj == null) {
            textView.setText("");
        } else {
            textView.setText(pj.getPjInfo().PrjName);
            TextView textView2 = (TextView) view.findViewById(R.id.txt_status);
            ((ImageButton) view.findViewById(R.id.btn_info)).setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.common.ListAdapter$$ExternalSyntheticLambda0
                {
                    ListAdapter.this = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ListAdapter.this.m132xd6564bfe(pj, view2);
                }
            });
            if (!Pj.getIns().isConnected()) {
                textView2.setText(pj.getPjInfo().getStatusString(this._context));
                int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$ConnectPjInfo$eStatus[pj.getStatus().ordinal()];
                if (i2 == 1) {
                    textView2.setText(this._context.getString(R.string._Standby_));
                    textView.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
                    textView2.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
                } else if (i2 == 2) {
                    textView2.setText(this._context.getString(R.string._NotAvailable_));
                    textView.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
                    textView2.setTextColor(MethodUtil.compatGetColor(this._context, R.color.GrayOut));
                } else {
                    textView.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
                    textView2.setTextColor(MethodUtil.compatGetColor(this._context, R.color.Font));
                }
            }
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$getView$0$com-epson-iprojection-ui-activities-pjselect-common-ListAdapter  reason: not valid java name */
    public /* synthetic */ void m132xd6564bfe(ConnectPjInfo connectPjInfo, View view) {
        ProjectorDetailDialog projectorDetailDialog = this._detailDialog;
        if (projectorDetailDialog == null || !projectorDetailDialog.isShowing()) {
            this._detailDialog = new ProjectorDetailDialog(this._context);
            D_PjInfo pjInfoByIp = Pj.getIns().getPjInfoByIp(NetUtils.cvtIPAddr(connectPjInfo.getPjInfo().IPAddr));
            if (pjInfoByIp != null) {
                connectPjInfo.setPjInfo(pjInfoByIp);
                this._detailDialog.show(pjInfoByIp);
                return;
            }
            this._detailDialog.show(connectPjInfo.getPjInfo());
        }
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.common.ListAdapter$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$ConnectPjInfo$eStatus;

        static {
            int[] iArr = new int[ConnectPjInfo.eStatus.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$ConnectPjInfo$eStatus = iArr;
            try {
                iArr[ConnectPjInfo.eStatus.standby.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$ConnectPjInfo$eStatus[ConnectPjInfo.eStatus.unavailable.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private ConnectPjInfo getRegisteredPj(int i) {
        if (this._list.size() <= i) {
            return null;
        }
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (Arrays.equals(this._list.get(i).getPjInfo().UniqInfo, next.getPjInfo().UniqInfo)) {
                return next;
            }
        }
        return null;
    }

    private ConnectPjInfo getPj(int i) {
        if (this._list.size() <= i) {
            return null;
        }
        if (Pj.getIns().isConnected()) {
            Iterator<ConnectPjInfo> it = Pj.getIns().getNowConnectingPJList().iterator();
            while (it.hasNext()) {
                ConnectPjInfo next = it.next();
                if (Arrays.equals(this._list.get(i).getPjInfo().UniqInfo, next.getPjInfo().UniqInfo)) {
                    return next;
                }
            }
            return null;
        }
        return getRegisteredPj(i);
    }
}
