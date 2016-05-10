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

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.hengtiansoft.tijianba.adapter.ContactAdapter;
import com.hengtiansoft.tijianba.adapter.ContactAdapter.ContactAdapterListener;
import com.hengtiansoft.tijianba.model.Contact;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.activity.ContactActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 16, 2014 1:28:52 PM
 */
public class MyFamilyActivity extends BaseActivity implements OnClickListener {
  private ContactAdapter mContactAdapter;
  private SwipeListView mContactListView;
  private ArrayList<Contact> contacts = new ArrayList<Contact>();
  private AlertDialog ad;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_my_family);
    setView();
  }

  private void setView() {
    findViewById(R.id.iv_add_person).setOnClickListener(this);
    findViewById(R.id.rl_message).setOnClickListener(this);
    mContactListView = (SwipeListView) findViewById(R.id.lv_contact);
    mContactListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
      @Override
      public void onClickFrontView(int position) {
        super.onClickFrontView(position);
        Intent intent = new Intent(MyFamilyActivity.this, HealthBookActivity.class);
        intent.putExtra("isFromFamily", true);
        intent.putExtra("name", contacts.get(position).getName());
        startActivity(intent);
      }
    });
    mContactAdapter = new ContactAdapter(this, contacts);
    mContactAdapter.setListener(new ContactAdapterListener() {

      @Override
      public void onContactAdapterListener(final int pos) {
        RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_dialog_family_other, null);
        TextView dialogTitle = (TextView) dialogLayout.findViewById(R.id.tv_dialog_title);
        dialogTitle.setText(getString(R.string.str_is_delete) + "\n" + contacts.get(pos).getName());
        ad = new AlertDialog.Builder(MyFamilyActivity.this).setView(dialogLayout)
            .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                contacts.remove(pos);
                mContactAdapter.notifyDataSetChanged();
                mContactListView.closeOpenedItems();
                ad.dismiss();
              }
            }).setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                mContactListView.closeOpenedItems();
                ad.dismiss();
              }
            }).show();
        Button positiveButton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.org_orange));
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        Button negativeButton = ad.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(getResources().getColor(R.color.cart_grey));
        negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
      }
    });
    mContactListView.setAdapter(mContactAdapter);
    contacts.clear();
    Contact contact = new Contact();
    contact.setName("龟梨和也");
    contact.setNickName("男神");
    contact.setMobile("10010");
    contacts.add(contact);
    Contact contact2 = new Contact();
    contact2.setName("张建霞");
    contact2.setNickName("Cece");
    contact2.setMobile("13777777777");
    contacts.add(contact2);
    Contact contact3 = new Contact();
    contact3.setName("李海霞");
    contact3.setNickName("Chanel");
    contact3.setMobile("13777775557");
    contacts.add(contact3);
    mContactAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
      case R.id.iv_add_person:
        intent.setClass(MyFamilyActivity.this, AddFamilyMemberActivity.class);
        startActivity(intent);
        break;
      case R.id.rl_message:
        intent.setClass(MyFamilyActivity.this, MeaasgeDetailActivity.class);
        startActivity(intent);
        break;
      default:
        break;
    }

  }
}
