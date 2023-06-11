package com.example.recreation

import android.content.Context
import android.content.SharedPreferences

class SharedPref {
    companion object {
        fun saveEmail(email: String, context: Context) {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("email", email).apply()
        }
        fun getEmail(context: Context) : String?{
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            return sp.getString("email", "no value assigned")
        }
        fun savePassword(password: String, context: Context) {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("password", password).apply()
        }
        fun getPassword(context: Context) : String? {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            return sp.getString("password", "no value assigned")
        }
        fun saveToken(token: String, context: Context) {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("token", token).apply()
        }
        fun getToken(context: Context) : String?{
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            return sp.getString("token", "no value assigned")
        }
        fun saveNotFirstEnter(context: Context) {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean("isFirstEnter", false).apply()
        }
        fun checkFirstEnter(context: Context) : Boolean {
            val sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            return sp.getBoolean("isFirstEnter", true)
        }
    }
}