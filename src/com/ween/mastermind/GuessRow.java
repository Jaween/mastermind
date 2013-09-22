package com.ween.mastermind;

import android.content.Context;


public class GuessRow extends Row {

	boolean highlighted = false;
	
	GuessRow(Context context, int size) {
		super(context, size);
	}
	
	void setHint(int[] hints) {
		
	}
	
	void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
