package com.troshchiy.coachmark

import android.content.Context
import android.graphics.PointF
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.doOnLayout

class LightweightCoachmark constructor(context: Context, anchorView: View) : FrameLayout(context, null, 0) {

    private val tag = this::class.java.simpleName

//    private var isShown: Boolean = false

    init {
        val anchorCenter = getAnchorCenter(anchorView)

        addView(LightweightCoachmarkCircle(context, anchorCenter))

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)
        dialog.findViewById<Button>(R.id.gotIt).setOnClickListener { close() }

        val coachmarkDescription = dialog.findViewById<LinearLayoutCompat>(R.id.coachmarkDescription)
        coachmarkDescription.doOnLayout {
            Log.w(tag, "w: ${coachmarkDescription.width}")
            val horizontalPadding = context.resources.getDimension(R.dimen.horizontal_padding)

            coachmarkDescription.x = anchorCenter.x - coachmarkDescription.width
            coachmarkDescription.y = anchorCenter.y
        }


//        dialog.x = anchorCenter.x - dialog.width
//        dialog.translationY = 200f


//        val layoutParams = dialog.layoutParams as LinearLayoutCompat.LayoutParams

        val layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
            gravity = Gravity.RIGHT
        }
//        dialog.layoutParams = layoutParams
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