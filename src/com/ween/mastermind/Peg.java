package com.ween.mastermind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;

public class Peg extends Button {
	private int colour;
	private Paint paint;
	
	Peg(Context context, int colour) {
		super(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		setColour(colour);
		
		setText("Peg ID " + getId());
	}
	
	int getColour() {
		return colour;
	}
	
	void setColour(int colour) {
		this.colour = colour;
		paint.setColor(colour);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth()/2, getHeight()/2, getHeight()/2, paint);
		canvas.drawCircle(50, 50, getHeight()/2, paint);
		super.onDraw(canvas);
	}
	
	
}
