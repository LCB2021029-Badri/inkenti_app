package com.example.inkentiappwhatsappclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.inkentiappwhatsappclone.R
import com.example.inkentiappwhatsappclone.databinding.ChatMsgLayoutBinding
import com.example.inkentiappwhatsappclone.databinding.ChatMsgLayoutReceiverBinding
import com.example.inkentiappwhatsappclone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list:ArrayList<MessageModel>) : RecyclerView.Adapter<ViewHolder>() {

    var ITEM_SENT = 1
    var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType == ITEM_SENT) {
            SenderViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_msg_layout,parent,false))
        }
        else{
            ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_msg_layout_receiver,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = list[position]
        if(holder.itemViewType == ITEM_SENT){
            val viewHolder = holder as SenderViewHolder
            viewHolder.binding.tvMessage.text = message.message
        }
        else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.tvMessage.text = message.message
        }
    }


    inner class SenderViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = ChatMsgLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = ChatMsgLayoutReceiverBinding.bind(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }

}