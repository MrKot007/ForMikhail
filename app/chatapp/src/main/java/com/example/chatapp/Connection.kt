package com.example.chatapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

object Connection {
    const val url = "ws://strukov-artemii.online:8085"
    val callbacks: MutableList<Callback> = mutableListOf()
    val client = object: WebSocketClient(URI("$url/chat"), mapOf("Authorization" to "Bearer ${UserInfo.token}")) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            callbacks.forEach {
                it.onOpen()
            }
        }

        override fun onMessage(message: String) {
            if ("\"type\":\"person\"" in message) {
                val modelUser = Gson().fromJson<ModelResponse<ModelUser>>(message, object:
                    TypeToken<ModelResponse<ModelUser>>(){}.type).body
                callbacks.forEach {
                    it.onPerson(modelUser)
                }
            }
            if ("\"type\":\"message\"" in message) {
                val modelMessage = Gson().fromJson<ModelResponse<ModelUserMessage>>(message, object:
                    TypeToken<ModelResponse<ModelUserMessage>>(){}.type).body
                callbacks.forEach {
                    it.onMessage(modelMessage)
                }
            }
            if ("\"type\":\"chats\"" in message) {
                val chats = Gson().fromJson<ModelResponse<List<ModelChat>>>(message, object:
                    TypeToken<ModelResponse<List<ModelChat>>>(){}.type).body
                callbacks.forEach {
                    it.onChats(chats)
                }
            }
            if ("\"type\":\"chat\"" in message) {
                val modelDataChat = Gson().fromJson<ModelResponse<ModelDataChat>>(message, object:
                    TypeToken<ModelResponse<ModelDataChat>>(){}.type).body
                callbacks.forEach {
                    it.onChat(modelDataChat)
                }
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {}

        override fun onError(ex: Exception?) {}

    }
}
interface Callback {
    fun onOpen()
    fun onPerson(user: ModelUser)
    fun onChat(chat: ModelDataChat)
    fun onChats(chats: List<ModelChat>)
    fun onMessage(message: ModelUserMessage)
}