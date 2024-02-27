package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;

/* loaded from: classes.dex */
public class FragmentPjControlNG extends FragmentPjControlBase {
    private static final String KEY_NG_TYPE = "key_ng_type";
    private static final String KEY_PJ_INFO = "key_pj_info";
    private Button _btnRetry;
    private IWebLoadRetryListener _implWebLoadRetryListener;

    /* loaded from: classes.dex */
    public enum MessageType {
        TYPE_NO_SELECT,
        TYPE_ERR_CONNECT,
        TYPE_ERR_AUTH
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public boolean canPjControl() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void disable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void enable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    protected int getLayoutId() {
        return R.layout.main_conpj_pjcontrol_ng;
    }

    public static FragmentPjControlNG newInstance(D_HistoryInfo d_HistoryInfo, MessageType messageType) {
        FragmentPjControlNG fragmentPjControlNG = new FragmentPjControlNG();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PJ_INFO, d_HistoryInfo);
        bundle.putSerializable(KEY_NG_TYPE, messageType);
        fragmentPjControlNG.setArguments(bundle);
        return fragmentPjControlNG;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public D_HistoryInfo getPjInfo() {
        return (D_HistoryInfo) getArguments().getSerializable(KEY_PJ_INFO);
    }

    public MessageType getNgType() {
        return (MessageType) getArguments().getSerializable(KEY_NG_TYPE);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IWebLoadRetryListener) {
            this._implWebLoadRetryListener = (IWebLoadRetryListener) context;
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this._implWebLoadRetryListener = null;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        setControl(view);
        super.onViewCreated(view, bundle);
    }

    private void setControl(View view) {
        TextView textView = (TextView) view.findViewById(R.id.text_message);
        Button button = (Button) view.findViewById(R.id.btn_retry);
        this._btnRetry = button;
        button.setOnClickListener(this);
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$remote$FragmentPjControlNG$MessageType[getNgType().ordinal()];
        if (i == 1) {
            textView.setText(R.string._ErrorSelectToProjector_);
            this._btnRetry.setVisibility(8);
        } else if (i == 2) {
            textView.setText(R.string._ErrorNetworkConnection_);
            this._btnRetry.setVisibility(0);
        } else if (i == 3) {
            textView.setText(R.string._AuthenticationError_);
            this._btnRetry.setVisibility(0);
        } else {
            textView.setText("");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.remote.FragmentPjControlNG$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$remote$FragmentPjControlNG$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$remote$FragmentPjControlNG$MessageType = iArr;
            try {
                iArr[MessageType.TYPE_NO_SELECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$remote$FragmentPjControlNG$MessageType[MessageType.TYPE_ERR_CONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$remote$FragmentPjControlNG$MessageType[MessageType.TYPE_ERR_AUTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view == this._btnRetry) {
            this._implWebLoadRetryListener.onWebLoadRetry(getPjInfo());
        }
    }
}
