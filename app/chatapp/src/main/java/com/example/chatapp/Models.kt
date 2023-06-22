package com.example.chatapp

import android.graphics.Color

data class ModelUser(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val avatar: String
) {
    fun getFullName() : String {
        return "$firstname $lastname"
    }
    fun getUserColor() : Int {
        val r = Math.pow(id.toDouble(), 4.0)
        val g = Math.pow(id.toDouble(), 3.0)
        val b = Math.pow(id.toDouble(), 5.0)
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }
}
data class ModelResponse<T>(
    val type: String,
    val body: T
)
data class ModelUserMessage(
    val id: Int,
    val message: String,
    val idChat: Int,
    val idUser: Int,
    val isYou: Boolean,
    val datetime: String,
    val isAudio: Boolean
) {
    fun toRenderMessage(user: ModelUser) : ModelRenderMessage {
        return ModelRenderMessage(id, message, user, datetime, isYou, isAudio)
    }
}

data class ModelArchivedMessage(
    val id: Int,
    val text: String,
    val datetime: String,
    val idUser: Int,
    val idChat: Int,
    val isAudio: Boolean
) {
    fun toRenderMessage(isYou: Boolean, user: ModelUser) : ModelRenderMessage {
        return ModelRenderMessage(id, text, user, datetime, isYou, isAudio)
    }
}
data class SendMessage(
    val text: String,
    val idChat: Int,
    val isAudio: Boolean
)

data class ModelRenderMessage(
    val id: Int,
    val message: String?,
    val user: ModelUser,
    val datetime: String,
    val isYou: Boolean,
    val isAudio: Boolean
)
data class ModelChat(
    val id: Int,
    val first: ModelUser,
    val second: ModelUser
)
data class ModelDataChat(
    val chat: ModelChat,
    val messages: List<ModelArchivedMessage>
) {
    fun getUser(idUser: Int) : ModelUser {
        if (idUser == chat.first.id) {
            return chat.first
        }else {
            return chat.second
        }
    }
}
