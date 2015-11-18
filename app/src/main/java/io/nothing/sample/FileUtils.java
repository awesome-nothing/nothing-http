package io.nothing.sample;
import android.content.Context;


import java.io.File;
import java.io.InputStream;
/**
 * Created by sanvi on 9/17/15.
 */
/**
 * Created by sanvi on 6/15/15.
 */
public class FileUtils {
//    // 读取raw文件
//    public static String rawRead(Context context,int rawId){
//        String ret = "";
//        try {
//            InputStream is = context.getResources().openRawResource(rawId);
//            int len = is.available();
//            byte []buffer = new byte[len];
//            is.read(buffer);
//            ret = EncodingUtils.getString(buffer, "utf-8");
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static synchronized boolean mkdir(File dir,boolean p) {
        if(dir.exists()) {
            if(dir.isDirectory()) {
                return p;
            } else {
                return false;
            }
        } else {
            return dir.mkdir();
        }
    }
}
