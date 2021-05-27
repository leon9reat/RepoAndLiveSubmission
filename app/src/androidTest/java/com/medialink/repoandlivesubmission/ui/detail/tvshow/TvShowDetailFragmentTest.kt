package com.medialink.repoandlivesubmission.ui.detail.tvshow

import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DetailActivity::class.java)

    val tvShow = Detail(
        AppConfig.TYPE_TV_SHOW,
        "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
        null,
        "http://www.cwtv.com/shows/the-flash/",
        60735,
        "en",
        "The Flash",
        "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
        1236.368,
        "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
        "2014-10-07",
        "Returning Series",
        "The Flash",
        7.7,
        7689
    )

    @Before
    fun setUp() {
        val intent = Intent()
        intent.putExtra(DetailActivity.PARCEL_DETAIL, tvShow)
        mActivityTestRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun test_showFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.withText(tvShow.title)))

        val release = mActivityTestRule.activity.getString(R.string.label_airing) + " " + tvShow.date
        Espresso.onView(ViewMatchers.withId(R.id.tv_first_air_date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_first_air_date))
            .check(ViewAssertions.matches(ViewMatchers.withText(release)))


        Espresso.onView(ViewMatchers.withId(R.id.tv_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText(tvShow.overview)))
    }

    @Test
    fun test_changeOrientation() {
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.isRoot())
            .perform(OrientationChangeAction.orientationLandscape())
        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.withText(tvShow.title)))

        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.isRoot())
            .perform(OrientationChangeAction.orientationPortrait())

        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.withText(tvShow.title)))
    }
}