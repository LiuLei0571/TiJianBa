package com.hengtiansoft.tijianba.widget;

import java.util.Calendar;

import com.hengtiansoft.tijianba.util.StepDetector;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint("CommitPrefEdits")
public class TimerStepCounter extends BroadcastReceiver {

	private SharedPreferences spSettting;
	private Editor editor;
	private Calendar cal;
	private String date;

	private int temp;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		spSettting = arg0.getSharedPreferences("settings", 0);
		editor = spSettting.edit();
		Day_clear();
 
		timing_update();
		
		}
//
	@SuppressWarnings("deprecation")
	private void timing_update(){
		temp=0;
		for(int i=0;i<=21;i+=3){
			if(cal.getTime().getHours()>=i&&cal.getTime().getHours()>i+3){		
				temp+=spSettting.getInt(Integer.toString(i)+":00", 0);
				}
			else{
				editor.putInt(Integer.toString(i)+":00",StepDetector.CURRENT_STEP-temp);
				editor.commit();
				break;
			}
		}
	}
	
	private void Day_clear(){
		cal=Calendar.getInstance();
		date=cal.getTime().toString().substring(0, 10);
		if(spSettting.getString("current_date", "").equals("")||!(date.equals(spSettting.getString("current_date", "")))){
			editor.putString("current_date",date);
			editor.putInt("Total_Step", 0);
			for(int i=0;i<=21;i+=3){
				editor.putInt(Integer.toString(i)+":00", 0);
			}
			StepDetector.CURRENT_STEP=0;
			editor.commit();
		}
	}
}
