package com.troshchiy.coachmark

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewManager
import androidx.core.view.doOnLayout

class LightweightCoachmark(context: Context, parent: ViewManager, private val anchorView: View) : View(context) {

    private val tag = this::class.java.simpleName

    private val anchorRadius = 28.dpToPx.toFloat()
    private val anchorViewLocation = IntArray(2)

    private val anchorCirclePath = Path()
    private val anchorCirclePaint = Paint().apply {
        color = context.getColor(R.color.spot_highlight_green_light)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
    }

    lateinit var anchorCenter: PointF

    init {
        anchorView.doOnLayout {

            anchorView.getLocationInWindow(anchorViewLocation)

            anchorCenter = PointF(
                anchorViewLocation[0].toFloat() + anchorView.width / 2,
                anchorViewLocation[1].toFloat() + anchorView.height / 2
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        anchorCirclePath.apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }

        canvas.drawPath(anchorCirclePath, anchorCirclePaint)
    }
}