package com.serenegiant.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Event.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a4\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\b\u001a)\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\n2\u0006\u0010\u000b\u001a\u0002H\u0002¢\u0006\u0002\u0010\f\u001a+\u0010\r\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\n2\u0006\u0010\u000b\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u0010\f*(\u0010\u000e\u001a\u0004\b\u0000\u0010\u0002\"\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u00032\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003*(\u0010\u000f\u001a\u0004\b\u0000\u0010\u0002\"\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\n2\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\n¨\u0006\u0010"}, d2 = {"observeEvent", "", "T", "Landroidx/lifecycle/LiveData;", "Lcom/serenegiant/view/Event;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "observer", "Landroidx/lifecycle/Observer;", "postValue", "Landroidx/lifecycle/MutableLiveData;", "value", "(Landroidx/lifecycle/MutableLiveData;Ljava/lang/Object;)V", "setValue", "LiveDataEvent", "MutableLiveDataEvent", "common_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class EventKt {
    public static /* synthetic */ void $r8$lambda$tTnahic9eYJ3OvhajqmliKItkw4(Observer observer, Event event) {
        m278observeEvent$lambda0(observer, event);
    }

    public static final <T> void observeEvent(LiveData<Event<T>> liveData, LifecycleOwner owner, final Observer<? super T> observer) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        liveData.observe(owner, new Observer() { // from class: com.serenegiant.view.EventKt$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                EventKt.$r8$lambda$tTnahic9eYJ3OvhajqmliKItkw4(Observer.this, (Event) obj);
            }
        });
    }

    /* renamed from: observeEvent$lambda-0 */
    public static final void m278observeEvent$lambda0(Observer observer, Event event) {
        Intrinsics.checkNotNullParameter(observer, "$observer");
        if (!(event instanceof EventImpl)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        EventImpl eventImpl = (EventImpl) event;
        if (eventImpl.getConsumed()) {
            return;
        }
        eventImpl.setConsumed(true);
        observer.onChanged(event.getValue());
    }

    public static final <T> void setValue(MutableLiveData<Event<T>> mutableLiveData, T t) {
        Intrinsics.checkNotNullParameter(mutableLiveData, "<this>");
        mutableLiveData.setValue(new EventImpl(t));
    }

    public static final <T> void postValue(MutableLiveData<Event<T>> mutableLiveData, T t) {
        Intrinsics.checkNotNullParameter(mutableLiveData, "<this>");
        mutableLiveData.postValue(new EventImpl(t));
    }
}
