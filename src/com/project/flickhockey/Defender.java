package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Defender extends Graphic {

	public Defender(Bitmap bitmap) {
		super(bitmap);
	}

	public Rect getCollisionRect() {
		Rect defenderCollisionRect = super.getCollisionRect();
		defenderCollisionRect.top -= super.getHeight() / 2;
		return defenderCollisionRect;
	}
}
