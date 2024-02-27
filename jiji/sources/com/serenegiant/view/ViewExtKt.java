package com.serenegiant.view;

import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.google.android.material.snackbar.Snackbar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewExt.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a:\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a(\u0010\r\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002¨\u0006\u000f"}, d2 = {"setupSnackbar", "", "Landroid/view/View;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "snackbarEvent", "Landroidx/lifecycle/LiveData;", "Lcom/serenegiant/view/Event;", "", TypedValues.TransitionType.S_DURATION, "", "callback", "Lcom/google/android/material/snackbar/Snackbar$Callback;", "showSnackbar", "message", "common_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ViewExtKt {
    /* renamed from: $r8$lambda$d-J5wcGJydHVUdzFSNV9_2a2sbE */
    public static /* synthetic */ void m279$r8$lambda$dJ5wcGJydHVUdzFSNV9_2a2sbE(View view, int i, Snackbar.Callback callback, CharSequence charSequence) {
        m280setupSnackbar$lambda0(view, i, callback, charSequence);
    }

    public static /* synthetic */ void setupSnackbar$default(View view, LifecycleOwner lifecycleOwner, LiveData liveData, int i, Snackbar.Callback callback, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            callback = null;
        }
        setupSnackbar(view, lifecycleOwner, liveData, i, callback);
    }

    public static final void setupSnackbar(final View view, LifecycleOwner lifecycleOwner, LiveData<Event<CharSequence>> snackbarEvent, final int i, final Snackbar.Callback callback) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(snackbarEvent, "snackbarEvent");
        EventKt.observeEvent(snackbarEvent, lifecycleOwner, new Observer() { // from class: com.serenegiant.view.ViewExtKt$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ViewExtKt.m279$r8$lambda$dJ5wcGJydHVUdzFSNV9_2a2sbE(view, i, callback, (CharSequence) obj);
            }
        });
    }

    /* renamed from: setupSnackbar$lambda-0 */
    public static final void m280setupSnackbar$lambda0(View this_setupSnackbar, int i, Snackbar.Callback callback, CharSequence it) {
        Intrinsics.checkNotNullParameter(this_setupSnackbar, "$this_setupSnackbar");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        showSnackbar(this_setupSnackbar, it, i, callback);
    }

    static /* synthetic */ void showSnackbar$default(View view, CharSequence charSequence, int i, Snackbar.Callback callback, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            callback = null;
        }
        showSnackbar(view, charSequence, i, callback);
    }

    private static final void showSnackbar(View view, CharSequence charSequence, int i, Snackbar.Callback callback) {
        Snackbar make = Snackbar.make(view, charSequence, i);
        if (callback != null) {
            make.addCallback(callback);
        }
        make.show();
    }
}
