package com.hengtiansoft.tijianba.db.dao;

import java.io.Serializable;

import com.hengtiansoft.tijianba.db.impl.ShoppingCartDaoImpl;
import com.hengtiansoft.tijianba.db.impl.StepDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 12:21:32 PM
 */
@DatabaseTable (daoClass = StepDaoImpl.class,tableName = "step")
public class StepDao {
  @DatabaseField(generatedId = true)
  private int id;
  @DatabaseField
  private String date;
  @DatabaseField
  private int step;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
  this.id = id;}
  

  public int getStep() {
    return this.step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

}
