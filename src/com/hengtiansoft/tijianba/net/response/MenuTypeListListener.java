
package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.net.response.MenuTypeListResult.MenuType;

/**
 * com.hengtiansoft.tijianba.net.response.MenuTypeListListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 10, 2014 2:26:05 PM
 */
public interface MenuTypeListListener {
    public void onSuccess(ArrayList<MenuType> menuTypes);

    public void onError(String errorCode, String errorMessage);
}
