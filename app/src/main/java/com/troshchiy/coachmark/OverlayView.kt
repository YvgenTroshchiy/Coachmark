package com.troshchiy.coachmark

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd

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

class OverlayView(context: Context, parent: ViewManager, private val anchorView: View) : View(context) {

    private val TAG = "OverlayView"

    private val dimColor = context.getColor(R.color.dim)

    private val anchorRadius = 28.dpToPx.toFloat()
    private val anchorViewLocation = IntArray(2)

    private val outerCircleRadius = 180.dpToPx.toFloat()

    private val dimTargetAlpha = 0.6f
    private val outerCircleTargetAlpha = 0.6f

    private var dimAlpha = 0f
    private var outerCircleAlpha = 0f

    private val outerCirclePaint = Paint().apply { color = context.getColor(R.color.outer_circle) }

    private val outerCircleXOffsetRatio = 0.92f
    private val outerCircleYOffsetRatio = 1.7f

    private val dimAnimator = ValueAnimator.ofFloat(0f, dimTargetAlpha).apply {
        startDelay = 100
        duration = 500
        interpolator = AccelerateDecelerateInterpolator()

        addUpdateListener { animation ->
            dimAlpha = animation.animatedValue as Float
            invalidate()
        }
    }

    private val outerCircleAnimator = ValueAnimator.ofFloat(0f, outerCircleTargetAlpha).apply {
        duration = 300
        interpolator = AccelerateDecelerateInterpolator()

        addUpdateListener { animation ->
            outerCircleAlpha = animation.animatedValue as Float
            invalidate()
        }
    }

    private val endAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
        interpolator = AccelerateDecelerateInterpolator()

        addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            dimAlpha *= value
            outerCircleAlpha *= value
            invalidate()
        }

        doOnEnd { parent.removeView(this@OverlayView) }
    }

    private val animators: Array<ValueAnimator> = arrayOf(dimAnimator, outerCircleAnimator, endAnimator)

    init {
        dimAnimator.start()
        outerCircleAnimator.start()

        setOnClickListener { endAnimator.start() }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        anchorView.getLocationInWindow(anchorViewLocation)

        val anchorCenter = PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )
        val anchorCirclePath = Path().apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }

        val detailsCircleCenter = PointF(anchorCenter.x * outerCircleXOffsetRatio, anchorCenter.y * outerCircleYOffsetRatio)

        clipOutPath(canvas, anchorCirclePath)
        canvas.drawColor(setAlpha(dimColor, dimAlpha))

        outerCirclePaint.alpha = (outerCircleAlpha * 255.0f).toInt()
        canvas.drawCircle(detailsCircleCenter.x, detailsCircleCenter.y, outerCircleRadius, outerCirclePaint)
    }

    private fun clipOutPath(canvas: Canvas, path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path);
        } else {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animators.forEach { it.removeAllUpdateListeners() }
    }

    /** Modifies the alpha value of the given ARGB color  */
    private fun setAlpha(argb: Int, a: Float): Int {
        var alpha = a

        if (alpha > 1.0f) {
            alpha = 1.0f
        } else if (alpha <= 0.0f) {
            alpha = 0.0f
        }
        return ((argb ushr 24) * alpha).toInt() shl 24 or (argb and 0x00FFFFFF)
    }
}