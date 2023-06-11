package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            Toast.makeText(this@ChatListActivity, "Welcome, ${user.firstname}!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onChat(chat: ModelDataChat) {}

    override fun onChats(chats: List<ModelChat>) {

    }

    override fun onMessage(message: ModelUserMessage) {}
}