package com.android.meterview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class MeterView : View {
    lateinit var kwCircle: RectF
    var kwCircleGapPercentage = 0.20f
    var kwCircleStartAngle = 90f
    var kwCircleSweepAngle = 180f
    var kwCircleStrokeWidthPercentage = 0.02f
    lateinit var kwCirclePaint: Paint


    constructor(context: Context?) : super(context) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun init() {
        kwCircle = RectF().apply {

        }
        kwCirclePaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
        }
    }


    private fun drawKwIndicator(canvas: Canvas?) {
        val heightGap = canvas?.height!! * kwCircleGapPercentage
        val widthGap = canvas.width * kwCircleGapPercentage
        kwCircle.apply {
            top = heightGap
            bottom = canvas.height - heightGap
            left = widthGap
            right = canvas.width - widthGap

        }
        kwCirclePaint.strokeWidth = canvas.height * kwCircleStrokeWidthPercentage
        canvas.drawArc(kwCircle, kwCircleStartAngle, kwCircleSweepAngle, false, kwCirclePaint )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
      drawKwIndicator(canvas)
    }
}