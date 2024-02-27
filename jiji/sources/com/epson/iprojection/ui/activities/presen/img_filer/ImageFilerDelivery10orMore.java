package com.epson.iprojection.ui.activities.presen.img_filer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.ui.activities.delivery.DeliveryFileIO10orMore;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageFilerDelivery10orMore.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0017J\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016J\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\tH\u0017¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/ui/activities/presen/img_filer/ImageFilerDelivery10orMore;", "Lcom/epson/iprojection/ui/activities/presen/img_filer/ImageFiler;", "context", "Landroid/content/Context;", "isNoSort", "", "(Landroid/content/Context;Z)V", "exists", "path", "", "getImage", "Landroid/graphics/Bitmap;", "num", "", "w", "h", "getImageFileList", "Ljava/util/LinkedList;", "Lcom/epson/iprojection/ui/activities/presen/img_filer/ImageFileInfo;", "selectedPath", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ImageFilerDelivery10orMore extends ImageFiler {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageFilerDelivery10orMore(Context context, boolean z) {
        super(context, z);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.epson.iprojection.ui.activities.presen.img_filer.ImageFiler
    public LinkedList<ImageFileInfo> getImageFileList(String str) {
        DeliveryFileIO10orMore.Companion companion = DeliveryFileIO10orMore.Companion;
        Context _context = this._context;
        Intrinsics.checkNotNullExpressionValue(_context, "_context");
        LinkedList<Uri> imageUriList = companion.getImageUriList(_context);
        LinkedList<ImageFileInfo> linkedList = new LinkedList<>();
        Iterator<Uri> it = imageUriList.iterator();
        while (it.hasNext()) {
            Uri next = it.next();
            linkedList.add(new ImageFileInfo(next, FileUtils.getFileNameFromUri(this._context, next)));
        }
        return linkedList;
    }

    @Override // com.epson.iprojection.ui.activities.presen.img_filer.ImageFiler, com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public synchronized Bitmap getImage(int i, int i2, int i3) {
        InputStream openInputStream;
        openInputStream = this._context.getContentResolver().openInputStream(this._imgFileList.get(i).getUri());
        Intrinsics.checkNotNull(openInputStream);
        return getFitImage(0, BitmapFactory.decodeStream(openInputStream), i2, i3);
    }

    @Override // com.epson.iprojection.ui.activities.presen.img_filer.ImageFiler, com.epson.iprojection.ui.activities.presen.interfaces.IFiler
    public boolean exists(String str) {
        if (str == null) {
            return false;
        }
        Iterator<ImageFileInfo> it = getImageFileList(null).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next().getPath(), str)) {
                return true;
            }
        }
        return false;
    }
}
