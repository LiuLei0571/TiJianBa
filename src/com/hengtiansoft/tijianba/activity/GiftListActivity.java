package com.hengtiansoft.tijianba.activity;

import com.juanliuinformation.lvningmeng.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class GiftListActivity extends BaseActivity implements OnClickListener {
	private ListView mLvGift;
	private ImageButton finish;
	private Button giftReceived;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gift_list);
		
		initView();
		
		finish.setOnClickListener(this);
		giftReceived.setOnClickListener(this);
		
//		initDate();
	}

	public void initView() {
//		mLvGift=(ListView) this.findViewById(R.id.lv_gift);
	    finish = (ImageButton) findViewById(R.id.gift_list_finish);
	    giftReceived = (Button) findViewById(R.id.gift_list_received);
	}

//	public void initDate() {
//		GiftListAdapter adapter=new GiftListAdapter(this,null );
//		mLvGift.setAdapter(adapter);
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.gift_list_finish:
			finish();
			break;
		case  R.id.gift_list_received:
			Intent intent = new Intent(GiftListActivity.this,GiftDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
