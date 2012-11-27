package com.project.flickhockey;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Window;

public class Flick_Hockey extends Activity {
	private int width, height;
	private MediaPlayer mp;
	private SoundPool soundPool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();

		mp = new MediaPlayer();
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

		setContentView(new Panel(this, width, height,mp,soundPool));
	}

}
