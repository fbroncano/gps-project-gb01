package es.unex.gps.weathevent.view

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritosFragment : Fragment() {

    private lateinit var db: WeathEventDataBase

    private lateinit var listener: OnCiudadClickListener
    interface OnCiudadClickListener {
        fun onCiudadClick(ciudad: Ciudad)
    }

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoritosAdapter

    private var favCiudades: List<Ciudad> = emptyList()

    private lateinit var userParam: ListEventFragment.UserParamFragment
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

        if (context is ListEventFragment.UserParamFragment) {
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
        // Inflate the layout for this fragment
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        loadFavorites()
    }

    private fun setUpRecyclerView() {
        adapter = FavoritosAdapter(ciudades = favCiudades,
        onFavoriteClick = {
            setNoFavorite(it)
            loadFavorites()
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

    private fun loadFavorites(){
        lifecycleScope.launch {
            binding.spinner.visibility = View.VISIBLE
            favCiudades = user.userId?.let { db.ciudadDao().getUserWithCiudades(it).ciudades }!!
            adapter.updateData(favCiudades)
            binding.spinner.visibility = View.GONE
        }
    }
    private fun setNoFavorite(ciudad: Ciudad){
        lifecycleScope.launch {
            ciudad.isFavorite = false
            db.ciudadDao().delete(ciudad)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }


}