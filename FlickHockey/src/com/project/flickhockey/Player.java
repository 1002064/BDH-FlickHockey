package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.SoundPool;

public class Player extends Graphic {

	public enum AnimationState {
		STATIC, MOVING, SHOOTING, CHEERING
	};

	private SoundPool soundPool;
	private int fps, frames;
	private int frameNumber;
	private int frameTotal;
	private int frameDelay;
	private int frameTimer;

	public Player(Bitmap bitmap) {
		super(bitmap);

		frameTimer = 0;
		frameNumber = 1;
	}

	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}

	public void setFps(int fps) {
		if (fps < 1) {
			fps = 1;
		}
		this.fps = fps;
		frameDelay = 1000 / fps;
	}

	public void setFrames(int frames) {
		this.frames = frames;
		frameTotal = frames - 1;
		super.setWidth(super.getWidth() / frames);
	}

	public void Animate(AnimationState animState) {

		if (animState == AnimationState.MOVING) {
			// if it's time to change frame
			if (frameTimer >= frameDelay) {
				// if the frame is not the final frame of the animation
				if (frameNumber < frameTotal) {
					// move to the next frame
					frameNumber++;
				}

				// if the frame is the final frame of the animation
				else {
					// move back to the start of the animation
					frameNumber = 0;
				}

				// reset the timer
				frameTimer = 0;
			}

			// increment the timer
			frameTimer++;
		}
	}

	public Rect getVisibleRect() {
		Rect visiblePlayerRect = super.getVisibleRect();
		visiblePlayerRect.left = frameNumber * super.getWidth();
		visiblePlayerRect.right = (frameNumber + 1) * super.getWidth();
		visiblePlayerRect.top = 0;
		return visiblePlayerRect;
	}

	public Rect getCollisionRect() {
		Rect playerCollisionRect = super.getCollisionRect();

		playerCollisionRect.top = playerCollisionRect.bottom - super.getHeight()
				/ 2;
		return playerCollisionRect;
	}

}
