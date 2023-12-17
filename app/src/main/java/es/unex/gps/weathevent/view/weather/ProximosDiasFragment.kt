package es.unex.gps.weathevent.view.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.databinding.FragmentProximosDiasBinding
import kotlinx.coroutines.launch
class ProximosDiasFragment : Fragment() {

    private var _binding: FragmentProximosDiasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProximosDiasAdapter

    private val viewModel: PronosticoViewModel by activityViewModels { PronosticoViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProximosDiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launch {
            viewModel.getProximosDias(requireContext(), adapter)
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProximosDiasAdapter(
            dias = emptyList()
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
}