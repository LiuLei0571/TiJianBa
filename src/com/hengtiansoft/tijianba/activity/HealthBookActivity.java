package com.hengtiansoft.tijianba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.hengtiansoft.tijianba.adapter.BookAdapter;
import com.juanliuinformation.lvningmeng.R;

public class HealthBookActivity extends BaseActivity {
    
  private ExpandableListView mListBook;
  private BookAdapter bookAdapter;
  private String[][] mItem={{"身高","体重"},{"收缩压","舒张压","心率/脉搏","体温","空腹血糖"},{"大便频率","尿量"},{"步行里程"},
      {"饮水量","蔬菜摄入量","水果摄入量","奶类及奶制品摄入量","豆制品摄入量"}};
  private boolean[] isCheck={false,false,false,false,false};
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_health_book);
    initView();
  }
  private void initView(){
    mListBook=(ExpandableListView) findViewById(R.id.lv_health_book);
    bookAdapter=new BookAdapter(this,mItem,isCheck);
    mListBook.setAdapter(bookAdapter);
    mListBook.setOnChildClickListener(new OnChildClickListener() {
      
      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(HealthBookActivity.this,CubicLineChartActivity.class);
        intent.putExtra("id", getId(mItem[groupPosition][childPosition]) );
        intent.putExtra("name", mItem[groupPosition][childPosition]);
        startActivity(intent);
        return false;
      }
    });
    mListBook.setOnGroupClickListener(new OnGroupClickListener() {
      
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
       if( isCheck[groupPosition]){
         isCheck[groupPosition]=false;
       }
       else{
         isCheck[groupPosition]=true;
       }
        bookAdapter.notifyDataSetChanged();
        return false;
      }
    });
  }

private Integer getId(String str){
  int id = 0;
  if(str.equals("身高")){
    id=1;
  }
  else if(str.equals("体重")){
    id=2;
  }
  else if(str.equals("收缩压")){
    id=3;
  }
  else if(str.equals("舒张压")){
    id=4;
  }
  else if(str.equals("心率/脉搏")){
    id=5;
  }
  
  else if(str.equals("体温")){
    id=6;
  }else if(str.equals("空腹血糖")){
    id=9;
  }
  else if(str.equals("大便频率")){
    id=7;
  }
  else if(str.equals("尿量")){
    id=8;
  }
  else if(str.equals("步行")){
    id=10;
  }
  else if(str.equals("饮水量")){
    id=11;
  }
  else if(str.equals("蔬菜摄入量")){
    id=12;
  }
  else if(str.equals("水果摄入量")){
    id=13;
  }
  else if(str.equals("奶类及奶制品摄入量")){
    id=14;
  }
  else if(str.equals("豆制品摄入量")){
    id=15;
  }
  return id;
}
}