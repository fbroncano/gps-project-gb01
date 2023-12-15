package es.unex.gps.weathevent.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.databinding.FavoritoItemListBinding
import es.unex.gps.weathevent.model.Ciudad

class FavoritosAdapter(
    private var ciudades: List<Ciudad>,
    private val onCiudadClick: (ciudad: Ciudad) -> Unit,
    private val onFavoriteClick: (ciudad: Ciudad) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.ShowViewHolder>() {

    class ShowViewHolder(
        private val binding: FavoritoItemListBinding,
        private val onCiudadClick: (ciudad: Ciudad) -> Unit,
        private val onFavoriteClick: (ciudad: Ciudad) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ciudad: Ciudad, totalItems: Int) {
            with(binding) {
                cityName2.text = ciudad.name

                cvItem.setOnClickListener {
                    onCiudadClick(ciudad)
                }

                favoriteIcon2.setOnClickListener {
                    onFavoriteClick(ciudad)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding =
            FavoritoItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding, onCiudadClick, onFavoriteClick)
    }

    override fun getItemCount() = ciudades.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(ciudades[position], ciudades.size)
    }

    fun updateData(newCiudades: List<Ciudad>) {
        ciudades = newCiudades
        notifyDataSetChanged()
    }

}