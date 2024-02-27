package com.epson.iprojection.ui.activities.marker;

import android.graphics.Bitmap;
import com.epson.iprojection.common.Lg;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class UndoRedoMgr implements IGettableUndoRedo {
    private static final int MAX_LIST_SIZE = 11;
    private int _listPos = -1;
    private final LinkedList<Bitmap> _list = new LinkedList<>();

    /* loaded from: classes.dex */
    public class CopyBitmapAllocationException extends Exception {
        public CopyBitmapAllocationException(String str) {
            super(str);
            Lg.e(str);
        }
    }

    private void resetListPos() {
        while (this._list.size() != 0 && this._list.size() - 1 > this._listPos) {
            this._list.removeLast();
        }
    }

    public void add(Bitmap bitmap) throws CopyBitmapAllocationException {
        resetListPos();
        while (this._list.size() >= 11) {
            this._list.removeFirst();
            this._listPos--;
        }
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        if (copy == null) {
            throw new CopyBitmapAllocationException("Error copying allocated Bitmap.");
        }
        this._list.add(copy);
        this._listPos++;
    }

    public void clear() {
        this._list.clear();
        this._listPos = -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.IGettableUndoRedo
    public boolean canUndo() {
        return this._listPos >= 1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.IGettableUndoRedo
    public boolean canRedo() {
        return this._listPos < this._list.size() - 1;
    }

    public Bitmap undo() throws CopyBitmapAllocationException {
        if (canUndo()) {
            int i = this._listPos - 1;
            this._listPos = i;
            Bitmap copy = this._list.get(i).copy(Bitmap.Config.ARGB_8888, true);
            if (copy != null) {
                return copy;
            }
            throw new CopyBitmapAllocationException("Error copying allocated Bitmap.");
        }
        return null;
    }

    public Bitmap redo() throws CopyBitmapAllocationException {
        if (canRedo()) {
            int i = this._listPos + 1;
            this._listPos = i;
            Bitmap copy = this._list.get(i).copy(Bitmap.Config.ARGB_8888, true);
            if (copy != null) {
                return copy;
            }
            throw new CopyBitmapAllocationException("Error copying allocated Bitmap.");
        }
        return null;
    }
}
