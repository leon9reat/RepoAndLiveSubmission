package com.medialink.repoandlivesubmission.ui.detail.movie

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
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

    private lateinit var myActivity: Activity

    @Before
    fun setUp() {
        val intent = Intent()
        intent.putExtra(DetailActivity.PARCEL_DETAIL, movie)
        mActivityTestRule.launchActivity(intent)
        myActivity = mActivityTestRule.activity
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

        val release = mActivityTestRule.activity.getString(R.string.label_release) + " " + movie.date
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(withText(release)))

        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(withText(movie.overview)))

        onView(withId(R.id.iv_poster)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.trailer_and_reviews)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_changeOrientation() {
        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))

        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))

        /*onView(isRoot()).perform(OrientationChangeAction.orientationPortrait())
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))*/
    }

}