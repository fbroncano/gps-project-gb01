package es.unex.gps.weathevent.view.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.databinding.FragmentListEventBinding

class ListEventFragment : Fragment() {

    private lateinit var binding: FragmentListEventBinding
    private lateinit var adapter: EventAdapter
    private val viewModel: ListEventViewModel by viewModels { ListEventViewModel.Factory  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addEventButton.setOnClickListener {
            val intent = Intent(context, AddEventActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
        subscribeUi(adapter)
    }

    private fun setUpRecyclerView() {
        adapter = EventAdapter(
            emptyList(),
            onClick = {
                val intent = Intent(requireContext(), EventDetailsActivity::class.java).apply {
                    putExtra(EventDetailsActivity.EVENT, it.id)
                }
                startActivity(intent)
            },
            onClickDelete = {
                viewModel.deleteEvent(it)
            }
        )

        with(binding) {
            rvListEvents.layoutManager = LinearLayoutManager(context)
            rvListEvents.adapter = adapter
        }
    }

    private fun subscribeUi(adapter: EventAdapter) {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.updateData(events)
        }
    }
}