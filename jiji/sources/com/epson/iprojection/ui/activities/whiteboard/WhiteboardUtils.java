package com.epson.iprojection.ui.activities.whiteboard;

import android.os.Build;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: WhiteboardUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WhiteboardUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: WhiteboardUtils.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardUtils$Companion;", "", "()V", "shouldOpenInMyApp", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean shouldOpenInMyApp() {
            D_LinkageData d_LinkageData = LinkageDataInfoStacker.getIns().get();
            return d_LinkageData != null && Build.VERSION.SDK_INT >= 29 && d_LinkageData.isEasyConnect;
        }
    }
}
