package com.hengtiansoft.tijianba.activity;

import com.juanliuinformation.lvningmeng.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GiftReceived extends Activity{
//	 private Button mButton;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.acitivity_gift_received_dialog);
//	    mButton = (Button) findViewById(R.id.duihuan);
//	    mButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent();
//				startActivity(i);
//				
//			}
//		});

	  }

}
