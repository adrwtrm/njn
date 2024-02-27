package com.serenegiant.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.TextView;
import com.serenegiant.common.R;
import com.serenegiant.system.ContextHolder;
import com.serenegiant.view.animation.ResizeAnimation;

/* loaded from: classes2.dex */
public abstract class MessagePanelUtils extends ContextHolder<Context> {
    public static final String APP_ACTION_CLICK_MESSAGE = "APP_ACTION_CLICK_MESSAGE";
    private static final String APP_ACTION_HIDE_MESSAGE = "APP_ACTION_HIDE_MESSAGE";
    private static final String APP_ACTION_SHOW_MESSAGE = "APP_ACTION_SHOW_MESSAGE";
    private static final String APP_EXTRA_KEY_CLICK_INTENT = "APP_EXTRA_KEY_CLICK_INTENT";
    public static final String APP_EXTRA_KEY_CLICK_START_ACTIVITY = "APP_EXTRA_KEY_CLICK_START_ACTIVITY";
    private static final String APP_EXTRA_KEY_EXCEPTION = "APP_EXTRA_KEY_EXCEPTION";
    private static final String APP_EXTRA_KEY_MESSAGE = "APP_EXTRA_KEY_MESSAGE";
    private static final String APP_EXTRA_KEY_MESSAGE_AUTO_HIDE = "APP_EXTRA_KEY_MESSAGE_AUTO_HIDE";
    private static final String APP_EXTRA_KEY_MESSAGE_HIDE_DELAY_MS = "APP_EXTRA_KEY_MESSAGE_HIDE_DELAY_MS";
    private static final String APP_EXTRA_KEY_MESSAGE_ID = "APP_EXTRA_KEY_MESSAGE_ID";
    private static final boolean DEBUG = false;
    public static final long MESSAGE_DURATION_FLASH = 1500;
    public static final long MESSAGE_DURATION_INFINITY = 0;
    public static final long MESSAGE_DURATION_LONG = 5000;
    public static final long MESSAGE_DURATION_SHORT = 2500;
    private static final String TAG = "MessagePanelUtils";

    /* loaded from: classes2.dex */
    public static class MessageSender extends MessagePanelUtils {
        @Override // com.serenegiant.system.ContextHolder
        protected void internalRelease() {
        }

        public MessageSender(Context context) {
            super(context);
        }

        public void showMessage(Intent intent) {
            sendLocalBroadcast(intent);
        }

        public void showMessage(int i, long j) {
            showMessage(i, j, (Intent) null);
        }

        public void showMessage(int i, long j, Intent intent) {
            sendLocalBroadcast(new Intent(MessagePanelUtils.APP_ACTION_SHOW_MESSAGE).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_AUTO_HIDE, j).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_ID, i).putExtra(MessagePanelUtils.APP_EXTRA_KEY_CLICK_INTENT, intent));
        }

        public void showMessage(String str, long j) {
            showMessage(str, j, (Intent) null);
        }

        public void showMessage(String str, long j, Intent intent) {
            sendLocalBroadcast(new Intent(MessagePanelUtils.APP_ACTION_SHOW_MESSAGE).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_AUTO_HIDE, j).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE, str).putExtra(MessagePanelUtils.APP_EXTRA_KEY_CLICK_INTENT, intent));
        }

        public void showMessage(Exception exc, long j) {
            showMessage(exc, j, (Intent) null);
        }

        public void showMessage(Exception exc, long j, Intent intent) {
            sendLocalBroadcast(new Intent(MessagePanelUtils.APP_ACTION_SHOW_MESSAGE).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_AUTO_HIDE, j).putExtra(MessagePanelUtils.APP_EXTRA_KEY_EXCEPTION, exc).putExtra(MessagePanelUtils.APP_EXTRA_KEY_CLICK_INTENT, intent));
        }

        public void hideMessage(long j) {
            sendLocalBroadcast(new Intent(MessagePanelUtils.APP_ACTION_HIDE_MESSAGE).putExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_HIDE_DELAY_MS, j));
        }
    }

    /* loaded from: classes2.dex */
    public static class MessageReceiver extends MessagePanelUtils {
        private static final int DURATION_RESIZE_MS = 300;
        private final Animation.AnimationListener mAnimationListener;
        final TextView mMessageTv;
        final View mPanelView;
        private final View mParentView;
        private final BroadcastReceiver mReceiver;

        public MessageReceiver(Context context, View view, TextView textView) {
            super(context);
            this.mAnimationListener = new Animation.AnimationListener() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.4
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    Object tag = MessageReceiver.this.mPanelView.getTag(R.id.visibility);
                    if ((tag instanceof Integer) && ((Integer) tag).intValue() == 1) {
                        Object tag2 = MessageReceiver.this.mPanelView.getTag(R.id.auto_hide_duration);
                        long longValue = tag2 instanceof Long ? ((Long) tag2).longValue() : 0L;
                        if (longValue > 0) {
                            MessageReceiver.this.mPanelView.postDelayed(new Runnable() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    MessageReceiver.this.hideMessage(300L);
                                }
                            }, longValue);
                            return;
                        }
                        return;
                    }
                    MessageReceiver.this.mPanelView.setVisibility(8);
                }
            };
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    String action = intent != null ? intent.getAction() : null;
                    if (TextUtils.isEmpty(action)) {
                        return;
                    }
                    action.hashCode();
                    if (action.equals(MessagePanelUtils.APP_ACTION_HIDE_MESSAGE)) {
                        MessageReceiver.this.hideMessage(intent.getLongExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_HIDE_DELAY_MS, 0L));
                    } else if (action.equals(MessagePanelUtils.APP_ACTION_SHOW_MESSAGE)) {
                        MessageReceiver.this.showMessage(context2, intent);
                    }
                }
            };
            this.mReceiver = broadcastReceiver;
            ViewParent parent = view.getParent();
            if (!(parent instanceof View)) {
                Log.w(MessagePanelUtils.TAG, "parent is not a instance of View," + parent);
                this.mParentView = view;
            } else {
                this.mParentView = (View) parent;
            }
            this.mPanelView = view;
            view.setVisibility(8);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Object tag = view2.getTag(R.id.intent);
                    if (!(tag instanceof Intent)) {
                        MessageReceiver.this.hideMessage(300L);
                        return;
                    }
                    Intent intent = (Intent) tag;
                    if (intent.getBooleanExtra(MessagePanelUtils.APP_EXTRA_KEY_CLICK_START_ACTIVITY, false)) {
                        view2.getContext().startActivity(intent);
                    } else {
                        MessageReceiver.this.sendLocalBroadcast(intent);
                    }
                }
            });
            this.mMessageTv = textView;
            IntentFilter intentFilter = new IntentFilter(MessagePanelUtils.APP_ACTION_SHOW_MESSAGE);
            intentFilter.addAction(MessagePanelUtils.APP_ACTION_HIDE_MESSAGE);
            requireLocalBroadcastManager().registerReceiver(broadcastReceiver, intentFilter);
        }

        @Override // com.serenegiant.system.ContextHolder
        protected void internalRelease() {
            try {
                requireLocalBroadcastManager().unregisterReceiver(this.mReceiver);
            } catch (Exception unused) {
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showMessage(Context context, Intent intent) {
            long longExtra = intent.getLongExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_AUTO_HIDE, 0L);
            int intExtra = intent.getIntExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE_ID, 0);
            String stringExtra = intent.getStringExtra(MessagePanelUtils.APP_EXTRA_KEY_MESSAGE);
            Intent intent2 = (Intent) intent.getParcelableExtra(MessagePanelUtils.APP_EXTRA_KEY_CLICK_INTENT);
            if (intExtra != 0 && !TextUtils.isEmpty(stringExtra)) {
                showMessage(getString(intExtra, stringExtra), longExtra, intent2);
            } else if (intExtra != 0) {
                showMessage(getString(intExtra), longExtra, intent2);
            } else if (!TextUtils.isEmpty(stringExtra)) {
                showMessage(stringExtra, longExtra, intent2);
            } else {
                try {
                    Exception exc = (Exception) intent.getSerializableExtra(MessagePanelUtils.APP_EXTRA_KEY_EXCEPTION);
                    if (exc != null) {
                        String message = exc.getMessage();
                        if (TextUtils.isEmpty(message)) {
                            return;
                        }
                        showMessage(message, longExtra, intent2);
                    }
                } catch (Exception e) {
                    Log.w(MessagePanelUtils.TAG, e);
                }
            }
        }

        private void showMessage(final String str, final long j, final Intent intent) {
            this.mPanelView.post(new Runnable() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.2
                @Override // java.lang.Runnable
                public void run() {
                    MessageReceiver.this.mMessageTv.setText(str);
                    MessageReceiver.this.mPanelView.clearAnimation();
                    ResizeAnimation resizeAnimation = new ResizeAnimation(MessageReceiver.this.mPanelView, MessageReceiver.this.mParentView.getWidth(), 0, MessageReceiver.this.mParentView.getWidth(), MessageReceiver.this.getResources().getDimensionPixelSize(R.dimen.bottom_message_panel_height));
                    resizeAnimation.setDuration(300L);
                    resizeAnimation.setAnimationListener(MessageReceiver.this.mAnimationListener);
                    MessageReceiver.this.mPanelView.setVisibility(0);
                    MessageReceiver.this.mPanelView.setTag(R.id.visibility, 1);
                    MessageReceiver.this.mPanelView.setTag(R.id.auto_hide_duration, Long.valueOf(j));
                    MessageReceiver.this.mPanelView.setTag(R.id.intent, intent);
                    MessageReceiver.this.mPanelView.startAnimation(resizeAnimation);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideMessage(final long j) {
            this.mPanelView.post(new Runnable() { // from class: com.serenegiant.view.MessagePanelUtils.MessageReceiver.3
                @Override // java.lang.Runnable
                public void run() {
                    if (MessageReceiver.this.mPanelView.getVisibility() == 0) {
                        MessageReceiver.this.mPanelView.clearAnimation();
                        ResizeAnimation resizeAnimation = new ResizeAnimation(MessageReceiver.this.mPanelView, MessageReceiver.this.mParentView.getWidth(), MessageReceiver.this.mPanelView.getHeight(), MessageReceiver.this.mParentView.getWidth(), 0);
                        resizeAnimation.setDuration(j);
                        resizeAnimation.setAnimationListener(MessageReceiver.this.mAnimationListener);
                        MessageReceiver.this.mPanelView.setTag(R.id.visibility, 0);
                        MessageReceiver.this.mPanelView.startAnimation(resizeAnimation);
                    }
                }
            });
        }
    }

    private MessagePanelUtils(Context context) {
        super(context);
    }
}
