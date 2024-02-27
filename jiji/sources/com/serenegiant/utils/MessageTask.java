package com.serenegiant.utils;

import android.util.Log;
import com.serenegiant.collections.ReentrantReadWriteList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public abstract class MessageTask implements Runnable {
    private static final boolean DEBUG = false;
    protected static final int REQUEST_TASK_NON = 0;
    protected static final int REQUEST_TASK_QUIT = -9;
    protected static final int REQUEST_TASK_RUN = -1;
    protected static final int REQUEST_TASK_RUN_AND_WAIT = -2;
    protected static final int REQUEST_TASK_START = -8;
    private static final String TAG = "MessageTask";
    private volatile boolean mFinished;
    private volatile boolean mIsRunning;
    private final int mMaxRequest;
    private final ReentrantReadWriteList<Request> mRequestPool;
    private final LinkedBlockingDeque<Request> mRequestQueue;
    private final Object mSync;
    private Thread mWorkerThread;
    private long mWorkerThreadId;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface MessageCallback {
        void onResult(Request request, Object obj);
    }

    /* loaded from: classes2.dex */
    public static class TaskBreak extends RuntimeException {
    }

    protected void onBeforeStop() {
    }

    protected boolean onError(Throwable th) {
        return true;
    }

    protected abstract void onInit(int i, int i2, Object obj);

    protected abstract void onRelease();

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract Object processRequest(int i, int i2, int i3, Object obj) throws TaskBreak;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static final class Request {
        int arg1;
        int arg2;
        MessageCallback callback;
        Object obj;
        int request;
        int request_for_result;
        Object result;

        private Request() {
            this.request_for_result = 0;
            this.request = 0;
        }

        public Request(int i, int i2, int i3, Object obj) {
            this.request = i;
            this.arg1 = i2;
            this.arg2 = i3;
            this.obj = obj;
            this.request_for_result = 0;
        }

        public void setResult(Object obj) {
            synchronized (this) {
                this.result = obj;
                this.request_for_result = 0;
                this.request = 0;
                MessageCallback messageCallback = this.callback;
                if (messageCallback != null) {
                    messageCallback.onResult(this, obj);
                }
                this.callback = null;
                notifyAll();
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof Request) {
                Request request = (Request) obj;
                return this.request == request.request && this.request_for_result == request.request_for_result && this.arg1 == request.arg1 && this.arg2 == request.arg2 && this.obj == request.obj;
            }
            return super.equals(obj);
        }

        public String toString() {
            return "Request{request=" + this.request + ", arg1=" + this.arg1 + ", arg2=" + this.arg2 + ", obj=" + this.obj + ", request_for_result=" + this.request_for_result + ", result=" + this.result + ", callback=" + this.callback + '}';
        }
    }

    public MessageTask() {
        this(-1, 0);
    }

    public MessageTask(int i) {
        this(-1, i);
    }

    public MessageTask(int i, int i2) {
        this.mSync = new Object();
        this.mMaxRequest = i;
        if (i > 0) {
            this.mRequestPool = new ReentrantReadWriteList<>();
            this.mRequestQueue = new LinkedBlockingDeque<>(i);
        } else {
            this.mRequestPool = new ReentrantReadWriteList<>();
            this.mRequestQueue = new LinkedBlockingDeque<>();
        }
        for (int i3 = 0; i3 < i2 && this.mRequestPool.add(new Request()); i3++) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init(int i, int i2, Object obj) {
        this.mFinished = false;
        this.mRequestQueue.offer(obtain(-8, i, i2, obj));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Request takeRequest() throws InterruptedException {
        return this.mRequestQueue.take();
    }

    public boolean waitReady() {
        boolean z;
        synchronized (this.mSync) {
            while (!this.mIsRunning && !this.mFinished) {
                try {
                    this.mSync.wait(500L);
                } catch (InterruptedException unused) {
                }
            }
            z = this.mIsRunning;
        }
        return z;
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public boolean isFinished() {
        return this.mFinished;
    }

    protected boolean isOnWorkerThread() {
        return this.mWorkerThreadId == Thread.currentThread().getId();
    }

    protected int getMaxRequest() {
        return this.mMaxRequest;
    }

    protected int getCurrentRequests() {
        return this.mRequestQueue.size();
    }

    @Override // java.lang.Runnable
    public void run() {
        Request request;
        this.mIsRunning = true;
        this.mFinished = false;
        try {
            request = this.mRequestQueue.take();
        } catch (InterruptedException unused) {
            this.mIsRunning = false;
            this.mFinished = true;
            request = null;
        }
        synchronized (this.mSync) {
            if (this.mIsRunning) {
                Thread currentThread = Thread.currentThread();
                this.mWorkerThread = currentThread;
                this.mWorkerThreadId = currentThread.getId();
                try {
                    onInit(request.arg1, request.arg2, request.obj);
                } catch (Exception e) {
                    Log.w(TAG, e);
                    this.mIsRunning = false;
                    this.mFinished = true;
                }
            }
            this.mSync.notifyAll();
        }
        if (this.mIsRunning) {
            try {
                onStart();
            } catch (Exception e2) {
                if (callOnError(e2)) {
                    this.mIsRunning = false;
                    this.mFinished = true;
                }
            }
        }
        while (this.mIsRunning) {
            try {
                Request takeRequest = takeRequest();
                int i = takeRequest.request;
                if (i == -9) {
                    break;
                } else if (i == -2) {
                    try {
                        takeRequest.setResult(processRequest(takeRequest.request_for_result, takeRequest.arg1, takeRequest.arg2, takeRequest.obj));
                    } catch (TaskBreak unused2) {
                        takeRequest.setResult(null);
                    } catch (Exception e3) {
                        takeRequest.setResult(null);
                        if (callOnError(e3)) {
                            break;
                        }
                    }
                    takeRequest.request_for_result = 0;
                    takeRequest.request = 0;
                    this.mRequestPool.add(takeRequest);
                } else if (i == -1) {
                    if (takeRequest.obj instanceof Runnable) {
                        try {
                            ((Runnable) takeRequest.obj).run();
                        } catch (Exception e4) {
                            if (callOnError(e4)) {
                                break;
                            }
                        }
                    } else {
                        continue;
                    }
                    takeRequest.request_for_result = 0;
                    takeRequest.request = 0;
                    this.mRequestPool.add(takeRequest);
                } else {
                    if (i != 0) {
                        try {
                            processRequest(takeRequest.request, takeRequest.arg1, takeRequest.arg2, takeRequest.obj);
                        } catch (Exception e5) {
                            if (callOnError(e5)) {
                                break;
                            }
                        }
                    } else {
                        continue;
                    }
                    takeRequest.request_for_result = 0;
                    takeRequest.request = 0;
                    this.mRequestPool.add(takeRequest);
                }
            } catch (TaskBreak | InterruptedException unused3) {
            }
        }
        boolean interrupted = Thread.interrupted();
        synchronized (this.mSync) {
            this.mWorkerThread = null;
            this.mWorkerThreadId = 0L;
            this.mIsRunning = false;
            this.mFinished = true;
        }
        if (!interrupted) {
            try {
                onBeforeStop();
                onStop();
            } catch (Exception e6) {
                callOnError(e6);
            }
        }
        try {
            onRelease();
        } catch (Exception unused4) {
        }
        synchronized (this.mSync) {
            this.mSync.notifyAll();
        }
    }

    protected boolean callOnError(Throwable th) {
        try {
            return onError(th);
        } catch (Exception unused) {
            return true;
        }
    }

    protected Request obtain(int i, int i2, int i3, Object obj) {
        Request removeLast = this.mRequestPool.removeLast();
        if (removeLast != null) {
            removeLast.request = i;
            removeLast.arg1 = i2;
            removeLast.arg2 = i3;
            removeLast.obj = obj;
            removeLast.request_for_result = 0;
            removeLast.result = null;
            removeLast.callback = null;
            return removeLast;
        }
        return new Request(i, i2, i3, obj);
    }

    public boolean offer(int i, int i2, int i3, Object obj) {
        return !this.mFinished && this.mRequestQueue.offer(obtain(i, i2, i3, obj));
    }

    public boolean offer(int i, int i2, Object obj) {
        return offer(i, i2, 0, obj);
    }

    public boolean offer(int i, int i2, int i3) {
        return offer(i, i2, i3, null);
    }

    public boolean offer(int i, int i2) {
        return offer(i, i2, 0, null);
    }

    public boolean offer(int i) {
        return offer(i, 0, 0, null);
    }

    public boolean offer(int i, Object obj) {
        return offer(i, 0, 0, obj);
    }

    public boolean offerFirst(int i, int i2, int i3, Object obj) {
        return !this.mFinished && this.mIsRunning && this.mRequestQueue.offerFirst(obtain(i, i2, i3, obj));
    }

    public Object offerAndWait(int i, int i2, int i3, Object obj) {
        if (this.mFinished || i <= 0) {
            return null;
        }
        Request obtain = obtain(-2, i2, i3, obj);
        if (!isOnWorkerThread()) {
            final Semaphore semaphore = new Semaphore(0);
            final AtomicReference atomicReference = new AtomicReference();
            obtain.request_for_result = i;
            obtain.result = null;
            obtain.callback = new MessageCallback() { // from class: com.serenegiant.utils.MessageTask.1
                @Override // com.serenegiant.utils.MessageTask.MessageCallback
                public void onResult(Request request, Object obj2) {
                    atomicReference.set(obj2);
                    semaphore.release();
                }
            };
            this.mRequestQueue.offer(obtain);
            while (this.mIsRunning && obtain.request_for_result != 0) {
                try {
                    semaphore.acquire(100);
                } catch (InterruptedException unused) {
                }
            }
            return atomicReference.get();
        }
        try {
            obtain.setResult(processRequest(obtain.request_for_result, obtain.arg1, obtain.arg2, obtain.obj));
        } catch (TaskBreak unused2) {
            obtain.setResult(null);
        } catch (Exception e) {
            obtain.setResult(null);
            callOnError(e);
        }
        return obtain.result;
    }

    public boolean queueEvent(Runnable runnable) {
        return (this.mFinished || runnable == null || !offer(-1, runnable)) ? false : true;
    }

    public void removeRequest(Request request) {
        Iterator<Request> it = this.mRequestQueue.iterator();
        while (it.hasNext()) {
            Request next = it.next();
            if (!this.mIsRunning || this.mFinished || !this.mRequestQueue.contains(request)) {
                return;
            }
            if (next.equals(request)) {
                this.mRequestQueue.remove(next);
                this.mRequestPool.add(next);
            }
        }
    }

    public void removeRequest(int i) {
        Iterator<Request> it = this.mRequestQueue.iterator();
        while (it.hasNext()) {
            Request next = it.next();
            if (!this.mIsRunning || this.mFinished) {
                return;
            }
            if (next.request == i) {
                this.mRequestQueue.remove(next);
                this.mRequestPool.add(next);
            }
        }
    }

    public void release() {
        release(false);
    }

    public void release(boolean z) {
        Thread thread;
        boolean z2 = this.mIsRunning;
        this.mIsRunning = false;
        if (this.mFinished) {
            return;
        }
        this.mRequestQueue.clear();
        this.mRequestQueue.offerFirst(obtain(-9, 0, 0, null));
        synchronized (this.mSync) {
            if (z2) {
                long id = Thread.currentThread().getId();
                Thread thread2 = this.mWorkerThread;
                if ((thread2 != null ? thread2.getId() : id) != id) {
                    if (z && (thread = this.mWorkerThread) != null) {
                        thread.interrupt();
                    }
                    while (!this.mFinished) {
                        try {
                            this.mSync.wait(300L);
                        } catch (InterruptedException unused) {
                        }
                    }
                }
            }
        }
    }

    public void releaseSelf() {
        this.mIsRunning = false;
        if (this.mFinished) {
            return;
        }
        this.mRequestQueue.clear();
        this.mRequestQueue.offerFirst(obtain(-9, 0, 0, null));
    }

    public void userBreak() throws TaskBreak {
        throw new TaskBreak();
    }
}
