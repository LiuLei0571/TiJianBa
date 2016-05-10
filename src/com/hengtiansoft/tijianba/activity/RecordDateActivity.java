package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.hengtiansoft.tijianba.adapter.RecordItemAdapter;
import com.hengtiansoft.tijianba.adapter.RecordItemAdapter.RecordItemAdapterListener;
import com.hengtiansoft.tijianba.net.response.BookData;
import com.hengtiansoft.tijianba.net.response.BookDataList;
import com.hengtiansoft.tijianba.net.response.BookDataListener;
import com.juanliuinformation.lvningmeng.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecordDateActivity extends BaseActivity {
  private TextView mTvItem;
  private SwipeListView sList;
  private Intent intent;
  private String mHealthItem[] = { "身高", "体重", "收缩压", "舒张压", "心率/脉搏", "体温", "大便频率", "尿量", "空腹血糖", "步行量", "饮水量",
      "蔬菜摄入率", "水果摄入率", "奶类及奶制品摄入量", "豆制品摄入量" };
  private int id;
  private String mUnit;
  private String mMax, mMin;
  private ArrayList<BookData> mDataList = new ArrayList<BookData>();
  private RecordItemAdapter mRecordAdapter;
private int valueType=0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record_date);
    intent = getIntent();
    id = intent.getIntExtra("id", 0);
    valueType = intent.getIntExtra("valueType", 0);
    mUnit = intent.getStringExtra("unit");
    mMax = intent.getStringExtra("mMax");
    mMin = intent.getStringExtra("mMin");
    initView();

    setItemClick();
  }

  private void initView() {
    mTvItem = (TextView) findViewById(R.id.tv_item);
    mTvItem.setText(mHealthItem[id - 1]);
    sList = (SwipeListView) findViewById(R.id.lv_record_date);
  }

  @Override
  protected void onResume() {
    super.onResume();
    sList.closeOpenedItems();
    getBookDataList();
  }

  private void getBookDataList() {

    remoteDataManager.bookDataListener = new BookDataListener() {

      @Override
      public void onSuccess(BookDataList bookDataList) {
        mDataList = bookDataList.getDataList();
        RecordDateActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mRecordAdapter = new RecordItemAdapter(RecordDateActivity.this, mDataList, remoteDataManager,
                validateInternet(), mUnit);
            mRecordAdapter.setListener(new RecordItemAdapterListener() {

              @Override
              public void onRecordItemAdapterListener() {
                sList.closeOpenedItems();
              }
            });
            sList.setAdapter(mRecordAdapter);
            dismissProgressDialog();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        dismissProgressDialog();
      }
    };
    if (validateInternet()) {
      showProgressDialog("数据", "加载中...");
      remoteDataManager.getBookData(id);
    }
  }

  private void setItemClick() {

    sList.setSwipeListViewListener(new BaseSwipeListViewListener() {
      @Override
      public void onClickFrontView(int position) {
        super.onClickFrontView(position);
        intent.setClass(RecordDateActivity.this, AddDataActivity.class);
        intent.putExtra("bookData", mDataList.get(position));
        intent.putExtra("unit", mUnit);
        intent.putExtra("valueType", valueType);
        intent.putExtra("state", 1);
        intent.putExtra("mMax", mMax);
        intent.putExtra("mMin", mMin);
        startActivity(intent);
      }
    });
  }

}