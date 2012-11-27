package com.project.flickhockey;

import android.graphics.Canvas;

class Flick_Hockey_Thread extends Thread {
    private Panel panel;
    private boolean run = false;
    
    public Flick_Hockey_Thread(Panel panel) {
        this.panel = panel;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public boolean isRunning() {
        return run;
    }

    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = panel.getHolder().lockCanvas(null);
                synchronized (panel.getHolder()) {
                    panel.onDraw(c);
                }
            } finally {
                if (c != null) {
                    panel.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
