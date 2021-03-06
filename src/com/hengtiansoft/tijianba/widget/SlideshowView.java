package com.hengtiansoft.tijianba.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.juanliuinformation.lvningmeng.R;

 

 
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
  public class SlideshowView extends FrameLayout {

	  private final boolean isAuto=true;
	    private List<Integer> imageUris;
	    private List<ImageView> imageViewsList;
	    private List<ImageView> dotViewsList;//小白点图标数组
	    private LinearLayout mLinearLayout;
	    private ViewPager mViewPager;
	    private int currentItem=0;//初始页卡
	    private ScheduledExecutorService scheduledExecutorService;
	    
	    private Handler handler=new Handler (){
	      public void handleMessage(Message msg){
	        super.handleMessage(msg);
	        mViewPager.setCurrentItem(currentItem);
	      }
	    };
	    
	  public SlideshowView(Context context) {
	    this(context,null);
	    // TODO Auto-generated constructor stub
	  }



	  public SlideshowView(Context context, AttributeSet attrs) {
	    this(context, attrs,0);
	    // TODO Auto-generated constructor stub
	  }
	  public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr) {
	    super(context, attrs, defStyleAttr);
	    // TODO Auto-generated constructor stub
	    
	   initUI(context);
	   if(!(imageUris.size()<=0)){
		   
	      setImageUris(imageUris);
	   }
	   if(isAuto){
	     startPlay();
	   }

	  }
	  
	  private void initUI(Context context) {
	    // TODO Auto-generated method stub
	    
	    imageViewsList=new ArrayList<ImageView>();
	    dotViewsList=new ArrayList<ImageView>();
	    imageUris=new ArrayList<Integer>();
	    
	    LayoutInflater.from(context).inflate(R.layout.layout_gift_slideshow,this,true);
	    mLinearLayout=(LinearLayout) findViewById(R.id.linearlayout);
	    mViewPager=(ViewPager) findViewById(R.id.viewPager);
	  }
	  
	  public void setImageUris(List<Integer> imageuris) {
	    // TODO Auto-generated method stub
	    for(int i=0;i<imageuris.size();i++){
	      imageUris.add(imageuris.get(i));
	      
	    }
	    
	    //在代码中设置margin
	    LinearLayout.LayoutParams lp=new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    lp.setMargins(5, 0, 0, 0);
	    for(int i=0;i<imageUris.size();i++){
	      ImageView imageView=new ImageView(getContext());
	      imageView.setScaleType(ScaleType.FIT_XY);//铺满全屏
	      imageView.setBackgroundResource((int)imageuris.get(i));
	      imageViewsList.add(imageView);
	      
	      ImageView imagedot=new ImageView(getContext());
	      if(i==0){
	        imagedot.setBackgroundResource(R.drawable.ic_dot_white);
	      }else{
	        imagedot.setBackgroundResource(R.drawable.ic_dot_light);
	      }
	      imagedot.setLayoutParams(lp);
	      dotViewsList.add(imagedot);
	      mLinearLayout.addView(imagedot);
	    }
	    mViewPager.setFocusable(true);
	    mViewPager.setAdapter(new MyPagerAdapter());
	    mViewPager.setOnPageChangeListener(new MyPageChangeListener());
	  }
	  /**
	   * command：执行线程
	   * initialDelay：初始化延时
	   * period：两次开始执行最小间隔时间
	   * unit：计时单位
	   * */
	  private void startPlay(){
	    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	    scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
	}

	  @SuppressWarnings("unused")
	  private void stopPlay(){
	      scheduledExecutorService.shutdown();
	  }
	/** 
	 * 设置选中的tip的背景 
	 * @param selectItems 
	 */  
	  private void setImageBackground(int selectItems){  
	        for(int i=0; i<dotViewsList.size(); i++){  
	            if(i == selectItems){  
	              dotViewsList.get(i).setBackgroundResource(R.drawable.ic_dot_white);  
	            }else{  
	              dotViewsList.get(i).setBackgroundResource(R.drawable.ic_dot_light);  
	            }  
	        }  
	    }  
	     


	  private class MyPagerAdapter  extends PagerAdapter{

	    @Override
	    public void destroyItem(View container, int position, Object object) {
	        // TODO Auto-generated method stub
	        //((ViewPag.er)container).removeView((View)object);
	        ((ViewPager)container).removeView(imageViewsList.get(position));
	        
	    }

	    @Override
	    public Object instantiateItem(View container, int position) {
	        // TODO Auto-generated method stub
	        ((ViewPager)container).addView(imageViewsList.get(position));
	        return imageViewsList.get(position);
	    }

	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return imageViewsList.size();
	    }

	    @Override
	    public boolean isViewFromObject(View arg0, Object arg1) {
	        // TODO Auto-generated method stub
	        return arg0 == arg1;
	    }
	    @Override
	    public void restoreState(Parcelable arg0, ClassLoader arg1) {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public Parcelable saveState() {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    @Override
	    public void startUpdate(View arg0) {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public void finishUpdate(View arg0) {
	        // TODO Auto-generated method stub
	        
	    }
	    
	}
	 

	  private class MyPageChangeListener implements OnPageChangeListener{

	    boolean isAutoPlay = false;

	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	        // TODO Auto-generated method stub
	        switch (arg0) {
	        case 1:
	            isAutoPlay = false;
	            break;
	        case 2:
	            isAutoPlay = true;
	            break;
	        case 0:
	            if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
	                mViewPager.setCurrentItem(0);
	            }
	            
	            else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
	                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
	            }
	            break;
	    }
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	        // TODO Auto-generated method stub
	        
	    }

	    @Override
	    public void onPageSelected(int pos) {
	        // TODO Auto-generated method stub
	       setImageBackground(pos % imageUris.size());  
	 
	    }
	    
	}

	  private class SlideShowTask implements Runnable{

	    @Override
	    public void run() {
	        // TODO Auto-generated method stub
	        synchronized (mViewPager) {
	            currentItem = (currentItem+1)%imageViewsList.size();
	            handler.obtainMessage().sendToTarget();
	        }
	    }
	    
	}
	  private void destoryBitmaps() {

	    for (int i = 0; i < imageViewsList.size(); i++) {
	        ImageView imageView = imageViewsList.get(i);
	        Drawable drawable = imageView.getDrawable();
	        if (drawable != null) {
	            
	            drawable.setCallback(null);
	        }
	    }
	}
}
