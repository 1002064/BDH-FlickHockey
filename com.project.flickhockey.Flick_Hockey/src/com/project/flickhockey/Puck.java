package com.project.flickhockey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.SoundPool;

public class Puck extends Graphic {
	private SoundPool soundPool;
	private int soundPuckShoot, soundPuckBarrierHit;
	private float flingStartX, flingStartY;
	private long startTime;
	private final float SPEED_AFTER_BORDER_COLLISION = 0.9f;
	Context context;

	public Puck(Bitmap bitmap, Context context) {
		super(bitmap);
		this.context = context;
		flingStartX = 0;
		flingStartY = 0;
		startTime = 0;
	}

	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
		soundPuckShoot = soundPool.load(context, R.raw.flickstick01, 1);
		soundPuckBarrierHit = soundPool.load(context, R.raw.barrier, 1);
	}

	public void playBarrierSound() {
		soundPool.play(soundPuckBarrierHit, 1.0f, 1.0f, 0, 0, 1.0f);
	}

	public void updatePosition() {
		Point position = super.getTopLeftCorner();
		super.setTopLeftCorner((int) (position.x + super.getSpeedX()),
				(int) (position.y + super.getSpeedY()));
	}

	public void setFlingStartPosition(float x, float y) {
		flingStartX = x;
		flingStartY = y;
	}

	public void calculateFlingSpeed(float x, float y) {
		if (x != flingStartX) {
			float Xdiff = x - flingStartX;
			float timeDiff = getTimeDifference() / 20.0f;
			this.setSpeedX(Xdiff / timeDiff);
		} else {
			this.setSpeedX(0.0f);
		}
		if (y != flingStartY) {
			float Ydiff = y - flingStartY;
			float timeDiff = getTimeDifference() / 20.0f;
			this.setSpeedY(Ydiff / timeDiff);
		} else {
			this.setSpeedY(0.0f);
		}

	}

	public void setStartTime(long currentTimeMillis) {
		startTime = currentTimeMillis;

	}

	private long getTimeDifference() {
		return System.currentTimeMillis() - startTime;
	}

}
