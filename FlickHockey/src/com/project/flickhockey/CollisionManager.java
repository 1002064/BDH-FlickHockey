package com.project.flickhockey;

import java.util.LinkedList;

import android.graphics.Rect;

public class CollisionManager {
	private Player player;
	private Puck puck;
	private GoalKeeper keeper;
	private LinkedList<Defender> staticDefenderList;
	private LinkedList<MobileDefender> mobileDefenderList;
	private LinkedList<SmartDefender> smartDefenderList;

	private boolean fail;
	private boolean goal;

	private int screenWidth, screenHeight;

	public CollisionManager() {
		player = null;
		puck = null;
		mobileDefenderList = new LinkedList<MobileDefender>();
		staticDefenderList = new LinkedList<Defender>();
		smartDefenderList = new LinkedList<SmartDefender>();
		fail = false;
		goal = false;
	}

	public void checkForCollision() {
		checkForBorderCollision();
		checkForDefenderCollision();
		checkForGoal();
	}

	private boolean checkForGoal() {
		// if the puck stops at the keeper
		boolean collision = false;

		if (rectCollision(keeper.getCollisionRect(), puck.getCollisionRect())) {
			puck.setSpeedX(0);
			puck.setSpeedY(0);
			fail = true;
			collision = true;
			// if the puck goes behind the line
		} else if (puck.getCollisionRect().top <= 150) {
			puck.setSpeedX(0);
			puck.setSpeedY(0);
			collision = true;
			if (puck.getCenter().x >= screenWidth / 2 + screenWidth / 9
					|| puck.getCenter().x <= screenWidth / 2 - screenWidth / 9) {

				fail = true;
			} else {
				goal = true;

			}

		}
		return collision;
	}

	private void checkForBorderCollision() {
		// if the puck leaves the bottom of the screen
		if (puck.getCenter().y >= screenHeight) {
			puck.setSpeedX(0.0f);
			puck.setSpeedY(0.0f);
			fail = true;
		}
		// if the puck crashes against the wall

		float borderXLeft = (float) ((screenWidth * (screenHeight - 1.5 * puck
				.getCenter().y)) / (6 * screenHeight));
		float borderXRight = screenWidth - borderXLeft;

		if (puck.getCollisionRect().left <= borderXLeft) {

			puck.setSpeedX(-puck.getSpeedX() * 0.9f);
			puck.setPosX((int) (borderXLeft + 2));
			puck.playBarrierSound();
		}

		if (puck.getCollisionRect().right >= borderXRight) {
			puck.setSpeedX(-puck.getSpeedX() * 0.9f);
			puck.setPosX((int) (borderXRight - puck.getWidth() - 2));
			puck.playBarrierSound();
		}
	}

	private void checkForDefenderCollision() {
		for (Defender defender : staticDefenderList) {
			if (rectCollision(defender.getCollisionRect(),
					puck.getCollisionRect())) {
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
			if (rectCollision(defender.getCollisionRect(),
					player.getCollisionRect())) {
				player.setSpeedX(0.0f);
				player.setSpeedY(0.0f);
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
		}
		for (MobileDefender defender : mobileDefenderList) {
			if (rectCollision(defender.getCollisionRect(),
					puck.getCollisionRect())) {
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
			if (rectCollision(defender.getCollisionRect(),
					player.getCollisionRect())) {
				player.setSpeedX(0.0f);
				player.setSpeedY(0.0f);
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
		}
		for (SmartDefender defender : smartDefenderList) {
			if (rectCollision(defender.getCollisionRect(),
					puck.getCollisionRect())) {
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
			if (rectCollision(defender.getCollisionRect(),
					player.getCollisionRect())) {
				player.setSpeedX(0.0f);
				player.setSpeedY(0.0f);
				puck.setSpeedX(0.0f);
				puck.setSpeedY(0.0f);
				fail = true;
			}
		}

	}

	private boolean rectCollision(Rect r1, Rect r2) {
		return (((r1.left > r2.left) && (r1.left < r2.right) || (r1.right > r2.left)
				&& (r1.right < r2.right)) && ((r1.bottom > r2.top)
				&& (r1.bottom < r2.bottom) || (r1.top > r2.top)
				&& (r1.top < r2.bottom)));
	}

	public boolean isFail() {
		return fail;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setPuck(Puck puck) {
		this.puck = puck;
	}

	public void setKeeper(GoalKeeper keeper) {
		this.keeper = keeper;
	}

	public void setMobileDefenders(LinkedList<MobileDefender> defenders) {
		this.mobileDefenderList = defenders;
		this.smartDefenderList.clear();
		this.staticDefenderList.clear();
	}

	public void setStaticDefenders(LinkedList<Defender> defenders) {
		this.staticDefenderList = defenders;
		this.smartDefenderList.clear();
		this.mobileDefenderList.clear();
	}

	public void setSmartDefenders(LinkedList<SmartDefender> defenders) {
		this.smartDefenderList = defenders;
		this.mobileDefenderList.clear();
		this.staticDefenderList.clear();
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void resetState() {
		fail = false;
		goal = false;
	}
}
