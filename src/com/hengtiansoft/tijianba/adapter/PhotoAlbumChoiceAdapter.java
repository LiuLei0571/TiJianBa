package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import com.hengtiansoft.tijianba.util.BitmapCompress;
import com.hengtiansoft.tijianba.util.UniversalImageLoadTool;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PhotoAlbumChoiceAdapter extends BaseAdapter {

  private ArrayList<HashMap<String, String>> arrayList;
  private LayoutInflater inflater;
  private WeakHashMap<String, Bitmap> weakHashMap;
  private Context context;
  private HashMap<String, BitmapCompress> bitmapHashMap;
  private int width;

  public PhotoAlbumChoiceAdapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.arrayList = arrayList;
    weakHashMap = new WeakHashMap<String, Bitmap>();
    bitmapHashMap = new HashMap<String, BitmapCompress>();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics  dm = new DisplayMetrics();      
    wm.getDefaultDisplay().getMetrics(dm);      
    int screenWidth = dm.widthPixels; 
    width=screenWidth/3;
  }

  @Override
  public int getCount() {
    return arrayList.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @SuppressLint("NewApi")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
   
    HashMap<String, String> hashMap = arrayList.get(position);
    ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = inflater.inflate(R.layout.layout_choice_item, null);
      holder.viewImg = (ImageView) convertView.findViewById(R.id.choice_item_photo);
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      linearParams.width = width;
      holder.viewImg.setLayoutParams(linearParams);
      holder.checkImg = (ImageView) convertView.findViewById(R.id.choice_item_sure);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    String id = "file://"+hashMap.get("path");
    UniversalImageLoadTool.disPlay(id, new ImageViewAware(holder.viewImg), R.drawable.hot4);
    if (hashMap.get("check") != null && hashMap.get("check").equals("true")) {
      holder.checkImg.setImageResource(R.drawable.ic_cart_selected);
    } else {
      holder.checkImg.setImageResource(R.drawable.ic_cart_unselected);}
    return convertView;
  }

  class ViewHolder {
    ImageView viewImg;
    ImageView checkImg;
  }
}
