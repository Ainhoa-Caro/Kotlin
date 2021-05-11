package com.example.chitchat.adaptadores

import android.app.Activity
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
import com.example.chitchat.fragments.SeleccionContactoFragmentDirections
import com.example.chitchat.pojos.Conversacion
import com.example.chitchat.pojos.Mensaje
import com.example.chitchat.pojos.User
import com.example.chitchat.pojos.UserFromFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import java.util.*

class ContactosAdapter (private val contactList: MutableList<UserFromFirebase>, private val context: Context, private val activity:Activity, private val databaseReference: DatabaseReference, private val firebaseAuth: FirebaseAuth): RecyclerView.Adapter<ContactosAdapter.ContactosHolder>(){

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

            val conversacion = crearConversacion(contactList[position].nombreusuario!!, firebaseAuth)
            val chat = crearHashMapChat(conversacion)
            guardarChatyNavegar(holder.cardView, conversacion, chat)
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


    private fun crearHashMapChat(conversacion: Conversacion): HashMap<String, String?> {
        var chat = HashMap<String, String?>()
        chat["name"] = conversacion.name
        chat["userLoginId"] = conversacion.userLoginId
        chat["otherUserId"] = conversacion.otherUserId
        return chat
    }

    private fun guardarChatyNavegar(root: View, conversacion: Conversacion, chat: HashMap<String, String?>) {
        activity?.let {
            //Agregamos nuevo chat a la BD, además...
            //FALTA: //Cada user debería tener un campo que incluyera todos los chats en los que está participando.
            databaseReference.child("chatsOneToOne").child(conversacion.id).setValue(chat).addOnCompleteListener(it) { task1 ->
                if (task1.isSuccessful) {
                    // Si todo está OK nos lleva al Fragment de Conversación One To One.
                    // Al que le pasamos por parámetro el id de la nueva conversacion/chat (ver mobile_navigation.xml)
                    val action = SeleccionContactoFragmentDirections.actionSeleccionContactoFragmentToConversacionOneToOne(conversacion.id)
                    Navigation.findNavController(root).navigate(action)
                } else {
                    //Aquí pondríamos un mensaje de error
                }
            }
        }
    }


private fun crearConversacion(usuarioDestino: String, firebaseAuth: FirebaseAuth): Conversacion {
    //Creamos nuevo Id de chat automáticamente:
    var chatId = UUID.randomUUID().toString()
    //Obtenemos id de user logueado
    var userLogueadoId: String = firebaseAuth.currentUser.uid
    //Obtenemos mail de user logueado
    var mailUserLogueado: String? = firebaseAuth.currentUser.email
    //Id del otro user (debe venir como parámetro cuando pulsemos en la lista de chats):
    var otroUserId = usuarioDestino.toString()

    //Creamos objeto Conversacion
    var conversacion = Conversacion(
            id = chatId,
            name = "Chat de $mailUserLogueado con $usuarioDestino",
            userLoginId = userLogueadoId,
            otherUserId = otroUserId,
            mensajesList = arrayListOf<Mensaje>()
    )


    return conversacion
}

}