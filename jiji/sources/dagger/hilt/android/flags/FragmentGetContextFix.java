package dagger.hilt.android.flags;

import android.content.Context;
import dagger.Module;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.internal.Preconditions;
import dagger.multibindings.Multibinds;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Set;
import javax.inject.Qualifier;

/* loaded from: classes2.dex */
public final class FragmentGetContextFix {

    @Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Qualifier
    /* loaded from: classes2.dex */
    public @interface DisableFragmentGetContextFix {
    }

    /* loaded from: classes2.dex */
    public interface FragmentGetContextFixEntryPoint {
        Set<Boolean> getDisableFragmentGetContextFix();
    }

    public static boolean isFragmentGetContextFixDisabled(Context context) {
        Set<Boolean> disableFragmentGetContextFix = ((FragmentGetContextFixEntryPoint) EntryPointAccessors.fromApplication(context, FragmentGetContextFixEntryPoint.class)).getDisableFragmentGetContextFix();
        Preconditions.checkState(disableFragmentGetContextFix.size() <= 1, "Cannot bind the flag @DisableFragmentGetContextFix more than once.", new Object[0]);
        if (disableFragmentGetContextFix.isEmpty()) {
            return true;
        }
        return disableFragmentGetContextFix.iterator().next().booleanValue();
    }

    @Module
    /* loaded from: classes2.dex */
    static abstract class FragmentGetContextFixModule {
        @Multibinds
        abstract Set<Boolean> disableFragmentGetContextFix();

        FragmentGetContextFixModule() {
        }
    }

    private FragmentGetContextFix() {
    }
}
