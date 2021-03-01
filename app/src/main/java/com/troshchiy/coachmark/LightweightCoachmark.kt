package com.troshchiy.coachmark

import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.view.ViewManager

class LightweightCoachmark(context: Context, parent: ViewManager, private val anchorView: View) : View(context) {

    private val targetAlpha = 0.6f

    init {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        canvas.drawCircle()
    }
}