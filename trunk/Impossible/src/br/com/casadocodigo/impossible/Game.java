package br.com.casadocodigo.impossible;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Game extends Activity implements OnTouchListener {

	Impossible view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Coloca em fullscreen o jogo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Lógica do Jogo
		view = new Impossible(this);

		// Touch Listener.
		view.setOnTouchListener(this);

		// Configura view
		setContentView(view);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Toast.makeText(Game.this, "Carregando o jogo...", Toast.LENGTH_LONG).show();

		view.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		view.moveDown(10);
		view.addScore(100);
		return true;
	}

}
