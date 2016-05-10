package com.hengtiansoft.tijianba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.hengtiansoft.tijianba.activity.MenuListActivity;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.MallMenuType;
import com.hengtiansoft.tijianba.widget.CircleImageView;
import com.juanliuinformation.lvningmeng.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MallTypeFragment extends BaseFragment {
  private View mView;
  private CircleImageView mImageView;
  private TextView mTextView;
  private MallMenuType mallMenuType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    mView = LayoutInflater.from(getActivity()).inflate(R.layout.mall_type_item, null);
    mImageView = (CircleImageView) mView.findViewById(R.id.iv_logo);
    mTextView = (TextView) mView.findViewById(R.id.tv_name);
    if (mallMenuType != null) {
      mTextView.setText(mallMenuType.getTitle());
      ImageLoader.getInstance().displayImage(RemoteDataManager.SERVICE + mallMenuType.getImg(), mImageView, options);
    }
    mView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MenuListActivity.class);
        intent.putExtra("menuTypeName", mallMenuType.getTitle());
        intent.putExtra("menuTypeId", mallMenuType.getMenuTypeId());
        intent.putExtra("fromMall", true);
        getActivity().startActivity(intent);
      }
    });

    new Handler().postDelayed(new Runnable() {
      public void run() {
        if (getActivity() != null) {
          DisplayMetrics dm = getResources().getDisplayMetrics();
          int w_screen = dm.widthPixels;
          LayoutParams layoutParams = (LayoutParams) mView.getLayoutParams();
          layoutParams.width = w_screen / 4;
          mView.setLayoutParams(layoutParams);
        }
      }
    }, 100L);

    return mView;
  }

  public void setData(MallMenuType menuType) {
    this.mallMenuType = menuType;
  }

}
