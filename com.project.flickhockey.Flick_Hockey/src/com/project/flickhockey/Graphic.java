package com.project.flickhockey;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class Graphic {
	private Bitmap bitmap;
	private Rect visibleRect;
	private RectF collisionRect;
	private RectF totalRect;
	private float speedX, speedY;
	private int width, height;

	public Graphic(Bitmap bitmap) {
		this.bitmap = bitmap;

		width = bitmap.getWidth();
		height = bitmap.getHeight();

		visibleRect = new Rect(0, 0, width, height);
		totalRect = new RectF();
		collisionRect = new RectF();

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rect getVisibleRect() {
		return visibleRect;
	}

	public RectF getCollisionRect() {
		return totalRect;
	}

	public RectF getTotalRect() {
		return totalRect;
	}

	public Point getCenter() {
		return new Point((int) (totalRect.left + width / 2),
				(int) (totalRect.top + height / 2));
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return this.bitmap;
	}

	public Point getTopLeftCorner() {
		return new Point((int)totalRect.left, (int)totalRect.top);
	}

	public void setTopLeftCorner(float x, float y) {
		totalRect.left = x;
		totalRect.top = y;
		totalRect.right = x + width;
		totalRect.bottom = y + height;
	}

	public void setCenter(float x, float y) {
		totalRect.left = x - width / 2;
		totalRect.top = y - height / 2;
		totalRect.right = x + width / 2;
		totalRect.bottom = y + height / 2;
	}

	public void setPosX(float x) {
		totalRect.left = x;
		totalRect.right = x + width;
	}

	public void setPosY(float y) {
		totalRect.top = y;
		totalRect.bottom = y + height;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

}