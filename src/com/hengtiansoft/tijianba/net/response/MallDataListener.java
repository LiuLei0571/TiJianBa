package com.hengtiansoft.tijianba.net.response;


/**
 * 
 * com.hengtiansoft.tijianba.net.response.MallDataListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 5, 2015 9:02:51 PM
 */
public interface MallDataListener {
  public void onSuccess(MallData mallData);

  public void onError(String errorCode, String errorMessage);
}
