package com.hengtiansoft.tijianba.calendarcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juanliuinformation.lvningmeng.R;

public class CheckableLayout extends RelativeLayout implements Checkable {
  private View view;
  private Context context;
  private TextView mDateTextView, mDateStatusTextView;
  private ImageView mDateImageView, mDateStatusImageView;
  private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
  private boolean checked = false;

  @SuppressLint("NewApi")
  public CheckableLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  public CheckableLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CheckableLayout(Context context) {
    super(context);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    view = inflate(context, R.layout.calendar_date_view, this);
    mDateTextView = (TextView) findViewById(R.id.tv_date);
    mDateImageView = (ImageView) findViewById(R.id.iv_date);
    mDateStatusTextView = (TextView) findViewById(R.id.tv_date_status);
    mDateStatusImageView = (ImageView) findViewById(R.id.iv_date_status);
  }

  @Override
  public boolean isChecked() {
    return checked;
  }

  @Override
  public void setChecked(boolean checked) {
    this.checked = checked;

    refreshDrawableState();

    // Propagate to childs
    final int count = getChildCount();
    for (int i = 0; i < count; i++) {
      final View child = getChildAt(i);
      if (child instanceof Checkable) {
        ((Checkable) child).setChecked(checked);
      }
    }
  }

  @Override
  protected int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }

  @Override
  public void toggle() {
    this.checked = !this.checked;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    // if (changed)
    // getLayoutParams().height = r - l;
  }

  public void setText(String text) {
    mDateTextView.setText(text);

  }

  public void setStatusVisibility(int visibility) {
    mDateStatusTextView.setVisibility(visibility);

  }

  public void setTextColor(int textColor) {
    mDateTextView.setTextColor(textColor);

  }

  public void setImageResource(int imageResource) {
    mDateImageView.setImageResource(imageResource);

  }

  public void setStatusImageResource(int imageResource) {
    mDateStatusImageView.setImageResource(imageResource);

  }

  public void select() {
    mDateTextView.setTextColor(context.getResources().getColor(R.color.white));
    setImageResource(R.drawable.day_selected);
    mDateStatusImageView.setImageResource(R.drawable.day_man);
  }

  public void unSelect(boolean isWeekEnd) {
    setImageResource(R.drawable.day_normal);
    mDateStatusImageView.setImageResource(R.drawable.day_man_blank);
    if (isWeekEnd) {
      mDateTextView.setTextColor(getResources().getColor(R.color.sub_green));
    } else {
      mDateTextView.setTextColor(getResources().getColor(R.color.cart_grey));
    }
  }
}
