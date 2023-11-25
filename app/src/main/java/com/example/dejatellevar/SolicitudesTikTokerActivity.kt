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

        val listView: ListView = findViewById(R.id.SolicitudesListview)
        val solicitudList: List<Solicitud> = SolicitudHolder.solicitudList ?: emptyList()

        val adapter = SolicitudAdapter(this, R.layout.activity_solicitud_adapter, solicitudList)
        listView.adapter = adapter

    }
}