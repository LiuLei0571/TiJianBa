package com.hengtiansoft.tijianba.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class SlopeGiftTextView extends TextView {

	public SlopeGiftTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SlopeGiftTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SlopeGiftTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onDraw(Canvas canvas) {
        canvas.rotate(-4, getMeasuredWidth()/54, getMeasuredHeight()/27);  
		super.onDraw(canvas);
	}
}
