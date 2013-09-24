package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Row extends ViewGroup implements OnClickListener, OnLongClickListener, OnTouchListener {

	protected int size;
	protected ArrayList<Slot> slots;
	
	protected  RelativeLayout layout;
	private Context context;
	private int padding;
	
	Row(Context context, int size, RelativeLayout layout) {
		super(context);
		this.context = context;;
		this.size = size;
		
		float dp = 2;
		padding = (int) (2*dp);
		
		createSlots();
	}
	
	// Creates and performs layout of slots
	public void createSlots() {
		slots = new ArrayList<Slot>(size);
		for (int i = 0; i < size; i++) {
			Slot slot = new Slot(context);

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_TOP, getId());
			if (i == 0) {
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			} else {
				int previousID = slots.get(i - 1).getId();
				params.addRule(RelativeLayout.RIGHT_OF, previousID);
			}

			slot.setLayoutParams(params);
			slot.setId(201 + i);
			slot.setOnClickListener(this);
			slot.setOnLongClickListener(this);
			slots.add(slot);
			
			// Attach the slot to this ViewGroup (make visible)
			addView(slot);
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

	@Override
	public void onClick(View v) {
		Slot slot = (Slot) v;
		slot.setPeg(null);
		Log.d("Clicked " + v.getId(), "Left is " + v.getLeft() + ", top is " + v.getTop() + ", right is " + v.getRight() + ", bottom is " + v.getBottom() +  ", size is (" + v.getWidth() + ", " + v.getHeight() + ")");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int spaces = slots.size();
		int slotWidth = getWidth()/slots.size() + padding;
		for (int i = 0; i < slots.size(); i++)
		{
			slots.get(i).layout(i*slotWidth , 0, getWidth() - (slots.size() - i - 1)*slotWidth, getHeight());
		}
		
	}

	@Override
	public boolean onLongClick(View view) {
		if (view instanceof Slot) {
			Slot slot = new Slot(context);
			layout.addView(slot);
			slot.setOnTouchListener(this);
		}
		return false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (view instanceof Slot){
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				view.setLeft((int) event.getX()/2);
				view.setTop((int) event.getY()/2);
			}
		}
		return false;
	}	
}
