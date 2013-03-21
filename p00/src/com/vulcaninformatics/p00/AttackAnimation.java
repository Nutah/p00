package com.vulcaninformatics.p00;


import java.util.List;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.provider.SyncStateContract.Columns;


@SuppressLint("DrawAllocation")
public class AttackAnimation {

	private final String TAG = getClass().getSimpleName();
	private int currentFrame = 0;
	private static final int BMP_ROWS = 1;
	private static final int BMP_COLUMNS = 4;
	
	private int x = 0;
	private int y = 0;
	
	private GameView gameView;
	private Bitmap bmp;
	
	private int width;
	private int height;
	private PointF pos;
	private Rect destination;


	

	public AttackAnimation(GameView gameView, Bitmap bmp) {
		
		this.gameView = gameView;
		this.bmp = bmp;
		pos = new PointF(this.width / 2, this.height / 2);
		this.x = this.gameView.getRight() / 2;
		this.y = this.gameView.getBottom() / 2;
		
		this.width = bmp.getWidth() /4;
		this.height = bmp.getHeight();
	}

	private void update() {
		
		currentFrame = ++currentFrame % BMP_COLUMNS;
		
		this.pos.x = x + this.width / 2;
		this.pos.y = y + this.height / 2;
	}

	public void onDraw(Canvas canvas) {
		update();

		int srcX = currentFrame * width;
		int srcY = (BMP_ROWS-1)*height;
		
		Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
		Rect dst = new Rect(destination.left, destination.top, destination.left+width, destination.top+height);
		canvas.drawBitmap(bmp, src, dst, null);
		System.out.println("currentFrame: " + currentFrame);
	}

	public PointF getPosition() {
		return this.pos;
	}
	
	public Rect getDestination() {
		return destination;
	}

	public void setDestination(Rect destination) {
		this.destination = destination;
	}
	
	public void reset() {
		this.currentFrame = 0;
	}
}
