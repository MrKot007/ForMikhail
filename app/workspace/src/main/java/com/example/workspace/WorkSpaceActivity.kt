package com.example.workspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workspace.Connection.api
import com.example.workspace.databinding.ActivityWorkSpaceBinding

class WorkSpaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkSpaceBinding
    private var date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        refreshData()
        api.getExpired("124").push(object: OnGetData<List<ModelTask>>{
            override fun onGet(data: List<ModelTask>) {
                binding.expiredRec.adapter = ExpiredAdapter(data)
                binding.expiredRec.layoutManager = LinearLayoutManager(this@WorkSpaceActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }

        }, this)
        binding.next.setOnClickListener {
            refreshData("next")
        }
        binding.prev.setOnClickListener {
            refreshData("prev")
        }
    }
    private fun refreshData(direction: String? = null) {
        api.getPlan(date, direction, "124").push(object: OnGetData<List<ModelTask>>{
            override fun onGet(data: List<ModelTask>) {
                val dateStr = data[0].datetime.substringBefore(" ")
                date = dateStr
                binding.date.text = dateStr
                binding.mainRec.adapter = MainRecyclerAdapter(data)
                binding.mainRec.layoutManager = LinearLayoutManager(this@WorkSpaceActivity)
                binding.mainRec.adapter!!.notifyDataSetChanged()
            }

            override fun onError(error: String) {
                Log.e("ERRROR", error)
            }
        }, this)
    }
}