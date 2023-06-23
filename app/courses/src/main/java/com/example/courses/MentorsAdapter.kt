package com.example.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.MentorBinding

class MentorViewHolder(val binding: MentorBinding) : RecyclerView.ViewHolder(binding.root)
class MentorsAdapter(val list: List<Mentor>) : RecyclerView.Adapter<MentorViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        return MentorViewHolder(MentorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {

    }

}