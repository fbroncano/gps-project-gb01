package es.unex.gps.weathevent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.SearchItemListBinding
import es.unex.gps.weathevent.model.Ciudad

class BuscarAdapter (
    private var ciudades: List<Ciudad>,
    private val onFavoriteClick: (ciudad: Ciudad) -> Unit,
    private val onCiudadClick: (ciudad: Ciudad) -> Unit,
    private val context: Context?,
    ) : RecyclerView.Adapter<BuscarAdapter.ShowViewHolder>() {
        class ShowViewHolder(
            private val binding: SearchItemListBinding,
            private val onFavoriteClick: (ciudad: Ciudad) -> Unit,
            private val onCiudadClick: (ciudad: Ciudad) -> Unit,
            private val context: Context?,
            private val db: WeathEventDataBase = context?.let { WeathEventDataBase.getInstance(it) }!!

        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(ciudad: Ciudad, totalItems: Int) {

                with(binding) {
                    cityName.text = ciudad.name

                    if (ciudad.isFavorite) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24)
                    } else {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_40)
                    }

                    favoriteIcon.setOnClickListener {
                        if(!ciudad.isFavorite) {
                            ciudad.isFavorite = true
                            favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24)
                            onFavoriteClick(ciudad)
                        }
                    }

                    cityName.setOnClickListener {
                        onCiudadClick(ciudad)
                    }
                }
            }


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
            val binding =
                SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShowViewHolder(binding, onFavoriteClick, onCiudadClick,context)
        }

        override fun getItemCount() = ciudades.size



        override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
            holder.bind(ciudades[position], ciudades.size)
        }

        fun updateData(ciudades: List<Ciudad>) {
            this.ciudades = ciudades
            notifyDataSetChanged()
        }
}