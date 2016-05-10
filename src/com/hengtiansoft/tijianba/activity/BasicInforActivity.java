package com.hengtiansoft.tijianba.activity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.HealthHome;
import com.hengtiansoft.tijianba.net.response.HealthHomeListener;
import com.hengtiansoft.tijianba.net.response.HealthUser;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.hengtiansoft.tijianba.util.CircleImageView;
import com.hengtiansoft.tijianba.util.ImageUtil;
import com.hengtiansoft.tijianba.widget.CustomDatePickerDialog;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class BasicInforActivity extends BaseActivity implements OnClickListener {
	private CircleImageView mCirculeImage;
	private File file;
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private static final String IMAGE_FILE_NAME = "mylogo.jpg";
	private ImageView mImgClander;
	private TextView mTvClandar;
	private CustomDatePickerDialog mDatePickerDialog;
	private Calendar cal;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	private OnDateSetListener dateListener;
	private RadioGroup mBasicRg;
	private RadioButton mBasicRbMale, mBasicRbFemale;
	private RelativeLayout mBtnSave;
	private EditText mEtName, mEtHeight;
	private byte[] ImgHead;
	private HealthUser mHealthUser;
	private Bitmap mBitmap;
	private boolean isOk = true;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private View parentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_basic_infor,
				null);
		setContentView(parentView);
		initView();
		getMyHealth();
		initPopWindow();
	}

	private void initView() {
		mEtName = (EditText) findViewById(R.id.et_mynicheng);
		mEtHeight = (EditText) findViewById(R.id.et_myhight);
		mEtHeight.setHint("身高范围0.0~250.0");
		mBtnSave = (RelativeLayout) findViewById(R.id.btn_save);
		mBtnSave.setOnClickListener(this);
		mBasicRg = (RadioGroup) findViewById(R.id.rg_basic_gender);
		mBasicRg.setOnCheckedChangeListener(listener);
		mBasicRbMale = (RadioButton) findViewById(R.id.rbtn_basic_male);
		mBasicRbFemale = (RadioButton) findViewById(R.id.rbtn_basic_female);
		mCirculeImage = (CircleImageView) findViewById(R.id.img_login_mylogo);
		mImgClander = (ImageView) findViewById(R.id.img_calendar);
		mTvClandar = (TextView) findViewById(R.id.tv_calendar);
		mCirculeImage.setOnClickListener(this);
		mImgClander.setOnClickListener(this);
		mTvClandar.setOnClickListener(this);
		cal = Calendar.getInstance();
		currentYear = 1980;
		currentMonth = 1;
		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM").parse(year + "-"
							+ (monthOfYear + 1));
				} catch (ParseException e) {
				}
				String dateTime = new SimpleDateFormat("yyyy" + "-" + "MM")
						.format(date);
				mTvClandar.setText(dateTime);
				mImgClander.setVisibility(View.GONE);
			}
		};
		mDatePickerDialog = new CustomDatePickerDialog(this, dateListener,
				currentYear, currentMonth, currentDay);
	}

	private void setView() {
		mEtHeight.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals("")) {
				} else {
					try {
						float num = Float.parseFloat(s.toString());
						if (num < 0 || num > 250.0) {
							isOk = false;
							mEtHeight.setError("身高范围0.0~250.0");
						}
					} catch (NumberFormatException e) {
						mEtHeight.setError("输入格式有误！");
					}
				}
			}
		});
		if (mHealthUser == null) {
		} else {
			mEtName.setText(mHealthUser.getName());
			mTvClandar.setText(getTime(mHealthUser.getBornDate()));
			mImgClander.setVisibility(View.GONE);
			if (mHealthUser.getGender() == 0) {
				mBasicRbMale.setChecked(true);
			} else {
				mBasicRbFemale.setChecked(true);
			}
			mEtHeight.setText(String.valueOf(mHealthUser.getHeight()));
			ImageLoader.getInstance().loadImage(
					RemoteDataManager.SERVICE + mHealthUser.getHeadImg(),
					options, new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							showProgressDialog("头像", "加载中...");
						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							dismissProgressDialog();
							Toast.makeText(BasicInforActivity.this, "头像加载失败",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							mBitmap = arg2;
							mCirculeImage.setImageBitmap(mBitmap);
							dismissProgressDialog();
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
						}
					});
		}
	}

	public void initPopWindow() {
		pop = new PopupWindow(BasicInforActivity.this);
		View view = getLayoutInflater().inflate(R.layout.layout_popupwindows,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button btnTakePhoto = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button btnAblum = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button btnCancle = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(this);
		btnTakePhoto.setOnClickListener(this);
		btnAblum.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_login_mylogo:
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.img_calendar:
			mDatePickerDialog.show();
			break;
		case R.id.tv_calendar:
			int[] time = getBirthTime(mTvClandar.getText().toString());
			mDatePickerDialog = new CustomDatePickerDialog(this, dateListener,
					time[0], time[1], 0);
			mDatePickerDialog.show();
			break;
		case R.id.btn_save:
			submmit();
			break;
		case R.id.parent:
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_camera:
			takePicture();
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_Photo:
			getPicture();
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		case R.id.item_popupwindows_cancel:
			pop.dismiss();
			ll_popup.clearAnimation();
			break;
		default:
			break;
		}
	}

	private void submmit() {
		String name = mEtName.getText().toString();
		float height = 0;
		int sex = 0;
		String birthDay = "";
		if (mEtName.getText().toString().equals("")) {
			mEtName.setError("昵称不能为空！");
			isOk = false;
		} else {
			name = mEtName.getText().toString();
			isOk = true;
		}
		if (mEtHeight.getText().toString().equals("")) {
			mEtHeight.setError("身高不能为空！");
			isOk = false;
		} else {
			height = Float.parseFloat(mEtHeight.getText().toString());
			isOk = true;
		}
		if (mBasicRbMale.isChecked()) {
			sex = 0;
			isOk = true;
		} else if (mBasicRbFemale.isChecked()) {
			sex = 1;
			isOk = true;
		} else {
			Toast.makeText(BasicInforActivity.this, "性别没有选择", 0).show();
			isOk = false;
		}
		if (mTvClandar.getText().toString().equals("")) {
			Toast.makeText(BasicInforActivity.this, "生日没有选择", 0).show();
			isOk = false;
		} else {
			birthDay = mTvClandar.getText().toString() + "-01 00:00:00";
			isOk = true;
		}
		if (isOk) {
			saveDate(name, sex, birthDay, height, ImgHead);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String getTime(String str) {
		String dateTime = "";
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
		}
		dateTime = new SimpleDateFormat("yyyy" + "-" + "MM").format(date);
		return dateTime;
	}

	private int[] getBirthTime(String str) {
		String[] strTime = str.split("-");
		int[] time = new int[strTime.length];
		for (int i = 0; i < strTime.length; i++) {
			time[i] = Integer.parseInt(strTime[i]);
		}
		return time;
	}

	// private void showDio(final String[] item) {
	// new AlertDialog.Builder(BasicInforActivity.this).setItems(items,
	// new android.content.DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface arg0, int arg1) {
	// if (item[arg1].equals("拍照")) {
	// takePicture();
	// } else if (item[arg1].equals("相册")) {
	// getPicture();
	// }
	// }
	// }).show();
	// }

	private void takePicture() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			String saveDir = Environment.getExternalStorageDirectory()
					+ "/lnmjk_android";
			File dir = new File(saveDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String save = Environment.getExternalStorageDirectory()
					+ "/lnmjk_android/logo";
			File direr = new File(save);
			if (!direr.exists()) {
				direr.mkdir();
			}
			file = new File(save, IMAGE_FILE_NAME);
			file.delete();
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
		PackageManager packageManager = this.getPackageManager();
		if (packageManager
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra("camerasensortype", 2);
			intent.putExtra("autofocus", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, CAMERA_REQUEST_CODE);
		} else {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra("autofocus", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, CAMERA_REQUEST_CODE);
		}

	}

	private void getPicture() {
		Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IMAGE_REQUEST_CODE:
			if (data == null) {
			} else {
				startPhotoZoom(data.getData());
			}
			break;
		case CAMERA_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				try {
					startPhotoZoom(Uri.fromFile(file));
				} catch (NullPointerException e) {
				}
			} else {
				file.delete();
			}
			break;
		case RESULT_REQUEST_CODE:
			if (data != null) {
				getImageToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			mBitmap = extras.getParcelable("data");
			if (mBitmap != null) {
				ImgHead = ImageUtil.getBitmapByte(mBitmap);
				mCirculeImage.setImageBitmap(mBitmap);
			}
		}
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup rg, int checkedId) {
			switch (rg.getId()) {
			case R.id.rg_basic_gender:
				if (checkedId == R.id.rbtn_basic_male) {
					if (mBasicRbMale.isChecked()) {
						mBasicRbMale.setTextColor(getResources().getColor(
								R.color.sub_green));
						mBasicRbFemale.setTextColor(getResources().getColor(
								R.color.sub_grey));
					} else
						mBasicRbMale.setTextColor(getResources().getColor(
								R.color.sub_grey));
				} else if (checkedId == R.id.rbtn_basic_female) {
					if (mBasicRbFemale.isChecked()) {
						mBasicRbMale.setTextColor(getResources().getColor(
								R.color.sub_grey));
						mBasicRbFemale.setTextColor(getResources().getColor(
								R.color.sub_red));
					} else
						mBasicRbFemale.setTextColor(getResources().getColor(
								R.color.sub_grey));
				}
				break;
			default:
				break;
			}
		}
	};

	private void saveDate(String name, int gender, String bornDate,
			float height, byte[] imgHead) {
		remoteDataManager.healthUserEditListener = new ReturnFromServerListener() {
			@Override
			public void onSuccess(String message) {
				BasicInforActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgressDialog();
						BasicInforActivity.this.finish();
					}
				});
			}

			@Override
			public void onError(String errorCode, String errorMessage) {
				dismissProgressDialog();
				handleError(errorMessage);
			}
		};
		if (validateInternet()) {
			showProgressDialog("基本信息", "上传中...");
			remoteDataManager.editHealthUser(name, gender, bornDate, height,
					imgHead);
		}
	}

	private void getMyHealth() {
		remoteDataManager.healthHomeListener = new HealthHomeListener() {
			@Override
			public void onSuccess(final HealthHome healthHome) {
				BasicInforActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgressDialog();
						mHealthUser = healthHome.getHealthUser();
						setView();
					}
				});
			}

			public void onError(String errorCode, String errorMessage) {
				dismissProgressDialog();
				handleError(errorMessage);
			}
		};
		if (validateInternet()) {
			showProgressDialog("基本信息", "加载中");
			remoteDataManager.getHealthHome();
		}
	}
}
