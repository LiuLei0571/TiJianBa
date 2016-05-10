package com.hengtiansoft.tijianba.net.response;



public interface BookDataListener {
	  public void onSuccess(BookDataList bookDataList );

	  public void onError(String errorCode, String errorMessage);

}
