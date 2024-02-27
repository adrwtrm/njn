package com.serenegiant.view;

import android.view.MotionEvent;
import com.serenegiant.utils.BitsHelper;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MotionEventUtils {
    public static final int BUTTON_PRIMARY = 32 | 1;
    public static final int BUTTON_SECONDARY = 64 | 2;
    private static final String[] BUTTON_SYMBOLIC_NAMES = {"BUTTON_PRIMARY", "BUTTON_SECONDARY", "BUTTON_TERTIARY", "BUTTON_BACK", "BUTTON_FORWARD", "BUTTON_STYLUS_PRIMARY", "BUTTON_STYLUS_SECONDARY", "0x00000080", "0x00000100", "0x00000200", "0x00000400", "0x00000800", "0x00001000", "0x00002000", "0x00004000", "0x00008000", "0x00010000", "0x00020000", "0x00040000", "0x00080000", "0x00100000", "0x00200000", "0x00400000", "0x00800000", "0x01000000", "0x02000000", "0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};

    public static boolean isPressed(int i, int i2) {
        return (i & i2) == i2;
    }

    public static boolean isPressedAny(int i, int i2) {
        return (i & i2) != 0;
    }

    private MotionEventUtils() {
    }

    public static String getActionString(MotionEvent motionEvent) {
        return MotionEvent.actionToString(motionEvent.getActionMasked());
    }

    public static String getButtonStateString(MotionEvent motionEvent) {
        int buttonState = motionEvent.getButtonState();
        if (buttonState == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (buttonState != 0) {
            boolean z = (buttonState & 1) != 0;
            buttonState >>>= 1;
            if (z) {
                String str = BUTTON_SYMBOLIC_NAMES[i];
                if (sb.length() == 1) {
                    sb.append(str);
                    if (buttonState == 0) {
                        break;
                    }
                } else {
                    sb.append('|');
                    sb.append(str);
                }
            }
            i++;
        }
        sb.append("]");
        return sb.toString();
    }

    public static boolean isPressed(MotionEvent motionEvent, int i) {
        return isPressed(motionEvent.getButtonState(), i);
    }

    public static boolean isPressedAny(MotionEvent motionEvent, int i) {
        return isPressedAny(motionEvent.getButtonState(), i);
    }

    public static int getNumPressed(MotionEvent motionEvent) {
        return getNumPressed(motionEvent.getButtonState());
    }

    public static int getNumPressed(int i) {
        return BitsHelper.countBits(i);
    }

    public static boolean isFromSource(MotionEvent motionEvent, int i) {
        return (motionEvent.getSource() & i) == i;
    }

    public static String debugMotionEventString(MotionEvent motionEvent) {
        return String.format(Locale.US, "%s(%f,%f):,down=%d,event=%d,src=0x%08x", getActionString(motionEvent), Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()), Long.valueOf(motionEvent.getDownTime()), Long.valueOf(motionEvent.getEventTime()), Integer.valueOf(motionEvent.getSource()));
    }
}
