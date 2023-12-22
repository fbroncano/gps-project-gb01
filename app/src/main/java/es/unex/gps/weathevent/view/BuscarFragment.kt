package es.unex.gps.weathevent.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.adapter.BuscarAdapter
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.FragmentBuscarBinding
import es.unex.gps.weathevent.interfaces.OnCiudadClickListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [BuscarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class BuscarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var db: WeathEventDataBase

    var _ciudades: List<Ciudad> = emptyList()

    private var _binding: FragmentBuscarBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BuscarAdapter

    private lateinit var userParam: UserParam
    private lateinit var user: User

    private lateinit var listener: OnCiudadClickListener

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = userParam.getUserFragment()
        // Inflate the layout for this fragment
        _binding = FragmentBuscarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launch {
            if (_ciudades.isEmpty()) {
                binding.spinner.visibility = View.VISIBLE
                try {
                    if (_ciudades.isEmpty()){
                        //Obtención de los ids de las ciudades favoritas del usuario
                        var userid = user.userId!!
                        val aux = db.ciudadDao().getUserWithCiudades(userid).ciudades.map { it.ciudadId }.toSet()

                        _ciudades = APIHelpers().fetchMunicipios().map { municipio->
                            val ciudadId = municipio.CODIGOINE?.substring(0, 5)?.toLong() ?: 0
                            val favorite = ciudadId in aux

                            Ciudad(
                                ciudadId = municipio.CODIGOINE?.substring(0, 5)?.toLong() ?: 0,
                                name = municipio.NOMBRE ?: "",
                                isFavorite = favorite
                            )
                        }
                        adapter.updateData(_ciudades)
                    }

                    // Configurar el EditText para filtrar la RecyclerView
                    binding.editText.editText?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            // Llamada al método para filtrar la RecyclerView
                            filterRecyclerView(s.toString())
                        }
                    })
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }finally{
                    binding.spinner.visibility = View.GONE
                }
            }
        }
    }

    private fun filterRecyclerView(query: String) {
        val filteredList = _ciudades.filter { ciudad ->
            ciudad.name.contains(query, ignoreCase = true)
        }
        adapter.updateData(filteredList)
        Log.d("Filtro", "Query: $query, Resultado: $filteredList")
    }

    private fun setUpRecyclerView() {
        adapter = BuscarAdapter(
            ciudades = _ciudades,
            onFavoriteClick = {
                setFavorite(it)
                Toast.makeText(context, "${it.name} added to favorites", Toast.LENGTH_SHORT).show()
            },
            onCiudadClick = {
                listener.onCiudadClick(it)
            },
            context = this.context
        )
        with(binding) {
            rvCiudades.layoutManager = LinearLayoutManager(context)
            rvCiudades.adapter = adapter
        }

        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    private fun setFavorite(ciudad: Ciudad){
        lifecycleScope.launch {
            ciudad.isFavorite = true
            user.userId?.let { db.ciudadDao().insertAndRelate(ciudad, it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BuscarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BuscarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}