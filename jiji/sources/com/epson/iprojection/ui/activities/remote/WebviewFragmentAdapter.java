package com.epson.iprojection.ui.activities.remote;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class WebviewFragmentAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<FragmentPjControlBase> _fragmentList;
    FragmentManager _fragmentManager;

    public WebviewFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this._fragmentList = new ArrayList<>();
        this._fragmentManager = fragmentManager;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public FragmentPjControlBase getItem(int i) {
        if (this._fragmentList.size() == 0 || this._fragmentList.size() <= i) {
            return null;
        }
        return this._fragmentList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return this._fragmentList.contains((Fragment) obj) ? -1 : -2;
    }

    public ArrayList<FragmentPjControlBase> getFragmentList() {
        return this._fragmentList;
    }

    public void addFragment(FragmentPjControlBase fragmentPjControlBase) {
        if (findPjOnFragmentList(fragmentPjControlBase.getPjInfo()) == null) {
            this._fragmentList.add(fragmentPjControlBase);
            fragmentPjControlBase.resume();
            callNotifyDataSetChanged();
        }
    }

    public void updateFragment(FragmentPjControlBase fragmentPjControlBase, int i) {
        Iterator<FragmentPjControlBase> it = this._fragmentList.iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            if (Arrays.equals(next.getPjInfo().macAddr, fragmentPjControlBase.getPjInfo().macAddr)) {
                removeFragment(next);
                this._fragmentList.set(i, fragmentPjControlBase);
                callNotifyDataSetChanged();
                return;
            }
        }
    }

    public int getIndex(FragmentPjControlBase fragmentPjControlBase) {
        return this._fragmentList.indexOf(fragmentPjControlBase);
    }

    public int getIndex(byte[] bArr) {
        if (bArr == null) {
            return getIndexOfBatch();
        }
        for (int i = 0; i < this._fragmentList.size(); i++) {
            if (Arrays.equals(this._fragmentList.get(i).getPjInfo().macAddr, bArr)) {
                return i;
            }
        }
        return 0;
    }

    public int getIndexOfBatch() {
        for (int i = 0; i < this._fragmentList.size(); i++) {
            if (this._fragmentList.get(i) instanceof FragmentPjControlBatch) {
                return i;
            }
        }
        return 0;
    }

    public FragmentPjControlBatch getFragmentOfBatch() {
        for (int i = 0; i < this._fragmentList.size(); i++) {
            if (this._fragmentList.get(i) instanceof FragmentPjControlBatch) {
                return (FragmentPjControlBatch) this._fragmentList.get(i);
            }
        }
        return null;
    }

    public FragmentPjControlBase findPjOnFragmentList(D_HistoryInfo d_HistoryInfo) {
        Iterator<FragmentPjControlBase> it = this._fragmentList.iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            if (next.getPjInfo() != null && d_HistoryInfo != null && Arrays.equals(next.getPjInfo().macAddr, d_HistoryInfo.macAddr)) {
                return next;
            }
        }
        return null;
    }

    public boolean isExistOnFragmentList(FragmentPjControlBase fragmentPjControlBase) {
        Iterator<FragmentPjControlBase> it = this._fragmentList.iterator();
        while (it.hasNext()) {
            if (it.next() == fragmentPjControlBase) {
                return true;
            }
        }
        return false;
    }

    public void removeFragment(FragmentPjControlBase fragmentPjControlBase) {
        FragmentPjControlBase findPjOnFragmentList = findPjOnFragmentList(fragmentPjControlBase.getPjInfo());
        if (findPjOnFragmentList == null || findPjOnFragmentList.getFragmentManager() == null) {
            return;
        }
        this._fragmentManager.beginTransaction().remove(findPjOnFragmentList).commitAllowingStateLoss();
        callNotifyDataSetChanged();
    }

    public void removeAllFragment(ViewGroup viewGroup) {
        Iterator<FragmentPjControlBase> it = this._fragmentList.iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            destroyItem(viewGroup, getIndex(next), (Object) next);
            removeFragment(next);
        }
        this._fragmentList.clear();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this._fragmentList.size();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        FragmentManager fragmentManager;
        try {
            super.destroyItem(viewGroup, i, obj);
            if (i > getCount() || (fragmentManager = ((Fragment) obj).getFragmentManager()) == null) {
                return;
            }
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.remove((Fragment) obj);
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception unused) {
        }
    }

    private void callNotifyDataSetChanged() {
        try {
            notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }
}
