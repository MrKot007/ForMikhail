package com.example.chatapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ChatItemBinding

class ChatItem(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)
class ChatListAdapter(val chats: List<ModelChat>, val onClickChat: OnClickChat) : RecyclerView.Adapter<ChatItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItem {
        return ChatItem(ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatItem, position: Int) {
        if (chats[position].first.id == UserInfo.userId) {
            holder.binding.background.setCardBackgroundColor(chats[position].second.getUserColor())
            holder.binding.avatar.text = chats[position].second.lastname[0].toString()
            holder.binding.chatName.text = chats[position].second.getFullName()
        }else {
            holder.binding.background.setCardBackgroundColor(chats[position].first.getUserColor())
            holder.binding.avatar.text = chats[position].first.lastname[0].toString()
            holder.binding.chatName.text = chats[position].first.getFullName()
        }
        holder.binding.base.setOnClickListener {
            onClickChat.onClick(chats[position])
        }
    }

}
interface OnClickChat {
    fun onClick(chat: ModelChat)
}