package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.SoundPool;
import android.util.FloatMath;
import android.util.Log;

public class GoalKeeper extends AnimatedGraphic {

	private float goalKeeperMovingDegree = 0.0f;
	private float goalKeeperSpeed = 0.08f;
	private int screenheight, screenWidth;
	private SoundPool soundPool;

	public GoalKeeper(Bitmap bitmap) {
		super(bitmap);
	}

	public void updateAnimation() {
		if (goalKeeperMovingDegree >= (float)Math.PI * 2) {
			goalKeeperMovingDegree = 0.0f;
		}
		// Log.v("goalKeeperMovingDegree", goalKeeperMovingDegree + "");
		super.setPosX(screenWidth / 2.0f - super.getWidth() / 2.0f
				+ FloatMath.sin(goalKeeperMovingDegree) * 30);
		goalKeeperMovingDegree += goalKeeperSpeed;
	}

	@Override
	public RectF getCollisionRect() {
		RectF keeperCollisionRect = super.getCollisionRect();
		keeperCollisionRect.top -= this.getHeight() * 0.8;
		return keeperCollisionRect;
	}
	public Rect getVisibleRect() {
		Rect visibleKeeperRect = super.getVisibleRect();
		visibleKeeperRect.top=0;
		return visibleKeeperRect;
	}
	public RectF getTotalRect() {
		RectF totalKeeperRect = super.getTotalRect();
		return totalKeeperRect;
	}
	public void setScreenDimensions(int width, int height) {
		this.screenWidth = width;
		this.screenheight = height;
	}

	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}

	public void setFps(int fps) {
		this.frameDelay = 1000/fps;
	}

	public void setFrames(int frames) {
		this.frameTotal = frames - 1;
	}

}
