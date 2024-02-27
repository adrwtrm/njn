package com.epson.iprojection.ui.activities.support.intro;

import com.epson.iprojection.R;
import kotlin.Metadata;

/* compiled from: TrademarkActivity.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0004H\u0014¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/support/intro/TrademarkActivity;", "Lcom/epson/iprojection/ui/activities/support/intro/Activity_BaseLicenses;", "()V", "getLayoutID", "", "getTextFileName", "", "getTitleStringID", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class TrademarkActivity extends Activity_BaseLicenses {
    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected int getLayoutID() {
        return R.layout.main_trademark;
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected String getTextFileName() {
        return "trademark";
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.Activity_BaseLicenses
    protected int getTitleStringID() {
        return R.string._Trademarks_;
    }
}
