package com.troshchiy.coachmark

import android.graphics.PointF
import android.view.View

class CoachmarkTarget(view: View) {

    var center: PointF

    init {
        val location = IntArray(2)
        view.getLocationInWindow(location)

        center = PointF(
            location[0].toFloat() + view.width / 2,
            location[1].toFloat() + view.height / 2
        )
    }
}