
package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 15:48:32 PM
 */
public interface ResultsListener<T> {
    public void onSuccess(ArrayList<?> arrayList);

    public void onError(String errorCode, String errorMessage);
}
