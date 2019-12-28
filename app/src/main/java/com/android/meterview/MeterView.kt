package com.android.meterview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class MeterView : View {
    lateinit var kwCircle: RectF
    var kwCircleGapPercentage = 0.10f
    var kwCircleStartAngle = 90f
    var kwCircleSweepAngle = 180f
    var kwCircleStrokeWidthPercentage = 0.008f
    lateinit var kwCirclePaint: Paint


    lateinit var speedCircle: RectF
    var speedCircleStartAngle = 0f
    var speedCircleSweepAngle = 360f
    var speedCircleGapPercentage = 0.07f
    var speedCircleStrokeWidthPercentage = 0.03f
    lateinit var speedCirclePaint: Paint

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
            isAntiAlias = true
            style = Paint.Style.STROKE
            shader = LinearGradient(0f, 0f, 0f, 150f, Color.parseColor("#e3a43e"), Color.parseColor("#fdf291"), Shader.TileMode.MIRROR)
        }

        speedCircle = RectF()

        speedCirclePaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            style = Paint.Style.STROKE
            shader = LinearGradient(0f, 0f, 0f, 150f, Color.parseColor("#87ffe7"), Color.parseColor("#0157ff"), Shader.TileMode.MIRROR)
        }
    }


    private fun drawSpeedIndicator(canvas: Canvas?) {
        val heightGap = canvas?.height!! * (kwCircleGapPercentage + speedCircleGapPercentage)
        val widthGap = canvas.width * (kwCircleGapPercentage + speedCircleGapPercentage)
        speedCircle.apply {
            top = heightGap
            bottom = canvas.height - heightGap
            left = widthGap
            right = canvas.width - widthGap

        }
        speedCirclePaint.strokeWidth = canvas.height * speedCircleStrokeWidthPercentage
        canvas.drawArc(speedCircle, speedCircleStartAngle, speedCircleSweepAngle, false, speedCirclePaint )
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
        drawSpeedIndicator(canvas)
    }
}