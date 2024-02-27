package com.epson.iprojection.service.mirroring.floatingview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.service.mirroring.floatingview.IColleague;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: FloatingMediator.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001fH\u0016J\u0010\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u0017H\u0002J\b\u0010&\u001a\u00020\"H\u0016J\u0010\u0010'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001fH\u0016J\u0018\u0010(\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u0017H\u0016J\u0018\u0010*\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u0017H\u0016J\u0010\u0010+\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001fH\u0016J\b\u0010,\u001a\u00020\"H\u0016J\b\u0010-\u001a\u00020\"H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001dj\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f` X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/FloatingMediator;", "Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "FADEOUT_ANIMATION_TIME", "", "REMOVE_AREA_BOUNDS", "", "SLEEP_TIME_BEFORE_DELIVER_IMAGE", "TIME_UNTIL_FADEOUT", "VIBRATION_TIME", "_context", "_fadeOutButtonRunner", "Ljava/lang/Runnable;", "_handler", "Landroid/os/Handler;", "_isButtonMoved", "", "_isInsideOfRemoverArea", "_removerArea", "", "_removerImagePosition", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "_shouldVibrate", "_startingFadeAnimationAlpha", "_vibrator", "Landroid/os/Vibrator;", "_viewList", "Ljava/util/HashMap;", "Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "Lcom/epson/iprojection/service/mirroring/floatingview/FloatingView;", "Lkotlin/collections/HashMap;", "addView", "", "view", "isInsideOfRemoverArea", "viewPosition", "onRotated", "reportActionUp", "reportMoved", "position", "reportShowed", "reportTouched", "startOverlay", "stopOverlay", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FloatingMediator implements IMediator {
    private final long FADEOUT_ANIMATION_TIME;
    private final int REMOVE_AREA_BOUNDS;
    private final long SLEEP_TIME_BEFORE_DELIVER_IMAGE;
    private final long TIME_UNTIL_FADEOUT;
    private final long VIBRATION_TIME;
    private final Context _context;
    private final Runnable _fadeOutButtonRunner;
    private final Handler _handler;
    private boolean _isButtonMoved;
    private boolean _isInsideOfRemoverArea;
    private final float _removerArea;
    private Position _removerImagePosition;
    private boolean _shouldVibrate;
    private float _startingFadeAnimationAlpha;
    private final Vibrator _vibrator;
    private HashMap<IColleague.ViewType, FloatingView> _viewList;

    /* renamed from: $r8$lambda$37J-Dn7P4r5v1rcOSxKDMaVvyzg */
    public static /* synthetic */ void m78$r8$lambda$37JDn7P4r5v1rcOSxKDMaVvyzg(FloatingMediator floatingMediator) {
        _fadeOutButtonRunner$lambda$0(floatingMediator);
    }

    public FloatingMediator(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this._context = context;
        this._viewList = new HashMap<>();
        this._startingFadeAnimationAlpha = 1.0f;
        this._handler = new Handler(Looper.getMainLooper());
        Object systemService = context.getSystemService("vibrator");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.Vibrator");
        this._vibrator = (Vibrator) systemService;
        this.VIBRATION_TIME = 100L;
        this.REMOVE_AREA_BOUNDS = 50;
        this._removerArea = (float) Math.sqrt(((float) Math.pow(DipUtils.dp2px(context, 50), 2)) * 2);
        this.TIME_UNTIL_FADEOUT = 3000L;
        this.FADEOUT_ANIMATION_TIME = 500L;
        this.SLEEP_TIME_BEFORE_DELIVER_IMAGE = 1000L;
        this._fadeOutButtonRunner = new Runnable() { // from class: com.epson.iprojection.service.mirroring.floatingview.FloatingMediator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FloatingMediator.m78$r8$lambda$37JDn7P4r5v1rcOSxKDMaVvyzg(FloatingMediator.this);
            }
        };
    }

    public static final void _fadeOutButtonRunner$lambda$0(FloatingMediator this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FloatingView floatingView = this$0._viewList.get(IColleague.ViewType.DeliveryButton);
        if (floatingView != null) {
            floatingView.fade(this$0._startingFadeAnimationAlpha, 0.5f, this$0.FADEOUT_ANIMATION_TIME);
        }
        this$0._startingFadeAnimationAlpha = 0.5f;
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void addView(FloatingView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this._viewList.put(view.getType(), view);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void startOverlay() {
        for (Map.Entry<IColleague.ViewType, FloatingView> entry : this._viewList.entrySet()) {
            entry.getValue().addViewToWindow();
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void stopOverlay() {
        for (Map.Entry<IColleague.ViewType, FloatingView> entry : this._viewList.entrySet()) {
            entry.getValue().removeViewFromWindow();
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void onRotated() {
        Position position = new Position(0.0f, (new Position(this._context.getResources().getDisplayMetrics().widthPixels, this._context.getResources().getDisplayMetrics().heightPixels).getFy() / 2) - DipUtils.dp2px(this._context, 64));
        FloatingView floatingView = this._viewList.get(IColleague.ViewType.RemoverImage);
        if (floatingView != null) {
            floatingView.move(position);
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void reportTouched(FloatingView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (view.getType() == IColleague.ViewType.DeliveryButton) {
            this._handler.removeCallbacks(this._fadeOutButtonRunner);
            view.fade(this._startingFadeAnimationAlpha, 1.0f, 1L);
            this._startingFadeAnimationAlpha = 1.0f;
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void reportMoved(FloatingView view, Position position) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(position, "position");
        if (view.getType() == IColleague.ViewType.DeliveryButton) {
            this._isButtonMoved = true;
            FloatingView floatingView = this._viewList.get(IColleague.ViewType.RemoverImage);
            if (floatingView != null) {
                floatingView.replaceResouceImage(R.drawable.remover_area);
            }
            FloatingView floatingView2 = this._viewList.get(IColleague.ViewType.RemoverImage);
            if (floatingView2 != null) {
                floatingView2.show();
            }
            if (this._removerImagePosition != null) {
                this._isInsideOfRemoverArea = isInsideOfRemoverArea(position);
            }
            if (this._isInsideOfRemoverArea) {
                Position position2 = this._removerImagePosition;
                if (position2 != null) {
                    view.move(position2);
                    FloatingView floatingView3 = this._viewList.get(IColleague.ViewType.RemoverImage);
                    if (floatingView3 != null) {
                        floatingView3.replaceResouceImage(R.drawable.overlap);
                    }
                }
                if (this._shouldVibrate) {
                    this._vibrator.vibrate(VibrationEffect.createOneShot(this.VIBRATION_TIME, -1));
                }
                this._shouldVibrate = false;
                return;
            }
            this._shouldVibrate = true;
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void reportActionUp(FloatingView view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Lg.i("mediator reportActionUp view.type = " + view.getType() + ", _isMovedButton = " + this._isButtonMoved);
        if (view.getType() == IColleague.ViewType.DeliveryButton) {
            if (!this._isButtonMoved) {
                view.fade(this._startingFadeAnimationAlpha, 0.2f, this.FADEOUT_ANIMATION_TIME);
                this._startingFadeAnimationAlpha = 0.2f;
                BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new FloatingMediator$reportActionUp$1(this, view, null), 2, null);
            }
            if (this._isInsideOfRemoverArea) {
                view.hide();
            }
            FloatingView floatingView = this._viewList.get(IColleague.ViewType.RemoverImage);
            if (floatingView != null) {
                floatingView.hide();
            }
            this._isButtonMoved = false;
            this._handler.postDelayed(this._fadeOutButtonRunner, this.TIME_UNTIL_FADEOUT);
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IMediator
    public void reportShowed(FloatingView view, Position position) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(position, "position");
        if (view.getType() == IColleague.ViewType.RemoverImage) {
            this._removerImagePosition = position;
        } else if (view.getType() == IColleague.ViewType.DeliveryButton) {
            this._handler.postDelayed(this._fadeOutButtonRunner, this.TIME_UNTIL_FADEOUT);
        }
    }

    private final boolean isInsideOfRemoverArea(Position position) {
        Position position2 = this._removerImagePosition;
        Float valueOf = position2 != null ? Float.valueOf(position.getDistance(position2)) : null;
        Intrinsics.checkNotNull(valueOf);
        return valueOf.floatValue() < this._removerArea;
    }
}
