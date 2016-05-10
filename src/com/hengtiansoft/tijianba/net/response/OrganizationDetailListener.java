
package com.hengtiansoft.tijianba.net.response;


/**
 * com.hengtiansoft.tijianba.net.response.OrganizationDetailListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 5:27:16 PM
 */
public interface OrganizationDetailListener {
    public void onSuccess(OrganizationDetail organizationDetail);

    public void onError(String errorCode, String errorMessage);
}
