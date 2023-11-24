package com.example.dejatellevar

import java.util.Date

data class Solicitud (
    val titulo: String,
    val descripcion: String,
    val fecha: Date,
    val valor: String,
    val estilo: String,
    val status: Boolean = false
)


