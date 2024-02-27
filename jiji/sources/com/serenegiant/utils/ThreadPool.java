package com.serenegiant.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class ThreadPool {
    private static final int CORE_POOL_SIZE = 1;
    private static PausableThreadPoolExecutor EXECUTOR = null;
    private static final int KEEP_ALIVE_TIME_SECS = 10;
    private static final int MAX_POOL_SIZE = 32;

    static {
        getInstance();
    }

    private ThreadPool() {
    }

    private static synchronized PausableThreadPoolExecutor getInstance() {
        PausableThreadPoolExecutor pausableThreadPoolExecutor;
        synchronized (ThreadPool.class) {
            if (EXECUTOR == null) {
                EXECUTOR = new PausableThreadPoolExecutor(1, 32, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            }
            pausableThreadPoolExecutor = EXECUTOR;
        }
        return pausableThreadPoolExecutor;
    }

    public static synchronized boolean isShutdown() {
        boolean z;
        synchronized (ThreadPool.class) {
            PausableThreadPoolExecutor pausableThreadPoolExecutor = EXECUTOR;
            if (pausableThreadPoolExecutor != null) {
                z = pausableThreadPoolExecutor.isShutdown();
            }
        }
        return z;
    }

    public static synchronized void shutdown() {
        synchronized (ThreadPool.class) {
            PausableThreadPoolExecutor pausableThreadPoolExecutor = EXECUTOR;
            if (pausableThreadPoolExecutor != null) {
                pausableThreadPoolExecutor.resume();
                EXECUTOR.shutdown();
                EXECUTOR = null;
            }
        }
    }

    public static synchronized List<Runnable> shutdownNow() {
        ArrayList arrayList;
        synchronized (ThreadPool.class) {
            arrayList = new ArrayList();
            PausableThreadPoolExecutor pausableThreadPoolExecutor = EXECUTOR;
            if (pausableThreadPoolExecutor != null) {
                List<Runnable> shutdownNow = pausableThreadPoolExecutor.shutdownNow();
                if (shutdownNow != null) {
                    arrayList.addAll(shutdownNow);
                }
                EXECUTOR = null;
            }
        }
        return arrayList;
    }

    public void resume() {
        getInstance().resume();
    }

    public void pause() {
        getInstance().pause();
    }

    public static void setCorePoolSize(int i) {
        getInstance().setCorePoolSize(i);
    }

    public static void setMaximumPoolSize(int i) {
        getInstance().setMaximumPoolSize(i);
    }

    public static void setKeepAliveTime(long j, TimeUnit timeUnit) {
        getInstance().setKeepAliveTime(j, timeUnit);
    }

    public static void preStartAllCoreThreads() {
        getInstance().prestartAllCoreThreads();
    }

    public static void queueEvent(Runnable runnable) throws RejectedExecutionException {
        getInstance().execute(runnable);
    }

    public static boolean removeEvent(Runnable runnable) {
        return getInstance().remove(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class PausableThreadPoolExecutor extends ThreadPoolExecutor {
        private boolean isPaused;
        private ReentrantLock pauseLock;
        private Condition unpaused;

        public PausableThreadPoolExecutor(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
            super(i, i2, j, timeUnit, blockingQueue);
            ReentrantLock reentrantLock = new ReentrantLock();
            this.pauseLock = reentrantLock;
            this.unpaused = reentrantLock.newCondition();
            this.isPaused = false;
            allowCoreThreadTimeOut(true);
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        protected void beforeExecute(Thread thread, Runnable runnable) {
            super.beforeExecute(thread, runnable);
            this.pauseLock.lock();
            while (this.isPaused) {
                try {
                    try {
                        this.unpaused.await();
                    } catch (InterruptedException unused) {
                        thread.interrupt();
                    }
                } finally {
                    this.pauseLock.unlock();
                }
            }
        }

        public void pause() {
            this.pauseLock.lock();
            try {
                this.isPaused = true;
            } finally {
                this.pauseLock.unlock();
            }
        }

        public void resume() {
            this.pauseLock.lock();
            try {
                this.isPaused = false;
                this.unpaused.signalAll();
            } finally {
                this.pauseLock.unlock();
            }
        }
    }
}
