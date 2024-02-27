package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_MirrorPjInfo;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ProjectorDetailDialog {
    private final Context _context;
    private AlertDialog _dialog;

    public static /* synthetic */ void lambda$show$0(DialogInterface dialogInterface, int i) {
    }

    public ProjectorDetailDialog(Context context) {
        this._context = context;
    }

    public void show(D_PjInfo d_PjInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._context);
        builder.setTitle(this._context.getString(R.string._DetailInformation_));
        builder.setMessage(createProjectorDetail(d_PjInfo, Pj.getIns().isConnected()));
        builder.setPositiveButton(this._context.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.ProjectorDetailDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectorDetailDialog.lambda$show$0(dialogInterface, i);
            }
        });
        AlertDialog create = builder.create();
        this._dialog = create;
        create.show();
        RegisteredDialog.getIns().setDialog(this._dialog);
    }

    public boolean isShowing() {
        return this._dialog.isShowing();
    }

    private String createProjectorDetail(D_PjInfo d_PjInfo, boolean z) {
        ConnectPjInfo next;
        String str = (this._context.getString(R.string._ProjectorName_) + ":\n" + d_PjInfo.PrjName + "\n\n") + this._context.getString(R.string._IPAddress_) + ":\n" + NetUtils.cvtIPAddr(d_PjInfo.IPAddr) + "\n\n";
        if (z) {
            Iterator<ConnectPjInfo> it = Pj.getIns().getNowConnectingPJList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (d_PjInfo.ProjectorID == it.next().getPjInfo().ProjectorID) {
                    str = str + this._context.getString(R.string._ProjectorKeyword_) + ":\n" + createProjectorDetailKeyword(next) + "\n\n";
                    break;
                }
            }
        }
        return str + this._context.getString(R.string._MirroringGroup_) + ":\n" + createProjectorDetailMirroring(d_PjInfo, z);
    }

    private String createProjectorDetailKeyword(ConnectPjInfo connectPjInfo) {
        if (connectPjInfo.getPjInfo().isNeededPjKeyword) {
            return Pj.getIns().isLinkageDataConnected() ? this._context.getString(R.string._Unknown_) : connectPjInfo.getKeyword();
        }
        return this._context.getString(R.string._KeywordNone_);
    }

    private String createProjectorDetailMirroring(D_PjInfo d_PjInfo, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (1 < d_PjInfo.aMirrorPjList.size()) {
            Iterator<D_MirrorPjInfo> it = d_PjInfo.aMirrorPjList.iterator();
            while (it.hasNext()) {
                D_MirrorPjInfo next = it.next();
                if (!next.isSamePjInfo(d_PjInfo)) {
                    sb.append(next.prjName).append("\n");
                    if (z) {
                        Iterator<ConnectPjInfo> it2 = Pj.getIns().getNowConnectingPJList().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                ConnectPjInfo next2 = it2.next();
                                if (next.isSamePjInfo(next2.getPjInfo())) {
                                    sb.append("(").append(this._context.getString(R.string._ProjectorKeyword_)).append(":").append(createProjectorDetailKeyword(next2)).append(")\n");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}
