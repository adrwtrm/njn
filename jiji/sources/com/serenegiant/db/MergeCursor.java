package com.serenegiant.db;

import android.database.AbstractCursor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class MergeCursor extends AbstractCursor {
    private static final boolean DEBUG = false;
    private static final String TAG = "MergeCursor";
    private Cursor mCursor;
    private final ArrayList<Cursor> mCursors;
    private final DataSetObserver mDefaultObserver;
    private int mIndex;
    private DataSetObserver mObserver;

    public MergeCursor(ArrayList<Cursor> arrayList) {
        DataSetObserver dataSetObserver = new DataSetObserver() { // from class: com.serenegiant.db.MergeCursor.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                MergeCursor.this.mPos = -1;
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                MergeCursor.this.mPos = -1;
            }
        };
        this.mDefaultObserver = dataSetObserver;
        arrayList = arrayList == null ? new ArrayList<>() : arrayList;
        this.mCursors = arrayList;
        this.mCursor = !arrayList.isEmpty() ? arrayList.get(0) : null;
        this.mObserver = dataSetObserver;
        registerDataSetObserver();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        int i;
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            i = 0;
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    i += next.getCount();
                }
            }
        }
        return i;
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        synchronized (this.mCursors) {
            this.mCursor = null;
            int i3 = -1;
            this.mIndex = -1;
            Iterator<Cursor> it = this.mCursors.iterator();
            int i4 = 0;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Cursor next = it.next();
                i3++;
                if (next != null) {
                    if (i2 < next.getCount() + i4) {
                        this.mCursor = next;
                        this.mIndex = i3;
                        break;
                    }
                    i4 += next.getCount();
                }
            }
            Cursor cursor = this.mCursor;
            if (cursor != null) {
                return cursor.moveToPosition(i2 - i4);
            }
            return false;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String getString(int i) {
        String string;
        synchronized (this.mCursors) {
            string = this.mCursor.getString(i);
        }
        return string;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        short s;
        synchronized (this.mCursors) {
            s = this.mCursor.getShort(i);
        }
        return s;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        int i2;
        synchronized (this.mCursors) {
            i2 = this.mCursor.getInt(i);
        }
        return i2;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        long j;
        synchronized (this.mCursors) {
            j = this.mCursor.getLong(i);
        }
        return j;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        float f;
        synchronized (this.mCursors) {
            f = this.mCursor.getFloat(i);
        }
        return f;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        double d;
        synchronized (this.mCursors) {
            d = this.mCursor.getDouble(i);
        }
        return d;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        int type;
        synchronized (this.mCursors) {
            type = this.mCursor.getType(i);
        }
        return type;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        boolean isNull;
        synchronized (this.mCursors) {
            isNull = this.mCursor.isNull(i);
        }
        return isNull;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        byte[] blob;
        synchronized (this.mCursors) {
            blob = this.mCursor.getBlob(i);
        }
        return blob;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        synchronized (this.mCursors) {
            Cursor cursor = this.mCursor;
            if (cursor != null) {
                return cursor.getColumnNames();
            }
            return new String[0];
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.deactivate();
                }
            }
        }
        super.deactivate();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null && !next.isClosed()) {
                    next.close();
                }
            }
            this.mCursors.clear();
        }
        super.close();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.registerContentObserver(contentObserver);
                }
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.unregisterContentObserver(contentObserver);
                }
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObserver = dataSetObserver;
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.registerDataSetObserver(dataSetObserver);
                }
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mObserver = this.mDefaultObserver;
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.unregisterDataSetObserver(dataSetObserver);
                }
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null && !next.requery()) {
                    return false;
                }
            }
            return true;
        }
    }

    public int getCurrentIndex() {
        int i;
        synchronized (this.mCursors) {
            i = this.mIndex;
        }
        return i;
    }

    public Cursor getCurrentCursor() {
        Cursor cursor;
        synchronized (this.mCursors) {
            cursor = this.mCursor;
        }
        return cursor;
    }

    public int getCursorCount() {
        int size;
        synchronized (this.mCursors) {
            size = this.mCursors.size();
        }
        return size;
    }

    public Cursor getCursor(int i) {
        Cursor cursor;
        synchronized (this.mCursors) {
            if (i >= 0) {
                try {
                    if (i < this.mCursors.size()) {
                        cursor = this.mCursors.get(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            cursor = null;
        }
        return cursor;
    }

    public void add(Cursor cursor) {
        synchronized (this.mCursors) {
            this.mCursors.add(cursor);
            cursor.registerDataSetObserver(this.mObserver);
        }
    }

    public Cursor add(int i, Cursor cursor) {
        Cursor cursor2;
        synchronized (this.mCursors) {
            if (this.mCursors.size() <= i) {
                this.mCursors.ensureCapacity(i + 1);
                for (int size = this.mCursors.size(); size <= i; size++) {
                    this.mCursors.add(null);
                }
            }
            cursor2 = getCursor(i);
            this.mCursors.add(i, cursor);
            cursor.registerDataSetObserver(this.mObserver);
            if (cursor2 != null) {
                cursor2.unregisterDataSetObserver(this.mObserver);
            }
        }
        return cursor2;
    }

    public void remove(Cursor cursor) {
        synchronized (this.mCursors) {
            cursor.unregisterDataSetObserver(this.mObserver);
            this.mCursors.remove(cursor);
        }
    }

    public Cursor remove(int i) {
        Cursor remove;
        synchronized (this.mCursors) {
            remove = this.mCursors.remove(i);
            if (remove != null) {
                remove.unregisterDataSetObserver(this.mObserver);
            }
        }
        return remove;
    }

    private void registerDataSetObserver() {
        synchronized (this.mCursors) {
            Iterator<Cursor> it = this.mCursors.iterator();
            while (it.hasNext()) {
                Cursor next = it.next();
                if (next != null) {
                    next.registerDataSetObserver(this.mObserver);
                }
            }
        }
    }
}
