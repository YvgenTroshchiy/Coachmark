package com.troshchiy.coachmark

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val anchorView = findViewById<ImageView>(R.id.image)

        val rootView = anchorView.rootView as ViewGroup
        val view = OverlayView(this, anchorView)
        rootView.addView(view)

        val o = 0
    }

    private fun findFrameLayout(anchorView: View): ViewGroup {
        var rootView = anchorView.rootView as ViewGroup

        if (rootView.childCount == 1 && rootView.getChildAt(0) is FrameLayout) {
            rootView = rootView.getChildAt(0) as ViewGroup
        }
        return rootView
    }
}