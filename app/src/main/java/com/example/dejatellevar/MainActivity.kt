package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
   // lateinit var calendario: TextView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        //calendario = findViewById(R.id.editCalendario)
        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)
        login = findViewById(R.id.login)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
    }

    private fun initListeners() {
        loginButton.setOnClickListener {
            Toast.makeText(this, ":) Ya estás en Login", Toast.LENGTH_SHORT).show()
        }

        signupButton.setOnClickListener {

            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, RegistrarActivity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        login.setOnClickListener {

            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias1Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

        }

    }
    //Calendario Instrucciones
    /*
    fun abrirCalendario(view: View) {
        val cal: Calendar = Calendar.getInstance()
        val anio: Int = cal.get(Calendar.YEAR)
        val mes: Int = cal.get(Calendar.MONTH)
        val dia: Int = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
            val fecha = "$dayOfMonth/$month/$year"
            calendario.text = fecha
        }, anio, mes, dia)
        dpd.show()
    }*/


}
