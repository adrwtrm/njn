package com.epson.iprojection.ui.activities.marker.config;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.ui.common.uiparts.RepeatButton;

/* loaded from: classes.dex */
public class DialogPenConfig extends BaseDialogToolConfig implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    protected RepeatButton _btnMinus;
    protected RepeatButton _btnPlus;
    protected final ImageView[] _btns;
    private ViewTreeObserver.OnGlobalLayoutListener _listenerConfigView;
    private ViewTreeObserver.OnGlobalLayoutListener _listenerSampleImage;
    protected RadioGroup _radioGroup;
    protected SeekBar _seekBar;
    protected int _selectPallet;
    protected TextView _textWidth;
    protected ImageView _viewSampleImage;
    protected int _width;

    @Override // com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig
    protected int getDialogLayoutId() {
        return R.layout.dialog_pen_config;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public DialogPenConfig(Activity activity, IToolConfig iToolConfig, IUpdateConfigListener iUpdateConfigListener) {
        super(activity, iToolConfig, iUpdateConfigListener);
        this._btns = new ImageView[ConfigDefine.PEN_COLOR_N];
        initPallet();
        initWidth();
        initRadioButtons();
        initButtons();
        initSampleImage();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig
    protected void setBackGround() {
        final View findViewById = this._layout.findViewById(R.id.pen_config_view);
        this._listenerConfigView = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.epson.iprojection.ui.activities.marker.config.DialogPenConfig$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                DialogPenConfig.this.m96x4fe33988(findViewById);
            }
        };
        findViewById.getViewTreeObserver().addOnGlobalLayoutListener(this._listenerConfigView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setBackGround$0$com-epson-iprojection-ui-activities-marker-config-DialogPenConfig  reason: not valid java name */
    public /* synthetic */ void m96x4fe33988(View view) {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this._listenerConfigView);
        this._listenerConfigView = null;
    }

    protected void initPallet() {
        this._selectPallet = this._config.getColorId();
        for (int i = 0; i < ConfigDefine.PEN_COLOR_N; i++) {
            this._btns[i] = (ImageView) this._layout.findViewById(ConfigDefine.PEN_BTN_IDs[i]);
            this._btns[i].setBackgroundColor(this._config.getColor(i));
            if (i == this._selectPallet) {
                this._btns[i].setImageResource(R.drawable.tool_select);
            } else {
                this._btns[i].setImageResource(R.drawable.tool_deselect);
            }
            this._btns[i].setOnClickListener(this);
        }
    }

    protected void initWidth() {
        this._width = this._config.getWidth();
        TextView textView = (TextView) this._layout.findViewById(R.id.txt_width);
        this._textWidth = textView;
        textView.setText(this._activity.getString(R.string._Width_) + ": " + this._width);
        SeekBar seekBar = (SeekBar) this._layout.findViewById(R.id.paint_config_width);
        this._seekBar = seekBar;
        seekBar.setMax(this._config.getWidthMax() - 1);
        this._seekBar.setProgress(this._width - 1);
        this._seekBar.setOnSeekBarChangeListener(this);
    }

    protected void initButtons() {
        RepeatButton repeatButton = (RepeatButton) this._layout.findViewById(R.id.btn_minus);
        this._btnMinus = repeatButton;
        repeatButton.setOnClickListener(this);
        RepeatButton repeatButton2 = (RepeatButton) this._layout.findViewById(R.id.btn_plus);
        this._btnPlus = repeatButton2;
        repeatButton2.setOnClickListener(this);
    }

    protected void initRadioButtons() {
        RadioGroup radioGroup = (RadioGroup) this._layout.findViewById(R.id.radio_group_alpha);
        this._radioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) this._layout.findViewById(this._config.hasAlpha() ? R.id.radio_marker_pen_configAlpha : R.id.radio_marker_pen_configNoAlpha)).setChecked(true);
    }

    protected void initSampleImage() {
        this._viewSampleImage = (ImageView) this._layout.findViewById(R.id.view_sample_image);
        this._listenerSampleImage = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.epson.iprojection.ui.activities.marker.config.DialogPenConfig$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                DialogPenConfig.this.m95x3a447f5a();
            }
        };
        this._viewSampleImage.getViewTreeObserver().addOnGlobalLayoutListener(this._listenerSampleImage);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$initSampleImage$1$com-epson-iprojection-ui-activities-marker-config-DialogPenConfig  reason: not valid java name */
    public /* synthetic */ void m95x3a447f5a() {
        updateSampleImage();
        this._viewSampleImage.getViewTreeObserver().removeOnGlobalLayoutListener(this._listenerSampleImage);
        this._listenerSampleImage = null;
    }

    protected void setWidthText(int i) {
        this._textWidth.setText(this._activity.getString(R.string._Width_) + ": " + (i + 1));
    }

    protected int getShowWidthViewColor() {
        int color = this._config.getColor(this._selectPallet);
        return Color.argb(this._config.getAlpha(), Color.red(color), Color.green(color), Color.blue(color));
    }

    protected void updateSampleImage() {
        ImageView imageView = this._viewSampleImage;
        if (imageView == null || imageView.getWidth() <= 0) {
            return;
        }
        int width = this._viewSampleImage.getWidth();
        int height = this._viewSampleImage.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(getShowWidthViewColor());
        int dp2px = DipUtils.dp2px(this._activity, this._width);
        int i = (height - dp2px) / 2;
        RectF rectF = new RectF(0.0f, i, width, i + dp2px);
        float f = dp2px / 2.0f;
        canvas.drawRoundRect(rectF, f, f, paint);
        this._viewSampleImage.setImageBitmap(createBitmap);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view != this._btnMinus && view != this._btnPlus) {
            int i = 0;
            while (true) {
                if (i >= ConfigDefine.PEN_COLOR_N) {
                    break;
                }
                ImageView[] imageViewArr = this._btns;
                if (view == imageViewArr[i]) {
                    imageViewArr[this._selectPallet].setImageResource(R.drawable.tool_deselect);
                    this._btns[i].setImageResource(R.drawable.tool_select);
                    this._config.saveColorID(i);
                    this._impl.onUpdateConfig();
                    this._selectPallet = i;
                    break;
                }
                i++;
            }
        } else {
            int progress = this._seekBar.getProgress();
            if (view == this._btnMinus && progress > 0) {
                progress--;
            } else if (view == this._btnPlus && progress < this._config.getWidthMax() - 1) {
                progress++;
            }
            this._seekBar.setProgress(progress);
            this._width = progress + 1;
            this._config.saveWidth(this._width);
        }
        updateSampleImage();
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_marker_pen_configAlpha /* 2131231396 */:
                this._config.saveAlphaID(true);
                break;
            case R.id.radio_marker_pen_configNoAlpha /* 2131231397 */:
                this._config.saveAlphaID(false);
                break;
        }
        updateSampleImage();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        setWidthText(i);
        this._width = i + 1;
        updateSampleImage();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        setWidthText(seekBar.getProgress());
        this._width = seekBar.getProgress() + 1;
        this._config.saveWidth(this._width);
        updateSampleImage();
    }
}
