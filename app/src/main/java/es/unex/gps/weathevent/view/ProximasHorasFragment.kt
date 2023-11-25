package es.unex.gps.weathevent

/**
 * A simple [Fragment] subclass.
 * Use the [ProximasHorasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

// Gestión de dias
class ProximasHorasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProximasHorasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProximasHorasAdapter

    var _tiempos: MutableList<TiempoPorHora> = mutableListOf()

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
        } else {
            throw RuntimeException(context.toString() + " must implement CiudadParam")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ciudad = ciudadParam.getCiudadParam()
        // Inflate the layout for this fragment
        _binding = FragmentProximasHorasBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProximasHorasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProximasHorasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}