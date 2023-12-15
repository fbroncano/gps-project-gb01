package es.unex.gps.weathevent.view.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.databinding.FragmentFavoritosBinding
import es.unex.gps.weathevent.interfaces.OnCiudadClickListener
import es.unex.gps.weathevent.view.home.HomeViewModel

class FavoritosFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: FavoritosViewModel by viewModels{ FavoritosViewModel.Factory }

    private lateinit var listener: OnCiudadClickListener
    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var adapter: FavoritosAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnCiudadClickListener) {
            listener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                Toast.makeText(context, "${it.name} removed from favorites", Toast.LENGTH_SHORT)
                    .show()
            },
            onCiudadClick = {
                listener.onCiudadClick(it)
            },
        )

        with(binding) {
            rvFavoritos.layoutManager = LinearLayoutManager(context)
            rvFavoritos.adapter = adapter
        }

        Log.d("DiscoverFragment", "setUpRecyclerView")
    }
}