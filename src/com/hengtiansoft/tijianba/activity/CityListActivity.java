package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hengtiansoft.tijianba.db.dao.CityDao;
import com.hengtiansoft.tijianba.net.response.CityListListener;
import com.hengtiansoft.tijianba.net.response.CityResult.City;
import com.juanliuinformation.lvningmeng.R;

/**
 * com.hengtiansoft.tijianba.activity.CityListActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 21, 2014 3:17:36 PM
 */
public class CityListActivity extends BaseActivity {
  private SimpleAdapter mAdapter;
  private ListView mCityListView;
  private EditText mSearchEditText;
  private ArrayList<CityDao> cityDaosAll = new ArrayList<CityDao>();
  private ArrayList<Map<String, Object>> mCityData = new ArrayList<Map<String, Object>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_city_list);
    setView();
  }

  private void setView() {
    TextView location = (TextView) findViewById(R.id.tv_location);
    if (remoteDataManager.locatedCity != null && !"".equals(remoteDataManager.locatedCity))
      location.setText(remoteDataManager.locatedCity);
    mSearchEditText = (EditText) findViewById(R.id.searchView);
    mSearchEditText.setOnKeyListener(new OnKeyListener() {

      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
          InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
          }
          return true;
        }
        return false;
      }
    });
    findViewById(R.id.spinnerid).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        mSearchEditText.requestFocus();
      }
    });
    mCityListView = (ListView) findViewById(R.id.lv_city_list);
    mAdapter = new SimpleAdapter(this, mCityData, R.layout.city_item, new String[] { "name" },
        new int[] { R.id.tv_name });
    mCityListView.setAdapter(mAdapter);
    mCityListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String cityName = cityDaosAll.get(arg2).getName().toString();
        remoteDataManager.cityCode = cityDaosAll.get(arg2).getCode();
        remoteDataManager.locatedCity = cityDaosAll.get(arg2).getName();
        Intent intent = new Intent();
        intent.putExtra("city", cityName);
        setResult(100, intent);
        finish();
      }
    });
  }

  private void getCities(String refreshTime) {
    remoteDataManager.cityListListener = new CityListListener() {

      @Override
      public void onSuccess(ArrayList<City> cities, String refrshTime) {
        dismissProgressDialog();
        Editor editor = spSettting.edit();
        editor.putString("REFRESH_TIME", refrshTime);
        editor.commit();
        ArrayList<CityDao> cityDaos = new ArrayList<CityDao>();
        for (int i = 0; i < cities.size(); i++) {
          CityDao city = new CityDao();
          city.cloneContent(city, cities.get(i));
          cityDaos.add(city);
        }
        mCityDaoImpl.batchInsert(cityDaos);
        cityDaosAll.clear();
        cityDaosAll.addAll(mCityDaoImpl.queryForCity());
        mCityData.clear();
        for (int i = 0; i < cityDaosAll.size(); i++) {
          Map<String, Object> item = new HashMap<String, Object>();
          item.put("name", cityDaosAll.get(i).getName().toString());
          mCityData.add(item);
        }
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            mAdapter.notifyDataSetChanged();
          }
        });

      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog("城市", "加载中..");
      remoteDataManager.getCityList(refreshTime);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mCityData.clear();
    getCities(spSettting.getString("REFRESH_TIME", ""));
  }
}
