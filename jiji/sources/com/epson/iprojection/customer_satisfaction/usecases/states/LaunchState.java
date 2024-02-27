package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: LaunchState.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/states/LaunchState;", "Lcom/epson/iprojection/customer_satisfaction/usecases/states/State;", "repository", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "(Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;Lkotlinx/coroutines/CoroutineScope;)V", "run", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LaunchState implements State {
    private final CoroutineScope coroutineScope;
    private final CSStoreInterface repository;

    public LaunchState(CSStoreInterface repository, CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
        this.repository = repository;
        this.coroutineScope = coroutineScope;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.states.State
    public void run() {
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new LaunchState$run$1(this, null), 3, null);
        Lg.d("cs launch state run finish");
    }
}
