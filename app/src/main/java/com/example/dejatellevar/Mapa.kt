package com.example.dejatellevar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView

class Mapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        initComponents()
        initListeners()


    }

    fun initComponents(){


    }

    fun initListeners(){



    }

}