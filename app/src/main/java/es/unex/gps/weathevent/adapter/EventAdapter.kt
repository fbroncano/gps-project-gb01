package es.unex.gps.weathevent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.databinding.EventItemListBinding
import es.unex.gps.weathevent.model.Event


class EventAdapter (
    private var events: List<Event>,
    private val onClick: (event: Event) -> Unit,
    private val onClickDelete: (event: Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(
        private val binding: EventItemListBinding,
        private val onClick: (event: Event) -> Unit,
        private val onClickDelete: (event: Event) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, totalItems: Int) {
            with(binding) {
                fechaView.text = event.date.getFormatDay()
                horaEventoView.text = event.date.getFormatHour()
                nombreEventoView.text = event.name

                cvEvent.setOnClickListener {
                    onClick(event)
                }

                deleteButton.setOnClickListener {
                    onClickDelete(event)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            EventItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onClick, onClickDelete)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position], events.size)
    }

    fun updateData(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }
}