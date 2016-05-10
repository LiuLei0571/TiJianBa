package com.hengtiansoft.tijianba;

import android.app.Application;
import android.content.Context;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * com.hengtiansoft.tijianba.TiJianBaApp
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 14:44:11 PM
 */
public class TiJianBaApp extends Application {
  private static Context mContext;

  @Override
  public void onCreate() {
    mContext = getApplicationContext();
    RemoteDataManager rdm = RemoteDataManager.getInstance();
    rdm.loginPath = "mobile/login";
    rdm.isMobileExitPath = "mobile/verifyMobile";
    rdm.registerPath = "mobile/register";
    rdm.verifyCodePath = "mobile/messageVerify";
    rdm.verifyCodeExpirePath = "mobile/verifyCode";
    rdm.resetPwdPath = "mobile/resetPassword";
    rdm.modifyPwdPath = "mobile/modifyPassword";
    rdm.menuListPath = "mobile/menuList";
    rdm.menuDetailPath = "mobile/menuDetail";
    rdm.organizationListPath = "mobile/organizationList";
    rdm.organizationDetailPath = "mobile/organizationDetail";
    rdm.orderDetailPath = "mobile/orderDetail";
    rdm.menuTypePath = "mobile/menuTypeList";
    rdm.cityListPath = "mobile/cityList";
    rdm.orgBrandPath = "mobile/brandList";
    rdm.cartCountPath = "mobile/cartCount";
    rdm.addToCartPath = "mobile/cartAdd";
    rdm.deleteCartPath = "mobile/cartDelete";
    rdm.cartListPath = "mobile/cartList";
    rdm.subscribeFullDays = "mobile/subscribeFullDays";
    rdm.cartUpdatePath = "mobile/cartUpdate";
    rdm.buyNowPath = "mobile/purchaseMenu";
    rdm.subscribeVerifyPath = "mobile/subscribeVerify";
    rdm.orderListPath = "mobile/orderList";
    rdm.myMenusPath = "mobile/userUnsubscribedMenu";
    rdm.subscribedMenuPath = "mobile/userhasSubscribedMenu";
    rdm.cancelOrderPath = "mobile/cancelOrder";
    rdm.paySuccessPath = "mobile/paySuccess";
    rdm.mallDataPath = "mobile/storeHome";
    rdm.getModifyVerifyCodePath = "mobile/modifyMobile/sendVerify";
    rdm.verifyOldMobilePath = "mobile/modifyMobile/verifyOldMobile";
    rdm.modifyPhoneNumPath = "mobile/modifyMobile/modifyNewMobile";
    rdm.bonusListPath = "mobile/bonusList";
    rdm.bonusUsedListPath = "mobile/bonusUsedList";
    rdm.subscribeConfirmPath = "mobile/subscribe";
    rdm.sharedWebInfoPath = "mobile/bonusSeize";
    rdm.versionCheckPath = "mobile/versionUpdate";
    rdm.interactionCheckPath = "mobile/InteractiveApplicationSwitch";
    rdm.bonusAvailablePath = "mobile/availableBonus";
    rdm.healthHomePath = "mobile/healthHome";
    rdm.healthHomeBookletListPath = "mobile/healthHomeBookletList";
    rdm.healthUserEditPath = "mobile/healthUserEdit";
    rdm.bookDataPath = "mobile/bookletDataList";
    rdm.bookletListPath = "mobile/bookletItemList";
    rdm.bookletDetailPath = "mobile/bookletDetail";
    rdm.bookletAddPath = "mobile/bookletAdd";
    rdm.bookletTypePath = "mobile/bookletTypeSwitch";
    rdm.bookletUpdatePath = "mobile/bookletUpdate";
    rdm.bookletDeletePath = "mobile/bookletDelete";
    rdm.bookletSwitchPath = "mobile/bookletShowSwitch";
    rdm.reportListPath = "mobile/reportList";
    rdm.reportDetailPath = "mobile/reportDetail";
    rdm.reportAddPath = "mobile/reportAdd";
    rdm.reportUpdatePath = "mobile/reportUpdate";
    rdm.reportDeletePath = "mobile/reportDelete";
    rdm.uploadstepPath="mobile/getStepCounter";
    rdm.stepgiftPath="mobile/getStepCounterGift";
    // TODO
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).memoryCacheExtraOptions(480, 800)
        .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.LIFO)
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
        .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)).writeDebugLogs().build();
    ImageLoader.getInstance().init(config);

  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  }

  public static Context getAppContext() {
    return mContext;
  }
}
