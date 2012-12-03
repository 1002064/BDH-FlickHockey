package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public class Defender extends AnimatedGraphic {

	public Defender(Bitmap bitmap) {
		super(bitmap);
	}

	@Override
	public RectF getCollisionRect() {
		RectF defenderCollisionRect = super.getCollisionRect();
		defenderCollisionRect.top -= super.getHeight() / 2;
		return defenderCollisionRect;
	}
}
