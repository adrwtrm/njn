package dagger.hilt.android.plugin.util;

import dagger.hilt.android.plugin.util.SimpleAGPVersion;
import java.util.Comparator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SimpleAGPVersion.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\b\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0000H\u0096\u0002J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\n\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0015"}, d2 = {"Ldagger/hilt/android/plugin/util/SimpleAGPVersion;", "", "major", "", "minor", "(II)V", "getMajor", "()I", "getMinor", "compareTo", "other", "component1", "component2", "copy", "equals", "", "", "hashCode", "toString", "", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class SimpleAGPVersion implements Comparable<SimpleAGPVersion> {
    private final int major;
    private final int minor;
    public static final Companion Companion = new Companion(null);
    private static final Lazy<SimpleAGPVersion> ANDROID_GRADLE_PLUGIN_VERSION$delegate = LazyKt.lazy(new Function0<SimpleAGPVersion>() { // from class: dagger.hilt.android.plugin.util.SimpleAGPVersion$Companion$ANDROID_GRADLE_PLUGIN_VERSION$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SimpleAGPVersion invoke() {
            Class<?> findClass = AndroidGradleCompatKt.findClass("com.android.Version");
            if (findClass == null) {
                findClass = AndroidGradleCompatKt.findClass("com.android.builder.model.Version");
            }
            if (findClass != null) {
                SimpleAGPVersion.Companion companion = SimpleAGPVersion.Companion;
                Object obj = findClass.getField("ANDROID_GRADLE_PLUGIN_VERSION").get(null);
                if (obj != null) {
                    return companion.parse((String) obj);
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            throw new IllegalStateException("Unable to obtain AGP version. It is likely that the AGP version being used is too old.".toString());
        }
    });

    public static /* synthetic */ SimpleAGPVersion copy$default(SimpleAGPVersion simpleAGPVersion, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = simpleAGPVersion.major;
        }
        if ((i3 & 2) != 0) {
            i2 = simpleAGPVersion.minor;
        }
        return simpleAGPVersion.copy(i, i2);
    }

    public final int component1() {
        return this.major;
    }

    public final int component2() {
        return this.minor;
    }

    public final SimpleAGPVersion copy(int i, int i2) {
        return new SimpleAGPVersion(i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleAGPVersion) {
            SimpleAGPVersion simpleAGPVersion = (SimpleAGPVersion) obj;
            return this.major == simpleAGPVersion.major && this.minor == simpleAGPVersion.minor;
        }
        return false;
    }

    public int hashCode() {
        return (Integer.hashCode(this.major) * 31) + Integer.hashCode(this.minor);
    }

    public String toString() {
        return "SimpleAGPVersion(major=" + this.major + ", minor=" + this.minor + ')';
    }

    public SimpleAGPVersion(int i, int i2) {
        this.major = i;
        this.minor = i2;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    @Override // java.lang.Comparable
    public int compareTo(SimpleAGPVersion other) {
        Intrinsics.checkNotNullParameter(other, "other");
        final Comparator comparator = new Comparator<T>() { // from class: dagger.hilt.android.plugin.util.SimpleAGPVersion$compareTo$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((SimpleAGPVersion) t).getMajor()), Integer.valueOf(((SimpleAGPVersion) t2).getMajor()));
            }
        };
        return new Comparator<T>() { // from class: dagger.hilt.android.plugin.util.SimpleAGPVersion$compareTo$$inlined$thenBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator.compare(t, t2);
                return compare != 0 ? compare : ComparisonsKt.compareValues(Integer.valueOf(((SimpleAGPVersion) t).getMinor()), Integer.valueOf(((SimpleAGPVersion) t2).getMinor()));
            }
        }.compare(this, other);
    }

    /* compiled from: SimpleAGPVersion.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0014\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/util/SimpleAGPVersion$Companion;", "", "()V", "ANDROID_GRADLE_PLUGIN_VERSION", "Ldagger/hilt/android/plugin/util/SimpleAGPVersion;", "getANDROID_GRADLE_PLUGIN_VERSION", "()Ldagger/hilt/android/plugin/util/SimpleAGPVersion;", "ANDROID_GRADLE_PLUGIN_VERSION$delegate", "Lkotlin/Lazy;", "parse", "version", "", "tryParse", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SimpleAGPVersion getANDROID_GRADLE_PLUGIN_VERSION() {
            return (SimpleAGPVersion) SimpleAGPVersion.ANDROID_GRADLE_PLUGIN_VERSION$delegate.getValue();
        }

        public final SimpleAGPVersion parse(String str) {
            SimpleAGPVersion tryParse = tryParse(str);
            if (tryParse != null) {
                return tryParse;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Unable to parse AGP version: ", str).toString());
        }

        private final SimpleAGPVersion tryParse(String str) {
            if (str == null) {
                return null;
            }
            List split$default = kotlin.text.StringsKt.split$default((CharSequence) str, new char[]{'.'}, false, 0, 6, (Object) null);
            if (split$default.size() == 1) {
                return new SimpleAGPVersion(Integer.parseInt((String) split$default.get(0)), 0);
            }
            return new SimpleAGPVersion(Integer.parseInt((String) split$default.get(0)), Integer.parseInt((String) split$default.get(1)));
        }
    }
}
