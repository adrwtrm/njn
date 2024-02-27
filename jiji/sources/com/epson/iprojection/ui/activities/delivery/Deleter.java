package com.epson.iprojection.ui.activities.delivery;

import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.Intent;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: Deleter.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0003J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\fH\u0002J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0012H\u0002J\u0018\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/epson/iprojection/ui/activities/delivery/Deleter;", "", "_activity", "Landroid/app/Activity;", "_thumMgr", "Lcom/epson/iprojection/ui/activities/presen/thumbnails/ThumbMgr;", "_filer", "Lcom/epson/iprojection/ui/activities/presen/interfaces/IFiler;", "_impl", "Lcom/epson/iprojection/ui/activities/delivery/IDeleteCallback;", "(Landroid/app/Activity;Lcom/epson/iprojection/ui/activities/presen/thumbnails/ThumbMgr;Lcom/epson/iprojection/ui/activities/presen/interfaces/IFiler;Lcom/epson/iprojection/ui/activities/delivery/IDeleteCallback;)V", "_checkedN", "", "_deletedN", "_processIndex", "delete", "", "delete10orMore", "", FirebaseAnalytics.Param.INDEX, "delete9orLess", "finish", "isCanceled", "onActivityResult", "resultCode", "data", "Landroid/content/Intent;", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Deleter {
    public static final Companion Companion = new Companion(null);
    public static final int REQUESTCODE_DELETE = 997;
    private final Activity _activity;
    private int _checkedN;
    private int _deletedN;
    private final IFiler _filer;
    private final IDeleteCallback _impl;
    private int _processIndex;
    private final ThumbMgr _thumMgr;

    public Deleter(Activity _activity, ThumbMgr _thumMgr, IFiler _filer, IDeleteCallback _impl) {
        Intrinsics.checkNotNullParameter(_activity, "_activity");
        Intrinsics.checkNotNullParameter(_thumMgr, "_thumMgr");
        Intrinsics.checkNotNullParameter(_filer, "_filer");
        Intrinsics.checkNotNullParameter(_impl, "_impl");
        this._activity = _activity;
        this._thumMgr = _thumMgr;
        this._filer = _filer;
        this._impl = _impl;
    }

    public final void onActivityResult(int i, Intent intent) {
        if (i == -1) {
            DeliveryFileIO.getIns().delete(this._activity, this._filer.getUri(this._processIndex));
            this._processIndex++;
            this._deletedN++;
            delete();
            return;
        }
        finish(true);
    }

    public final void delete() {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new Deleter$delete$1(this, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void delete9orLess(int i) {
        if (DeliveryFileIO.getIns().delete(this._activity, this._filer.getUri(i))) {
            this._deletedN++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean delete10orMore(int i) {
        try {
            if (DeliveryFileIO.getIns().delete(this._activity, this._filer.getUri(i))) {
                this._deletedN++;
            }
        } catch (RecoverableSecurityException e) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new Deleter$delete10orMore$1(e, this, null), 2, null);
            return false;
        } catch (Exception unused) {
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finish(boolean z) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new Deleter$finish$1(z, this, null), 2, null);
    }

    /* compiled from: Deleter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/delivery/Deleter$Companion;", "", "()V", "REQUESTCODE_DELETE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
