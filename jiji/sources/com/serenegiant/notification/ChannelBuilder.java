package com.serenegiant.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.media.AudioAttributes;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.utils.ObjectHelper;
import com.serenegiant.utils.XmlHelper;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class ChannelBuilder {
    private static final boolean DEBUG = false;
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    public static final Set<Integer> IMPORTANCE;
    public static final Set<Integer> NOTIFICATION_VISIBILITY;
    private static final String TAG = "ChannelBuilder";
    private int argb;
    private AudioAttributes audioAttributes;
    private boolean bypassDnd;
    private String channelId;
    private boolean createIfExists;
    private String description;
    private String groupId;
    private String groupName;
    private int importance;
    private boolean lights;
    private int lockscreenVisibility;
    private final Context mContext;
    private CharSequence name;
    private boolean showBadge;
    private Uri sound;
    private boolean vibration;
    private long[] vibrationPattern;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Importance {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface NotificationVisibility {
    }

    protected NotificationChannel setupNotificationChannel(NotificationChannel notificationChannel) {
        return notificationChannel;
    }

    protected NotificationChannelGroup setupNotificationChannelGroup(NotificationChannelGroup notificationChannelGroup) {
        return notificationChannelGroup;
    }

    static {
        HashSet hashSet = new HashSet();
        IMPORTANCE = hashSet;
        Collections.addAll(hashSet, Integer.valueOf((int) NotificationManagerCompat.IMPORTANCE_UNSPECIFIED), 0, 1, 2, 3, 4, 5);
        HashSet hashSet2 = new HashSet();
        NOTIFICATION_VISIBILITY = hashSet2;
        Collections.addAll(hashSet2, 1, 0, -1);
    }

    public static ChannelBuilder getBuilder(Context context, String str) {
        NotificationChannel notificationChannel = NotificationManagerCompat.from(context).getNotificationChannel(str);
        if (notificationChannel != null) {
            ChannelBuilder channelBuilder = new ChannelBuilder(context, str, notificationChannel.getName(), notificationChannel.getImportance());
            channelBuilder.setLockscreenVisibility(notificationChannel.getLockscreenVisibility()).setBypassDnd(notificationChannel.canBypassDnd()).setShowBadge(notificationChannel.canShowBadge()).setDescription(notificationChannel.getDescription()).setLightColor(notificationChannel.getLightColor()).setVibrationPattern(notificationChannel.getVibrationPattern()).enableLights(notificationChannel.shouldShowLights()).enableVibration(notificationChannel.shouldVibrate()).setSound(notificationChannel.getSound(), notificationChannel.getAudioAttributes()).setGroup(notificationChannel.getGroup(), null).setCreateIfExists(true);
            return channelBuilder;
        }
        return new ChannelBuilder(context, str, null, 0);
    }

    public static List<String> updateNotificationChannel(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 2) {
                    readEntryOne(context, xml, arrayList);
                    continue;
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "IOException", e);
        } catch (XmlPullParserException e2) {
            Log.d(TAG, "XmlPullParserException", e2);
        }
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void readEntryOne(Context context, XmlPullParser xmlPullParser, List<String> list) throws XmlPullParserException, IOException {
        Context context2;
        String str;
        Context context3;
        String str2;
        boolean z;
        String str3;
        int i;
        int i2;
        char c;
        String str4;
        String str5;
        Context context4 = context;
        int eventType = xmlPullParser.getEventType();
        String str6 = null;
        ChannelBuilder channelBuilder = null;
        while (eventType != 1) {
            String name = xmlPullParser.getName();
            if (TextUtils.isEmpty(name) || !name.equalsIgnoreCase("notificationChannel")) {
                context2 = context4;
                str = str6;
            } else if (eventType == 2) {
                String str7 = "";
                String attribute = XmlHelper.getAttribute(context4, xmlPullParser, str6, "channelId", "");
                if (TextUtils.isEmpty(attribute)) {
                    context2 = context4;
                    str = str6;
                } else {
                    boolean z2 = false;
                    ChannelBuilder createIfExists = getBuilder(context4, attribute).setCreateIfExists(false);
                    int attributeCount = xmlPullParser.getAttributeCount();
                    int i3 = 0;
                    while (i3 < attributeCount) {
                        String attributeName = xmlPullParser.getAttributeName(i3);
                        if (!TextUtils.isEmpty(attributeName)) {
                            attributeName.hashCode();
                            i = attributeCount;
                            i2 = i3;
                            String str8 = str7;
                            int i4 = -1;
                            switch (attributeName.hashCode()) {
                                case -1930680666:
                                    if (attributeName.equals("showBadge")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1898049152:
                                    if (attributeName.equals("enableLights")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1724546052:
                                    if (attributeName.equals("description")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1402300654:
                                    if (attributeName.equals("bypassDnd")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3373707:
                                    if (attributeName.equals(AppMeasurementSdk.ConditionalUserProperty.NAME)) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 102970646:
                                    if (attributeName.equals("light")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 109627663:
                                    if (attributeName.equals("sound")) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 906619038:
                                    if (attributeName.equals("vibrationPattern")) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1363834825:
                                    if (attributeName.equals("lockscreenVisibility")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1654598901:
                                    if (attributeName.equals("createIfExists")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1988784207:
                                    if (attributeName.equals("enableVibration")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2125650548:
                                    if (attributeName.equals("importance")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    z = false;
                                    createIfExists.setShowBadge(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "showBadge", createIfExists.canShowBadge()));
                                    break;
                                case 1:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    createIfExists.enableLights(XmlHelper.getAttribute(context3, xmlPullParser, str2, "enableLights", createIfExists.shouldShowLights()));
                                    break;
                                case 2:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    createIfExists.setDescription(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "description", createIfExists.getDescription()));
                                    break;
                                case 3:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    createIfExists.setBypassDnd(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "bypassDnd", createIfExists.canBypassDnd()));
                                    break;
                                case 4:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    createIfExists.setName(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, AppMeasurementSdk.ConditionalUserProperty.NAME, createIfExists.getName()));
                                    break;
                                case 5:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    createIfExists.setLightColor(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "light", createIfExists.getLightColor()));
                                    createIfExists.enableLights(XmlHelper.getAttribute(context3, xmlPullParser, str2, "enableLights", createIfExists.shouldShowLights()));
                                    break;
                                case 6:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    String attribute2 = XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "sound", str3);
                                    if (TextUtils.isEmpty(attribute2)) {
                                        break;
                                    } else {
                                        createIfExists.setSound(Uri.parse(attribute2), null);
                                        break;
                                    }
                                case 7:
                                    z = false;
                                    context3 = context;
                                    str3 = str8;
                                    String attribute3 = XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "vibrationPattern", str3);
                                    if (!TextUtils.isEmpty(attribute3)) {
                                        String[] split = attribute3.trim().split(RemotePrefUtils.SEPARATOR);
                                        if (split.length > 0) {
                                            long[] jArr = new long[split.length];
                                            for (String str9 : split) {
                                                i4++;
                                                jArr[i4] = ObjectHelper.asLong(str9, 0L);
                                            }
                                            if (i4 >= 0) {
                                                createIfExists.setVibrationPattern(Arrays.copyOf(jArr, i4 + 1));
                                            }
                                        }
                                    }
                                    str2 = null;
                                    break;
                                case '\b':
                                    z = false;
                                    str4 = null;
                                    context3 = context;
                                    int attribute4 = XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "lockscreenVisibility", createIfExists.getLockscreenVisibility());
                                    if (NOTIFICATION_VISIBILITY.contains(Integer.valueOf(attribute4))) {
                                        createIfExists.setLockscreenVisibility(attribute4);
                                    }
                                    str2 = str4;
                                    str3 = str8;
                                    break;
                                case '\t':
                                    z = false;
                                    str4 = null;
                                    context3 = context;
                                    createIfExists.setCreateIfExists(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "createIfExists", false));
                                    str2 = str4;
                                    str3 = str8;
                                    break;
                                case '\n':
                                    str5 = null;
                                    context3 = context;
                                    createIfExists.enableVibration(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "enableVibration", createIfExists.shouldVibrate()));
                                    str2 = str5;
                                    str3 = str8;
                                    z = false;
                                    break;
                                case 11:
                                    str5 = null;
                                    context3 = context;
                                    createIfExists.setImportance(XmlHelper.getAttribute(context3, xmlPullParser, (String) null, "importance", createIfExists.getImportance()));
                                    str2 = str5;
                                    str3 = str8;
                                    z = false;
                                    break;
                                default:
                                    z = false;
                                    str2 = null;
                                    context3 = context;
                                    str3 = str8;
                                    break;
                            }
                        } else {
                            context3 = context4;
                            str2 = str6;
                            z = z2;
                            str3 = str7;
                            i = attributeCount;
                            i2 = i3;
                        }
                        i3 = i2 + 1;
                        z2 = z;
                        str7 = str3;
                        str6 = str2;
                        context4 = context3;
                        attributeCount = i;
                    }
                    context2 = context4;
                    str = str6;
                    channelBuilder = createIfExists;
                }
            } else {
                context2 = context4;
                str = str6;
                if (eventType == 3 && channelBuilder != null) {
                    channelBuilder.build();
                    list.add(channelBuilder.getId());
                    return;
                }
            }
            eventType = xmlPullParser.next();
            str6 = str;
            context4 = context2;
        }
    }

    public ChannelBuilder(Context context) {
        this(context, "miscellaneous", "miscellaneous", 0, null, null);
    }

    public ChannelBuilder(Context context, String str, CharSequence charSequence, int i) {
        this(context, str, charSequence, i, null, null);
    }

    public ChannelBuilder(Context context, String str, CharSequence charSequence, int i, String str2, String str3) {
        this.lockscreenVisibility = 0;
        this.bypassDnd = false;
        this.showBadge = true;
        this.argb = 0;
        this.createIfExists = true;
        this.mContext = context;
        this.channelId = TextUtils.isEmpty(str) ? "miscellaneous" : str;
        this.name = charSequence;
        this.importance = i;
        this.groupId = str2;
        this.groupName = str3;
    }

    public String toString() {
        return "ChannelBuilder{channelId='" + this.channelId + "', name=" + ((Object) this.name) + ", importance=" + this.importance + ", lockscreenVisibility=" + this.lockscreenVisibility + ", bypassDnd=" + this.bypassDnd + ", showBadge=" + this.showBadge + ", description='" + this.description + "', argb=" + this.argb + ", lights=" + this.lights + ", vibrationPattern=" + Arrays.toString(this.vibrationPattern) + ", vibration=" + this.vibration + ", sound=" + this.sound + ", audioAttributes=" + this.audioAttributes + ", groupId='" + this.groupId + "', groupName='" + this.groupName + "', createIfExists=" + this.createIfExists + '}';
    }

    public NotificationChannel build() {
        if (BuildCheck.isOreo()) {
            return createNotificationChannel(this.mContext);
        }
        return null;
    }

    public ChannelBuilder setId(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "miscellaneous";
        }
        this.channelId = str;
        return this;
    }

    public String getId() {
        return this.channelId;
    }

    public ChannelBuilder setName(CharSequence charSequence) {
        this.name = charSequence;
        return this;
    }

    public CharSequence getName() {
        return this.name;
    }

    public ChannelBuilder setImportance(int i) {
        this.importance = i;
        return this;
    }

    public int getImportance() {
        return this.importance;
    }

    public ChannelBuilder setLockscreenVisibility(int i) {
        this.lockscreenVisibility = i;
        return this;
    }

    public int getLockscreenVisibility() {
        return this.lockscreenVisibility;
    }

    public ChannelBuilder setBypassDnd(boolean z) {
        this.bypassDnd = z;
        return this;
    }

    public boolean canBypassDnd() {
        return this.bypassDnd;
    }

    public ChannelBuilder setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public ChannelBuilder setLightColor(int i) {
        this.argb = i;
        return this;
    }

    public int getLightColor() {
        return this.argb;
    }

    public ChannelBuilder enableLights(boolean z) {
        this.lights = z;
        return this;
    }

    public boolean shouldShowLights() {
        return this.lights;
    }

    public ChannelBuilder setShowBadge(boolean z) {
        this.showBadge = z;
        return this;
    }

    public boolean canShowBadge() {
        return this.showBadge;
    }

    public ChannelBuilder setVibrationPattern(long[] jArr) {
        this.vibration = jArr != null && jArr.length > 0;
        this.vibrationPattern = jArr;
        return this;
    }

    public long[] getVibrationPattern() {
        return this.vibrationPattern;
    }

    public ChannelBuilder enableVibration(boolean z) {
        this.vibration = z;
        return this;
    }

    public boolean shouldVibrate() {
        long[] jArr;
        return this.vibration && (jArr = this.vibrationPattern) != null && jArr.length > 0;
    }

    public ChannelBuilder setSound(Uri uri, AudioAttributes audioAttributes) {
        this.sound = uri;
        this.audioAttributes = audioAttributes;
        return this;
    }

    public Uri getSound() {
        return this.sound;
    }

    public AudioAttributes getAudioAttributes() {
        return this.audioAttributes;
    }

    public ChannelBuilder setGroup(String str, String str2) {
        this.groupId = str;
        this.groupName = str2;
        return this;
    }

    public String getGroup() {
        return this.groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public ChannelBuilder setCreateIfExists(boolean z) {
        this.createIfExists = z;
        return this;
    }

    public boolean isCreateIfExists() {
        return this.createIfExists;
    }

    protected NotificationChannel createNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager) ContextUtils.requireSystemService(context, NotificationManager.class);
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(this.channelId);
        if (this.createIfExists || notificationChannel == null) {
            if (this.importance == 0) {
                this.importance = 3;
            }
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(this.channelId, this.name, this.importance);
            }
            notificationChannel.setImportance(this.importance);
            notificationChannel.setLockscreenVisibility(this.lockscreenVisibility);
            notificationChannel.setBypassDnd(this.bypassDnd);
            notificationChannel.setShowBadge(this.showBadge);
            notificationChannel.setLightColor(this.argb);
            notificationChannel.enableLights(this.lights);
            notificationChannel.setVibrationPattern(this.vibrationPattern);
            notificationChannel.enableVibration(this.vibration);
            notificationChannel.setSound(this.sound, this.audioAttributes);
        }
        if (notificationChannel != null) {
            if (!TextUtils.isEmpty(this.groupId)) {
                createNotificationChannelGroup(context, this.groupId, this.groupName);
            }
            notificationChannel.setName(this.name);
            notificationChannel.setDescription(this.description);
            notificationChannel.setGroup(this.groupId);
            notificationManager.createNotificationChannel(setupNotificationChannel(notificationChannel));
        }
        return notificationChannel;
    }

    protected void createNotificationChannelGroup(Context context, String str, String str2) {
        NotificationChannelGroup notificationChannelGroup;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) ContextUtils.requireSystemService(context, NotificationManager.class);
        Iterator<NotificationChannelGroup> it = notificationManager.getNotificationChannelGroups().iterator();
        while (true) {
            if (!it.hasNext()) {
                notificationChannelGroup = null;
                break;
            }
            notificationChannelGroup = it.next();
            if (str.equals(notificationChannelGroup.getId())) {
                break;
            }
        }
        if (notificationChannelGroup == null) {
            if (TextUtils.isEmpty(str2)) {
                str2 = str;
            }
            notificationManager.createNotificationChannelGroup(setupNotificationChannelGroup(new NotificationChannelGroup(str, str2)));
        }
    }
}
