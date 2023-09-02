package com.example.dejatellevar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.dejatellevar.R

class InicioTikTok : AppCompatActivity() {

    lateinit var contratoact: ImageView
    lateinit var historialTikTok: ImageView
    lateinit var creeps: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_tik_tok)
    }

    fun initComponents(){
        // identificar id de una imagen botton
        contratoact = findViewById(R.id.contratos)
        historialTikTok = findViewById(R.id.historial)
        creeps = findViewById(R.id.creeps)
    }

    private fun initListeners() {

        contratoact.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, contratoact::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        historialTikTok.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, HistorialTikTok::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)

        }

        creeps.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, agregarProduct::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }



    }

}