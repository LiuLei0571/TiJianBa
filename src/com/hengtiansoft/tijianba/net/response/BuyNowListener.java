package com.hengtiansoft.tijianba.net.response;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.BuyNowListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 26, 2014 7:04:06 PM
 */
public interface BuyNowListener {
  public void onSuccess(BuyDetailResult buyResult);

  public void onError(String errorCode, String errorMessage);
}
