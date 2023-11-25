package com.example.dejatellevar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.dejatellevar.databinding.ActivityMain2Binding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding


    // Inicializa una instancia de Firebase Firestore
    val db = FirebaseFirestore.getInstance()

    // Obtiene una referencia a la colección "empresa"
    val empresasCollection = db.collection("empresa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        // Inicializar el objeto de binding
        binding = ActivityMain2Binding.inflate(layoutInflater)

        // Obtener la raíz de la vista inflada y establecerla como contenido de la actividad
        setContentView(binding.root)

        // Aquí puedes llamar a tus funciones u operaciones
        initservicioscomida()
    }
    private fun initservicioscomida() {
        val serviciosList = mutableListOf<Servicio>()

        empresasCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val tipos = document.get("tipo") as? List<String>
                    if (tipos != null && "restaurante" in tipos) {
                        // Este documento representa una empresa que es un restaurante

                        val idServicio = document.id // Identificador único del servicio
                        val idEmpresa = document.id // Identificador de la empresa relacionada
                        val nombre = document.getString("nombre") ?: ""
                        val tipos = document.get("tipo") as? List<String> ?: emptyList()
                        val descripcion = document.getString("descripcion") ?: ""
                        val precio = document.getDouble("precio") ?: 0.0
                        val etiquetas = document.get("etiquetas") as? List<String> ?: emptyList()
                        val horario = document.get("horario") as? List<String> ?: emptyList()
                        val comentario = document.getString("comentario") ?: ""
                        val tiktokerPromocion = document.getString("tiktokerPromocion") ?: ""
                        val reel = document.getString("reel") ?: ""
                        val foto = document.getString("foto") ?: ""
                        val video = document.getString("video") ?: ""
                        val estadoReserva = document.getBoolean("estadoReserva") ?: false

                        val servicio = Servicio(
                            idServicio,
                            idEmpresa,
                            nombre,
                            tipos,
                            descripcion,
                            precio,
                            etiquetas,
                            horario,
                            comentario,
                            tiktokerPromocion,
                            reel,
                            foto,
                            video,
                            estadoReserva
                        )

                        serviciosList.add(servicio)
                    }
                }

                // Inicializar el adaptador con la lista de continentes y el contexto
                val adapter = ServicioAdapter(this, serviciosList)
                // Establecer el adaptador en la vista de lista
                binding.serviciosList.adapter = adapter

            }
            .addOnFailureListener { e ->
                // Maneja cualquier error que pueda ocurrir al leer desde Firestore
            }
    }
}