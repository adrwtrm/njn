package com.serenegiant.utils;

import com.serenegiant.utils.IEnum;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EnumExt.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a8\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0006\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aB\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0006\u001a<\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a8\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0006\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\n"}, d2 = {"asEnum", "", "E", "id", "", "clazz", "Ljava/lang/Class;", "a", "label", "", "common_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class EnumExtKt {
    public static final <E extends Enum<E>> Enum<E> asEnum(String label, Class<Enum<E>> clazz) {
        Enum<E> r5;
        Enum<E>[] enumConstants;
        Enum<E>[] enumConstants2;
        Enum<E>[] enumConstants3;
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        int i = 0;
        Enum<E> r2 = null;
        if (IEnumKt.class.isAssignableFrom(clazz) && (enumConstants3 = clazz.getEnumConstants()) != null) {
            int length = enumConstants3.length;
            for (int i2 = 0; i2 < length; i2++) {
                r5 = enumConstants3[i2];
                Intrinsics.checkNotNull(r5, "null cannot be cast to non-null type com.serenegiant.utils.IEnumKt");
                if (Intrinsics.areEqual(((IEnumKt) r5).label(), label)) {
                    break;
                }
            }
        }
        r5 = null;
        if (IEnum.EnumInterface.class.isAssignableFrom(clazz) && (enumConstants2 = clazz.getEnumConstants()) != null) {
            int length2 = enumConstants2.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    r5 = null;
                    break;
                }
                r5 = enumConstants2[i3];
                Intrinsics.checkNotNull(r5, "null cannot be cast to non-null type com.serenegiant.utils.IEnum.EnumInterface");
                if (Intrinsics.areEqual(((IEnum.EnumInterface) r5).label(), label)) {
                    break;
                }
                i3++;
            }
        }
        if (r5 == null && (enumConstants = clazz.getEnumConstants()) != null) {
            int length3 = enumConstants.length;
            while (true) {
                if (i >= length3) {
                    break;
                }
                Enum<E> r3 = enumConstants[i];
                if (Intrinsics.areEqual(r3.name(), label)) {
                    r2 = r3;
                    break;
                }
                i++;
            }
            r5 = r2;
        }
        if (r5 != null) {
            return r5;
        }
        throw new NoSuchElementException("Unknown enum label=" + label);
    }

    public static final <E extends Enum<E>> Enum<E> asEnum(int i, Class<Enum<E>> clazz) {
        Enum<E> r6;
        Enum<E>[] enumConstants;
        Enum<E>[] enumConstants2;
        Enum<E>[] enumConstants3;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Enum<E> r3 = null;
        if (IEnumKt.class.isAssignableFrom(clazz) && (enumConstants3 = clazz.getEnumConstants()) != null) {
            int length = enumConstants3.length;
            for (int i2 = 0; i2 < length; i2++) {
                r6 = enumConstants3[i2];
                Intrinsics.checkNotNull(r6, "null cannot be cast to non-null type com.serenegiant.utils.IEnumKt");
                if (((IEnumKt) r6).id() == i) {
                    break;
                }
            }
        }
        r6 = null;
        if (IEnum.EnumInterface.class.isAssignableFrom(clazz) && (enumConstants2 = clazz.getEnumConstants()) != null) {
            int length2 = enumConstants2.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    r6 = null;
                    break;
                }
                r6 = enumConstants2[i3];
                Intrinsics.checkNotNull(r6, "null cannot be cast to non-null type com.serenegiant.utils.IEnum.EnumInterface");
                if (((IEnum.EnumInterface) r6).id() == i) {
                    break;
                }
                i3++;
            }
        }
        if (r6 == null && (enumConstants = clazz.getEnumConstants()) != null) {
            int length3 = enumConstants.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length3) {
                    break;
                }
                Enum<E> r5 = enumConstants[i4];
                if (r5.ordinal() == i) {
                    r3 = r5;
                    break;
                }
                i4++;
            }
            r6 = r3;
        }
        if (r6 != null) {
            return r6;
        }
        throw new NoSuchElementException("Unknown enum id=" + i);
    }

    public static final <E extends Enum<E>> Enum<E> asEnum(int i, String str, Class<Enum<E>> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        try {
            return asEnum(i, clazz);
        } catch (NoSuchElementException unused) {
            if (str != null) {
                return asEnum(str, clazz);
            }
            throw new NoSuchElementException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v8 */
    public static final <E extends Enum<E>> Enum<E> asEnum(String label, Enum<E> a) {
        Enum<E> r5;
        Enum<E>[] enumArr;
        Object[] enumConstants;
        Enum<E> r52;
        Object[] enumConstants2;
        Enum<E> r53;
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(a, "a");
        int i = 0;
        Enum<E> r2 = null;
        if (!(a instanceof IEnumKt) || (enumConstants2 = a.getClass().getEnumConstants()) == null) {
            r5 = null;
        } else {
            int length = enumConstants2.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    r53 = null;
                    break;
                }
                r53 = enumConstants2[i2];
                if (Intrinsics.areEqual(((IEnumKt) ((Enum) r53)).label(), label)) {
                    break;
                }
                i2++;
            }
            r5 = r53;
        }
        if (r5 == null && (a instanceof IEnum.EnumInterface) && (enumConstants = a.getClass().getEnumConstants()) != null) {
            int length2 = enumConstants.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    r52 = null;
                    break;
                }
                r52 = enumConstants[i3];
                if (Intrinsics.areEqual(((IEnum.EnumInterface) ((Enum) r52)).label(), label)) {
                    break;
                }
                i3++;
            }
            r5 = r52;
        }
        if (r5 == null && (enumArr = (Enum[]) a.getClass().getEnumConstants()) != null) {
            int length3 = enumArr.length;
            while (true) {
                if (i >= length3) {
                    break;
                }
                Enum<E> r3 = enumArr[i];
                if (Intrinsics.areEqual(r3.name(), label)) {
                    r2 = r3;
                    break;
                }
                i++;
            }
            r5 = r2;
        }
        if (r5 != null) {
            return r5;
        }
        throw new NoSuchElementException("Unknown enum label=" + label);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v16 */
    public static final <E extends Enum<E>> Enum<E> asEnum(int i, Enum<E> a) {
        Enum<E> r6;
        Enum<E>[] enumArr;
        Object[] enumConstants;
        Enum<E> r62;
        Object[] enumConstants2;
        Enum<E> r63;
        Intrinsics.checkNotNullParameter(a, "a");
        Enum<E> r3 = null;
        if (!(a instanceof IEnumKt) || (enumConstants2 = a.getClass().getEnumConstants()) == null) {
            r6 = null;
        } else {
            int length = enumConstants2.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    r63 = null;
                    break;
                }
                r63 = enumConstants2[i2];
                if (((IEnumKt) ((Enum) r63)).id() == i) {
                    break;
                }
                i2++;
            }
            r6 = r63;
        }
        if (r6 == null && (a instanceof IEnum.EnumInterface) && (enumConstants = a.getClass().getEnumConstants()) != null) {
            int length2 = enumConstants.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    r62 = null;
                    break;
                }
                r62 = enumConstants[i3];
                if (((IEnum.EnumInterface) ((Enum) r62)).id() == i) {
                    break;
                }
                i3++;
            }
            r6 = r62;
        }
        if (r6 == null && (enumArr = (Enum[]) a.getClass().getEnumConstants()) != null) {
            int length3 = enumArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length3) {
                    break;
                }
                Enum<E> r5 = enumArr[i4];
                if (r5.ordinal() == i) {
                    r3 = r5;
                    break;
                }
                i4++;
            }
            r6 = r3;
        }
        if (r6 != null) {
            return r6;
        }
        throw new NoSuchElementException("Unknown enum id=" + i);
    }

    public static final <E extends Enum<E>> Enum<E> asEnum(int i, String str, Enum<E> a) {
        Intrinsics.checkNotNullParameter(a, "a");
        try {
            return asEnum(i, a);
        } catch (NoSuchElementException unused) {
            if (str != null) {
                return asEnum(str, a);
            }
            throw new NoSuchElementException();
        }
    }
}
