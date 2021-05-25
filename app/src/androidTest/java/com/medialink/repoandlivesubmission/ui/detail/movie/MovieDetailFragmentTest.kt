package com.medialink.repoandlivesubmission.ui.detail.movie

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.ui.detail.DetailActivity
import com.medialink.repoandlivesubmission.utils.AppConfig
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DetailActivity::class.java)

    val movie = Detail(
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
    }

    @Test
    fun test_showFragment() {
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(ViewAssertions.matches(withText(movie.title)))

        val release = mActivityTestRule.activity.getString(R.string.label_release) + " " + movie.date
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(ViewAssertions.matches(withText(release)))

        val rate = String.format(mActivityTestRule.activity.getString(R.string.rating), movie.voteAverage)
        onView(withId(R.id.tv_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(ViewAssertions.matches(withText(rate)))

        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(ViewAssertions.matches(withText(movie.overview)))
    }

}