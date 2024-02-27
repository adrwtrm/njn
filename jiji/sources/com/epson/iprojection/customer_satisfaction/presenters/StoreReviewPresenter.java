package com.epson.iprojection.customer_satisfaction.presenters;

import android.content.Context;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: StoreReviewPresenter.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/StoreReviewPresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "collectStoreReviews", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class StoreReviewPresenter implements StoreReviewCollectInterface {
    private final Context context;

    public StoreReviewPresenter(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface
    public void collectStoreReviews(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(coroutineContext.plus(Dispatchers.getMain())), null, null, new StoreReviewPresenter$collectStoreReviews$1(this, null), 3, null);
    }
}
