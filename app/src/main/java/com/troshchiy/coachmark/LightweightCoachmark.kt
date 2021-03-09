package com.troshchiy.coachmark

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.doOnLayout
import kotlin.math.max

@SuppressLint("ViewConstructor")
class LightweightCoachmark constructor(context: Context, anchorView: View) : FrameLayout(context, null, 0) {

    private val tag = this::class.java.simpleName

    private var isShownCoachmark = false

    init {
        setOnClickListener {
            // To prevent close by click outside "Got It" Button.
            // Add open one more Coachmark by click on "Anchor view".
            Log.d(tag, "setOnClickListener")
        }

        val anchorCenter = getAnchorCenter(anchorView)

        addView(LightweightCoachmarkCircle(context, anchorCenter))

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)
        dialog.findViewById<Button>(R.id.gotIt).setOnClickListener { close() }

        val coachmarkDescription = dialog.findViewById<LinearLayoutCompat>(R.id.coachmarkDescription)
        coachmarkDescription.doOnLayout {
            val defaultMargin = context.resources.getDimension(R.dimen.default_margin)

            val widthAnchor = anchorCenter.x - coachmarkDescription.width
            val widthParent = width - coachmarkDescription.width - defaultMargin

            coachmarkDescription.x = max(widthAnchor, widthParent)
            coachmarkDescription.y = anchorCenter.y + LightweightCoachmarkCircle.radius + defaultMargin
        }
    }

    private fun close() {
        (parent as ViewManager).removeView(this@LightweightCoachmark)
    }

    private fun getAnchorCenter(anchorView: View): PointF {
        val location = IntArray(2)
        anchorView.getLocationInWindow(location)

        return PointF(
            location[0].toFloat() + anchorView.width / 2,
            location[1].toFloat() + anchorView.height / 2
        )
    }
}