package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CartListListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 23, 2014 12:48:22 PM
 */
public interface CartListListener {
  public void onSuccess(ArrayList<CartDetail> cartDetails);

  public void onError(String errorCode, String errorMessage);
}
