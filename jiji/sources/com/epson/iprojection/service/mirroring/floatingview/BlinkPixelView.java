package com.epson.iprojection.service.mirroring.floatingview;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import com.epson.iprojection.common.utils.Sleeper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlinkPixelView.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0014J\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\fJ\u0006\u0010\u000f\u001a\u00020\fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/BlinkPixelView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "_handler", "Landroid/os/Handler;", "_isRunning", "", "onDetachedFromWindow", "", "setAnimation", "start", "stop", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class BlinkPixelView extends View {
    private final Handler _handler;
    private boolean _isRunning;

    /* renamed from: $r8$lambda$Lo1JrgFD97doUkZ-9OCAsPYFEGE */
    public static /* synthetic */ void m76$r8$lambda$Lo1JrgFD97doUkZ9OCAsPYFEGE(BlinkPixelView blinkPixelView, ValueAnimator valueAnimator) {
        setAnimation$lambda$1(blinkPixelView, valueAnimator);
    }

    public static /* synthetic */ void $r8$lambda$T3GDD52JCwA96e7bWxN79RLauZM(ValueAnimator valueAnimator) {
        valueAnimator.start();
    }

    /* renamed from: $r8$lambda$gW7hKNjp-c0xCKrS9jquTgp2Azw */
    public static /* synthetic */ void m77$r8$lambda$gW7hKNjpc0xCKrS9jquTgp2Azw(BlinkPixelView blinkPixelView) {
        start$lambda$0(blinkPixelView);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlinkPixelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
        this._isRunning = true;
        this._handler = new Handler(Looper.getMainLooper());
    }

    public final void start() {
        this._isRunning = true;
        new Thread(new Runnable() { // from class: com.epson.iprojection.service.mirroring.floatingview.BlinkPixelView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BlinkPixelView.m77$r8$lambda$gW7hKNjpc0xCKrS9jquTgp2Azw(BlinkPixelView.this);
            }
        }).start();
    }

    public static final void start$lambda$0(BlinkPixelView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        while (this$0._isRunning) {
            this$0.setAnimation();
            Sleeper.sleep(1000L);
        }
    }

    public final void stop() {
        this._isRunning = false;
    }

    public final void setAnimation() {
        final ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(Color.argb(0, 255, 0, 0)), Integer.valueOf(Color.argb(1, 0, 0, 255)));
        ofObject.setDuration(1000L);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.epson.iprojection.service.mirroring.floatingview.BlinkPixelView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BlinkPixelView.m76$r8$lambda$Lo1JrgFD97doUkZ9OCAsPYFEGE(BlinkPixelView.this, valueAnimator);
            }
        });
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.service.mirroring.floatingview.BlinkPixelView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BlinkPixelView.$r8$lambda$T3GDD52JCwA96e7bWxN79RLauZM(ofObject);
            }
        });
    }

    public static final void setAnimation$lambda$1(BlinkPixelView this$0, ValueAnimator animator) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animator, "animator");
        Object animatedValue = animator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        this$0.setBackgroundColor(((Integer) animatedValue).intValue());
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this._isRunning = false;
    }
}
