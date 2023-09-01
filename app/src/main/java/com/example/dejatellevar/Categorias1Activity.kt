package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class Categorias1Activity : AppCompatActivity() {

    private lateinit var imageView6: ImageView
    private lateinit var imageView10: ImageView
    private lateinit var imageView11: ImageView
    private lateinit var imageView13: ImageView
    private lateinit var imageView16: ImageView
    private lateinit var imageView12: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categoriaslugar1)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        imageView6 = findViewById(R.id.imageView6)
        imageView10 = findViewById(R.id.imageView10)
        imageView11 = findViewById(R.id.imageView11)
        imageView13 = findViewById(R.id.imageView13)
        imageView16 = findViewById(R.id.imageView16)
        imageView12 = findViewById(R.id.imageView12)
    }
    private fun initListeners() {
        imageView6.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias3Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        imageView10.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias2Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        imageView11.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias2Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        imageView13.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias2Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        imageView16.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias2Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }
        imageView12.setOnClickListener {
            // Crear un Intent para abrir Categorias1 actividad
            val intent = Intent(this, Categorias3Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }

}