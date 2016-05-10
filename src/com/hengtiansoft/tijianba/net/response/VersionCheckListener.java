
package com.hengtiansoft.tijianba.net.response;


public interface VersionCheckListener {

    public void onSuccess(String Url);

    public void onError(String errorCode, String errorMessage);

}
