package com.tokodeveloper.reactiontime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainMenuFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(ReactionTimeActivity::class.java)

    @Test
    fun jumpToGameFragment() {
        onView(withId(R.id.start_game)).perform(click())
        onView(withId(R.id.start)).check(matches(isDisplayed()))
    }
}