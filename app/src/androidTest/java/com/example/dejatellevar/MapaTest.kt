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

class MapaTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Mapa::class.java)

    @Test
    fun testCasaIconoClick() {
        // Hacer clic en casaicono
        Espresso.onView(ViewMatchers.withId(R.id.casaicono))
            .perform(ViewActions.click())

        // Verificar que la navegación a Categorias1Activity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageView6))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
