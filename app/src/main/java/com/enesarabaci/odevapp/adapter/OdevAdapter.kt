package com.enesarabaci.odevapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.model.Odev
import com.enesarabaci.odevapp.view.OdevFragment
import kotlinx.android.synthetic.main.odev_row.view.*

class OdevAdapter(var list : ArrayList<Odev>, var dayList : ArrayList<Int>, var odevFragment : OdevFragment) : RecyclerView.Adapter<OdevAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.odev_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.row_ders.text = list.get(position).ders
        holder.itemView.row_day.text = list.get(position).day.toString()
        holder.itemView.row_month.text = (list.get(position).month + 1).toString()
        holder.itemView.row_year.text = list.get(position).year.toString()
        if (dayList.get(position) == 366) {
            holder.itemView.dayText.setTextColor(Color.parseColor("#28df99"))
            holder.itemView.dayText.text = "365+"
        }else if(dayList.get(position) < 366 && dayList.get(position) > 7) {
            holder.itemView.dayText.setTextColor(Color.parseColor("#28df99"))
            holder.itemView.dayText.text = dayList.get(position).toString()
        }else if(dayList.get(position) < 8 && dayList.get(position) > 3) {
            holder.itemView.dayText.text = dayList.get(position).toString()
            holder.itemView.dayText.setTextColor(Color.parseColor("#fddb3a"))
        }else if(dayList.get(position) < 4 && dayList.get(position) > 0) {
            holder.itemView.dayText.text = dayList.get(position).toString()
            holder.itemView.dayText.setTextColor(Color.parseColor("#ff4b5c"))
        }else if(dayList.get(position) == 0) {
            holder.itemView.dayText.setText("Bugün")
        }else if (dayList.get(position) < 0) {
            holder.itemView.dayText.setText("")
        }
        holder.itemView.odev_row_layout.setOnClickListener {
            odevFragment.createDialog(list.get(position), position)
        }
        when(list.get(position).status) {
            "ok" -> holder.itemView.row_status.setBackgroundColor(Color.parseColor("#28df99"))
            "close" -> holder.itemView.row_status.setBackgroundColor(Color.parseColor("#ff4b5c"))
            "wait" -> holder.itemView.row_status.setBackgroundColor(Color.parseColor("#fddb3a"))
        }
        if (list.get(position).status == "ok") {
            holder.itemView.row_status.setBackgroundColor(Color.parseColor("#28df99"))
            holder.itemView.row_status.foreground = ContextCompat.getDrawable(odevFragment.requireContext(), R.drawable.ic_baseline_check_24)
        }else if (list.get(position).status == "close") {
            holder.itemView.row_status.setBackgroundColor(Color.parseColor("#ff4b5c"))
            holder.itemView.row_status.foreground = ContextCompat.getDrawable(odevFragment.requireContext(), R.drawable.ic_baseline_close_24)
        }else if (list.get(position).status == "wait") {
            holder.itemView.row_status.setBackgroundColor(Color.parseColor("#fddb3a"))
            holder.itemView.row_status.foreground = ContextCompat.getDrawable(odevFragment.requireContext(), R.drawable.ic_baseline_timer_24)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    fun refresh(list : ArrayList<Odev>, dayList : ArrayList<Int>) {
        this.list = list
        this.dayList = dayList
        notifyDataSetChanged()
    }

}