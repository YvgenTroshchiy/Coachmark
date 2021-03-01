package com.troshchiy.coachmark

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

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

    private val TAG = "OverlayView"

    private val dimColor = context.getColor(R.color.bg)

    private val anchorRadius = 28.dpToPx.toFloat()
    private val anchorViewLocation = IntArray(2)

    private val detailsRadius = 180.dpToPx.toFloat()

    private var bgAlpha = 0f
    private val bgTargetAlpha = 0.6f

    private val detailsTargetAlpha = 0.6f
    private var detailsAlpha = 0f

    private val detailsCirclePaint = Paint().apply { color = context.getColor(R.color.details_color) }

    private val detailsCircleXOffsetRatio = 0.92f
    private val detailsCircleYOffsetRatio = 1.7f

    private val detailsPaint = Paint().apply {
        isAntiAlias = true
        color = context.getColor(R.color.details_color)
    }


    init {
        val bgAnimator = ValueAnimator.ofFloat(0f, bgTargetAlpha).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener { animation ->
                bgAlpha = (animation.animatedValue as Float)
                invalidate()
            }
        }
        postDelayed({ bgAnimator.start() }, 100)
//        bgAnimator.start()

        val detailsAnimator = ValueAnimator.ofFloat(0f, detailsTargetAlpha).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener { animation ->
                detailsAlpha = (animation.animatedValue as Float)
                invalidate()
            }
        }
//        detailsAnimator.start()
        postDelayed({ detailsAnimator.start() }, 0)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        Log.i("OverlayView", "onDraw. detailsAlpha: $detailsAlpha")

        anchorView.getLocationInWindow(anchorViewLocation)

        val anchorCenter = PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )
        val anchorCirclePath = Path().apply {
            addCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Path.Direction.CW)
        }
        val detailsCircleCenter =
            PointF(
                anchorCenter.x * detailsCircleXOffsetRatio,
                anchorCenter.y * detailsCircleYOffsetRatio
            )
        val detailsCirclePath = Path().apply {
            addCircle(
                detailsCircleCenter.x,
                detailsCircleCenter.y,
                detailsRadius,
                Path.Direction.CW
            )
        }

        clipOutPath(canvas, anchorCirclePath)

        canvas.drawColor(setAlpha(dimColor, bgAlpha))

        detailsCirclePaint.alpha = (detailsAlpha * 255.0f).toInt()
        canvas.drawCircle(detailsCircleCenter.x, detailsCircleCenter.y, detailsRadius, detailsCirclePaint)

//        canvas.drawCircle(anchorCenter.x, anchorCenter.y, anchorRadius, Paint().apply { color = context.getColor(R.color.details_color) })

        // Draw details circle
//        canvas.save()
//        clipOutPath(canvas, anchorCirclePath)
//        canvas.drawCircle(detailsCircleCenter.x, detailsCircleCenter.y, detailsRadius, detailsPaint)
//        canvas.restore()

        // Draw background
//        canvas.save()
//        clipOutPath(canvas, detailsCirclePath)
//        canvas.drawColor(context.getColor(R.color.bg_dim))
    }

    private fun clipOutPath(canvas: Canvas, path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path);
        } else {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
        }
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