package com.example.chitchat.converOneToOne

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chitchat.R
import com.example.chitchat.adapters.MessageAdapter
import com.example.chitchat.login.LoginFragmentDirections
import com.example.chitchat.pojos.Conversacion
import com.example.chitchat.pojos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chat_one_to_one.*
import java.util.*

class ChatOneToOneFragment : Fragment() {
    //Variables:
    private lateinit var chatId: String
    private lateinit var userLogueadoId: String


    //Variable de enlace con base de datos
    private val auth: FirebaseAuth = Firebase.auth
    private val database : DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        chatId = requireArguments().getString("idChat").toString()
        //Toast de comprobación paso de parámetro:
        Toast.makeText(context, chatId, Toast.LENGTH_LONG).show()

        userLogueadoId = auth.currentUser.getUid()

        //Enlazamos Fragment con su Layout
        return inflater.inflate(R.layout.fragment_chat_one_to_one, container, false)

    //initViews()
    }
    /Configuración de RecyclerView con su MensajeAdapter
    private fun initViews(){




        msjRecylerView.layoutManager = LinearLayoutManager(this)
        msjRecylerView.adapter = MessageAdapter(user)

        enviarMsjButton.setOnClickListener { sendMessage() }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("mensajes").orderBy("dob", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { messages ->
                    val listMessages = messages.toObjects(Mensaje::class.java)
                    (msjRecylerView.adapter as MessageAdapter).setData(listMessages)
                }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
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
                from = user

                //Meter un comprobador isEmpty???

        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        msjTextField.setText("")


    }



}