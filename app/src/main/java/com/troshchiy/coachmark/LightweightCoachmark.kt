package com.troshchiy.coachmark

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.FrameLayout

class LightweightCoachmark constructor(
    context: Context,
    parent: ViewGroup,
    anchorView: View
) : FrameLayout(context, null, 0) {


    init {
        addView(LightweightCoachmarkCircle(context, getAnchorCenter(anchorView)))

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)
        dialog.findViewById<Button>(R.id.gotIt).setOnClickListener {
            (parent as ViewManager).removeView(this@LightweightCoachmark)
        }

    }

    private fun getAnchorCenter(anchorView: View): PointF {
        val anchorViewLocation = IntArray(2)
        anchorView.getLocationInWindow(anchorViewLocation)

        return PointF(
            anchorViewLocation[0].toFloat() + anchorView.width / 2,
            anchorViewLocation[1].toFloat() + anchorView.height / 2
        )
    }
}