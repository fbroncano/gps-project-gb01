package es.unex.gps.weathevent.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.databinding.FragmentProximasHorasBinding
import es.unex.gps.weathevent.interfaces.CiudadParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.TiempoPorHora
import es.unex.gps.weathevent.view.home.BuscarViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


// Gestión de dias
class ProximasHorasFragment : Fragment() {


    private var _binding: FragmentProximasHorasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProximasHorasAdapter

    var _tiempos: MutableList<TiempoPorHora> = mutableListOf()

    private var ciudad: Ciudad? = null

    private val pronosticoViewModel: PronosticoViewModel by viewModels { PronosticoViewModel.Factory }

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

        pronosticoViewModel.ciudad.observe(viewLifecycleOwner) { ciudad ->
            this.ciudad = ciudad
        }
        lifecycleScope.launch {
            if (_tiempos.isEmpty()) {
                try {
                    val codProv = APIHelpers.getCodProv(ciudad?.ciudadId!!)
                    val ciudadId = APIHelpers.getCiudadFormat(ciudad?.ciudadId!!)

                    val response = getElTiempoService().getMunicipio(codProv, ciudadId)

                    val horasHoy = response.pronostico?.hoy?.viento?.map {
                        it.attributes?.periodo
                    }

                    var hour = LocalDateTime.now().hour.toString()
                    if (hour.length == 1) hour = "0$hour"

                    val index = horasHoy?.indexOf(hour)!!

                    // Se añaden las horas restantes del día en curso
                    if (index != -1) {
                        if ((index + 1) < horasHoy?.size!!) {
                            for (i in (index + 1)..< horasHoy?.size!!) {
                                _tiempos.add(
                                    TiempoPorHora(
                                        horasHoy?.get(i)!! + ":00",
                                        APIHelpers.convertTempToPreferences(response.pronostico?.hoy?.temperatura?.get(i)!!.toLong(), requireContext()),
                                        response.pronostico?.hoy?.estadoCieloDescripcion?.get(i)!!
                                    )
                                )
                            }
                        }
                    }

                    // Se añaden las horas del día siguiente
                    val horasManana = response.pronostico?.manana?.viento?.map {
                        it.attributes?.periodo
                    }

                    Log.d("INDICES", index.toString() + " " + horasHoy?.size.toString() + " " + horasManana?.size.toString())
                    for (i in 0..< horasManana?.size!!) {
                        _tiempos.add(
                            TiempoPorHora(
                                horasManana?.get(i)!! + ":00",
                                APIHelpers.convertTempToPreferences(response.pronostico?.manana?.temperatura?.get(i)!!.toLong(), requireContext()),
                                response.pronostico?.manana?.estadoCieloDescripcion?.get(i)!!
                            )
                        )
                    }

                    adapter.updateData(_tiempos)
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        adapter = ProximasHorasAdapter(
            tiempos = _tiempos
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