
package com.hengtiansoft.tijianba.db.interfaces;

import java.util.ArrayList;

import com.hengtiansoft.tijianba.db.dao.ShoppingCartDao;

/**
 * com.hengtiansoft.tijianba.db.interfaces.SurveyContentImpl
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 6, 2014 2:25:00 PM
 */
public interface ShoppingCartDaoImpl {
    public void deleteAll();

    public void batchInsert(ArrayList<ShoppingCartDao> shoppingCarts);

    public ShoppingCartDao getShoppingCartByID(int menuId, int orgId, int mobile);

    public boolean isExist();

    public void deleteByID(int menuId, int orgId, int mobile);
}
