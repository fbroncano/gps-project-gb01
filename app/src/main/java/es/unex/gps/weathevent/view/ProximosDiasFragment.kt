package es.unex.gps.weathevent.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.api.ProximosDiasArray
import es.unex.gps.weathevent.data.api.ProximosDiasSingle
import es.unex.gps.weathevent.databinding.FragmentProximosDiasBinding
import es.unex.gps.weathevent.interfaces.CiudadParam
import es.unex.gps.weathevent.model.Ciudad
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProximosDiasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProximosDiasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProximosDiasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProximosDiasAdapter

    var _dias: MutableList<ProximosDiasTiempo> = mutableListOf()

    private var ciudad: Ciudad? = null
    private lateinit var ciudadParam: CiudadParam


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)

        if(context is CiudadParam){
            ciudadParam = context
            ciudad = ciudadParam.getCiudadParam()
        } else {
            throw RuntimeException(context.toString() + " must implement CiudadParam")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProximosDiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launch {
            if (_dias.isEmpty()) {
                try {
                    val codProv = APIHelpers.getCodProv(ciudad?.ciudadId!!)
                    val ciudadId = APIHelpers.getCiudadFormat(ciudad?.ciudadId!!)

                    val response = getElTiempoService().getMunicipio(codProv, ciudadId)
                    response.obtainProximosDias()

                    for (obj in response.proximosDias) {
                        if (obj is ProximosDiasArray) {
                            val dates = obj.attributes?.fecha?.split("-")
                            val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                            _dias.add(ProximosDiasTiempo(
                                fecha.getFormatDay(),
                                "${APIHelpers.convertTempToPreferences(obj.temperatura?.minima?.toLong()!!, requireContext())}\n${APIHelpers.convertTempToPreferences(obj.temperatura?.maxima?.toLong()!!, requireContext())}",
                                obj.estadoCieloDescripcion?.get(0)!!,
                                "Sens. ter.: ${APIHelpers.convertTempToPreferences(obj.sensTermica?.minima?.toLong()!!, requireContext())} - ${APIHelpers.convertTempToPreferences(obj.sensTermica?.maxima?.toLong()!!, requireContext())}",
                                "Precipitacion: ${obj.probPrecipitacion?.get(0)!!}%",
                                "Viento: ${APIHelpers.convertVelToPreferences(obj.viento?.get(0)?.velocidad?.toLong()!!, requireContext())} ${obj.viento?.get(0)?.direccion}"
                                ))
                        } else if (obj is ProximosDiasSingle) {
                            val dates = obj.attributes?.fecha?.split("-")
                            val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                            _dias.add(ProximosDiasTiempo(
                                fecha.getFormatDay(),
                                "${APIHelpers.convertTempToPreferences(obj.temperatura?.minima?.toLong()!!, requireContext())}\n${APIHelpers.convertTempToPreferences(obj.temperatura?.maxima?.toLong()!!, requireContext())}",
                                obj.estadoCieloDescripcion!!,
                                "Sens. ter.: ${APIHelpers.convertTempToPreferences(obj.sensTermica?.minima?.toLong()!!, requireContext())} - ${APIHelpers.convertTempToPreferences(obj.sensTermica?.maxima?.toLong()!!, requireContext())}",
                                "Precipitacion: ${obj.probPrecipitacion?.get(0)!!}%",
                                "Viento: ${APIHelpers.convertVelToPreferences(obj.viento?.velocidad?.toLong()!!, requireContext())} ${obj.viento?.direccion}"
                            ))
                        }
                    }


                    adapter.updateData(_dias)
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProximosDiasAdapter(
            dias = _dias
        )
        with(binding) {
            rvProximosDias.layoutManager = LinearLayoutManager(context)
            rvProximosDias.adapter = adapter
        }

        Log.d("DiscoverFragment", "setUpRecyclerView")
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
         * @return A new instance of fragment ProximosDiasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProximosDiasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}