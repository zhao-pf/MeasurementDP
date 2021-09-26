package com.zpf.drawtheline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @创建者 赵鹏飞
 * @文件介绍 TODO
 * @创建日期 2021/9/25 20:33
 * @Github https://github.com/zhao-pf
 */
public class MeasurementView extends View {

    float lastMoveY;
    float beforeY = 0f;
    int i = 2;
    float verticallyX;
    float horizontalY;

    public MeasurementView(Context context) {
        super(context);
    }

    private GestureDetector gestureDetector;

    public MeasurementView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {


            boolean rightleftlock = false;
            boolean updownlock = false;
            boolean lock = false;

            @Override
            public boolean onDown(MotionEvent e) {
                rightleftlock = false;
                updownlock = false;
                lock = false;
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                verticallyX = e.getX();
                horizontalY = e.getY();
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int i = 20;
                if (!updownlock) {
                    if (distanceX < -i || distanceX > i) rightleftlock = true;
                    if (distanceX < 0) {
                        Log.e("状态", "右滑" + distanceX);
                        verticallyX = verticallyX - distanceX;
                    } else if (distanceX > 0) {
                        Log.e("状态", "左滑" + distanceX);
                        verticallyX = verticallyX - distanceX;
                    }
                }
                if (!rightleftlock) {
                    if (distanceY < -i || distanceY > i) updownlock = true;
                    if (distanceY < 0) {
                        Log.e("状态", "下滑" + distanceY);
                        horizontalY = horizontalY - distanceY;
                    } else if (distanceY > 0) {
                        Log.e("状态", "上滑" + distanceY);
                        horizontalY = horizontalY - distanceY;
                    }
                }

                if (horizontalY >= getHeight()) horizontalY = getHeight();
                if (horizontalY <= 0) horizontalY = 0;

                if (verticallyX >= getWidth()) verticallyX = getWidth();
                if (verticallyX <= 0) verticallyX = 0;
                invalidate();
                if (e2.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("状态", "抬起手");
                }

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });

//        this.setOnTouchListener(this);
    }

    public MeasurementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MeasurementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        horizontalY = h / 2;
        verticallyX = w / 2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint verticalline = new Paint();
        verticalline.setColor(getResources().getColor(R.color.red));
        verticalline.setStrokeWidth(5);

        canvas.drawLine(verticallyX, 0, verticallyX, getHeight(), verticalline);

        Paint horizontal = new Paint();
        horizontal.setColor(getResources().getColor(R.color.red));
        horizontal.setStrokeWidth(5);
        canvas.drawLine(0, horizontalY, getWidth(), horizontalY, horizontal);
        invalidate();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public float px2dp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }
}
