package com.medialink.repoandlivesubmission.ui.detail.movie

import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.medialink.repoandlivesubmission.OrientationChangeAction
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.ui.detail.DetailActivity
import com.medialink.repoandlivesubmission.utils.AppConfig
import com.medialink.repoandlivesubmission.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DetailActivity::class.java)

    private val movie = Detail(
        AppConfig.TYPE_MOVIE,
        "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
        null,
        "https://animemafia.in",
        567189,
        "en",
        "Tom Clancy's Without Remorse",
        "An elite Navy SEAL uncovers an international conspiracy while seeking justice for the murder of his pregnant wife.",
        2235.578,
        "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
        "2021-04-29",
        "Released",
        "Tom Clancy's Without Remorse",
        7.3,
        991
    )

    @Before
    fun setUp() {
        val intent = Intent()
        intent.putExtra(DetailActivity.PARCEL_DETAIL, movie)
        mActivityTestRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun test_showFragment() {
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))

        val release =
            mActivityTestRule.activity.getString(R.string.label_release) + " " + movie.date
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(withText(release)))

        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(withText(movie.overview)))
    }

    @Test
    fun test_changeOrientation() {
        SystemClock.sleep(1000)
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape())
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))

        SystemClock.sleep(1000)
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait())
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))
    }

}