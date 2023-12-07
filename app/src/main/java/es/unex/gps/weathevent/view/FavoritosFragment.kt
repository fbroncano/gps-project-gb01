package es.unex.gps.weathevent.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.adapter.FavoritosAdapter
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.database.FavoritoDao
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.FragmentFavoritosBinding
import es.unex.gps.weathevent.interfaces.OnCiudadClickListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.view.home.BuscarViewModel
import es.unex.gps.weathevent.view.home.HomeViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoritosFragment : Fragment() {

    private lateinit var db: WeathEventDataBase
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: FavoritosViewModel by viewModels{ FavoritosViewModel.Factory }
    private lateinit var favRepo: FavoritosRepository
    private lateinit var listener: OnCiudadClickListener

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoritosAdapter

    private var favCiudades: List<Ciudad> = emptyList()

    private lateinit var userParam: UserParam
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = WeathEventDataBase.getInstance(context)!!

        if (context is OnCiudadClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCiudadClickListener")
        }

        if (context is UserParam) {
            userParam = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEventClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = userParam.getUserFragment()
        favRepo = FavoritosRepository.getInstance(db.favoritoDao())
        // Inflate the layout for this fragment
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        Log.e("","suscribeUi")
        suscribeUi(adapter)
    }

    private fun suscribeUi(adapter: FavoritosAdapter) {
        viewModel.favorites.observe(viewLifecycleOwner) {
            var ciudad: Ciudad = it[1]
            Log.e("Ciudad", "${ciudad.name}")
            adapter.updateData(it)
        }
    }

    private fun setUpRecyclerView() {
        adapter = FavoritosAdapter(ciudades = emptyList(),
        onFavoriteClick = {
            viewModel.setNoFavorite(it)
            Toast.makeText(context, "${it.name} removed from favorites", Toast.LENGTH_SHORT).show()
        }, onCiudadClick = {
            listener.onCiudadClick(it)
        },
        )
        with(binding) {
            rvFavoritos.layoutManager = LinearLayoutManager(context)
            rvFavoritos.adapter = adapter
        }
        android.util.Log.d("DiscoverFragment", "setUpRecyclerView")
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }


}