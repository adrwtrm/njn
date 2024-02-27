package com.epson.iprojection.ui.activities.drawermenu;

/* loaded from: classes.dex */
public enum eDrawerMenuItem {
    Connect("ホーム"),
    Remote("リモコン"),
    UserCtl("マルチ投写"),
    Photo("写真"),
    Document("PDF"),
    Web("Web"),
    Camera("カメラ"),
    Delivery("受信画像"),
    AppSettings("アプリ設定"),
    Support("サポート");
    
    private final String menuName;

    eDrawerMenuItem(String str) {
        this.menuName = str;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public static String stringOf(int i) {
        eDrawerMenuItem[] values;
        for (eDrawerMenuItem edrawermenuitem : values()) {
            if (edrawermenuitem.ordinal() == i) {
                return edrawermenuitem.getMenuName();
            }
        }
        return null;
    }
}
