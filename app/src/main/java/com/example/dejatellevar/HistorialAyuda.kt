package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HistorialAyuda : AppCompatActivity() {
    lateinit var atras: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_ayuda)
        initComponents()
        initListeners()
    }

    fun initComponents() {
        // identificar id de una imagen botton
        atras = findViewById(R.id.imageView18)
    }

    private fun initListeners() {

        atras.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, InicioTikTok::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

    }
}
