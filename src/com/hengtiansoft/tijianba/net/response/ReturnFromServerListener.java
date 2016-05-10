package com.hengtiansoft.tijianba.net.response;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 11:21:32 AM
 */
public interface ReturnFromServerListener {
  public void onSuccess(String message);

  public void onError(String errorCode, String errorMessage);
}
