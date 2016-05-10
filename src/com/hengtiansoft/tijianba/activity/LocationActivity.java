package com.hengtiansoft.tijianba.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.LocationActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 12, 2015 4:59:53 PM
 */
public class LocationActivity extends BaseActivity implements
		OnGetPoiSearchResultListener {
	MapView mMapView = null;
	String address, region;
	private PoiSearch mPoiSearch = null;
	private BaiduMap mBaiduMap = null;
	private SDKReceiver mReceiver;

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(LocationActivity.this,
						"key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置",
						Toast.LENGTH_LONG).show();
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(LocationActivity.this, "网络出错", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_location);
		address = getIntent().getStringExtra("address");
		region = getIntent().getStringExtra("region");
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		// 初始化搜索模块，注册事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		SearchButtonProcess();
	}

	/**
	 * 发起搜索
	 * 
	 * @param v
	 */
	public void SearchButtonProcess() {
		mPoiSearch.searchInCity((new PoiCitySearchOption()).city(region)
				.keyword(address));
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mPoiSearch.destroy();
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}


	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(LocationActivity.this, "未找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}

	}
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
						.poiUid(poi.uid));
			// }
			return true;
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(LocationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(LocationActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}


}
