package cc.springwind.tianziyihao.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HeFan on 2016/7/27 0027.
 */
public class AssetsUtil {
    public static void initDB(String dbName, Context context) {
        File files = context.getFilesDir();
        File file = new File(files, dbName);
        if (file.exists()) {
            return;
        }
        InputStream mInputStream = null;
        FileOutputStream mFileOutputStream = null;
        try {
            mInputStream = context.getAssets().open(dbName);
            //3,将读取的内容写入到指定文件夹的文件中去
            mFileOutputStream = new FileOutputStream(file);
            //4,每次的读取内容大小
            byte[] bs = new byte[1024];
            int temp;
            while ((temp = mInputStream.read(bs)) != -1) {
                mFileOutputStream.write(bs, 0, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mInputStream != null && mFileOutputStream != null) {
                try {
                    mInputStream.close();
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
