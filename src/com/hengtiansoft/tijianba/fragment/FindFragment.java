package com.hengtiansoft.tijianba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.hengtiansoft.tijianba.activity.Gogogo;
import com.hengtiansoft.tijianba.activity.InteractionActivity;
import com.hengtiansoft.tijianba.activity.LoginActivity;
import com.hengtiansoft.tijianba.activity.MyFamilyActivity;
import com.hengtiansoft.tijianba.activity.QuestionListActivity;
import com.juanliuinformation.lvningmeng.R;

public class FindFragment extends BaseFragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.main_find, null);
		view.findViewById(R.id.rl_interaction).setOnClickListener(this);
		view.findViewById(R.id.rl_my_family).setOnClickListener(this);
		view.findViewById(R.id.rl_health_info).setOnClickListener(this);
		
		view.findViewById(R.id.gogogo).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rl_interaction:
			if (remoteDataManager.isLogin) {
				intent.setClass(getActivity(), InteractionActivity.class);
				startActivity(intent);
			} else {
				Intent intentLogin = new Intent(getActivity(),
						LoginActivity.class);
				intentLogin.putExtra("isGotoFind", true);
				startActivity(intentLogin);
			}
			break;
		case R.id.rl_my_family:
			intent.setClass(getActivity(), MyFamilyActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_health_info:
			intent.setClass(getActivity(), QuestionListActivity.class);
			startActivity(intent);
			break;
		case R.id.gogogo:
			intent.setClass(getActivity(), Gogogo.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}
}
