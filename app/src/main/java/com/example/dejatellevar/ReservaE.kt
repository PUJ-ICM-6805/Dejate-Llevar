package com.example.dejatellevar

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class ReservaE : AppCompatActivity() {

    lateinit var imageCalendario1: ImageButton
    lateinit var imageCalendario2: ImageButton
    lateinit var editCalendario1: TextView
    lateinit var editCalendario2: TextView
    lateinit var pagarButton: Button
    lateinit var previousButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reserva)
        initComponents()

        previousButton.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias1Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        pagarButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, AgregarTarjeta::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        imageCalendario1.setOnClickListener {
            setDate(1)
        }
        imageCalendario2.setOnClickListener {
            setDate(2)
        }
    }

    private fun initComponents() {
        imageCalendario1 = findViewById(R.id.btnFechaLlegada)
        imageCalendario2 = findViewById(R.id.btnFechaSalida)
        editCalendario1 = findViewById(R.id.txtFechaLlegada)
        editCalendario2 = findViewById(R.id.txtFechaSalida)
        pagarButton = findViewById(R.id.pagarButton)
        previousButton = findViewById(R.id.previousFromReserva)
    }

    private fun setDate(eventTime: Int) {
        val cal: Calendar = Calendar.getInstance()
        val anio: Int = cal.get(Calendar.YEAR)
        val mes: Int = cal.get(Calendar.MONTH)
        val dia: Int = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
            val fecha = "$dayOfMonth/$month/$year"
            val editableFecha = Editable.Factory.getInstance().newEditable(fecha)
            if (eventTime == 1)
                editCalendario1.text = editableFecha
            else
                editCalendario2.text = editableFecha
        }, anio, mes, dia)
        dpd.show()
        Toast.makeText(this, "Seleccione la fecha de su agrado!", Toast.LENGTH_SHORT).show()
    }
}