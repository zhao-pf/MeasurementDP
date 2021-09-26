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
class MeasurementView : View {
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
        isClickable = true
        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            var rightleftlock = false
            var updownlock = false
            var lock = false
            override fun onDown(e: MotionEvent): Boolean {
                rightleftlock = false
                updownlock = false
                lock = false
                lastVerticallyX = verticallyX
                lastHorizontalY = horizontalY
                return true
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                verticallyX = e.x
                horizontalY = e.y
                lastVerticallyX = e.x
                lastHorizontalY = e.y
                return true
            }
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                val i = 10 // 容错
                if (!lock){
                    if (distanceY < -i || distanceY > i) updownlock = true
                    if (distanceX < -i || distanceX > i) rightleftlock = true
                    lock=true
                }
                if (!rightleftlock) {
                    horizontalY -= distanceY
                    isDrawScaleLineVertically=false
                }
                if (!updownlock) {

                    verticallyX -= distanceX
                    isDrawScaleLineHorizontal=false
                }




                if (horizontalY >= height) horizontalY = height.toFloat()
                if (horizontalY <= 0) horizontalY = 0f
                if (verticallyX >= width) verticallyX = width.toFloat()
                if (verticallyX <= 0) verticallyX = 0f
                invalidate()
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
        drawVerticallyLine(canvas) // 垂直线
        drawHorizontalLine(canvas) //水平线
        invalidate()
    }

    private fun drawVerticallyLine(canvas: Canvas) {
        val vertically = Paint()
        vertically.color = resources.getColor(R.color.red)
        vertically.strokeWidth = strokeWidth
        canvas.drawLine(verticallyX, 0f, verticallyX, height.toFloat(), vertically)
        drawScaleLineVertically(canvas) // 垂直尺寸线
    }
    var strokeWidth=2f
    private fun drawHorizontalLine(canvas: Canvas) {
        val horizontal = Paint()
        horizontal.color = resources.getColor(R.color.red)
        horizontal.strokeWidth = strokeWidth
        canvas.drawLine(0f, horizontalY, width.toFloat(), horizontalY, horizontal)
        drawScaleLineHorizontal(canvas) // 水平尺寸线
    }

    private fun drawScaleLineHorizontal(canvas: Canvas) {
        fun drawLastHorizontalLine() {
            val vertically = Paint()
            vertically.color = resources.getColor(R.color.green)
            vertically.strokeWidth = strokeWidth
            canvas.drawLine(lastVerticallyX, 0f, lastVerticallyX, height.toFloat(), vertically)
        }
        val i1 = (lastVerticallyX.toInt() - verticallyX.toInt()) * -1
        isDrawScaleLineHorizontal = i1 != 0 // 是否绘制
        if (isDrawScaleLineHorizontal){
            val scaleLineHorizontal = Paint()
            scaleLineHorizontal.color = resources.getColor(R.color.colorPrimaryDark)
            scaleLineHorizontal.strokeWidth = 5f
            scaleLineHorizontal.textSize = 40f
            canvas.drawLine(lastVerticallyX, horizontalY, verticallyX, horizontalY, scaleLineHorizontal)
            val dpStr = "${i1}px"
            val textWidth = scaleLineHorizontal.measureText(dpStr)
            canvas.drawText(
                dpStr,
                (verticallyX - lastVerticallyX) / 2 + lastVerticallyX - textWidth / 2,
                horizontalY + 40,
                scaleLineHorizontal
            )
            drawLastHorizontalLine()
        }

    }
    var isDrawScaleLineVertically=false // 是否绘制垂直尺寸线
    var isDrawScaleLineHorizontal=false // 是否绘制水平尺寸线
    private fun drawScaleLineVertically(canvas: Canvas) {
        fun drawVerticallyLine() {
            val horizontal = Paint()
            horizontal.color = resources.getColor(R.color.green)
            horizontal.strokeWidth = strokeWidth
            canvas.drawLine(0f, lastHorizontalY, width.toFloat(), lastHorizontalY, horizontal)
        }
        val i2 = (lastHorizontalY.toInt() - horizontalY.toInt()) * -1
        isDrawScaleLineVertically=i2 != 0 // 是否绘制
        if (isDrawScaleLineVertically){
            val scaleLineVertically = Paint()
            scaleLineVertically.color = resources.getColor(R.color.colorPrimaryDark)
            scaleLineVertically.strokeWidth = 5f
            scaleLineVertically.textSize = 40f
            canvas.drawLine(verticallyX, lastHorizontalY, verticallyX, horizontalY, scaleLineVertically)

            val dpStr = "${i2}px"
            val textWidth = scaleLineVertically.measureText(dpStr)
            canvas.drawText(
                dpStr,
                verticallyX + 40,
                (horizontalY - lastHorizontalY) / 2 + lastHorizontalY - textWidth / 2,
                scaleLineVertically
            )
            drawVerticallyLine()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    fun px2dp(px: Float): Float {
        return px / resources.displayMetrics.density
    }
}