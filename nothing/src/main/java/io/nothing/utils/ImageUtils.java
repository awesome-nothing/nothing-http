package io.nothing.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class ImageUtils {


  public static byte[] bitmap2Bytes(Bitmap bm) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
    return baos.toByteArray();
  }

  public static String bitmaptoString(Bitmap bitmap) {
    //将Bitmap转换成字符串
    String string = null;
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
    byte[] bytes = bStream.toByteArray();
    string = Base64.encodeToString(bytes, Base64.DEFAULT);
    return string;
  }

  public static Bitmap getBitmap(String path) {
    return BitmapFactory.decodeFile(path);//此时返回bm为空
  }

  public static String getMimeTypeOfFile(String pathName) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(pathName, opt);
    return opt.outMimeType;
  }


  private static int calculateInSampleSize(BitmapFactory.Options options,
                                           int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 2;

    if (height > reqHeight || width > reqWidth) {

      // Calculate ratios of height and width to requested height and
      // width
      final int heightRatio = Math.round((float) height
              / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);

      // Choose the smallest ratio as inSampleSize value, this will
      // guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
    }

    return inSampleSize;
  }


  public static Bitmap getImage(String filePath) {

    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filePath, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, 720, 1280);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    Bitmap bm = BitmapFactory.decodeFile(filePath, options);
    if (bm == null) {
      return null;
    }
    int degree = getBitmapDegree(filePath);
    bm = rotateBitmapByDegree(bm, degree);
    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
      Log.d("ImageUtils", "baos size:" + baos.toByteArray().length);
    } finally {
      try {
        if (baos != null)
          baos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return bm;
  }


  public static int[] getImageSize(String path) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);
    int imageHeight = options.outHeight;
    int imageWidth = options.outWidth;
    return new int[]{imageWidth, imageHeight};
  }


  public static String getExtension(String url) {
    return url.substring(url.lastIndexOf(".") + 1, url.length());
  }

  /**
   * 读取图片的旋转的角度
   *
   * @param path 图片绝对路径
   * @return 图片的旋转角度
   */
  public static int getBitmapDegree(String path) {
    int degree = 0;
    try {
      // 从指定路径下读取图片，并获取其EXIF信息
      ExifInterface exifInterface = new ExifInterface(path);
      // 获取图片的旋转信息
      int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
              ExifInterface.ORIENTATION_NORMAL);
      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return degree;
  }

  /**
   * 将图片按照某个角度进行旋转
   *
   * @param bm     需要旋转的图片
   * @param degree 旋转角度
   * @return 旋转后的图片
   */
  public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
    Bitmap returnBm = null;

    // 根据旋转角度，生成旋转矩阵
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    try {
      // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
      returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    } catch (OutOfMemoryError e) {
    }
    if (returnBm == null) {
      returnBm = bm;
    }
    if (bm != returnBm) {
      bm.recycle();
    }
    return returnBm;
  }


  public static Bitmap getThumbnailBitmap(String path, int sampleSize) {
    FileInputStream fis = null;
    Bitmap bm = null;
    try {
      fis = new FileInputStream(path);
      BufferedInputStream bis = new BufferedInputStream(fis);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int hasRead = 0;
      byte[] buffer = new byte[1024 * 2];
      while ((hasRead = bis.read(buffer)) > 0) {
        //读出多少数据，向输出流中写入多少
        out.write(buffer);
        out.flush();
      }
      out.close();
      fis.close();
      bis.close();
      byte[] data = out.toByteArray();
      //长宽减半
      BitmapFactory.Options opts = new BitmapFactory.Options();
      opts.inSampleSize = sampleSize;
      bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bm;

  }

  public static String saveBitmapToPath(Bitmap bitmap, String imagePath) {
    return saveBitmapToPath(bitmap, imagePath, 100);
  }

  public static String saveBitmapToPath(Bitmap bitmap, String imagePath, int quality) {
    File destFile = new File(imagePath);
    Bitmap newbitmap = null;
    OutputStream os = null;
    try {
      os = new FileOutputStream(destFile);
      if (bitmap == null) {
        return "";
      }
      if (!bitmap.isRecycled()) {
        newbitmap = bitmap.copy(bitmap.getConfig(), false);
      } else {
        throw new RuntimeException("OOM");
      }
      newbitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
      os.flush();
      os.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      destFile = null;
      if (bitmap != null && !bitmap.isRecycled()) {
        bitmap.recycle();
        bitmap = null;
      }
      if (newbitmap != null && !newbitmap.isRecycled()) {
        newbitmap.recycle();
        newbitmap = null;
      }
      System.gc();
    }
    return imagePath;
  }





  public static Bitmap getResizedBitmap(String filePath, int newHeight, int newWidth) {
    Bitmap bm = getBitmap(filePath);
    int width = bm.getWidth();
    int height = bm.getHeight();
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // CREATE A MATRIX FOR THE MANIPULATION
    Matrix matrix = new Matrix();
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight);

    // "RECREATE" THE NEW BITMAP
    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    if (!bm.isRecycled()) {
      bm.recycle();
      bm = null;
      System.gc();
    }
    return resizedBitmap;
  }


  /*
     * 得到图片字节流 数组大小
     * */
  public static byte[] readStream(InputStream inStream) throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = inStream.read(buffer)) != -1) {
      outStream.write(buffer, 0, len);
    }
    outStream.close();
    inStream.close();
    return outStream.toByteArray();
  }


  public static Bitmap getClipBitmap(Context context, Bitmap bitmap, int maskResId) {
    Bitmap original = bitmap;
    Bitmap mask = BitmapFactory.decodeResource(context.getResources(),
            maskResId);
    original = Bitmap.createScaledBitmap(original, mask.getWidth(),
            mask.getHeight(), true);

    Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(),
            Bitmap.Config.ARGB_8888);
    Canvas mCanvas = new Canvas(result);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    mCanvas.drawBitmap(original, 0, 0, null);
    mCanvas.drawBitmap(mask, 0, 0, paint);
    paint.setXfermode(null);
    return result;
  }

  public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }

    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
  }



}
