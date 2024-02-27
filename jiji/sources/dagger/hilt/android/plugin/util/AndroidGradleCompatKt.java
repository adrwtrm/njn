package dagger.hilt.android.plugin.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AndroidGradleCompat.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"findClass", "Ljava/lang/Class;", "fqName", "", "plugin"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidGradleCompatKt {
    public static final Class<?> findClass(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        try {
            return Class.forName(fqName);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
