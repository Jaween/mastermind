package com.ween.mastermind;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;


public class GuessRow extends Row implements OnClickListener {

	private boolean highlighted = false;
	private Button okButton;
	private RowBackground background;
	
	GuessRow(Context context, int size, RelativeLayout layout, Button okButton) {
		super(context, size, layout);
		
		background = new RowBackground(context);
		addView(background);
		
		// Score Button
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(96, RelativeLayout.LayoutParams.WRAP_CONTENT);
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		this.okButton = okButton;
		okButton.setLayoutParams(buttonParams);
		okButton.setEnabled(false);
		addView(okButton);

		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).setOnClickListener(this);
		}
	}
	
	void setHint(int[] hints) {
		
	}
	
	@Override
	public void onClick(View view) {
		// Temporary method to remove a peg from it's slot
		Slot slot = (Slot) view;
		slot.setPeg(null);
		toggleScoreButton();
		
		view.invalidate();
	}
	
	@Override
	public boolean setPeg(int position, Peg peg) { 
		boolean success = super.setPeg(position, peg);
		toggleScoreButton();
		return success;
	}

	public void removeOKButton() {
		removeView(okButton);
	}
	
	private void toggleScoreButton() {
		for (int i = 0; i < size; i++) {
			if (slots.get(i).isEmpty()) {
				okButton.setEnabled(false);
				return;
			}
		}
		okButton.setEnabled(true);
		return;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (highlighted)
			background.layout(0, 0, getWidth(), getHeight());
		
		int spaces = slots.size() + 1;
		int slotWidth = getWidth()/spaces + padding;
		for (int i = 0; i < slots.size(); i++)
		{
			slots.get(i).layout(i*slotWidth , 0, getWidth() - (spaces - i - 1)*slotWidth, getHeight());
		}
		okButton.layout((spaces - 1)*slotWidth, 0, getWidth(), 96);		
	}

	void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		
		if (!highlighted)
			removeView(background);
	}
}
