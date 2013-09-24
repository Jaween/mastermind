package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.widget.RelativeLayout;

public class SolutionRow extends Row {

	SolutionRow(Context context, ArrayList<Peg> pegs, RelativeLayout layout) {
		super(context, pegs.size(), layout);
		for (int i = 0; i < pegs.size(); i++) {
			setPeg(i, pegs.get(i));
		}
	}
}
