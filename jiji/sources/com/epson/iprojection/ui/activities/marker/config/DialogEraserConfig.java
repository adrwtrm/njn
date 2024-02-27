package com.epson.iprojection.ui.activities.marker.config;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.DipUtils;

/* loaded from: classes.dex */
public class DialogEraserConfig extends BaseDialogToolConfig implements View.OnClickListener {
    protected Button _btnClearAll;
    protected final ImageView[] _btns;
    private ViewTreeObserver.OnGlobalLayoutListener _listenerConfigView;
    protected int _selectWidthPos;
    protected int _width;

    @Override // com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig
    protected int getDialogLayoutId() {
        return R.layout.dialog_eraser_config;
    }

    public DialogEraserConfig(Activity activity, IToolConfig iToolConfig, IUpdateConfigListener iUpdateConfigListener) {
        super(activity, iToolConfig, iUpdateConfigListener);
        this._btns = new ImageView[ConfigDefine.ERASER_WIDTH_N];
        initWidth();
        initButtons();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig
    protected void setBackGround() {
        final View findViewById = this._layout.findViewById(R.id.eraser_config_view);
        this._listenerConfigView = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.epson.iprojection.ui.activities.marker.config.DialogEraserConfig$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                DialogEraserConfig.this.m94xc4e5da01(findViewById);
            }
        };
        findViewById.getViewTreeObserver().addOnGlobalLayoutListener(this._listenerConfigView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setBackGround$0$com-epson-iprojection-ui-activities-marker-config-DialogEraserConfig  reason: not valid java name */
    public /* synthetic */ void m94xc4e5da01(View view) {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this._listenerConfigView);
        this._listenerConfigView = null;
    }

    protected void initWidth() {
        this._width = this._config.getWidth();
        for (int i = 0; i < ConfigDefine.ERASER_WIDTH_N; i++) {
            this._btns[i] = (ImageView) this._layout.findViewById(ConfigDefine.ERASER_BTN_IDs[i]);
            if (ConfigDefine.ERASER_WIDTH[i] == this._width) {
                this._selectWidthPos = i;
                setEraserIcon(i, true);
            } else {
                setEraserIcon(i, false);
            }
            this._btns[i].setOnClickListener(this);
        }
    }

    protected void initButtons() {
        Button button = (Button) this._layout.findViewById(R.id.btn_marker_clearAll);
        this._btnClearAll = button;
        button.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnClearAll) {
            this._impl.onClickClearAllButton();
            dismiss();
            return;
        }
        for (int i = 0; i < ConfigDefine.ERASER_WIDTH_N; i++) {
            if (view == this._btns[i]) {
                setEraserIcon(this._selectWidthPos, false);
                setEraserIcon(i, true);
                this._selectWidthPos = i;
                this._width = ConfigDefine.ERASER_WIDTH[i];
                this._config.saveWidth(this._width);
                return;
            }
        }
    }

    private void setEraserIcon(int i, boolean z) {
        int i2 = z ? R.drawable.tool_select : R.drawable.tool_deselect;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap decodeResource = BitmapFactory.decodeResource(this._activity.getResources(), i2, options);
        Canvas canvas = new Canvas(decodeResource);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-1);
        canvas.drawCircle(decodeResource.getWidth() / 2.0f, decodeResource.getHeight() / 2.0f, DipUtils.dp2px(this._activity, ConfigDefine.ERASER_WIDTH[i]) / 2.0f, paint);
        this._btns[i].setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this._btns[i].setImageBitmap(decodeResource);
    }
}
