package dagger.hilt.android.internal.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;
import dagger.Module;
import dagger.hilt.EntryPoints;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.multibindings.Multibinds;
import java.util.Set;
import javax.inject.Inject;

/* loaded from: classes2.dex */
public final class DefaultViewModelFactories {

    /* loaded from: classes2.dex */
    public interface ActivityEntryPoint {
        InternalFactoryFactory getHiltInternalFactoryFactory();
    }

    @Module
    /* loaded from: classes2.dex */
    interface ActivityModule {
        @Multibinds
        Set<String> viewModelKeys();
    }

    /* loaded from: classes2.dex */
    public interface FragmentEntryPoint {
        InternalFactoryFactory getHiltInternalFactoryFactory();
    }

    public static ViewModelProvider.Factory getActivityFactory(ComponentActivity activity, ViewModelProvider.Factory delegateFactory) {
        return ((ActivityEntryPoint) EntryPoints.get(activity, ActivityEntryPoint.class)).getHiltInternalFactoryFactory().fromActivity(activity, delegateFactory);
    }

    public static ViewModelProvider.Factory getFragmentFactory(Fragment fragment, ViewModelProvider.Factory delegateFactory) {
        return ((FragmentEntryPoint) EntryPoints.get(fragment, FragmentEntryPoint.class)).getHiltInternalFactoryFactory().fromFragment(fragment, delegateFactory);
    }

    /* loaded from: classes2.dex */
    public static final class InternalFactoryFactory {
        private final Application application;
        private final Set<String> keySet;
        private final ViewModelComponentBuilder viewModelComponentBuilder;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Inject
        public InternalFactoryFactory(Application application, Set<String> keySet, ViewModelComponentBuilder viewModelComponentBuilder) {
            this.application = application;
            this.keySet = keySet;
            this.viewModelComponentBuilder = viewModelComponentBuilder;
        }

        ViewModelProvider.Factory fromActivity(ComponentActivity activity, ViewModelProvider.Factory delegateFactory) {
            return getHiltViewModelFactory(activity, activity.getIntent() != null ? activity.getIntent().getExtras() : null, delegateFactory);
        }

        ViewModelProvider.Factory fromFragment(Fragment fragment, ViewModelProvider.Factory delegateFactory) {
            return getHiltViewModelFactory(fragment, fragment.getArguments(), delegateFactory);
        }

        private ViewModelProvider.Factory getHiltViewModelFactory(SavedStateRegistryOwner owner, Bundle defaultArgs, ViewModelProvider.Factory extensionDelegate) {
            if (extensionDelegate == null) {
                extensionDelegate = new SavedStateViewModelFactory(this.application, owner, defaultArgs);
            }
            return new HiltViewModelFactory(owner, defaultArgs, this.keySet, extensionDelegate, this.viewModelComponentBuilder);
        }
    }

    private DefaultViewModelFactories() {
    }
}
