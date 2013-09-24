package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Board extends View implements OnClickListener {

	private int solutionSize;
	private int numberOfRows;
	private int[] score = new int[2];
	
	private ArrayList<GuessRow> guessRows;
	private GuessRow currentRow;
	private Row paletteRow;
	private SolutionRow solutionRow;
	
	private RelativeLayout layout;
	private float dp = 2;
	private int margin = (int) (4*dp);
	
	private Context context;
	
	Board(Context context, RelativeLayout layout, int solutionSize) {
		super(context);
		this.context = context;
		this.layout = layout;
		this.solutionSize = solutionSize;
		
		numberOfRows = 7;
		
		createRows();
		currentRow = guessRows.get(0);
	}
	
	// Creates and performs the layout of the palette, guess rows and solution row
	private void createRows() {
		
		// Create the palette and position it
		RelativeLayout.LayoutParams paletteParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 96);
		paletteParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		paletteRow = createPalette();
		paletteRow.setId(10101);
		paletteRow.setLayoutParams(paletteParams);
		layout.addView(paletteRow);
		
		// Creates the guess rows
		guessRows = new ArrayList<GuessRow>(solutionSize);
		for (int i = 0; i < numberOfRows; i++) {
			GuessRow guessRow = new GuessRow(context, solutionSize, layout);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 96);
			params.topMargin = margin;
			params.bottomMargin = margin;

			// First row above the palette, subsequent rows stacked above
			if (i == 0) {
				int paletteID = paletteRow.getId();
				params.addRule(RelativeLayout.ABOVE, paletteID);
			} else {
				int previousID = guessRows.get(i - 1).getId();
				params.addRule(RelativeLayout.ABOVE, previousID);
			}

			int id = 100 + i;
			guessRow.setId(id);
			guessRow.setLayoutParams(params);
			guessRows.add(guessRow);
			layout.addView(guessRow);
			
//			RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(96, 96);
//			buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//			buttonParams.addRule(RelativeLayout.ALIGN_BASELINE, guessRow.getId());
//			Button okButton = new Button(context);
//			okButton.setText("OK");
//			okButton.setLayoutParams(buttonParams);
//			layout.addView(okButton);			
		}
	}
	
	
	private Row createPalette() {
		Row pallet = new Row(context, solutionSize, layout);
		for (int i = 0; i < solutionSize; i++) {
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

	@Override
	public void onClick(View v) {
		Log.d("Board", "Clicked peg " + v.getId());
		Log.d("Board", "There are " + guessRows.size() + " guess rows");
	}
	
}
