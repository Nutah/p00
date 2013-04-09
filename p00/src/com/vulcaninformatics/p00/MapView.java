/**
 * 
 */
package com.vulcaninformatics.p00;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

/**
 * @author bmr
 * 
 */
public class MapView extends View implements OnTouchListener {

	private final String TAG = getClass().getSimpleName();

	Bitmap bitmap;
	Bitmap locationMarker;
	Bitmap defaultBitmap;
	Paint p = new Paint();
	boolean done = false;
	boolean alternate = false;

	Bitmap[] info = new Bitmap[4];

	public MapView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		// this.setOnTouchListener(this);

		defaultBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blankmap);

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return onTouch(getRootView(), event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		String obenlinks = "http://tile.openstreetmap.org/18/137702/90307.png";
		String obenrechts = "http://tile.openstreetmap.org/18/137703/90307.png";
		String untenlinks = "http://tile.openstreetmap.org/18/137702/90308.png";
		String untenrechts = "http://tile.openstreetmap.org/18/137703/90308.png";
		String addy[] = { obenlinks, obenrechts, untenlinks, untenrechts };
		if (!done) {
			for (int i = 0; i < addy.length; i++) {

				try {
					URL url = new URL(addy[i]);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect(); // Connect to URL
					InputStream is = conn.getInputStream();
					info[i] = BitmapFactory.decodeStream(is); // Download from
																// URL as bitmap
					is.close(); // Close connection

				} catch (Exception e) {
					Log.i(TAG, "onTouchEvent exception");
				}
			}
		}
		done = true;
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawBitmap(defaultBitmap, null, p);

		int tmpX = 0;
		int tmpY = 0;

		if (info[0] != null && info[1] != null && info[2] != null && info[3] != null) {
			canvas.drawBitmap(info[0], 0, 0, p);

			canvas.drawBitmap(info[1], 256, 0, p);

			canvas.drawBitmap(info[2], 0, 256, p);

			canvas.drawBitmap(info[3], 256, 256, p);
		} else if (defaultBitmap != null) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					canvas.drawBitmap(defaultBitmap, tmpX, tmpY, p);
					tmpX += 72;
				}
				tmpX = 0;
				tmpY += 72;
			}

		} else {
			Toast.makeText(super.getContext(), "text", Toast.LENGTH_SHORT)
					.show();
		}

		
	}

	public static String getTileNumber(final double lat, final double lon,
			final int zoom) {
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math
				.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1
						/ Math.cos(Math.toRadians(lat)))
						/ Math.PI)
						/ 2 * (1 << zoom));

		return ("" + zoom + "/" + xtile + "/" + ytile);
	}

}
