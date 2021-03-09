package com.troshchiy.coachmark

import android.content.Context
import android.graphics.*
import android.view.View

class LightweightCoachmarkCircle(
    context: Context,
    var anchorCenter: PointF
) : View(context) {

    private val tag = this::class.java.simpleName

    private val anchorRadius = 28.dpToPx.toFloat()

    private val anchorCirclePath = Path()
    private val anchorCirclePaint = Paint().apply {
        color = context.getColor(R.color.spot_highlight_green_light)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        anchorCirclePath.apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }

        canvas.drawPath(anchorCirclePath, anchorCirclePaint)
    }
}