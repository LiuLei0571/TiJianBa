package com.hengtiansoft.tijianba.db.dao;

import com.hengtiansoft.tijianba.db.impl.CityDaoImpl;
import com.hengtiansoft.tijianba.net.response.CityResult.City;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 12:21:32 PM
 */
@DatabaseTable(daoClass = CityDaoImpl.class, tableName = "City")
public class CityDao {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String code;
	@DatabaseField
	private String name;
	@DatabaseField
	private String parentCode;
	@DatabaseField
	private int level;
	@DatabaseField
	private int weight;
	@DatabaseField
	private int status;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void cloneContent(CityDao cityDao, City city) {
		setCode(city.getCode());
		setName(city.getName());
		setParentCode(city.getParentCode());
		setLevel(city.getLevel());
		setWeight(city.getWeight());
		setStatus(city.getStatus());
	}
}
