package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * com.hengtiansoft.tijianba.net.response.MyMenusListener
 * @author flychen <br/>
 * create at Dec 26, 2014 4:09:45 PM
 */
public interface MyMenusListener {
  public void onSuccess(ArrayList<Menu> menus);
  public void onError(String errorCode, String errorMessage);
}
