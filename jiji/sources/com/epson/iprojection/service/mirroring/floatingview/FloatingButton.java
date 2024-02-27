package com.epson.iprojection.service.mirroring.floatingview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.service.mirroring.floatingview.IColleague;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRequestTransferScreenDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FloatingButton.kt */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u0015\u001a\u00020\u0016H\u0016J \u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0013H\u0016J\u001a\u0010\"\u001a\u00020\u001f2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020\u0016H\u0016J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020\tH\u0016J\b\u0010*\u001a\u00020\u0016H\u0016J\b\u0010+\u001a\u00020\u0016H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/FloatingButton;", "Lcom/epson/iprojection/service/mirroring/floatingview/FloatingView;", "Landroid/view/View$OnTouchListener;", "context", "Landroid/content/Context;", "type", "Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;)V", "THRESHOLD_MOVING_VALUE", "", "_buttonLayout", "Landroid/view/ViewGroup;", "_context", "_deliverImageBtn", "Landroidx/appcompat/widget/AppCompatImageButton;", "kotlin.jvm.PlatformType", "_layoutParams", "Landroid/view/WindowManager$LayoutParams;", "_startingPoint", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "_touchDownPosition", "addViewToWindow", "", "fade", TypedValues.TransitionType.S_FROM, "", TypedValues.TransitionType.S_TO, "time", "", "hide", "isMoved", "", "position", "move", "onTouch", "v", "Landroid/view/View;", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "removeViewFromWindow", "replaceResouceImage", "resourceImageId", "requestDelivery", "show", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FloatingButton extends FloatingView implements View.OnTouchListener {
    private final int THRESHOLD_MOVING_VALUE;
    private final ViewGroup _buttonLayout;
    private final Context _context;
    private final AppCompatImageButton _deliverImageBtn;
    private WindowManager.LayoutParams _layoutParams;
    private Position _startingPoint;
    private Position _touchDownPosition;

    public static final void addViewToWindow$lambda$1(View view) {
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void replaceResouceImage(int i) {
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void show() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatingButton(Context context, IColleague.ViewType type) {
        super(context, type);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        this._context = context;
        this.THRESHOLD_MOVING_VALUE = 2;
        WindowManager.LayoutParams layoutParams = get_params();
        layoutParams.x = get_displaySize().getX() / 2;
        layoutParams.y = 0;
        this._layoutParams = layoutParams;
        View inflate = get_inflater().inflate(R.layout.floating_button, (ViewGroup) null);
        Intrinsics.checkNotNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) inflate;
        this._buttonLayout = viewGroup;
        this._deliverImageBtn = (AppCompatImageButton) viewGroup.findViewById(R.id.deliverImagebtn);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void addViewToWindow() {
        this._deliverImageBtn.setOnTouchListener(this);
        this._deliverImageBtn.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.service.mirroring.floatingview.FloatingButton$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatingButton.addViewToWindow$lambda$1(view);
            }
        });
        get_windowManager().addView(this._buttonLayout, this._layoutParams);
        IMediator iMediator = get_mediator();
        if (iMediator != null) {
            iMediator.reportShowed(this, getPosition(this._layoutParams));
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void removeViewFromWindow() {
        get_windowManager().removeView(this._buttonLayout);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void hide() {
        this._buttonLayout.setVisibility(8);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void requestDelivery() {
        Pj.getIns().requestDelivery(false, false, false, false, false);
        Analytics.getIns().setRequestTransferScreenEvent(eRequestTransferScreenDimension.projectionScreen);
        Analytics.getIns().sendEvent(eCustomEvent.REQUEST_TRANSFER_SCREEN);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void move(Position position) {
        Intrinsics.checkNotNullParameter(position, "position");
        setPosition(this._layoutParams, position);
        get_windowManager().updateViewLayout(this._buttonLayout, this._layoutParams);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void fade(float f, float f2, long j) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillAfter(true);
        this._deliverImageBtn.startAnimation(alphaAnimation);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
        IMediator iMediator;
        Intrinsics.checkNotNullParameter(event, "event");
        int action = event.getAction();
        if (action == 0) {
            this._startingPoint = getPosition(this._layoutParams);
            this._touchDownPosition = getPosition(event).minus(get_displayCenterPosition());
            IMediator iMediator2 = get_mediator();
            if (iMediator2 != null) {
                iMediator2.reportTouched(this);
                return false;
            }
            return false;
        }
        if (action != 1) {
            if (action == 2) {
                Position minus = getPosition(event).minus(get_displayCenterPosition());
                WindowManager.LayoutParams layoutParams = this._layoutParams;
                Position position = this._startingPoint;
                Intrinsics.checkNotNull(position);
                Position position2 = this._touchDownPosition;
                Intrinsics.checkNotNull(position2);
                setPosition(layoutParams, position.plus(minus.minus(position2)));
                get_windowManager().updateViewLayout(this._buttonLayout, this._layoutParams);
                if (!isMoved(minus) || (iMediator = get_mediator()) == null) {
                    return false;
                }
                iMediator.reportMoved(this, getPosition(this._layoutParams));
                return false;
            } else if (action != 3) {
                return false;
            }
        }
        IMediator iMediator3 = get_mediator();
        if (iMediator3 != null) {
            iMediator3.reportActionUp(this);
            return false;
        }
        return false;
    }

    private final boolean isMoved(Position position) {
        Position position2 = this._touchDownPosition;
        Float valueOf = position2 != null ? Float.valueOf(position2.getDistance(position)) : null;
        Intrinsics.checkNotNull(valueOf);
        return valueOf.floatValue() > ((float) DipUtils.dp2px(this._context, this.THRESHOLD_MOVING_VALUE));
    }
}
