package com.hengtiansoft.tijianba.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.juanliuinformation.lvningmeng.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

  private IWXAPI api;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    api = WXAPIFactory.createWXAPI(this, RemoteDataManager.APP_ID, false);
    api.handleIntent(getIntent(), this);

  }

  @Override
  public void onReq(BaseReq arg0) {

  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    api.handleIntent(intent, this);
  }

  @Override
  public void onResp(BaseResp resp) {
    int result = 0;

    switch (resp.errCode) {
      case BaseResp.ErrCode.ERR_OK:
        result = R.string.errcode_success;
        break;
      case BaseResp.ErrCode.ERR_USER_CANCEL:
        result = R.string.errcode_cancel;
        break;
      case BaseResp.ErrCode.ERR_AUTH_DENIED:
        result = R.string.errcode_deny;
        break;
      default:
        result = R.string.errcode_unknown;
        break;
    }
    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
  }
}