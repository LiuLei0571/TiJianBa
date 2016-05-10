package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.net.response.OrgBrandListResult.Brand;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.OrgBrandListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 19, 2014 2:01:24 PM
 */
public interface OrgBrandListener {
  public void onSuccess(ArrayList<Brand> brands);

  public void onError(String errorCode, String errorMessage);
}
