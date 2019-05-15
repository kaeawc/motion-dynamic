package io.kaeawc.motiondynamic

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene

fun MotionLayout.after(completion: () -> Unit) {
    this.setTransitionListener(object: MotionLayout.TransitionListener {
        override fun onTransitionTrigger(layout: MotionLayout?, startId: Int, something: Boolean, progress: Float) {}
        override fun onTransitionStarted(layout: MotionLayout?, startId: Int, endId: Int) {}
        override fun onTransitionChange(layout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
        override fun onTransitionCompleted(layout: MotionLayout?, currentId: Int) {
            this@after.postDelayed({
                completion()
            }, 1)
        }

        override fun allowsTransition(transition: MotionScene.Transition): Boolean = true
    })
}

fun MotionLayout.stopListening() {
    this.setTransitionListener(null)
}
