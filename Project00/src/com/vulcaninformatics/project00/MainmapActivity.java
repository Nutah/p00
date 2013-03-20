package com.vulcaninformatics.project00;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainmapActivity extends Activity {

	RelativeLayout relativeLayoutmap;
	MapView mapview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmap);
		
		relativeLayoutmap = (RelativeLayout) findViewById(R.id.relativeLayoutmap);
		
		mapview = new MapView(this);
		
		relativeLayoutmap.addView(mapview);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmap, menu);
		return true;
	}

}
