package io.kaeawc.motiondynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.TOP
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.START
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.BOTTOM
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        button?.setOnClickListener(::onMotion)
    }

    private fun onMotion(@Suppress("UNUSED_ARGUMENT") view: View) {

        when (motion_scene?.currentState) {
            R.id.start -> onStartMotion()
            else -> onEndMotion()
        }
    }

    private fun onStartMotion() {

        reconstrain(R.id.start, R.id.square)

        motion_scene?.transitionToEnd()
    }

    private fun onEndMotion() {

        motion_scene?.apply {
            motion_scene?.transitionToStart()
        }
    }

    private fun resetState(state: Int) {
        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        motion_scene?.setState(state, widthPixels, heightPixels)
    }

    private fun reconstrain(constraint: Int, view: Int) {
        val constraintSet = motion_scene?.getConstraintSet(constraint)
        val parent = R.id.motion_scene
        val size = resources.getDimensionPixelSize(R.dimen.object_size)
        val range = resources.displayMetrics.heightPixels - size
        val topMargin = Random.nextInt(range)

        // `connect` appears to do nothing
        constraintSet?.connect(view, TOP, parent, TOP, topMargin)

        // setMargin actually has the intended effect
        // constraintSet?.setMargin(view, TOP, topMargin)

        // `updateState`
        motion_scene?.updateState(constraint, constraintSet)
    }
}
