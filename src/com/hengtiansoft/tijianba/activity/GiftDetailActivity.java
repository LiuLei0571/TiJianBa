package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.List;

import com.hengtiansoft.tijianba.net.response.StepGiftDetailData;
import com.hengtiansoft.tijianba.net.response.StepGiftDetailListener;
import com.hengtiansoft.tijianba.widget.SlideshowView;
import com.juanliuinformation.lvningmeng.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class GiftDetailActivity extends BaseActivity implements OnClickListener {
	private SlideshowView slideshowview;
	private ImageButton sure;
	private TextView tv_detail_name,tv_detail_item,tv_detail_price,tv_activitytime,tv_lottery_time,tv_rule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gift_detail);
		initView();
		initDate();

		sure.setOnClickListener(this);
	}

	private void initDate() {
		remoteDataManager.stepgiftdetailListener = new StepGiftDetailListener() {

			@Override
			public void onSuccess(final StepGiftDetailData data) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						tv_detail_name.setText(data.getGiftname());
						tv_detail_item.setText(data.getDetail());
						tv_detail_price.setText("￥"+data.getPrice());
						tv_activitytime.setText(data.getActivitytime());
						tv_lottery_time.setText(data.getLotterytime());
						tv_rule.setText(data.getRules());
					}

				});
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				// TODO Auto-generated method stub

			}
		};

		remoteDataManager.StepGiftDetail(1);

	}

	private void initView() {
		// TODO Auto-generated method stub
		List<Integer> imgList=new ArrayList<Integer>();
		   imgList.add(R.drawable.one);
		   imgList.add(R.drawable.two);
		   imgList.add(R.drawable.three);
		   
		    slideshowview=(SlideshowView) findViewById(R.id.home_slideshow);
		    slideshowview.setImageUris(imgList);
	    
//		sure = (ImageButton) findViewById(R.id.gift_detail_sure);
//		tv_detail_name=(TextView) findViewById(R.id.tv_detail_name);
//		tv_detail_item=(TextView) findViewById(R.id.tv_detail_item);
//		tv_detail_price=(TextView) findViewById(R.id.tv_detail_item_price);
//		tv_activitytime=(TextView) findViewById(R.id.tv_activitytime);
//		tv_lottery_time=(TextView) findViewById(R.id.tv_lottery_time);
//		tv_rule=(TextView) findViewById(R.id.tv_rule);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.gift_detail_sure:
			Intent i = new Intent(GiftDetailActivity.this, GiftReceived.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}

}
