package com.epson.iprojection.ui.activities.marker;

import android.content.Context;
import android.graphics.Bitmap;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.ImageIOUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: ImageSaver10orMore.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0017¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/marker/ImageSaver10orMore;", "Lcom/epson/iprojection/ui/activities/marker/ImageSaver;", "c", "Landroid/content/Context;", "(Landroid/content/Context;)V", "save", "", "bmp", "Landroid/graphics/Bitmap;", "isDelivery", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ImageSaver10orMore extends ImageSaver {
    public static /* synthetic */ void $r8$lambda$EaB3UrsL5fSgmk3BJGAB_05Zl6c(ImageSaver10orMore imageSaver10orMore, boolean z, Bitmap bitmap) {
        save$lambda$0(imageSaver10orMore, z, bitmap);
    }

    public ImageSaver10orMore(Context context) {
        super(context);
    }

    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver
    public void save(final Bitmap bmp, final boolean z) {
        Intrinsics.checkNotNullParameter(bmp, "bmp");
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.marker.ImageSaver10orMore$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ImageSaver10orMore.$r8$lambda$EaB3UrsL5fSgmk3BJGAB_05Zl6c(ImageSaver10orMore.this, z, bmp);
            }
        }).start();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r13v4, types: [T, java.lang.String] */
    public static final void save$lambda$0(ImageSaver10orMore this$0, boolean z, Bitmap bmp) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bmp, "$bmp");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = CommonDefine.SAVEDIMAGE_RELATIVE_PATH;
        String str = this$0.getTimeFileName() + ImageSaver.FILE_TYPE;
        if (z) {
            objectRef.element = CommonDefine.SHAREDIMAGE_RELATIVE_PATH;
        }
        Context _context = this$0._context;
        Intrinsics.checkNotNullExpressionValue(_context, "_context");
        T relativePath = objectRef.element;
        Intrinsics.checkNotNullExpressionValue(relativePath, "relativePath");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new ImageSaver10orMore$save$1$1(ImageIOUtilsKt.save(_context, bmp, (String) relativePath, str), this$0, objectRef, str, null), 2, null);
    }
}
