package com.hengtiansoft.tijianba.net.response;

import com.hengtiansoft.tijianba.net.response.CheckInteractionResult.InteractionStatus;

/**
 * 
 * com.hengtiansoft.tijianba.net.response.CheckInteractionListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 15, 2015 12:39:42 PM
 */
public interface CheckInteractionListener {
  public void onSuccess(InteractionStatus interactionStatus);

  public void onError(String errorCode, String errorMessage);
}
