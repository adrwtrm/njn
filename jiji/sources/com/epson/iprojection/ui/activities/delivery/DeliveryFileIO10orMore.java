package com.epson.iprojection.ui.activities.delivery;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.ImageIOUtilsKt;
import com.epson.iprojection.engine.common.D_DeliveryInfo;
import com.google.android.material.timepicker.TimeModel;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import org.objectweb.asm.signature.SignatureVisitor;

/* compiled from: DeliveryFileIO10orMore.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0017J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0017J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u0004H\u0002J\u001a\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0017¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/ui/activities/delivery/DeliveryFileIO10orMore;", "Lcom/epson/iprojection/ui/activities/delivery/DeliveryFileIO;", "()V", "createWhitePaper", "", "context", "Landroid/content/Context;", "w", "", "h", "delete", "", "uri", "Landroid/net/Uri;", "exists", "getFileNum", "", "getNewestFileName", "getTimeFileName", "save", "info", "Lcom/epson/iprojection/engine/common/D_DeliveryInfo;", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DeliveryFileIO10orMore extends DeliveryFileIO {
    public static final Companion Companion = new Companion(null);

    /* renamed from: $r8$lambda$DxuWQhurEK5a_Q-zEfflIaXoDjo */
    public static /* synthetic */ int m88$r8$lambda$DxuWQhurEK5a_QzEfflIaXoDjo(Function2 function2, Object obj, Object obj2) {
        return getNewestFileName$lambda$0(function2, obj, obj2);
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String save(Context context, D_DeliveryInfo d_DeliveryInfo) {
        Intrinsics.checkNotNullParameter(context, "context");
        String relativePath = CommonDefine.SHAREDIMAGE_RELATIVE_PATH;
        String timeFileName = getTimeFileName();
        Intrinsics.checkNotNull(d_DeliveryInfo);
        ByteBuffer byteBuffer = d_DeliveryInfo.buffer;
        Intrinsics.checkNotNullExpressionValue(byteBuffer, "info!!.buffer");
        Intrinsics.checkNotNullExpressionValue(relativePath, "relativePath");
        ImageIOUtilsKt.save(context, byteBuffer, relativePath, timeFileName);
        return relativePath + '/' + timeFileName;
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String createWhitePaper(Context context, short s, short s2) {
        Intrinsics.checkNotNullParameter(context, "context");
        String relativePath = CommonDefine.SHAREDIMAGE_RELATIVE_PATH;
        String timeFileName = getTimeFileName();
        Bitmap bitmap = BitmapUtils.createWhiteBitmap(s, s2);
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        Intrinsics.checkNotNullExpressionValue(relativePath, "relativePath");
        ImageIOUtilsKt.save(context, bitmap, relativePath, timeFileName);
        return relativePath + '/' + timeFileName;
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public boolean exists(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Companion.getImageUriList(context).size() != 0;
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public int getFileNum(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Companion.getImageUriList(context).size();
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public String getNewestFileName(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedList<Uri> imageUriList = Companion.getImageUriList(context);
        if (imageUriList.size() == 0) {
            return null;
        }
        final DeliveryFileIO10orMore$getNewestFileName$1 deliveryFileIO10orMore$getNewestFileName$1 = new Function2<Uri, Uri, Integer>() { // from class: com.epson.iprojection.ui.activities.delivery.DeliveryFileIO10orMore$getNewestFileName$1
            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(Uri uri, Uri uri2) {
                String path = uri2.getPath();
                Intrinsics.checkNotNull(path);
                String path2 = uri.getPath();
                Intrinsics.checkNotNull(path2);
                return Integer.valueOf(path.compareTo(path2));
            }
        };
        CollectionsKt.sortWith(imageUriList, new Comparator() { // from class: com.epson.iprojection.ui.activities.delivery.DeliveryFileIO10orMore$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DeliveryFileIO10orMore.m88$r8$lambda$DxuWQhurEK5a_QzEfflIaXoDjo(Function2.this, obj, obj2);
            }
        });
        return imageUriList.get(0).getPath();
    }

    public static final int getNewestFileName$lambda$0(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    @Override // com.epson.iprojection.ui.activities.delivery.DeliveryFileIO
    public boolean delete(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        return context.getContentResolver().delete(uri, null, null) > 0;
    }

    private final String getTimeFileName() {
        Calendar calendar = Calendar.getInstance();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(1))}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(2) + 1)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
        String format3 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(5))}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject4 = StringCompanionObject.INSTANCE;
        String format4 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(11))}, 1));
        Intrinsics.checkNotNullExpressionValue(format4, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject5 = StringCompanionObject.INSTANCE;
        String format5 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(12))}, 1));
        Intrinsics.checkNotNullExpressionValue(format5, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject6 = StringCompanionObject.INSTANCE;
        String format6 = String.format(Locale.getDefault(), TimeModel.ZERO_LEADING_NUMBER_FORMAT, Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(13))}, 1));
        Intrinsics.checkNotNullExpressionValue(format6, "format(locale, format, *args)");
        StringCompanionObject stringCompanionObject7 = StringCompanionObject.INSTANCE;
        String format7 = String.format(Locale.getDefault(), "%03d", Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(14))}, 1));
        Intrinsics.checkNotNullExpressionValue(format7, "format(locale, format, *args)");
        return format + SignatureVisitor.SUPER + format2 + SignatureVisitor.SUPER + format3 + SignatureVisitor.SUPER + format4 + SignatureVisitor.SUPER + format5 + SignatureVisitor.SUPER + format6 + SignatureVisitor.SUPER + format7 + ".jpg";
    }

    /* compiled from: DeliveryFileIO10orMore.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/delivery/DeliveryFileIO10orMore$Companion;", "", "()V", "getImageUriList", "Ljava/util/LinkedList;", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LinkedList<Uri> getImageUriList(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinkedList<Uri> linkedList = new LinkedList<>();
            try {
                Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (String[]) CollectionsKt.listOf((Object[]) new String[]{"_id", "relative_path"}).toArray(new String[0]), null, null, null);
                if (query != null && query.moveToFirst()) {
                    do {
                        int columnIndex = query.getColumnIndex("_id");
                        String string = query.getString(query.getColumnIndex("relative_path"));
                        String dropLast = string != null ? StringsKt.dropLast(string, 1) : null;
                        if (Intrinsics.areEqual(dropLast, CommonDefine.SHAREDIMAGE_RELATIVE_PATH) || Intrinsics.areEqual(dropLast, "EPSON/iProjection/SharedImages")) {
                            Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(columnIndex));
                            Intrinsics.checkNotNullExpressionValue(withAppendedId, "withAppendedId(\n        …                        )");
                            linkedList.add(withAppendedId);
                        }
                    } while (query.moveToNext());
                    query.close();
                }
            } catch (Exception unused) {
            }
            return linkedList;
        }
    }
}
