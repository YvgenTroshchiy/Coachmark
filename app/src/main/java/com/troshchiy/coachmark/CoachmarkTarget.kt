package com.troshchiy.coachmark

import android.graphics.PointF
import android.view.View
import androidx.core.view.doOnLayout

class CoachmarkTarget(view: View) {

    private val anchorViewLocation = IntArray(2)

    lateinit var center: PointF

    init {
        view.doOnLayout {

            view.getLocationInWindow(anchorViewLocation)

            center = PointF(
                anchorViewLocation[0].toFloat() + view.width / 2,
                anchorViewLocation[1].toFloat() + view.height / 2
            )
        }
    }
}