package com.ween.mastermind;

import android.content.Context;
import android.view.View;

public class Slot extends View {
	
	boolean empty;
	Peg peg;
	
	Slot(Context context) {
		super(context);
		empty = true;
	}
	
	boolean isEmpty() {
		return empty;
	}
	
	void setPeg(Peg peg) {
		this.peg = peg;
		peg.setLayoutParams(getLayoutParams());
		empty = false;
	}
	
	Peg getPeg() {
		return peg;
	}
}
