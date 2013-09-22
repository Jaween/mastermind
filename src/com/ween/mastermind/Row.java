package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class Row extends View {

	protected int size;
	protected ArrayList<Slot> slots;
	
	Row(Context context, int size) {
		super(context);
		this.size = size;
		slots = new ArrayList<Slot>(size);
		for (int i = 0; i < size; i++) {
			Slot slot = new Slot(context);
			slot.setId(200 + i);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);

			if (i != 0) {
				int previousID = slots.get(i - 1).getId();
				params.addRule(RelativeLayout.RIGHT_OF, previousID);
			} else {
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			}
			slot.setLayoutParams(params);
			slots.add(slot);
		}
	}
	
	public Slot getSlot(int position)  {
		return slots.get(position);
	}
	
	public boolean setPeg(int position, Peg peg) {
		if (position < size) {
			slots.get(position).setPeg(peg);
			return true;
		} else {
			return false;
		}
	}	
}
