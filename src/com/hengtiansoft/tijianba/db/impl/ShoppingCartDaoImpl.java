
package com.hengtiansoft.tijianba.db.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.hengtiansoft.tijianba.db.dao.ShoppingCartDao;
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
public class ShoppingCartDaoImpl extends BaseDaoImpl<ShoppingCartDao, Integer> implements
        com.hengtiansoft.tijianba.db.interfaces.ShoppingCartDaoImpl {

    public ShoppingCartDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ShoppingCartDao.class);
    }

    @Override
    public int create(ShoppingCartDao data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<ShoppingCartDao> queryForAll() throws SQLException {

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
    public void batchInsert(ArrayList<ShoppingCartDao> shoppingCarts) {

        ShoppingCartDao shoppingCart = new ShoppingCartDao();
        ShoppingCartDao shoppingCartDB = new ShoppingCartDao();
        if (shoppingCarts.size() == 0) {
            Log.i("nodata", "nodata");
        } else {
            for (int i = 0; i < shoppingCarts.size(); i++) {
                shoppingCart = shoppingCarts.get(i);
                shoppingCartDB = getShoppingCartByID(shoppingCart.getMenuId(), shoppingCart.getOrgId(),
                        shoppingCart.getMobile());
                try {
                    if (shoppingCartDB == null) {
                        create(shoppingCart);
                    } else {
                        shoppingCartDB.setBuyNum(shoppingCartDB.getBuyNum() + shoppingCart.getBuyNum());
                        update(shoppingCartDB);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public ShoppingCartDao getShoppingCartByID(int menuId, int orgId, int mobile) {
        ShoppingCartDao shoppingCart = null;
        try {
            QueryBuilder<ShoppingCartDao, Integer> qb = this.queryBuilder();
            Where<ShoppingCartDao, Integer> where = qb.where();
            where.eq("menuId", menuId).and().eq("orgId", orgId).and().eq("mobile", mobile);
            PreparedQuery<ShoppingCartDao> preQuery = qb.prepare();
            if (query(preQuery).size() != 0) {
                shoppingCart = query(preQuery).get(0);
            } else {
                shoppingCart = null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return shoppingCart;
    }

    @Override
    public void deleteByID(int menuId, int orgId, int mobile) {
        ShoppingCartDao shoppingCart = new ShoppingCartDao();
        shoppingCart = getShoppingCartByID(menuId, orgId, mobile);
        if (shoppingCart != null) {
            try {
                if (shoppingCart.getBuyNum() != 1) {
                    shoppingCart.setBuyNum(shoppingCart.getBuyNum() - 1);

                    update(shoppingCart);
                } else {
                    delete(shoppingCart);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
