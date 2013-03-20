package com.vulcaninformatics.project00;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class GameLoopThread extends Thread {
	static final long FPS = 10;
    private GameView view;
    private boolean running = false;
   
    public GameLoopThread(GameView view) {
          this.view = view;
    }

    public void setRunning(boolean run) {
          running = run;
    }

    @Override
    public void run() {
          while (running) {
                 Canvas c = null;
                 try {
                        c = view.getHolder().lockCanvas();
                        synchronized (view.getHolder()) {
                               view.onDraw(c);
                        }
                 } finally {
                        if (c != null) {
                               view.getHolder().unlockCanvasAndPost(c);
                        }
                 }
          }
    }
}  
