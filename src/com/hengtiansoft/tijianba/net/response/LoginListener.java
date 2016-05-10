
package com.hengtiansoft.tijianba.net.response;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 15:47:32 PM
 */
public interface LoginListener {
    public void onSuccess(String userId);

    public void onError(String errorCode, String errorMessage);
}
