package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hengtiansoft.tijianba.util.BitmapCompress;
import com.juanliuinformation.lvningmeng.R;

public class GridAdapter extends BaseAdapter {
  private ArrayList<String> arrayList;
  private ArrayList<Integer> idList;
  private LayoutInflater inflater;
  private WeakHashMap<String, Bitmap> weakHashMap;
  private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
  private Context context;
  private HashMap<String, BitmapCompress> bitmapHashMap;
  private int width;
  private static GridAdapterListener listener;
  public GridAdapter(ArrayList<String> arrayList, Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.arrayList = arrayList;
    weakHashMap = new WeakHashMap<String, Bitmap>();
    bitmapHashMap = new HashMap<String, BitmapCompress>();
    idList = new ArrayList<Integer>();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    width = wm.getDefaultDisplay().getWidth() / 3;
  }

  public void setListener(GridAdapterListener listener) {
    this.listener = listener;
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
        arrayList.remove(position);
//        notifyDataSetChanged();
        listener.onGridAdapterListener(arrayList);
      }
    });
    String id = arrayList.get(position);
    if (id.equals("add")) {
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      holder.viewImg.setLayoutParams(linearParams);
      holder.viewImg.setImageResource(R.drawable.ic_add_reportphoto);
      holder.deleteImg.setVisibility(View.GONE);
    } else {
      RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.viewImg.getLayoutParams();
      linearParams.height = width;
      linearParams.width = width;
      holder.viewImg.setLayoutParams(linearParams);
      Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 0);
      if (weakHashMap.get(id) == null ) {
        BitmapCompress bitmapCompress = new BitmapCompress(context, holder.viewImg, weakHashMap, bitmapHashMap,
            bitmapList, 2);
        bitmapCompress.execute(id);
        bitmapHashMap.put(id, bitmapCompress);
      } else {
        bitmap = weakHashMap.get(id);
            if(bitmap!=null)
          holder.viewImg.setBackground(new BitmapDrawable(context.getResources(), bitmap));
      }
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

  public interface GridAdapterListener {
    public void onGridAdapterListener(ArrayList<String> arrayList);
  }

}
