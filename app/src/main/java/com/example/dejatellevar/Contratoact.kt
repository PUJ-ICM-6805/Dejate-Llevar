package com.example.dejatellevar

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class Contratoact : AppCompatActivity(){

    lateinit var duracionSpin : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val duraciones: Array<String> = resources.getStringArray(R.array.duracion)

        duracionSpin = findViewById(R.id.DuracionSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, duraciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        duracionSpin.adapter = adapter

    }


}