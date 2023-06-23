package com.example.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.databinding.CartItemBinding

class CartCourseViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)
class CartAdapter(val list: List<CourseData>) : RecyclerView.Adapter<CartCourseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartCourseViewHolder {
        return CartCourseViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartCourseViewHolder, position: Int) {
        holder.binding.cartCourseTitle.text = list[position].title
        holder.binding.cartCoursePrice.text = "₽${list[position].price}"
    }

}