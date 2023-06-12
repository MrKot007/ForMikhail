package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.databinding.ChatItemBinding
import com.google.gson.Gson

class ChatActivity : AppCompatActivity(), Callback {
    private lateinit var binding: ActivityChatBinding
    private lateinit var currentChat: ModelDataChat
    private var messages: MutableList<ModelRenderMessage> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Connection.callbacks.add(this)
        binding.chatTitle.text = intent.getStringExtra("title")
        Connection.client.send("/chat ${intent.getIntExtra("chatId", 0)}")
        binding.back.setOnClickListener {
            finish()
        }
        binding.send.setOnClickListener {
            if (binding.messageInput.text.toString() != "") {
                val sendMessage = SendMessage(binding.messageInput.text.toString(), intent.getIntExtra("chatId", 0), false)
                val sendMessageJson = Gson().toJson(sendMessage)
                Connection.client.send(sendMessageJson)
                binding.messageInput.text = null
            }else {
               Toast.makeText(this@ChatActivity, "Coобщение не должно быть пустым!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Connection.callbacks.remove(this)
    }

    override fun onOpen() {}

    override fun onPerson(user: ModelUser) {}

    override fun onChat(chat: ModelDataChat) {
        runOnUiThread {
            currentChat = chat
            binding.avaChat.text = chat.getUser(chat.chat.first.id).lastname[0].toString()
            binding.avaBackgroundChat.setCardBackgroundColor(chat.getUser(chat.chat.first.id).getUserColor())
            messages = chat.messages.map { it.toRenderMessage(UserInfo.userId == it.idUser, chat.getUser(it.idUser) ) }
                .toMutableList()
            binding.chat.adapter = ChatAdapter(messages.reversed())
            binding.chat.layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, true)


        }
    }

    override fun onChats(chats: List<ModelChat>) {}

    override fun onMessage(message: ModelUserMessage) {
        runOnUiThread {
            if (message.id == 0) {
                Log.e("ERRORMESSAGE", message.message)
            }else {
                Log.d("MESSAGERECEIVED", message.message)
                val modelMessage = message.toRenderMessage(currentChat.getUser(message.idUser))
                messages.add(messages.lastIndex+1, modelMessage)
                binding.chat.adapter!!.notifyItemInserted(messages.lastIndex)
                binding.chat.adapter = ChatAdapter(messages.reversed())
            }
        }
    }
}