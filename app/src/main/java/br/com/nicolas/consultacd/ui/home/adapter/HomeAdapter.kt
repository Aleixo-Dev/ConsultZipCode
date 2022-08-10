package br.com.nicolas.consultacd.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.nicolas.consultacd.databinding.LayoutCepBinding
import br.com.nicolas.consultacd.databinding.LayoutDirectBinding
import br.com.nicolas.consultacd.models.CepRemote

class HomeAdapter(
    private val cities: List<String>
) : RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {

    class MainViewHolder(private val binding: LayoutDirectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cities: String) {
            binding.textViewDirectCity.text = cities
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutDirectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount() = cities.size
}