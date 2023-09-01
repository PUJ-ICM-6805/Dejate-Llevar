package com.example.dejatellevar

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class RegistrarActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var editText: EditText
    lateinit var editTextTextPassword: EditText
    lateinit var loginButton: Button
    lateinit var signupButton: Button
    lateinit var login: Button
    lateinit var imageView2: ImageView
    lateinit var imageView3: ImageView
    lateinit var imageView4: ImageView
    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var TextNombre: EditText
    lateinit var editApellido: EditText
    lateinit var editCalendario: EditText
    lateinit var spinnerGenero: Spinner
    lateinit var editUsername: EditText
    lateinit var editTPassword: EditText
    lateinit var registrarse: Button
    lateinit var terminosYcondiciones: TextView
    lateinit var imageCalendario: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)
        registrarse = findViewById(R.id.registrarse)
        imageCalendario = findViewById(R.id.imageCalendario)
        editCalendario = findViewById(R.id.editCalendario)
    }

    private fun initListeners() {

        signupButton.setOnClickListener{
            Toast.makeText(this, ":) Ya estás en Registrar", Toast.LENGTH_SHORT).show()
        }
        loginButton.setOnClickListener {

            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, MainActivity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

        }

        registrarse.setOnClickListener {
            Toast.makeText(this, ":) Te registraste con exito", Toast.LENGTH_SHORT).show()
        }

        imageCalendario.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val anio: Int = cal.get(Calendar.YEAR)
            val mes: Int = cal.get(Calendar.MONTH)
            val dia: Int = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
                val fecha = "$dayOfMonth/$month/$year"
                val editableFecha = Editable.Factory.getInstance().newEditable(fecha)
                editCalendario.text = editableFecha
            }, anio, mes, dia)
            dpd.show()
        }
    }
}