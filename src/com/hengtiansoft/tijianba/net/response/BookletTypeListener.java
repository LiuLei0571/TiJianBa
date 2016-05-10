package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;


public interface BookletTypeListener {
	  public void onSuccess(ArrayList<HealthHomeBookletDate> bookletTypeList);
	  public void onError(String errorCode, String errorMessage);

}
