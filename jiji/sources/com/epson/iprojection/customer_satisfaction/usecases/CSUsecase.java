package com.epson.iprojection.customer_satisfaction.usecases;

import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.states.CollectingLaunchState;
import com.epson.iprojection.customer_satisfaction.usecases.states.CollectingUsedState;
import com.epson.iprojection.customer_satisfaction.usecases.states.EmptyState;
import com.epson.iprojection.customer_satisfaction.usecases.states.LaunchState;
import com.epson.iprojection.customer_satisfaction.usecases.states.State;
import com.epson.iprojection.customer_satisfaction.usecases.states.UsingState;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CSUsecase.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0013H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecase;", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$Controller;", "satisfactionPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "storeReviewPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "repository", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "analytics", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "assistance", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "(Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;Lkotlinx/coroutines/CoroutineScope;)V", "state", "Lcom/epson/iprojection/customer_satisfaction/usecases/states/State;", "onChangeState", "", "Lcom/epson/iprojection/customer_satisfaction/entities/EState;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSUsecase implements CSUsecaseInterface.Controller {
    private final CSAnalyticsInterface analytics;
    private final CSAssistanceInterface assistance;
    private final CoroutineScope coroutineScope;
    private final CSStoreInterface repository;
    private final SatisfactionCollectInterface satisfactionPresenter;
    private State state;
    private final StoreReviewCollectInterface storeReviewPresenter;

    /* compiled from: CSUsecase.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EState.values().length];
            try {
                iArr[EState.Empty.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EState.LAUNCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EState.USING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[EState.COLLECTING_LAUNCH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[EState.COLLECTING_USED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CSUsecase(SatisfactionCollectInterface satisfactionPresenter, StoreReviewCollectInterface storeReviewPresenter, CSStoreInterface repository, CSAnalyticsInterface analytics, CSAssistanceInterface assistance, CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(satisfactionPresenter, "satisfactionPresenter");
        Intrinsics.checkNotNullParameter(storeReviewPresenter, "storeReviewPresenter");
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(analytics, "analytics");
        Intrinsics.checkNotNullParameter(assistance, "assistance");
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
        this.satisfactionPresenter = satisfactionPresenter;
        this.storeReviewPresenter = storeReviewPresenter;
        this.repository = repository;
        this.analytics = analytics;
        this.assistance = assistance;
        this.coroutineScope = coroutineScope;
        this.state = new EmptyState();
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface.Controller
    public void onChangeState(EState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            this.state = new EmptyState();
        } else if (i == 2) {
            this.state = new LaunchState(this.repository, this.coroutineScope);
        } else if (i == 3) {
            this.state = new UsingState(this.repository, this.coroutineScope);
        } else if (i == 4) {
            this.state = new CollectingLaunchState(this.storeReviewPresenter, this.satisfactionPresenter, this.repository, this.analytics, this.assistance, this.coroutineScope);
        } else if (i == 5) {
            this.state = new CollectingUsedState(this.storeReviewPresenter, this.satisfactionPresenter, this.repository, this.analytics, this.assistance, this.coroutineScope);
        }
        this.state.run();
    }
}
