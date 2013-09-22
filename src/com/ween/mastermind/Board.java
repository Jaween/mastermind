package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class Board extends View {

	private int solutionSize;
	private ArrayList<GuessRow> guessRows;
	private GuessRow currentRow;
	private Row palletRow;
	private SolutionRow solutionRow;
	private int[] score = new int[2];
	
	private Context context;
	
	Board(Context context, RelativeLayout layout, int solutionSize) {
		super(context);
		this.context = context;
		this.solutionSize = solutionSize;
		
		ArrayList<Peg> solutionPegs = makeSolution(solutionSize);
		solutionRow = new SolutionRow(context, solutionPegs);
		
		guessRows = new ArrayList<GuessRow>(solutionSize);
		
		for (int i = 0; i < solutionSize; i++) {
			GuessRow row = new GuessRow(context, solutionSize);
			row.setId(100 + i);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			// Sets the row to be above the previous row, and places the first row at the bottom
			if (i != 0) {
				int previousID = guessRows.get(i - 1).getId();
				params.addRule(RelativeLayout.ABOVE, previousID);
			} else {
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			}
			
			// HEY! I'M CURRENTLY TRYING TO DISPLAY ALL THE ROWS AND SLOTS, ETC. CORRECTLY
			// I'M USING THESE PEGS HERE TO BE PLACED AT THE LOCATION OF THE ROWS SO I CAN
			// SEE WHAT I'M DOING! THE HIERARCHY SHOULD BE AS FOLLOWS: PEGS ARE DRAWN,
			// THEY'RE WITHIN, SLOTS, THERE'S FOUR SLOTS BESIDE EACH OTHER PER ROW 
			// AND MANY STACKED ROWS
			// TEST
			Peg peg = new Peg(context, (int) (Math.random() * Integer.MAX_VALUE));
			peg.setText("i is " + i);
			peg.setTextColor(Color.BLACK);
			Log.d("Board", "Left is " +  peg.getLeft());
			peg.setLayoutParams(params);
			layout.addView(peg);
			
			row.setLayoutParams(params);
			guessRows.add(row);
			layout.addView(row);
		}
		currentRow = guessRows.get(0);
		
		palletRow = createPallet(solutionSize);
	}
	
	private Row createPallet(int size) {
		Row pallet = new Row(context, size);
		for (int i = 0; i < size; i++) {
			pallet.setPeg(i, new Peg(context, (int) (Math.random() * Integer.MAX_VALUE)));
		}
		return pallet;
	}
	
	private ArrayList<Peg> makeSolution(int size) {
		ArrayList<Peg> pegs = new ArrayList<Peg>(size);
		for (int i = 0; i < size; i++) {
			int colour = (int) (Math.random() * 4);
			Peg peg = new Peg(context, colour);
			Log.d("Board", "Solution " + i + " is " + colour);
			pegs.add(peg);
		}
		return pegs;
	}
	
	public boolean setNextPeg(Peg peg) {
		for (int i = 0; i < solutionSize; i++) {
			if (currentRow.getSlot(i).isEmpty()) {
				currentRow.setPeg(i, peg);
				Log.d("Board", "Setting current row peg " + i + " to " + peg.getColour());
				return true;
			}
		}
		return false;
	}
	
	public GuessRow getPegs() {
		return currentRow;
	}
	
	public boolean scoreGuess() {
		// Exits if a slot is empty (also sets peg flags false), if not scores the guess
		boolean pegCounted[] = new boolean[solutionSize];
		for (int i = 0; i < solutionSize; i++) {
			pegCounted[i] = false;
			if (currentRow.getSlot(i).isEmpty()) {
				Log.d("Board", "Slot " + i + " is empty");
				return false;
			} 
 		}
		
		// Counts blacks
		int blacks = 0;		
		for (int i = 0; i < solutionSize; i++) {
			int guessColour = currentRow.getSlot(i).getPeg().getColour();
			int solutionColour = solutionRow.getSlot(i).getPeg().getColour();
			if (guessColour == solutionColour) {
				blacks++;
				pegCounted[i] = true;
			} 
		}
		
		// Counts whites
		int whites = 0;
		for (int i = 0; i < solutionSize; i++) {
			int solutionColour = solutionRow.getSlot(i).getPeg().getColour();
			for (int j = 0; j < solutionSize; j++) {
				int guessColour = currentRow.getSlot(j).getPeg().getColour();
				if (guessColour == solutionColour && pegCounted[j] == false) {
					whites++;
					pegCounted[j] = true;
				}
			}
		}
		
		score[0] = blacks;
		score[1] = whites;

		return true;
	}
	
	public int[] getScore() {
		return score;
	}
	
}
