package com.epson.iprojection.ui.engine_wrapper.utils;

import com.epson.iprojection.engine.Engine;

/* loaded from: classes.dex */
public final class MsgGetterUtils {
    public static String getConnectStatusMsg(int i) {
        return i != 1 ? i != 3 ? i != 5 ? "不明なエラーコード" : "切断処理結果通知" : "投射中のエラー通知、切断してください。" : "接続処理結果通知";
    }

    public static String getControlCommandMsg(int i) {
        switch (i) {
            case 1:
                return "A/Vミュート";
            case 2:
                return "ソース切り替え（ビデオ系）";
            case 3:
                return "ソース切り替え（コンピュータ系）";
            case 4:
                return "ボリュームアップ";
            case 5:
                return "ボリュームダウン";
            case 6:
                return "LAN";
            case 7:
                return "FREEZE";
            default:
                return "不明なコマンド(未定義)";
        }
    }

    public static String getErrMsg(int i) {
        if (i != -430) {
            if (i != -426) {
                if (i != -424) {
                    if (i != -421) {
                        if (i != -411) {
                            if (i != -204) {
                                if (i != -13) {
                                    if (i != -9) {
                                        if (i != -5) {
                                            if (i != -2) {
                                                if (i != -409) {
                                                    if (i != -408) {
                                                        if (i != -214) {
                                                            if (i != -213) {
                                                                if (i != -207) {
                                                                    if (i != -206) {
                                                                        if (i != -202) {
                                                                            if (i != -201) {
                                                                                if (i != -18) {
                                                                                    if (i != -17) {
                                                                                        if (i != 0) {
                                                                                            if (i != 1) {
                                                                                                if (i != 2) {
                                                                                                    switch (i) {
                                                                                                        case Engine.ERROR_ALREADY_NOW_UPDATE_CLIENT_RESOLUTION /* -453 */:
                                                                                                            return "すでにUpdateClientResolution実施中";
                                                                                                        case Engine.ERROR_NOT_SELECT_RESOLUTION_IMAGE /* -452 */:
                                                                                                            return "選択外の解像度のイメージが指定された";
                                                                                                        case Engine.ERROR_NOT_SUPPORT_RESOLUTION /* -451 */:
                                                                                                            return "サポート外の解像度の場合";
                                                                                                        case Engine.ERROR_DISCONNECT_TIMEOUT /* -450 */:
                                                                                                            return "切断処理のタイムアウト";
                                                                                                        default:
                                                                                                            switch (i) {
                                                                                                                case -440:
                                                                                                                    return "検索中ではないのに検索停止が要求された";
                                                                                                                case -439:
                                                                                                                    return "プロジェクターと未接続のため実施不可";
                                                                                                                case Engine.ENGINE_INFO_DISCONNECTED_ADMIN /* -438 */:
                                                                                                                    return "管理者から切断された";
                                                                                                                default:
                                                                                                                    switch (i) {
                                                                                                                        case Engine.ERROR_NETWORK_ERROR /* -406 */:
                                                                                                                            return "接続中に通信エラーが発生した";
                                                                                                                        case Engine.ERROR_APPLICATION_VERSION /* -405 */:
                                                                                                                            return "接続するアプリケーションのバージョンが古すぎる";
                                                                                                                        case Engine.ERROR_INTERRUPT /* -404 */:
                                                                                                                            return "接続中に他PCに割り込まれた";
                                                                                                                        case Engine.ERROR_DISCONNECT_CLIENT /* -403 */:
                                                                                                                            return "接続中にプロジェクターがOFFになった";
                                                                                                                        case Engine.ERROR_ILLEGAL_PROJECTORKEYWORD /* -402 */:
                                                                                                                            return "プロジェクターキーワードが間違っている";
                                                                                                                        case Engine.ERROR_NOTCONNECT_PROJECTOR /* -401 */:
                                                                                                                            return "プロジェクターの接続処理に失敗した";
                                                                                                                        default:
                                                                                                                            return "不明なエラーコード";
                                                                                                                    }
                                                                                                            }
                                                                                                    }
                                                                                                }
                                                                                                return "正常終了(選択UIを表示する)";
                                                                                            }
                                                                                            return "キャンセル";
                                                                                        }
                                                                                        return "正常終了";
                                                                                    }
                                                                                    return "検索処理中";
                                                                                }
                                                                                return "接続処理中";
                                                                            }
                                                                            return "使用可能なNICの情報取得に失敗";
                                                                        }
                                                                        return "NICのIPアドレスが無効である";
                                                                    }
                                                                    return "コマンドの送信に失敗";
                                                                }
                                                                return "検索処理動作中";
                                                            }
                                                            return "NICが有効でない";
                                                        }
                                                        return "NICがネットワークに接続されていない";
                                                    }
                                                    return "投射処理に失敗した";
                                                }
                                                return "プロジェクターの切断処理に失敗した";
                                            }
                                            return "内部エラー(他に分類不可能なエラー含む)";
                                        }
                                        return "パラメータエラー";
                                    }
                                    return "初期化されていない";
                                }
                                return "ソケット読み込み／書き込みエラー";
                            }
                            return "指定の検索情報と競合するものが検索情報リストにある";
                        }
                        return "投射モードの設定／取得に失敗した";
                    }
                    return "応答なし";
                }
                return "他のユーザーが会議を終了した";
            }
            return "プロジェクター情報未受信";
        }
        return "プロジェクタ制御コマンドの送受信エラー";
    }

    public static String getSearchModeMsg(int i) {
        return i != 0 ? i != 1 ? "不明な検索種別" : "ステータスの更新(指定検索含む)" : "自動検索";
    }

    private MsgGetterUtils() {
    }
}
