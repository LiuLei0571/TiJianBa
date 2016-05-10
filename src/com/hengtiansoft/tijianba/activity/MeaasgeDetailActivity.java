package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;

import com.hengtiansoft.tijianba.adapter.ExpandableMessageAdapter;
import com.hengtiansoft.tijianba.model.Contact;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.MeaasgeDetailActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 4:30:12 PM
 */
public class MeaasgeDetailActivity extends BaseActivity implements OnClickListener {
  private ExpandableListView mMessageExpandableListView;
  private ExpandableMessageAdapter mMessageExpandableAdapter;
  private List<String> mGroups = new ArrayList<String>();
  private List<List<Contact>> mChilds = new ArrayList<List<Contact>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_message_detail);
    initExpandableListView();
  }

  private void initExpandableListView() {
    mMessageExpandableListView = (ExpandableListView) findViewById(R.id.elv_messge);
    mMessageExpandableAdapter = new ExpandableMessageAdapter(MeaasgeDetailActivity.this, mGroups, mChilds);

    mGroups.add(getString(R.string.str_friend_request));
    mGroups.add(getString(R.string.str_friend_delete));
    mGroups.add(getString(R.string.str_friend_reminder));

    List<Contact> child1 = new ArrayList<Contact>();
    child1.add(new Contact("龟梨和也", "男神", "10010",0, "",""));
    child1.add(new Contact("张建霞", "Cece", "13744444444",1, "",""));

    List<Contact> child2 = new ArrayList<Contact>();
    child2.add(new Contact("李海霞", "Chanel", "13744448844",2, "",""));
    child2.add(new Contact("赤西仁", "笨蛋", "13744455844",2, "",""));

    List<Contact> child3 = new ArrayList<Contact>();
    child3.add(new Contact("龟梨和也", "", "",3, "2014-12-12 20:00","按时吃药"));
    child3.add(new Contact("赤西仁", "", "",3, "2014-12-12 21:00","浙一医院体检"));

    mChilds.add(child1);
    mChilds.add(child2);
    mChilds.add(child3);

    mMessageExpandableListView.setAdapter(mMessageExpandableAdapter);
    mMessageExpandableListView.setGroupIndicator(null);
    for (int i = 0; i < mGroups.size(); i++) {
      mMessageExpandableListView.expandGroup(i);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      default:
        break;
    }
  }

}
