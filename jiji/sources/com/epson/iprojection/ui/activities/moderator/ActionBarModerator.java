package com.epson.iprojection.ui.activities.moderator;

import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeViewBtn;
import com.epson.iprojection.ui.activities.pjselect.TabType;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.layouts.LayoutType;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class ActionBarModerator extends BaseCustomActionBar implements View.OnClickListener {
    protected boolean _btnVisible;
    protected CheckBox _cbChangeView;
    protected BaseDialog _dialog;
    protected IOnChangeViewBtn _implChangeView;
    protected IOnClickAppIconButton _implIcon;
    protected boolean _isThumbnailView;
    protected LayoutType _layoutType;
    protected TabType _tabType;

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setFlag_sendsImgWhenConnect() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void updateTopBarGroup() {
    }

    public ActionBarModerator(AppCompatActivity appCompatActivity, boolean z) {
        super(appCompatActivity);
        this._cbChangeView = null;
        this._dialog = null;
        this._btnVisible = true;
        this._tabType = TabType.Select;
        this._layoutType = LayoutType.PJSelect;
        this._implChangeView = null;
        this._isThumbnailView = z;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setOnClickAppIconButton(IOnClickAppIconButton iOnClickAppIconButton) {
        this._implIcon = iOnClickAppIconButton;
    }

    public void setOnClickChangeViewButton(IOnChangeViewBtn iOnChangeViewBtn) {
        this._implChangeView = iOnChangeViewBtn;
    }

    public void layoutBtn() {
        super.layout(R.layout.toolbar_moderator);
        CheckBox checkBox = (CheckBox) this._activity.findViewById(R.id.checkbox_multictrl_thumbnail);
        this._cbChangeView = checkBox;
        checkBox.setOnClickListener(this);
        this._cbChangeView.setChecked(this._isThumbnailView);
        if (Pj.getIns().isModerator()) {
            enable();
        } else {
            disable();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        IOnChangeViewBtn iOnChangeViewBtn;
        if (view != this._cbChangeView || (iOnChangeViewBtn = this._implChangeView) == null) {
            return;
        }
        iOnChangeViewBtn.onChangeThumbnailAndList();
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.THUMBNAIL_DISPLAY);
        Analytics.getIns().sendEvent(eCustomEvent.THUMBNAIL_DISPLAY);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void disable() {
        CheckBox checkBox = this._cbChangeView;
        if (checkBox != null) {
            checkBox.setVisibility(8);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void enable() {
        if (this._cbChangeView != null) {
            if (Pj.getIns().hasMppThumbnail()) {
                this._cbChangeView.setVisibility(0);
            } else {
                this._cbChangeView.setVisibility(8);
            }
        }
    }

    public void visible() {
        this._btnVisible = true;
        if (this._cbChangeView != null) {
            if (Pj.getIns().isModerator()) {
                enable();
            } else {
                disable();
            }
        }
    }

    public void invisible() {
        this._btnVisible = false;
        CheckBox checkBox = this._cbChangeView;
        if (checkBox != null) {
            checkBox.setVisibility(8);
        }
    }

    public void setTabType(TabType tabType) {
        this._tabType = tabType;
    }

    /* renamed from: com.epson.iprojection.ui.activities.moderator.ActionBarModerator$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$LayoutType;

        static {
            int[] iArr = new int[LayoutType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$LayoutType = iArr;
            try {
                iArr[LayoutType.Other.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$LayoutType[LayoutType.ProfileMenu.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void setLayoutType(LayoutType layoutType) {
        this._layoutType = layoutType;
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$layouts$LayoutType[layoutType.ordinal()];
        if (i == 1 || i == 2) {
            invisible();
        } else {
            visible();
        }
        update();
    }
}
