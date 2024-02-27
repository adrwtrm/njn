package com.epson.iprojection.ui.activities.marker.config;

import android.app.Activity;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;
import com.epson.iprojection.ui.common.exception.IProjRuntimeException;

/* loaded from: classes.dex */
public class DialogToolConfigFactory {

    /* renamed from: com.epson.iprojection.ui.activities.marker.config.DialogToolConfigFactory$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$marker$config$ConfigDefine$ConfigKind;

        static {
            int[] iArr = new int[ConfigDefine.ConfigKind.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$marker$config$ConfigDefine$ConfigKind = iArr;
            try {
                iArr[ConfigDefine.ConfigKind.PEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$marker$config$ConfigDefine$ConfigKind[ConfigDefine.ConfigKind.ERASER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static BaseDialogToolConfig create(Activity activity, IToolConfig iToolConfig, IUpdateConfigListener iUpdateConfigListener) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$marker$config$ConfigDefine$ConfigKind[iToolConfig.getKind().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return new DialogEraserConfig(activity, iToolConfig, iUpdateConfigListener);
            }
            throw new IProjRuntimeException("Illegal btnID");
        }
        return new DialogPenConfig(activity, iToolConfig, iUpdateConfigListener);
    }
}
