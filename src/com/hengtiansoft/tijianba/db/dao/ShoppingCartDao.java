
package com.hengtiansoft.tijianba.db.dao;

import com.hengtiansoft.tijianba.db.impl.ShoppingCartDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 12:21:32 PM
 */
@DatabaseTable(daoClass = ShoppingCartDaoImpl.class, tableName = "ShoppingCart")
public class ShoppingCartDao {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int menuId;
    @DatabaseField
    private int orgId;
    @DatabaseField
    private int buyNum;
    @DatabaseField
    private int mobile;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return this.menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getOrgId() {
        return this.orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getBuyNum() {
        return this.buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getMobile() {
        return this.mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

}
