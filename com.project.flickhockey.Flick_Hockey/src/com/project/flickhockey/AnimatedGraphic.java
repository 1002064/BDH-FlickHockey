package com.project.flickhockey;

import android.graphics.Bitmap;

public class AnimatedGraphic extends Graphic 
{
	protected int frameNumber;
	protected int frameTotal;
	protected int frameDelay;
	protected int frameTimer;
	
	public AnimatedGraphic(Bitmap bitmap) 
	{
		super(bitmap);	
	}
	
	//single row animation function, likely overridden in inheriting classes
	public void Animate()
	{
		// if it's time to change frame
		if (frameTimer >= frameDelay)
		{
			// if the frame is not the final frame of the animation
			if (frameNumber < frameTotal) 
			{
				// move to the next frame
				frameNumber++;
			}

			// if the frame is the final frame of the animation
			else 
			{
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
