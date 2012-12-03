package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class MobileDefender extends Defender {

	boolean pointReached = false;
	float finalPosX, finalPosY;
	float startPosX, startPosY;
	Rect collisionRect;

	// constructor
	public MobileDefender(Bitmap bitmap) {
		super(bitmap);
	}

	public void moveTo(float x, float y) {
		this.finalPosX = x;
		this.finalPosY = y;
	}

	// moves the graphic along the set path
	public void UpdatePosition() {
		Point position=super.getTopLeftCorner();
		float speedX = super.getSpeedX();
		float speedY = super.getSpeedY();
		if (!pointReached) {
			if (startPosX < finalPosX) {
				position.x += speedX;

				if (position.x >= finalPosX) {
					pointReached = true;
				}
			}

			if (startPosX > finalPosX) {
				position.x -= speedX;

				if (position.x <= finalPosX) {
					pointReached = true;
				}
			}

			if (startPosY > finalPosY) {
				position.y -= speedY;

				if (position.y <= finalPosY) {
					pointReached = true;
				}
			}

			if (startPosY < finalPosY) {
				position.y += speedY;
				if (position.y >= finalPosY) {
					pointReached = true;
				}
			}
		} else {
			if (startPosX < finalPosX) {
				position.x -= speedX;

				if (position.x <= startPosX) {
					pointReached = false;
				}
			}

			if (startPosX > finalPosX) {
				position.x += speedX;

				if (position.x >= startPosX) {
					pointReached = false;
				}
			}

			if (startPosY > finalPosY) {
				position.y += speedY;

				if (position.y >= startPosY) {
					pointReached = false;
				}
			}

			if (startPosY < finalPosY) {
				position.y -= speedY;

				if (position.y <= startPosY) {
					pointReached = false;
				}
			}
		}
		super.setPosX(position.x);
		super.setPosY(position.y);
		super.setSpeedX(speedX);
		super.setSpeedY(speedY);

	}

}
