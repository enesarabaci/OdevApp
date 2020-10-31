package com.enesarabaci.odevapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.model.Ders
import kotlinx.android.synthetic.main.ders_row.view.*

class DerslerAdapter(var list : ArrayList<Ders>) : RecyclerView.Adapter<DerslerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ders_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ders_ders.text = list.get(position).ders
        holder.itemView.ders_sayi.text = list.get(position).odevSayi.toString()
        holder.itemView.ders_ok.text = list.get(position).ok.toString()
        holder.itemView.ders_wait.text = list.get(position).wait.toString()
        holder.itemView.ders_close.text = list.get(position).close.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    fun updateList(newList : ArrayList<Ders>) {
        list = newList
        notifyDataSetChanged()
    }

}