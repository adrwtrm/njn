package com.epson.iprojection.ui.engine_wrapper.utils;

import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserListUtils.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/engine_wrapper/utils/UserListUtils;", "", "()V", "isMyScreenProjectedOn1Screen", "", "mppLayout", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/engine/common/D_MppLayoutInfo;", "Lkotlin/collections/ArrayList;", "myUniqueId", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class UserListUtils {
    public static final UserListUtils INSTANCE = new UserListUtils();

    public static /* synthetic */ boolean $r8$lambda$91rumO0Ou8J72fPgxSffZPMmbbc(Function1 function1, Object obj) {
        return isMyScreenProjectedOn1Screen$lambda$1(function1, obj);
    }

    public static /* synthetic */ boolean $r8$lambda$GkoJpBTRRHB1E9LOwxdXfH66onQ(Function1 function1, Object obj) {
        return isMyScreenProjectedOn1Screen$lambda$0(function1, obj);
    }

    private UserListUtils() {
    }

    public static final boolean isMyScreenProjectedOn1Screen$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    public final boolean isMyScreenProjectedOn1Screen(ArrayList<D_MppLayoutInfo> arrayList, long j) {
        if (arrayList == null) {
            return false;
        }
        Stream stream = arrayList.stream();
        final UserListUtils$isMyScreenProjectedOn1Screen$1 userListUtils$isMyScreenProjectedOn1Screen$1 = new Function1<D_MppLayoutInfo, Boolean>() { // from class: com.epson.iprojection.ui.engine_wrapper.utils.UserListUtils$isMyScreenProjectedOn1Screen$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(D_MppLayoutInfo i) {
                Intrinsics.checkNotNullParameter(i, "i");
                return Boolean.valueOf(i.active);
            }
        };
        if (stream.filter(new Predicate() { // from class: com.epson.iprojection.ui.engine_wrapper.utils.UserListUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UserListUtils.$r8$lambda$GkoJpBTRRHB1E9LOwxdXfH66onQ(Function1.this, obj);
            }
        }).count() != 1) {
            return false;
        }
        Stream stream2 = arrayList.stream();
        final UserListUtils$isMyScreenProjectedOn1Screen$2 userListUtils$isMyScreenProjectedOn1Screen$2 = new Function1<D_MppLayoutInfo, Boolean>() { // from class: com.epson.iprojection.ui.engine_wrapper.utils.UserListUtils$isMyScreenProjectedOn1Screen$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(D_MppLayoutInfo i) {
                Intrinsics.checkNotNullParameter(i, "i");
                return Boolean.valueOf(i.active);
            }
        };
        return ((D_MppLayoutInfo) stream2.filter(new Predicate() { // from class: com.epson.iprojection.ui.engine_wrapper.utils.UserListUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UserListUtils.$r8$lambda$91rumO0Ou8J72fPgxSffZPMmbbc(Function1.this, obj);
            }
        }).findAny().get()).uniqueId == j;
    }

    public static final boolean isMyScreenProjectedOn1Screen$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }
}
