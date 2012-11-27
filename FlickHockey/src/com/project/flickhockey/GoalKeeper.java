package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.SoundPool;
import android.util.Log;

public class GoalKeeper extends Graphic {

	private double goalKeeperMovingDegree = 0.0;
	private float goalKeeperSpeed = 0.08f;
	private int screenheight, screenWidth;
	private SoundPool soundPool;
	private int fps, frames;

	public GoalKeeper(Bitmap bitmap) {
		super(bitmap);
	}

	public void updateAnimation() {
		if (goalKeeperMovingDegree >= Math.PI * 2) {
			goalKeeperMovingDegree = 0.0;
		}
		// Log.v("goalKeeperMovingDegree", goalKeeperMovingDegree + "");
		super.setPosX((int) (screenWidth / 2.0f - super.getWidth() / 2.0f
				+ (float) Math.sin(goalKeeperMovingDegree) * 30));
		goalKeeperMovingDegree += goalKeeperSpeed;
	}

	public Rect getCollisionRect() {
		Rect keeperCollisionRect = super.getCollisionRect();
		keeperCollisionRect.top -= this.getHeight() * 0.8;
		return keeperCollisionRect;
	}
	public Rect getVisibleRect() {
		Rect visibleKeeperRect = super.getVisibleRect();
		visibleKeeperRect.top=0;
		return visibleKeeperRect;
	}
	public Rect getTotalRect() {
		Rect visibleKeeperRect = super.getTotalRect();
		//visibleKeeperRect.top=0;
		return visibleKeeperRect;
	}
	public void setScreenDimensions(int width, int height) {
		this.screenWidth = width;
		this.screenheight = height;
	}

	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

}
