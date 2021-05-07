package com.example.chitchat.adaptadores

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chitchat.R
import com.example.chitchat.fragments.MainFragmentDirections
import com.example.chitchat.pojos.Conversacion
import com.example.chitchat.pojos.User
import com.example.chitchat.pojos.UserFromFirebase
import java.util.*

class ContactosAdapter (private val contactList: MutableList<UserFromFirebase>, val context:Context): RecyclerView.Adapter<ContactosAdapter.ContactosHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactosHolder(
                layoutInflater.inflate(
                        R.layout.item_list_contactos,
                        parent,
                        false
                )
        )
    }

    //Bind holder actions
    override fun onBindViewHolder(holder: ContactosHolder, position: Int) {


        holder.cardView.setOnClickListener {

            //crearConversacion(contactList[position].email)
            //Crear conversacion si no existe
            //Viajar a la ventana de conversacion con el id de la conversacion

        }

        /*
        Glide.with(context)
                .load(contactList[position].foto)
                .into(holder.imageViewContacto)


         */

        //holder.imageViewContacto.setImageURI(contactList)
        //Poner imagen del contacto

        //Escribir los datos correspondientes en los respectivos campos
        holder.nombreContacto.text = contactList[position].nombreusuario
        holder.biografiaContacto.text = contactList[position].informacion



    }

    //Get list size
    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactosHolder(view: View): RecyclerView.ViewHolder(view){
        val cardView = view.findViewById<View>(R.id.cardViewContacto)
        val imageViewContacto: ImageView = view.findViewById(R.id.imageViewFotoContacto_Contacto)
        val nombreContacto = view.findViewById<View>(R.id.textView_NombreContacto_ListaContactos) as TextView
        val biografiaContacto = view.findViewById<View>(R.id.textViewBiografiaContacto) as TextView

    }


    /*
    private fun crearConversacion(usuarioDestino: String): Conversacion {



        //Creamos nuevo Id de chat automáticamente:
        var chatId = UUID.randomUUID().toString()
        //Obtenemos id de user logueado
        var userLogueadoId: String = auth.currentUser.getUid()
        //Obtenemos mail de user logueado
        var mailUserLogueado: String? = auth.currentUser.getEmail()
        //Id del otro user (debe venir como parámetro cuando pulsemos en la lista de chats):
        var otroUserId = "Id del otro usuario".toString()

        //Creamos objeto Conversacion
        var conversacion = Conversacion(
                id = chatId,
                name = "Chat de $mailUserLogueado con $usuarioDestino",
                userLoginId = userLogueadoId,
                otherUserId = otroUserId
        )


        return conversacion
    }

     */

}