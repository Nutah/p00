package com.vulcaninformatics.project00;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {

	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> badasses = new ArrayList<Sprite>();
	private Sprite hero;
	private AttackAnimation basicAttack;
	
	boolean bAlternate = true;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for (Sprite sprite : badasses) {
			sprite.onDraw(canvas);
		}
		hero.onDraw(canvas);
		basicAttack.onDraw(canvas);
		synchronized (getHolder()) {
            for (int i = badasses.size() - 1; i >= 0; i--) 
            {
            	Sprite bad = badasses.get(i);
            	
            	if(hero.isCollision(bad.getPosition()) && !bad.isAttack())
            	{
            		bad.setAttack(true);
            		hero.setAttack(true);
            		break;
            	}
            	else if(hero.isCollision(bad.getPosition()) && bad.isAttack())
            	{
            		bad.setFlee(true);
            		hero.setFlee(true);
            		break;
            	}
            	else if(!hero.isCollision(bad.getPosition()) && bad.isFlee())
            	{
            		bad.setAttack(false);
            		bad.setFlee(false);
            		hero.setAttack(false);
            		hero.setFlee(false);
            		break;
            	}
            
            }
		}
	}

	private void createSprites() {
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		badasses.add(createSprite(R.drawable.badass));
		hero = createSprite(R.drawable.hero);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.basicattack);
		List<Rect> list = new ArrayList<Rect>();
		list.add(new Rect(0,15,15,30));
		list.add(new Rect(20,14,39,33));
		list.add(new Rect(44,9,71,34));
		list.add(new Rect(83,0,122,39));
		basicAttack = new AttackAnimation(this, bmp, list);
	}

	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this, bmp,4,3);
	}

}
