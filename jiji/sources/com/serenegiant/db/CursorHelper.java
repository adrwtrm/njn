package com.serenegiant.db;

import android.database.Cursor;
import android.util.Log;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;

/* loaded from: classes2.dex */
public final class CursorHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "CursorHelper";

    private CursorHelper() {
    }

    public static String get(Cursor cursor, String str, String str2) {
        if (cursor == null || cursor.isClosed()) {
            return str2;
        }
        try {
            return cursor.getString(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return str2;
        }
    }

    public static CharSequence get(Cursor cursor, String str, CharSequence charSequence) {
        if (cursor == null || cursor.isClosed()) {
            return charSequence;
        }
        try {
            return cursor.getString(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return charSequence;
        }
    }

    public static String getString(Cursor cursor, String str, CharSequence charSequence) {
        CharSequence charSequence2 = get(cursor, str, charSequence);
        if (charSequence2 != null) {
            return charSequence2.toString();
        }
        return null;
    }

    public static int get(Cursor cursor, String str, int i) {
        if (cursor == null || cursor.isClosed()) {
            return i;
        }
        try {
            return cursor.getInt(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return i;
        }
    }

    public static short get(Cursor cursor, String str, short s) {
        if (cursor == null || cursor.isClosed()) {
            return s;
        }
        try {
            return cursor.getShort(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return s;
        }
    }

    public static long get(Cursor cursor, String str, long j) {
        if (cursor == null || cursor.isClosed()) {
            return j;
        }
        try {
            return cursor.getLong(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return j;
        }
    }

    public static float get(Cursor cursor, String str, float f) {
        if (cursor == null || cursor.isClosed()) {
            return f;
        }
        try {
            return cursor.getFloat(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return f;
        }
    }

    public static double get(Cursor cursor, String str, double d) {
        if (cursor == null || cursor.isClosed()) {
            return d;
        }
        try {
            return cursor.getDouble(cursor.getColumnIndexOrThrow(str));
        } catch (Exception unused) {
            return d;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001f, code lost:
        r0 = r5.getPosition();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int findPositionFromId(android.database.Cursor r5, long r6) {
        /*
            r0 = -1
            if (r5 == 0) goto L34
            boolean r1 = r5.isClosed()
            if (r1 != 0) goto L34
            int r1 = r5.getPosition()
            boolean r2 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L2f
            if (r2 == 0) goto L2b
        L13:
            java.lang.String r2 = "_id"
            r3 = 0
            long r2 = get(r5, r2, r3)     // Catch: java.lang.Throwable -> L2f
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 != 0) goto L25
            int r6 = r5.getPosition()     // Catch: java.lang.Throwable -> L2f
            r0 = r6
            goto L2b
        L25:
            boolean r2 = r5.moveToNext()     // Catch: java.lang.Throwable -> L2f
            if (r2 != 0) goto L13
        L2b:
            r5.moveToPosition(r1)
            goto L34
        L2f:
            r6 = move-exception
            r5.moveToPosition(r1)
            throw r6
        L34:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.db.CursorHelper.findPositionFromId(android.database.Cursor, long):int");
    }

    public static void dumpCursor(Cursor cursor) {
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        int columnCount = cursor.getColumnCount();
        String[] columnNames = cursor.getColumnNames();
        int i = 0;
        do {
            sb.setLength(0);
            sb.append("row=").append(i).append(", ");
            for (int i2 = 0; i2 < columnCount; i2++) {
                int type = cursor.getType(i2);
                if (type == 0) {
                    sb.append(columnNames[i2]).append("=").append("NULL");
                } else if (type == 1) {
                    sb.append(columnNames[i2]).append("=").append(cursor.getLong(i2));
                } else if (type == 2) {
                    sb.append(columnNames[i2]).append("=").append(cursor.getDouble(i2));
                } else if (type == 3) {
                    sb.append(columnNames[i2]).append("=").append(cursor.getString(i2));
                } else if (type == 4) {
                    sb.append(columnNames[i2]).append("=").append("BLOB");
                } else {
                    sb.append(columnNames[i2]).append("=").append("UNKNOWN");
                }
                sb.append(", ");
            }
            Log.v(TAG, "dumpCursor:" + ((Object) sb));
            i++;
        } while (cursor.moveToNext());
    }

    public static String toString(Cursor cursor) {
        if (cursor == null) {
            return "{null}";
        }
        if (cursor.isClosed()) {
            return "{closed}";
        }
        if (cursor.isBeforeFirst()) {
            return "{before first}";
        }
        if (cursor.isAfterLast()) {
            return "{after last}";
        }
        StringBuilder sb = new StringBuilder("{");
        int columnCount = cursor.getColumnCount();
        String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnCount; i++) {
            int type = cursor.getType(i);
            if (type == 0) {
                sb.append(columnNames[i]).append("=NULL");
            } else if (type == 1) {
                sb.append(columnNames[i]).append("=").append(cursor.getLong(i));
            } else if (type == 2) {
                sb.append(columnNames[i]).append("=").append(cursor.getDouble(i));
            } else if (type == 3) {
                sb.append(columnNames[i]).append("=").append(cursor.getString(i));
            } else if (type == 4) {
                sb.append(columnNames[i]).append("=BLOB");
            } else {
                sb.append(columnNames[i]).append("=UNKNOWN");
            }
            if (i < columnCount - 1) {
                sb.append(RemotePrefUtils.SEPARATOR);
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
