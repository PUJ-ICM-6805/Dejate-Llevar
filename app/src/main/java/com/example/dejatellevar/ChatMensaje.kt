package com.example.dejatellevar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ChatMensaje : AppCompatActivity() {

    lateinit var editTextMensaje: EditText
    lateinit var buttonEnviar: Button
    lateinit var listViewMensajes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_mensaje)

        editTextMensaje = findViewById(R.id.editTextMensaje)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        listViewMensajes = findViewById(R.id.listViewMensajes)

        val usuarioID = intent.getStringExtra("usuarioID") ?: return

        cargarMensajes(usuarioID)

        buttonEnviar.setOnClickListener {
            enviarMensaje(usuarioID)
        }
    }

    private fun enviarMensaje(idDestinatario: String) {
        val mensaje = editTextMensaje.text.toString()
        if (mensaje.isNotEmpty()) {
            val idRemitente = "56sfd56sa4d56sad56das14775" // Reemplazar con el ID real del remitente

            val db = FirebaseFirestore.getInstance()
            val chat = hashMapOf(
                "remitente" to idRemitente,
                "destinatario" to idDestinatario,
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
        val idRemitente = "56sfd56sa4d56sad56das14775" // Este es el ID del remitente
        val db = FirebaseFirestore.getInstance()

        db.collection("chats")
            // Filtrar mensajes donde el usuario actual es el destinatario o el remitente es el usuario actual
            .whereIn("remitente", listOf(idRemitente, idUsuarioActual))
            .whereIn("destinatario", listOf(idRemitente, idUsuarioActual))
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    // Manejar el error
                    return@addSnapshotListener
                }

                val mensajes = mutableListOf<String>()
                // limpiar la lista de mensajes
                mensajes.clear()
                for (doc in snapshots!!) {
                    val mensaje = doc.getString("mensaje") ?: ""
                    val remitente = doc.getString("remitente") ?: ""

                    if (remitente == idRemitente) {
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
