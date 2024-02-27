package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import kotlin.Metadata;

/* compiled from: StateContract.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/IContextListener;", "", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/State;", "onFinished", "type", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$FinishType;", "reason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IContextListener {
    void changeState(State state);

    void onFinished(Define.FinishType finishType, Define.ConnectFailedReason connectFailedReason);
}
