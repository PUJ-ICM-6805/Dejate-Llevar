package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class Mapa : AppCompatActivity() {

    lateinit var casaicono: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        initComponents()
        initListeners()


    }

    fun initComponents(){
        casaicono= findViewById(R.id.casaicono)

    }

    fun initListeners(){

    casaicono.setOnClickListener {

        // Crear un Intent para abrir la nueva actividad
        val intent = Intent(this, Categorias1Activity::class.java)

        // Iniciar la nueva actividad
        startActivity(intent)
    }

    }

}