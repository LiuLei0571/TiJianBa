package com.hengtiansoft.tijianba.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {


//  public static Bitmap toRoundBitmap(Bitmap bitmap) {
//    int width = bitmap.getWidth();
//    int height = bitmap.getHeight();
//    float roundPx;
//    float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
//    if (width <= height) {
//      roundPx = width / 2;
//      top = 0;
//      left = 0;
//      bottom = width;
//      right = width;
//      height = width;
//      dst_left = 0;
//      dst_top = 0;
//      dst_right = width;
//      dst_bottom = width;
//    } else {
//      roundPx = height / 2;
//      float clip = (width - height) / 2;
//      left = clip;
//      right = width - clip;
//      top = 0;
//      bottom = height;
//      width = height;
//      dst_left = 0;
//      dst_top = 0;
//      dst_right = height;
//      dst_bottom = height;
//    }
//    Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//    Canvas canvas = new Canvas(output);
//    final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
//    final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
//    final RectF rectF = new RectF(dst);
//    paint.setAntiAlias(true);
//    canvas.drawARGB(0, 0, 0, 0);
//    paint.setStrokeWidth(STROKE_WIDTH);
//    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//    canvas.drawBitmap(bitmap, src, dst, paint);
//    paint.reset();
//    paint.setColor(Color.parseColor("#a3cf62"));
//    paint.setStyle(Paint.Style.STROKE);
//    paint.setStrokeWidth(STROKE_WIDTH);
//    paint.setAntiAlias(true);
//    canvas.drawCircle(width / 2, width / 2, width / 2 - STROKE_WIDTH / 2, paint);
//    return output;
//  }

  public static byte[] getBitmapByte(Bitmap bitmap) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    try {
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }

  public static Bitmap getBitmapFromByte(byte[] temp) {
    if (temp != null) {
      Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
      return bitmap;
    } else {
      return null;
    }
  }

}
