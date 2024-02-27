package com.epson.iprojection.ui.activities.pjselect;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.epson.iprojection.R;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.common.ListAdapter;
import com.epson.iprojection.ui.activities.pjselect.common.SpoilerListAdapter;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentHomeBaseSelected extends FragmentHomeBase implements AdapterView.OnItemClickListener, IPjSearchStatusListener {
    protected ListAdapter _adapter;
    protected boolean _isSpoilerOpened = false;
    protected ListView _listPjs;
    protected ListView _listSpoiler;
    protected LinearLayout _panelRootLayout;
    protected ViewGroup _panelViews;
    protected ViewGroup _rootLayout;

    protected abstract void addPanelViews(LinearLayout linearLayout);

    protected abstract ArrayList<ConnectPjInfo> getPjList();

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjFind(D_PjInfo d_PjInfo, boolean z) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjLose(int i) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjStatusChanged(D_PjInfo d_PjInfo, boolean z) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onSearchPause() {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onSearchStart() {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        this._rootLayout = viewGroup;
        return viewGroup2;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._panelRootLayout.removeView(this._panelViews);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this._panelRootLayout.removeView(this._panelViews);
        addPanelViews(this._panelRootLayout);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ListView listView = this._listSpoiler;
        if (adapterView == listView) {
            this._isSpoilerOpened = !this._isSpoilerOpened;
            listView.setAdapter((android.widget.ListAdapter) new SpoilerListAdapter(getActivity(), this._isSpoilerOpened));
            updateListVisibility();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateListVisibility() {
        if (getPjList() != null && getPjList().size() > 1) {
            this._listSpoiler.setVisibility(0);
            if (this._isSpoilerOpened) {
                this._listPjs.setVisibility(0);
                setVisibleDivider();
                return;
            }
            this._listPjs.setVisibility(8);
            setInvisibleDivider();
            return;
        }
        this._listPjs.setVisibility(0);
        this._listSpoiler.setVisibility(8);
        setInvisibleDivider();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setupViews(View view) {
        this._adapter = new ListAdapter(getActivity(), getPjList());
        ListView listView = (ListView) view.findViewById(R.id.listPjs);
        this._listPjs = listView;
        listView.setAdapter((android.widget.ListAdapter) this._adapter);
        this._listPjs.setOnItemClickListener(this);
        ListView listView2 = (ListView) view.findViewById(R.id.listSpoiler);
        this._listSpoiler = listView2;
        listView2.setAdapter((android.widget.ListAdapter) new SpoilerListAdapter(getActivity(), this._isSpoilerOpened));
        this._listSpoiler.setOnItemClickListener(this);
    }

    private void setVisibleDivider() {
        View findViewById = this._rootLayout.findViewById(R.id.view_divider_up);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        View findViewById2 = this._rootLayout.findViewById(R.id.view_divider_bottom);
        if (findViewById2 != null) {
            findViewById2.setVisibility(0);
        }
    }

    private void setInvisibleDivider() {
        View findViewById = this._rootLayout.findViewById(R.id.view_divider_up);
        if (findViewById != null) {
            findViewById.setVisibility(4);
        }
        View findViewById2 = this._rootLayout.findViewById(R.id.view_divider_bottom);
        if (findViewById2 != null) {
            findViewById2.setVisibility(4);
        }
    }

    public void updateSpoilerView() {
        ListView listView = this._listSpoiler;
        if (listView != null) {
            listView.setAdapter((android.widget.ListAdapter) new SpoilerListAdapter(getActivity(), this._isSpoilerOpened));
        }
        ListAdapter listAdapter = this._adapter;
        if (listAdapter != null) {
            this._listPjs.setAdapter((android.widget.ListAdapter) listAdapter);
        }
        updateListVisibility();
    }
}
