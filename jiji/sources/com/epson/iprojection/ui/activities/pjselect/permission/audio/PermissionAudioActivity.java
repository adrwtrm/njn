package com.epson.iprojection.ui.activities.pjselect.permission.audio;

import com.epson.iprojection.ui.common.permision.PermissionActivity;
import kotlin.Metadata;

/* compiled from: PermissionAudioActivity.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/permission/audio/PermissionAudioActivity;", "Lcom/epson/iprojection/ui/common/permision/PermissionActivity;", "()V", "createPresenter", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionAudioActivity extends PermissionActivity {
    @Override // com.epson.iprojection.ui.common.permision.PermissionActivity
    public void createPresenter() {
        setMPresenter(new PermissionAudioPresenter(this));
    }
}
