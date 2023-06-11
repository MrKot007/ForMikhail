package com.example.recreation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.recreation.Connection.api
import com.example.recreation.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sexes = arrayOf<String>("Мужчина", "Женщина")
        var chosenSex = "Мужчина"
        binding.spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexes)
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chosenSex = sexes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.create.setOnClickListener {
            val request = listOf<String>(
                binding.firstName.text.toString(),
                binding.secondName.text.toString(),
                binding.patronymic.text.toString(),
                chosenSex,
                binding.date.toString(),
                binding.email.toString(),
                binding.pass.toString()
            )
            request.forEach {
                if (it == "") {
                    showAlert("Заполнены не все поля!", this@SignUpActivity)
                    return@setOnClickListener
                }
            }
            if (binding.pass.text != binding.pssRepeat) {
                showAlert("Пароли не совпадают!", this@SignUpActivity)
                return@setOnClickListener
            }
            api.signUp(ModelReg(request[0], request[1], request[2], request[5], request[6], request[4], request[3])).push(object: OnGetData<ModelIdentity>{
                override fun onGet(data: ModelIdentity) {
                    SharedPref.saveEmail(request[5], this@SignUpActivity)
                    SharedPref.savePassword(request[6], this@SignUpActivity)
                    SharedPref.saveToken(data.token, this@SignUpActivity)
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }

                override fun onError(error: String) {
                    Log.e("ERRROR", error)
                }

            },this)
        }

    }
    private fun showAlert(reason: String, context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Ошибка!")
            .setMessage(reason)
            .setPositiveButton("OK") {
                    dialog, id-> dialog.cancel()
            }.create().show()
    }
}