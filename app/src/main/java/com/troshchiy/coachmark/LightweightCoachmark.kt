package com.troshchiy.coachmark

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.doOnLayout
import kotlin.math.max

@SuppressLint("ViewConstructor")
class LightweightCoachmark(context: Context, target: CoachmarkTarget) : FrameLayout(context, null, 0) {

    private val tag = this::class.java.simpleName

    init {
        setOnClickListener {
            // To prevent close by click outside "Got It" Button.
            // Add open one more Coachmark by click on "Anchor view".
            Log.d(tag, "setOnClickListener")
        }

        addView(LightweightCoachmarkCircle(context, target.center))

        val dialog = LayoutInflater.from(context).inflate(R.layout.lightweight_coachmark_description, this, true)
        dialog.findViewById<Button>(R.id.gotIt).setOnClickListener { close() }

        val coachmarkDescription = dialog.findViewById<LinearLayoutCompat>(R.id.coachmarkDescription)
        coachmarkDescription.doOnLayout {
            val defaultMargin = context.resources.getDimension(R.dimen.default_margin)

            val widthAnchor = target.center.x - coachmarkDescription.width
            val widthParent = width - coachmarkDescription.width - defaultMargin

            coachmarkDescription.x = max(widthAnchor, widthParent)
            coachmarkDescription.y = target.center.y + LightweightCoachmarkCircle.radius + defaultMargin
        }
    }

    private fun close() {
        (parent as ViewManager).removeView(this@LightweightCoachmark)
    }
}