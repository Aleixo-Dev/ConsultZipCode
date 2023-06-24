package br.com.nicolas.consultacd.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.nicolas.consultacd.databinding.LayoutDirectBinding
import java.lang.StringBuilder

class HomeAdapter(
    private val cities: List<String>,
    private val state: String
) : RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {

    inner class MainViewHolder(private val binding: LayoutDirectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: String) {
            binding.textViewDirectCity.text = city
            binding.textViewDirectState.text = StringBuilder()
                .append("-").append(state)
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
        val sortedByNameList = cities.sortedBy { it }
        holder.bind(sortedByNameList[position])
    }

    override fun getItemCount() = cities.size
}