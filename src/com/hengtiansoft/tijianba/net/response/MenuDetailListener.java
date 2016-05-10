
package com.hengtiansoft.tijianba.net.response;


/**
 * com.hengtiansoft.tijianba.net.response.MenuDetailListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 7, 2014 2:53:30 PM
 */
public interface MenuDetailListener {
    public void onSuccess(MenuDetail menuDetail);

    public void onError(String errorCode, String errorMessage);
}
