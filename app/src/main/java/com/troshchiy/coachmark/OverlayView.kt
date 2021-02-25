package com.troshchiy.coachmark

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View

val Int.dpToPx: Int
    get() = this.toFloat().dpToPx

val Float.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


class ClippedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
}

class OverlayView(context: Context, private val anchorView: View) : View(context) {

    private val anchorRadius = 28.dpToPx.toFloat()
    private val anchorViewLocation = IntArray(2)

    private val detailsRadius = 180.dpToPx.toFloat()

    private val detailsCircleXOffsetRatio = 0.92f
    private val detailsCircleYOffsetRatio = 1.7f

    private val detailsPaint = Paint().apply {
        isAntiAlias = true
        color = context.getColor(R.color.details_dim)
    }

    init {

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        Log.w("OverlayView", "onDraw")

        anchorView.getLocationInWindow(anchorViewLocation)

        val anchorCenter = PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )

        val bigCircleCenter =
            PointF(
                anchorCenter.x * detailsCircleXOffsetRatio,
                anchorCenter.y * detailsCircleYOffsetRatio
            )

        val detailsCirclePath = Path().apply {
            addCircle(bigCircleCenter.x, bigCircleCenter.y, detailsRadius, Path.Direction.CW)
        }
        clipOutPath(canvas, detailsCirclePath)

        canvas.drawColor(context.getColor(R.color.bg_dim))
        canvas.restore()

        canvas.save()
        val anchorCirclePath = Path().apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }
        clipOutPath(canvas, anchorCirclePath)
        canvas.drawCircle(bigCircleCenter.x, bigCircleCenter.y, detailsRadius, detailsPaint)
    }

    private fun clipOutPath(canvas: Canvas, path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path);
        } else {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
        }
    }
}