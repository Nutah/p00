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
import android.graphics.Point;
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
	boolean doneDownload = false;
	boolean alternate = false;

	Bitmap[] info = new Bitmap[4];

	Bitmap[][] bufferBitmap = new Bitmap[3][3];
	Point bufferBitmapPoint[][] = new Point[3][3];
	String bufferBitmapUrl[][] = new String[3][3];
	BoundingBox bufferBitmapBBox[][] = new BoundingBox[3][3];

	final int ZOOM = 18;
	final int BUFFER_SIZE = 3;
	final int PICTURE_SIZE = 5;

	// test data. location is University of Stuttgart, CS Department
	double latTest = 48.744783;
	double lonTest = 9.106451;

	public MapView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);

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

		fillAll(latTest, lonTest, ZOOM);

		if (!doneDownload) {
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 3; k++) {
					try {
						URL url = new URL(bufferBitmapUrl[i][k]);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.connect(); // Connect to URL
						InputStream is = conn.getInputStream();
						bufferBitmap[i][k] = BitmapFactory.decodeStream(is); // Download
						// from
						// URL as
						// bitmap
						is.close(); // Close connection

					} catch (Exception e) {
						Log.i(TAG, "onTouchEvent exception");
					}
				}
			}
		}
		doneDownload = true;
		invalidate();

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		int tmpX = 0;
		int tmpY = 0;

		if (doneDownload) {
			// TOP
			canvas.drawBitmap(bufferBitmap[0][0], 256 * 0, 256 * 0, p);
			Log.i(TAG, "bufferBitmapBBox[0][0] north: " + bufferBitmapBBox[0][0].north + " east: " + bufferBitmapBBox[0][0].east + " south: " + bufferBitmapBBox[0][0].south + " west: " + bufferBitmapBBox[0][0].west);
			canvas.drawBitmap(bufferBitmap[0][1], 256 * 1, 256 * 0, p);
			Log.i(TAG, "bufferBitmapBBox[0][1] north: " + bufferBitmapBBox[0][1].north + " east: " + bufferBitmapBBox[0][1].east + " south: " + bufferBitmapBBox[0][1].south + " west: " + bufferBitmapBBox[0][1].west);
			canvas.drawBitmap(bufferBitmap[0][2], 256 * 2, 256 * 0, p);
			Log.i(TAG, "bufferBitmapBBox[0][2] north: " + bufferBitmapBBox[0][2].north + " east: " + bufferBitmapBBox[0][2].east + " south: " + bufferBitmapBBox[0][2].south + " west: " + bufferBitmapBBox[0][2].west);
			// CENTRAL
			canvas.drawBitmap(bufferBitmap[1][0], 256 * 0, 256 * 1, p);
			Log.i(TAG, "bufferBitmapBBox[1][0] north: " + bufferBitmapBBox[1][0].north + " east: " + bufferBitmapBBox[1][0].east + " south: " + bufferBitmapBBox[1][0].south + " west: " + bufferBitmapBBox[1][0].west);
			canvas.drawBitmap(bufferBitmap[1][1], 256 * 1, 256 * 1, p);
			Log.i(TAG, "bufferBitmapBBox[1][1] north: " + bufferBitmapBBox[1][1].north + " east: " + bufferBitmapBBox[1][1].east + " south: " + bufferBitmapBBox[1][1].south + " west: " + bufferBitmapBBox[1][1].west);
			canvas.drawBitmap(bufferBitmap[1][2], 256 * 2, 256 * 1, p);
			Log.i(TAG, "bufferBitmapBBox[1][2] north: " + bufferBitmapBBox[1][2].north + " east: " + bufferBitmapBBox[1][2].east + " south: " + bufferBitmapBBox[1][2].south + " west: " + bufferBitmapBBox[1][2].west);
			// BOTTOM
			canvas.drawBitmap(bufferBitmap[2][0], 256 * 0, 256 * 2, p);
			Log.i(TAG, "bufferBitmapBBox[2][0] north: " + bufferBitmapBBox[2][0].north + " east: " + bufferBitmapBBox[2][0].east + " south: " + bufferBitmapBBox[2][0].south + " west: " + bufferBitmapBBox[2][0].west);
			canvas.drawBitmap(bufferBitmap[2][1], 256 * 1, 256 * 2, p);
			Log.i(TAG, "bufferBitmapBBox[2][1] north: " + bufferBitmapBBox[2][1].north + " east: " + bufferBitmapBBox[2][1].east + " south: " + bufferBitmapBBox[2][1].south + " west: " + bufferBitmapBBox[2][1].west);
			canvas.drawBitmap(bufferBitmap[2][2], 256 * 2, 256 * 2, p);
			Log.i(TAG, "bufferBitmapBBox[2][2] north: " + bufferBitmapBBox[2][2].north + " east: " + bufferBitmapBBox[2][2].east + " south: " + bufferBitmapBBox[2][2].south + " west: " + bufferBitmapBBox[2][2].west);

		} else if (defaultBitmap != null) {
			for (int i = 0; i < PICTURE_SIZE; i++) {
				for (int j = 0; j < PICTURE_SIZE; j++) {
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

	/**
	 * Fills all the buffers with the required data given the correct
	 * parameters.
	 * 
	 * @param lat
	 *            Latitude value of current center position.
	 * @param lon
	 *            Longitude value of current center position.
	 * @param zoom
	 *            Default zoom level = 18.
	 */
	private void fillAll(final double lat, final double lon, final int zoom) {
		double result[] = getTileNumberDouble(lat, lon, zoom);
		fillBufferBitmapPoint(result);
		fillBufferBitmapUrl(bufferBitmapPoint, zoom);
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

	/**
	 * Transforms longitude and latitude in the decimaldegree format into
	 * corresponding xtile and ytile values.
	 * 
	 * @param lat
	 *            Latitude value. e.g. 9.106451
	 * @param lon
	 *            Longitude value. e.g. 48.744783
	 * @param zoom
	 *            Default zoom value: 18. Ranges from 1-18 (18 being closest)
	 * @return
	 */
	public double[] getTileNumberDouble(final double lat, final double lon,
			final int zoom) {
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math
				.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1
						/ Math.cos(Math.toRadians(lat)))
						/ Math.PI)
						/ 2 * (1 << zoom));

		double[] res = { xtile, ytile, zoom };
		return res;
	}

	/**
	 * Returns given input into a string which can be used for building a valid
	 * URL.
	 * 
	 * @param input
	 *            Should be {xtile,ytile,zoom}.
	 * @return "zoom/xtile/ytile"
	 */
	public static String getTileDoubleToString(double[] input) {
		if (input != null)
			return ("" + input[2] + "/" + input[0] + "/" + input[1]);
		else
			return null;
	}

	/**
	 * Fills bufferBitmapPoint with the values for xtile and ytile for the url
	 * http://tile.openstreetmap.org/zoom/xtile/ytile.png
	 * 
	 * @param input
	 *            Requires output of getTileNumberDouble() as input.
	 */
	private void fillBufferBitmapPoint(double[] input) {
		int x = (int) input[0];
		int y = (int) input[1];

		// TOP
		bufferBitmapPoint[0][0] = new Point(x - 1, y - 1);
		bufferBitmapPoint[0][1] = new Point(x, y - 1);
		bufferBitmapPoint[0][2] = new Point(x + 1, y - 1);
		// CENTRAL
		bufferBitmapPoint[1][0] = new Point(x - 1, y);
		bufferBitmapPoint[1][1] = new Point(x, y);
		bufferBitmapPoint[1][2] = new Point(x + 1, y);
		// BOTTOM
		bufferBitmapPoint[2][0] = new Point(x - 1, y + 1);
		bufferBitmapPoint[2][1] = new Point(x, y + 1);
		bufferBitmapPoint[2][2] = new Point(x + 1, y + 1);

		fillBufferBitmapUrl(bufferBitmapPoint, ZOOM);
		fillBufferBitmapBBox(bufferBitmapPoint);
	}

	/**
	 * Fills bufferBitmapUrl with the required URLs
	 * http://tile.openstreetmap.org/zoom/xtile/ytile.png
	 * 
	 * @param input
	 *            Requires the output of fillBufferBitmapPoint() as input.
	 * @param zoom
	 *            Zoom level. Default: 18
	 */
	private void fillBufferBitmapUrl(Point[][] input, int zoom) {
		// TOP
		bufferBitmapUrl[0][0] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[0][0].x + "/" + input[0][0].y + ".png";
		bufferBitmapUrl[0][1] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[0][1].x + "/" + input[0][1].y + ".png";
		bufferBitmapUrl[0][2] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[0][2].x + "/" + input[0][2].y + ".png";
		// CENTRAL
		bufferBitmapUrl[1][0] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[1][0].x + "/" + input[1][0].y + ".png";
		bufferBitmapUrl[1][1] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[1][1].x + "/" + input[1][1].y + ".png";
		bufferBitmapUrl[1][2] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[1][2].x + "/" + input[1][2].y + ".png";
		// BOTTOM
		bufferBitmapUrl[2][0] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[2][0].x + "/" + input[2][0].y + ".png";
		bufferBitmapUrl[2][1] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[2][1].x + "/" + input[2][1].y + ".png";
		bufferBitmapUrl[2][2] = "http://tile.openstreetmap.org/" + zoom + "/"
				+ input[2][2].x + "/" + input[2][2].y + ".png";
	}

	/**
	 * Fills up the bufferBitmapBBox with BoundingBoxes and requires
	 * bufferBitmapPoint to be filled with correct data.
	 * 
	 * @param input
	 *            3x3 point buffer with x- and ytile values. Use
	 *            fillBufferBitmapPoint() to get the required results.
	 */
	private void fillBufferBitmapBBox(Point[][] input) {
		for (int i = 0; i < BUFFER_SIZE; i++) {
			for (int k = 0; k < BUFFER_SIZE; k++) {
				bufferBitmapBBox[i][k] = tile2boundingBox(input[i][k].x,
						input[i][k].y, ZOOM);
			}
		}
	}

	class BoundingBox {
		double north;
		double south;
		double east;
		double west;
	}

	BoundingBox tile2boundingBox(final int x, final int y, final int zoom) {
		BoundingBox bb = new BoundingBox();
		bb.north = tile2lat(y, zoom);
		bb.south = tile2lat(y + 1, zoom);
		bb.west = tile2lon(x, zoom);
		bb.east = tile2lon(x + 1, zoom);
		return bb;
	}

	static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}

}
