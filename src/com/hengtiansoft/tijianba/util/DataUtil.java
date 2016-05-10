package com.hengtiansoft.tijianba.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.hengtiansoft.tijianba.db.DatabaseHelper;

/**
 * 
 * com.hengtiansoft.tijianba.util.DataBaseUtil
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 21, 2014 3:41:06 PM
 */
public class DataUtil {

  private Context context;
  public static String dbName = DatabaseHelper.DATABASE_NAME;
  public static String spName = "settings.xml";
  private static String DATABASE_PATH;
  private static String SHARED_PREFS_PATH;
  public static final String REFRESH_TIME_FILE_NAME = "refresh.txt";

  public DataUtil(Context context) {
    this.context = context;
    DATABASE_PATH = context.getFilesDir().getParent() + "/databases/";
    SHARED_PREFS_PATH = context.getFilesDir().getParent() + "/shared_prefs/";
  }

  /**
   * 判断数据库是否存在
   * 
   * @return false or true
   */
  public boolean checkDataBase() {
    SQLiteDatabase db = null;
    try {
      String databaseFilename = DATABASE_PATH + dbName;
      db = SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READONLY);
    } catch (SQLiteException e) {

    }
    if (db != null) {
      db.close();
    }
    return db != null ? true : false;
  }

  /**
   * 复制数据库到手机指定文件夹下
   * 
   * @throws IOException
   */
  public void copyDataBase() throws IOException {
    String databaseFilenames = DATABASE_PATH + dbName;
    File dir = new File(DATABASE_PATH);
    if (!dir.exists())
      dir.mkdir();
    FileOutputStream os = new FileOutputStream(databaseFilenames);
    AssetManager am = null;
    am = context.getAssets();
    InputStream is = am.open(DatabaseHelper.DATABASE_NAME);
    byte[] buffer = new byte[8192];
    int count = 0;
    while ((count = is.read(buffer)) > 0) {
      os.write(buffer, 0, count);
      os.flush();
    }
    is.close();
    os.close();
  }

  public void copySharedPrefs() throws IOException {
    String databaseFilenames = SHARED_PREFS_PATH + spName;
    File dir = new File(SHARED_PREFS_PATH);
    if (!dir.exists())
      dir.mkdir();
    FileOutputStream os = new FileOutputStream(databaseFilenames);
    AssetManager am = null;
    am = context.getAssets();
    InputStream is = am.open(spName);
    byte[] buffer = new byte[8192];
    int count = 0;
    while ((count = is.read(buffer)) > 0) {
      os.write(buffer, 0, count);
      os.flush();
    }
    is.close();
    os.close();
  }
}
