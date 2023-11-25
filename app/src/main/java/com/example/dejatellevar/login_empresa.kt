package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class login_empresa : AppCompatActivity() {

    lateinit var imageView21: ImageView
    lateinit var contratos2: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_empresa)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        imageView21 = findViewById(R.id.imageView21)
        contratos2 = findViewById(R.id.contratos2)
    }

    private fun initListeners() {
        imageView21.setOnClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
        }
        contratos2.setOnClickListener {
            val intent = Intent(this, ContratoEmpActivity::class.java)
            startActivity(intent)
        }
    }

}