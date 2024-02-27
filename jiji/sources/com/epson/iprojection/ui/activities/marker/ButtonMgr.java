package com.epson.iprojection.ui.activities.marker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;
import com.epson.iprojection.ui.activities.marker.config.EraserConfig;
import com.epson.iprojection.ui.activities.marker.config.IToolConfig;
import com.epson.iprojection.ui.activities.marker.config.PenConfig;
import com.epson.iprojection.ui.activities.marker.utils.SharedPrefMarkerConfig;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ButtonMgr implements View.OnClickListener, View.OnLongClickListener {
    public static final int BTN_ID_DEFAULT = 0;
    public static final int BTN_ID_ERASER = 2;
    public static final int BTN_ID_MAX = 2;
    public static final int BTN_ID_PEN1 = 0;
    public static final int BTN_ID_PEN2 = 1;
    private final Context _context;
    private final IPaintButtonClickListener _impl;
    private IGettableUndoRedo _implGettableUndoRedo;
    private int _lastSelectPenButtonId;
    private final ImageButton _redoBtn;
    private int _selectedToolButtonId;
    private final ArrayList<ToolButton> _toolButtonList;
    private final ImageButton _undoBtn;
    private static final int[] ENABLE_PEN_BTN_RES_IDs = {R.drawable.red_select, R.drawable.lime_select, R.drawable.blue_select, R.drawable.yellow_select, R.drawable.purple_select, R.drawable.aqua_select, R.drawable.green_select, R.drawable.pink_select, R.drawable.orange_select, R.drawable.brown_select, R.drawable.black_select, R.drawable.white_select};
    private static final int[] DISABLE_PEN_BTN_RES_IDs = {R.drawable.red, R.drawable.lime, R.drawable.blue, R.drawable.yellow, R.drawable.purple, R.drawable.aqua, R.drawable.green, R.drawable.pink, R.drawable.orange, R.drawable.brown, R.drawable.black, R.drawable.white};
    private static final int[] ENABLE_ERASER_BTN_RES_IDs = {R.drawable.eraser_select};
    private static final int[] DISABLE_ERASER_BTN_RES_IDs = {R.drawable.eraser};

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ToolButton {
        private final int[] _disShape;
        private final int[] _enaShape;
        private final ImageButton _imageButton;
        private final IToolConfig _toolConfig;

        public ToolButton(ImageButton imageButton, int[] iArr, int[] iArr2, IToolConfig iToolConfig) {
            this._imageButton = imageButton;
            this._enaShape = iArr;
            this._disShape = iArr2;
            this._toolConfig = iToolConfig;
        }

        public ImageButton getImageButton() {
            return this._imageButton;
        }

        public int getEnaShape() {
            int colorId = this._toolConfig.getColorId();
            if (colorId >= 0) {
                int[] iArr = this._enaShape;
                if (colorId < iArr.length) {
                    return iArr[colorId];
                }
                return -1;
            }
            return -1;
        }

        public int getDisShape() {
            int colorId = this._toolConfig.getColorId();
            if (colorId >= 0) {
                int[] iArr = this._disShape;
                if (colorId < iArr.length) {
                    return iArr[colorId];
                }
                return -1;
            }
            return -1;
        }

        public int getBtnId() {
            return this._toolConfig.getBtnId();
        }

        public IToolConfig getToolConfig() {
            return this._toolConfig;
        }

        public ConfigDefine.ConfigKind getKind() {
            return this._toolConfig.getKind();
        }
    }

    public ButtonMgr(Activity activity, IPaintButtonClickListener iPaintButtonClickListener) {
        ArrayList<ToolButton> arrayList = new ArrayList<>();
        this._toolButtonList = arrayList;
        this._selectedToolButtonId = 0;
        this._lastSelectPenButtonId = 0;
        this._context = activity.getApplicationContext();
        this._impl = iPaintButtonClickListener;
        int[] iArr = ENABLE_PEN_BTN_RES_IDs;
        int[] iArr2 = DISABLE_PEN_BTN_RES_IDs;
        arrayList.add(new ToolButton((ImageButton) activity.findViewById(R.id.btn_marker_pen1), iArr, iArr2, new PenConfig(activity, 0, 0)));
        arrayList.add(new ToolButton((ImageButton) activity.findViewById(R.id.btn_marker_pen2), iArr, iArr2, new PenConfig(activity, 1, 1)));
        arrayList.add(new ToolButton((ImageButton) activity.findViewById(R.id.btn_marker_eraser), ENABLE_ERASER_BTN_RES_IDs, DISABLE_ERASER_BTN_RES_IDs, new EraserConfig(activity, 2)));
        Iterator<ToolButton> it = arrayList.iterator();
        while (it.hasNext()) {
            ImageButton imageButton = it.next().getImageButton();
            imageButton.setOnClickListener(this);
            imageButton.setOnLongClickListener(this);
        }
        ImageButton imageButton2 = (ImageButton) activity.findViewById(R.id.btn_marker_undo);
        this._undoBtn = imageButton2;
        imageButton2.setOnClickListener(this);
        ImageButton imageButton3 = (ImageButton) activity.findViewById(R.id.btn_marker_redo);
        this._redoBtn = imageButton3;
        imageButton3.setOnClickListener(this);
    }

    public void setGettableUndoRedoImpl(IGettableUndoRedo iGettableUndoRedo) {
        this._implGettableUndoRedo = iGettableUndoRedo;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._undoBtn) {
            this._impl.onClickUndoButton();
            updateUndoRedoButton();
        } else if (view == this._redoBtn) {
            this._impl.onClickRedoButton();
            updateUndoRedoButton();
        } else {
            Iterator<ToolButton> it = this._toolButtonList.iterator();
            while (it.hasNext()) {
                ToolButton next = it.next();
                if (view == next.getImageButton()) {
                    selectedToolButton(next, next.getBtnId() == this._selectedToolButtonId);
                    return;
                }
            }
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        Iterator<ToolButton> it = this._toolButtonList.iterator();
        while (it.hasNext()) {
            ToolButton next = it.next();
            if (view == next.getImageButton()) {
                selectedToolButton(next, true);
                return true;
            }
        }
        return false;
    }

    private void selectedToolButton(ToolButton toolButton, boolean z) {
        setSelectedToolButtonId(toolButton.getBtnId());
        if (toolButton.getKind() == ConfigDefine.ConfigKind.PEN) {
            this._lastSelectPenButtonId = toolButton.getBtnId();
        }
        this._impl.onClickToolButton(z);
    }

    public void selectLastSelectPenButton() {
        Iterator<ToolButton> it = this._toolButtonList.iterator();
        while (it.hasNext()) {
            ToolButton next = it.next();
            if (next.getBtnId() == this._lastSelectPenButtonId) {
                selectedToolButton(next, false);
            }
        }
    }

    public void setBackgroundColorUpdate() {
        Iterator<ToolButton> it = this._toolButtonList.iterator();
        while (it.hasNext()) {
            ToolButton next = it.next();
            setBackgroundColor(next, next.getBtnId() != this._selectedToolButtonId);
        }
    }

    private void setBackgroundColor(ToolButton toolButton, boolean z) {
        ImageButton imageButton = toolButton.getImageButton();
        if (z) {
            imageButton.setImageResource(toolButton.getDisShape());
            imageButton.setBackground(null);
            return;
        }
        imageButton.setImageResource(toolButton.getEnaShape());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(1, Color.rgb(70, 100, 220));
        gradientDrawable.setColor(Color.rgb(197, 214, 244));
        imageButton.setBackground(gradientDrawable);
    }

    public void updateUndoRedoButton() {
        this._undoBtn.setEnabled(this._implGettableUndoRedo.canUndo());
        this._redoBtn.setEnabled(this._implGettableUndoRedo.canRedo());
    }

    public IToolConfig getToolConfig() {
        Iterator<ToolButton> it = this._toolButtonList.iterator();
        while (it.hasNext()) {
            ToolButton next = it.next();
            if (next.getBtnId() == this._selectedToolButtonId) {
                return next.getToolConfig();
            }
        }
        return null;
    }

    public void initialSelectedToolButtonId(Context context) {
        int readUsingToolID = SharedPrefMarkerConfig.readUsingToolID(context);
        this._selectedToolButtonId = readUsingToolID;
        if (readUsingToolID == 2) {
            setSelectedToolButtonId(0);
        }
        Iterator<ToolButton> it = this._toolButtonList.iterator();
        while (it.hasNext()) {
            ToolButton next = it.next();
            if (next.getBtnId() == this._selectedToolButtonId && next.getKind() == ConfigDefine.ConfigKind.PEN) {
                this._lastSelectPenButtonId = this._selectedToolButtonId;
            }
        }
    }

    private void setSelectedToolButtonId(int i) {
        this._selectedToolButtonId = i;
        SharedPrefMarkerConfig.writeUsingToolID(this._context, i);
    }
}
