package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView

class Reserva : AppCompatActivity() {

    lateinit var pagar: Button
    lateinit var chat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        initComponents()
        initListeners()
    }


    fun initComponents(){
        pagar = findViewById(R.id.pagar)
        chat = findViewById(R.id.chat)
    }

    private fun initListeners() {

        pagar.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Pagar ::class.java)
            startActivity(intent)
        }

        chat.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Chat::class.java)

            startActivity(intent)
        }
    }


}