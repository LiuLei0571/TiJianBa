package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.hengtiansoft.tijianba.util.ImageAdd;
import com.juanliuinformation.lvningmeng.R;

public class AddAdapter extends BaseAdapter {
  private LayoutInflater inflater;
  private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
  private Context context;
  private int width;
  private static AddAdapterListener listener;
  public AddAdapter(ArrayList<Bitmap> bitmapList, Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.bitmapList=bitmapList;
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics  dm = new DisplayMetrics();      
    wm.getDefaultDisplay().getMetrics(dm);      
    int screenWidth = dm.widthPixels; 
    width=screenWidth/3;
  }

  public void setListener(AddAdapterListener listener) {
    this.listener = listener;
  }

  @Override
  public int getCount() {
    return bitmapList.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @SuppressLint("NewApi")
  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = inflater.inflate(R.layout.layout_grid_view, null);
      holder.viewImg = (ImageView) convertView.findViewById(R.id.image_view);
      holder.deleteImg = (ImageView) convertView.findViewById(R.id.image_delete);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.deleteImg.setVisibility(View.VISIBLE);
    holder.deleteImg.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onAddAdapterListener(position);
      }
    });
   
    if (bitmapList.get(position)==ImageAdd.bitmap) {
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      holder.viewImg.setLayoutParams(linearParams);
      holder.viewImg.setImageBitmap(bitmapList.get(position));
      holder.deleteImg.setVisibility(View.GONE);
    } else {
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      linearParams.width = width;
      holder.viewImg.setLayoutParams(linearParams);
      holder.viewImg.setBackground(new BitmapDrawable(bitmapList.get(position)));
    }
    return convertView;
  }

  class ViewHolder {
    ImageView viewImg;
    ImageView deleteImg;
  }

  public ArrayList<Bitmap> getList() {
    return bitmapList;
  }

  public interface AddAdapterListener {
    public void onAddAdapterListener(int position);
  }

}
