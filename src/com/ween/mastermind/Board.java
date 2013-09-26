package com.ween.mastermind;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class Board extends View implements OnClickListener, OnTouchListener {

	private int solutionSize;
	private int numberOfRows;
	private int[] score = new int[2];
	private int guessNumber = 0;
	
	private ArrayList<GuessRow> guessRows;
	private GuessRow currentRow;
	private Row paletteRow;
	private SolutionRow solutionRow;
	private ArrayList<Button> okButtons;
	
	private RelativeLayout layout;
	private float dp = 2;
	private int margin = (int) (4*dp);
	
	private final int OKBUTTON_ID = 700;
	
	private Context context;
	
	
	private RelativeLayout.LayoutParams pegParams;
	private int downX = 0;
	private int downY = 0;
	
	Board(Context context, RelativeLayout layout, int solutionSize) {
		super(context);
		this.context = context;
		this.layout = layout;
		this.solutionSize = solutionSize;
		
		numberOfRows = 7;
		createOKButtons();
		createRows();
		solutionRow = new SolutionRow(context, generateSolution(), layout);
		
		currentRow = guessRows.get(0);
	}

	private void createOKButtons() {
		okButtons = new ArrayList<Button>(numberOfRows);
		for (int i = 0; i < numberOfRows; i++) {
			Button okButton = new Button(context);
			okButton.setText("OK");
			okButton.setId(OKBUTTON_ID + i);
			okButton.setOnClickListener(this);
			okButton.setTextColor(Color.BLACK);
			okButtons.add(okButton);
		}
	}
	
	// Creates and performs the layout of the palette, guess rows and solution row
	private void createRows() {
		
		// Create the palette and position it
		RelativeLayout.LayoutParams paletteParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 96);
		paletteParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		paletteRow = createPalette();
		paletteRow.setId(500);
		paletteRow.setLayoutParams(paletteParams);
		layout.addView(paletteRow);
		
		// Creates the guess rows
		guessRows = new ArrayList<GuessRow>(solutionSize);
		for (int i = 0; i < numberOfRows; i++) {
			GuessRow guessRow = new GuessRow(context, solutionSize, layout, okButtons.get(i));
			
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
			
			// Only make the first row visible
			if (i <= guessNumber)
			{
				guessRow.setHighlighted(true);
				layout.addView(guessRow);
			}
		}
	}
	
	private Row createPalette() {
		pegParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		pegParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		int paletteSize = 6;
		Row palette = new Row(context, paletteSize, layout);
		for (int i = 0; i < paletteSize; i++) {
			Peg palettePeg = new Peg(context, (int) (Math.random() * Integer.MAX_VALUE));
			palettePeg.setOnTouchListener(this);
			palettePeg.setOnClickListener(this);
			
			pegParams.addRule(RelativeLayout.ALIGN_BASELINE, palette.getSlot(i).getId());
			palettePeg.setLayoutParams(pegParams);
			
			palette.setPeg(i, palettePeg);
			//layout.addView(palettePeg);
			
			// Temporary peg selection
			Slot slot = palette.getSlot(i);
			slot.setOnTouchListener(this);
		}
		return palette;
	}
	
	private ArrayList<Peg> generateSolution() {
		int paletteSize = paletteRow.getSize();
		ArrayList<Peg> solutionPegs = new ArrayList<Peg>(solutionSize);
		for (int i = 0; i < solutionSize; i++) {
			int randomPalettePeg = (int) (Math.random() * paletteSize);
			Peg peg = paletteRow.getSlot(randomPalettePeg).getPeg();
			solutionPegs.add(peg);
			
			int colour = peg.getColour();
		}
		return solutionPegs;
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
	
	public void switchToNextRow() {
		guessNumber++;
		currentRow.setHighlighted(false);
		currentRow.removeOKButton();
		currentRow = guessRows.get(guessNumber);
		currentRow.setHighlighted(true);
		layout.addView(currentRow);	
	}
	
	@Override
	public void onClick(View view) {
		if (view instanceof Button) {
			// Displays the score
			if (view.getId()  <= OKBUTTON_ID + numberOfRows - 1) {
				scoreGuess();
				int[] score = getScore();
				Toast.makeText(context, "Scored B:" + score[0] + ", W:" + score[1], Toast.LENGTH_SHORT).show();
				
			} 
			
			// We are have guesses to go so move to the next row
			if (view.getId()  < OKBUTTON_ID + numberOfRows - 1) {
				switchToNextRow();				
			}
		} 
		
//		if (view instanceof Peg)
//			setNextPeg((Peg) view);
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (view instanceof Peg) {
			pegParams = (LayoutParams) view.getLayoutParams();
			Log.d("Touch", "Params are " + pegParams.leftMargin);
			
			switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					downX = (int) event.getX(0);
					downY = (int) event.getY(0);
					break;
				case MotionEvent.ACTION_MOVE:
					pegParams.leftMargin += (int) event.getX(0) - downX;
					pegParams.topMargin += (int) event.getY(0) - downY;
					view.setLayoutParams(pegParams);
					break;
				case MotionEvent.ACTION_UP:
					break;
			}
		}
		
		if (view instanceof Slot)
			setNextPeg(((Slot) view).getPeg());
		return false;
	}
	
}
