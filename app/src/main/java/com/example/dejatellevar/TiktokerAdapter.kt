package com.example.dejatellevar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TiktokerAdapter(private val context: Context, private val tiktokerList: List<Tiktoker>) :
    RecyclerView.Adapter<TiktokerAdapter.TiktokerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiktokerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tiktoker, parent, false)
        return TiktokerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TiktokerViewHolder, position: Int) {
        val tiktoker = tiktokerList[position]
        holder.bind(tiktoker)
    }

    override fun getItemCount(): Int {
        return tiktokerList.size
    }

    inner class TiktokerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTiktokerName: TextView = itemView.findViewById(R.id.textViewTiktokerName)
        private val imageViewTiktoker: ImageView = itemView.findViewById(R.id.imageViewTiktoker)

        fun bind(tiktoker: Tiktoker) {
            textViewTiktokerName.text = tiktoker.nombre

            // Cargar la imagen utilizando Glide
            Glide.with(context)
                .load(tiktoker.imagenUrl)
                .placeholder(R.drawable.addcard)
                .into(imageViewTiktoker)
        }
    }
}
