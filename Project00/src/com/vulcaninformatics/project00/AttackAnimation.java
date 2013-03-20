package com.vulcaninformatics.project00;


import java.util.List;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;


@SuppressLint("DrawAllocation")
public class AttackAnimation {

	private final String TAG = getClass().getSimpleName();
	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 back, 1 left, 0 front, 2 right
	
	private int x = 0;
	private int y = 0;
	
	private GameView gameView;
	private Bitmap bmp;
	
	private int width;
	private int height;
	private PointF pos;
	
	private int k=0;
	
	private List<Rect> sequence;

	public AttackAnimation(GameView gameView, Bitmap bmp, List<Rect> sequence) {
		
		this.gameView = gameView;
		this.bmp = bmp;
		pos = new PointF(this.width / 2, this.height / 2);
		this.x = this.gameView.getRight() / 2;
		this.y = this.gameView.getBottom() / 2;
		this.sequence = sequence;
	}

	private Rect update() {
		// 15 19 27 39
		// 15 39 71 122
		// (0,15-15,30)
		// 0,15 15x15    (0,15,15,30)
		// 20,14 19x19   (20,14,39,33)
		// 44,9 27x25	 (44,9,71,34)
		// 83,0 39x39	 (83,0,122,39)
		Rect rec = sequence.get(k);
		k++;
		if(k>sequence.size()-1)
		{
			k=0;
		}
		this.pos.x = x + this.width / 2;
		this.pos.y = y + this.height / 2;
		
		return rec;
	}

	public void onDraw(Canvas canvas) {
		Rect src = update();
		
		Rect dst = new Rect(193, 161, 193+src.width(), 162+src.height());
		canvas.drawBitmap(bmp, src, dst, null);

	}

	public PointF getPosition() {
		return this.pos;
	}
}
