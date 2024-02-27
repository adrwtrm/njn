package com.epson.iprojection.ui.activities.fileselect;

import android.content.Context;
import android.content.Intent;
import com.epson.iprojection.ui.activities.fileselect.FileSelectContract;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageFileSelectPresenter.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/ImageFileSelectPresenter;", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectPresenter;", "context", "Landroid/content/Context;", "pj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "view", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;", "(Landroid/content/Context;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;)V", "canSelectMultiple", "", "setMimeType", "", "intent", "Landroid/content/Intent;", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ImageFileSelectPresenter extends FileSelectPresenter {
    public static final Companion Companion = new Companion(null);
    public static final String TAG_IMAGE = "intent_tag_file_select_image";

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectPresenter
    public boolean canSelectMultiple() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageFileSelectPresenter(Context context, IPj pj, FileSelectContract.View view) {
        super(context, pj, view);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pj, "pj");
        Intrinsics.checkNotNullParameter(view, "view");
    }

    /* compiled from: ImageFileSelectPresenter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/ImageFileSelectPresenter$Companion;", "", "()V", "TAG_IMAGE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectPresenter
    public void setMimeType(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/x-ms-bmp", "image/bmp", "image/jpeg", "image/png", "image/gif"});
    }
}
