package es.unex.gps.weathevent.view.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.FragmentListEventBinding
import es.unex.gps.weathevent.interfaces.OnClickEventListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.view.weather.PronosticoActivity

class ListEventFragment : Fragment() {

    private lateinit var binding: FragmentListEventBinding
    private lateinit var adapter: EventAdapter

    private lateinit var userParam: UserParam
    private lateinit var listener: OnClickEventListener
    private var events : List<Event> = emptyList()

    private val viewModel: ListEventViewModel by viewModels { ListEventViewModel.Factory  }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnClickEventListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEventClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        adapter = EventAdapter(events,
            onClick = {
                listener.onEventClick(it)
            },
            onClickDelete = {
                listener.onEventDelete(it)
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