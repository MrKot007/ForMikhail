package com.example.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.SmallTagBinding

class SmallTagViewHolder(val binding: SmallTagBinding) : RecyclerView.ViewHolder(binding.root)
class NestedRecyclerAdapter(val tags: List<ModelTag>, val ids: List<Int>, val selectedTags: List<Int>) : RecyclerView.Adapter<SmallTagViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallTagViewHolder {
        return SmallTagViewHolder(SmallTagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onBindViewHolder(holder: SmallTagViewHolder, position: Int) {
        for (i in tags.indices) {
            if (tags[i].id in ids) {
                holder.binding.smallTagName.text = tags[position].name
            }else {
                continue
            }
        }
    }

}