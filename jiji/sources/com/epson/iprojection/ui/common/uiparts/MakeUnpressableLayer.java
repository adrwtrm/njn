package com.epson.iprojection.ui.common.uiparts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: MakeUnpressableLayer.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/common/uiparts/MakeUnpressableLayer;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attr", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "update", "", "mode", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnConnectListener$MppControlMode;", "userInfo", "Lcom/epson/iprojection/engine/common/D_MppUserInfo;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MakeUnpressableLayer extends LinearLayout {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MakeUnpressableLayer(Context context, AttributeSet attr) {
        super(context, attr);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attr, "attr");
    }

    public final void update(Context context, IOnConnectListener.MppControlMode mode, D_MppUserInfo d_MppUserInfo) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (mode != IOnConnectListener.MppControlMode.ModeratorEntry) {
            setVisibility(8);
        } else if (d_MppUserInfo == null) {
        } else {
            setVisibility(0);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String string = context.getString(R.string._CannotPerformOperationsModerator_);
            Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.stri…formOperationsModerator_)");
            String format = String.format(string, Arrays.copyOf(new Object[]{d_MppUserInfo.userName}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
            ((TextView) findViewById(R.id.txt_moderator_name)).setText(format);
        }
    }
}
