package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.databinding.ChatItemBinding

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
            messages = chat.messages.map { it.toRenderMessage(chat.getUser(it.idUser), UserInfo.userId == it.idUser) }
                .toMutableList()
            binding.chat.adapter = ChatAdapter(messages.reversed())
        }
    }

    override fun onChats(chats: List<ModelChat>) {}

    override fun onMessage(message: ModelUserMessage) {
        TODO("Not yet implemented")
    }
}