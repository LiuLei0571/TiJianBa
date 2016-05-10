
package com.hengtiansoft.tijianba.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hengtiansoft.tijianba.db.dao.CityDao;
import com.hengtiansoft.tijianba.db.dao.ShoppingCartDao;
import com.hengtiansoft.tijianba.db.dao.StepDao;
import com.hengtiansoft.tijianba.db.impl.CityDaoImpl;
import com.hengtiansoft.tijianba.db.impl.ShoppingCartDaoImpl;
import com.hengtiansoft.tijianba.db.impl.StepDaoImpl;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 3, 2014 10:42:17 AM
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "dongdong.db";
    private static int DATABASE_VERSION = 1;
    private CityDaoImpl mCityDaoImpl = null;
    private StepDaoImpl mStepDaoImpl = null;
    private ShoppingCartDaoImpl mShoppingCartDaoImpl = null;

    public DatabaseHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);

        connectionSource = new AndroidConnectionSource(this);
    }

    /**
     * build SQLite data base
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CityDao.class);
            TableUtils.createTableIfNotExists(connectionSource, ShoppingCartDao.class);
            TableUtils.createTableIfNotExists(connectionSource, StepDao.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update SQLite database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, CityDao.class, true);
            TableUtils.dropTable(connectionSource, ShoppingCartDao.class, true);
            onCreate(sqliteDatabase);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        } catch (java.sql.SQLException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }
    public CityDaoImpl getmCityDaoImpl() {
        if (mCityDaoImpl == null) {
            try {
                mCityDaoImpl = DaoManager.createDao(connectionSource, CityDao.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return mCityDaoImpl;
    }

    public ShoppingCartDaoImpl getmShoppingCartDaoImpl() {
        if (mShoppingCartDaoImpl == null) {
            try {
                mShoppingCartDaoImpl = DaoManager.createDao(connectionSource, ShoppingCartDao.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return mShoppingCartDaoImpl;
    }
    public StepDaoImpl getmStepDaoImpl() {
      if (mStepDaoImpl == null) {
          try {
            mStepDaoImpl = DaoManager.createDao(connectionSource, StepDao.class);
          } catch (java.sql.SQLException e) {
              e.printStackTrace();
          }
      }
      return mStepDaoImpl;
  }
	/**
     * release the table
     */
    @Override
    public void close() {
        super.close();
        mCityDaoImpl = null;
        mShoppingCartDaoImpl = null;
        mStepDaoImpl = null;
    }
}
