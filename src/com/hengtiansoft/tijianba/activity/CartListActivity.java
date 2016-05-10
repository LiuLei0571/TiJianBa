package com.hengtiansoft.tijianba.activity;

import java.text.DecimalFormat;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.hengtiansoft.tijianba.adapter.CartAdapter;
import com.hengtiansoft.tijianba.adapter.CartAdapter.CartAdapterListener;
import com.hengtiansoft.tijianba.net.response.BuyMenu;
import com.hengtiansoft.tijianba.net.response.CartDetail;
import com.hengtiansoft.tijianba.net.response.CartDetailUpdate;
import com.hengtiansoft.tijianba.net.response.CartListListener;
import com.hengtiansoft.tijianba.net.response.PackedDeleteCartRequest;
import com.hengtiansoft.tijianba.net.response.PackedUpdateCartRequest;
import com.hengtiansoft.tijianba.net.response.ReturnFromServerListener;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.CartListActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 27, 2014 11:22:37 AM
 */
public class CartListActivity extends BaseActivity implements OnClickListener {
  private SwipeListView mCartListView;
  private ArrayList<CartDetail> mCartMenus = new ArrayList<CartDetail>();
  private CartAdapter mCartAdapter;
  private TextView mCartNumTextView, mTotalPriceTextView, mEditOrFinishTextView;
  private CheckBox mAllCheckBox;
  private int totalPrice;
  private ImageView mClearNowImageView;
  private boolean canClose = false;
  private TextView noDataTextView;
  private boolean needClear = true;
  private DecimalFormat decimalFormat = new DecimalFormat("##0.00");

  private ArrayList<Integer> mCartDeleted = new ArrayList<Integer>();;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_cart_list);
    setView();
  }

  private void setView() {
    findViewById(R.id.rl_close_now).setOnClickListener(this);
    mClearNowImageView = (ImageView) findViewById(R.id.iv_close_now);
    mCartNumTextView = (TextView) findViewById(R.id.tv_num);
    mAllCheckBox = (CheckBox) findViewById(R.id.chb_all);
    noDataTextView = (TextView) findViewById(R.id.tv_no_data);
    mAllCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          if (mEditOrFinishTextView.getText().toString().equals(getString(R.string.str_edit))) {
            canClose = true;
            mClearNowImageView.setImageResource(R.drawable.btn_clear_now);
          }
          for (int i = 0; i < mCartMenus.size(); i++) {
            mCartMenus.get(i).setSelect(true);
          }
        } else {
          if (needClear) {
            for (int i = 0; i < mCartMenus.size(); i++) {
              mCartMenus.get(i).setSelect(false);
            }
          }
        }
        mCartAdapter.notifyDataSetChanged();
      }
    });
    mTotalPriceTextView = (TextView) findViewById(R.id.tv_total_price);
    mCartListView = (SwipeListView) findViewById(R.id.lv_cart);
    mCartAdapter = new CartAdapter(this, mCartMenus);
    mCartAdapter.setListener(new CartAdapterListener() {

      @Override
      public void onCartAdapterListener(int type, int position) {
        // TODO Auto-generated method stub
        if (type == 1) {
          deteleCartItem(position, mCartMenus.get(position).getCartDetailId());
          return;
        }
        int selectNum = 0;
        for (int i = 0; i < mCartMenus.size(); i++) {
          if (mCartMenus.get(i).isSelect()) {
            selectNum++;
          }
        }
        if (selectNum != mCartMenus.size()) {
          needClear = false;
          mAllCheckBox.setChecked(false);
        } else {
          needClear = true;
          mAllCheckBox.setChecked(true);
        }
        totalPrice = 0;
        for (int i = 0; i < mCartMenus.size(); i++) {
          if (mCartMenus.get(i).isSelect()) {
            if (mCartMenus.get(i).isFamily()) {
              totalPrice = totalPrice + mCartMenus.get(i).getNumber() * mCartMenus.get(i).getFamilyPrice();
            } else {
              totalPrice = totalPrice + mCartMenus.get(i).getNumber() * mCartMenus.get(i).getPlatformPrice();
            }
          }
        }
        mTotalPriceTextView.setText(decimalFormat.format(totalPrice));
        if (mEditOrFinishTextView.getText().toString().equals(getString(R.string.str_edit))) {
          canClose = true;
          mClearNowImageView.setImageResource(R.drawable.btn_clear_now);
        }
      }
    });
    mCartListView.setAdapter(mCartAdapter);
    mEditOrFinishTextView = (TextView) findViewById(R.id.tv_edit_or_finish);
    mEditOrFinishTextView.setOnClickListener(this);
  }

  private void deleteCartDialog(final int pos) {
    RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_dialog_changepwd, null);
    TextView dialogTitle = (TextView) dialogLayout.findViewById(R.id.tv_dialog_title);
    dialogTitle.setText(getString(R.string.str_is_delete_cart));
    AlertDialog ad = new AlertDialog.Builder(this).setView(dialogLayout)
        .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            mCartMenus.remove(pos);
            mCartAdapter.notifyDataSetChanged();
            mCartListView.closeOpenedItems();
            dialog.dismiss();

          }
        }).setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            mCartListView.closeOpenedItems();
            dialog.dismiss();

          }
        }).show();
    Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
    positiveButton.setTextColor(getResources().getColor(R.color.org_orange));
    positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
    Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
    negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
    negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.tv_edit_or_finish:
        if (mEditOrFinishTextView.getText().toString().equals(getString(R.string.str_finish))) {
          mEditOrFinishTextView.setText(getString(R.string.str_edit));
          for (int i = 0; i < mCartMenus.size(); i++) {
            mCartMenus.get(i).setEdit(false);
          }
          mCartAdapter.notifyDataSetChanged();
          if (totalPrice != 0)
            mClearNowImageView.setImageResource(R.drawable.btn_clear_now);
          canClose = true;
          updateCartList();
        } else {
          mEditOrFinishTextView.setText(getString(R.string.str_finish));
          mAllCheckBox.setChecked(true);
          for (int i = 0; i < mCartMenus.size(); i++) {
            mCartMenus.get(i).setEdit(true);
            mCartMenus.get(i).setSelect(true);
          }
          mCartAdapter.notifyDataSetChanged();
          mClearNowImageView.setImageResource(R.drawable.btn_clear_now_unenable);
          canClose = false;
        }
        break;
      case R.id.rl_close_now:
        if (canClose) {
          ArrayList<BuyMenu> buyMenus = new ArrayList<BuyMenu>();
          ArrayList<Integer> mCartDeleted = new ArrayList<Integer>();
          for (int i = 0; i < mCartMenus.size(); i++) {
            if (mCartMenus.get(i).isSelect()) {
              BuyMenu menu = new BuyMenu();
              menu.setBuyNum(mCartMenus.get(i).getNumber());
              menu.setFamily(mCartMenus.get(i).isFamily());
              menu.setMenuId(mCartMenus.get(i).getId());
              buyMenus.add(menu);
              mCartDeleted.add(mCartMenus.get(i).getCartDetailId());
            }
          }
          Intent intent = new Intent(CartListActivity.this, AddContactActivity.class);
          intent.putExtra("buyMenus", buyMenus);
          intent.putExtra("cartDeleted", mCartDeleted);
          intent.putExtra("totalPrice", totalPrice);
          startActivity(intent);
        }
        break;
      default:
        break;
    }
  }

  private void getCartList() {
    remoteDataManager.cartListListener = new CartListListener() {

      @Override
      public void onSuccess(final ArrayList<CartDetail> cartDetails) {
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            mCartNumTextView.setText(cartDetails.size() + "");
            if (cartDetails.size() == 0 && mCartMenus.size() == 0) {
              noDataTextView.setVisibility(View.VISIBLE);
            } else {
              noDataTextView.setVisibility(View.GONE);
            }
            mCartMenus.clear();
            for (int i = 0; i < cartDetails.size(); i++) {
              CartDetail cartDetail = new CartDetail();
              cartDetail.setId(cartDetails.get(i).getId());
              cartDetail.setCartDetailId(cartDetails.get(i).getCartDetailId());
              cartDetail.setFamily(cartDetails.get(i).isFamily());
              cartDetail.setFamilyPrice(cartDetails.get(i).getFamilyPrice());
              cartDetail.setMarketPrice(cartDetails.get(i).getMarketPrice());
              cartDetail.setPlatformPrice(cartDetails.get(i).getPlatformPrice());
              cartDetail.setMenuTypeName(cartDetails.get(i).getMenuTypeName());
              cartDetail.setName(cartDetails.get(i).getName());
              cartDetail.setNumber(cartDetails.get(i).getNumber());
              cartDetail.setTotalNum(cartDetails.get(i).getTotalNum());
              cartDetail.setPurchaseNum(cartDetails.get(i).getPurchaseNum());
              cartDetail.setLogo(cartDetails.get(i).getLogo());
              mCartMenus.add(cartDetail);
            }
            mCartAdapter.notifyDataSetChanged();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      showProgressDialog(getString(R.string.str_cart), "加载中..");
      remoteDataManager.getCartList();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mCartMenus.clear();
    mAllCheckBox.setChecked(false);
    totalPrice = 0;
    mTotalPriceTextView.setText(decimalFormat.format(totalPrice));
    getCartList();
  }

  private void deteleCartItem(final int position, int cartDetailId) {
    remoteDataManager.cartCountListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        dismissProgressDialog();
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            mCartMenus.remove(position);
            mCartAdapter.notifyDataSetChanged();
            mCartListView.closeOpenedItems();
            mCartNumTextView.setText(mCartMenus.size() + "");
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      PackedDeleteCartRequest packedDeleteCartRequest = new PackedDeleteCartRequest();
      mCartDeleted.clear();
      mCartDeleted.add(cartDetailId);
      packedDeleteCartRequest.setCartDeleteList(mCartDeleted);
      remoteDataManager.deleteCart(packedDeleteCartRequest);
    }

  }

  private void updateCartList() {
    PackedUpdateCartRequest packedUpdateCartRequest = new PackedUpdateCartRequest();
    ArrayList<CartDetailUpdate> cartDetailUpdates = new ArrayList<CartDetailUpdate>();
    for (int i = 0; i < mCartMenus.size(); i++) {
      CartDetailUpdate detail = new CartDetailUpdate();
      detail.setCartDetailId(mCartMenus.get(i).getCartDetailId());
      detail.setNumber(mCartMenus.get(i).getNumber());
      cartDetailUpdates.add(detail);
    }
    packedUpdateCartRequest.setMenus(cartDetailUpdates);
    remoteDataManager.cartCountListener = new ReturnFromServerListener() {

      @Override
      public void onSuccess(String message) {
        runOnUiThread(new Runnable() {
          public void run() {
            getCartList();
          }
        });
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        handleError(errorMessage);
      }
    };
    if (validateInternet()) {
      remoteDataManager.updateCart(packedUpdateCartRequest);
    }
  }
}
