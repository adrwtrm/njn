package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ListNameAdapter extends BaseAdapter implements View.OnTouchListener {
    private final Context _context;
    private DataList _dataList = null;
    private final IOnDropListener _impl;
    private final IOnDragStateListener _implDragState;
    private final WindowMgr _windowMgr;

    /* loaded from: classes.dex */
    public static class ViewHolder {
        public TextView _name;
        public int _position;
        public boolean isDisconnected;
        public float lastTouchedX;
        public float lastTouchedY;
    }

    public ListNameAdapter(Context context, WindowMgr windowMgr, IOnDropListener iOnDropListener, IOnDragStateListener iOnDragStateListener) {
        this._context = context;
        this._windowMgr = windowMgr;
        this._impl = iOnDropListener;
        this._implDragState = iOnDragStateListener;
    }

    public void updateDataList(ArrayList<D_MppUserInfo> arrayList) {
        this._dataList = new DataList(arrayList);
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        D_MppUserInfo d_MppUserInfo = (D_MppUserInfo) getItem(i);
        if (view == null) {
            view = LayoutInflater.from(this._context).inflate(R.layout.list_mpp_row, (ViewGroup) null);
            view.setOnTouchListener(this);
            mySetOnDragListener(view);
            viewHolder = new ViewHolder();
            viewHolder._name = (TextView) view.findViewById(R.id.txt_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder._name.setText(d_MppUserInfo.userName);
        viewHolder._position = i;
        SelectableLinearLayout selectableLinearLayout = (SelectableLinearLayout) view;
        selectableLinearLayout.clear();
        selectableLinearLayout.setUniqueID(d_MppUserInfo.uniqueId);
        int i2 = 0;
        while (true) {
            if (i2 >= 4) {
                break;
            } else if (d_MppUserInfo.uniqueId != this._windowMgr.getUniqueID(i2)) {
                i2++;
            } else if (!d_MppUserInfo.disconnected) {
                selectableLinearLayout.setDisplay(false, this._windowMgr.isActive(i2));
            }
        }
        if (d_MppUserInfo.disconnected) {
            viewHolder._name.setTextColor(MethodUtil.compatGetColor(this._context, R.color.BlackGrayOut));
        } else {
            viewHolder._name.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        viewHolder.isDisconnected = d_MppUserInfo.disconnected;
        return view;
    }

    private void mySetOnDragListener(View view) {
        view.setOnDragListener(new View.OnDragListener() { // from class: com.epson.iprojection.ui.activities.moderator.ListNameAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                return ListNameAdapter.this.m99x6d615ef8(view2, dragEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$mySetOnDragListener$0$com-epson-iprojection-ui-activities-moderator-ListNameAdapter  reason: not valid java name */
    public /* synthetic */ boolean m99x6d615ef8(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 1) {
            this._implDragState.onStartDrag();
            return true;
        } else if (action != 2) {
            if (action != 3) {
                if (action == 4) {
                    this._implDragState.onEndDrag();
                }
                return false;
            }
            boolean isEnabledDrop = this._implDragState.isEnabledDrop();
            this._implDragState.onEndDrag();
            if (isEnabledDrop) {
                try {
                    this._impl.onDropFromWindowToList(((TouchPosGettableImageView) dragEvent.getLocalState()).getWindID());
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this._dataList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._dataList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        D_MppUserInfo d_MppUserInfo = this._dataList.get(i);
        if (d_MppUserInfo == null) {
            return -1L;
        }
        return d_MppUserInfo.uniqueId;
    }

    public D_MppUserInfo getUserInfoFromUniqueID(long j) {
        for (int i = 0; i < this._dataList.size(); i++) {
            D_MppUserInfo d_MppUserInfo = this._dataList.get(i);
            if (d_MppUserInfo.uniqueId == j) {
                return d_MppUserInfo;
            }
        }
        return null;
    }

    public int getPositionFromUniqueID(long j) {
        for (int i = 0; i < this._dataList.size(); i++) {
            if (this._dataList.get(i).uniqueId == j) {
                return i;
            }
        }
        return -1;
    }

    public Object getItem(long j) {
        return this._dataList.get(j);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.lastTouchedX = motionEvent.getX();
        viewHolder.lastTouchedY = motionEvent.getY();
        return false;
    }
}
