package com.fiapon.androidsolution.ui.flight

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiapon.androidsolution.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FlightDataTest {
    @Suppress("unused") val activityScenario = ActivityScenario.launch(FlightDataActivity::class.java)

    @Test
    fun initialPageSetup() {
        onView(withId(R.id.mainFlightDataActivity)).check(matches(isDisplayed()))
        onView(withId(R.id.flightTextView)).check(matches(withText("Encontre os melhores voos com as melhores ofertas")))
        onView(withId(R.id.btnFooter)).check(matches(isNotClickable()))
        onView(withId(R.id.dateDestiny)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun completedFormMustEnableButton() {
        onView(withId(R.id.editTextDateOrigin)).perform(ViewActions.click())
        onView(withText("OK")).perform(ViewActions.click())
        onView(withId(R.id.btnFooter)).check(matches(isEnabled()))
    }
}