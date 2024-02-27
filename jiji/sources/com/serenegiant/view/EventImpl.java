package com.serenegiant.view;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Event.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\fJ\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0016\u0010\u0003\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lcom/serenegiant/view/EventImpl;", "T", "Lcom/serenegiant/view/Event;", "value", "(Ljava/lang/Object;)V", "consumed", "", "getConsumed", "()Z", "setConsumed", "(Z)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/serenegiant/view/EventImpl;", "equals", "other", "", "hashCode", "", "toString", "", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class EventImpl<T> implements Event<T> {
    private boolean consumed;
    private final T value;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ EventImpl copy$default(EventImpl eventImpl, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = eventImpl.getValue();
        }
        return eventImpl.copy(obj);
    }

    public final T component1() {
        return getValue();
    }

    public final EventImpl<T> copy(T t) {
        return new EventImpl<>(t);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EventImpl) && Intrinsics.areEqual(getValue(), ((EventImpl) obj).getValue());
    }

    public int hashCode() {
        if (getValue() == null) {
            return 0;
        }
        return getValue().hashCode();
    }

    public String toString() {
        return "EventImpl(value=" + getValue() + ')';
    }

    public EventImpl(T t) {
        this.value = t;
    }

    @Override // com.serenegiant.view.Event
    public T getValue() {
        return this.value;
    }

    public final boolean getConsumed() {
        return this.consumed;
    }

    public final void setConsumed(boolean z) {
        this.consumed = z;
    }
}
