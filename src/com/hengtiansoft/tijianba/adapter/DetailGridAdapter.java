package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.ReportItem;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DetailGridAdapter extends BaseAdapter {
  private ArrayList<ReportItem> arrayList;
  private Context context;
  private DisplayImageOptions options;
  private int width;
  private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
  private String id;
  private static  DetailAdapterListener listener;
  public DetailGridAdapter(ArrayList<ReportItem> arrayList, Context context) {
    this.context = context;
    this.arrayList = arrayList;
    options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(false).considerExifParams(true)
        .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(false).displayer(new SimpleBitmapDisplayer())
        .build();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics  dm = new DisplayMetrics();      
    wm.getDefaultDisplay().getMetrics(dm);      
    int screenWidth = dm.widthPixels; 
    width=screenWidth/3;
  }

  public void setListener(DetailAdapterListener listener) {
    this.listener = listener;
  }
  
  @Override
  public int getCount() {
    return arrayList.size();
  }

  @Override
  public Object getItem(int position) {
    return arrayList.get(position);
  }

  @Override
  public long getItemId(int position) {
 return position;
  }

  @SuppressLint("NewApi")
  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = LinearLayout.inflate(context, R.layout.layout_grid_view, null);
      holder.viewImg = (ImageView) convertView.findViewById(R.id.image_view);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    final ReportItem item = arrayList.get(position);
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      linearParams.width = width;
      holder.viewImg.setLayoutParams(linearParams);
      holder.viewImg.setBackgroundResource(R.drawable.hot4);
      id = item.getSmallUrl();
        ImageLoader.getInstance().loadImage(RemoteDataManager.SERVICE + id, options, new ImageLoadingListener() {
          @Override
          public void onLoadingStarted(String arg0, View arg1) {
            
          }
          @Override
          public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            listener.onFinish(false);
          }
          @SuppressLint("NewApi")
          @Override
          public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
            holder.viewImg.setBackground(new BitmapDrawable(context.getResources(), arg2));
              ImageAdd.detailMap.put(position , arg2);
          }
          @Override
          public void onLoadingCancelled(String arg0, View arg1) {
          }
        });
        if(ImageAdd.detailMap.size()==arrayList.size()){
         listener.onFinish(true);
        }
    return convertView;
  }

  public ArrayList<Bitmap> getBitmap() {
    return bitmapList;
  }
  class ViewHolder {
    ImageView viewImg;
    ImageView deleteImg;
  }

  
  public interface DetailAdapterListener {
    public void onFinish(boolean success);
  }
}
