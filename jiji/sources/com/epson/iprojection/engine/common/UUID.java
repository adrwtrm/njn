package com.epson.iprojection.engine.common;

import android.content.Context;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UUID.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0004H\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/engine/common/UUID;", "", "()V", "<set-?>", "", "uuid", "getUuid", "()Ljava/lang/String;", "createUUID", "setUUID", "", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class UUID {
    public static final UUID INSTANCE = new UUID();
    private static String uuid = "00000000-0000-0000-0000-000000000000";

    private UUID() {
    }

    public final String getUuid() {
        return uuid;
    }

    public final void setUUID(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String id = PrefUtils.read(context, PrefTagDefine.PROJECTORLOG_UUID);
        if (id == null) {
            id = createUUID();
            PrefUtils.write(context, PrefTagDefine.PROJECTORLOG_UUID, id);
        }
        Intrinsics.checkNotNullExpressionValue(id, "id");
        uuid = id;
    }

    private final String createUUID() {
        String uuid2 = java.util.UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid2, "randomUUID().toString()");
        return uuid2;
    }
}
