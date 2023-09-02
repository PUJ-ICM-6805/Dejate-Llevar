package com.example.dejatellevar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AgregarTarjeta : AppCompatActivity() {

    lateinit var editCalendario: EditText
    lateinit var agregarButton: Button
    lateinit var imageEyePass: ImageButton
    lateinit var previousButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_tarjeta)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        editCalendario = findViewById(R.id.txtfechaExpiracion)
        agregarButton = findViewById(R.id.agregarButton)
        imageEyePass = findViewById(R.id.imageEyePassword)
        previousButton = findViewById(R.id.previousFromNewCard)
    }

    private fun initListeners() {

        previousButton.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Reserva::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        agregarButton.setOnClickListener {
            Toast.makeText(this, "Tarjeta agragada con exito :)", Toast.LENGTH_SHORT).show()
        }

        editCalendario.setOnClickListener {
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

        imageEyePass.setOnClickListener {
            if (imageEyePass.tag == "eyeopen" || imageEyePass.tag == null) {
                imageEyePass.setImageResource(R.drawable.eyeclose)
                imageEyePass.tag = "eyeclose"
            } else {
                imageEyePass.setImageResource(R.drawable.eyeopen)
                imageEyePass.tag = "eyeopen"
            }
        }

    }

}