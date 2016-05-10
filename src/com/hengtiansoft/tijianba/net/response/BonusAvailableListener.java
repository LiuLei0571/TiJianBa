package com.hengtiansoft.tijianba.net.response;


/**
 * 
 * com.hengtiansoft.tijianba.net.response.BonusAvailableListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 15, 2015 12:56:21 PM
 */
public interface BonusAvailableListener {
  public void onSuccess(int bonusAvailable);

  public void onError(String errorCode, String errorMessage);
}
