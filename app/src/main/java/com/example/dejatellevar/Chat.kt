package com.example.dejatellevar

import UsuarioAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
class Chat : AppCompatActivity() {
    lateinit var listview: ListView
    data class Usuario(
        var id: String = "", // Asegúrate de tener este campo
        var nombre: String = "",
        var apellido: String = "",
        var correo: String = "",
        var fechaNacimiento: String = "",
        var genero: String = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        listview = findViewById(R.id.listaUsuarios)

        // consultar usuarios en firebase
        // mostrar usuarios en listview y solo mostrar nombre
        val db = FirebaseFirestore.getInstance()
        val usuarios = db.collection("usuarios")
        usuarios.get().addOnSuccessListener { result ->
            val listaUsuarios = mutableListOf<Usuario>()
            for (document in result) {
                val usuario = document.toObject(Usuario::class.java).apply {
                    id = document.id // Asegúrate de asignar el ID del documento aquí
                }
                listaUsuarios.add(usuario)
            }
            val adapter = UsuarioAdapter(this, listaUsuarios)
            listview.adapter = adapter

            // Configurar el listener para los elementos del ListView
            listview.setOnItemClickListener { _, _, position, _ ->
                val usuarioSeleccionado = listaUsuarios[position]
                val intent = Intent(this, ChatMensaje::class.java)
                intent.putExtra("ID_DESTINATARIO", usuarioSeleccionado.id)
                startActivity(intent)
            }
        }


    }
}