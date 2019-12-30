package com.android.meterview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class MeterView : View {
    lateinit var kwPath: Path
    lateinit var kwCircle: RectF
    var kwCircleGapPercentage = 0.10f
    var kwCircleStartAngle = 90f
    var kwCircleSweepAngle = 180f
    var kwCircleStrokeWidthPercentage = 0.008f
    lateinit var kwCirclePaint: Paint

    lateinit var speedPath: Path
    lateinit var speedCircle: RectF
    var speedCircleStartAngle = 0f
    var speedCircleSweepAngle = 150f
    var speedCircleGapPercentage = 0.07f
    var speedCircleStrokeWidthPercentage = 0.03f
    lateinit var speedCirclePaint: Paint

    lateinit var textPaint: Paint
    var speedTextGapPercentage = 0.07f
    var speedTextStrokeWidthPercentage = 0.03f

    lateinit var tickPath: Path
    lateinit var pathPaint: Paint
    var tickCount = 36

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
            shader = LinearGradient(
                0f,
                0f,
                0f,
                150f,
                Color.parseColor("#e3a43e"),
                Color.parseColor("#fdf291"),
                Shader.TileMode.MIRROR
            )
        }

        speedCircle = RectF()

        speedCirclePaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            style = Paint.Style.STROKE
            shader = LinearGradient(
                0f,
                0f,
                0f,
                500f,
                Color.parseColor("#87ffe7"),
                Color.parseColor("#0157ff"),
                Shader.TileMode.MIRROR
            )
        }

        textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 100f
            isAntiAlias = true
        }
        kwPath = Path()
        speedPath = Path()

        tickPath = Path()
        pathPaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 10f
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
        speedPath.addArc(speedCircle, speedCircleStartAngle, speedCircleSweepAngle)
        speedCirclePaint.strokeWidth = canvas.height * speedCircleStrokeWidthPercentage
        canvas.drawPath(speedPath, speedCirclePaint)
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
//
        kwCirclePaint.strokeWidth = canvas.height * kwCircleStrokeWidthPercentage
        kwPath.apply {
            addArc(kwCircle, kwCircleStartAngle, kwCircleSweepAngle)
        }
        kwCirclePaint.maskFilter = BlurMaskFilter(40f, BlurMaskFilter.Blur.SOLID)
        canvas.drawPath(kwPath, kwCirclePaint)


    }

    private fun drawSpeed(speed: String = "00", canvas: Canvas?) {
        val startY = canvas?.height!! / 2f + 25f
        val startX = canvas.width / 2 - textPaint.measureText(speed, 0, speed.length) / 2f
        canvas.drawText(speed, 0, speed.length, startX, startY, textPaint)
    }

    private fun drawSpeedTicks(canvas: Canvas?) {
        val tickStartX = canvas?.width!! * (kwCircleGapPercentage + kwCircleStrokeWidthPercentage)
        val tickEndX =
            canvas.width * (kwCircleGapPercentage + kwCircleStrokeWidthPercentage + speedCircleGapPercentage)
        val ticksStep = 360 / tickCount

        canvas.save()
        for (b in 0 until 360 step ticksStep) {
            tickPath.apply {
                moveTo(tickStartX, 150f)
                lineTo(tickEndX, 150f)

                canvas.rotate(b.toFloat())
            }
            canvas.drawPath(tickPath, pathPaint)
        }

        canvas.restore()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawKwIndicator(canvas)
        drawSpeedTicks(canvas)
        drawSpeedIndicator(canvas)
        drawSpeed(canvas = canvas)
    }
}