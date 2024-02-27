package com.epson.iprojection.ui.common.analytics.event.screenview;

import com.epson.iprojection.ui.activities.camera.permission.camera.PermissionCameraActivity;
import com.epson.iprojection.ui.activities.camera.permission.strage.PermissionStrageActivity;
import com.epson.iprojection.ui.activities.camera.views.activities.Activity_Camera;
import com.epson.iprojection.ui.activities.delivery.Activity_MarkerDelivery;
import com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery;
import com.epson.iprojection.ui.activities.drawermenu.NotAvailableMirroringActivity;
import com.epson.iprojection.ui.activities.fileselect.FileSelectActivity;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.mirroring.MirroringNotificationActivity;
import com.epson.iprojection.ui.activities.moderator.Activity_Moderator;
import com.epson.iprojection.ui.activities.moderator.Activity_Moderator_Thumbnail;
import com.epson.iprojection.ui.activities.pjselect.Activity_Other;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.activities.pjselect.history.Activity_PjHistory;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioActivity;
import com.epson.iprojection.ui.activities.pjselect.permission.wificonnection.PermissionLocationActivity;
import com.epson.iprojection.ui.activities.pjselect.profile.Activity_Profile;
import com.epson.iprojection.ui.activities.pjselect.qrcode.permission.PermissionQrCameraActivity;
import com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities.Activity_QrCamera;
import com.epson.iprojection.ui.activities.pjselect.settings.Activity_PjSettings;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.remote.Activity_Remote;
import com.epson.iprojection.ui.activities.remote.FragmentPjControlBatch;
import com.epson.iprojection.ui.activities.remote.FragmentPjControlTouchPad;
import com.epson.iprojection.ui.activities.restorewifi.Activity_RestoreWifi;
import com.epson.iprojection.ui.activities.splash.Activity_Splash;
import com.epson.iprojection.ui.activities.start.Activity_StartFromImplicitIntent;
import com.epson.iprojection.ui.activities.start.Activity_StartFromNfc;
import com.epson.iprojection.ui.activities.start.Activity_StartSplash;
import com.epson.iprojection.ui.activities.support.Activity_AppVersion;
import com.epson.iprojection.ui.activities.support.Activity_SupportEntrance;
import com.epson.iprojection.ui.activities.support.DebugSettingActivity;
import com.epson.iprojection.ui.activities.support.howto.Activity_HowTo;
import com.epson.iprojection.ui.activities.support.intro.CopyrightActivity;
import com.epson.iprojection.ui.activities.support.intro.LisenceActivity;
import com.epson.iprojection.ui.activities.support.intro.TrademarkActivity;
import com.epson.iprojection.ui.activities.support.intro.delivery.Activity_IntroDelivery;
import com.epson.iprojection.ui.activities.support.intro.gesturemenu.Activity_IntroGestureMenu;
import com.epson.iprojection.ui.activities.support.intro.mirroring.Activity_IntroMirroring;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.activities.support.intro.notfindpj.Activity_IntroNotFindPj;
import com.epson.iprojection.ui.activities.support.intro.overlay.Activity_IntroOverlay;
import com.epson.iprojection.ui.activities.support.intro.userctrl.Activity_IntroUserCtrl;
import com.epson.iprojection.ui.activities.support.intro.wifi.Activity_IntroWifi;
import com.epson.iprojection.ui.activities.terms.Activity_TermsToMain;
import com.epson.iprojection.ui.activities.web.Activity_Web;
import com.epson.iprojection.ui.activities.web.menu.bookmark.Activity_Bookmark;
import com.epson.iprojection.ui.activities.web.menu.history.Activity_WebHistory;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardActivity;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ScreenNameGetter {
    public static final String NO_SEND = "ga_no_send_screen_name";
    private static final ArrayList<D_GaDataSet> _list = new ArrayList<D_GaDataSet>() { // from class: com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameGetter.1
        {
            add(new D_GaDataSet(Activity_Camera.class.getName(), "カメラ画面"));
            add(new D_GaDataSet(PermissionStrageActivity.class.getName(), "カメラストレージ権限画面"));
            add(new D_GaDataSet(FileSelectActivity.class.getName(), "ファイル選択画面"));
            add(new D_GaDataSet(Activity_PresenDelivery.class.getName(), "受信画像画面"));
            add(new D_GaDataSet(Activity_Marker.class.getName(), "マーカー画面"));
            add(new D_GaDataSet(com.epson.iprojection.ui.activities.marker.permission.PermissionStrageActivity.class.getName(), "マーカーストレージ権限画面"));
            add(new D_GaDataSet(Activity_Moderator.class.getName(), "マルチ投写画面"));
            add(new D_GaDataSet(Activity_Moderator_Thumbnail.class.getName(), "マルチ投写サムネイル画面"));
            add(new D_GaDataSet(FragmentPjControlTouchPad.ANALYTICS_SHOW_TOUCHPAD, "ジェスチャリモコン画面"));
            add(new D_GaDataSet(FragmentPjControlTouchPad.ANALYTICS_SHOW_ESCVPSENDER, "リモコン画面"));
            add(new D_GaDataSet(Activity_Remote.class.getName(), "リモコン画面"));
            add(new D_GaDataSet(FragmentPjControlBatch.ANALYTICS_CONTROLALL, "リモコン一括操作画面"));
            add(new D_GaDataSet(Activity_PjHistory.class.getName(), "履歴接続画面"));
            add(new D_GaDataSet(Activity_QrCamera.class.getName(), "QRコード読み取り画面"));
            add(new D_GaDataSet(Activity_PjSettings.class.getName(), "アプリ設定画面"));
            add(new D_GaDataSet(Activity_Profile.class.getName(), "プロファイル画面いずれか"));
            add(new D_GaDataSet(Activity_Other.class.getName(), "指定検索画面"));
            add(new D_GaDataSet(Activity_PjSelect.class.getName(), "ホーム画面"));
            add(new D_GaDataSet(Activity_Presen.class.getName(), "プレゼン画面"));
            add(new D_GaDataSet(NotAvailableMirroringActivity.class.getName(), "ミラーリング未接続状態ガイド"));
            add(new D_GaDataSet(Activity_HowTo.class.getName(), "iProjectionの使い方画面"));
            add(new D_GaDataSet(Activity_IntroDelivery.class.getName(), "[チ]投写中の画面をユーザーに転送する画面"));
            add(new D_GaDataSet(Activity_IntroGestureMenu.class.getName(), "[チ]タッチパッドでメニューを操作する画面"));
            add(new D_GaDataSet(Activity_IntroModerator.class.getName(), "[チ]モデレーター機能を使用する画面"));
            add(new D_GaDataSet(Activity_IntroNotFindPj.class.getName(), "[チ]プロジェクターを見つける画面"));
            add(new D_GaDataSet(Activity_IntroUserCtrl.class.getName(), "[チ]マルチ投写を制御する画面"));
            add(new D_GaDataSet(Activity_IntroOverlay.class.getName(), "[チ]オーバレイ表示をする画面"));
            add(new D_GaDataSet(LisenceActivity.class.getName(), "ソフトウェア使用許諾画面"));
            add(new D_GaDataSet(CopyrightActivity.class.getName(), "著作権情報画面"));
            add(new D_GaDataSet(TrademarkActivity.class.getName(), "商標画面"));
            add(new D_GaDataSet(Activity_AppVersion.class.getName(), "バージョン情報画面"));
            add(new D_GaDataSet(Activity_SupportEntrance.class.getName(), "サポートトップ画面"));
            add(new D_GaDataSet(Activity_TermsToMain.class.getName(), "規約同意するしない画面"));
            add(new D_GaDataSet(Activity_Bookmark.class.getName(), "ブックマーク画面"));
            add(new D_GaDataSet(Activity_WebHistory.class.getName(), "Web履歴画面"));
            add(new D_GaDataSet(Activity_Web.class.getName(), "Web画面"));
            add(new D_GaDataSet(WhiteboardActivity.class.getName(), "Whiteboard画面"));
            add(new D_GaDataSet(Activity_RestoreWifi.class.getName(), "Notificationから復帰用通過画面"));
            add(new D_GaDataSet(MirroringNotificationActivity.class.getName(), "MirroringNotificationをタップ"));
            add(new D_GaDataSet(Activity_Splash.class.getName(), "スプラッシュ画面"));
            add(new D_GaDataSet(Activity_StartFromImplicitIntent.class.getName(), "暗黙的インテント用通過画面"));
            add(new D_GaDataSet(Activity_StartFromNfc.class.getName(), "NFCインテント用通過画面"));
            add(new D_GaDataSet(Activity_StartSplash.class.getName(), "ゼロ起動用通過画面"));
            add(new D_GaDataSet(Activity_IntroMirroring.class.getName(), "[チ]その他アプリを投写する画面"));
            add(new D_GaDataSet(Activity_IntroWifi.class.getName(), "[チ]マニュアルモードのWi-Fi設定説明画面"));
            add(new D_GaDataSet(Activity_MarkerDelivery.class.getName(), "マーカー画面(受信画像の)"));
            add(new D_GaDataSet(PermissionQrCameraActivity.class.getName(), "QRカメラパーミッション"));
            add(new D_GaDataSet(PermissionCameraActivity.class.getName(), "カメラパーミッション"));
            add(new D_GaDataSet(PermissionAudioActivity.class.getName(), "Audioパーミッション"));
            add(new D_GaDataSet(PermissionLocationActivity.class.getName(), "[WiFi]位置情報パーミッション"));
            add(new D_GaDataSet(com.epson.iprojection.ui.activities.pjselect.permission.registration.PermissionLocationActivity.class.getName(), "[登録]位置情報パーミッション"));
            add(new D_GaDataSet(DebugSettingActivity.class.getName(), "デバッグ設定画面"));
        }
    };

    public static String getScreenName(String str) {
        Iterator<D_GaDataSet> it = _list.iterator();
        while (it.hasNext()) {
            D_GaDataSet next = it.next();
            if (next._className.compareTo(str) == 0) {
                if (next._screenName.compareTo("ホーム画面") == 0) {
                    if (Pj.getIns().isConnected()) {
                        return String.format("%s[%s]", next._screenName, "接続済");
                    }
                    if (Pj.getIns().isRegistered()) {
                        return String.format("%s[%s]", next._screenName, "登録済");
                    }
                    return String.format("%s[%s]", next._screenName, "未選択");
                }
                return next._screenName;
            }
        }
        return "[スクリーン名取得エラー]:" + str;
    }

    public static boolean isSendable(String str) {
        return !str.contains(NO_SEND);
    }
}
