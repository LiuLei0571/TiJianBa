package com.hengtiansoft.tijianba.activity;


import java.util.ArrayList;
import java.util.List;
import com.hengtiansoft.tijianba.adapter.RankingAdapter;
import com.hengtiansoft.tijianba.model.Ranking;
import com.juanliuinformation.lvningmeng.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RankingActivity extends Activity implements OnClickListener{
	
private ListView mListView;
   private List<Ranking> mList = new ArrayList<Ranking>();
   
   private ImageButton backWeek;
   private ImageButton finish;
   private TextView week;
   private TextView date;
   private LinearLayout gift;
   private LinearLayout nextWeek;
   private ImageButton nextWeekButton;
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_ranking);
    
    rankingResult();
    
    initView();
    
    backWeek.setOnClickListener(this);
    finish.setOnClickListener(this);
    gift.setOnClickListener(this);
    nextWeek.setOnClickListener(this);
    
    for(int i=1;i<=10;i++){
    	mList.add(new Ranking("momo"));
    }
    

    
//    for(int i=1;i<=10;i++){
//    	mArrayList.add(new Ranking(R.drawable.paihang_one,
//    			R.drawable.paihang_baby, "name is i",
//    			R.drawable.paihang_one_star, "***"));
//    }
 
    mListView = (ListView) findViewById(R.id.ranking_lv);
    mListView.setAdapter(new RankingAdapter(this,mList));
  
  }

  private List<Ranking> rankingResult() {
	  
	return mList;
}

private void initView() {
	    backWeek = (ImageButton) findViewById(R.id.rangking_backweek_ib);
	    week = (TextView) findViewById(R.id.ranking_week_tv);
	    date = (TextView) findViewById(R.id.ranking_date_tv);
	    finish = (ImageButton) findViewById(R.id.ranking_finish_ib);
	    gift = (LinearLayout) findViewById(R.id.rangking_gift);
	    nextWeek = (LinearLayout) findViewById(R.id.rangking_nextweek);
	    nextWeekButton = (ImageButton) findViewById(R.id.rangking_nextweek_ib);
  }

@Override
  public void onClick(View arg0) {
	switch (arg0.getId()) {
	case  R.id.rangking_backweek_ib:
		week.setText("上周");
		date.setText("5月11日-5月17日");
		nextWeekButton.setVisibility(View.VISIBLE);
		break;
	case  R.id.ranking_finish_ib:
		finish();
		break;
	case R.id.rangking_gift:
		Intent i = new Intent(RankingActivity.this,GiftListActivity.class);
		startActivity(i);
		break;
	case R.id.rangking_nextweek:
		week.setText("本周");
		date.setText("5月4日-5月10日");
		nextWeekButton.setVisibility(View.INVISIBLE);
	default:
		break;
	}
  }
}
