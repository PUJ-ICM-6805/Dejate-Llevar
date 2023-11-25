package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class Categorias3ActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Categorias3Activity::class.java)

    @Test
    fun testArrow1ImageViewClick() {
        // Hacer clic en arrow1ImageView
        Espresso.onView(ViewMatchers.withId(R.id.arrow1))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias4Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.arrow1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testArrow2ImageViewClick() {
        // Hacer clic en arrow2ImageView
        Espresso.onView(ViewMatchers.withId(R.id.arrow2))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias4Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.arrow1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testArrow3ImageViewClick() {
        // Hacer clic en arrow3ImageView
        Espresso.onView(ViewMatchers.withId(R.id.arrow3))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias4Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.arrow1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testImageView7Click() {
        // Hacer clic en imageView7
        Espresso.onView(ViewMatchers.withId(R.id.imageView7))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias1Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageView6))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testMapaIconoClick() {
        // Hacer clic en mapaicono
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .perform(ViewActions.click())

        // Verificar que la navegación a Mapa se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.mapaicono))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
