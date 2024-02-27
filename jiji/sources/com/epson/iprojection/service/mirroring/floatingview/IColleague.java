package com.epson.iprojection.service.mirroring.floatingview;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* compiled from: IColleague.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u0014J\b\u0010\u0002\u001a\u00020\u0003H&J \u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0003H&¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/IColleague;", "", "addViewToWindow", "", "fade", "frome", "", TypedValues.TransitionType.S_TO, "time", "", "hide", "move", "position", "Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "removeViewFromWindow", "replaceResouceImage", "resourceImageId", "", "requestDelivery", "show", "ViewType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface IColleague {

    /* compiled from: IColleague.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/IColleague$ViewType;", "", "(Ljava/lang/String;I)V", "DeliveryButton", "RemoverImage", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum ViewType {
        DeliveryButton,
        RemoverImage
    }

    void addViewToWindow();

    void fade(float f, float f2, long j);

    void hide();

    void move(Position position);

    void removeViewFromWindow();

    void replaceResouceImage(int i);

    void requestDelivery();

    void show();
}
