package com.hengtiansoft.tijianba.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.hengtiansoft.tijianba.db.DatabaseHelper;
import com.hengtiansoft.tijianba.db.impl.CityDaoImpl;
import com.hengtiansoft.tijianba.db.impl.ShoppingCartDaoImpl;
import com.hengtiansoft.tijianba.db.impl.StepDaoImpl;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.BuyDetailResult;
import com.hengtiansoft.tijianba.net.response.BuyMenu;
import com.hengtiansoft.tijianba.net.response.BuyNowListener;
import com.hengtiansoft.tijianba.net.response.PackedBuyNowRequest;
import com.hengtiansoft.tijianba.net.response.PackedDeleteCartRequest;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * com.hengtiansoft.tijianba.activity.BaseActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 3, 2014 10:42:17 AM
 */
public class BaseActivity extends Activity {

  private Context mContext;
  private AlertDialog mAlertDialog;
  private ProgressDialog mProgressDialog;
  public DatabaseHelper mDatabaseHelper;
  public CityDaoImpl mCityDaoImpl;
  public StepDaoImpl mStepDaoImpl;
  public ShoppingCartDaoImpl mShoppingCartDaoImpl;
  public RemoteDataManager remoteDataManager;
  public SharedPreferences spSettting;
  protected Dialog dialogsupport;
  protected IWXAPI api;
  protected DisplayImageOptions options;
  protected ArrayList<Integer> mCartDeleted = new ArrayList<Integer>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    mContext = this;
    super.onCreate(savedInstanceState);
    mDatabaseHelper = new DatabaseHelper(this);
    mCityDaoImpl = mDatabaseHelper.getmCityDaoImpl();
    mShoppingCartDaoImpl = mDatabaseHelper.getmShoppingCartDaoImpl();
    mStepDaoImpl = mDatabaseHelper.getmStepDaoImpl();
    remoteDataManager = RemoteDataManager.getInstance();
    spSettting = getSharedPreferences("settings", 0);

    options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
        .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true).displayer(new SimpleBitmapDisplayer())
        .build();
  }

  /**
   * Show a alert dialog with the given title and message, if a dialog is
   * already exist, update the display.
   * 
   * @param title
   *          The title to show in the dialog
   * @param msg
   *          The message to show in the dialog.
   */
  public void showAlertDialog(String title, String msg) {
    if (mAlertDialog == null) {
      mAlertDialog = new AlertDialog.Builder(this).create();
    }
    mAlertDialog.setTitle(title);
    mAlertDialog.setMessage(msg);
    // mAlertDialog.setIcon(R.drawable.icon);
    mAlertDialog.setCancelable(true);
    mAlertDialog.setCanceledOnTouchOutside(true);
    mAlertDialog.show();
  }

  @SuppressLint("NewApi")
  public void showAlertDialog(int title, int msg) {
    if (mAlertDialog == null) {
      mAlertDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
    }
    mAlertDialog.setTitle(getString(title));
    mAlertDialog.setMessage(getString(msg));
    // mAlertDialog.setIcon(R.drawable.icon);
    mAlertDialog.setCancelable(true);
    mAlertDialog.setCanceledOnTouchOutside(true);
    mAlertDialog.show();
  }

  /**
   * Check if their is a dialog on the screen.
   * 
   * @return true if there is a dialog on the screen
   */
  public boolean isAlertShowing() {
    if (mAlertDialog == null)
      return false;
    return mAlertDialog.isShowing();
  }

  /**
   * Dismiss the dialog
   */
  public void dismissAlertDialog() {
    if (mAlertDialog != null && mAlertDialog.isShowing()) {
      mAlertDialog.dismiss();
    }
  }

  /**
   * Show a progress dialog with the given title and message, if a progress is
   * already exist, update the display.
   * 
   * @param title
   *          The title to show in the progress dialog
   * @param msg
   *          The message to show in the progress dialog
   */
  public void showProgressDialog(String title, String msg) {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
    }
    mProgressDialog.setTitle(title);
    mProgressDialog.setMessage(msg);
    mProgressDialog.setCanceledOnTouchOutside(false);
    mProgressDialog.setCancelable(false);
    mProgressDialog.show();
  }

  /**
   * Show a progress dialog with the given title and message, if a progress is
   * already exist, update the display.
   * 
   * @param titleResId
   *          The title string recourse id to show in the progress dialog
   * @param msgResId
   *          The message string recourse id to show in the progress dialog
   */
  public void showProgressDialog(int titleResId, int msgResId) {
    showProgressDialog(getString(titleResId), getString(msgResId));
  }

  /**
   * Dismiss the progress dialog
   */
  public void dismissProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  /**
   * Show a Toast with the given message
   * 
   * @param msg
   *          The message to show in the Toast
   */
  public void complain(String msg) {
    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
  }

  /**
   * Show a Toast with the given string resource id
   * 
   * @param resId
   *          The message to show in the Toast
   */
  public void complain(int resId) {
    Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
  }

  public void handleError(final String errorMsg) {
    runOnUiThread(new Runnable() {

      @Override
      public void run() {
        Log.e("errorMsg", "errorMessage");
        dismissProgressDialog();
        Toast.makeText(BaseActivity.this, errorMsg, Toast.LENGTH_LONG).show();
      }
    });

  }

  public void handleSuccess(final String successMsg) {
    runOnUiThread(new Runnable() {

      @Override
      public void run() {
        dismissProgressDialog();
        Toast.makeText(BaseActivity.this, successMsg, Toast.LENGTH_LONG).show();
      }
    });
  }

  public boolean validateInternet() {
    ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (manager == null) {
      openWirelessSet();
      return false;
    } else {
      NetworkInfo[] info = manager.getAllNetworkInfo();
      if (info != null) {
        for (int i = 0; i < info.length; i++) {
          if (info[i].getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    }
    openWirelessSet();
    return false;
  }

  public void openWirelessSet() {
    if (null != dialogsupport && dialogsupport.isShowing()) {
      dialogsupport.cancel();
    }
    dialogsupport = new AlertDialog.Builder(mContext).setTitle(R.string.str_prompt)
        .setMessage(mContext.getString(R.string.str_no_connection))
        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            mContext.startActivity(intent);
          }
        }).setNegativeButton(R.string.str_no, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int whichButton) {
            dialog.cancel();
          }
        }).create();
    dialogsupport.setCanceledOnTouchOutside(false);
    dialogsupport.show();
  }

  public void getCartCount(final TextView textView) {
    remoteDataManager.cartCountListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            dismissProgressDialog();
            if (remoteDataManager.cartCount >= 0) {
              textView.setVisibility(View.VISIBLE);
              textView.setText(remoteDataManager.cartCount + "");
            }
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      remoteDataManager.getCartCount();
    }
  }

  public void buyNow(ArrayList<BuyMenu> buyMenus, String orderUserMobile, String orderUserName, String orderUserEmail,
      String orderUserRemark, int bonusMoney) {
    PackedBuyNowRequest buyNowRequest = new PackedBuyNowRequest();
    buyNowRequest.setMenus(buyMenus);
    buyNowRequest.setOrderUserMobile(orderUserMobile);
    buyNowRequest.setOrderUserName(orderUserName);
    buyNowRequest.setOrderUserEmail(orderUserEmail);
    buyNowRequest.setOrderUserRemark(orderUserRemark);
    buyNowRequest.setPayChannel(1);
    buyNowRequest.setPayType(0);
    buyNowRequest.setBonusMoney(bonusMoney);
    remoteDataManager.buyNowListener = new BuyNowListener() {

      @Override
      public void onSuccess(final BuyDetailResult buyDetailResult) {
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            dismissProgressDialog();
            if (mCartDeleted != null && mCartDeleted.size() != 0) {
              remoteDataManager.cartCountListener = new ReturnFromServerListener() {

                @Override
                public void onSuccess(String message) {

                }

                @Override
                public void onError(String errorCode, String errorMessage) {
                  handleError(errorMessage);
                }
              };

              if (validateInternet()) {
                PackedDeleteCartRequest packedDeleteCartRequest = new PackedDeleteCartRequest();
                packedDeleteCartRequest.setCartDeleteList(mCartDeleted);
                remoteDataManager.deleteCart(packedDeleteCartRequest);
              }
            }
            Intent intent = new Intent(BaseActivity.this, ClosingActivity.class);
            startActivityForResult(intent, RemoteDataManager.GO_CLOSING);
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };

    if (validateInternet()) {
      remoteDataManager.buyNow(buyNowRequest);
    }
  }

  public Bitmap takeScreenShot(Activity activity) {

    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap b1 = view.getDrawingCache();
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    int statusBarHeight = frame.top;
    Display display = activity.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int height = size.y;
    Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
    view.destroyDrawingCache();
    return b;
  }

  public File getAlbumStorageDir(String albumName) {
    // Get the directory for the user's public pictures directory.
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
      Log.e("SignaturePad", "Directory not created");
    }
    return file;
  }

  public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {

    Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(newBitmap);
    canvas.drawColor(Color.WHITE);
    canvas.drawBitmap(bitmap, 0, 0, null);
    OutputStream stream = new FileOutputStream(photo);
    newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
    stream.close();
  }

  public boolean addSignatureToGallery(Bitmap signature) {
    boolean result = false;
    try {
      File photo = new File(getAlbumStorageDir("lvningmeng"), String.format("lvningmeng_share_%d.jpg",
          System.currentTimeMillis()));
      saveBitmapToJPG(signature, photo);
      Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
      Uri contentUri = Uri.fromFile(photo);
      mediaScanIntent.setData(contentUri);
      BaseActivity.this.sendBroadcast(mediaScanIntent);
      result = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Show a alert dialog with the given title and message, if a dialog is
   * already exist, update the display.
   * 
   * @param title
   *          The title to show in the dialog
   * @param msg
   *          The message to show in the dialog.
   * @param negativeText
   * @param negativeListener
   * @param positiveText
   * @param positiveListener
   */
  public void showAlertDialog(String title, String msg, String negativeText, OnClickListener negativeListener,
      String positiveText, OnClickListener positiveListener) {
    if (mAlertDialog == null) {
      mAlertDialog = new AlertDialog.Builder(this).setNegativeButton(negativeText, negativeListener)
          .setPositiveButton(positiveText, positiveListener).create();
    }
    mAlertDialog.setTitle(title);
    mAlertDialog.setMessage(msg);
    // mAlertDialog.setIcon(R.drawable.icon);
    mAlertDialog.setCancelable(true);
    mAlertDialog.setCanceledOnTouchOutside(true);
    mAlertDialog.show();
  }

  public void initWXAPI() {
    api = WXAPIFactory.createWXAPI(this, RemoteDataManager.APP_ID, false);
    api.registerApp(RemoteDataManager.APP_ID);
    api = WXAPIFactory.createWXAPI(BaseActivity.this, RemoteDataManager.APP_ID, false);
    boolean isInstalledWeibo = api.isWXAppInstalled();

    if (!isInstalledWeibo) {
      Toast.makeText(BaseActivity.this, "未安装微信客户端", Toast.LENGTH_SHORT).show();
    }
    // 注册到微信
    api.registerApp(RemoteDataManager.APP_ID);
    // sendToWx();
  }

  protected String buildTransaction(final String type) {
    return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK && requestCode == RemoteDataManager.GO_CLOSING) {
      finish();
    }
  }
}
