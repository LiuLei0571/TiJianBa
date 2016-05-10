package com.hengtiansoft.tijianba.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.google.gson.Gson;
import com.hengtiansoft.tijianba.TiJianBaApp;
import com.hengtiansoft.tijianba.net.HttpClientUtil;
import com.hengtiansoft.tijianba.net.NetException;
import com.hengtiansoft.tijianba.net.response.BonusAvailableListener;
import com.hengtiansoft.tijianba.net.response.BonusAvailableResult;
import com.hengtiansoft.tijianba.net.response.BonusListListener;
import com.hengtiansoft.tijianba.net.response.BonusListResult;
import com.hengtiansoft.tijianba.net.response.BonusUsedListListener;
import com.hengtiansoft.tijianba.net.response.BonusUsedListResult;
import com.hengtiansoft.tijianba.net.response.BookDataListener;
import com.hengtiansoft.tijianba.net.response.BookDataResult;
import com.hengtiansoft.tijianba.net.response.BookletAdd;
import com.hengtiansoft.tijianba.net.response.BookletDetailListener;
import com.hengtiansoft.tijianba.net.response.BookletDetailResult;
import com.hengtiansoft.tijianba.net.response.BookletItemListener;
import com.hengtiansoft.tijianba.net.response.BookletItemResult;
import com.hengtiansoft.tijianba.net.response.BookletTypeListener;
import com.hengtiansoft.tijianba.net.response.BookletTypeResult;
import com.hengtiansoft.tijianba.net.response.BuyDetail;
import com.hengtiansoft.tijianba.net.response.BuyDetailResult;
import com.hengtiansoft.tijianba.net.response.BuyNowListener;
import com.hengtiansoft.tijianba.net.response.CartCountResult;
import com.hengtiansoft.tijianba.net.response.CartListListener;
import com.hengtiansoft.tijianba.net.response.CartListResult;
import com.hengtiansoft.tijianba.net.response.CheckInteractionListener;
import com.hengtiansoft.tijianba.net.response.CheckInteractionResult;
import com.hengtiansoft.tijianba.net.response.CityListListener;
import com.hengtiansoft.tijianba.net.response.CityResult;
import com.hengtiansoft.tijianba.net.response.ExamReportUpdate;
import com.hengtiansoft.tijianba.net.response.ReportDetailListener;
import com.hengtiansoft.tijianba.net.response.ReportDetailResult;
import com.hengtiansoft.tijianba.net.response.ReportListListener;
import com.hengtiansoft.tijianba.net.response.ReportListResult;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletListener;
import com.hengtiansoft.tijianba.net.response.HealthHomeBookletResult;
import com.hengtiansoft.tijianba.net.response.HealthHomeListener;
import com.hengtiansoft.tijianba.net.response.HealthHomeResult;
import com.hengtiansoft.tijianba.net.response.LoginListener;
import com.hengtiansoft.tijianba.net.response.LoginResult;
import com.hengtiansoft.tijianba.net.response.MallDataListener;
import com.hengtiansoft.tijianba.net.response.MallDataResult;
import com.hengtiansoft.tijianba.net.response.MenuDetailListener;
import com.hengtiansoft.tijianba.net.response.MenuDetailResult;
import com.hengtiansoft.tijianba.net.response.MenuListListener;
import com.hengtiansoft.tijianba.net.response.MenuListResult;
import com.hengtiansoft.tijianba.net.response.MenuTypeListListener;
import com.hengtiansoft.tijianba.net.response.MenuTypeListResult;
import com.hengtiansoft.tijianba.net.response.MyMenusListener;
import com.hengtiansoft.tijianba.net.response.OrderDetailListener;
import com.hengtiansoft.tijianba.net.response.OrderDetailResult;
import com.hengtiansoft.tijianba.net.response.OrderListListener;
import com.hengtiansoft.tijianba.net.response.OrderListResult;
import com.hengtiansoft.tijianba.net.response.OrgBrandListResult;
import com.hengtiansoft.tijianba.net.response.OrgBrandListener;
import com.hengtiansoft.tijianba.net.response.OrganizationDetailListener;
import com.hengtiansoft.tijianba.net.response.OrganizationDetailResult;
import com.hengtiansoft.tijianba.net.response.OrganizationListListener;
import com.hengtiansoft.tijianba.net.response.OrganizationListResult;
import com.hengtiansoft.tijianba.net.response.PackedBuyNowRequest;
import com.hengtiansoft.tijianba.net.response.PackedDeleteCartRequest;
import com.hengtiansoft.tijianba.net.response.PackedMenuRequest;
import com.hengtiansoft.tijianba.net.response.PackedNewAccount;
import com.hengtiansoft.tijianba.net.response.PackedOrderRequest;
import com.hengtiansoft.tijianba.net.response.PackedOrgRequest;
import com.hengtiansoft.tijianba.net.response.PackedSubscribeInfo;
import com.hengtiansoft.tijianba.net.response.PackedUpdateCartRequest;
import com.hengtiansoft.tijianba.net.response.PaySuccessListener;
import com.hengtiansoft.tijianba.net.response.PaySuccessResult;
import com.hengtiansoft.tijianba.net.response.Response;
import com.hengtiansoft.tijianba.net.response.ResponseResult;
import com.hengtiansoft.tijianba.net.response.ResultsListener;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.net.response.SharedWedInfoListener;
import com.hengtiansoft.tijianba.net.response.SharedWebInfoResult;
import com.hengtiansoft.tijianba.net.response.StepGiftDetailListener;
import com.hengtiansoft.tijianba.net.response.StepGiftDetailtResponse;
import com.hengtiansoft.tijianba.net.response.SubscribeFullDay;
import com.hengtiansoft.tijianba.net.response.SubscribeFullDaysListener;
import com.hengtiansoft.tijianba.net.response.SubscribeRecordList;
import com.hengtiansoft.tijianba.net.response.SubscribeRecordListListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerifyListener;
import com.hengtiansoft.tijianba.net.response.SubscribeVerifyResult;
import com.hengtiansoft.tijianba.net.response.UploadStepListener;
import com.hengtiansoft.tijianba.net.response.UploadStepResponse;
import com.hengtiansoft.tijianba.net.response.VersionCheckListener;
import com.hengtiansoft.tijianba.net.response.VersionCheckResult;
import com.juanliuinformation.lvningmeng.R;

/**
 * com.hengtiansoft.tijianba.model.RemoteDataManager
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 5, 2014 11:21:32 AM
 */
public class RemoteDataManager {
	public static final String APP_ID = "wx116ea0db6a03f7f8";
	 public static final String SERVICE = "http://www.lnmjk.com/";//生产
	 public static final String SERVICE_NORMAL
	 ="http://www.lnmjk.com/mobileSns/";
	// public static final String SERVICE = "http://172.16.129.124/";//内测
	// public static final String SERVICE_NORMAL = "http://172.16.129.124/";
	// public static final String SERVICE = "http://115.29.209.135/";//外测
	// public static final String SERVICE_NORMAL
	// ="http://115.29.209.135:9090/mobileSns/";
	//public static final String SERVICE = "http://192.168.56.1/";
	//public static final String SERVICE_NORMAL = "http://192.168.56.1:8080/health2/";
	public static int SUBCRIBE = 101;
	public static int ADD_CART = 102;
	public static int MORE_LOGIN = 103;
	public static int GO_SUBCRIBE = 104;
	public static int GO_CLOSING = 104;
	public static final String[] mOrderTyNames;
	static {
		mOrderTyNames = new String[3];
		mOrderTyNames[0] = TiJianBaApp.getAppContext().getString(
				R.string.str_org_type_pro);
		mOrderTyNames[1] = TiJianBaApp.getAppContext().getString(
				R.string.str_org_type_re);
		mOrderTyNames[2] = TiJianBaApp.getAppContext().getString(
				R.string.str_org_type_pub);
	}
	public static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd",
			Locale.getDefault());
	public static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy年MM月",
			Locale.getDefault());
	private static RemoteDataManager sharedManager = null;
	public boolean isLogin = false;
	public static HashMap<Integer, String> orgType = new HashMap<Integer, String>();
	public static HashMap<Integer, String> payType = new HashMap<Integer, String>();
	public static HashMap<Integer, String> reserveStatus = new HashMap<Integer, String>();
	public LoginListener newAccountLoginListener;
	public ReturnFromServerListener registerListener;
	public ReturnFromServerListener isMobileExitListener;
	public ReturnFromServerListener loginListener;
	public ReturnFromServerListener getVerifyCodeListener;
	public ReturnFromServerListener verifyCodeExpireListener;
	public ReturnFromServerListener resetPwdListener;
	public ReturnFromServerListener modifyPwdListener;
	public ReturnFromServerListener cartCountListener;
	public ReturnFromServerListener cancelOrderListener;
	public OrganizationListListener organizationListListener;
	public OrganizationDetailListener organizationDetailListener;
	public MenuListListener menuListListener;
	public MenuDetailListener menuDetailListener;
	public MenuTypeListListener menuTypeListListener;
	public CartListListener cartListListener;
	public CityListListener cityListListener;
	public OrderListListener orderlistListener;
	public OrderDetailListener orderDetailListener;
	public OrgBrandListener orgBrandListener;
	public BuyNowListener buyNowListener;
	public SubscribeVerifyListener subscribeVerifyListener;
	public ResultsListener<?> resultsListener;
	public SubscribeFullDaysListener subscribeFullDaysListener;
	public MyMenusListener myMenusListener;
	public SubscribeRecordListListener subscribeRecordListListener;
	public PaySuccessListener paySuccessListener;
	public MallDataListener mallDataListener;
	public ReturnFromServerListener getModifyVerifyCodeListener;
	public ReturnFromServerListener verifyOldMobileListener;
	public ReturnFromServerListener modifyPhoneNumListener;
	public BonusListListener bonusListListener;
	public BonusUsedListListener bonusUsedListListener;
	public ReturnFromServerListener subscribeConfirmListener;
	public SharedWedInfoListener sharedWedInfoListener;
	public VersionCheckListener versionCheckListener;
	public CheckInteractionListener checkInteractionListener;
	public BonusAvailableListener bonusAvailableListener;
	public HealthHomeListener healthHomeListener;
	public HealthHomeBookletListener healthHomeBookletListener;
	public ReturnFromServerListener healthUserEditListener;
	public BookDataListener bookDataListener;
	public BookletDetailListener bookletDetailListener;
	public BookletItemListener bookletItemListener;
	public BookletTypeListener bookletTypeListener;
	public ReturnFromServerListener bookletAddListener;
	public ReturnFromServerListener bookletUpdateListener;
	public ReturnFromServerListener bookletDeleteListener;
	public ReturnFromServerListener bookletSwitchListener;
	public ReportListListener reportListListener;
	public ReportDetailListener reportDetailListener;
	public ReturnFromServerListener reportDeleteListener;
	public ReturnFromServerListener reportAddListener;
	public ReturnFromServerListener reportUpdateListener;

	public UploadStepListener uploadListener;
	public StepGiftDetailListener stepgiftdetailListener;
	// TODO

	public String loginPath;
	public String isMobileExitPath;
	public String registerPath;
	public String verifyCodePath;
	public String verifyCodeExpirePath;
	public String resetPwdPath;
	public String modifyPwdPath;
	public String organizationDetailPath;
	public String organizationListPath;
	public String orderListPath;
	public String orderDetailPath;
	public String menuDetailPath;
	public String menuListPath;
	public String menuTypePath;
	public String cityListPath;
	public String orgBrandPath;
	public String cartCountPath;
	public String addToCartPath;
	public String deleteCartPath;
	public String cartListPath;
	public String subscribeFullDays;
	public String cartUpdatePath;
	public String buyNowPath;
	public String subscribeVerifyPath;
	public String myMenusPath;
	public String subscribedMenuPath;
	public String cancelOrderPath;
	public String mallDataPath;
	public String paySuccessPath;
	public String getModifyVerifyCodePath;
	public String verifyOldMobilePath;
	public String modifyPhoneNumPath;
	public String bonusListPath;
	public String bonusUsedListPath;
	public String subscribeConfirmPath;
	public String sharedWebInfoPath;
	public String versionCheckPath;
	public String interactionCheckPath;
	public String bonusAvailablePath;
	public String healthHomePath;
	public String healthHomeBookletListPath;
	public String healthUserEditPath;
	public String bookDataPath;
	public String bookletListPath;
	public String bookletDetailPath;
	public String bookletAddPath;
	public String bookletTypePath;
	public String bookletSwitchPath;
	public String bookletUpdatePath;
	public String bookletDeletePath;
	public String bookletTypeSwitchPath;
	public String reportListPath;
	public String reportDetailPath;
	public String reportDeletePath;
	public String reportAddPath;
	public String reportUpdatePath;

	public String uploadstepPath;
	public String stepgiftPath;
	// TODO
	private HashMap<Class<?>, List<String>> classList = new HashMap<Class<?>, List<String>>();
	public String loginInfo;
	public int userId;
	public int userType = 0;
	public int companyId = 0;
	public String userName = "";
	public String locatedCity = "";
	public String cityCode = "";
	public int cartCount = 0;
	public BuyDetail mBuyDetail;
	public boolean firstOpenApp = true;
	public int currentPage;

	protected RemoteDataManager() {
		// Prevents external instantiation
		orgType.put(0, "专业体检机构");
		orgType.put(1, "公立医院");
		orgType.put(2, "疗养院");
		payType.put(0, "支付宝");
		reserveStatus.put(0, "未支付");
		reserveStatus.put(1, "已支付");
		reserveStatus.put(2, "已关闭");
		reserveStatus.put(3, "");
		reserveStatus.put(4, "已退款");
		reserveStatus.put(5, "已取消");
		reserveStatus.put(6, "已删除");
		reserveStatus.put(7, "部分退款");
	}

	public static RemoteDataManager getInstance() {
		if (sharedManager == null) {
			sharedManager = new RemoteDataManager();
		}

		return sharedManager;
	}

	public boolean pushAll() {
		return true;
	}

	public boolean refreshAll() {
		return true;
	}

	public void isMobileExit(String mobile) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new IsMobileExitWorker(mobile));
		executor.shutdown();
	}

	public class IsMobileExitWorker implements Runnable {
		String mobile;

		public IsMobileExitWorker(String mobile) {
			super();
			this.mobile = mobile;
		}

		@Override
		public void run() {
			doIsMobileExit(mobile);

		}

	}

	public void doIsMobileExit(String mobile) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(isMobileExitPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					isMobileExitListener.onSuccess(result.getMsg());
				} else {
					isMobileExitListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doIsMobileExit", e.getMessage());
			isMobileExitListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doIsMobileExit", e.getMessage());
			isMobileExitListener.onError("JSONException", e.getMessage());
		}
	}

	public void modifyPhoneNum(String mobile, String verifyCode) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new ModifyPhoneNumWorker(mobile, verifyCode));
		executor.shutdown();
	}

	public class ModifyPhoneNumWorker implements Runnable {
		String mobile;
		String verifyCode;

		public ModifyPhoneNumWorker(String mobile, String verifyCode) {
			super();
			this.mobile = mobile;
			this.verifyCode = verifyCode;
		}

		@Override
		public void run() {
			doModifyPhoneNum(mobile, verifyCode);

		}

	}

	public void doModifyPhoneNum(String mobile, String verifyCode) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			jsonObject.put("verifyCode", verifyCode);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(modifyPhoneNumPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					modifyPhoneNumListener.onSuccess(result.getMsg());
				} else {
					modifyPhoneNumListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doModifyPhoneNum", e.getMessage());
			modifyPhoneNumListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doModifyPhoneNum", e.getMessage());
			modifyPhoneNumListener.onError("JSONException", e.getMessage());
		}
	}

	public void checkVersion(String version) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new CheckVersionWorker(version));
		executor.shutdown();
	}

	public class CheckVersionWorker implements Runnable {
		String version;

		public CheckVersionWorker(String version) {
			super();
			this.version = version;
		}

		@Override
		public void run() {
			doCheckVersion(version);

		}

	}

	public void doCheckVersion(String version) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("version", version);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(versionCheckPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				VersionCheckResult result = gson.fromJson(json.toString(),
						VersionCheckResult.class);
				if (result.isSuccess()) {
					versionCheckListener.onSuccess(result.getLinkUrl());
				} else {
					versionCheckListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			// Log.e("RemoteDataManager::doCheckVersion", e.getMessage());
			versionCheckListener.onError("", "");
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doCheckVersion", e.getMessage());
			versionCheckListener.onError("JSONException", e.getMessage());
		}
	}

	public void subscribeConfirm(PackedSubscribeInfo packedSubscribeInfo) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new SubscribeConfirmWorker(packedSubscribeInfo));
		executor.shutdown();
	}

	public class SubscribeConfirmWorker implements Runnable {
		PackedSubscribeInfo packedSubscribeInfo;

		public SubscribeConfirmWorker(PackedSubscribeInfo packedSubscribeInfo) {
			super();
			this.packedSubscribeInfo = packedSubscribeInfo;
		}

		@Override
		public void run() {
			doSubscribeConfirm(packedSubscribeInfo);

		}

	}

	public void doSubscribeConfirm(PackedSubscribeInfo packedSubscribeInfo) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson
				.toJson(packedSubscribeInfo)));

		try {
			Response response = HttpClientUtil.doPost(subscribeConfirmPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					subscribeConfirmListener.onSuccess(result.getMsg());
				} else {
					subscribeConfirmListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doSubscribeConfirm", e.getMessage());
			subscribeConfirmListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doSubscribeConfirm", e.getMessage());
			subscribeConfirmListener.onError("JSONException", e.getMessage());
		}
	}

	public void verifyOldMobile(String mobile, String verifyCode) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new VerifyOldMobileWorker(mobile, verifyCode));
		executor.shutdown();
	}

	public class VerifyOldMobileWorker implements Runnable {
		String mobile;
		String verifyCode;

		public VerifyOldMobileWorker(String mobile, String verifyCode) {
			super();
			this.mobile = mobile;
			this.verifyCode = verifyCode;
		}

		@Override
		public void run() {
			doverifyOldMobile(mobile, verifyCode);

		}

	}

	public void doverifyOldMobile(String mobile, String verifyCode) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			jsonObject.put("verifyCode", verifyCode);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(verifyOldMobilePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					verifyOldMobileListener.onSuccess(result.getMsg());
				} else {
					verifyOldMobileListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doverifyOldMobile", e.getMessage());
			verifyOldMobileListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doverifyOldMobile", e.getMessage());
			verifyOldMobileListener.onError("JSONException", e.getMessage());
		}
	}

	public void register(PackedNewAccount packedNewAccount) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new RegisterWorker(packedNewAccount));
		executor.shutdown();
	}

	public class RegisterWorker implements Runnable {
		PackedNewAccount packedNewAccount;

		public RegisterWorker(PackedNewAccount packedNewAccount) {
			super();
			this.packedNewAccount = packedNewAccount;
		}

		@Override
		public void run() {
			doRegister(packedNewAccount);

		}

	}

	public void doRegister(PackedNewAccount packedNewAccount) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		packedNewAccount.setPassword(md5(packedNewAccount.getPassword()));
		params.add(new BasicNameValuePair("param", gson
				.toJson(packedNewAccount)));
		try {
			Response response = HttpClientUtil.doPost(registerPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					registerListener.onSuccess(result.getMsg());
				} else {
					registerListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::Register", e.getMessage());
			registerListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::Register", e.getMessage());
			registerListener.onError("JSONException", e.getMessage());
		}
	}

	public void getVerifyCode(String mobile) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetVerifyCodeWorker(mobile));
		executor.shutdown();
	}

	public class GetVerifyCodeWorker implements Runnable {
		String mobile;

		public GetVerifyCodeWorker(String mobile) {
			super();
			this.mobile = mobile;
		}

		@Override
		public void run() {
			doGetVerifyCode(mobile);

		}

	}

	public void doGetVerifyCode(String mobile) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(verifyCodePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					getVerifyCodeListener.onSuccess(result.getMsg());
				} else {
					getVerifyCodeListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetVerifyCode", e.getMessage());
			getVerifyCodeListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetVerifyCode", e.getMessage());
			getVerifyCodeListener.onError("JSONException", e.getMessage());
		}
	}

	public void getModifyVerifyCode(String mobile) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetModifyVerifyCodeWorker(mobile));
		executor.shutdown();
	}

	public class GetModifyVerifyCodeWorker implements Runnable {
		String mobile;

		public GetModifyVerifyCodeWorker(String mobile) {
			super();
			this.mobile = mobile;
		}

		@Override
		public void run() {
			doGetModifyVerifyCode(mobile);

		}

	}

	public void doGetModifyVerifyCode(String mobile) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(getModifyVerifyCodePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					getModifyVerifyCodeListener.onSuccess(result.getMsg());
				} else {
					getModifyVerifyCodeListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetModifyVerifyCode", e.getMessage());
			getModifyVerifyCodeListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetModifyVerifyCode", e.getMessage());
			getModifyVerifyCodeListener
					.onError("JSONException", e.getMessage());
		}
	}

	public void getSharedWebInfo() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetSharedWebInfoWorker());
		executor.shutdown();
	}

	private class GetSharedWebInfoWorker implements Runnable {
		public GetSharedWebInfoWorker() {
			super();
		}

		@Override
		public void run() {
			doGetSharedWebInfo();
		}
	}

	private void doGetSharedWebInfo() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(sharedWebInfoPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				SharedWebInfoResult result = gson.fromJson(json.toString(),
						SharedWebInfoResult.class);
				if (result.isSuccess()) {
					sharedWedInfoListener.onSuccess(result.getSharedWebInfor());
				} else {
					sharedWedInfoListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetSharedWebInfo", e.getMessage());
			sharedWedInfoListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetSharedWebInfo", e.getMessage());
			sharedWedInfoListener.onError("JSONException", e.getMessage());
		}
	}

	public void login(String userName, String pwd, String verifyCode) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new LoginWorker(userName, pwd, verifyCode));
		executor.shutdown();
	}

	private class LoginWorker implements Runnable {
		private String userName;
		private String password;
		private String verifyCode;

		public LoginWorker(String userName, String pwd, String verifyCode) {
			this.userName = userName;
			this.password = pwd;
			this.verifyCode = verifyCode;
		}

		@Override
		public void run() {
			doLogin(userName, password, verifyCode);
		}
	}

	private void doLogin(String userName, String pwd, String verifyCode) {
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			Gson gson = new Gson();
			JSONObject jsonObject = null;
			jsonObject = new JSONObject();
			jsonObject.put("mobile", userName);
			jsonObject.put("password", md5(pwd));
//			jsonObject.put("password", pwd);
			jsonObject.put("verifyCode", verifyCode);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(loginPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				LoginResult result = gson.fromJson(json.toString(),
						LoginResult.class);
				if (result.isSuccess()) {
					this.userName = userName;
					userId = result.getUser().getUserId();
					userType = result.getUser().getUserType();
					companyId = result.getUser().getCompanyId();
					loginInfo = result.getUserString();
					isLogin = true;
					loginListener.onSuccess(result.getMsg());
				} else {
					loginListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::login", e.getMessage());
			loginListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::Login", e.getMessage());
			loginListener.onError("JSONException", e.getMessage());
		}
	}

	public void VerifyCodeExpire(String mobile, String verifyCode) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new VerifyCodeExpireWorker(mobile, verifyCode));
		executor.shutdown();
	}

	public class VerifyCodeExpireWorker implements Runnable {
		String mobile;
		String verifyCode;

		public VerifyCodeExpireWorker(String mobile, String verifyCode) {
			super();
			this.mobile = mobile;
			this.verifyCode = verifyCode;
		}

		@Override
		public void run() {
			doVerifyCodeExpire(mobile, verifyCode);

		}

	}

	public void doVerifyCodeExpire(String mobile, String verifyCode) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			jsonObject.put("verifyCode", verifyCode);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(verifyCodeExpirePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					verifyCodeExpireListener.onSuccess(result.getMsg());
				} else {
					verifyCodeExpireListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::VerifyCodeExpire", e.getMessage());
			verifyCodeExpireListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::VerifyCodeExpire", e.getMessage());
			verifyCodeExpireListener.onError("JSONException", e.getMessage());
		}
	}

	public void resetPwd(String mobile, String pwd) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new ResetPwdWorker(mobile, pwd));
		executor.shutdown();
	}

	public class ResetPwdWorker implements Runnable {
		String mobile;
		String pwd;

		public ResetPwdWorker(String mobile, String pwd) {
			super();
			this.mobile = mobile;
			this.pwd = pwd;
		}

		@Override
		public void run() {
			doResetPwd(mobile, pwd);

		}

	}

	public void doResetPwd(String mobile, String pwd) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("mobile", mobile);
			jsonObject.put("password", md5(pwd));
			// jsonObject.put("password", pwd);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(resetPwdPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					resetPwdListener.onSuccess(result.getMsg());
				} else {
					resetPwdListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::ResetPwd", e.getMessage());
			resetPwdListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::ResetPwd", e.getMessage());
			resetPwdListener.onError("JSONException", e.getMessage());
		}
	}

	public void modifyPwd(String pwdNew, String pwdOld) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new ModifyPwdWorker(pwdNew, pwdOld));
		executor.shutdown();
	}

	public class ModifyPwdWorker implements Runnable {
		String pwdNew;
		String pwdOld;

		public ModifyPwdWorker(String pwdNew, String pwdOld) {
			super();
			this.pwdNew = pwdNew;
			this.pwdOld = pwdOld;
		}

		@Override
		public void run() {
			doModifyPwd(pwdNew, pwdOld);

		}

	}

	public void doModifyPwd(String pwdNew, String pwdOld) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("password", md5(pwdNew));
			jsonObject.put("oldPassword", md5(pwdOld));
			// jsonObject.put("userId", userId);
			// jsonObject.put("password", pwdNew);
			// jsonObject.put("oldPassword", pwdOld);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(modifyPwdPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					modifyPwdListener.onSuccess(result.getMsg());
				} else {
					modifyPwdListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::ModifyPwd", e.getMessage());
			modifyPwdListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::ModifyPwd", e.getMessage());
			modifyPwdListener.onError("JSONException", e.getMessage());
		}
	}

	public void SubscribeVerify(String cardNo, String cardPassword,
			int bookServerId) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new SubscribeVerifyWorker(cardNo, cardPassword,
				bookServerId));
		executor.shutdown();
	}

	public class SubscribeVerifyWorker implements Runnable {
		String cardNo;
		String cardPassword;
		int bookServerId;

		public SubscribeVerifyWorker(String cardNo, String cardPassword,
				int bookServerId) {
			super();
			this.cardNo = cardNo;
			this.cardPassword = cardPassword;
			this.bookServerId = bookServerId;
		}

		@Override
		public void run() {
			doSubscribeVerify(cardNo, cardPassword, bookServerId);

		}

	}

	public void doSubscribeVerify(String cardNo, String cardPassword,
			int bookServerId) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("cardNo", cardNo);
			jsonObject.put("cardPassword", cardPassword);
			jsonObject.put("bookServerId", bookServerId);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(subscribeVerifyPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				SubscribeVerifyResult result = gson.fromJson(json.toString(),
						SubscribeVerifyResult.class);
				if (result.isSuccess()) {
					subscribeVerifyListener.onSuccess(result
							.getSubscribeVerify());
				} else {
					subscribeVerifyListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::SubscribeVerify", e.getMessage());
			subscribeVerifyListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::SubscribeVerify", e.getMessage());
			subscribeVerifyListener.onError("JSONException", e.getMessage());
		}
	}

	public void getOrderList(PackedOrderRequest orderRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetOrderListWorker(orderRequest));
		executor.shutdown();
	}

	public class GetOrderListWorker implements Runnable {
		PackedOrderRequest orderRequest;

		public GetOrderListWorker(PackedOrderRequest orderRequest) {
			super();
			this.orderRequest = orderRequest;
		}

		@Override
		public void run() {
			doGetOrderList(orderRequest);

		}

	}

	public void doGetOrderList(PackedOrderRequest orderRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson.toJson(orderRequest)));
		try {
			Response response = HttpClientUtil.doPost(orderListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				OrderListResult result = gson.fromJson(json.toString(),
						OrderListResult.class);
				if (result.isSuccess()) {
					orderlistListener.onSuccess(result.getOrderList());
				} else {
					orderlistListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetOrderList", e.getMessage());
			orderlistListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetOrderList", e.getMessage());
			orderlistListener.onError("JSONException", e.getMessage());
		}
	}

	public void getOrgList(PackedOrgRequest orgRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetOrgListWorker(orgRequest));
		executor.shutdown();
	}

	public class GetOrgListWorker implements Runnable {
		PackedOrgRequest orgRequest;

		public GetOrgListWorker(PackedOrgRequest orgRequest) {
			super();
			this.orgRequest = orgRequest;
		}

		@Override
		public void run() {
			doGetOrgList(orgRequest);

		}

	}

	public void doGetOrgList(PackedOrgRequest orgRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson.toJson(orgRequest)));
		try {
			Response response = HttpClientUtil.doPost(organizationListPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				OrganizationListResult result = gson.fromJson(json.toString(),
						OrganizationListResult.class);
				if (result.isSuccess()) {
					organizationListListener.onSuccess(result
							.getOrganizations());
				} else {
					organizationListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetOrgList", e.getMessage());
			organizationListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetOrgList", e.getMessage());
			organizationListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getOrganizationDetail(int id, int pageNo, int pageSize,
			String refreshTime) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetOrganizationDetailWorker(id, pageNo, pageSize,
				refreshTime));
		executor.shutdown();
	}

	private class GetOrganizationDetailWorker implements Runnable {
		private int id;
		private int pageNo;
		private int pageSize;
		private String refreshTime;

		public GetOrganizationDetailWorker(int id, int pageNo, int pageSize,
				String refreshTime) {
			this.id = id;
			this.pageNo = pageNo;
			this.pageSize = pageSize;
			this.refreshTime = refreshTime;
		}

		@Override
		public void run() {
			doGetOrganizationDetail(id, pageNo, pageSize, refreshTime);
		}
	}

	private void doGetOrganizationDetail(int id, int pageNo, int pageSize,
			String refreshTime) {
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			Gson gson = new Gson();
			JSONObject jsonObject = null;
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			jsonObject.put("pageNo", pageNo);
			jsonObject.put("pageSize", pageSize);
			jsonObject.put("refreshTime", refreshTime);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(organizationDetailPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				OrganizationDetailResult result = gson.fromJson(
						json.toString(), OrganizationDetailResult.class);
				if (result.isSuccess()) {
					organizationDetailListener.onSuccess(result
							.getOrganizationDetail());
				} else {
					organizationDetailListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getOrganizationDetail", e.getMessage());
			organizationDetailListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getOrganizationDetail", e.getMessage());
			organizationDetailListener.onError("JSONException", e.getMessage());
		}
	}

	public void getOrderDetail(int id) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetOrderDetailWorker(id));
		executor.shutdown();
	}

	private class GetOrderDetailWorker implements Runnable {
		private int id;

		public GetOrderDetailWorker(int id) {
			this.id = id;

		}

		@Override
		public void run() {
			doGetOrderDetail(id);
		}
	}

	private void doGetOrderDetail(int id) {
		try {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			Gson gson = new Gson();
			JSONObject jsonObject = null;
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(orderDetailPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				OrderDetailResult result = gson.fromJson(json.toString(),
						OrderDetailResult.class);
				if (result.isSuccess()) {
					orderDetailListener.onSuccess(result.getOrderDetail());
				} else {
					orderDetailListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetOrderDetail", e.getMessage());
			orderDetailListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetOrderDetail", e.getMessage());
			orderDetailListener.onError("JSONException", e.getMessage());
		}
	}

	public void getMenuList(PackedMenuRequest packedMenuRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetMenuListWorker(packedMenuRequest));
		executor.shutdown();
	}

	public class GetMenuListWorker implements Runnable {
		PackedMenuRequest packedMenuRequest;

		public GetMenuListWorker(PackedMenuRequest packedMenuRequest) {
			super();
			this.packedMenuRequest = packedMenuRequest;
		}

		@Override
		public void run() {
			doGetMenuList(packedMenuRequest);

		}

	}

	public void doGetMenuList(PackedMenuRequest packedMenuRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson
				.toJson(packedMenuRequest)));
		try {
			Response response = HttpClientUtil.doPost(menuListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				MenuListResult result = gson.fromJson(json.toString(),
						MenuListResult.class);
				if (result.isSuccess()) {
					if (result.getMenus().size() != 0) {
						menuListListener.onSuccess(result.getMenus(), result
								.getMenus().get(0).isFamily());
					} else {
						menuListListener.onSuccess(result.getMenus(),
								packedMenuRequest.isFamily());
					}
				} else {
					menuListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getMenuList", e.getMessage());
			menuListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getMenuList", e.getMessage());
			menuListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getMenuDetail(int id, boolean isFamily, int pageNo,
			int pageSize, String refreshTime) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetMenuDetailWorker(id, isFamily, pageNo,
				pageSize, refreshTime));
		executor.shutdown();
	}

	private class GetMenuDetailWorker implements Runnable {
		private int id;
		private boolean isFamily;
		private int pageNo;
		private int pageSize;
		private String refreshTime;

		public GetMenuDetailWorker(int id, boolean isFamily, int pageNo,
				int pageSize, String refreshTime) {
			this.id = id;
			this.pageNo = pageNo;
			this.pageSize = pageSize;
			this.refreshTime = refreshTime;
			this.isFamily = isFamily;
		}

		@Override
		public void run() {
			doGetMenuDetail(id, isFamily, pageNo, pageSize, refreshTime);
		}
	}

	private void doGetMenuDetail(int id, boolean isFamily, int pageNo,
			int pageSize, String refreshTime) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("menuId", id);
			jsonObject.put("isFamily", isFamily);
			jsonObject.put("pageNo", pageNo);
			jsonObject.put("pageSize", pageSize);
			jsonObject.put("refreshTime", refreshTime);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(menuDetailPath, params,
					loginInfo);

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				MenuDetailResult result = gson.fromJson(json.toString(),
						MenuDetailResult.class);
				if (result.isSuccess()) {
					menuDetailListener.onSuccess(result.getMenuDetail());
				} else {
					menuDetailListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetMenuDetail", e.getMessage());
			menuDetailListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetMenuDetail", e.getMessage());
			menuDetailListener.onError("JSONException", e.getMessage());
		}
	}

	public void getCityList(String refreshTime) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetCityListWorker(refreshTime));
		executor.shutdown();
	}

	public class GetCityListWorker implements Runnable {
		String refreshTime;

		public GetCityListWorker(String refreshTime) {
			super();
			this.refreshTime = refreshTime;
		}

		@Override
		public void run() {
			doGetCityList(refreshTime);

		}

	}

	public void doGetCityList(String refreshTime) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("refreshTime", refreshTime);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(cityListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				CityResult result = gson.fromJson(json.toString(),
						CityResult.class);
				if (result.isSuccess()) {
					// if (refreshTimeCity.equals("")
					// || !refreshTimeCity.equals(result.getData()
					// .getRefreshTime())) {
					String refreshTimeFromServer = result.getData()
							.getRefreshTime();
					cityListListener.onSuccess(result.getData().getCityList(),
							refreshTimeFromServer);
					// } else {
					// Log.i("RemoteDataManager::GetCityList", "no new city");
					// }
				} else {
					cityListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetCityList", e.getMessage());
			cityListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetCityList", e.getMessage());
			cityListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getMenuType() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetMenuTypeWorker());
		executor.shutdown();
	}

	private class GetMenuTypeWorker implements Runnable {

		public GetMenuTypeWorker() {
		}

		@Override
		public void run() {
			doGetMenuType();
		}
	}

	private void doGetMenuType() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(menuTypePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				MenuTypeListResult result = gson.fromJson(json.toString(),
						MenuTypeListResult.class);
				if (result.isSuccess()) {
					menuTypeListListener.onSuccess(result.getMenuTypes());
				} else {
					menuTypeListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetMenuType", e.getMessage());
			menuTypeListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetMenuType", e.getMessage());
			menuTypeListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getBonusList() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetBonusListWorker());
		executor.shutdown();
	}

	private class GetBonusListWorker implements Runnable {

		public GetBonusListWorker() {
		}

		@Override
		public void run() {
			doGetBonusList();
		}
	}

	private void doGetBonusList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(bonusListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				BonusListResult result = gson.fromJson(json.toString(),
						BonusListResult.class);
				if (result.isSuccess()) {
					bonusListListener.onSuccess(result.getBonusList());
				} else {
					bonusListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBonusList", e.getMessage());
			bonusListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBonusList", e.getMessage());
			bonusListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getBonusUsedList() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetBonusUsedListWorker());
		executor.shutdown();
	}

	private class GetBonusUsedListWorker implements Runnable {

		public GetBonusUsedListWorker() {
		}

		@Override
		public void run() {
			doGetBonusUsedList();
		}
	}

	private void doGetBonusUsedList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(bonusUsedListPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				BonusUsedListResult result = gson.fromJson(json.toString(),
						BonusUsedListResult.class);
				if (result.isSuccess()) {
					bonusUsedListListener.onSuccess(result.getBonusUsed());
				} else {
					bonusUsedListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBonusUsedList", e.getMessage());
			bonusUsedListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBonusUsedList", e.getMessage());
			bonusUsedListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getOrgBrand() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetOrgBrandWorker());
		executor.shutdown();
	}

	private class GetOrgBrandWorker implements Runnable {

		public GetOrgBrandWorker() {
		}

		@Override
		public void run() {
			doGetOrgBrand();
		}
	}

	private void doGetOrgBrand() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(orgBrandPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				OrgBrandListResult result = gson.fromJson(json.toString(),
						OrgBrandListResult.class);
				if (result.isSuccess()) {
					orgBrandListener.onSuccess(result.getBrands());
				} else {
					orgBrandListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetOrgBrand", e.getMessage());
			orgBrandListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetOrgBrand", e.getMessage());
			orgBrandListener.onError("JSONException", e.getMessage());
		}
	}

	public void getCartCount() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetCartCount());
		executor.shutdown();
	}

	private class GetCartCount implements Runnable {

		public GetCartCount() {
		}

		@Override
		public void run() {
			doGetCartCount();
		}
	}

	private void doGetCartCount() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(cartCountPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				CartCountResult result = gson.fromJson(json.toString(),
						CartCountResult.class);
				if (result.isSuccess()) {
					cartCount = result.getCartCount().getNumber();
					cartCountListener.onSuccess(result.getMsg());
				} else {
					cartCountListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetCartCount", e.getMessage());
			cartCountListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetCartCount", e.getMessage());
			cartCountListener.onError("JSONException", e.getMessage());
		}
	}

	public void addToCart(int menuId, boolean isFamily, int number) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new AddToCart(menuId, isFamily, number));
		executor.shutdown();
	}

	private class AddToCart implements Runnable {
		int menuId;
		boolean isFamily;
		int number;

		public AddToCart(int menuId, boolean isFamily, int number) {
			super();
			this.menuId = menuId;
			this.isFamily = isFamily;
			this.number = number;
		}

		@Override
		public void run() {
			doAddToCart(menuId, isFamily, number);
		}
	}

	private void doAddToCart(int menuId, boolean isFamily, int number) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("menuId", menuId);
			jsonObject.put("isFamily", isFamily);
			jsonObject.put("number", number);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(addToCartPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				CartCountResult result = gson.fromJson(json.toString(),
						CartCountResult.class);
				if (result.isSuccess()) {
					cartCount = result.getCartCount().getNumber();
					cartCountListener.onSuccess(result.getMsg());
				} else {
					cartCountListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::AddToCart", e.getMessage());
			cartCountListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::AddToCart", e.getMessage());
			cartCountListener.onError("JSONException", e.getMessage());
		}
	}

	public void deleteCart(PackedDeleteCartRequest packedDeleteCartRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new DeleteCart(packedDeleteCartRequest));
		executor.shutdown();
	}

	private class DeleteCart implements Runnable {
		PackedDeleteCartRequest packedDeleteCartRequest;

		public DeleteCart(PackedDeleteCartRequest packedDeleteCartRequest) {
			super();
			this.packedDeleteCartRequest = packedDeleteCartRequest;
		}

		@Override
		public void run() {
			doDeleteCart(packedDeleteCartRequest);
		}
	}

	private void doDeleteCart(PackedDeleteCartRequest packedDeleteCartRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {
			params.add(new BasicNameValuePair("param", gson
					.toJson(packedDeleteCartRequest)));
			Response response = HttpClientUtil.doPost(deleteCartPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				CartCountResult result = gson.fromJson(json.toString(),
						CartCountResult.class);
				if (result.isSuccess()) {
					cartCount = result.getCartCount().getNumber();
					cartCountListener.onSuccess(result.getMsg());
				} else {
					cartCountListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::DeleteCart", e.getMessage());
			cartCountListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::DeleteCart", e.getMessage());
			cartCountListener.onError("JSONException", e.getMessage());
		}
	}

	public void getCartList() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetCartList());
		executor.shutdown();
	}

	private class GetCartList implements Runnable {

		public GetCartList() {
			super();
		}

		@Override
		public void run() {
			doGetCartList();
		}
	}

	private void doGetCartList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {
			Response response = HttpClientUtil.doPost(cartListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				CartListResult result = gson.fromJson(json.toString(),
						CartListResult.class);
				if (result.isSuccess()) {
					cartListListener.onSuccess(result.getCartDetails());
				} else {
					cartListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::GetCartList", e.getMessage());
			cartListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::GetCartList", e.getMessage());
			cartListListener.onError("JSONException", e.getMessage());
		}
	}

	public void subscribeFullDays(int examType, int orgId, String startTime,
			String endTime) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new SubscribeFullDays(examType, orgId, startTime,
				endTime));
		executor.shutdown();
	}

	private class SubscribeFullDays implements Runnable {
		int examType;
		int orgId;
		String startTime;
		String endTime;

		public SubscribeFullDays(int examType, int orgId, String startTime,
				String endTime) {
			super();
			this.examType = examType;
			this.orgId = orgId;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@Override
		public void run() {
			getSubscribeFullDays(examType, orgId, startTime, endTime);
		}
	}

	private void getSubscribeFullDays(int examType, int orgId,
			String startTime, String endTime) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("orgId", orgId);
			jsonObject.put("startTime", startTime);
			jsonObject.put("endTime", endTime);
			jsonObject.put("examType", examType);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(subscribeFullDays,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				SubscribeFullDay result = gson.fromJson(json.toString(),
						SubscribeFullDay.class);
				if (result.isSuccess()) {
					subscribeFullDaysListener.onSuccess(result.isSuccess(),
							result.getDate());
				} else {
					subscribeFullDaysListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::SubscribeFullDays", e.getMessage());
			subscribeFullDaysListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::SubscribeFullDays", e.getMessage());
			subscribeFullDaysListener.onError("JSONException", e.getMessage());
		}
	}

	public void getMyMenus() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new MyMenusWorker());
		executor.shutdown();
	}

	private class MyMenusWorker implements Runnable {

		public MyMenusWorker() {
			super();
		}

		@Override
		public void run() {
			doGetMyMenus();
		}
	}

	private void doGetMyMenus() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {
			Response response = HttpClientUtil.doPost(myMenusPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				MenuListResult result = gson.fromJson(json.toString(),
						MenuListResult.class);
				if (result.isSuccess()) {
					myMenusListener.onSuccess(result.getMenus());
				} else {
					myMenusListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getMyMenu", e.getMessage());
			myMenusListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getMyMenu", e.getMessage());
			myMenusListener.onError("JSONException", e.getMessage());
		}
	}

	public void getSubscribeRecord() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new SubscribeRecordWorker());
		executor.shutdown();
	}

	private class SubscribeRecordWorker implements Runnable {
		public SubscribeRecordWorker() {
			super();
		}

		@Override
		public void run() {
			doGetSubscribeRecord();
		}
	}

	private void doGetSubscribeRecord() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {
			Response response = HttpClientUtil.doPost(subscribedMenuPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				SubscribeRecordList result = gson.fromJson(json.toString(),
						SubscribeRecordList.class);
				if (result.isSuccess()) {
					subscribeRecordListListener.onSuccess(result.getRecords());
				} else {
					subscribeRecordListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getSubscribeRecord", e.getMessage());
			subscribeRecordListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getSubscribeRecord", e.getMessage());
			subscribeRecordListListener
					.onError("JSONException", e.getMessage());
		}
	}

	public void updateCart(PackedUpdateCartRequest packedUpdateCartRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new UpdateCartWorker(packedUpdateCartRequest));
		executor.shutdown();
	}

	public class UpdateCartWorker implements Runnable {
		PackedUpdateCartRequest packedUpdateCartRequest;

		public UpdateCartWorker(PackedUpdateCartRequest packedUpdateCartRequest) {
			super();
			this.packedUpdateCartRequest = packedUpdateCartRequest;
		}

		@Override
		public void run() {
			doUpdateCart(packedUpdateCartRequest);

		}

	}

	public void doUpdateCart(PackedUpdateCartRequest packedUpdateCartRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson
				.toJson(packedUpdateCartRequest)));
		try {
			Response response = HttpClientUtil.doPost(cartUpdatePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				CartCountResult result = gson.fromJson(json.toString(),
						CartCountResult.class);
				if (result.isSuccess()) {
					cartCount = result.getCartCount().getNumber();
					cartCountListener.onSuccess(result.getMsg());
				} else {
					cartCountListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::UpdateCart", e.getMessage());
			cartCountListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::UpdateCart", e.getMessage());
			cartCountListener.onError("JSONException", e.getMessage());
		}
	}

	public void buyNow(PackedBuyNowRequest packedBuyNowRequest) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BuyNowWorker(packedBuyNowRequest));
		executor.shutdown();
	}

	public class BuyNowWorker implements Runnable {
		PackedBuyNowRequest packedBuyNowRequest;

		public BuyNowWorker(PackedBuyNowRequest packedBuyNowRequest) {
			super();
			this.packedBuyNowRequest = packedBuyNowRequest;
		}

		@Override
		public void run() {
			doBuyNow(packedBuyNowRequest);

		}

	}

	public void doBuyNow(PackedBuyNowRequest packedBuyNowRequest) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson
				.toJson(packedBuyNowRequest)));
		try {
			Response response = HttpClientUtil.doPost(buyNowPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				BuyDetailResult result = gson.fromJson(json.toString(),
						BuyDetailResult.class);
				if (result.isSuccess()) {
					mBuyDetail = result.getBuyDetail();
					buyNowListener.onSuccess(result);
				} else {
					buyNowListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::BuyNow", e.getMessage());
			buyNowListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::BuyNow", e.getMessage());
			buyNowListener.onError("JSONException", e.getMessage());
		}
	}

	public void cancelOrder(int orderId) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new cancelOrderWorker(orderId));
		executor.shutdown();
	}

	public class cancelOrderWorker implements Runnable {
		int orderId;

		public cancelOrderWorker(int orderId) {
			super();
			this.orderId = orderId;
		}

		@Override
		public void run() {
			doCancelOrder(orderId);

		}

	}

	public void doCancelOrder(int orderId) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", orderId);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(cancelOrderPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					cancelOrderListener.onSuccess(result.getMsg());
				} else {
					cancelOrderListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::CancelOrder", e.getMessage());
			cancelOrderListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::CancelOrder", e.getMessage());
			cancelOrderListener.onError("JSONException", e.getMessage());
		}
	}

	public void getMallData(String cityCode) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new MallDataWorker(cityCode));
		executor.shutdown();
	}

	private class MallDataWorker implements Runnable {
		String cityCode;

		public MallDataWorker(String cityCode) {
			super();
			this.cityCode = cityCode;
		}

		@Override
		public void run() {
			doGetMallData(cityCode);
		}
	}

	private void doGetMallData(String cityCode) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("cityCode", cityCode);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(mallDataPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				MallDataResult result = gson.fromJson(json.toString(),
						MallDataResult.class);
				if (result.isSuccess()) {
					mallDataListener.onSuccess(result.getMallData());
				} else {
					mallDataListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getMallData", e.getMessage());
			mallDataListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getMallData", e.getMessage());
			mallDataListener.onError("JSONException", e.getMessage());
		}
	}

	public <T> boolean addObject(Object object) {
		return true;
	}

	public String getLoginInfo() {
		return this.loginInfo;
	}

	public void paySuccess(String orderNo, int payType, int payChannel) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new PaySuccessWorker(orderNo, payType, payChannel));
		executor.shutdown();
	}

	public class PaySuccessWorker implements Runnable {
		private String orderNo;
		private int payType;
		private int payChannel;

		public PaySuccessWorker(String orderNo, int payType, int payChannel) {
			super();
			this.orderNo = orderNo;
			this.payType = payType;
			this.payChannel = payChannel;
		}

		@Override
		public void run() {
			doPaySuccess(orderNo, payType, payChannel);

		}

	}

	public void doPaySuccess(String orderNo, int payType, int payChannel) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("orderNo", orderNo);
			// jsonObject.put("bonusMoney", bonusMoney);
			jsonObject.put("payType", payType);
			jsonObject.put("payChannel", payChannel);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(paySuccessPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				PaySuccessResult result = gson.fromJson(json.toString(),
						PaySuccessResult.class);
				if (result.isSuccess()) {
					paySuccessListener.onSuccess(result.getPaySuccess());
				} else {
					paySuccessListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::PaySuccess ", e.getMessage());
			paySuccessListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::PaySuccess", e.getMessage());
			paySuccessListener.onError("JSONException", e.getMessage());
		}
	}

	public void checkInteraction() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new CheckInteractionWorker());
		executor.shutdown();
	}

	private class CheckInteractionWorker implements Runnable {
		public CheckInteractionWorker() {
			super();
		}

		@Override
		public void run() {
			doCheckInteraction();
		}
	}

	private void doCheckInteraction() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(interactionCheckPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				CheckInteractionResult result = gson.fromJson(json.toString(),
						CheckInteractionResult.class);
				if (result.isSuccess()) {
					if (result.getInteractionStatus().size() != 0)
						checkInteractionListener.onSuccess(result
								.getInteractionStatus().get(0));
				} else {
					checkInteractionListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doCheckInteraction", e.getMessage());
			checkInteractionListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doCheckInteraction", e.getMessage());
			checkInteractionListener.onError("JSONException", e.getMessage());
		}
	}

	public void getAvailableBonus(int totalPrice) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetAvailableBonusWorker(totalPrice));
		executor.shutdown();
	}

	public class GetAvailableBonusWorker implements Runnable {
		int totalPrice;

		public GetAvailableBonusWorker(int totalPrice) {
			super();
			this.totalPrice = totalPrice;
		}

		@Override
		public void run() {
			doGetAvailableBonus(totalPrice);
		}

	}

	public void doGetAvailableBonus(int totalPrice) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("totalPrice", totalPrice);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bonusAvailablePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				BonusAvailableResult result = gson.fromJson(json.toString(),
						BonusAvailableResult.class);
				if (result.isSuccess()) {
					bonusAvailableListener
							.onSuccess(result.getBonusAvailable());
				} else {
					bonusAvailableListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetAvailableBonus", e.getMessage());
			bonusAvailableListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetAvailableBonus", e.getMessage());
			bonusAvailableListener.onError("JSONException", e.getMessage());
		}
	}

	public void getHealthHome() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetHealthHomeWorker());
		executor.shutdown();
	}

	private class GetHealthHomeWorker implements Runnable {
		public GetHealthHomeWorker() {
			super();
		}

		@Override
		public void run() {
			doGetHealthHome();
		}
	}

	private void doGetHealthHome() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(healthHomePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				HealthHomeResult result = gson.fromJson(json.toString(),
						HealthHomeResult.class);
				if (result.isSuccess()) {
					healthHomeListener.onSuccess(result.getHealthHome());
				} else {
					healthHomeListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetHealthHome", e.getMessage());
			healthHomeListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetHealthHome", e.getMessage());
			healthHomeListener.onError("JSONException", e.getMessage());
		}
	}

	public void getHealthHomeBooklet(int pageNo, int pageSize, String version,
			int source) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new GetHealthHomeBookletWorker(pageNo, pageSize,
				version, source));
		executor.shutdown();
	}

	public class GetHealthHomeBookletWorker implements Runnable {
		int pageNo;
		int pageSize;
		String version;
		int source;

		public GetHealthHomeBookletWorker(int pageNo, int pageSize,
				String version, int source) {
			super();
			this.pageNo = pageNo;
			this.pageSize = pageSize;
			this.version = version;
			this.source = source;
		}

		@Override
		public void run() {
			doGetHealthHomeBooklet(pageNo, pageSize, version, source);
		}
	}

	public void doGetHealthHomeBooklet(int pageNo, int pageSize,
			String version, int source) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("pageNo", pageNo);
			jsonObject.put("pageSize", pageSize);
			jsonObject.put("version", version);
			jsonObject.put("source", source);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(
					healthHomeBookletListPath, params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				HealthHomeBookletResult result = gson.fromJson(json.toString(),
						HealthHomeBookletResult.class);
				if (result.isSuccess()) {
					healthHomeBookletListener.onSuccess(result
							.getHealthHomeBookletList());
				} else {
					healthHomeBookletListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetHealthHomeBooklet", e.getMessage());
			healthHomeBookletListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetHealthHomeBooklet", e.getMessage());
			healthHomeBookletListener.onError("JSONException", e.getMessage());
		}
	}

	public void editHealthUser(String name, int gender, String bornDate,
			float height, byte[] imgHead) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new EditHealthUserWorker(name, gender, bornDate,
				height, imgHead));
		executor.shutdown();
	}

	public class EditHealthUserWorker implements Runnable {
		String name;
		int gender;
		String bornDate;
		float height;
		byte[] imgHead;

		public EditHealthUserWorker(String name, int gender, String bornDate,
				float height, byte[] imgHead) {
			super();
			this.name = name;
			this.gender = gender;
			this.bornDate = bornDate;
			this.height = height;
			this.imgHead = imgHead;
		}

		@Override
		public void run() {
			doHealthUserEdit(name, gender, bornDate, height, imgHead);
		}
	}

	public void doHealthUserEdit(String name, int gender, String bornDate,
			float height, byte[] headImg) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("gender", gender);
			jsonObject.put("bornDate", bornDate);
			jsonObject.put("height", height);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doImagePost(SERVICE_NORMAL,
					healthUserEditPath, params, headImg, "headImg.jpg",
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					healthUserEditListener.onSuccess(result.getMsg());
				} else {
					healthUserEditListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doHealthUserEdit", e.getMessage());
			healthUserEditListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doHealthUserEdit", e.getMessage());
			healthUserEditListener.onError("JSONException", e.getMessage());
		}
	}

	// TODO
	public void getBookletItem() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BookletItemWorker());
		executor.shutdown();
	}

	public class BookletItemWorker implements Runnable {

		public BookletItemWorker() {
			super();
		}

		@Override
		public void run() {
			doGetBookletItem();
		}
	}

	public void doGetBookletItem() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {
			Response response = HttpClientUtil.doPost(bookletListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				BookletItemResult result = gson.fromJson(json.toString(),
						BookletItemResult.class);
				if (result.isSuccess()) {
					bookletItemListener.onSuccess(result.getBookletItemList());
				} else {
					bookletItemListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBookletItem", e.getMessage());
			bookletItemListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBookletItem", e.getMessage());
			bookletItemListener.onError("JSONException", e.getMessage());
		}
	}

	public void getBookletDetail(int id) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BookletDetailWorker(id));
		executor.shutdown();
	}

	public class BookletDetailWorker implements Runnable {
		int id;

		public BookletDetailWorker(int id) {
			super();
			this.id = id;
		}

		@Override
		public void run() {
			doGetBookletDetail(id);
		}
	}

	public void doGetBookletDetail(int id) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookletDetailPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				BookletDetailResult result = gson.fromJson(json.toString(),
						BookletDetailResult.class);
				if (result.isSuccess()) {
					bookletDetailListener.onSuccess(result
							.getBookletDetailItem());
				} else {
					bookletDetailListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBookletDetail", e.getMessage());
			bookletDetailListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBookletDetail", e.getMessage());
			bookletDetailListener.onError("JSONException", e.getMessage());
		}
	}

	public void getBookletType(int id, int type) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BookletTypeWorker(id, type));
		executor.shutdown();
	}

	public class BookletTypeWorker implements Runnable {
		int id;
		int type;

		public BookletTypeWorker(int id, int type) {
			super();
			this.id = id;
			this.type = type;
		}

		@Override
		public void run() {
			doGetBookletType(id, type);
		}
	}

	public void doGetBookletType(int id, int type) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			jsonObject.put("type", type);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookletTypePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				BookletTypeResult result = gson.fromJson(json.toString(),
						BookletTypeResult.class);
				if (result.isSuccess()) {
					bookletTypeListener.onSuccess(result.getBookletTypeList());
				} else {
					bookletTypeListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBookletType", e.getMessage());
			bookletTypeListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBookletType", e.getMessage());
			bookletTypeListener.onError("JSONException", e.getMessage());
		}
	}

	public void getBookData(int id) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BookDataWorker(id));
		executor.shutdown();
	}

	public class BookDataWorker implements Runnable {
		int id;

		public BookDataWorker(int id) {
			super();
			this.id = id;
		}

		@Override
		public void run() {
			doGetBookDataList(id);
		}
	}

	public void doGetBookDataList(int id) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookDataPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				BookDataResult result = gson.fromJson(json.toString(),
						BookDataResult.class);
				if (result.isSuccess()) {
					bookDataListener.onSuccess(result.getDataList());
				} else {
					bookDataListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetBookDataList", e.getMessage());
			bookDataListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetBookDataList", e.getMessage());
			bookDataListener.onError("JSONException", e.getMessage());
		}
	}

	public void deleteBooklet(int bookletId) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new deleteBookletWork(bookletId));
		executor.shutdown();
	}

	public class deleteBookletWork implements Runnable {
		private int bookletId;

		public deleteBookletWork(int bookletId) {
			this.bookletId = bookletId;
		}

		@Override
		public void run() {
			doDeleteBooklet(bookletId);
		}
	}

	public void doDeleteBooklet(int bookletId) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("bookletId", bookletId);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookletDeletePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strReusult = response.getContent();
				JSONObject json = new JSONObject(strReusult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					bookletDeleteListener.onSuccess(result.getMsg());
				} else {
					bookletDeleteListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (JSONException e) {

			Log.e("RemoteDataManager::doDeleteBooklet", e.getMessage());
			bookletDeleteListener.onError("JSONException", e.getMessage());
		} catch (NetException e) {

			Log.e("RemoteDataManager::doDeleteBooklet", e.getMessage());
			bookletDeleteListener.onError("NetException", e.getMessage());
		}
	}

	public void showBookletSwitch(int id, int isShow) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new BookletSwitchWork(id, isShow));
		executor.shutdown();
	}

	public class BookletSwitchWork implements Runnable {
		private int id, isShow;

		public BookletSwitchWork(int id, int isShow) {
			this.id = id;
			this.isShow = isShow;
		}

		@Override
		public void run() {
			doShowBookletSwitch(id, isShow);
		}
	}

	public void doShowBookletSwitch(int id, int isShow) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("isShow", isShow);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookletSwitchPath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					bookletSwitchListener.onSuccess(result.getMsg());
				} else {
					bookletSwitchListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doShowBookletSwitch", e.getMessage());
			bookletSwitchListener.onError("JSONException", e.getMessage());
		} catch (NetException e) {
			Log.e("RemoteDataManager::doShowBookletSwitch", e.getMessage());
			bookletSwitchListener.onError("NetException", e.getMessage());
		}
	}

	public void updateBooklet(int id, int bookletId, float value,
			String dataTime) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new updateBookletWork(id, bookletId, value, dataTime));
		executor.shutdown();
	}

	public class updateBookletWork implements Runnable {
		private int id;
		private int bookletId;
		private float value;
		private String dataTime;

		public updateBookletWork(int id, int bookletId, float value,
				String dataTime) {
			this.id = id;
			this.bookletId = bookletId;
			this.value = value;
			this.dataTime = dataTime;
		}

		@Override
		public void run() {
			doUpdateBooklet(id, bookletId, value, dataTime);
		}
	}

	public void doUpdateBooklet(int id, int bookletId, float value,
			String dataTime) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			jsonObject.put("bookletId", bookletId);
			jsonObject.put("value", value);
			jsonObject.put("dataTime", dataTime);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(bookletUpdatePath,
					params, loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strReusult = response.getContent();
				JSONObject json = new JSONObject(strReusult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					bookletUpdateListener.onSuccess(result.getMsg());
				} else {
					bookletUpdateListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (JSONException e) {

			Log.e("RemoteDataManager::doUpdateBooklet", e.getMessage());
			bookletUpdateListener.onError("JSONException", e.getMessage());
		} catch (NetException e) {

			Log.e("RemoteDataManager::doUpdateBooklet", e.getMessage());
			bookletUpdateListener.onError("NetException", e.getMessage());
		}
	}

	public void doAddBooklet(BookletAdd bookletAdd) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new addBookletWorker(bookletAdd));
		executor.shutdown();
	}

	public class addBookletWorker implements Runnable {
		BookletAdd bookletAdd;

		public addBookletWorker(BookletAdd bookletAdd) {
			super();
			this.bookletAdd = bookletAdd;
		}

		@Override
		public void run() {
			addBooklet(bookletAdd);
		}
	}

	public void addBooklet(BookletAdd bookletAdd) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		try {

			params.add(new BasicNameValuePair("param", gson.toJson(bookletAdd)));
			Response response = HttpClientUtil.doPost(bookletAddPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					bookletAddListener.onSuccess(result.getMsg());
				} else {
					bookletAddListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::addBooklet", e.getMessage());
			bookletAddListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::addBooklet", e.getMessage());
			bookletAddListener.onError("JSONException", e.getMessage());
		}
	}

	public void getReportList() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new ReportListWorker());
		executor.shutdown();
	}

	private class ReportListWorker implements Runnable {
		public ReportListWorker() {
			super();
		}

		@Override
		public void run() {
			doGetReportList();
		}
	}

	private void doGetReportList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			Response response = HttpClientUtil.doPost(reportListPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ReportListResult result = gson.fromJson(json.toString(),
						ReportListResult.class);
				if (result.isSuccess()) {
					reportListListener.onSuccess(result.getExamReportList());
				} else {
					reportListListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::doGetReportList", e.getMessage());
			reportListListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::doGetReportList", e.getMessage());
			reportListListener.onError("JSONException", e.getMessage());
		}
	}

	public void getExamReportDetail(int id) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new getDetialWorker(id));
		executor.shutdown();
	}

	public class getDetialWorker implements Runnable {
		int id;

		public getDetialWorker(int id) {
			super();
			this.id = id;
		}

		@Override
		public void run() {
			getReportDetail(id);
		}
	}

	public void getReportDetail(int id) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(reportDetailPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ReportDetailResult result = gson.fromJson(json.toString(),
						ReportDetailResult.class);
				if (result.isSuccess()) {
					reportDetailListener
							.onSuccess(result.getExamReportDetail());
				} else {
					reportDetailListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::getReportDetail", e.getMessage());
			reportDetailListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::getReportDetail", e.getMessage());
			reportDetailListener.onError("JSONException", e.getMessage());
		}
	}

	// TODO

	public void doAddReport(String name, String orgName, String examTime,
			int src, ArrayList<byte[]> attachments) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new AddReportWorker(name, orgName, examTime, src,
				attachments));
		executor.shutdown();
	}

	public class AddReportWorker implements Runnable {
		String name;
		String orgName;
		String examTime;
		int src;
		ArrayList<byte[]> attachments;

		public AddReportWorker(String name, String orgName, String examTime,
				int src, ArrayList<byte[]> attachments) {
			super();
			this.name = name;
			this.orgName = orgName;
			this.src = src;
			this.examTime = examTime;
			this.attachments = attachments;
		}

		@Override
		public void run() {
			addReport(name, orgName, examTime, src, attachments);
		}
	}

	public void addReport(String name, String orgName, String examTime,
			int src, ArrayList<byte[]> attachments) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("orgName", orgName);
			jsonObject.put("examTime", examTime);
			jsonObject.put("src", src);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doReportPost(SERVICE_NORMAL,
					reportAddPath, params, attachments, "attachments.jpg",
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					reportAddListener.onSuccess(result.getMsg());
				} else {
					reportAddListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::addReport", e.getMessage());
			reportAddListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::addReport", e.getMessage());
			reportAddListener.onError("JSONException", e.getMessage());
		}
	}

	public void doUpdateExamReport(ExamReportUpdate examReportUpdate,
			ArrayList<byte[]> attachments) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new UpdateExamReportWorker(examReportUpdate,
				attachments));
		executor.shutdown();
	}

	public class UpdateExamReportWorker implements Runnable {
		private ExamReportUpdate examReportUpdate;
		private ArrayList<byte[]> attachments;

		public UpdateExamReportWorker(ExamReportUpdate examReportUpdate,
				ArrayList<byte[]> attachments) {
			super();
			this.examReportUpdate = examReportUpdate;
			this.attachments = attachments;
		}

		@Override
		public void run() {
			updateExamReport(examReportUpdate, attachments);
		}
	}

	public void updateExamReport(ExamReportUpdate examReportUpdate,
			ArrayList<byte[]> attachments) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		Gson gson = new Gson();
		params.add(new BasicNameValuePair("param", gson
				.toJson(examReportUpdate)));
		try {
			Response response = HttpClientUtil.doReportPost(SERVICE_NORMAL,
					reportUpdatePath, params, attachments, "attachments.jpg",
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					reportUpdateListener.onSuccess(result.getMsg());
				} else {
					reportUpdateListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::updateExamReport", e.getMessage());
			reportUpdateListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::updateExamReport", e.getMessage());
			reportUpdateListener.onError("JSONException", e.getMessage());
		}
	}

	public void doReportDelete(int id) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new reportDeleteWorker(id));
		executor.shutdown();
	}

	public class reportDeleteWorker implements Runnable {
		int id;

		public reportDeleteWorker(int id) {
			super();
			this.id = id;
		}

		@Override
		public void run() {
			deleteReportDetail(id);
		}
	}

	public void deleteReportDetail(int id) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("id", id);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(reportDeletePath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				ResponseResult result = gson.fromJson(json.toString(),
						ResponseResult.class);
				if (result.isSuccess()) {
					reportDeleteListener.onSuccess(result.getMsg());
				} else {
					reportDeleteListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			}
		} catch (NetException e) {
			Log.e("RemoteDataManager::examReportDeleteListener", e.getMessage());
			reportDeleteListener.onError("NetException", e.getMessage());
		} catch (JSONException e) {
			Log.e("RemoteDataManager::examReportDeleteListener", e.getMessage());
			reportDeleteListener.onError("JSONException", e.getMessage());
		}
	}

	// 计步器页面上传步数等信息及获得返回信息
	public void UploadStep(int userId, int daystep, float kilometre) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new doUploadStepWorker(userId, daystep, kilometre));
		executor.shutdown();
	}

	public class doUploadStepWorker implements Runnable {
		int userId, daystep;
		float kilometre;

		public doUploadStepWorker(int userId, int daystep, float kilometre) {
			super();
			this.userId = userId;
			this.daystep = daystep;
			this.kilometre = kilometre;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			doUploadStep(userId, daystep, kilometre);
		}

	}

	public void doUploadStep(int userId, int daystep, float kilometre) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("userId", userId);
			jsonObject.put("daystep", daystep);
			jsonObject.put("kilometre", kilometre);
			params.add(new BasicNameValuePair("param", jsonObject.toString()));
			Response response = HttpClientUtil.doPost(uploadstepPath, params,
					loginInfo);
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				String strResult = response.getContent();
				JSONObject json = new JSONObject(strResult)
						.getJSONObject("result");
				Gson gson = new Gson();
				UploadStepResponse result = gson.fromJson(json.toString(),
						UploadStepResponse.class);
				if (result.isSuccess()) {
					uploadListener.onSuccess(result.getStepdata());
				} else {
					uploadListener.onError(result.getErrorCode(),
							result.getMsg());
				}
			} else {
				System.out.println("uploadstep failed");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NetException e) {
			e.printStackTrace();
		}
	}

	
	// 礼品页面获得返回信息
		public void StepGiftDetail(int userId) {
			ExecutorService executor = Executors.newFixedThreadPool(5);
			executor.execute(new StepGiftWorker(userId));
			executor.shutdown();
		}
		public class StepGiftWorker implements Runnable {
			int userId;

			public StepGiftWorker(int userId) {
				super();
				this.userId = userId;
			}

			@Override
			public void run() {
				doStepGiftDetail();
			}
		}
		public void doStepGiftDetail(){
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject();
				jsonObject.put("id", 1);
				params.add(new BasicNameValuePair("param", jsonObject.toString()));
				Response response = HttpClientUtil.doPost(stepgiftPath, params,
						loginInfo);
				if (response.getStatusCode() == HttpStatus.SC_OK) {
					String strResult = response.getContent();
					JSONObject json = new JSONObject(strResult)
							.getJSONObject("result");
					Gson gson = new Gson();
					StepGiftDetailtResponse result = gson.fromJson(json.toString(),
							StepGiftDetailtResponse.class);
					if (result.isSuccess()) {
						stepgiftdetailListener.onSuccess(result.getData());
					} else {
						stepgiftdetailListener.onError(result.getErrorCode(),
								result.getMsg());
					}
				} else {
					System.out.println("uploadstep failed");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NetException e) {
				e.printStackTrace();
			}
		}
	// public static String md5(String string) {
	//
	// byte[] hash;
	//
	// try {
	//
	// hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	//
	// } catch (NoSuchAlgorithmException e) {
	//
	// throw new RuntimeException("Huh, MD5 should be supported?", e);
	//
	// } catch (UnsupportedEncodingException e) {
	//
	// throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	//
	// }
	//
	// StringBuilder hex = new StringBuilder(hash.length * 2);
	//
	// for (byte b : hash) {
	//
	// if ((b & 0xFF) < 0x10)
	// hex.append("0");
	//
	// hex.append(Integer.toHexString(b & 0xFF));
	//
	// }
	// Log.i("hex", hex.toString());
	// return hex.toString();
	//
	// }
	public static String md5(String text) {
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support MD5 algorithm.");
		}

		msgDigest.update(text.getBytes());

		byte[] bytes = msgDigest.digest();

		byte tb;
		char low;
		char high;
		char tmpChar;

		String md5Str = new String();

		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];

			tmpChar = (char) ((tb >>> 4) & 0x000f);

			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}

			md5Str += high;
			tmpChar = (char) (tb & 0x000f);

			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}

			md5Str += low;
		}

		return md5Str;
	}
}
