package com.epson.iprojection.ui.activities.moderator;

import kotlin.Metadata;

/* compiled from: MultiProjectionDisplaySettings.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0005\"\u0004\b\t\u0010\u0007¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/ui/activities/moderator/MultiProjectionDisplaySettings;", "", "()V", "isSmall", "", "()Z", "setSmall", "(Z)V", "isThumb", "setThumb", "clear", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MultiProjectionDisplaySettings {
    public static final MultiProjectionDisplaySettings INSTANCE;
    private static boolean isSmall;
    private static boolean isThumb;

    private MultiProjectionDisplaySettings() {
    }

    public final boolean isThumb() {
        return isThumb;
    }

    public final void setThumb(boolean z) {
        isThumb = z;
    }

    static {
        MultiProjectionDisplaySettings multiProjectionDisplaySettings = new MultiProjectionDisplaySettings();
        INSTANCE = multiProjectionDisplaySettings;
        isSmall = true;
        multiProjectionDisplaySettings.clear();
    }

    public final boolean isSmall() {
        return isSmall;
    }

    public final void setSmall(boolean z) {
        isSmall = z;
    }

    public final void clear() {
        isThumb = false;
        isSmall = true;
    }
}
