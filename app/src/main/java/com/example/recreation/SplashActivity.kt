package com.example.recreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.recreation.Connection.api

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (SharedPref.checkFirstEnter(this@SplashActivity)) {
            SharedPref.saveNotFirstEnter(this@SplashActivity)
            startActivity(Intent(this@SplashActivity, SignUpActivity::class.java))
            finish()
        }else {
            api.signIn(ModelAuth(SharedPref.getEmail(this@SplashActivity)!!, SharedPref.getPassword(this@SplashActivity)!!))
                .push(object: OnGetData<ModelIdentity>{
                    override fun onGet(data: ModelIdentity) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onError(error: String) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                            finish()
                        }, 1500)
                    }
                }, this)
        }

    }
}