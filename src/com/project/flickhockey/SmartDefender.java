package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class SmartDefender extends MobileDefender {
	Rect collisionRect;

	// constructor
	public SmartDefender(Bitmap bitmap) {
		super(bitmap);
	}

	// moves the graphic toward the passed in player's position
	public void UpdatePosition(Point playerPosition) {
		Point defenderPosition = super.getTopLeftCorner();
		if (defenderPosition.x < playerPosition.x) {
			defenderPosition.x -= super.getSpeedX();
		} else {
			defenderPosition.x += super.getSpeedX();
		}

		if (defenderPosition.y < playerPosition.y) {
			defenderPosition.y -= super.getSpeedY();
		} else {
			defenderPosition.y += super.getSpeedY();
		}
	}

}
