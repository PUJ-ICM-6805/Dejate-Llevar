package com.example.dejatellevar

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class RegistrarActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RegistrarActivity::class.java)

    @Test
    fun testLoginButton() {
        // Hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .perform(ViewActions.click())

        // Verificar que la navegación a MainActivity se haya realizado correctamente
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
