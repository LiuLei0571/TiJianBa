package com.hengtiansoft.tijianba.activity;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class WebViewActivity extends BaseActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    WebView webview = new WebView(this);
    setContentView(webview);
    Bundle bundle = this.getIntent().getExtras();
    String data = bundle.getString("introduce");
    webview.loadDataWithBaseURL("", data, "text/html", "utf-8", "");
  }

}
