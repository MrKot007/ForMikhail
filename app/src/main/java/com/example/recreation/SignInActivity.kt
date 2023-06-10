package com.example.recreation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.recreation.Connection.api
import com.example.recreation.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goToCreateAccount.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
        binding.enter.setOnClickListener {
            val email = binding.mail.text.toString()
            val password = binding.pass.text.toString()
            if (!checkEmail(email)) {
                showAlert("Почта не сходится с паттерном!", this@SignInActivity)
                return@setOnClickListener
            }
            if (email == "" || password == "") {
                showAlert("Заполните все поля!", this@SignInActivity)
                return@setOnClickListener
            }
            api.signIn(ModelAuth(email, password)).push(object: OnGetData<ModelIdentity>{
                override fun onGet(data: ModelIdentity) {
                    SharedPref.saveEmail(email, this@SignInActivity)
                    SharedPref.savePassword(password, this@SignInActivity)
                    SharedPref.saveToken(data.token, this@SignInActivity)
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()
                }

                override fun onError(error: String) {
                    showAlert(error, this@SignInActivity)
                }

            }, this)
        }
    }
    private fun checkEmail(email: String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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