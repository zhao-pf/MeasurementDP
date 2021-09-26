package com.zpf.drawtheline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * @创建者 赵鹏飞
 * @文件介绍 TODO
 * @创建日期 2021/9/25 20:33
 * @Github https://github.com/zhao-pf
 */
class MeasurementTwoView : View {
    var lastMoveY = 0f
    var beforeY = 0f
    var i = 2
    var verticallyX = 0f
    var horizontalY = 0f
    var lastVerticallyX = 0f
    var lastHorizontalY = 0f

    constructor(context: Context?) : super(context)

    private var gestureDetector: GestureDetector? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                lastVerticallyX = verticallyX
                lastHorizontalY = horizontalY
                invalidate()
                return true
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                verticallyX = e.x
                horizontalY = e.y
//                lastVerticallyX = e.x
//                lastHorizontalY = e.y
                invalidate()
                return true
            }
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {}
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return true
            }
        })

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        horizontalY = (h / 2).toFloat()
        verticallyX = (w / 2).toFloat()
        lastHorizontalY = horizontalY
        lastVerticallyX = verticallyX
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val vertically = Paint()
        vertically.color = resources.getColor(R.color.red)
        vertically.strokeWidth = strokeWidth
        canvas.drawLine(verticallyX, horizontalY, lastVerticallyX, lastHorizontalY, vertically)
        invalidate()
    }

    var strokeWidth=4f


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    fun px2dp(px: Float): Float {
        return px / resources.displayMetrics.density
    }
}