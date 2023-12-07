package es.unex.gps.weathevent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.SearchItemListBinding
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class BuscarAdapter (
    private var ciudades: List<Ciudad>,
    private val onFavoriteLoad: (ciudad: Ciudad) -> Boolean,
    private val onFavoriteClick: (ciudad: Ciudad) -> Unit,
    private val onCiudadClick: (ciudad: Ciudad) -> Unit
    ) : RecyclerView.Adapter<BuscarAdapter.CiudadViewHolder>() {
        class CiudadViewHolder(
            private val binding: SearchItemListBinding,
            private val onFavoriteLoad: (ciudad: Ciudad) -> Boolean,
            private val onFavoriteClick: (ciudad: Ciudad) -> Unit,
            private val onCiudadClick: (ciudad: Ciudad) -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(ciudad: Ciudad, totalItems: Int) {

                var isFavorite = onFavoriteLoad(ciudad)

                with(binding) {
                    cityName.text = ciudad.name

                    if (isFavorite) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24)
                    } else {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_40)
                    }

                    favoriteIcon.setOnClickListener {

                        if(!isFavorite) {
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadViewHolder {
            val binding =
                SearchItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return CiudadViewHolder(binding, onFavoriteLoad, onFavoriteClick, onCiudadClick)
        }

        override fun getItemCount() = ciudades.size



        override fun onBindViewHolder(holder: CiudadViewHolder, position: Int) {
            holder.bind(ciudades[position], ciudades.size)
        }

        fun updateData(ciudades: List<Ciudad>) {
            this.ciudades = ciudades
            notifyDataSetChanged()
        }
}