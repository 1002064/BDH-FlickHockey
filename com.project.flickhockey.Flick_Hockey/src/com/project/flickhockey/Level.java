package com.project.flickhockey;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Level {
	// linked lists for the level obstacles
	private LinkedList<Defender> staticDefenders = new LinkedList<Defender>();
	private LinkedList<MobileDefender> mobileDefenders = new LinkedList<MobileDefender>();
	private LinkedList<SmartDefender> smartDefenders = new LinkedList<SmartDefender>();
	private int screenWidth, screenHeight;

	// constructor
	public Level(int levelNumber, Bitmap defenderBitmap) {
		// adds the correct obstacles based on the level number
		switch (levelNumber) {
		case 1:
			break;

		case 2:
			Defender staticDefender = new Defender(defenderBitmap);
			staticDefender.setCenter(screenWidth / 2, screenHeight / 2);
			staticDefenders.add(staticDefender);
			break;

		case 3:
			MobileDefender mobileDefender = new MobileDefender(defenderBitmap);
			mobileDefender.setCenter(screenWidth / 2, screenHeight / 2);
			mobileDefender.moveTo(screenWidth / 2 + screenWidth / 6,
					screenHeight / 2);
			mobileDefenders.add(mobileDefender);

			break;

		case 4:
			SmartDefender smartDefender = new SmartDefender(defenderBitmap);
			smartDefender.setCenter(screenWidth / 2, screenHeight / 2);
			smartDefenders.add(smartDefender);
			break;

		default:
			break;
		}
	}

	public void setScreenDimensions(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	// getter for the static defender list
	public LinkedList<Defender> getStaticDefenders() {
		return staticDefenders;
	}

	public LinkedList<MobileDefender> getMobileDefenders() {
		return mobileDefenders;
	}

	public LinkedList<SmartDefender> getSmartDefenders() {
		return smartDefenders;
	}
}
