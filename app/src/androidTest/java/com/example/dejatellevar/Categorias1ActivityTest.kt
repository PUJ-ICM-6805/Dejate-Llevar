package com.example.dejatellevar

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class Categorias1ActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Categorias1Activity::class.java)

    @Test
    fun testImageView6Click() {
        // Hacer clic en imageView6
        Espresso.onView(ViewMatchers.withId(R.id.imageView6))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias3Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageView7))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView10Click() {
        // Hacer clic en imageView10
        Espresso.onView(ViewMatchers.withId(R.id.imageView10))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias2Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView11Click() {
        // Hacer clic en imageView11
        Espresso.onView(ViewMatchers.withId(R.id.imageView11))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias2Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView13Click() {
        // Hacer clic en imageView13
        Espresso.onView(ViewMatchers.withId(R.id.imageView13))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias2Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView16Click() {
        // Hacer clic en imageView16
        Espresso.onView(ViewMatchers.withId(R.id.imageView16))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias2Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView12Click() {
        // Hacer clic en imageView12
        Espresso.onView(ViewMatchers.withId(R.id.imageView12))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias3Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageView7))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
