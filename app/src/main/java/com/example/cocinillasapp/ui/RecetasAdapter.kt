package com.example.cocinillasapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocinillasapp.R
import com.example.cocinillasapp.data.model.Receta
import com.squareup.picasso.Picasso

class RecetasAdapter(
    private var recetas: List<Receta>,
    private val onItemClick: (Receta) -> Unit
) : RecyclerView.Adapter<RecetasAdapter.RecetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = recetas[position]
        holder.bind(receta)
        holder.itemView.setOnClickListener { onItemClick(receta) }
    }

    override fun getItemCount(): Int = recetas.size

    fun updateRecetas(recetas: List<Receta>) {
        this.recetas = recetas
        notifyDataSetChanged()
    }

    class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivReceta: ImageView = itemView.findViewById(R.id.ivReceta)
        private val tvNombreReceta: TextView = itemView.findViewById(R.id.tvNombreReceta)

        fun bind(receta: Receta) {
            tvNombreReceta.text = receta.nombre
            Picasso.get().load(receta.imagen).into(ivReceta)
        }
    }
}
