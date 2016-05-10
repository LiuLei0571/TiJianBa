package com.hengtiansoft.tijianba.adapter;


import com.juanliuinformation.lvningmeng.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookAdapter extends BaseExpandableListAdapter {
private String[] mGroup={"BMI","生理指数","代谢指数","运动指数","饮食营养指数"};
private String[][] mItem;
  private Context mContext;
  private int[] mGroupIcon={R.drawable.ic_book_one ,R.drawable.ic_book_four,R.drawable.ic_book_nine,R.drawable.ic_book_twelve,R.drawable.ic_book_thriteen};
  private int[] mGroupColor={R.color.book_txet_one ,R.color.book_txet_two,R.color.book_txet_three,R.color.book_txet_four,R.color.book_txet_five};
  private int[][] mItemIcon={
      {R.drawable.ic_book_two ,R.drawable.ic_book_three},
      {R.drawable.ic_book_spb,R.drawable.ic_book_dbp,R.drawable.ic_book_six,R.drawable.ic_book_seven,R.drawable.ic_book_eight},
      {R.drawable.ic_book_ten,R.drawable.ic_book_eleven},{R.drawable.ic_book_twelve},{R.drawable.ic_book_water,R.drawable.ic_book_fourteen,
      R.drawable.ic_book_fifteen,R.drawable.ic_book_sixteen,R.drawable.ic_book_seventeen}};
  
  private boolean[] isCheck;
  public BookAdapter(Context mContext,String[][] mItem,boolean[] isCheck){
    this.mContext=mContext;
    this.mItem=mItem;
    this.isCheck=isCheck;
  }
  
  @Override
  public int getGroupCount() {
    return mGroup.length;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return mItem[groupPosition].length;
  }



  @Override
  public Object getGroup(int groupPosition) {
    return mGroup[groupPosition];
  }



  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return mItem[groupPosition][childPosition];
  }



  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }



  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }



  @Override
  public boolean hasStableIds() {
    return false;
  }



  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    convertView=LinearLayout.inflate(mContext, R.layout.layout_book_child, null);
    TextView tv=(TextView) convertView.findViewById(R.id.tv_title);
    tv.setText(mGroup[groupPosition]);
    tv.setTextColor(mContext.getResources().getColor(mGroupColor[groupPosition]));
    if(groupPosition==0){
      convertView.findViewById(R.id.seperate).setVisibility(View.GONE);
    }
    ImageView image=(ImageView) convertView.findViewById(R.id.img);
    image.setImageResource(mGroupIcon[groupPosition]);
    ImageView arrow=(ImageView) convertView.findViewById(R.id.ic_arrow);
    if(isCheck[groupPosition]){
      arrow.setImageResource(R.drawable.ic_right);
    }
    return convertView;
  }


  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    convertView=LinearLayout.inflate(mContext, R.layout.layout_book_parent, null);
    TextView tv=(TextView) convertView.findViewById(R.id.tv_title);
    tv.setText(mItem[groupPosition][childPosition]);
    ImageView image=(ImageView) convertView.findViewById(R.id.img);
    image.setImageResource(mItemIcon[groupPosition][childPosition]);
    return convertView;
  }



  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
  
  
  
  
}
