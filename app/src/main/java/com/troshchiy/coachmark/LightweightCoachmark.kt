package com.troshchiy.coachmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.doOnLayout

class LightweightCoachmark constructor(
    context: Context,
    parent: ViewGroup,
    anchorView: View
) : FrameLayout(context, null, 0) {

    init {
        val circle = LightweightCoachmarkCircle(context, parent, anchorView)
        circle.doOnLayout {

        }

        addView(circle)

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)
        dialog.findViewById<Button>(R.id.gotIt).setOnClickListener {
            (parent as ViewManager).removeView(this@LightweightCoachmark)
        }

    }
}