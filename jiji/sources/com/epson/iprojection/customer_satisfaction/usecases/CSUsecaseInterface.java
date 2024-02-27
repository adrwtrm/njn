package com.epson.iprojection.customer_satisfaction.usecases;

import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import kotlin.Metadata;

/* compiled from: CSUsecaseInterface.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface;", "", "CollectResultListener", "Controller", "OnSatisfactionFeedbackFinishedListener", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface CSUsecaseInterface {

    /* compiled from: CSUsecaseInterface.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$CollectResultListener;", "", "onCollectCanceled", "", "onCollectSucceed", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface CollectResultListener {
        void onCollectCanceled();

        void onCollectSucceed(ECustomerSatisfaction eCustomerSatisfaction);
    }

    /* compiled from: CSUsecaseInterface.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$Controller;", "", "onChangeState", "", "state", "Lcom/epson/iprojection/customer_satisfaction/entities/EState;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface Controller {
        void onChangeState(EState eState);
    }

    /* compiled from: CSUsecaseInterface.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$OnSatisfactionFeedbackFinishedListener;", "", "onSatisfactionFeedbackFinished", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface OnSatisfactionFeedbackFinishedListener {
        void onSatisfactionFeedbackFinished();
    }
}
