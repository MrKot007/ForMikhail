package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatListBinding

class ChatListActivity : AppCompatActivity(), Callback {
    private lateinit var binding: ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Connection.callbacks.add(this)
        Connection.client.connect()
    }

    override fun onOpen() {
        Connection.client.send("/chats")
    }

    override fun onPerson(user: ModelUser) {
        UserInfo.userId = user.id
        runOnUiThread {
            binding.avaBackground.setCardBackgroundColor(user.getUserColor())
            binding.ava.text = user.lastname[0].toString()
            Toast.makeText(this@ChatListActivity, "Welcome, ${user.firstname}!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onChat(chat: ModelDataChat) {}

    override fun onChats(chats: List<ModelChat>) {
        Log.d("CHATS", chats.toString())
        runOnUiThread {
            binding.chatListRecyler.adapter = ChatListAdapter(chats, object: OnClickChat{
                override fun onClick(chat: ModelChat) {

                }
            })
            binding.chatListRecyler.layoutManager = LinearLayoutManager(this@ChatListActivity)
        }
    }

    override fun onMessage(message: ModelUserMessage) {}
}