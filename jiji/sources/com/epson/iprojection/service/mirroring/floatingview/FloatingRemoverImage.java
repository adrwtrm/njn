package com.epson.iprojection.service.mirroring.floatingview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.service.mirroring.floatingview.IColleague;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FloatingRemoverImage.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\fH\u0016J\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/FloatingRemoverImage;", "Lcom/epson/iprojection/service/mirroring/floatingview/FloatingView;", "context", "Landroid/content/Context;", "type", "Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;)V", "_layoutParams", "Landroid/view/WindowManager$LayoutParams;", "_removerImage", "Landroid/view/ViewGroup;", "addViewToWindow", "", "fade", "frome", "", TypedValues.TransitionType.S_TO, "time", "", "hide", "move", "position", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "removeViewFromWindow", "replaceResouceImage", "resourceImageId", "", "requestDelivery", "show", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FloatingRemoverImage extends FloatingView {
    public static final Companion Companion = new Companion(null);
    public static final int POSITION_MARGINE_BOTTOM = 64;
    private final WindowManager.LayoutParams _layoutParams;
    private final ViewGroup _removerImage;

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void fade(float f, float f2, long j) {
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void requestDelivery() {
    }

    /* compiled from: FloatingRemoverImage.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/FloatingRemoverImage$Companion;", "", "()V", "POSITION_MARGINE_BOTTOM", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatingRemoverImage(Context context, IColleague.ViewType type) {
        super(context, type);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        WindowManager.LayoutParams layoutParams = get_params();
        layoutParams.x = 0;
        layoutParams.y = (get_displaySize().getY() / 2) - DipUtils.dp2px(context, 64);
        this._layoutParams = layoutParams;
        View inflate = get_inflater().inflate(R.layout.floating_remover, (ViewGroup) null);
        Intrinsics.checkNotNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        this._removerImage = (ViewGroup) inflate;
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void addViewToWindow() {
        get_windowManager().addView(this._removerImage, this._layoutParams);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void removeViewFromWindow() {
        get_windowManager().removeView(this._removerImage);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void show() {
        this._removerImage.setVisibility(0);
        IMediator iMediator = get_mediator();
        if (iMediator != null) {
            iMediator.reportShowed(this, getPosition(this._layoutParams));
        }
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void hide() {
        this._removerImage.setVisibility(8);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void move(Position position) {
        Intrinsics.checkNotNullParameter(position, "position");
        setPosition(this._layoutParams, position);
        get_windowManager().updateViewLayout(this._removerImage, this._layoutParams);
    }

    @Override // com.epson.iprojection.service.mirroring.floatingview.IColleague
    public void replaceResouceImage(int i) {
        ((ImageView) this._removerImage.findViewById(R.id.remover_image)).setImageResource(i);
    }
}
