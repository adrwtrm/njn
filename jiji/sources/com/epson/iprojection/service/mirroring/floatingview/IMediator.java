package com.epson.iprojection.service.mirroring.floatingview;

import kotlin.Metadata;

/* compiled from: IMediator.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000e\u001a\u00020\u0003H&¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;", "", "addView", "", "view", "Lcom/epson/iprojection/service/mirroring/floatingview/FloatingView;", "onRotated", "reportActionUp", "reportMoved", "position", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "reportShowed", "reportTouched", "startOverlay", "stopOverlay", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IMediator {
    void addView(FloatingView floatingView);

    void onRotated();

    void reportActionUp(FloatingView floatingView);

    void reportMoved(FloatingView floatingView, Position position);

    void reportShowed(FloatingView floatingView, Position position);

    void reportTouched(FloatingView floatingView);

    void startOverlay();

    void stopOverlay();
}
