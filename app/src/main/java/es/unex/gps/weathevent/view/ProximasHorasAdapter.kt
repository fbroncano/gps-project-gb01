package es.unex.gps.weathevent.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.model.TiempoPorHora
import es.unex.gps.weathevent.databinding.PronosticoItemListBinding

class ProximasHorasAdapter(
    private var tiempos: List<TiempoPorHora>,
)
     : RecyclerView.Adapter<ProximasHorasAdapter.ShowViewHolder>() {
        class ShowViewHolder(
            private val binding: PronosticoItemListBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(tiempo: TiempoPorHora, totalItems: Int) {
                with(binding) {
                    hora.text = tiempo.hora
                    temperatura.text = tiempo.temperatura
                    descripcionView.text = tiempo.estadoCieloDescripcion

                    //Gestión de imagen
                    when {
                        tiempo.estadoCieloDescripcion =="Despejado" -> {
                            imageSky.setImageResource(R.drawable.sol)
                        }
                        tiempo.estadoCieloDescripcion =="Nuboso" ||
                                tiempo.estadoCieloDescripcion =="Muy nuboso" ||
                                tiempo.estadoCieloDescripcion =="Cubierto"||
                                tiempo.estadoCieloDescripcion =="Intervalos nubosos" -> {
                            imageSky.setImageResource(R.drawable.baseline_cloud_24)
                        }
                        tiempo.estadoCieloDescripcion =="Poco nuboso"||
                                tiempo.estadoCieloDescripcion == "Nubes altas" -> {
                            imageSky.setImageResource(R.drawable.sunandclouds)
                        }
                        tiempo.estadoCieloDescripcion =="Niebla" ||
                                tiempo.estadoCieloDescripcion =="Bruma" -> {
                            imageSky.setImageResource(R.drawable.niebla)
                        }
                        tiempo.estadoCieloDescripcion.contains("lluvia", true) -> {
                            imageSky.setImageResource(R.drawable.lluvia)
                        }
                        else -> {
                            imageSky.setImageResource(R.drawable.baseline_cloud_24)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
            val binding =
                PronosticoItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShowViewHolder(binding)
        }

        override fun getItemCount() = tiempos.size

        override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
            holder.bind(tiempos[position], tiempos.size)
        }

        fun updateData(newTiempos: List<TiempoPorHora>) {
            tiempos = newTiempos
            notifyDataSetChanged()
        }
}