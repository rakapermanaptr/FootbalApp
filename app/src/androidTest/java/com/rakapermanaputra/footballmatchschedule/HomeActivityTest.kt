package com.rakapermanaputra.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.rakapermanaputra.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testActivityBehaviour() {

        loading()
//        matches.lastMatch
        onView(withId(matches)).check(matches(isDisplayed()))
        onView(withId(rvLast)).check(matches(isDisplayed()))
        onView(withId(rvLast)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        loading()
        onView(withId(rvLast)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(imgHomeTeam)).check(matches(isDisplayed()))
        onView(withId(imgAwayTeam)).check(matches(isDisplayed()))
        loading()
        onView(withId(add_to_favorite)).perform(click())
        loading()
        pressBack()
        onView(withId(spinnerLastMatch)).check(matches(isDisplayed()))
//        matches.nextmatch
        onView(withId(viewPager)).perform(swipeLeft())
        onView(withId(rvNext)).check(matches(isDisplayed()))
        onView(withId(rvNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        loading()
        onView(withId(rvNext)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(imgHomeTeam)).check(matches(isDisplayed()))
        onView(withId(imgAwayTeam)).check(matches(isDisplayed()))
        loading()
        onView(withId(add_to_favorite)).perform(click())
        loading()
        pressBack()
        loading()
//        teams
        onView(withId(teams)).check(matches(isDisplayed()))
        onView(withId(teams)).perform(click())
        loading()
        onView(withId(rvTeams)).check(matches(isDisplayed()))
        onView(withId(rvTeams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        loading()
        onView(withId(rvTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))
        loading()
        onView(withId(viewPager)).perform(swipeLeft())
        loading()
        onView(withId(rvPlayers)).check(matches(isDisplayed()))
        onView(withId(rvPlayers)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        loading()
        onView(withId(rvPlayers)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))
        loading()
        pressBack()
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        loading()
        //favorite.match
        onView(withId(favorite)).perform(click())
        loading()
        onView(withId(rvFavorite)).check(matches(isDisplayed()))
        onView(withId(rvFavorite)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rvFavorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        loading()
        onView(withId(add_to_favorite)).perform(click())
        loading()
        pressBack()
        onView(withId(refreshFavMatch)).check(matches(isDisplayed()))
        onView(withId(refreshFavMatch)).perform(swipeDown())
        loading()
        //favorite.team
        onView(withId(favorite)).check(matches(isDisplayed()))
        onView(withId(favorite)).perform(click())
        loading()
        onView(withId(viewPager)).perform(swipeLeft())
        loading()
        onView(withId(rvFavTeams)).check(matches(isDisplayed()))
        onView(withId(rvFavTeams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rvFavTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        loading()
        onView(withId(add_to_favorite)).perform(click())
        loading()
        pressBack()
        onView(withId(refreshFavTeam)).check(matches(isDisplayed()))
        onView(withId(refreshFavTeam)).perform(swipeDown())

    }

    private fun loading() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}