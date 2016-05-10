package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.hengtiansoft.tijianba.adapter.PhotoAlbumChoiceAdapter;
import com.juanliuinformation.lvningmeng.R;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoAlbumChoiceActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
  private ArrayList<HashMap<String, String>> arrayList;
  private Map<Integer, String> localPicMap;
  private GridView gridView;
  private PhotoAlbumChoiceAdapter adapter;
  private TextView mTvAdd;
  private ArrayList<String> idList = new ArrayList<String>();
  private Intent intent;
  private int num;
  private int leastNum = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_photo_album_choice);
    intent = getIntent();
    num = intent.getIntExtra("num", 0);
    initView();
  }

  private void initView() {
    mTvAdd = (TextView) findViewById(R.id.tv_add);
    mTvAdd.setText("完成" + leastNum + "/" + num);
    mTvAdd.setOnClickListener(this);
    arrayList = new ArrayList<HashMap<String, String>>();
    gridView = (GridView) findViewById(R.id.gridView);
    adapter = new PhotoAlbumChoiceAdapter(arrayList, this);
    gridView.setAdapter(adapter);
    gridView.setOnItemClickListener(this);
    getColumnData();
    adapter.notifyDataSetChanged();
  }

  private void getColumnData() {
    localPicMap = new HashMap<Integer, String>();
    ContentResolver testcr = getContentResolver();
    String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
    Cursor cur = testcr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null,"_ID desc" );
    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
      int _id;
      int image_id;
      String image_path;
      int _idColumn = cur.getColumnIndex(Thumbnails._ID);
      int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
      _id = cur.getInt(_idColumn);
      image_path = cur.getString(dataColumn);
      localPicMap.put(cur.getPosition(), image_path);
      HashMap<String, String> hash = new HashMap<String, String>();
      hash.put("id", String.valueOf(_id));
      hash.put("path", image_path);
      arrayList.add(hash);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    HashMap<String, String> hashMap = arrayList.get(position);
    if (leastNum < num) {
      if (hashMap.get("check") != null && hashMap.get("check").equals("true")) {
        hashMap.put("check", "false");
        leastNum -= 1;
        mTvAdd.setText("完成" + leastNum + "/" + num);
      } else {
        hashMap.put("check", "true");
        leastNum += 1;
        mTvAdd.setText("完成" + leastNum + "/" + num);
      }
      arrayList.set(position, hashMap);
      adapter.notifyDataSetChanged();
    } else if (leastNum == num) {
      if (hashMap.get("check") != null && hashMap.get("check").equals("true")) {
        hashMap.put("check", "false");
        leastNum -= 1;
        mTvAdd.setText("完成" + leastNum + "/" + num);
      }
      else {
        Toast.makeText(PhotoAlbumChoiceActivity.this, "图片已满", 0).show();
      }
      arrayList.set(position, hashMap);
      adapter.notifyDataSetChanged();
    } 
  }

  @Override
  public void onClick(View v) {
    ArrayList<String> pathList = new ArrayList<String>();
    for (int i = 0; i < arrayList.size(); i++) {
      if (("true").equals(arrayList.get(i).get("check"))) {
        pathList.add(arrayList.get(i).get("path"));
        idList.add(arrayList.get(i).get("id"));
      }
    }
    Intent intent = new Intent();
    intent.putStringArrayListExtra("arrayList", pathList);
    intent.putStringArrayListExtra("idList", idList);
    this.setResult(RESULT_OK, intent);
    this.finish();
  }
}
