package com.epson.iprojection.ui.activities.marker.permission;

import com.epson.iprojection.ui.common.permision.PermissionContract;
import com.epson.iprojection.ui.common.permision.PermissionPresenter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionStragePresenter.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006H\u0016J\u0013\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/marker/permission/PermissionStragePresenter;", "Lcom/epson/iprojection/ui/common/permision/PermissionPresenter;", "view", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "(Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;)V", "getNextActivityClass", "Ljava/lang/Class;", "getPermissions", "", "", "()[Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionStragePresenter extends PermissionPresenter {
    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public Class<?> getNextActivityClass() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionStragePresenter(PermissionContract.View view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public String[] getPermissions() {
        return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
    }
}