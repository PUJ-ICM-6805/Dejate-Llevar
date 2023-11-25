package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class notificacion : AppCompatActivity() {

    lateinit var atras: ImageView
    lateinit var editTextMensaje: EditText
    lateinit var buttonEnviar: Button
    lateinit var listViewMensajes: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacion)
        initComponents()
        initListeners()

        val usuarioID = intent.getStringExtra("usuarioID") ?: return
        Log.i("usuarioID", usuarioID)

        cargarMensajes(usuarioID)

        buttonEnviar.setOnClickListener {
            enviarMensaje(usuarioID)
        }



    }

    fun initComponents(){
        // identificar id de una imagen botton
        atras = findViewById(R.id.imageView15)
        editTextMensaje = findViewById(R.id.editTextMensaje)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        listViewMensajes = findViewById(R.id.listViewMensajes)
    }

    private fun initListeners() {

        atras.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, InicioTikTok::class.java)
            // guardar el id del usuario
            val usuarioID = intent.getStringExtra("usuarioID") ?: return@setOnClickListener
            // Enviar el usuarioID a la actividad de notificaciones
            intent.putExtra("usuarioID", usuarioID)

            // Iniciar la nueva actividad
            startActivity(intent)
        }

    }

    private fun enviarMensaje(idDestinatario: String) {
        val mensaje = editTextMensaje.text.toString()
        if (mensaje.isNotEmpty()) {
            val idRemitente = idDestinatario // Reemplazar con el ID real del remitente

            val db = FirebaseFirestore.getInstance()
            val chat = hashMapOf(
                "remitente" to idRemitente,
                "destinatario" to "56sfd56sa4d56sad56das14775",
                "mensaje" to mensaje,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("chats")
                .add(chat)
                .addOnSuccessListener {
                    editTextMensaje.text.clear()
                    cargarMensajes(idDestinatario) // Recargar mensajes después de enviar uno nuevo
                }
                .addOnFailureListener { e ->
                    // Manejar el error
                }
        }
    }

    private fun cargarMensajes(idUsuarioActual: String) {
        val idDestinatario = "56sfd56sa4d56sad56das14775" // Este es el ID del destinatario
        val db = FirebaseFirestore.getInstance()

        db.collection("chats")
            // Filtrar mensajes donde el usuario actual es el remitente o el destinatario es el usuario actual
            .whereIn("remitente", listOf(idUsuarioActual, idDestinatario))
            .whereIn("destinatario", listOf(idUsuarioActual, idDestinatario))
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    // Manejar el error
                    return@addSnapshotListener
                }

                val mensajes = mutableListOf<String>()
                // limpiar los mensajes
                mensajes.clear()
                for (doc in snapshots!!) {
                    val mensaje = doc.getString("mensaje") ?: ""
                    val remitente = doc.getString("remitente") ?: ""

                    if (remitente == idUsuarioActual) {
                        mensajes.add("Yo: $mensaje")
                    } else {
                        mensajes.add("Des: $mensaje")
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mensajes)
                listViewMensajes.adapter = adapter
            }
    }





}
