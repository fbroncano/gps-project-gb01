package es.unex.gps.weathevent.view.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.databinding.DayItemListBinding
import es.unex.gps.weathevent.model.ProximosDiasTiempo

class ProximosDiasAdapter(private var dias: List<ProximosDiasTiempo>)
    :RecyclerView.Adapter<ProximosDiasAdapter.DayViewHolder>(){

    class DayViewHolder(private val binding: DayItemListBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(dia: ProximosDiasTiempo, totalItems: Int) {
            with(binding) {
                dayView.text = dia.dia
                temperaturaView.text = dia.temperatura
                estadoCieloView.text = dia.estadoCieloDescripcion
                sensTermicaView.text = dia.sensTermica
                precipitacionView.text = dia.precipitacion
                vientoView.text = dia.viento

                when {
                    dia.estadoCieloDescripcion =="Despejado" -> {
                        estadoImageView.setImageResource(R.drawable.sol)
                    }
                    dia.estadoCieloDescripcion =="Nuboso" ||
                            dia.estadoCieloDescripcion =="Muy nuboso" ||
                            dia.estadoCieloDescripcion =="Cubierto"||
                            dia.estadoCieloDescripcion =="Intervalos nubosos" -> {
                        estadoImageView.setImageResource(R.drawable.baseline_cloud_24)
                    }
                    dia.estadoCieloDescripcion =="Poco nuboso"||
                            dia.estadoCieloDescripcion == "Nubes altas" -> {
                        estadoImageView.setImageResource(R.drawable.sunandclouds)
                    }
                    dia.estadoCieloDescripcion =="Niebla" ||
                            dia.estadoCieloDescripcion =="Bruma" -> {
                        estadoImageView.setImageResource(R.drawable.niebla)
                    }
                    dia.estadoCieloDescripcion.contains("lluvia", true) -> {
                        estadoImageView.setImageResource(R.drawable.lluvia)
                    }
                    else -> {
                        estadoImageView.setImageResource(R.drawable.baseline_cloud_24)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding =
            DayItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun getItemCount() = dias.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dias[position],  dias.size )
    }

    fun updateData(newDias: List<ProximosDiasTiempo>) {
        dias = newDias
        notifyDataSetChanged()
    }
}