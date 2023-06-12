package com.example.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.FriendMessageBinding
import com.example.chatapp.databinding.MyMessageBinding

open class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bind(renderMessage: ModelRenderMessage) {}
}
class MyMessageViewHolder(private val binding: MyMessageBinding) : MessageViewHolder(binding.root) {
    override fun bind(renderMessage: ModelRenderMessage) {
        binding.fbgc.setCardBackgroundColor(renderMessage.user.getUserColor())
        binding.date.text = renderMessage.datetime
        binding.msg.text = renderMessage.message
    }
}
class FriendMessageViewHolder(private val binding: FriendMessageBinding) : MessageViewHolder(binding.root) {
    override fun bind(renderMessage: ModelRenderMessage) {
        binding.fbgc.setCardBackgroundColor(renderMessage.user.getUserColor())
        binding.date.text = renderMessage.datetime
        binding.msg.text = renderMessage.message
    }
}
class ChatAdapter(val messages: List<ModelRenderMessage>) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == 0) {
            return MyMessageViewHolder(MyMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else {
            return FriendMessageViewHolder(FriendMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        val modelMessage = messages[position]
        return if (modelMessage.isYou) 0 else 1
    }

}