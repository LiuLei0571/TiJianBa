package com.hengtiansoft.tijianba.db.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.hengtiansoft.tijianba.db.dao.CityDao;
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
public class CityDaoImpl extends BaseDaoImpl<CityDao, Integer> implements
		com.hengtiansoft.tijianba.db.interfaces.CityDaoImpl {

	public CityDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, CityDao.class);
	}

	@Override
	public int create(CityDao data) {
		try {
			return super.create(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<CityDao> queryForAll() throws SQLException {

		return super.queryForAll();
	}

	@Override
	public void deleteAll() {
		try {
			if (queryForAll() != null)
				delete(queryForAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isExist() {
		return false;
	}

	@Override
	public CityDao getCityByCode(String code) {
		CityDao city = null;
		try {
			QueryBuilder<CityDao, Integer> qb = this.queryBuilder();
			Where<CityDao, Integer> where = qb.where();
			where.eq("code", code);
			PreparedQuery<CityDao> preQuery = qb.prepare();
			if (query(preQuery).size() != 0) {
				city = query(preQuery).get(0);
			} else {
				city = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return city;
	}

	@Override
	public void batchInsert(ArrayList<CityDao> cities) {

		CityDao city = new CityDao();
		CityDao cityDB = new CityDao();
		if (cities.size() == 0) {
			Log.i("nodata", "nodata");
		} else {
			for (int i = 0; i < cities.size(); i++) {
				city = cities.get(i);
				if (city.getStatus() == 1) {
					deleteByCode(city.getCode());
				} else {
					cityDB = getCityByCode(city.getCode());
					try {
						if (cityDB == null) {
							create(city);
						} else {
							cityDB.setCode(city.getCode());
							cityDB.setName(city.getName());
							cityDB.setLevel(city.getLevel());
							cityDB.setParentCode(city.getParentCode());
							cityDB.setWeight(city.getWeight());
							update(cityDB);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public void deleteByCode(String code) {
		try {
			if (getCityByCode(code) != null) {
				delete(getCityByCode(code));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CityDao> queryForCity() {
		try {
			QueryBuilder<CityDao, Integer> qb = this.queryBuilder();
			Where<CityDao, Integer> where = qb.where();
			where.eq("level", 2);
			PreparedQuery<CityDao> preQuery = qb.prepare();
			if (query(preQuery).size() != 0) {
				return query(preQuery);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCityCode(String cityName) {
		try {
			QueryBuilder<CityDao, Integer> qb = this.queryBuilder();
			Where<CityDao, Integer> where = qb.where();
			where.eq("name", cityName);
			PreparedQuery<CityDao> preQuery = qb.prepare();
			if (query(preQuery).size() != 0) {
				return query(preQuery).get(0).getCode();
			} else {
				return "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public CityDao queryForCityName(String cityName) {
		CityDao city = null;
		try {
			QueryBuilder<CityDao, Integer> qb = this.queryBuilder();
			Where<CityDao, Integer> where = qb.where();
			where.eq("name", cityName);
			PreparedQuery<CityDao> preQuery = qb.prepare();
			if (query(preQuery).size() != 0) {
				city = query(preQuery).get(0);
			} else {
				city = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return city;
	}
}
