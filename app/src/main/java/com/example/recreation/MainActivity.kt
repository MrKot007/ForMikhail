package com.example.recreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.recreation.Connection.api
import com.example.recreation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.exit.setOnClickListener {
            api.signOut("Bearer ${SharedPref.getToken(this@MainActivity)!!}").push(object: OnGetData<Boolean>{
                override fun onGet(data: Boolean) {
                    SharedPref.saveEmail("", this@MainActivity)
                    SharedPref.savePassword("", this@MainActivity)
                    SharedPref.saveToken("", this@MainActivity)
                    finishAffinity()
                }

                override fun onError(error: String) {
                    Log.e("ERRROR", error)
                }
            }, this)
        }
    }
}