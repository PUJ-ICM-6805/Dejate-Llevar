package com.example.dejatellevar.perfilUsuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.dejatellevar.Categorias1Activity
import com.example.dejatellevar.R

class Categorias4Activity : AppCompatActivity() {
    lateinit var imageView9: ImageView
    lateinit var casaicono: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias4)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        initComponents()
        initListeners()
    }
    fun initComponents(){
        imageView9 = findViewById(R.id.imageView9)
        casaicono = findViewById(R.id.casaicono)
    }


    private fun initListeners() {
        imageView9.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, Categorias1Activity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        casaicono.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, Categorias1Activity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)

        }


    }




}