package com.example.dejatellevar

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLoginButton() {
        // Hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .perform(ViewActions.click())

        // Verificar que un elemento específico se muestre después de hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSignupButton() {
        // Hacer clic en el botón de registro
        Espresso.onView(ViewMatchers.withId(R.id.signupButton))
            .perform(ViewActions.click())

        // Verificar que la actividad de registro se haya iniciado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageCalendario))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testLoginNavigation() {
        // Hacer clic en el botón de inicio de sesión (u otro elemento que inicie la navegación)
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .perform(ViewActions.click())

        // Verificar que la navegación a la actividad "Categorias1" se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.imageView13))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}




