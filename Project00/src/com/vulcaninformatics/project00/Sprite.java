package com.vulcaninformatics.project00;

import java.util.Arrays;
import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

@SuppressLint("DrawAllocation")
public class Sprite {
	private final String TAG = getClass().getSimpleName();
	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 back, 1 left, 0 front, 2 right
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private static final int MAX_SPEED = 5;
	private static final int COLLISION_RADIUS = 17;
	private int x = 0;
	private int y = 0;
	private int xSpeed;
	private GameView gameView;
	private Bitmap bmp;
	private int currentFrame = 0;
	private int width;
	private int height;
	private int ySpeed;
	private PointF pos;
	private boolean attack;
	private boolean flee;



	public Sprite(GameView gameView, Bitmap bmp, int rows, int columns) {
		this.width = bmp.getWidth() / columns;
		this.height = bmp.getHeight() / rows;
		this.gameView = gameView;
		this.bmp = bmp;
		this.attack = false;
		this.flee = false;
		pos = new PointF(this.width/2,this.height/2);
		

		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}

	private void update() {
		if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0 || attack) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0 || attack) {
			ySpeed = -ySpeed;
			System.out.println(this.TAG + " " + hashCode() +  " xSpeed: " + xSpeed + " ySpeed: " + ySpeed);
		}
		
		
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;

		this.pos.x = x + this.width / 2;
		this.pos.y = y + this.height / 2;
	}

	public void onDraw(Canvas canvas) {
		update();
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
	
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
		
	}

	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 back, 1 left, 0 front, 2 right
	private int getAnimationRow() {
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

	public boolean isCollision(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}

	public boolean isCollision(PointF p) {
		double distance = Math.sqrt(Math.pow(Math.abs(p.x - this.pos.x), 2)
				+ Math.pow(Math.abs(p.y - this.pos.y),2));

		if (distance < COLLISION_RADIUS*3)
		{
			Log.i(TAG, "Treffer!");
			return true;
		}
		else
		{
			return false;
		}
	}

	public PointF getPosition() {
		return this.pos;
	}

	public void setAttack(boolean value) {
		this.attack = value;
	}
	
	public boolean isAttack()
	{
		return this.attack;
	}
	
	public boolean isFlee() {
		return flee;
	}

	public void setFlee(boolean flee) {
		this.flee = flee;
	}

}
