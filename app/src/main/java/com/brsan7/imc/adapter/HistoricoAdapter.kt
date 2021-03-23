package com.brsan7.imc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brsan7.imc.HistoricoClickedListener
import com.brsan7.imc.R
import com.brsan7.imc.model.HistoricoVO
import kotlinx.android.synthetic.main.item_historico.view.*

class HistoricoAdapter(
        private val context: Context,
        private val lista: List<HistoricoVO>,
        private val listener: HistoricoClickedListener
) : RecyclerView.Adapter<HistoricoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_historico,parent,false)
        return HistoricoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val itemHist = lista[position]
        val dataTxt = "[ "+itemHist.data+" ]"
        val horaTxt = "[ "+itemHist.hora+" ]"
        with(holder.itemView){
            tvData.text = dataTxt
            tvHora.text = horaTxt
            tvPeso.text = itemHist.peso
            fabDelete.setOnClickListener { listener.historicoRemoveItem(itemHist.id) }
            llItem.setOnClickListener { listener.historicoClickedItem(itemHist.id) }
        }
    }

}

class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)