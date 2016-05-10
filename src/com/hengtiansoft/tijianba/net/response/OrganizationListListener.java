package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

/**
 * com.hengtiansoft.tijianba.net.response.OrganizationListListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 7, 2014 3:07:49 PM
 */
public interface OrganizationListListener {
  public void onSuccess(ArrayList<Organization> organizations);

  public void onError(String errorCode, String errorMessage);
}
