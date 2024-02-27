package com.serenegiant.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import com.serenegiant.system.ContextUtils;

/* loaded from: classes2.dex */
public class KeyboardUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "KeyboardUtils";

    /* loaded from: classes2.dex */
    public interface OnKeyboardVisibilityChangedListener {
        void onKeyboardVisibilityChanged(boolean z);
    }

    private KeyboardUtils() {
    }

    public static void hide(View view) {
        view.clearFocus();
        ((InputMethodManager) ContextUtils.requireSystemService(view.getContext(), InputMethodManager.class)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hide(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) ContextUtils.requireSystemService(activity, InputMethodManager.class);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static View setOnKeyboardVisibilityChangedListener(final View view, final OnKeyboardVisibilityChangedListener onKeyboardVisibilityChangedListener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.serenegiant.utils.KeyboardUtils.1
            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = 48 + 100;
            private final Rect rect = new Rect();

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int applyDimension = (int) TypedValue.applyDimension(1, this.EstimatedKeyboardDP, view.getResources().getDisplayMetrics());
                view.getWindowVisibleDisplayFrame(this.rect);
                boolean z = view.getRootView().getHeight() - (this.rect.bottom - this.rect.top) >= applyDimension;
                if (z != this.alreadyOpen) {
                    this.alreadyOpen = z;
                    onKeyboardVisibilityChangedListener.onKeyboardVisibilityChanged(z);
                }
            }
        });
        return view;
    }
}
