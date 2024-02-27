package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class RegisteredPjDataForFile implements Serializable {
    public static final String FILE_NAME = "registeredPjs.dat";
    private final ArrayList<D_HistoryInfo> _list = new ArrayList<>();

    public void save(Context context, ArrayList<ConnectPjInfo> arrayList) {
        Iterator<ConnectPjInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            this._list.add(D_HistoryInfo.convertFromPjInfo(it.next().getPjInfo()));
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput("registeredPjs.dat", 0));
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception unused) {
        }
    }

    public ArrayList<ConnectPjInfo> get(Context context) {
        ConnectConfig connectConfig = new ConnectConfig(context);
        ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
        Iterator<D_HistoryInfo> it = this._list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConnectPjInfo(it.next().convertToPjInfo(), connectConfig.getInterruptSetting()));
        }
        return arrayList;
    }

    public void remove(Context context) {
        context.deleteFile("registeredPjs.dat");
    }

    public static RegisteredPjDataForFile read(Context context) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput("registeredPjs.dat"));
            RegisteredPjDataForFile registeredPjDataForFile = (RegisteredPjDataForFile) objectInputStream.readObject();
            objectInputStream.close();
            return registeredPjDataForFile;
        } catch (Exception unused) {
            return new RegisteredPjDataForFile();
        }
    }
}
