package com.zpf.drawtheline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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
    private float moveY = px2dp(100);

    public MeasurementView(Context context) {
        super(context);
    }

    public MeasurementView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        this.setOnTouchListener(this);
    }

    public MeasurementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MeasurementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint verticalline = new Paint();
        verticalline.setColor(getResources().getColor(R.color.red));
        verticalline.setStrokeWidth(5);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), verticalline);

        Paint horizontal = new Paint();
        horizontal.setColor(getResources().getColor(R.color.red));
        horizontal.setStrokeWidth(5);
        canvas.drawLine(0, moveY, getWidth(), moveY, horizontal);
        invalidate();
    }
    float moveLast = 0f;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getY();
                if (moveLast == 0) {
                    moveLast = event.getY()+1;
                }
                if (nowY - moveLast > 5) {
                    moveY = moveY + getHeight() / 300;
//                    moveY=moveY+(nowY-beforeY)/20;
                    Log.e("状态", "下滑状态");
                } else if (moveLast - nowY > 5) {
                    moveY = moveY - getHeight() / 300;
//                    moveY=moveY+(nowY-beforeY)/20;
                    Log.e("状态", "上滑状态");
                }
                moveLast = 0f;
                Log.e("beforeY状态", event.getY() + "");
//                Log.e("状态",nowY+"");


        }
        invalidate();
        return true;
    }

    public float px2dp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }
}
