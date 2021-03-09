package com.troshchiy.coachmark

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View

@SuppressLint("ViewConstructor")
class LightweightCoachmarkCircle(context: Context, var center: PointF) : View(context) {

    companion object {
        val radius = 28.dpToPx.toFloat()
    }

    private val tag = this::class.java.simpleName

    private val path = Path()

    private val paint = Paint().apply {
        color = context.getColor(R.color.spot_highlight_green_light)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.apply {
            addCircle(center.x, center.y, radius, Path.Direction.CW)
        }

        canvas.drawPath(path, paint)
    }
}