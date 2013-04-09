package com.vulcaninformatics.p00;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {

	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> badasses = new ArrayList<Sprite>();
	private Sprite hero;
	private MapView mapView;
	
	
	boolean bAlternate = true;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		mapView = new MapView(context); 
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
		mapView.onDraw(canvas);
		for (Sprite sprite : badasses) {
			sprite.onDraw(canvas);
		}
		hero.onDraw(canvas);

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
	}

	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		Bitmap bmpAttack = BitmapFactory.decodeResource(getResources(), R.drawable.basicattack3);
		return new Sprite(this, bmp,bmpAttack,4, 3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mapView.onTouch(this, event);
	}

}
