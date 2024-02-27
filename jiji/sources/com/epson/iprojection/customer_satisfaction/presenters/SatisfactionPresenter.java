package com.epson.iprojection.customer_satisfaction.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.ui.CSCustomDialog;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import com.google.android.material.button.MaterialButtonToggleGroup;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: SatisfactionPresenter.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\"\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0016J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0010H\u0016J\b\u0010 \u001a\u00020\u0012H\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/SatisfactionPresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "Lcom/google/android/material/button/MaterialButtonToggleGroup$OnButtonCheckedListener;", "Lcom/epson/iprojection/customer_satisfaction/presenters/SendActionInterface;", "context", "Landroid/content/Context;", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "inflater", "Landroid/view/LayoutInflater;", "(Landroid/content/Context;Landroidx/fragment/app/FragmentManager;Landroid/view/LayoutInflater;)V", "_cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "_dialog", "Lcom/epson/iprojection/customer_satisfaction/ui/CSCustomDialog;", "_usecaseCallback", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$CollectResultListener;", "collectSatisfaction", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "onButtonChecked", "group", "Lcom/google/android/material/button/MaterialButtonToggleGroup;", "checkedId", "", "isChecked", "", "onCancel", "onSend", "setCallback", "callback", "showSatisfactionDialog", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SatisfactionPresenter implements SatisfactionCollectInterface, MaterialButtonToggleGroup.OnButtonCheckedListener, SendActionInterface {
    private ECustomerSatisfaction _cs;
    private CSCustomDialog _dialog;
    private CSUsecaseInterface.CollectResultListener _usecaseCallback;
    private final Context context;
    private final FragmentManager fragmentManager;
    private final LayoutInflater inflater;

    public SatisfactionPresenter(Context context, FragmentManager fragmentManager, LayoutInflater inflater) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragmentManager, "fragmentManager");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.inflater = inflater;
    }

    @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
    public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean z) {
        Lg.d("checkedId = " + i);
        if (z) {
            switch (i) {
                case R.id.awesome /* 2131230826 */:
                    Lg.d("checked = AWESOME");
                    this._cs = ECustomerSatisfaction.AWESOME;
                    break;
                case R.id.fair /* 2131231082 */:
                    Lg.d("checked = FAIR");
                    this._cs = ECustomerSatisfaction.FAIR;
                    break;
                case R.id.poor /* 2131231376 */:
                    Lg.d("checked = POOR");
                    this._cs = ECustomerSatisfaction.POOR;
                    break;
                case R.id.unacceptable /* 2131231645 */:
                    Lg.d("checked = UNACCEPTABLE");
                    this._cs = ECustomerSatisfaction.UNACCEPTABLE;
                    break;
                case R.id.very_good /* 2131231655 */:
                    Lg.d("checked = VERY_GOOD");
                    this._cs = ECustomerSatisfaction.VERY_GOOD;
                    break;
            }
            CSCustomDialog cSCustomDialog = this._dialog;
            AlertDialog alertDialog = (AlertDialog) (cSCustomDialog != null ? cSCustomDialog.getDialog() : null);
            if (alertDialog != null) {
                alertDialog.getButton(-1).setEnabled(true);
            }
        }
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface
    public void collectSatisfaction(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(coroutineContext.plus(Dispatchers.getMain())), null, null, new SatisfactionPresenter$collectSatisfaction$1(this, null), 3, null);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface
    public void setCallback(CSUsecaseInterface.CollectResultListener callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this._usecaseCallback = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSatisfactionDialog() {
        View dialogView = this.inflater.inflate(R.layout.satisfaction_button_group, (ViewGroup) null);
        View findViewById = dialogView.findViewById(R.id.satisfaction_toggle_button_group);
        Intrinsics.checkNotNullExpressionValue(findViewById, "dialogView.findViewById(…tion_toggle_button_group)");
        ((MaterialButtonToggleGroup) findViewById).addOnButtonCheckedListener(this);
        String string = this.context.getString(R.string._ThankYouForUsingIProjection_);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.stri…kYouForUsingIProjection_)");
        String string2 = this.context.getString(R.string._Send_);
        Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.string._Send_)");
        String string3 = this.context.getString(R.string._Close_);
        Intrinsics.checkNotNullExpressionValue(string3, "context.getString(R.string._Close_)");
        Intrinsics.checkNotNullExpressionValue(dialogView, "dialogView");
        this._dialog = new CSCustomDialog(dialogView, this, string, string2, string3);
        Lg.d("cs dialog show");
        CSCustomDialog cSCustomDialog = this._dialog;
        Intrinsics.checkNotNull(cSCustomDialog);
        cSCustomDialog.show(this.fragmentManager, "satisfaction");
    }

    @Override // com.epson.iprojection.customer_satisfaction.presenters.SendActionInterface
    public void onSend() {
        CSUsecaseInterface.CollectResultListener collectResultListener;
        ECustomerSatisfaction eCustomerSatisfaction = this._cs;
        if (eCustomerSatisfaction == null || (collectResultListener = this._usecaseCallback) == null) {
            return;
        }
        Intrinsics.checkNotNull(eCustomerSatisfaction);
        collectResultListener.onCollectSucceed(eCustomerSatisfaction);
    }

    @Override // com.epson.iprojection.customer_satisfaction.presenters.SendActionInterface
    public void onCancel() {
        Lg.d("cs dialog onCancel");
        CSUsecaseInterface.CollectResultListener collectResultListener = this._usecaseCallback;
        if (collectResultListener != null) {
            collectResultListener.onCollectCanceled();
        }
    }
}
