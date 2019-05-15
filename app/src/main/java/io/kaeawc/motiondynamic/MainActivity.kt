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
        Timber.d("onMotion")

        when (motion_scene?.currentState) {
            R.id.start -> onStartMotion()
            else -> onEndMotion()
        }
    }

    private fun onStartMotion() {
        Timber.d("onStartMotion")

//        val newConstraints = reconstrain(motion_scene?.getConstraintSet(R.id.start), R.id.square)


        val cs = motion_scene?.getConstraintSet(R.id.start)
        val parent = R.id.motion_scene
        val size = resources.getDimensionPixelSize(R.dimen.object_size)
        val range = resources.displayMetrics.heightPixels - size
        val topMargin = Random.nextInt(range)
        Timber.i("topMargin: $topMargin")

//        cs?.setHorizontalBias(R.id.square, Random.nextFloat());
        cs?.setVerticalBias(R.id.square, Random.nextFloat());
        cs?.clear(R.id.square, TOP)
        cs?.clear(R.id.square, BOTTOM)
        cs?.setMargin(R.id.square, START, Random.nextInt(200))

        motion_scene?.updateState(R.id.start, cs)

//        resetState(R.id.start)

//        motion_scene?.rebuildScene()

//        motion_scene?.setTransition(R.id.start, R.id.end)
        motion_scene?.transitionToEnd()


    }

    private fun onEndMotion() {
        Timber.d("onEndMotion")

        motion_scene?.apply {
            motion_scene?.transitionToStart()
        }
    }

    private fun resetState(state: Int) {
        Timber.d("resetState")
        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        motion_scene?.setState(state, widthPixels, heightPixels)
    }

    private fun reconstrain(constraintSet: ConstraintSet?, view: Int): ConstraintSet? {
        Timber.d("reconstrain")
        val parent = R.id.motion_scene
        val size = resources.getDimensionPixelSize(R.dimen.object_size)
        val range = resources.displayMetrics.heightPixels - size
        val topMargin = Random.nextInt(range)
        Timber.i("topMargin: $topMargin")

        constraintSet?.connect(view, TOP, parent, TOP, topMargin)
        return constraintSet
    }
}
