package com.project.flickhockey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.project.flickhockey.Player.AnimationState;

public class Panel extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {

	// game thread
	private Flick_Hockey_Thread thread;

	// level object
	private Level level;

	// graphics objects
	private GoalKeeper goalKeeper;
	private Player player;
	private Puck puck;

	// bitmaps
	private Bitmap floorBitmap, startBitmap, failBitmap, goalBitmap,
			defenderBitmap;

	// booleans
	private boolean lineDrawn = false;
	private boolean buttonPressed = false;
	private boolean fling = false;
	private boolean showGoal = false, showFail = false;
	private boolean startNewGame = false;

	// integers
	private int screenWidth, screenHeight;
	private int levelNumber;
	private int soundPuckShoot, soundPuckBarrierHit;
	private int index = 0;
	private int maxIndex = -1;
	private final int MAX_POINTS = 1000;

	// floating point numbers
	private float[] points;
	private float flingStartX = 0, flingStartY = 0;
	private double goalKeeperMovingDegree = 0.0;
	private float goalKeeperSpeed = 0.08f;
	// floating point rectangle
	private RectF playerRect;

	private AnimationState playerAnimationState = AnimationState.STATIC;

	private GestureDetector gestureDetector;
	private Context context;
	Paint paint = new Paint();
	private long startTime;
	MediaPlayer mp;
	SoundPool soundPool;
	CollisionManager manager;

	public Panel(Context context, int width, int height, MediaPlayer mp,
			SoundPool soundPool) {
		super(context);
		getHolder().addCallback(this);
		this.setOnTouchListener(this);
		this.setOnClickListener(null);
		this.soundPool = soundPool;
		this.mp = mp;
		this.context = context;
		this.screenWidth = width;
		this.screenHeight = height;
		thread = new Flick_Hockey_Thread(this);
		points = new float[MAX_POINTS * 2];
		gestureDetector = new GestureDetector(context, myGestureMethod);

		levelNumber = 1;

		initializeAudio();
		initializeGraphics();

		level = new Level(levelNumber, defenderBitmap);
		level.setScreenDimensions(screenWidth, screenHeight);

		manager = new CollisionManager();
		manager.setKeeper(goalKeeper);
		manager.setPlayer(player);
		manager.setStaticDefenders(level.getStaticDefenders());
		manager.setPuck(puck);
		manager.setScreenHeight(height);
		manager.setScreenWidth(width);
		setFocusable(true);
	}

	private void initializeGraphics() {
		// set up background
		floorBitmap = Bitmap
				.createScaledBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.ground), screenWidth,
						screenHeight, false);

		// set up player object
		Bitmap playerBitmap = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.player1),
				screenWidth / 2, screenWidth / 4, false);

		player = new Player(playerBitmap);
		player.setSoundPool(soundPool);
		player.setFps(50);
		player.setFrames(2);
		player.setCenter(screenWidth / 2, screenHeight - screenHeight / 6);
		
		// set up goalkeeper object
		Bitmap goalKeeperBitmap = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.goalkeeper),
				screenWidth / 6, screenWidth / 6, false);
		goalKeeper = new GoalKeeper(goalKeeperBitmap);
		goalKeeper.setSoundPool(soundPool);
		goalKeeper.setScreenDimensions(screenWidth, screenHeight);
		goalKeeper.setCenter(screenWidth / 2, screenHeight / 5);

		
		Log.v("goalKeeper",
				goalKeeper.getVisibleRect().left + "/"
						+ goalKeeper.getVisibleRect().top + "/"
						+ goalKeeper.getVisibleRect().right + "/"
						+ goalKeeper.getVisibleRect().bottom);
		Log.v("goalKeeper",
				goalKeeper.getTotalRect().left + "/" + goalKeeper.getTotalRect().top
						+ "/" + goalKeeper.getTotalRect().right + "/"
						+ goalKeeper.getTotalRect().bottom);

		// set up puck object
		Bitmap puckBitmap = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.puck),
				screenWidth / 18, screenWidth / 18, false);
		puck = new Puck(puckBitmap, context);
		puck.setSoundPool(soundPool);
		puck.setCenter(player.getTopLeftCorner().x,
				(int) player.getTopLeftCorner().y + 25);

		defenderBitmap = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.defender),
				screenWidth / 6, screenHeight / 6, false);

		// set up interface objects
		startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.startgif), screenWidth / 5,
				screenWidth / 5, false);
		failBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.failgif), screenWidth - 100,
				screenWidth / 2, false);
		goalBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.goalgif), screenWidth / 2,
				screenWidth / 2, false);

	}

	private void initializeAudio() {
		// prepare music to be played
		mp = MediaPlayer.create(context, R.raw.flicktheme);
		// prepare sound effects to be played
		soundPuckShoot = soundPool.load(context, R.raw.flickstick01, 1);
		soundPuckBarrierHit = soundPool.load(context, R.raw.barrier, 1);

	}

	OnGestureListener myGestureMethod = new OnGestureListener() {
		public boolean onDown(MotionEvent event) {
			Log.d("Touch", "down");
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent event,
				float velocityX, float velocityY) {
			Log.d("Touch", "Fling");
			return false;
		}

		public void onLongPress(MotionEvent event) {
			Log.d("Touch", "long press");
		}

		public boolean onScroll(MotionEvent e1, MotionEvent event,
				float distanceX, float distanceY) {
			Log.d("Touch", "scroll");
			if (!lineDrawn) {
				points[index++] = event.getX();
				points[index++] = event.getY();
			}
			return false;
		}

		public void onShowPress(MotionEvent e) {
			Log.d("Touch", "show press");
		}

		public boolean onSingleTapUp(MotionEvent e) {
			Log.d("Touch", "tab up");
			return false;
		}
	};

	@Override
	public void onDraw(Canvas canvas) {
		// animates the player graphic
		player.Animate(playerAnimationState);

		// ------------- DRAW FLOOR ------------- //
		canvas.drawBitmap(floorBitmap, 0, 0, null);

		// ------------- DRAW LINE ------------- //
		if (index >= 4 && !buttonPressed) {

			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(8);
			paint.setColor(Color.GREEN);
			for (int i = 0; i < index - 3; i += 2) {
				canvas.drawLine(points[i], points[i + 1], points[i + 2],
						points[i + 3], paint);
			}
		}
		if (buttonPressed) {

			// moves all the mobile defenders in the level along their path
			for (MobileDefender defender : level.getSmartDefenders()) {
				defender.UpdatePosition();
			}
			for (SmartDefender defender : level.getSmartDefenders()) {
				defender.UpdatePosition(player.getTopLeftCorner());
			}

			if (index < maxIndex - 1) {
				player.setCenter((int) points[index++], (int) points[index++]);
				// player.followLine(points[index++], points[index++]);

				if (!fling) {
					// changes the player's animation to moving
					playerAnimationState = AnimationState.MOVING;
				}
			} else {
				// changes the player's animation to not moving
				playerAnimationState = AnimationState.STATIC;
			}

			if (fling) {
				puck.updatePosition();
			} else {
				puck.setTopLeftCorner(player.getTopLeftCorner().x,
						player.getTopLeftCorner().y + 25);
			}

		}
		if (!(showGoal || showFail)) {
			goalKeeper.updateAnimation();
		}
		// draw player
		Rect rect = new Rect(100, 100, 200, 200);
		Rect rect2 = new Rect(0, 0, 100, 100);
		Log.v("player2",
				player.getVisibleRect().left + "/" + player.getVisibleRect().top
						+ "/" + player.getVisibleRect().right + "/"
						+ player.getVisibleRect().bottom);
		canvas.drawBitmap(player.getBitmap(), player.getVisibleRect(),
				player.getTotalRect(), null);

		// draw puck
		canvas.drawBitmap(puck.getBitmap(), puck.getVisibleRect(), puck.getTotalRect(), null);
		/*
		Log.v("goalKeeper",
				goalKeeper.getVisibleRect().left + "/"
						+ goalKeeper.getVisibleRect().top + "/"
						+ goalKeeper.getVisibleRect().right + "/"
						+ goalKeeper.getVisibleRect().bottom);
		Log.v("goalKeeper",
				goalKeeper.getTotalRect().left + "/" + goalKeeper.getTotalRect().top
						+ "/" + goalKeeper.getTotalRect().right + "/"
						+ goalKeeper.getTotalRect().bottom);
						
		*/
		// draw goalkeeper
		canvas.drawBitmap(goalKeeper.getBitmap(), goalKeeper.getVisibleRect(),
				goalKeeper.getTotalRect(), null);

		// draws all the defender objects in the level
		for (Defender defender : level.getStaticDefenders()) {
			canvas.drawBitmap(defender.getBitmap(), defender.getVisibleRect(),
					defender.getTotalRect(), null);
		}
		for (MobileDefender defender : level.getMobileDefenders()) {
			canvas.drawBitmap(defender.getBitmap(), defender.getVisibleRect(),
					defender.getTotalRect(), null);
		}
		for (SmartDefender defender : level.getSmartDefenders()) {
			canvas.drawBitmap(defender.getBitmap(), defender.getVisibleRect(),
					defender.getTotalRect(), null);
		}

		if (lineDrawn && !buttonPressed) {
			canvas.drawBitmap(startBitmap, screenWidth - screenWidth / 4,
					screenHeight - screenHeight / 4, null);
		}
		manager.checkForCollision();
		showFail = manager.isFail();
		showGoal = manager.isGoal();
		// draws the fail bitmap
		if (showFail) {
			canvas.drawBitmap(failBitmap, 50, screenHeight / 2 - screenWidth
					/ 3, null);

		}

		if (showGoal) {
			canvas.drawBitmap(goalBitmap, screenWidth / 4, screenHeight / 2
					- screenWidth / 3, null);

		}
		canvas.drawRect(goalKeeper.getCollisionRect(), paint);
		canvas.drawRect(player.getCollisionRect(), paint);
	}

	// resets all the level data
	public void resetParameters() {
		player.setCenter(screenWidth / 2, screenHeight - screenHeight / 6);
		puck.setCenter(player.getTopLeftCorner().x,
				player.getTopLeftCorner().y + 25);
		playerAnimationState = AnimationState.STATIC;
		level = new Level(levelNumber, defenderBitmap);
		switch (levelNumber) {
		case 1:
			manager.setStaticDefenders(level.getStaticDefenders());
			break;
		case 2:
			manager.setMobileDefenders(level.getMobileDefenders());
			break;
		case 3:
			manager.setSmartDefenders(level.getSmartDefenders());
			break;
		default:
			break;
		}
		lineDrawn = false;
		buttonPressed = false;
		fling = false;
		showGoal = false;
		showFail = false;
		manager.resetState();
		startNewGame = true;
		points = new float[1000];
		index = 0;
		maxIndex = -1;
		flingStartX = 0;
		flingStartY = 0;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// ------------- START BACKGROUND MUSIC ------------- //
		mp.start();
		if (!thread.isAlive()) {
			thread = new Flick_Hockey_Thread(this);
		}
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		Log.i("thread", "Thread terminated...");
	}

	public boolean onTouch(View v, MotionEvent event) {

		synchronized (getHolder()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (buttonPressed) {
					puck.setStartTime(System.currentTimeMillis());

					// play the puck shoot sound effect and animation
					if (!fling) {
						soundPool.play(soundPuckShoot, 1.0f, 1.0f, 0, 0, 1.0f);
						playerAnimationState = AnimationState.SHOOTING;

						fling = true;
						puck.setFlingStartPosition(event.getX(), event.getY());
					}
				} else // button has not been pressed
				{
					// checks for the button being pressed
					if (event.getX() >= screenWidth - screenWidth / 4
							&& event.getX() <= screenWidth - screenWidth / 4
									+ screenWidth / 5
							&& event.getY() >= screenHeight - screenHeight / 4
							&& event.getY() <= screenHeight - screenHeight / 4
									+ screenWidth / 5) {
						buttonPressed = true;
						maxIndex = index;
						index = 0;
					}
				}
				if (showFail || showGoal) {
					// if a goal has been scored, and the level is not the final
					// one, moves to the next level
					if (showGoal && levelNumber < 4) {
						levelNumber++;
					}
					resetParameters();
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				Log.d("Touch", "move");

				if (!lineDrawn && index < 2 * MAX_POINTS - 1) {
					points[index++] = event.getX();
					points[index++] = event.getY();
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (buttonPressed) {
					puck.calculateFlingSpeed(event.getX(), event.getY());

				} else {
					if (startNewGame) {
						startNewGame = false;
					} else {
						lineDrawn = true;
					}
				}
			}
			return gestureDetector.onTouchEvent(event);

		}
	}
}