package com.serenegiant.system;

import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.serenegiant.widget.Keyboard;
import com.serenegiant.widget.KeyboardView;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public abstract class KeyboardDelegater implements KeyboardView.OnKeyboardActionListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "KeyboardDelegater";
    private final EditText mEditText;
    private Keyboard mKeyboard;
    private final int mKeyboardLayoutRes;
    private final KeyboardView mKeyboardView;

    protected abstract void onCancelClick();

    protected abstract void onOkClick();

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void onPress(int i) {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void onRelease(int i) {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void onText(CharSequence charSequence) {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void swipeDown() {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void swipeLeft() {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void swipeRight() {
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void swipeUp() {
    }

    public KeyboardDelegater(EditText editText, KeyboardView keyboardView, int i) {
        this.mEditText = editText;
        this.mKeyboardView = keyboardView;
        this.mKeyboardLayoutRes = i;
    }

    public void showKeyboard() {
        hideSystemSoftKeyboard();
        if (this.mKeyboard == null) {
            Keyboard keyboard = new Keyboard(this.mEditText.getContext(), this.mKeyboardLayoutRes);
            this.mKeyboard = keyboard;
            this.mKeyboardView.setKeyboard(keyboard);
        }
        this.mKeyboardView.setEnabled(true);
        this.mKeyboardView.setPreviewEnabled(false);
        this.mKeyboardView.setOnKeyboardActionListener(this);
        int visibility = this.mKeyboardView.getVisibility();
        if (visibility == 8 || visibility == 4) {
            this.mKeyboardView.setVisibility(0);
        }
    }

    public void hideKeyboard() {
        if (this.mKeyboardView.getVisibility() == 0) {
            this.mKeyboardView.setVisibility(8);
        }
    }

    public void hideSystemSoftKeyboard() {
        try {
            Method method = EditText.class.getMethod("setShowSoftInputOnFocus", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(this.mEditText, false);
        } catch (NoSuchMethodException | SecurityException | Exception unused) {
        }
        ((InputMethodManager) ContextUtils.requireSystemService(this.mEditText.getContext(), InputMethodManager.class)).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
    }

    @Override // com.serenegiant.widget.KeyboardView.OnKeyboardActionListener
    public void onKey(int i, int[] iArr) {
        Editable text = this.mEditText.getText();
        int selectionStart = this.mEditText.getSelectionStart();
        if (i == -5) {
            if (text == null || text.length() <= 0 || selectionStart <= 0) {
                return;
            }
            text.delete(selectionStart - 1, selectionStart);
        } else if (i == -3) {
            hideKeyboard();
            onCancelClick();
        } else if (i == -4) {
            hideKeyboard();
            onOkClick();
        } else {
            text.insert(selectionStart, Character.toString((char) i));
        }
    }
}
