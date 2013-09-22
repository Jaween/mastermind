package com.ween.mastermind;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameActivity extends Activity implements OnClickListener {
	
	Button scoreButton;
	Board board;
	RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		layout = (RelativeLayout) findViewById(R.id.rlMain);
		
		int size = 4;
		board = new Board(getApplicationContext(), layout, size);
		
		initialiseLayout();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	void initialiseLayout() {
		layout = (RelativeLayout) findViewById(R.id.rlMain);
		
		scoreButton = (Button) findViewById(R.id.btScoreButton);
		scoreButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		switch(id) {
			case R.id.btScoreButton:
				int[] score = board.getScore();
				Log.d("Game Activity", "Scored B:" + score[0] + ", W:" + score[1]);
				break;
		}
	}

}
