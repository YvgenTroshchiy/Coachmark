package com.troshchiy.coachmark

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.View

val Int.dpToPx: Int
    get() = this.toFloat().dpToPx

val Float.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

class OverlayView(context: Context, private val anchorView: View) : View(context) {


    private val bitmap: Bitmap? = null

    private val smallRadius = 28.dpToPx.toFloat()
    private val bigRadius = 180.dpToPx.toFloat()

    private val bigCircleXOffsetRatio = 0.92f
    private val bigCircleYOffsetRatio = 1.7f

    private val anchorViewLocation = IntArray(2)

    private val backgroundPaint = Paint()

    init {
//         setWillNotDraw(false)
//        setLayerType(LAYER_TYPE_HARDWARE, null);

//        backgroundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        backgroundPaint.color = context.getColor(R.color.dim_strong)
        backgroundPaint.alpha = 160

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        anchorView.getLocationInWindow(anchorViewLocation)

        val circleCenter = PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )

        canvas.drawCircle(circleCenter.x, circleCenter.y, smallRadius, backgroundPaint)
        canvas.drawCircle(
            circleCenter.x * bigCircleXOffsetRatio,
            circleCenter.y * bigCircleYOffsetRatio,
            bigRadius,
            backgroundPaint
        )

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }
}