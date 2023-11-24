package com.example.dejatellevar

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SolicitudesTikTokerActivity :  AppCompatActivity() {

    private lateinit var statusBtn: Button
    private lateinit var solicitudesListview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiktoker_solicitudes)

        solicitudesListview = findViewById(R.id.SolicitudesListview)

        if(SolicitudHolder.solicitudList != null){

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                SolicitudHolder.solicitudList!!
            )
            solicitudesListview.adapter = adapter
        }

    }
}