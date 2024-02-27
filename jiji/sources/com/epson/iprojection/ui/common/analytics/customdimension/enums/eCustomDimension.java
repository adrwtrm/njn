package com.epson.iprojection.ui.common.analytics.customdimension.enums;

import kotlin.Metadata;

/* compiled from: eCustomDimension.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b%\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'¨\u0006("}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/eCustomDimension;", "", "dimensionName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDimensionName", "()Ljava/lang/String;", "SEARCH_ROUTE", "SEARCH_ROUTE_REGISTERED", "CONNECT_ERROR_REASON", "MANUAL_SEARCH_RESULT", "LAUNCH_CONNECTING_TIME", "TIME_FROM_DISCONNECTED_TO_CONNECT", "CONNECTING_TIME", "FIRST_TIME_PROJECTION_CONTENTS", "REQUEST_TRANSFER_SCREEN", "PRESENTATION_CONTENTS", "PRESENTATION_IMPLICIT", "WEB_SCREEN_INTENT", "PEN_USAGE_SITUATION", "DISPLAY_COUNT", "DISPLAY_TIME", "CONTENTS_USED_COUNT", "PROTOCOL_AND_CODEC", "DEFINITION_FILE_READ_RESULT", "CAMERA_PERMISSION", "LOCATION_PERMISSION", "CONNECT_AS_MODERATOR", "OPENED_CONTENTS", "USE_BAND_WIDTH", "AUDIO_TRANSFER_SETTING", "CONNECTION_USERS_NUM", "PROJECTOR_STATUS", "PROJECTOR_TYPE", "PROJECTOR_HEAD", "PROJECTOR_NUM", "PROTOCOL_MODE", "SCREEN_NAME", "SEARCH_RESULT", "COUNT", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum eCustomDimension {
    SEARCH_ROUTE("検索経路"),
    SEARCH_ROUTE_REGISTERED("検索経路_登録"),
    CONNECT_ERROR_REASON("接続失敗理由"),
    MANUAL_SEARCH_RESULT("指定検索結果"),
    LAUNCH_CONNECTING_TIME("起動から接続までの時間"),
    TIME_FROM_DISCONNECTED_TO_CONNECT("切断から接続までの時間"),
    CONNECTING_TIME("接続時間"),
    FIRST_TIME_PROJECTION_CONTENTS("初回投写コンテンツ"),
    REQUEST_TRANSFER_SCREEN("転送画面"),
    PRESENTATION_CONTENTS("ファイル種別_Androidドキュメント"),
    PRESENTATION_IMPLICIT("プレゼン画面表示方法"),
    WEB_SCREEN_INTENT("Web画面表示方法"),
    PEN_USAGE_SITUATION("ペンの使用状況"),
    DISPLAY_COUNT("利用回数"),
    DISPLAY_TIME("利用時間"),
    CONTENTS_USED_COUNT("コンテンツ利用回数"),
    PROTOCOL_AND_CODEC("プロトコルとコーデック"),
    DEFINITION_FILE_READ_RESULT("設定ファイル読み込み結果"),
    CAMERA_PERMISSION("カメラパーミッション実行結果"),
    LOCATION_PERMISSION("位置情報パーミッション実行結果"),
    CONNECT_AS_MODERATOR("モデレータとして接続"),
    OPENED_CONTENTS("開いているコンテンツ"),
    USE_BAND_WIDTH("使用帯域"),
    AUDIO_TRANSFER_SETTING("音声転送設定"),
    CONNECTION_USERS_NUM("接続ユーザー数"),
    PROJECTOR_STATUS("プロジェクター状態"),
    PROJECTOR_TYPE("プロジェクター種別"),
    PROJECTOR_HEAD("機種ヘッダーコード"),
    PROJECTOR_NUM("選択台数"),
    PROTOCOL_MODE("プロトコル_モード種別"),
    SCREEN_NAME("スクリーン名"),
    SEARCH_RESULT("調査結果"),
    COUNT("回数");
    
    private final String dimensionName;

    eCustomDimension(String str) {
        this.dimensionName = str;
    }

    public final String getDimensionName() {
        return this.dimensionName;
    }
}
