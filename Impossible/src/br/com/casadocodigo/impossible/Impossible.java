package br.com.casadocodigo.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable {

	boolean running = false;
	Thread renderThread = null;

	SurfaceHolder holder;
	Paint paint;
	private int playerX = 300, playerY = 300, playerRadius = 50;
	private float enemyX, enemyY, enemyRadius;
	private double distance;
	private boolean gameOver;
	private int score;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	@Override
	public void run() {
		while (running) {
			System.out.println("Impossible is Running...!");

			// verifica se a tela j� est� pronta.
			if (!holder.getSurface().isValid()) {
				continue;
			}

			// bloqueia o canvas.
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);

			// desenha o player e o inimigo.
			drawPlayer(canvas);
			drawEnemy(canvas);

			// detecta a colis�o.
			checkColision(canvas);

			if (gameOver) {
				stopGame(canvas);
				break;
			}

			// atualiza o placar
			drawScore(canvas);

			// atualiza e libera o canvas
			holder.unlockCanvasAndPost(canvas);
		}

	}

	private void drawPlayer(Canvas canvas) {
		System.out.println("Desenhando o player...");
		paint.setColor(Color.GREEN);
		canvas.drawCircle(playerX, playerY, playerRadius, paint);
	}

	private void drawEnemy(Canvas canvas) {
		paint.setColor(Color.GRAY);
		enemyRadius++;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}

	private void drawScore(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(20);
		canvas.drawText(new String("Score: ").concat(String.valueOf(score)), 5, 20, paint);
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

	private void checkColision(Canvas canvas) {
		// calcula a hipotenusa.
		distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);

		// verifica a distancia entre os raios.
		if (distance <= playerRadius + enemyRadius) {
			System.out.println("Colis�o detectada...");
			gameOver = true;
		}
	}
	
	private void stopGame(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.RED);
		paint.setTextSize(70);
		canvas.drawText("GAME OVER!", 30, 350, paint);
		holder.unlockCanvasAndPost(canvas);
	}

	public void addScore(int points) {
		score += points;
	}
}
