package com.ween.mastermind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class RowBackground extends View {

	
	private final int backgroundColor = 0x77DBDBDB;
	
	public RowBackground(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(backgroundColor);
		super.onDraw(canvas);
	}
	
	

}
