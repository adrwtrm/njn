package com.epson.iprojection.ui.common.singleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.delivery.Activity_MarkerDelivery;
import com.epson.iprojection.ui.activities.drawermenu.DrawerSelectStatus;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.pjselect.Activity_Other;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.activities.pjselect.history.Activity_PjHistory;
import com.epson.iprojection.ui.activities.pjselect.profile.Activity_Profile;
import com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities.Activity_QrCamera;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.start.Activity_StartFromImplicitIntent;
import com.epson.iprojection.ui.activities.start.IntentPathGetter;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.activities.support.intro.userctrl.Activity_IntroUserCtrl;
import com.epson.iprojection.ui.activities.terms.Activity_TermsToMain;
import com.epson.iprojection.ui.activities.web.Activity_Web;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activitystatus.ActivityKillStatus;
import com.epson.iprojection.ui.common.activitystatus.NextCallIntentHolder;
import com.epson.iprojection.ui.common.activitystatus.NextCallType;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eWebScreenDimension;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class AppStartActivityManager {
    private static final AppStartActivityManager _inst = new AppStartActivityManager();
    private ActivityType _activityType = ActivityType.NONE;
    private String _path = null;
    private final ArrayList<String> _presenMimeTypeList = new ArrayList<>();
    private final ArrayList<String> _webMimeTypeList = new ArrayList<>();

    /* loaded from: classes.dex */
    public enum ActivityType {
        NONE,
        PRESEN,
        WEB
    }

    public static AppStartActivityManager getIns() {
        return _inst;
    }

    private AppStartActivityManager() {
        init();
    }

    private void init() {
        this._presenMimeTypeList.clear();
        this._presenMimeTypeList.add("image/");
        this._presenMimeTypeList.add("application/");
        this._webMimeTypeList.clear();
        this._webMimeTypeList.add("text/");
    }

    /* renamed from: com.epson.iprojection.ui.common.singleton.AppStartActivityManager$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$singleton$AppStartActivityManager$ActivityType;

        static {
            int[] iArr = new int[ActivityType.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$singleton$AppStartActivityManager$ActivityType = iArr;
            try {
                iArr[ActivityType.PRESEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$singleton$AppStartActivityManager$ActivityType[ActivityType.WEB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public Intent getStartActivityIntent(Context context) {
        Intent intent;
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$singleton$AppStartActivityManager$ActivityType[this._activityType.ordinal()];
        if (i == 1) {
            intent = new Intent(context, Activity_Presen.class);
            intent.putExtra(Activity_StartFromImplicitIntent.TAG_IMPLICIT, "nothing");
            String str = this._path;
            if (str != null) {
                intent.putExtra(Activity_Presen.INTENT_TAG_PATH, str);
                intent.setFlags(335544320);
            }
        } else if (i != 2) {
            return null;
        } else {
            intent = new Intent(context, Activity_Web.class);
            intent.putExtra(Activity_StartFromImplicitIntent.TAG_IMPLICIT, "nothing");
            if (WebUtils.isUrlAvailable(this._path)) {
                intent.putExtra(Activity_Web.INTENT_TAG_URL, this._path);
                intent.setFlags(335544320);
            }
        }
        return intent;
    }

    public void clearStartActivity() {
        this._activityType = ActivityType.NONE;
        this._path = null;
    }

    public void setStartActivity(Activity activity, Intent intent) {
        String action;
        if (intent == null || (action = intent.getAction()) == null) {
            return;
        }
        action.hashCode();
        if (action.equals("android.intent.action.SEND")) {
            setActivityActionSend(activity, intent);
        } else if (action.equals("android.intent.action.VIEW")) {
            setActivityActionView(activity, intent);
        }
    }

    public void doStartActivity(Activity activity) {
        Intent startActivityIntent = getStartActivityIntent(activity);
        if (startActivityIntent != null) {
            activity.startActivity(startActivityIntent);
            clearStartActivity();
        }
    }

    private void setActivityActionView(Activity activity, Intent intent) {
        this._activityType = ActivityType.PRESEN;
        setExtraInfo(activity, intent);
        Analytics.getIns().setPresentationEvent(true);
    }

    private void setActivityActionSend(Activity activity, Intent intent) {
        String type = intent.getType();
        Iterator<String> it = this._presenMimeTypeList.iterator();
        while (it.hasNext()) {
            if (type.startsWith(it.next())) {
                this._activityType = ActivityType.PRESEN;
                setExtraInfo(activity, intent);
                Analytics.getIns().setPresentationEvent(true);
                return;
            }
        }
        Iterator<String> it2 = this._webMimeTypeList.iterator();
        while (it2.hasNext()) {
            if (type.startsWith(it2.next())) {
                this._activityType = ActivityType.WEB;
                setExtraInfo(activity, intent);
                Analytics.getIns().setWebScreenEvent(eWebScreenDimension.implicitIntents);
                return;
            }
        }
    }

    private void setExtraInfo(Activity activity, Intent intent) {
        this._path = new IntentPathGetter(activity, intent).get();
    }

    public boolean isNextActivityAvailableWithoutSD() {
        return this._activityType != ActivityType.PRESEN;
    }

    public void transitionActivityPjSelectAny(Activity activity) {
        Activity frontActivity;
        if (PrefUtils.isConsented(activity) && (frontActivity = ActivityGetter.getIns().getFrontActivity()) != null) {
            if (frontActivity instanceof Activity_PjSelect) {
                restartActivityPjSelect(activity, ((Activity_PjSelect) frontActivity).isRootActivity(), false);
            } else if ((frontActivity instanceof Activity_Marker) || (frontActivity instanceof Activity_MarkerDelivery)) {
                frontActivity.finish();
                showPjSelect(frontActivity, true);
            } else {
                if (frontActivity instanceof Activity_IntroModerator) {
                    Activity_IntroModerator activity_IntroModerator = (Activity_IntroModerator) frontActivity;
                    if (activity_IntroModerator.isCalledFromMarker()) {
                        activity_IntroModerator.cancelClose();
                        showPjSelect(frontActivity, true);
                        return;
                    }
                }
                showPjSelect(frontActivity, true);
            }
        }
    }

    public static boolean shouldFinishActivityWhenNfc(Activity activity) {
        return (activity instanceof Activity_Other) || (activity instanceof Activity_PjHistory) || (activity instanceof Activity_Profile);
    }

    public static boolean isNFCAvailableActivity(Activity activity) {
        return !(activity instanceof Activity_QrCamera);
    }

    private void finishNoRootActivity(Activity activity) {
        Intent intent = new Intent(activity, Activity_TermsToMain.class);
        intent.setFlags(67108864);
        NextCallIntentHolder.getIns().set(intent);
        NextCallType.getIns().set(eContentsType.None);
        ActivityKillStatus.getIns().startKill();
        activity.finish();
    }

    private void showPjSelect(Activity activity, boolean z) {
        NextCallType.getIns().set(eContentsType.None);
        if (DrawerSelectStatus.getIns().get() == eDrawerMenuItem.UserCtl) {
            if (activity instanceof Activity_IntroUserCtrl) {
                ((Activity_IntroUserCtrl) activity).cancelClose();
            } else {
                activity.finish();
            }
        }
        Intent intent = new Intent(activity, Activity_PjSelect.class);
        if (z) {
            intent.putExtra(Activity_PjSelect.TAG_NFC_CONNECT, "empty message");
        }
        activity.startActivityForResult(intent, CommonDefine.REQUEST_CODE_DRAWER);
    }

    private void restartActivityPjSelect(Activity activity, boolean z, boolean z2) {
        Intent intent = new Intent(activity, Activity_PjSelect.class);
        if (z2) {
            intent.putExtra(Activity_PjSelect.TAG_NFC_CONNECT, "empty message");
        }
        intent.setFlags(67108864);
        if (z) {
            intent.putExtra(IntentTagDefine.ROOT_TAG, "empty message");
            DrawerSelectStatus.getIns().clear();
        }
        activity.startActivity(intent);
    }
}
