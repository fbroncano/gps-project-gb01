package es.unex.gps.weathevent.view.buscar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.databinding.SearchItemListBinding
import es.unex.gps.weathevent.model.Ciudad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuscarAdapter (
    private var ciudades: List<Ciudad>,
    private val onFavoriteLoad: (ciudad: Ciudad) -> Boolean,
    private val onFavoriteClick: suspend (ciudad: Ciudad) -> Boolean,
    private val onCiudadClick: (ciudad: Ciudad) -> Unit
    ) : RecyclerView.Adapter<BuscarAdapter.CiudadViewHolder>() {
        class CiudadViewHolder(
            private val binding: SearchItemListBinding,
            private val onFavoriteLoad: (ciudad: Ciudad) -> Boolean,
            private val onFavoriteClick: suspend (ciudad: Ciudad) -> Boolean,
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
                        CoroutineScope(Dispatchers.IO).launch {

                            var isFavorite = onFavoriteClick(ciudad)

                            if (isFavorite) {
                                favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24)
                            } else {
                                favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_40)
                            }
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