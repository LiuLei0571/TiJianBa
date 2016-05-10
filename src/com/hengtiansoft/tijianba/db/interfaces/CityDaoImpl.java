package com.hengtiansoft.tijianba.db.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.hengtiansoft.tijianba.db.dao.CityDao;

/**
 * com.hengtiansoft.tijianba.db.interfaces.SurveyContentImpl
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 6, 2014 2:25:00 PM
 */
public interface CityDaoImpl {
  public void deleteAll();

  public boolean isExist();

  public void batchInsert(ArrayList<CityDao> cities);

  public CityDao getCityByCode(String code);

  public void deleteByCode(String code);

  public List<CityDao> queryForCity();

  public String getCityCode(String cityName);
  
  public CityDao queryForCityName(String cityName);
}
