package com.hengtiansoft.tijianba.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.hengtiansoft.tijianba.model.Question;
import com.juanliuinformation.lvningmeng.R;

/**
 * 
 * com.hengtiansoft.tijianba.adapter.QuestionAdapter
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 11, 2014 3:09:00 PM
 */
public class QuestionAdapter extends BaseListAdapter<Question> {

  private Context mContext;
  private LayoutInflater mInflater;
  private ViewHolder holder;
  private ArrayList<Question> data;
  private QuestionAdapterListener listener;

  public void setListener(QuestionAdapterListener listener) {
    this.listener = listener;
  }

  public QuestionAdapter(Context context, ArrayList<Question> list) {
    super(context, list, R.layout.question_item);
    this.mContext = context;
    this.data = list;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Question getItem(int position) {
    return data.get(position);
  }

  @Override
  public View getView(final int pos, View view, ViewGroup arg2) {
    ViewHolder holder = null;
    final Question item = getItem(pos);
    if (view == null) {
      // view = inflateLayout();
      holder = new ViewHolder();
      view = LayoutInflater.from(mContext).inflate(R.layout.question_item, null);
      holder.mTitleTextView = (TextView) view.findViewById(R.id.tv_que_title);
      holder.mOptionGroup = (RadioGroup) view.findViewById(R.id.rg_option);
      holder.mOptoionAButton = (RadioButton) view.findViewById(R.id.rb_option_A);
      holder.mOptoionBButton = (RadioButton) view.findViewById(R.id.rb_option_B);
      holder.mOptoionCButton = (RadioButton) view.findViewById(R.id.rb_option_C);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    try {
      boolean flag = true;
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).getAnswer() == null || data.get(i).getAnswer() == "") {
          flag = false;
          break;
        }
      }
      if (flag) {
        listener.onQuestionAdapterListener();
      }
      holder.mTitleTextView.setText(item.getTitle());
      holder.mOptoionAButton.setText(item.getOption().get(0));
      holder.mOptoionBButton.setText(item.getOption().get(1));
      holder.mOptoionCButton.setText(item.getOption().get(2));
      holder.mOptionGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
          if (checkedId == R.id.rb_option_A) {
            data.get(pos).setAnswer("A");
          } else if (checkedId == R.id.rb_option_B) {
            data.get(pos).setAnswer("B");
          } else {
            data.get(pos).setAnswer("C");
          }
          notifyDataSetChanged();
        }
      });
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return view;
  }

  class ViewHolder {
    public TextView mTitleTextView;
    public RadioGroup mOptionGroup;
    public RadioButton mOptoionAButton;
    public RadioButton mOptoionBButton;
    public RadioButton mOptoionCButton;

    void initHolder(View view) {
      mTitleTextView = (TextView) view.findViewById(R.id.tv_que_title);
      mOptionGroup = (RadioGroup) view.findViewById(R.id.rg_option);
      mOptoionAButton = (RadioButton) view.findViewById(R.id.rb_option_A);
      mOptoionBButton = (RadioButton) view.findViewById(R.id.rb_option_B);
      mOptoionCButton = (RadioButton) view.findViewById(R.id.rb_option_C);
    }
  }

  public interface QuestionAdapterListener {
    public void onQuestionAdapterListener();

  }
}
