package com.example.courses

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.TagBinding

class TagViewHolder(val binding: TagBinding) : RecyclerView.ViewHolder(binding.root)
class TagsAdapter(val list: List<ModelTag>) : RecyclerView.Adapter<TagViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(TagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            holder.binding.tagName.text = list[position].name
            holder.binding.back.setCardBackgroundColor(Color.rgb(255, 193, 0))
        }else {
            holder.binding.tagName.text = list[position].name
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].name == "Все") {
            return 0
        } else {
            return 1
        }
    }

}