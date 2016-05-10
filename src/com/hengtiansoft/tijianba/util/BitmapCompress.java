package com.hengtiansoft.tijianba.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.ImageView;

public class BitmapCompress extends AsyncTask<String, Integer, Boolean> {

  private ImageView imageView;
  private Context context;
  private WeakHashMap<String, Bitmap> weakHashMap;
  private ArrayList<Bitmap> bitmapList;
  private HashMap<String, BitmapCompress> bitmapHashMap;
  private Bitmap bitmap;
  private int type;

  public BitmapCompress(Context context, ImageView imageView, WeakHashMap<String, Bitmap> weakHashMap,
      HashMap<String, BitmapCompress> bitmapHashMap, ArrayList<Bitmap> bitmapList, int type) {
    this.imageView = imageView;
    this.context = context;
    this.weakHashMap = weakHashMap;
    this.bitmapHashMap = bitmapHashMap;
    this.bitmapList = bitmapList;
    this.type = type;
  }

  @Override
  protected Boolean doInBackground(String... params) {
    String id = params[0];
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inDither = false;
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    options.inSampleSize = 3;
      try{
      Integer mId=Integer.parseInt(id);
      bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), mId,
          Images.Thumbnails.MINI_KIND, options);
      }catch(NumberFormatException e){
        bitmap = BitmapFactory.decodeFile(id, options);
      }
    weakHashMap.put(id, bitmap);
    bitmapHashMap.remove(id);
    return true;
  }

  @SuppressLint("NewApi")
  @Override
  protected void onPostExecute(Boolean result) {
    super.onPostExecute(result);
    if (bitmap == null) {
    } else {
      if (bitmapList != null) {
        bitmapList.add(bitmap);
      }
      imageView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
      imageView.requestLayout();
    }
  }
}
