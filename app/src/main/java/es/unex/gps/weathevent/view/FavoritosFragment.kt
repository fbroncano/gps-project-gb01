package es.unex.gps.weathevent.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private lateinit var favRepo: FavoritosRepository
    private lateinit var listener: OnCiudadClickListener

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoritosAdapter

    private var favCiudades: List<Ciudad> = emptyList()

    private lateinit var userParam: UserParam
    private lateinit var user: User

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        suscribeUi(adapter)

    }

    private fun suscribeUi(adapter: FavoritosAdapter) {
        favRepo.favs.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    private fun setUpRecyclerView() {
        adapter = FavoritosAdapter(ciudades = favCiudades,
        onFavoriteClick = {
            setNoFavorite(it)
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


    private fun setNoFavorite(ciudad: Ciudad){
        lifecycleScope.launch {
            favRepo.desmarkFavorite(ciudad.ciudadId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }


}