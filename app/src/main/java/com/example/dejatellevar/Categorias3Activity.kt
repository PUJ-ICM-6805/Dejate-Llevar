package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.dejatellevar.perfilUsuario.Categorias4Activity

class Categorias3Activity : AppCompatActivity() {

    lateinit var imageView7: ImageView
    lateinit var arrow1ImageView: AppCompatImageView
    lateinit var arrow2ImageView: AppCompatImageView
    lateinit var arrow3ImageView: AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comida)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        initComponents()
        initListeners()


    }

    fun initComponents(){
        arrow1ImageView = findViewById<AppCompatImageView>(R.id.arrow1)
        arrow2ImageView = findViewById<AppCompatImageView>(R.id.arrow2)
        arrow3ImageView = findViewById<AppCompatImageView>(R.id.arrow3)
        imageView7 = findViewById(R.id.imageView7)

    }


    private fun initListeners() {
        arrow1ImageView.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Categorias4Activity::class.java)


            // Iniciar la nueva actividad
            startActivity(intent)

        }
        arrow2ImageView.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Categorias4Activity::class.java)


            // Iniciar la nueva actividad
            startActivity(intent)

        }
        arrow3ImageView.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Categorias4Activity::class.java)


            // Iniciar la nueva actividad
            startActivity(intent)

        }
        imageView7.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Categorias1Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

        }


    }






}