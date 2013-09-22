package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;

public class SolutionRow extends Row {

	SolutionRow(Context context, ArrayList<Peg> pegs) {
		super(context, pegs.size());
		for (int i = 0; i < pegs.size(); i++) {
			setPeg(i, pegs.get(i));
		}
	}
}
