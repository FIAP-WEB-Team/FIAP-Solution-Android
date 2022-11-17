/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.passenger

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiapon.androidsolution.R
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PassengerDataTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<PassengerDataActivity> = ActivityScenarioRule(
        Intent(
            ApplicationProvider.getApplicationContext(),
            PassengerDataActivity::class.java
        ).putExtra("api_token", "").putExtra("selected_flight", 0)
    )


    @Test
    fun clickingOnNoToggleMustEmptyAllFields() {
        Espresso.onView(withId(R.id.noRadioButton)).perform(ViewActions.click())

        Espresso.onView(
            allOf(
                withId(R.id.editText),
                isDescendantOfA(withId(R.id.firstNameEditText))
            )
        ).check(matches(withText("")))

        Espresso.onView(
            allOf(
                withId(R.id.editText),
                isDescendantOfA(withId(R.id.lastNameEditText))
            )
        ).check(matches(withText("")))

        Espresso.onView(
            allOf(
                withId(R.id.editText),
                isDescendantOfA(withId(R.id.birthDateEditText))
            )
        ).check(matches(withText("")))

        Espresso.onView(
            allOf(
                withId(R.id.editText),
                isDescendantOfA(withId(R.id.genderEditText))
            )
        ).check(matches(withText("")))

        Espresso.onView(
            allOf(
                withId(R.id.editText),
                isDescendantOfA(withId(R.id.nationalityEditText))
            )
        ).check(matches(withText("")))
    }

    @Test
    fun clickingOnYesToggleMustEnableButton() {
        Espresso.onView(withId(R.id.yesRadioButton)).perform(ViewActions.click())

        Espresso.onView(
            allOf(
                withId(R.id.footerButton),
                isDescendantOfA(withId(R.id.footer))
            )
        ).check(matches(isEnabled()))
    }

    @Test
    fun emptyPassengerFieldsDisableButton() {
        Espresso.onView(withId(R.id.noRadioButton)).perform(ViewActions.click())

        Espresso.onView(
            allOf(
                withId(R.id.footerButton),
                isDescendantOfA(withId(R.id.footer))
            )
        ).check(matches(isNotEnabled()))
    }
}