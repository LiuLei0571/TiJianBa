package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.adapter.ClosingAdapter;
import com.hengtiansoft.tijianba.net.response.BuyDetail;
import com.hengtiansoft.tijianba.net.response.Menu;
import com.hengtiansoft.tijianba.widget.NonScrollListView;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.ClosingActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 27, 2014 11:15:48 PM
 */
public class ClosingActivity extends BaseActivity {
	private NonScrollListView mClosingListView;
	private ArrayList<Menu> mClosingMenus = new ArrayList<Menu>();
	private ClosingAdapter mClosingAdapter;
	private TextView mClosingNumTextView, mTotalPriceTextView,
			mSummationPriceTextView, mRebateTextView, mOrderIdTextView,
			mNameTextView, mMobileTextView, mEmailTextView, mRemarkTextView;
	// private CheckBox mBonusCheckBox;
	private BuyDetail mBuyResult;
	private String url;
	private RelativeLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_closing);
		mBuyResult = remoteDataManager.mBuyDetail;
		setView();
	}

	private void setView() {
		// mBonusCheckBox = (CheckBox) findViewById(R.id.chb_bonus);
		// mBonusCheckBox.setOnCheckedChangeListener(new
		// OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// if (isChecked) {
		// mTotalPriceTextView.setText((mBuyResult.getPayMoney() -
		// mBuyResult.getBonusMoneyCanUse()) + "");
		// } else {
		// mTotalPriceTextView.setText(mBuyResult.getPayMoney() + "");
		// }
		// }
		// });
		url = mBuyResult.getVerifyUrl();
		mOrderIdTextView = (TextView) findViewById(R.id.tv_order_id);
		mClosingNumTextView = (TextView) findViewById(R.id.tv_num);
		mTotalPriceTextView = (TextView) findViewById(R.id.tv_total_price);
		mSummationPriceTextView = (TextView) findViewById(R.id.tv_summation);
		mRebateTextView = (TextView) findViewById(R.id.tv_repayed);
		// mBonusTextView = (TextView) findViewById(R.id.tv_available_bonus);
		mClosingListView = (NonScrollListView) findViewById(R.id.lv_cart);
		mNameTextView = (TextView) findViewById(R.id.tv_name);
		mNameTextView.setText(mBuyResult.getOrderUserName());
		mMobileTextView = (TextView) findViewById(R.id.tv_mobile);
		mMobileTextView.setText(mBuyResult.getOrderUserMobile());
		mEmailTextView = (TextView) findViewById(R.id.tv_email);
		mEmailTextView.setText(mBuyResult.getOrderUserEmail());
		mRemarkTextView = (TextView) findViewById(R.id.tv_remark);
		mRemarkTextView.setText(mBuyResult.getOrderUserRemark());
		if (mBuyResult != null) {
			mClosingMenus = mBuyResult.getMenuList();
			mClosingAdapter = new ClosingAdapter(this, mClosingMenus, true);
			mClosingListView.setAdapter(mClosingAdapter);
			mClosingNumTextView.setText(mClosingMenus.size() + "");
			mOrderIdTextView.setText(mBuyResult.getOrderNo());
			mClosingNumTextView.setText(mBuyResult.getMenuList().size() + "");
			mSummationPriceTextView.setText(mBuyResult.getPayMoney() + "");
			mTotalPriceTextView.setText(mBuyResult.getPayMoney() + "");
			// if (mBuyResult.getBonusMoneyCanUse() != 0) {
			// findViewById(R.id.rl_available_bonus).setVisibility(View.VISIBLE);
			// mBonusTextView.setText(getString(R.string.str_available_bonus) +
			// mBuyResult.getBonusMoneyCanUse()
			// + getString(R.string.str_yuan) + getString(R.string.str_bonus));
			// } else {
			// findViewById(R.id.rl_available_bonus).setVisibility(View.GONE);
			// }
			if (mBuyResult.getRebateTotal() != 0) {
				findViewById(R.id.ll_repay).setVisibility(View.VISIBLE);
				mRebateTextView.setText(mBuyResult.getRebateTotal() + "");
			} else {
				findViewById(R.id.ll_repay).setVisibility(View.GONE);
			}
		}
		relativeLayout = (RelativeLayout) findViewById(R.id.rl_go_pay);
		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				relativeLayout.setEnabled(false);
				Intent intent = new Intent(ClosingActivity.this,
						PayActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("description", mBuyResult.getMenuList().get(0)
						.getMenuName());
				intent.putExtra("money",mBuyResult.getPayMoney()
						);
				intent.putExtra("orderNo", mBuyResult.getOrderNo());
				// if (mBonusCheckBox.isChecked() == true) {
				// intent.putExtra("bonusMoney",
				// mBuyResult.getBonusMoneyCanUse());
				// } else {
				// intent.putExtra("bonusMoney", 0);
				// }
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean paySuccess = getIntent().getBooleanExtra("paySuccess", false);
		if (!paySuccess)
			relativeLayout.setEnabled(true);
	}

	@Override
	public void onBackPressed() {
		AlertDialog ad = new AlertDialog.Builder(this)
				.setMessage("是否确定取消支付？")
				.setPositiveButton(getString(R.string.str_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								setResult(RESULT_OK);
								finish();
							}
						})
				.setNegativeButton(getString(R.string.str_no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).show();
		Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
		positiveButton
				.setTextColor(getResources().getColor(R.color.org_orange));
		positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
		negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
		negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	}
}
