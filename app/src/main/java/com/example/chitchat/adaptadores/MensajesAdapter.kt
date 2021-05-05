package com.example.chitchat.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.pojos.Mensaje
import kotlinx.android.synthetic.main.item_list_mensajes.view.*

class MensajesAdapter(private val user: String): RecyclerView.Adapter<MensajesAdapter.MessageViewHolder>() {

    private var mensajes: List<Mensaje> = emptyList()

    fun setData(list: List<Mensaje>){
        mensajes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_mensajes,
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