package com.ween.mastermind;

import android.content.Context;
import android.widget.RelativeLayout;


public class GuessRow extends Row {

	boolean highlighted = false;
	
	GuessRow(Context context, int size, RelativeLayout layout) {
		super(context, size, layout);

		this.size = size;
		this.layout = layout;
	}
	
	void setHint(int[] hints) {
		
	}
	
	void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
