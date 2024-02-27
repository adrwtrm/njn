package com.epson.iprojection.service.mirroring.floatingview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.epson.iprojection.service.mirroring.floatingview.IColleague;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FloatingView.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0015\u0010!\u001a\u00020\b*\u00020\"8F¢\u0006\u0006\u001a\u0004\b#\u0010$R(\u0010!\u001a\u00020\b*\u00020\u00182\u0006\u0010%\u001a\u00020\b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b#\u0010&\"\u0004\b'\u0010(¨\u0006,"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/FloatingView;", "Lcom/epson/iprojection/service/mirroring/floatingview/IColleague;", "context", "Landroid/content/Context;", "type", "Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;)V", "_displayCenterPosition", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "get_displayCenterPosition", "()Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "_displaySize", "get_displaySize", "_inflater", "Landroid/view/LayoutInflater;", "get_inflater", "()Landroid/view/LayoutInflater;", "_mediator", "Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;", "get_mediator", "()Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;", "set_mediator", "(Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;)V", "_params", "Landroid/view/WindowManager$LayoutParams;", "get_params", "()Landroid/view/WindowManager$LayoutParams;", "_windowManager", "Landroid/view/WindowManager;", "get_windowManager", "()Landroid/view/WindowManager;", "getType", "()Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "position", "Landroid/view/MotionEvent;", "getPosition", "(Landroid/view/MotionEvent;)Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "value", "(Landroid/view/WindowManager$LayoutParams;)Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "setPosition", "(Landroid/view/WindowManager$LayoutParams;Lcom/epson/iprojection/service/mirroring/floatingview/Position;)V", "setMediator", "", "mediator", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class FloatingView implements IColleague {
    private final Position _displayCenterPosition;
    private final Position _displaySize;
    private final LayoutInflater _inflater;
    private IMediator _mediator;
    private final WindowManager.LayoutParams _params;
    private final WindowManager _windowManager;
    private final IColleague.ViewType type;

    public FloatingView(Context context, IColleague.ViewType type) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this._windowManager = (WindowManager) systemService;
        Object systemService2 = context.getSystemService("layout_inflater");
        Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.view.LayoutInflater");
        this._inflater = (LayoutInflater) systemService2;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        layoutParams.gravity = 17;
        this._params = layoutParams;
        Position position = new Position(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
        this._displaySize = position;
        float f = 2;
        this._displayCenterPosition = new Position(position.getFx() / f, position.getFy() / f);
    }

    public final IColleague.ViewType getType() {
        return this.type;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final IMediator get_mediator() {
        return this._mediator;
    }

    protected final void set_mediator(IMediator iMediator) {
        this._mediator = iMediator;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final WindowManager get_windowManager() {
        return this._windowManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final LayoutInflater get_inflater() {
        return this._inflater;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final WindowManager.LayoutParams get_params() {
        return this._params;
    }

    public final Position getPosition(WindowManager.LayoutParams layoutParams) {
        Intrinsics.checkNotNullParameter(layoutParams, "<this>");
        return new Position(layoutParams.x, layoutParams.y);
    }

    public final void setPosition(WindowManager.LayoutParams layoutParams, Position value) {
        Intrinsics.checkNotNullParameter(layoutParams, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        layoutParams.x = value.getX();
        layoutParams.y = value.getY();
    }

    public final Position getPosition(MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "<this>");
        return new Position(motionEvent.getRawX(), motionEvent.getRawY());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Position get_displaySize() {
        return this._displaySize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Position get_displayCenterPosition() {
        return this._displayCenterPosition;
    }

    public final void setMediator(IMediator mediator) {
        Intrinsics.checkNotNullParameter(mediator, "mediator");
        this._mediator = mediator;
    }
}
