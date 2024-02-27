package com.epson.iprojection.ui.common.analytics.event.enums;

import kotlin.Metadata;

/* compiled from: ECustomEvent.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b;\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=¨\u0006>"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/event/enums/eCustomEvent;", "", "eventName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getEventName", "()Ljava/lang/String;", "CONNECT", "CONNECT_COMPLETE", "CONNECT_ERROR_COUNT", "REGISTERED", "CONNECT_ERROR", "MANUAL_SEARCH", "OPERATION_TIME", "FIRST_TIME_PROJECTION", "REQUEST_TRANSFER_SCREEN", "RECEIVED_TRANSFER_IMAGE", "PRESENTATION_SCREEN", "WEB_SCREEN", "PEN_USAGE_SITUATION", "PHOTO_DISPLAY_START", "PHOTO_DISPLAY_END", "DOCUMENT_DISPLAY_START", "DOCUMENT_DISPLAY_END", "WEB_DISPLAY_START", "WEB_DISPLAY_END", "CAMERA_DISPLAY_START", "CAMERA_DISPLAY_END", "RECEIVED_IMAGE_DISPLAY_START", "RECEIVED_IMAGE_DISPLAY_END", "MIRRORING_START", "MIRRORING_END", "DEFINITION_FILE", "CAMERA_PERMISSION", "LOCATION_PERMISSION", "PROJECTOR_HEADER", "MODERATOR_START", "MODERATOR_END", "DISCONNECT", "PROJECT_ME", "PARTITION1", "PARTITION2", "PARTITION4", "PLAY", "STOP", "PAUSE", "THUMBNAIL_DISPLAY", "THUMBNAIL_RELOAD", "THUMBNAIL_SCALE", "USER_OPERATION", "SATISFACTION_UI_DISPLAY_LAUNCH", "SATISFACTION_UI_DISPLAY_OPERATION_END", "SATISFACTION_UI_SEND_LAUNCH", "SATISFACTION_UI_SEND_OPERATION_END", "SATISFACTION_UI_CANCEL_LAUNCH", "SATISFACTION_UI_CANCEL_OPERATION_END", "USAGE_TIPS", "MANUAL", "SUPPORTED_PROJECTORS_LIST", "EPSON_SETUP_NAVI", "SUPPORT_SCREEN_DISPLAY_FROM_HOME", "SUPPORT_SCREEN_DISPLAY_FROM_DRAWER", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum eCustomEvent {
    CONNECT("接続確認"),
    CONNECT_COMPLETE("接続完了"),
    CONNECT_ERROR_COUNT("成功までの失敗回数"),
    REGISTERED("登録完了"),
    CONNECT_ERROR("接続失敗"),
    MANUAL_SEARCH("指定検索"),
    OPERATION_TIME("操作時間"),
    FIRST_TIME_PROJECTION("初回投写"),
    REQUEST_TRANSFER_SCREEN("画面転送要求"),
    RECEIVED_TRANSFER_IMAGE("転送画像を受信"),
    PRESENTATION_SCREEN("プレゼン画面表示"),
    WEB_SCREEN("Web画面表示"),
    PEN_USAGE_SITUATION("マーカー機能の利用"),
    PHOTO_DISPLAY_START("写真表示開始"),
    PHOTO_DISPLAY_END("写真表示終了"),
    DOCUMENT_DISPLAY_START("ドキュメント表示開始"),
    DOCUMENT_DISPLAY_END("ドキュメント表示終了"),
    WEB_DISPLAY_START("Web表示開始"),
    WEB_DISPLAY_END("Web表示終了"),
    CAMERA_DISPLAY_START("カメラ表示開始"),
    CAMERA_DISPLAY_END("カメラ表示終了"),
    RECEIVED_IMAGE_DISPLAY_START("受信画像表示開始"),
    RECEIVED_IMAGE_DISPLAY_END("受信画像表示終了"),
    MIRRORING_START("端末の画面表示開始"),
    MIRRORING_END("端末の画面表示終了"),
    DEFINITION_FILE("設定ファイル"),
    CAMERA_PERMISSION("カメラパーミッション"),
    LOCATION_PERMISSION("位置情報パーミッション"),
    PROJECTOR_HEADER("機種ヘッダーコード"),
    MODERATOR_START("モデレータになる"),
    MODERATOR_END("モデレータをやめる"),
    DISCONNECT("切断"),
    PROJECT_ME("自分の画面を投写"),
    PARTITION1("分割1画面"),
    PARTITION2("分割2画面"),
    PARTITION4("分割4画面"),
    PLAY("再生"),
    STOP("停止"),
    PAUSE("一時停止"),
    THUMBNAIL_DISPLAY("サムネイルを表示"),
    THUMBNAIL_RELOAD("サムネイルリロード"),
    THUMBNAIL_SCALE("ユーザーリストのサムネイルサイズ変更"),
    USER_OPERATION("ユーザ制御"),
    SATISFACTION_UI_DISPLAY_LAUNCH("満足度調査UI_表示_起動時"),
    SATISFACTION_UI_DISPLAY_OPERATION_END("満足度調査UI_表示_操作終了時"),
    SATISFACTION_UI_SEND_LAUNCH("満足度調査UI_結果送信_起動時"),
    SATISFACTION_UI_SEND_OPERATION_END("満足度調査UI_結果送信_操作終了時"),
    SATISFACTION_UI_CANCEL_LAUNCH("満足度調査UI_キャンセル_起動時"),
    SATISFACTION_UI_CANCEL_OPERATION_END("満足度調査UI_キャンセル_操作終了時"),
    USAGE_TIPS("使い方のヒント"),
    MANUAL("マニュアル"),
    SUPPORTED_PROJECTORS_LIST("対応プロジェクターリスト"),
    EPSON_SETUP_NAVI("EpsonSetupNavi"),
    SUPPORT_SCREEN_DISPLAY_FROM_HOME("サポート画面表示_ホーム画面から"),
    SUPPORT_SCREEN_DISPLAY_FROM_DRAWER("サポート画面表示_ドロワーメニューから");
    
    private final String eventName;

    eCustomEvent(String str) {
        this.eventName = str;
    }

    public final String getEventName() {
        return this.eventName;
    }
}
