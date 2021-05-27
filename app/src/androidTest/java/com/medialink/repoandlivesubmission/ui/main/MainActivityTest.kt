package com.medialink.repoandlivesubmission.ui.main

import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import com.medialink.repoandlivesubmission.OrientationChangeAction.Companion.orientationLandscape
import com.medialink.repoandlivesubmission.OrientationChangeAction.Companion.orientationPortrait
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.TvShowRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.ui.detail.DetailActivity
import com.medialink.repoandlivesubmission.ui.fragment.BaseFragment
import com.medialink.repoandlivesubmission.ui.main.MainActivityTest.RecyclerViewMatchers.hasItemCount
import com.medialink.repoandlivesubmission.utils.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    object RecyclerViewMatchers {
        @JvmStatic
        fun hasItemCount(itemCount: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(
                RecyclerView::class.java) {

                override fun describeTo(description: Description) {
                    description.appendText("has $itemCount items")
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    return view.adapter?.itemCount == itemCount
                }
            }
        }
    }

    @Test
    fun test_LoadDataMovies() {
        val matcher = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed()
        )

        val apiService = RetrofitClient.getApiService()
        val repository = MovieRepository(apiService)
        var i: Int
        runBlocking {
            val x = repository.getList(1)
            i = x.size
        }

        onView(matcher).check(matches(isDisplayed()))
        onView(matcher).check(matches((hasItemCount(i))))
    }

    @Test
    fun test_LoadDataTvShow() {

        val tabs = Matchers.allOf(
            withText("Tv Shows"),
            isDescendantOfA(withId(R.id.tabs))
        )
        onView(tabs).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(matchCurrentTabTitle("Tv Shows")))

        val matcher = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed(),
        )

        val apiService = RetrofitClient.getApiService()
        val repository = TvShowRepository(apiService)
        var i: Int
        runBlocking {
            val x = repository.getList(1)
            i = x.size
        }

        onView(matcher).check(matches(isDisplayed()))
        onView(matcher).check(matches((hasItemCount(i))))
    }


    @Test
    fun test_ButtonTvShowFavoriteClick() {
        val tabs = Matchers.allOf(
            withText("Tv Shows"),
            isDescendantOfA(withId(R.id.tabs))
        )
        onView(tabs).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(matchCurrentTabTitle("Tv Shows")))

        val recycler = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed()
        )

        onView(recycler)
            .check(matches(hasDescendant(withText(R.string.title_favorite))))
        clickOnFavoriteAtRow(0, recycler)
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Favorite")))
    }

    @Test
    fun test_ButtonMovieFavoriteClick() {
        val recycler = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed()
        )

        onView(recycler)
            .check(matches(hasDescendant(withText(R.string.title_favorite))))
        clickOnFavoriteAtRow(0, recycler)
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Favorite")))
    }

    @Test
    fun test_ButtonMovieShareClick() {
        val recycler = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed()
        )

        onView(recycler)
            .check(matches(hasDescendant(withText(R.string.title_share))))
        clickOnShareAtRow(0, recycler)
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Share")))
    }

    @Test
    fun test_ButtonTvShowShareClick() {
        val tabs = Matchers.allOf(
            withText("Tv Shows"),
            isDescendantOfA(withId(R.id.tabs))
        )
        onView(tabs).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(matchCurrentTabTitle("Tv Shows")))


        val recycler = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed()
        )

        onView(recycler)
            .check(matches(hasDescendant(withText(R.string.title_share))))
        clickOnShareAtRow(0, recycler)
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Share")))
    }

    @Test
    fun test_changeOrientation() {
        SystemClock.sleep(1000)
        onView(isRoot()).perform(orientationLandscape())
        SystemClock.sleep(1000)
        onView(isRoot()).perform(orientationPortrait())
    }


    @Test
    fun test_clickShowDetail() {

        val matcher = Matchers.allOf(
            withId(R.id.movies_rv),
            isDisplayed(),
        )
        onView(matcher).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Intents.intended(
            Matchers.allOf(
                IntentMatchers.hasComponent(ComponentNameMatchers.hasShortClassName(".ui.detail.DetailActivity")),
            )
        )

    }

    private fun matchCurrentTabTitle(tabTitle: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("unable to match title of current selected tab with $tabTitle")
            }

            override fun matchesSafely(item: View?): Boolean {
                val tabLayout = item as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabLayout.selectedTabPosition)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index ${tabLayout.selectedTabPosition}"))
                        .build()

                return tabAtIndex.text.toString().contains(tabTitle, true)
            }
        }
    }

    private fun clickOnFavoriteAtRow(position: Int, view: Matcher<View>) {
        onView(view)
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (position, ClickOnButtonView()))
    }

    private fun clickOnShareAtRow(position: Int, view: Matcher<View>) {
        onView(view)
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (position, ClickOnShareButton()))
    }

    inner class ClickOnButtonView : ViewAction {
        private var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController?, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.btn_like))
        }
    }

    inner class ClickOnShareButton : ViewAction {
        private var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController?, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.btn_share))
        }
    }



}