package com.epson.iprojection.customer_satisfaction.usecases.store;

import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: CSStoreInterface.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0005\u001a\u00020\u0006H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\u0006H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0011\u0010\b\u001a\u00020\tH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0011\u0010\n\u001a\u00020\tH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0004J\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0006H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0006H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u0019\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\tH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\tH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "", "getCollectedLastDate", "Ljava/time/LocalDate;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getIsCollectedSatisfaction", "", "getIsCollectedStoreReview", "getTryCollectCount", "", "getUsedCount", "updateCollectedLastDays", "", "date", "(Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedSatisfaction", "isCollected", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateIsCollectedStoreReview", "updateTryCollectCount", "count", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUsedCount", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface CSStoreInterface {
    Object getCollectedLastDate(Continuation<? super LocalDate> continuation);

    Object getIsCollectedSatisfaction(Continuation<? super Boolean> continuation);

    Object getIsCollectedStoreReview(Continuation<? super Boolean> continuation);

    Object getTryCollectCount(Continuation<? super Integer> continuation);

    Object getUsedCount(Continuation<? super Integer> continuation);

    Object updateCollectedLastDays(LocalDate localDate, Continuation<? super Unit> continuation);

    Object updateIsCollectedSatisfaction(boolean z, Continuation<? super Unit> continuation);

    Object updateIsCollectedStoreReview(boolean z, Continuation<? super Unit> continuation);

    Object updateTryCollectCount(int i, Continuation<? super Unit> continuation);

    Object updateUsedCount(int i, Continuation<? super Unit> continuation);
}
