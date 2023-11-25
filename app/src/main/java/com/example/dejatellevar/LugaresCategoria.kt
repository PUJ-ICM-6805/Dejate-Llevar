package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.dejatellevar.databinding.ActivityLugaresCategoriaBinding
import com.example.dejatellevar.databinding.ActivityMain2Binding
import com.example.dejatellevar.perfilUsuario.Categorias4Activity
import com.google.firebase.firestore.FirebaseFirestore

class LugaresCategoria : AppCompatActivity() {

    lateinit var imageView7: ImageView
    lateinit var mapaicono: ImageView
    lateinit var calendaIcono: ImageView

    private lateinit var binding: ActivityLugaresCategoriaBinding

    val db = FirebaseFirestore.getInstance()

    val empresasCollection = db.collection("empresa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        binding = ActivityLugaresCategoriaBinding.inflate(layoutInflater)

        // Obtener la raíz de la vista inflada y establecerla como contenido de la actividad
        setContentView(binding.root)

        initComponents()
        initListeners()
        // Aquí puedes llamar a tus funciones u operaciones
        initserviciolugares()


    }

    fun initComponents(){
        imageView7 = findViewById(R.id.imageView7)
        mapaicono = findViewById(R.id.mapaicono)
        calendaIcono = findViewById(R.id.calendaicono)
    }

    private fun initListeners() {

        imageView7.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Categorias1Activity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

        }

        mapaicono.setOnClickListener{
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Mapa::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

        }


        calendaIcono.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad

            val intent = Intent(this, Reserva::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

    }

    private fun initserviciolugares() {
        val serviciosList = mutableListOf<Servicio>()

        empresasCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val tipos = document.get("tipo") as? List<String>
                    if (tipos != null && "cenderismo" in tipos) {
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
                val adapter = ServicioLugaresAdapter(this, serviciosList)
                // Establecer el adaptador en la vista de lista
                binding.serviciosList.adapter = adapter

            }
            .addOnFailureListener { e ->
                // Maneja cualquier error que pueda ocurrir al leer desde Firestore
            }
    }





}