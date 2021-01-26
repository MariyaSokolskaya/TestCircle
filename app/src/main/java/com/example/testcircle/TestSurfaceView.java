package com.example.testcircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    float tX = -1000, tY = -1000;
    int r = 0;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tX = event.getX();
        tY = event.getY();
        r = 0;
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    Canvas canvas = null;
                    canvas = getHolder().lockCanvas();
                    synchronized (getHolder()){
                        Paint paint = new Paint();
                        paint.setColor(Color.YELLOW);
                        canvas.drawColor(Color.BLUE);
                        canvas.drawCircle(tX, tY, r, paint);

                    }
                    getHolder().unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    r += 5;
                }
            }
        };

        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
