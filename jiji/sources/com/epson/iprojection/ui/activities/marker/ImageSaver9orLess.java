package com.epson.iprojection.ui.activities.marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.ImgFileUtils;
import java.io.File;

/* loaded from: classes.dex */
public class ImageSaver9orLess extends ImageSaver {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SaveParam {
        public Bitmap bmp;
        public boolean isDelivery;

        private SaveParam() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SaveResult {
        public String path;
        public boolean result;

        private SaveResult() {
        }
    }

    public ImageSaver9orLess(Context context) {
        super(context);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.epson.iprojection.ui.activities.marker.ImageSaver9orLess$1] */
    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver
    public void save(Bitmap bitmap, boolean z) {
        SaveParam saveParam = new SaveParam();
        saveParam.bmp = bitmap;
        saveParam.isDelivery = z;
        new AsyncTask<SaveParam, Void, SaveResult>() { // from class: com.epson.iprojection.ui.activities.marker.ImageSaver9orLess.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public SaveResult doInBackground(SaveParam... saveParamArr) {
                String str;
                SaveParam saveParam2 = saveParamArr[0];
                SaveResult saveResult = new SaveResult();
                if (saveParam2.isDelivery) {
                    str = Environment.getExternalStorageDirectory().getPath() + "/EPSON/iProjection/SharedImages";
                } else {
                    str = Environment.getExternalStorageDirectory().getPath() + "/EPSON/iProjection";
                }
                File file = new File(str);
                if (!file.exists() && !file.mkdirs()) {
                    saveResult.result = false;
                    return saveResult;
                }
                String str2 = str + "/" + ImageSaver9orLess.this.getTimeFileName();
                File file2 = new File(str2 + ImageSaver.FILE_TYPE);
                if (!file2.exists()) {
                    saveResult.path = file2.getPath();
                    saveResult.result = ImageSaver9orLess.this.write(saveParam2.bmp, saveResult.path);
                    return saveResult;
                }
                for (int i = 2; i < Integer.MAX_VALUE; i++) {
                    String str3 = str2 + "(" + i + ").png";
                    if (!new File(str3).exists()) {
                        saveResult.path = str3;
                        saveResult.result = ImageSaver9orLess.this.write(saveParam2.bmp, saveResult.path);
                        return saveResult;
                    }
                }
                saveResult.result = false;
                return saveResult;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(SaveResult saveResult) {
                if (saveResult.result) {
                    FileUtils.registToAndroidDB(ImageSaver9orLess.this._context, saveResult.path);
                    ImageSaver9orLess.this.showSaveSucceed(saveResult.path);
                    ImageSaver9orLess.this._saveFinishCallback.callbackSaveSucceed();
                    return;
                }
                ImageSaver9orLess.this.showSaveFailed();
                ImageSaver9orLess.this._saveFinishCallback.callbackSaveFail();
            }
        }.execute(saveParam);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean write(Bitmap bitmap, String str) {
        return ImgFileUtils.write(bitmap, str);
    }
}
