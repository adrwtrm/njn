package com.epson.iprojection.ui.common.analytics.customdimension;

import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CustomDimensionForAllEvent.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001:\u0007\u001a\u001b\u001c\u001d\u001e\u001f B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0012\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0014\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u0006\u0010\u0019\u001a\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent;", "", "()V", "_connectionUserNum", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ConnectionUserNum;", "_projectorHeaderCode", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorHeaderCode;", "_projectorStatus", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorStatus;", "_projectorType", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorType;", "_protocolModeType", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProtocolModeType;", "_selectedN", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$SelectedNum;", "getConnectionUserNum", "", "getProjectorHeaderCodeParam", "getProjectorStatusParam", "getProjectorTypeParam", "getProtocolModeTypeParam", "getSelectedNumParam", "initialize", "", "updateChangeableInfoDuringConnect", "updateUnchangeableInfoDuringConnect", "AbstractParameter", "ConnectionUserNum", "ProjectorHeaderCode", "ProjectorStatus", "ProjectorType", "ProtocolModeType", "SelectedNum", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CustomDimensionForAllEvent {
    private final ProjectorStatus _projectorStatus = new ProjectorStatus();
    private final ProjectorType _projectorType = new ProjectorType();
    private final SelectedNum _selectedN = new SelectedNum();
    private final ProtocolModeType _protocolModeType = new ProtocolModeType();
    private final ProjectorHeaderCode _projectorHeaderCode = new ProjectorHeaderCode();
    private final ConnectionUserNum _connectionUserNum = new ConnectionUserNum();

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u000bH&R(\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "", "()V", "<set-?>", "", "_param", "get_param", "()Ljava/lang/String;", "set_param", "(Ljava/lang/String;)V", "setDefaultValue", "", "update", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static abstract class AbstractParameter {
        private String _param;

        public abstract void setDefaultValue();

        public abstract void update();

        public final String get_param() {
            return this._param;
        }

        protected final void set_param(String str) {
            this._param = str;
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorStatus;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class ProjectorStatus extends AbstractParameter {
        private static final String CONNECTING = "接続中";
        public static final Companion Companion = new Companion(null);
        private static final String DEFAULT = "未選択";
        private static final String REGISTED = "登録済";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(DEFAULT);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            if (Pj.getIns().isConnected()) {
                set_param(CONNECTING);
            } else if (Pj.getIns().isRegistered()) {
                set_param(REGISTED);
            } else {
                setDefaultValue();
            }
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorStatus$Companion;", "", "()V", "CONNECTING", "", "DEFAULT", "REGISTED", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0016\u0010\b\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0016\u0010\t\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorType;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "containBz", "", "list", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/ui/engine_wrapper/ConnectPjInfo;", "containHome", "containSig", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class ProjectorType extends AbstractParameter {
        private static final String BUSINESS = "ビジネス";
        public static final Companion Companion = new Companion(null);
        private static final String HOME = "ホーム";
        private static final String NONE = "未使用";
        private static final String SIGNAGE = "サイネージ";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(NONE);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            set_param("");
            ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
            if (nowConnectingPJList == null) {
                setDefaultValue();
                return;
            }
            if (containBz(nowConnectingPJList)) {
                set_param(get_param() + BUSINESS);
            }
            if (containHome(nowConnectingPJList)) {
                StringBuilder append = new StringBuilder().append(get_param());
                String str = get_param();
                Intrinsics.checkNotNull(str);
                set_param(append.append(str.length() > 0 ? ":" : "").toString());
                set_param(get_param() + HOME);
            }
            if (containSig(nowConnectingPJList)) {
                StringBuilder append2 = new StringBuilder().append(get_param());
                String str2 = get_param();
                Intrinsics.checkNotNull(str2);
                set_param(append2.append(str2.length() > 0 ? ":" : "").toString());
                set_param(get_param() + SIGNAGE);
            }
        }

        private final boolean containBz(ArrayList<ConnectPjInfo> arrayList) {
            Iterator<ConnectPjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                ConnectPjInfo next = it.next();
                if (!next.getPjInfo().isSmartphoneRemote && !next.getPjInfo().isSignageMode) {
                    return true;
                }
            }
            return false;
        }

        private final boolean containHome(ArrayList<ConnectPjInfo> arrayList) {
            Iterator<ConnectPjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().getPjInfo().isSmartphoneRemote) {
                    return true;
                }
            }
            return false;
        }

        private final boolean containSig(ArrayList<ConnectPjInfo> arrayList) {
            Iterator<ConnectPjInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().getPjInfo().isSignageMode) {
                    return true;
                }
            }
            return false;
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorType$Companion;", "", "()V", "BUSINESS", "", "HOME", "NONE", "SIGNAGE", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$SelectedNum;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class SelectedNum extends AbstractParameter {
        public static final Companion Companion = new Companion(null);
        private static final String NUM_0 = "なし";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(NUM_0);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
            if (nowConnectingPJList == null) {
                setDefaultValue();
            } else {
                set_param(String.valueOf(nowConnectingPJList.size()));
            }
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$SelectedNum$Companion;", "", "()V", "NUM_0", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProtocolModeType;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class ProtocolModeType extends AbstractParameter {
        public static final Companion Companion = new Companion(null);
        private static final String MPP_CLIENT = "MPPクライアント";
        private static final String MPP_COLLABORATION = "MPPコラボレーション";
        private static final String MPP_MODERATOR = "MPPモデレーター";
        private static final String NONE = "なし";
        private static final String NP = "NP";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(NONE);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            String str;
            if (Pj.getIns().isModerator()) {
                str = MPP_MODERATOR;
            } else if (Pj.getIns().isMppClient()) {
                str = MPP_CLIENT;
            } else if (Pj.getIns().isCollaboration()) {
                str = MPP_COLLABORATION;
            } else {
                str = (!Pj.getIns().isConnected() || Pj.getIns().isMpp()) ? NONE : NP;
            }
            set_param(str);
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProtocolModeType$Companion;", "", "()V", "MPP_CLIENT", "", "MPP_COLLABORATION", "MPP_MODERATOR", "NONE", ProtocolModeType.NP, "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorHeaderCode;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class ProjectorHeaderCode extends AbstractParameter {
        public static final Companion Companion = new Companion(null);
        private static final int HEADER_BEGIN = 4;
        private static final int HEADER_END = 8;
        private static final String NONE = "未選択";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(NONE);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
            if (nowConnectingPJList == null || !Pj.getIns().isConnected()) {
                setDefaultValue();
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            Pj.getIns().sendAndReceiveEscvpCommandWithIp(nowConnectingPJList.get(0).getPjInfo().IPAddr, "SNO?", stringBuffer, nowConnectingPJList.get(0).getPjInfo().isSupportedSecuredEscvp);
            set_param(stringBuffer.length() > 8 ? stringBuffer.substring(4, 8) : NONE);
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ProjectorHeaderCode$Companion;", "", "()V", "HEADER_BEGIN", "", "HEADER_END", "NONE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: CustomDimensionForAllEvent.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ConnectionUserNum;", "Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$AbstractParameter;", "()V", "getConnectedUserNum", "", "userList", "Ljava/util/ArrayList;", "Lcom/epson/iprojection/engine/common/D_MppUserInfo;", "Lkotlin/collections/ArrayList;", "setDefaultValue", "", "update", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class ConnectionUserNum extends AbstractParameter {
        public static final Companion Companion = new Companion(null);
        private static final String NONE = "not_logging";

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void setDefaultValue() {
            set_param(NONE);
        }

        @Override // com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent.AbstractParameter
        public void update() {
            if (Pj.getIns().getMppUserList() == null || !Pj.getIns().isConnected()) {
                setDefaultValue();
                return;
            }
            ArrayList<D_MppUserInfo> mppUserList = Pj.getIns().getMppUserList();
            Intrinsics.checkNotNullExpressionValue(mppUserList, "getIns().mppUserList");
            set_param(String.valueOf(getConnectedUserNum(mppUserList)));
        }

        private final int getConnectedUserNum(ArrayList<D_MppUserInfo> arrayList) {
            Iterator<D_MppUserInfo> it = arrayList.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (!it.next().disconnected) {
                    i++;
                }
            }
            return i;
        }

        /* compiled from: CustomDimensionForAllEvent.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/CustomDimensionForAllEvent$ConnectionUserNum$Companion;", "", "()V", "NONE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    public final void initialize() {
        this._projectorStatus.setDefaultValue();
        this._projectorType.setDefaultValue();
        this._selectedN.setDefaultValue();
        this._protocolModeType.setDefaultValue();
        this._projectorHeaderCode.setDefaultValue();
        this._connectionUserNum.setDefaultValue();
    }

    public final void updateChangeableInfoDuringConnect() {
        this._projectorStatus.update();
        this._projectorType.update();
        this._selectedN.update();
        this._protocolModeType.update();
        this._connectionUserNum.update();
    }

    public final void updateUnchangeableInfoDuringConnect() {
        this._projectorHeaderCode.update();
    }

    public final String getProjectorStatusParam() {
        return this._projectorStatus.get_param();
    }

    public final String getProjectorTypeParam() {
        return this._projectorType.get_param();
    }

    public final String getSelectedNumParam() {
        return this._selectedN.get_param();
    }

    public final String getProtocolModeTypeParam() {
        return this._protocolModeType.get_param();
    }

    public final String getProjectorHeaderCodeParam() {
        return this._projectorHeaderCode.get_param();
    }

    public final String getConnectionUserNum() {
        return this._connectionUserNum.get_param();
    }
}
