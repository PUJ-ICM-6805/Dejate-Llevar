package com.example.dejatellevar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class SolicitudAdapter(context: Context, resource: Int, objects: List<Solicitud>) :
    ArrayAdapter<Solicitud>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: inflater.inflate(R.layout.activity_solicitud_adapter, parent, false)
        val currentItem = getItem(position)

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val acceptButton: Button = itemView.findViewById(R.id.acceptButton)

        titleTextView.text = currentItem?.titulo

        val buttonText = if (currentItem?.status == true) "aceptado" else "aceptar"
        acceptButton.text = buttonText

        acceptButton.setOnClickListener {
            currentItem?.let {
                it.status = !it.status
                notifyDataSetChanged()
            }

            val intent = Intent(context, Chat::class.java)
            context.startActivity(intent)
        }

        return itemView
    }
}
