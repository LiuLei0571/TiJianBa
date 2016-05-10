
package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * com.hengtiansoft.tijianba.net.response.MenuListListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 7, 2014 2:35:54 PM
 */
public interface MenuListListener {
    public void onSuccess(ArrayList<Menu> menus,boolean isFamily);

    public void onError(String errorCode, String errorMessage);
}
