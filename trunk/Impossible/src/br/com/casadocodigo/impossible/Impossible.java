package br.com.casadocodigo.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable {
	
	boolean running = false;
	Thread renderThread = null;
	
	SurfaceHolder holder;
	Paint paint;
	private int playerY = 300;
	private float enemyRadius;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	@Override
	public void run() {
		while (running) {
			System.out.println("Impossible is Running...!");
			
			// verifica se a tela já esetá pronta.
			if (!holder.getSurface().isValid()) {
				continue;
			}
			
			// bloqueia o canvas.
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			
			// desenha o player e o inimigo.
			drawPlayer(canvas);
			drawEnemy(canvas);
			
			// atualiza e libera o canvas
			holder.unlockCanvasAndPost(canvas);
		}
		
	}
	
	private void drawPlayer(Canvas canvas) {
		System.out.println("Desenhando o player...");
		paint.setColor(Color.GREEN);
		canvas.drawCircle(300, playerY, 50, paint);
	}
	
	private void drawEnemy(Canvas canvas) {
		paint.setColor(Color.GRAY);
		enemyRadius++;
		canvas.drawCircle(100, 100, enemyRadius, paint);
	}
	
	public void moveDown(int pixels) {
		System.out.println("Movendo o player...");
		playerY += pixels;
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
}
