package com.example.chitchat.adaptadores

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R

//Coger de la BD una lista con los datos de las conversaciones
class ConversationsAdapter (private val conversationList: List<Uri>): RecyclerView.Adapter<ConversationsAdapter.ConversationsHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ConversationsHolder(
                layoutInflater.inflate(
                        R.layout.item_list_conversaciones,
                        parent,
                        false
                )
        )
    }

    //Bind holder actions
    override fun onBindViewHolder(holder: ConversationsHolder, position: Int) {
        /*
        holder.cardView.setOnClickListener {
        //Llevar a la ventana de la conevrsacion (para escribir y leer)
        }

        holder.imageView.setImageURI(imagesList[position])
        //Poner imagen del contacto

        //Escribir los datos correspondientes en los respectivos campos
        holder.nombreContacto.setText()
        holder.resumenMensaje.setText()
        holder.fechaUltMensaje.setText()
        holder.numMensajesNoLeidos.setText()

         */
    }

    //Get list size
    override fun getItemCount(): Int {
        return conversationList.size
    }

    class ConversationsHolder(view: View):RecyclerView.ViewHolder(view){
        /*
        val cardView = view.findViewById<View>(R.id.cardViewConversacion)
        val imageViewContacto: ImageView = view.findViewById(R.id.imageViewFotoContacto)
        val nombreContacto = view.findViewById<View>(R.id.textView_NombreContacto) as TextView
        val resumenMensaje = view.findViewById<View>(R.id.textViewResumenMensaje) as TextView
        val fechaUltMensaje = view.findViewById<View>(R.id.textViewFechaUltMensaje)  as TextView
        val numMensajesNoLeidos = view.findViewById<View>(R.id.textViewMensajesNoLeidos) as TextView


         */
    }
}