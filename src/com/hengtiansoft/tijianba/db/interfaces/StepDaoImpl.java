package com.hengtiansoft.tijianba.db.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.hengtiansoft.tijianba.db.dao.StepDao;

/**
 * com.hengtiansoft.tijianba.db.interfaces.SurveyContentImpl
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 6, 2014 2:25:00 PM
 */
public interface StepDaoImpl {
 public int  create(StepDao stepDao);
  public StepDao queryForStep(String date);
  public void addStep(StepDao stepDao);
  public void updateStep(StepDao stepDao);
  public ArrayList<StepDao> queryForAll();
}
