package com.hengtiansoft.tijianba.activity;

import com.juanliuinformation.lvningmeng.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class GiftReceivedDialog extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gift_list);
	}
}
