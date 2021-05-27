package com.medialink.repoandlivesubmission

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.hamcrest.Matcher

/**
 * An Espresso ViewAction that changes the orientation of the screen
 */
class OrientationChangeAction private constructor(private val orientation: Int) : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isRoot()
    }

    override fun getDescription(): String {
        return "change orientation to $orientation"
    }

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()
        val activity = view.context as Activity
        activity.requestedOrientation = orientation
        val resumedActivities =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.isEmpty()) {
            throw RuntimeException("Could not change orientation")
        }
    }

    companion object {
        fun orientationLandscape(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        fun orientationPortrait(): ViewAction {
            return OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }
}