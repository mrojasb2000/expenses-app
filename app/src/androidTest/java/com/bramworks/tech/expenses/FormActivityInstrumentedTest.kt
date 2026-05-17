package com.bramworks.tech.expenses

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormActivityInstrumentedTest {

    @Test
    fun saveWithValidData_finishesActivity_happyPath() {
        ActivityScenario.launch(FormActivity::class.java).use { scenario ->
            onView(withId(R.id.etAmount)).perform(typeText("12345"), closeSoftKeyboard())
            onView(withId(R.id.etDescription)).perform(typeText("Pago mensual"), closeSoftKeyboard())
            onView(withId(R.id.etCreateAt)).perform(replaceText("2026-05-17"), closeSoftKeyboard())

            onView(withId(R.id.btnSaveExpenses)).perform(click())

            scenario.onActivity {
                assertTrue(it.isFinishing || it.isDestroyed)
            }
        }
    }

    @Test
    fun saveWithInvalidAmount_keepsActivityOpen_sadPath() {
        ActivityScenario.launch(FormActivity::class.java).use { scenario ->
            onView(withId(R.id.etAmount)).perform(clearText(), closeSoftKeyboard())
            onView(withId(R.id.btnSaveExpenses)).perform(click())

            scenario.onActivity {
                assertTrue(!it.isFinishing)
            }
            onView(withId(R.id.etAmount)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun createAtIsPrefilledOnOpen_happyPath() {
        ActivityScenario.launch(FormActivity::class.java).use {
            onView(withId(R.id.etCreateAt)).check(matches(not(withText(""))))
        }
    }
}


