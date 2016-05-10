package com.hengtiansoft.tijianba.db.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.hengtiansoft.tijianba.db.dao.StepDao;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

/**
 * com.hengtiansoft.tijianba.db.impl.CityImpl
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 6, 2014 2:27:53 PM
 */
public class StepDaoImpl extends BaseDaoImpl<StepDao, Integer> implements
    com.hengtiansoft.tijianba.db.interfaces.StepDaoImpl {

  public StepDaoImpl(ConnectionSource connectionSource) throws SQLException {
    super(connectionSource, StepDao.class);
  }

  @Override
  public int create(StepDao data) {
    try {
    super.create(data);
    } catch (SQLException e) {
    }
    return 0;
  }

   @Override
    public ArrayList<StepDao> queryForAll()  {
     ArrayList<StepDao> list=null;
      try {
        list=(ArrayList<StepDao>) super.queryForAll();
      } catch (SQLException e) {
      }
      return list;
    }

  @Override
  public StepDao queryForStep(String date) {
    StepDao step = null;
    try {
      QueryBuilder<StepDao, Integer> qb = this.queryBuilder();
      Where<StepDao, Integer> where = qb.where();
      where.eq("date", date);
      PreparedQuery<StepDao> preQuery = qb.prepare();
      if (query(preQuery).size() != 0) {
        step = query(preQuery).get(0);
      } else {
        step = null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return step;
  }

  @Override
  public void addStep(StepDao stepDao) {
  }

  @Override
  public void updateStep(StepDao stepDao) {
    try {
      createOrUpdate(stepDao);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
