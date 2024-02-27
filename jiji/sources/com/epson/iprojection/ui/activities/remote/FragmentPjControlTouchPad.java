package com.epson.iprojection.ui.activities.remote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.CommandSender;
import com.epson.iprojection.ui.activities.remote.commandsend.D_SendCommand;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentPjControlTouchPad extends FragmentPjControlBase {
    public static final String ANALYTICS_SHOW_ESCVPSENDER = "FragmentPjControl_Show_ESCVPSender";
    public static final String ANALYTICS_SHOW_TOUCHPAD = "FragmentPjControl_Show_TouchPad";
    protected static final String TAG_ALREADY_USE_MARK = "tag_already_use_mark_swipectrler";
    protected Button _btnEsc;
    protected Button _btnHatena;
    protected Button _btnMenu;
    protected ViewFlipper _flipper;
    protected Animation _inFromLeftAnimation;
    protected Animation _inFromRightAnimation;
    protected Animation _outToLeftAnimation;
    protected Animation _outToRightAnimation;
    protected SwipeCtrlView _swipeView;
    protected boolean _nowShowTouchPad = false;
    protected final CommandSender _commandSender = new CommandSender();

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public boolean haveFlipper() {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initializeTouchPad();
        if (this._nowShowTouchPad) {
            showFirstDisplayTouchPad();
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnMenu) {
            sendCommand(Pj.ESCVP_COMMAND_MENU);
        } else if (view == this._btnEsc) {
            sendCommand(Pj.ESCVP_COMMAND_ESC);
        } else if (view == this._btnHatena) {
            showDialogGestureMenu();
        }
    }

    private void sendCommand(String str) {
        D_HistoryInfo pjInfo = getPjInfo();
        String read = PrefUtils.read(getContext(), RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(pjInfo.macAddr));
        Lg.d("保存されていたパスワード = " + read);
        ArrayList<D_SendCommand> arrayList = new ArrayList<>();
        if (read == null) {
            read = "";
        }
        arrayList.add(new D_SendCommand(read, pjInfo));
        this._commandSender.send(str, this._activity, Pj.getIns(), arrayList, false, null);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void changeFlipper(boolean z, boolean z2) {
        ViewFlipper viewFlipper = this._flipper;
        if (viewFlipper == null) {
            this._nowShowTouchPad = z;
            return;
        }
        boolean z3 = this._nowShowTouchPad;
        if (z3 && !z) {
            viewFlipper.setInAnimation(null);
            this._flipper.setOutAnimation(null);
            this._flipper.showPrevious();
            this._nowShowTouchPad = false;
            sendGoogleAnalyticsScreenName(ANALYTICS_SHOW_ESCVPSENDER);
        } else if (z3 || !z) {
        } else {
            viewFlipper.setInAnimation(null);
            this._flipper.setOutAnimation(null);
            this._flipper.showNext();
            this._nowShowTouchPad = true;
            showFirstDisplayTouchPad();
            sendGoogleAnalyticsScreenName(ANALYTICS_SHOW_TOUCHPAD);
        }
    }

    private void setAnimations() {
        this._inFromRightAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
        this._inFromLeftAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.left_in);
        this._outToRightAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.right_out);
        this._outToLeftAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.left_out);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initializeTouchPad() {
        if (this._flipper != null) {
            return;
        }
        this._flipper = (ViewFlipper) this._view.findViewById(R.id.flipper);
        setAnimations();
        if (this._nowShowTouchPad) {
            this._flipper.showNext();
        }
        SwipeCtrlView swipeCtrlView = (SwipeCtrlView) this._view.findViewById(R.id.swv_swipeCtrlView);
        this._swipeView = swipeCtrlView;
        swipeCtrlView.setTargetPjInfo(getPjInfo());
        this._swipeView.setCommandSender(this._commandSender);
        Button button = (Button) this._view.findViewById(R.id.btn_remote_gesture_menu);
        this._btnMenu = button;
        button.setOnClickListener(this);
        Button button2 = (Button) this._view.findViewById(R.id.btn_remote_gesture_esc);
        this._btnEsc = button2;
        button2.setOnClickListener(this);
        Button button3 = (Button) this._view.findViewById(R.id.btn_remote_gesture_hatena);
        this._btnHatena = button3;
        button3.setOnClickListener(this);
    }

    private void showFirstDisplayTouchPad() {
        if (getActivity() == null || isAlreadyUsed()) {
            return;
        }
        showDialogGestureMenu();
        recordAlreadyUsed();
    }

    private void showDialogGestureMenu() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_gesturemenu, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_ges);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), (int) R.style.style_theme_black));
        builder.setTitle(getActivity().getString(R.string._OperationExplanation_));
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    private boolean isAlreadyUsed() {
        return PrefUtils.read(getActivity(), TAG_ALREADY_USE_MARK) != null;
    }

    private void recordAlreadyUsed() {
        PrefUtils.write(getActivity(), TAG_ALREADY_USE_MARK, "empty message", (SharedPreferences.Editor) null);
    }
}
