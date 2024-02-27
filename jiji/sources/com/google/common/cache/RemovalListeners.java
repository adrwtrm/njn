package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class RemovalListeners {
    private RemovalListeners() {
    }

    public static <K, V> RemovalListener<K, V> asynchronous(final RemovalListener<K, V> removalListener, final Executor executor) {
        Preconditions.checkNotNull(removalListener);
        Preconditions.checkNotNull(executor);
        return new RemovalListener() { // from class: com.google.common.cache.RemovalListeners$$ExternalSyntheticLambda1
            @Override // com.google.common.cache.RemovalListener
            public final void onRemoval(RemovalNotification removalNotification) {
                RemovalListeners.lambda$asynchronous$1(executor, removalListener, removalNotification);
            }
        };
    }

    public static /* synthetic */ void lambda$asynchronous$0(RemovalListener removalListener, RemovalNotification removalNotification) {
        removalListener.onRemoval(removalNotification);
    }

    public static /* synthetic */ void lambda$asynchronous$1(Executor executor, final RemovalListener removalListener, final RemovalNotification removalNotification) {
        executor.execute(new Runnable() { // from class: com.google.common.cache.RemovalListeners$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RemovalListeners.lambda$asynchronous$0(RemovalListener.this, removalNotification);
            }
        });
    }
}
