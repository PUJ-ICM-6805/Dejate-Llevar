package com.example.dejatellevar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dejatellevar.R  // Reemplaza con la ubicación correcta de tu archivo R
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ServicioAdapter(private val context: Context, private val serviciosList: List<Servicio>) : BaseAdapter() {
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

        val view = LayoutInflater.from(context).inflate(R.layout.activity_servicio_adapter, parent, false)

        val servicioNombre = view.findViewById<TextView>(R.id.servicioNombre)
        val servicioFoto = view.findViewById<ImageView>(R.id.servicioFoto)

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

        return view
    }
}




