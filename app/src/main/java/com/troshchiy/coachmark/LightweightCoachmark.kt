package com.troshchiy.coachmark

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewManager

class LightweightCoachmark(context: Context, parent: ViewManager, private val anchorView: View) : View(context) {

    private val tag = this::class.java.simpleName

    private val anchorRadius = 28.dpToPx.toFloat()
    private val anchorViewLocation = IntArray(2)

    private val anchorTargetAlpha = 0.6f

    private val anchorCirclePaint = Paint().apply {
        color = context.getColor(R.color.spot_highlight_green_light)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
    }

    init {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        anchorView.getLocationInWindow(anchorViewLocation)

        //TODO: init not here
        val anchorCenter = PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )

        val anchorCirclePath = Path().apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }
        canvas.drawPath(anchorCirclePath, anchorCirclePaint)
    }
}