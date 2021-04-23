package com.example.chitchat.converOneToOne

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chitchat.R
import com.example.chitchat.adapters.MessageAdapter
import com.example.chitchat.pojos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chat_one_to_one.*
import java.util.*

class ChatOneToOneFragment : Fragment() {
    //Variables:
    private lateinit var chatId: String
    private lateinit var userLogueadoId: String

    //Variables de enlace con base de datos
    private val auth: FirebaseAuth = Firebase.auth
    private val database : DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Recibimos parámetro
        chatId = requireArguments().getString("idChat").toString()
        //Toast de comprobación paso de parámetro:
        Toast.makeText(context, chatId, Toast.LENGTH_LONG).show()

        userLogueadoId = auth.currentUser.getUid()

        //Enlazamos Fragment con su Layout
        return inflater.inflate(R.layout.fragment_chat_one_to_one, container, false)

    initViews()
    }

    //Configuración de RecyclerView con su MensajeAdapter
    private fun initViews(){
        msjRecylerView.layoutManager = LinearLayoutManager(context)
        msjRecylerView.adapter = MessageAdapter(userLogueadoId)

        //Cuando pulsemos el botón enviar vamos a "enviarMensaje"
        enviarMsjButton.setOnClickListener { sendMessage() }




        database.child("chats").child(chatId).child("mensajes").orderByChild("fecha")
            .get()
            .addOnSuccessListener { messages ->
                val listaMensajes: List<Mensaje>? = messages.getValue(object : GenericTypeIndicator<List<Mensaje>>(){})
                if (listaMensajes != null) {
                        (msjRecylerView.adapter as MessageAdapter).setData(listaMensajes)
                }
            }

        database.child("chats").child(chatId).child("mensajes").orderByChild("fecha")
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(Mensaje::class.java)
                        (msjRecylerView.adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }
    }

    private fun sendMessage(){
        val message = Mensaje(
                message = msjTextField.text.toString(),
                from = userLogueadoId

                //Meter un comprobador isEmpty???

        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        msjTextField.setText("")


    }



}