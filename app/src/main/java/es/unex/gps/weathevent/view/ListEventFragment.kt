package es.unex.gps.weathevent.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.FragmentListEventBinding
import es.unex.gps.weathevent.interfaces.OnClickEventListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListEventFragment : Fragment() {

    private var _binding: FragmentListEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventAdapter

    private lateinit var userParam: UserParam
    private lateinit var listener: OnClickEventListener

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnClickEventListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEventClickListener")
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
        _binding = FragmentListEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addEventButton.setOnClickListener {
            val intent = Intent(context, AddEventActivity::class.java)
            intent.putExtra(AddEventActivity.USER, userParam.getUserFragment())
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        var events : List<Event>

        lifecycleScope.launch {
            Log.d("userID", userParam.getUserFragment().userId!!.toString())
            events = WeathEventDataBase.getInstance(requireContext()).eventDao().searchByUser(userParam.getUserFragment().userId!!)

            adapter = EventAdapter(events,
                onClick = {
                    listener.onEventClick(it)
                },
                onClickDelete = {
                    listener.onEventDelete(it)
                    lifecycleScope.launch {
                        events = WeathEventDataBase.getInstance(requireContext()).eventDao()
                            .searchByUser(userParam.getUserFragment().userId!!)
                        (binding.rvListEvents.adapter as EventAdapter).updateData(events)
                    }
                }
            )

            with(binding) {
                rvListEvents.layoutManager = LinearLayoutManager(context)
                rvListEvents.adapter = adapter
            }
        }

        Log.d("ListEventFragment", "setUpRecyclerView")
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            var events = WeathEventDataBase.getInstance(requireContext()).eventDao().searchByUser(userParam.getUserFragment().userId!!)
            (binding.rvListEvents.adapter as EventAdapter).updateData(events)
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
         * @return A new instance of fragment ListEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}