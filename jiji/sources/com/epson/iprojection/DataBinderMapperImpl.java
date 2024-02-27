package com.epson.iprojection;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.databinding.MainCameraBindingImpl;
import com.epson.iprojection.databinding.MainQrcodeCameraBindingImpl;
import com.epson.iprojection.databinding.ToolbarCameraBindingImpl;
import com.epson.iprojection.databinding.ToolbarDocumentBindingImpl;
import com.epson.iprojection.databinding.ToolbarHistoryBindingImpl;
import com.epson.iprojection.databinding.ToolbarHowtoBindingImpl;
import com.epson.iprojection.databinding.ToolbarLicenseBindingImpl;
import com.epson.iprojection.databinding.ToolbarMarkerBindingImpl;
import com.epson.iprojection.databinding.ToolbarModeratorBindingImpl;
import com.epson.iprojection.databinding.ToolbarNotavailableMirroringBindingImpl;
import com.epson.iprojection.databinding.ToolbarOtherBindingImpl;
import com.epson.iprojection.databinding.ToolbarPresenBindingImpl;
import com.epson.iprojection.databinding.ToolbarPresenEditBindingImpl;
import com.epson.iprojection.databinding.ToolbarPresenEmptyBindingImpl;
import com.epson.iprojection.databinding.ToolbarProfileBindingImpl;
import com.epson.iprojection.databinding.ToolbarRemoteBindingImpl;
import com.epson.iprojection.databinding.ToolbarSelectBindingImpl;
import com.epson.iprojection.databinding.ToolbarSettingBindingImpl;
import com.epson.iprojection.databinding.ToolbarSupportEntranceBindingImpl;
import com.epson.iprojection.databinding.ToolbarWebBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_MAINCAMERA = 1;
    private static final int LAYOUT_MAINQRCODECAMERA = 2;
    private static final int LAYOUT_TOOLBARCAMERA = 3;
    private static final int LAYOUT_TOOLBARDOCUMENT = 4;
    private static final int LAYOUT_TOOLBARHISTORY = 5;
    private static final int LAYOUT_TOOLBARHOWTO = 6;
    private static final int LAYOUT_TOOLBARLICENSE = 7;
    private static final int LAYOUT_TOOLBARMARKER = 8;
    private static final int LAYOUT_TOOLBARMODERATOR = 9;
    private static final int LAYOUT_TOOLBARNOTAVAILABLEMIRRORING = 10;
    private static final int LAYOUT_TOOLBAROTHER = 11;
    private static final int LAYOUT_TOOLBARPRESEN = 12;
    private static final int LAYOUT_TOOLBARPRESENEDIT = 13;
    private static final int LAYOUT_TOOLBARPRESENEMPTY = 14;
    private static final int LAYOUT_TOOLBARPROFILE = 15;
    private static final int LAYOUT_TOOLBARREMOTE = 16;
    private static final int LAYOUT_TOOLBARSELECT = 17;
    private static final int LAYOUT_TOOLBARSETTING = 18;
    private static final int LAYOUT_TOOLBARSUPPORTENTRANCE = 19;
    private static final int LAYOUT_TOOLBARWEB = 20;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(20);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.main_camera, 1);
        sparseIntArray.put(R.layout.main_qrcode_camera, 2);
        sparseIntArray.put(R.layout.toolbar_camera, 3);
        sparseIntArray.put(R.layout.toolbar_document, 4);
        sparseIntArray.put(R.layout.toolbar_history, 5);
        sparseIntArray.put(R.layout.toolbar_howto, 6);
        sparseIntArray.put(R.layout.toolbar_license, 7);
        sparseIntArray.put(R.layout.toolbar_marker, 8);
        sparseIntArray.put(R.layout.toolbar_moderator, 9);
        sparseIntArray.put(R.layout.toolbar_notavailable_mirroring, 10);
        sparseIntArray.put(R.layout.toolbar_other, 11);
        sparseIntArray.put(R.layout.toolbar_presen, 12);
        sparseIntArray.put(R.layout.toolbar_presen_edit, 13);
        sparseIntArray.put(R.layout.toolbar_presen_empty, 14);
        sparseIntArray.put(R.layout.toolbar_profile, 15);
        sparseIntArray.put(R.layout.toolbar_remote, 16);
        sparseIntArray.put(R.layout.toolbar_select, 17);
        sparseIntArray.put(R.layout.toolbar_setting, 18);
        sparseIntArray.put(R.layout.toolbar_support_entrance, 19);
        sparseIntArray.put(R.layout.toolbar_web, 20);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        int i2 = INTERNAL_LAYOUT_ID_LOOKUP.get(i);
        if (i2 > 0) {
            Object tag = view.getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
            switch (i2) {
                case 1:
                    if ("layout/main_camera_0".equals(tag)) {
                        return new MainCameraBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for main_camera is invalid. Received: " + tag);
                case 2:
                    if ("layout/main_qrcode_camera_0".equals(tag)) {
                        return new MainQrcodeCameraBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for main_qrcode_camera is invalid. Received: " + tag);
                case 3:
                    if ("layout/toolbar_camera_0".equals(tag)) {
                        return new ToolbarCameraBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_camera is invalid. Received: " + tag);
                case 4:
                    if ("layout/toolbar_document_0".equals(tag)) {
                        return new ToolbarDocumentBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_document is invalid. Received: " + tag);
                case 5:
                    if ("layout/toolbar_history_0".equals(tag)) {
                        return new ToolbarHistoryBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_history is invalid. Received: " + tag);
                case 6:
                    if ("layout/toolbar_howto_0".equals(tag)) {
                        return new ToolbarHowtoBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_howto is invalid. Received: " + tag);
                case 7:
                    if ("layout/toolbar_license_0".equals(tag)) {
                        return new ToolbarLicenseBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_license is invalid. Received: " + tag);
                case 8:
                    if ("layout/toolbar_marker_0".equals(tag)) {
                        return new ToolbarMarkerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_marker is invalid. Received: " + tag);
                case 9:
                    if ("layout/toolbar_moderator_0".equals(tag)) {
                        return new ToolbarModeratorBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_moderator is invalid. Received: " + tag);
                case 10:
                    if ("layout/toolbar_notavailable_mirroring_0".equals(tag)) {
                        return new ToolbarNotavailableMirroringBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_notavailable_mirroring is invalid. Received: " + tag);
                case 11:
                    if ("layout/toolbar_other_0".equals(tag)) {
                        return new ToolbarOtherBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_other is invalid. Received: " + tag);
                case 12:
                    if ("layout/toolbar_presen_0".equals(tag)) {
                        return new ToolbarPresenBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_presen is invalid. Received: " + tag);
                case 13:
                    if ("layout/toolbar_presen_edit_0".equals(tag)) {
                        return new ToolbarPresenEditBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_presen_edit is invalid. Received: " + tag);
                case 14:
                    if ("layout/toolbar_presen_empty_0".equals(tag)) {
                        return new ToolbarPresenEmptyBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_presen_empty is invalid. Received: " + tag);
                case 15:
                    if ("layout/toolbar_profile_0".equals(tag)) {
                        return new ToolbarProfileBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_profile is invalid. Received: " + tag);
                case 16:
                    if ("layout/toolbar_remote_0".equals(tag)) {
                        return new ToolbarRemoteBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_remote is invalid. Received: " + tag);
                case 17:
                    if ("layout/toolbar_select_0".equals(tag)) {
                        return new ToolbarSelectBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_select is invalid. Received: " + tag);
                case 18:
                    if ("layout/toolbar_setting_0".equals(tag)) {
                        return new ToolbarSettingBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_setting is invalid. Received: " + tag);
                case 19:
                    if ("layout/toolbar_support_entrance_0".equals(tag)) {
                        return new ToolbarSupportEntranceBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_support_entrance is invalid. Received: " + tag);
                case 20:
                    if ("layout/toolbar_web_0".equals(tag)) {
                        return new ToolbarWebBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for toolbar_web is invalid. Received: " + tag);
                default:
                    return null;
            }
        }
        return null;
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Integer num;
        if (str == null || (num = InnerLayoutIdLookup.sKeys.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return InnerBrLookup.sKeys.get(i);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return arrayList;
    }

    /* loaded from: classes.dex */
    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(3);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "imageRes");
            sparseArray.put(2, "viewmodel");
        }
    }

    /* loaded from: classes.dex */
    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(20);
            sKeys = hashMap;
            hashMap.put("layout/main_camera_0", Integer.valueOf((int) R.layout.main_camera));
            hashMap.put("layout/main_qrcode_camera_0", Integer.valueOf((int) R.layout.main_qrcode_camera));
            hashMap.put("layout/toolbar_camera_0", Integer.valueOf((int) R.layout.toolbar_camera));
            hashMap.put("layout/toolbar_document_0", Integer.valueOf((int) R.layout.toolbar_document));
            hashMap.put("layout/toolbar_history_0", Integer.valueOf((int) R.layout.toolbar_history));
            hashMap.put("layout/toolbar_howto_0", Integer.valueOf((int) R.layout.toolbar_howto));
            hashMap.put("layout/toolbar_license_0", Integer.valueOf((int) R.layout.toolbar_license));
            hashMap.put("layout/toolbar_marker_0", Integer.valueOf((int) R.layout.toolbar_marker));
            hashMap.put("layout/toolbar_moderator_0", Integer.valueOf((int) R.layout.toolbar_moderator));
            hashMap.put("layout/toolbar_notavailable_mirroring_0", Integer.valueOf((int) R.layout.toolbar_notavailable_mirroring));
            hashMap.put("layout/toolbar_other_0", Integer.valueOf((int) R.layout.toolbar_other));
            hashMap.put("layout/toolbar_presen_0", Integer.valueOf((int) R.layout.toolbar_presen));
            hashMap.put("layout/toolbar_presen_edit_0", Integer.valueOf((int) R.layout.toolbar_presen_edit));
            hashMap.put("layout/toolbar_presen_empty_0", Integer.valueOf((int) R.layout.toolbar_presen_empty));
            hashMap.put("layout/toolbar_profile_0", Integer.valueOf((int) R.layout.toolbar_profile));
            hashMap.put("layout/toolbar_remote_0", Integer.valueOf((int) R.layout.toolbar_remote));
            hashMap.put("layout/toolbar_select_0", Integer.valueOf((int) R.layout.toolbar_select));
            hashMap.put("layout/toolbar_setting_0", Integer.valueOf((int) R.layout.toolbar_setting));
            hashMap.put("layout/toolbar_support_entrance_0", Integer.valueOf((int) R.layout.toolbar_support_entrance));
            hashMap.put("layout/toolbar_web_0", Integer.valueOf((int) R.layout.toolbar_web));
        }
    }
}
