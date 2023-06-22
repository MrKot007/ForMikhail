package com.example.workspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workspace.databinding.ExpiredTaskBinding
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class TaskViewHolder(val binding: ExpiredTaskBinding) : RecyclerView.ViewHolder(binding.root)
class ExpiredAdapter(val list: List<ModelTask>) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ExpiredTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.lessonName.text = list[position].title
        val lessonDate = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault()).parse(list[position].datetime)
        val today = Date()
        val diffMills = abs(today.time - lessonDate.time)
        val diffSec = diffMills/1000
        val diffMin = diffSec/60
        val diffHrs = diffMin/60
        val diffDays = (diffHrs/24).toInt()

        holder.binding.delay.text = "просрочено - $diffDays ${getDaysText(diffDays)}"
    }
    private fun getDaysText(days: Int) : String{
        if ((days%10 == 1) && (days%100 != 11)) {
            return "день"
        }
        if(days%10 in listOf(2, 3, 4)) {
            return "дня"
        }
        return "дней"
    }

}