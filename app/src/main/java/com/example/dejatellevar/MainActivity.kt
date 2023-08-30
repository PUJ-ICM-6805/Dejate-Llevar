package com.example.dejatellevar

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var calendario: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaslugar1)

        //calendario vista
        //calendario= findViewById(R.id.editCalendario)

    }


    //Calendario Instrucciones
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
    }


}
