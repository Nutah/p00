package com.vulcaninformatics.p00;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class MainActivity extends Activity {

	
	MapView mapView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this));
		
		/*final Button startB = (Button) findViewById(R.id.startB);
		
		//relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
		
		//mapView = new MapView(this);
		
       // relativeLayout.addView(mapView, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        
        startB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent inten = new Intent(getApplicationContext(), MainmapActivity.class);
				startActivity(inten);
				//switchActivity();
			}
		});*/
		
	}

	public void switchActivity(){
		Intent intent = new Intent(getApplicationContext(),MainmapActivity.class);
		startActivity(intent);
	}
}
