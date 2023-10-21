package com.example.dejatellevar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dejatellevar.R  // Reemplaza con la ubicación correcta de tu archivo R
import android.widget.BaseAdapter
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dejatellevar.perfilUsuario.Categorias4Activity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicioLugaresAdapter (private val context: Context, private val serviciosList: List<Servicio>) : BaseAdapter() {

    override fun getCount(): Int {
        return serviciosList.size
    }

    override fun getItem(position: Int): Any {
        return serviciosList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val servicio = serviciosList[position]

        val view = LayoutInflater.from(context).inflate(R.layout.activity_servicio_lugares_adapter, parent, false)

        val servicioNombre = view.findViewById<TextView>(R.id.servicioNombre)
        val servicioFoto = view.findViewById<ImageView>(R.id.servicioFoto)
        val cardView = view.findViewById<CardView>(R.id.cardView) // Agrega la referencia al CardView


        servicioNombre.text = servicio.nombre

        // Cargar la imagen en un hilo de fondo
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(servicio.foto)
                .submit()
                .get()

            // Actualizar la vista en el hilo principal
            withContext(Dispatchers.Main) {
                servicioFoto.setImageBitmap(bitmap)
            }
        }

        // Agregar márgenes en la parte superior e inferior
        val margin = context.resources.getDimensionPixelSize(R.dimen.your_margin_dimen) // Reemplaza 'your_margin_dimen' con el recurso de dimensión deseado
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.topMargin = margin
        layoutParams.bottomMargin = margin
        view.layoutParams = layoutParams

        // Initialize Firestore
        val db = FirebaseFirestore.getInstance()
        cardView.setOnClickListener {
            // Assuming you have Firestore initialized (as shown earlier)
            val imageList = mutableListOf<ImageData>()
            db.collection("servicio")
                .whereEqualTo("idEmpresa", "1")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        // Extract image URL, likes, and document ID from the document
                        val documentId = document.id
                        val imageUrl = document.getString("foto") ?: ""
                        val likes = document.getLong("likes")?.toInt() ?: 0

                        // Create an ImageData object and add it to the list
                        val imageData = ImageData(documentId, imageUrl, likes)
                        imageList.add(imageData)
                    }
                    // After populating imageList, start the activity to navigate through images
                    val intent = Intent(context, Categorias4Activity::class.java)
                    intent.putExtra("imageList", ArrayList(imageList))
                    context.startActivity(intent)
                }
        }
        return view
    }
}