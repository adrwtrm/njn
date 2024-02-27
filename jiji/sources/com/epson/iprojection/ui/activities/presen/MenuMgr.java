package com.epson.iprojection.ui.activities.presen;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public class MenuMgr extends ModeratorMenuMgr {
    private static final int MENU_ID_COMP = 2131231273;
    private static final int MENU_ID_EDIT = 2131231274;
    private static final int MENU_ID_NOSELECTALL = 2131231275;
    private static final int MENU_ID_NUM = 5;
    private static final int MENU_ID_SELECTALL = 2131231276;
    private static final int MENU_ID_SORT = 2131231282;
    private final int[] IDs;
    private eType _eType;
    private final IOnClickMenuButtonListener _impl;
    private final ThumbMgr _thumbMgr;

    public MenuMgr(PjConnectableActivity pjConnectableActivity, IOnClickMenuButtonListener iOnClickMenuButtonListener, ThumbMgr thumbMgr) {
        super(pjConnectableActivity);
        this.IDs = r2;
        this._impl = iOnClickMenuButtonListener;
        int[] iArr = {R.id.menu_presen_sort, R.id.menu_deliver_edit, R.id.menu_deliver_complete, R.id.menu_deliver_selectall, R.id.menu_deliver_noselectall};
        this._thumbMgr = thumbMgr;
    }

    public void setType(eType etype) {
        this._eType = etype;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.presen_menu, menu);
        super.onCreateOptionsMenu(activity, menu);
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean onPrepareOptionsMenu = super.onPrepareOptionsMenu(menu);
        for (int i : this.IDs) {
            menu.findItem(i).setVisible(false);
        }
        if (this._isAvailable) {
            int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$presen$eType[this._eType.ordinal()];
            if (i2 == 1) {
                ThumbMgr thumbMgr = this._thumbMgr;
                if (thumbMgr != null && thumbMgr.isEditMode()) {
                    menu.findItem(R.id.menu_deliver_complete).setVisible(true);
                    if (this._thumbMgr.isCheckedAll()) {
                        menu.findItem(R.id.menu_deliver_noselectall).setVisible(true);
                    } else {
                        menu.findItem(R.id.menu_deliver_selectall).setVisible(true);
                    }
                } else if (this._thumbMgr == null) {
                    return onPrepareOptionsMenu;
                } else {
                    menu.findItem(R.id.menu_deliver_edit).setVisible(true);
                }
            } else if (i2 != 2) {
                return onPrepareOptionsMenu;
            } else {
                menu.findItem(R.id.menu_presen_sort).setVisible(true);
            }
            return true;
        }
        return onPrepareOptionsMenu;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.presen.MenuMgr$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$presen$eType;

        static {
            int[] iArr = new int[eType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$presen$eType = iArr;
            try {
                iArr[eType.DELIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eType[eType.PHOTO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_presen_sort) {
            this._impl.onClickSort();
            return false;
        }
        switch (itemId) {
            case R.id.menu_deliver_complete /* 2131231273 */:
                ThumbMgr thumbMgr = this._thumbMgr;
                if (thumbMgr != null) {
                    thumbMgr.stopEditMode();
                }
                this._impl.onClickStopEdit();
                return false;
            case R.id.menu_deliver_edit /* 2131231274 */:
                ThumbMgr thumbMgr2 = this._thumbMgr;
                if (thumbMgr2 != null) {
                    thumbMgr2.startEditMode();
                }
                this._impl.onClickStartEdit();
                return false;
            case R.id.menu_deliver_noselectall /* 2131231275 */:
                ThumbMgr thumbMgr3 = this._thumbMgr;
                if (thumbMgr3 != null) {
                    thumbMgr3.uncheckAll();
                    return false;
                }
                return false;
            case R.id.menu_deliver_selectall /* 2131231276 */:
                ThumbMgr thumbMgr4 = this._thumbMgr;
                if (thumbMgr4 != null) {
                    thumbMgr4.checkAll();
                    return false;
                }
                return false;
            default:
                return false;
        }
    }
}
