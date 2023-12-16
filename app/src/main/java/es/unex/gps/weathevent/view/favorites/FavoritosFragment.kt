package es.unex.gps.weathevent.view.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.databinding.FragmentFavoritosBinding
import es.unex.gps.weathevent.view.weather.PronosticoActivity

class FavoritosFragment : Fragment() {

    private val viewModel: FavoritosViewModel by viewModels{ FavoritosViewModel.Factory }

    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var adapter: FavoritosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        suscribeUi(adapter)
    }

    private fun suscribeUi(adapter: FavoritosAdapter) {
        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    private fun setUpRecyclerView() {
        adapter = FavoritosAdapter(
            ciudades = emptyList(),
            onFavoriteClick = {
                viewModel.setNoFavorite(it)
                Toast.makeText(context, "${it.name} eliminado de favoritos", Toast.LENGTH_SHORT)
                    .show()
            },
            onCiudadClick = { ciudad ->
                val intent = Intent(requireContext(), PronosticoActivity::class.java)
                intent.putExtra(PronosticoActivity.CIUDAD, ciudad)
                startActivity(intent)
            }
        )

        with(binding) {
            rvFavoritos.layoutManager = LinearLayoutManager(context)
            rvFavoritos.adapter = adapter
        }
    }
}