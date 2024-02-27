package com.epson.iprojection.customer_satisfaction.gateways.testdouble;

import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: StubRepository.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0019\u001a\u00020\u0004H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0011\u0010\u001b\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0011\u0010\u001c\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0011\u0010\u0012\u001a\u00020\u0011H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0011\u0010\u0017\u001a\u00020\u0011H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0019\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0003\u001a\u00020\u0004H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\u0019\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\"J\u0019\u0010#\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\"J\u0019\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0011H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010&J\u0019\u0010'\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0011H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010&R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006("}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/gateways/testdouble/StubRepository;", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "()V", "date", "Ljava/time/LocalDate;", "getDate", "()Ljava/time/LocalDate;", "setDate", "(Ljava/time/LocalDate;)V", "isCollectedSatisfaction", "", "()Z", "setCollectedSatisfaction", "(Z)V", "isCollectedStoreReview", "setCollectedStoreReview", "tryCollectCount", "", "getTryCollectCount", "()I", "setTryCollectCount", "(I)V", "usedCount", "getUsedCount", "setUsedCount", "getCollectedLastDate", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getIsCollectedSatisfaction", "getIsCollectedStoreReview", "updateCollectedLastDays", "", "(Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedSatisfaction", "isCollected", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedStoreReview", "updateTryCollectCount", "count", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUsedCount", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class StubRepository implements CSStoreInterface {
    private LocalDate date;
    private boolean isCollectedSatisfaction;
    private boolean isCollectedStoreReview;
    private int tryCollectCount;
    private int usedCount;

    public StubRepository() {
        LocalDate MAX = LocalDate.MAX;
        Intrinsics.checkNotNullExpressionValue(MAX, "MAX");
        this.date = MAX;
    }

    public final int getUsedCount() {
        return this.usedCount;
    }

    public final void setUsedCount(int i) {
        this.usedCount = i;
    }

    public final boolean isCollectedStoreReview() {
        return this.isCollectedStoreReview;
    }

    public final void setCollectedStoreReview(boolean z) {
        this.isCollectedStoreReview = z;
    }

    public final boolean isCollectedSatisfaction() {
        return this.isCollectedSatisfaction;
    }

    public final void setCollectedSatisfaction(boolean z) {
        this.isCollectedSatisfaction = z;
    }

    public final int getTryCollectCount() {
        return this.tryCollectCount;
    }

    public final void setTryCollectCount(int i) {
        this.tryCollectCount = i;
    }

    public final LocalDate getDate() {
        return this.date;
    }

    public final void setDate(LocalDate localDate) {
        Intrinsics.checkNotNullParameter(localDate, "<set-?>");
        this.date = localDate;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object updateUsedCount(int i, Continuation<? super Unit> continuation) {
        this.usedCount = i;
        return Unit.INSTANCE;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object updateIsCollectedStoreReview(boolean z, Continuation<? super Unit> continuation) {
        this.isCollectedStoreReview = z;
        return Unit.INSTANCE;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object updateIsCollectedSatisfaction(boolean z, Continuation<? super Unit> continuation) {
        this.isCollectedSatisfaction = z;
        return Unit.INSTANCE;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object updateTryCollectCount(int i, Continuation<? super Unit> continuation) {
        this.tryCollectCount = i;
        return Unit.INSTANCE;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object updateCollectedLastDays(LocalDate localDate, Continuation<? super Unit> continuation) {
        this.date = localDate;
        return Unit.INSTANCE;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object getUsedCount(Continuation<? super Integer> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new StubRepository$getUsedCount$2(this, null), continuation);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object getIsCollectedStoreReview(Continuation<? super Boolean> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new StubRepository$getIsCollectedStoreReview$2(this, null), continuation);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object getIsCollectedSatisfaction(Continuation<? super Boolean> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new StubRepository$getIsCollectedSatisfaction$2(this, null), continuation);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object getTryCollectCount(Continuation<? super Integer> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new StubRepository$getTryCollectCount$2(this, null), continuation);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface
    public Object getCollectedLastDate(Continuation<? super LocalDate> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new StubRepository$getCollectedLastDate$2(this, null), continuation);
    }
}
