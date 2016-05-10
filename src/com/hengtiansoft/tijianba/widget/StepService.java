package com.hengtiansoft.tijianba.widget;

import java.util.Calendar;
import com.hengtiansoft.tijianba.util.StepDetector;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class StepService extends Service {
	public static Boolean flag = false;
	private SensorManager sensormanager;
	private StepDetector stepdetector;
	
	private Intent intent;
	private Calendar cal;
	private AlarmManager am;
	private PendingIntent pi;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		intent=new Intent(this,TimerStepCounter.class);
		pi=PendingIntent.getBroadcast(this, 0, intent, 0);
		//
		cal=Calendar.getInstance();
//		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//		cal.setTimeInMillis(System.currentTimeMillis());
		
		
		am=(AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),5*60*1000, pi);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startStepDetector();

			}

		}).start();
		
	}


	private void startStepDetector() {
		flag = true;
		stepdetector = new StepDetector(this);
		sensormanager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		Sensor sensor = sensormanager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensormanager.registerListener(stepdetector, sensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		sensormanager.unregisterListener(stepdetector);
		am.cancel(pi);
	}

}
