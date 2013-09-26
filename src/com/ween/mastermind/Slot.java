package com.ween.mastermind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class Slot extends View {
	
	private boolean empty = true;
	private Peg peg;
	
	// Drawing fields
	private Paint outlinePaint;
	private Paint pegPaint;
	private RectF borderRect;
	private float radius;
	private float dp = 2;
	
	Slot(Context context) {
		super(context);
		
		//setAlpha(1);
		radius = 20*dp;
		
		outlinePaint = new Paint();
		outlinePaint.setColor(Color.BLACK);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStrokeWidth(2*dp);
		outlinePaint.setStyle(Style.STROKE);
		
		pegPaint = new Paint();
		pegPaint.setColor(Color.LTGRAY);
		pegPaint.setAntiAlias(true);
		pegPaint.setStyle(Style.FILL);
		
		borderRect = new RectF();
	}

	boolean isEmpty() {
		return empty;
	}
	
	void setPeg(Peg peg) {
		this.peg = peg;
		
		if (peg != null) {
			empty = false;
			peg.setLayoutParams(getLayoutParams());
			int pegColour = peg.getColour();
			pegPaint.setColor(pegColour);
			
			float[] pegColourHSV = new float[3];
			Color.colorToHSV(pegColour, pegColourHSV);
			pegColourHSV[2] *= 0.7;
			outlinePaint.setColor(Color.HSVToColor(pegColourHSV));
		} else {
			empty = true;
			pegPaint.setColor(Color.LTGRAY);
			outlinePaint.setColor(Color.DKGRAY);
		}
		
		invalidate();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		borderRect.set(0, 0, getWidth(), getHeight());
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth()/2, getHeight()/2, radius, outlinePaint);
		canvas.drawCircle(getWidth()/2, getHeight()/2, (radius - 1*dp), pegPaint);
		super.onDraw(canvas);
	}

	Peg getPeg() {
		return peg;
	}
}
