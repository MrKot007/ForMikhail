package com.example.workspace

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workspace.databinding.LiveTaskBinding

class LiveTaskViewHolder(var binding: LiveTaskBinding) : RecyclerView.ViewHolder(binding.root)
class MainRecyclerAdapter(val list: List<ModelTask>) : RecyclerView.Adapter<LiveTaskViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveTaskViewHolder {
        return LiveTaskViewHolder(LiveTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LiveTaskViewHolder, position: Int) {
        holder.binding.label.text = list[position].title
        holder.binding.time.text = "${list[position].duration/60} ${getHoursText(list[position].duration/60)}"
        if (list[position].isComplete) {
            holder.binding.status.text = "выполнено"
            holder.binding.statusBgc.setCardBackgroundColor(Color.rgb(255, 224, 127))
        }else {
            holder.binding.status.text = "начать"
            holder.binding.statusBgc.setCardBackgroundColor(Color.rgb(255, 193, 0))
        }
    }
    private fun getHoursText(hours: Int): String {
        if (hours%10 == 1 && hours != 11) {
            return "час"
        }
        if (hours%10 in listOf(2, 3, 4)) {
            return "часа"
        }
        return "часов"
    }

}