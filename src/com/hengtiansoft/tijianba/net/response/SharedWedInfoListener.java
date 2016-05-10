
package com.hengtiansoft.tijianba.net.response;

import com.hengtiansoft.tijianba.net.response.SharedWebInfoResult.SharedWebInfor;

public interface SharedWedInfoListener {
    public void onSuccess(SharedWebInfor sharedWebInfor);

    public void onError(String errorCode, String errorMessage);
}
