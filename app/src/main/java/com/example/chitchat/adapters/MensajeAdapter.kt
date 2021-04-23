package com.example.chitchat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.pojos.Mensaje
import kotlinx.android.synthetic.main.item_mensaje.view.*

class MessageAdapter(private val user: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var mensajes: List<Mensaje> = emptyList()

    fun setData(list: List<Mensaje>){
        mensajes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_mensaje,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = mensajes[position]

        if(user == message.from){
            holder.itemView.miMensajeLayout.visibility = View.VISIBLE
            holder.itemView.extMensajeLayout.visibility = View.GONE

            holder.itemView.miMensajeTextView.text = message.message
        } else {
            holder.itemView.miMensajeLayout.visibility = View.GONE
            holder.itemView.extMensajeLayout.visibility = View.VISIBLE

            holder.itemView.extMensajeTextView.text = message.message
        }

    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}