package com.epson.iprojection.ui.activities.pjselect.qrcode.permission;

import com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities.Activity_QrCamera;
import com.epson.iprojection.ui.common.permision.PermissionContract;
import com.epson.iprojection.ui.common.permision.PermissionPresenter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionQrCameraPresenter.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0016J\u0013\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/permission/PermissionQrCameraPresenter;", "Lcom/epson/iprojection/ui/common/permision/PermissionPresenter;", "view", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "(Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;)V", "getNextActivityClass", "Ljava/lang/Class;", "getPermissions", "", "", "()[Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionQrCameraPresenter extends PermissionPresenter {
    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public Class<?> getNextActivityClass() {
        return Activity_QrCamera.class;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionQrCameraPresenter(PermissionContract.View view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionPresenter
    public String[] getPermissions() {
        return new String[]{"android.permission.CAMERA"};
    }
}
