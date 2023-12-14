package es.unex.gps.weathevent.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.adapter.BuscarAdapter
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.databinding.FragmentProximasHorasBinding
import es.unex.gps.weathevent.interfaces.CiudadParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.TiempoPorHora
import es.unex.gps.weathevent.view.home.BuscarViewModel
import es.unex.gps.weathevent.view.home.HomeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


// Gestión de dias
class ProximasHorasFragment : Fragment() {


    private var _binding: FragmentProximasHorasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProximasHorasAdapter

    private val viewModel: PronosticoViewModel by activityViewModels {PronosticoViewModel.Factory}

    private var ciudad: Ciudad? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProximasHorasBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        lifecycleScope.launch {
            viewModel.getProximasHoras(requireContext(), adapter)
        }
        //subscribeUi(adapter)
    }


    private fun subscribeUi(adapter: ProximasHorasAdapter) {
        viewModel.tiempos.observe(viewLifecycleOwner) {
                tiempos -> adapter.updateData(tiempos)
        }
    }
    private fun setUpRecyclerView() {
        adapter = ProximasHorasAdapter(
            tiempos = emptyList()
        )
        with(binding) {
            rvProximasHoras.layoutManager = LinearLayoutManager(context)
            rvProximasHoras.adapter = adapter
        }

        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}