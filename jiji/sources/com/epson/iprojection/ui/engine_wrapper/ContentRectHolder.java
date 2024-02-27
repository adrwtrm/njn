package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.utils.FitResolution;
import com.epson.iprojection.ui.common.ResRect;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ContentRectHolder.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/ui/engine_wrapper/ContentRectHolder;", "", "()V", "contentRect", "Lcom/epson/iprojection/ui/common/ResRect;", "clear", "", "get", "isEmpty", "", "setContentRect", "width", "", "height", "update", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ContentRectHolder {
    public static final ContentRectHolder INSTANCE = new ContentRectHolder();
    private static final ResRect contentRect = new ResRect();

    private ContentRectHolder() {
    }

    public final void setContentRect(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return;
        }
        int shadowResWidth = (int) (Pj.getIns().getShadowResWidth() * 1.0f);
        int shadowResHeight = (int) (Pj.getIns().getShadowResHeight() * 1.0f);
        clear();
        ResRect resRect = contentRect;
        resRect.w = i;
        resRect.h = i2;
        if (i / i2 < 1.3333334f) {
            resRect.w = (i2 * 4) / 3;
        }
        FitResolution.getRectFitWithIn(resRect, new ResRect(shadowResWidth, shadowResHeight));
    }

    public final ResRect get() {
        ResRect m191clone = contentRect.m191clone();
        Intrinsics.checkNotNullExpressionValue(m191clone, "contentRect.clone()");
        return m191clone;
    }

    public final void update() {
        ResRect resRect = contentRect;
        setContentRect(resRect.w, resRect.h);
    }

    public final boolean isEmpty() {
        ResRect resRect = contentRect;
        return resRect.w == 0 || resRect.h == 0;
    }

    public final void clear() {
        ResRect resRect = contentRect;
        resRect.x = 0;
        resRect.y = 0;
        resRect.w = 0;
        resRect.h = 0;
    }
}
