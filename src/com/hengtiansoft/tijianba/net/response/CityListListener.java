package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.net.response.CityResult.City;

/**
 * com.hengtiansoft.tijianba.net.response.CityListListener
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 12, 2014 2:07:04 PM
 */
public interface CityListListener {
	public void onSuccess(ArrayList<City> cities, String refreshTime);

	public void onError(String errorCode, String errorMessage);
}
