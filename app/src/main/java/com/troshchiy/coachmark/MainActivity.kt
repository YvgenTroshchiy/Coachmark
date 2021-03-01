package com.troshchiy.coachmark

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val share = findViewById<ImageView>(R.id.share)
        val heart = findViewById<ImageView>(R.id.heart)

        share.setOnClickListener { showTapTargetView() }
        heart.setOnClickListener {
            val overlayView = OverlayView(this, heart)
            (window.decorView as ViewGroup).addView(overlayView)
        }
    }

    private fun showTapTargetView() {
        // You don't always need a sequence, and for that there's a single time tap target
        val spannedDesc = SpannableString("Still deciding? Favorite it for price drop alerts.")
        //        spannedDesc.setSpan(UnderlineSpan(), spannedDesc.length - "TapTargetView".length, spannedDesc.length, 0)

        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.heart), "Want it? Heart it.", spannedDesc)
            .outerCircleColor(R.color.white)
            .outerCircleAlpha(0.85f)
            .cancelable(false)
            .drawShadow(true)
            .dimColor(R.color.dim)
            .titleTextColor(R.color.black)
            .titleTextDimen(R.dimen.title_text_size)
            .tintTarget(false), object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView) {
                super.onTargetClick(view)
                // .. which evidently starts the sequence we defined earlier
                //                sequence.start()
            }

            override fun onOuterCircleClick(view: TapTargetView) {
                super.onOuterCircleClick(view)
                Toast.makeText(view.context, "You clicked the outer circle!", Toast.LENGTH_SHORT).show()
            }

            override fun onTargetDismissed(view: TapTargetView, userInitiated: Boolean) {
                Log.d("TapTargetViewSample", "You dismissed me :(")
            }
        })
    }

    private fun findFrameLayout(anchorView: View): ViewGroup {
        var rootView = anchorView.rootView as ViewGroup

        if (rootView.childCount == 1 && rootView.getChildAt(0) is FrameLayout) {
            rootView = rootView.getChildAt(0) as ViewGroup
        }
        return rootView
    }
}