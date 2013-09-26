package com.ween.mastermind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class Peg extends View {
	private int colour;
	private Paint paint;
	
	Peg(Context context, int colour) {
		super(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		setColour(colour);
	}
	
	int getColour() {
		return colour;
	}
	
	void setColour(int colour) {
		this.colour = colour;
		paint.setColor(colour);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		//Log.d("Peg", "Size is (" + getWidth() + ", " + getHeight() + ")");
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth()/2, getHeight()/2, getHeight()/2, paint);
		canvas.drawCircle(50, 50, getHeight()/2, paint);
		super.onDraw(canvas);
	}
	
	
}
