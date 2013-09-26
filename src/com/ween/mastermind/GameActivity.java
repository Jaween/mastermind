package com.ween.mastermind;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	
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
	}

}
