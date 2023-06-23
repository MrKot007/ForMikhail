package com.example.courses

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.Connection.api
import com.example.courses.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cartRec.adapter = CartAdapter(cart)
        binding.cartRec.layoutManager = LinearLayoutManager(this@CartActivity)
        var sum = 0
        for (i in cart) {
            sum+=i.price
        }
        binding.sum.text ="₽$sum"
        binding.count.text = "${cart.size} шт"
        binding.submit.setOnClickListener {
            val order = mutableListOf<Int>()
            for (i in cart) {
                order.add(i.id)
            }
            api.createOrder("124", order).push(object: OnGetData<Boolean>{
                override fun onGet(data: Boolean) {
                    binding.submit.setCardBackgroundColor(Color.rgb(230, 230, 230))
                    binding.submitText.text = "Заказ оформлен!"
                    cart.clear()
                    binding.cartRec.adapter!!.notifyDataSetChanged()
                    sum = 0
                }

                override fun onError(error: String) {
                    Log.e("ERRROR", error)
                }
            }, this)
        }
        binding.backFromCart.setOnClickListener {
            finish()
        }
    }
}