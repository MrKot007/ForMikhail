package com.example.workspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workspace.databinding.ActivityWorkSpaceBinding

class WorkSpaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkSpaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}