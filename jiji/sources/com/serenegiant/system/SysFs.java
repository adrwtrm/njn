package com.serenegiant.system;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class SysFs {
    private static final boolean DEBUG = false;
    private static final String TAG = "SysFs";
    private static final Map<String, WeakReference<ReentrantReadWriteLock>> sSysFs = new HashMap();
    protected final ReentrantReadWriteLock mLock;
    private final String mName;
    private final String mPath;
    private final Lock mReadLock;
    private final Lock mWriteLock;

    public void release() {
    }

    public SysFs(String str) throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock;
        WeakReference<ReentrantReadWriteLock> weakReference;
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            throw new IOException(str + " does not exist or can't read.");
        }
        this.mPath = str;
        this.mName = file.getName();
        Map<String, WeakReference<ReentrantReadWriteLock>> map = sSysFs;
        synchronized (map) {
            reentrantReadWriteLock = null;
            if (map.containsKey(str) && (weakReference = map.get(str)) != null) {
                reentrantReadWriteLock = weakReference.get();
            }
            if (reentrantReadWriteLock == null) {
                reentrantReadWriteLock = new ReentrantReadWriteLock();
                map.put(str, new WeakReference<>(reentrantReadWriteLock));
            }
        }
        this.mLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public String getPath() {
        return this.mPath;
    }

    public String getName() {
        return this.mName;
    }

    public String readString(String str) throws IOException {
        this.mReadLock.lock();
        try {
            FileReader fileReader = new FileReader(getPath(str));
            String readLine = new BufferedReader(fileReader).readLine();
            fileReader.close();
            return readLine;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public String readString() throws IOException {
        return readString(null);
    }

    public byte[] readBytes(String str) throws IOException {
        this.mReadLock.lock();
        try {
            byte[] bArr = new byte[512];
            MyByteArrayOutputStream myByteArrayOutputStream = new MyByteArrayOutputStream(1024);
            FileInputStream fileInputStream = new FileInputStream(getPath(str));
            for (int available = fileInputStream.available(); available > 0; available = fileInputStream.available()) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    myByteArrayOutputStream.write(bArr, 0, read);
                }
            }
            byte[] byteArray = myByteArrayOutputStream.toByteArray();
            fileInputStream.close();
            return byteArray;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public byte[] readBytes() throws IOException {
        return readBytes(null);
    }

    public byte readByte(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            byte readByte = dataInputStream.readByte();
            dataInputStream.close();
            return readByte;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public byte readByte() throws IOException {
        return readByte(null);
    }

    public short readShort(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            short readShort = dataInputStream.readShort();
            dataInputStream.close();
            return readShort;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public short readShort() throws IOException {
        return readShort(null);
    }

    public int readInt(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            int readInt = dataInputStream.readInt();
            dataInputStream.close();
            return readInt;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public int readInt() throws IOException {
        return readInt(null);
    }

    public long readLong(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            long readLong = dataInputStream.readLong();
            dataInputStream.close();
            return readLong;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public long readLong() throws IOException {
        return readLong(null);
    }

    public float readFloat(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            float readFloat = dataInputStream.readFloat();
            dataInputStream.close();
            return readFloat;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public float readFloat() throws IOException {
        return readFloat(null);
    }

    public double readDouble(String str) throws IOException {
        this.mReadLock.lock();
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(getPath(str)));
            double readDouble = dataInputStream.readDouble();
            dataInputStream.close();
            return readDouble;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public double readDouble() throws IOException {
        return readDouble(null);
    }

    public void write(String str, byte[] bArr) throws IOException {
        write(str, bArr, 0, bArr.length);
    }

    public void write(byte[] bArr) throws IOException {
        write(null, bArr, 0, bArr.length);
    }

    public void write(String str, byte[] bArr, int i, int i2) throws IOException {
        this.mWriteLock.lock();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getPath(str));
            fileOutputStream.write(bArr, i, i2);
            fileOutputStream.flush();
            fileOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        write(null, bArr, i, i2);
    }

    public void write(String str, String str2) throws IOException {
        this.mWriteLock.lock();
        try {
            FileWriter fileWriter = new FileWriter(getPath(str));
            fileWriter.write(str2);
            fileWriter.flush();
            fileWriter.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(String str) throws IOException {
        write((String) null, str);
    }

    public void write(String str, boolean z) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeBoolean(z);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(boolean z) throws IOException {
        write((String) null, z);
    }

    public void write(String str, byte b) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeByte(b);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(byte b) throws IOException {
        write((String) null, b);
    }

    public void write(String str, short s) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeShort(s);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(short s) throws IOException {
        write((String) null, s);
    }

    public void write(String str, int i) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeInt(i);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(int i) throws IOException {
        write((String) null, i);
    }

    public void write(String str, float f) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeFloat(f);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(float f) throws IOException {
        write((String) null, f);
    }

    public void write(String str, double d) throws IOException {
        this.mWriteLock.lock();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(getPath(str)));
            dataOutputStream.writeDouble(d);
            dataOutputStream.flush();
            dataOutputStream.close();
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public void write(double d) throws IOException {
        write((String) null, d);
    }

    public String toString() {
        try {
            return String.format(Locale.US, "%s=%s", this.mPath, readString());
        } catch (IOException unused) {
            return String.format(Locale.US, "%s=null", this.mPath);
        }
    }

    private File getPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return new File(this.mPath);
        }
        return new File(new File(this.mPath), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class MyByteArrayOutputStream extends ByteArrayOutputStream {
        public MyByteArrayOutputStream() {
        }

        public MyByteArrayOutputStream(int i) {
            super(i);
        }

        public ByteBuffer getByteBuffer() {
            return ByteBuffer.wrap(this.buf, 0, size());
        }

        public byte[] getBuffer() {
            return this.buf;
        }
    }
}
