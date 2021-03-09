package com.troshchiy.coachmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

//        ViewManager

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)

        (parent as ViewGroup).addView(circle)
    }
}