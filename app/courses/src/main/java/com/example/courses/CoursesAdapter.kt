package com.example.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.CourseBinding

class CourseViewHolder(val binding: CourseBinding) : RecyclerView.ViewHolder(binding.root)
class CoursesAdapter(var list: List<ModelCourse>, val tags: List<ModelTag>, val onClickCourse: OnClickCourse) : RecyclerView.Adapter<CourseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(CourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.binding.courseTitle.text = list[position].title
        holder.binding.coursePrice.text = "₽${list[position].price}"
        holder.binding.relevantTags.adapter = NestedRecyclerAdapter(tags, list[position].tags)
        holder.binding.relevantTags.layoutManager = LinearLayoutManager(holder.binding.cardView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.cardView.setOnClickListener {
            onClickCourse.onClick(list[position])
        }
    }
    fun setData(data: List<ModelCourse>) {
        list = data
        notifyDataSetChanged()
    }

}
interface OnClickCourse {
    fun onClick(course: ModelCourse)
}