package com.example.chitchat.converOneToOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.adapters.MessageAdapter
import com.example.chitchat.pojos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class ChatOneToOneFragment : Fragment() {
    //Variables:
    private lateinit var chatId: String
    private lateinit var userLogueadoId: String
    private lateinit var msjTextField: EditText
    private lateinit var enviarMsjButton: Button
    private lateinit var msjRecylerView: RecyclerView
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
        val root = inflater.inflate(R.layout.fragment_chat_one_to_one, container, false)

        //Declaración y acciones del botón Enviar
        enviarMsjButton = root.findViewById<Button>(R.id.enviarMsjButton)
        //Texto a enviar:
        msjTextField = root.findViewById<EditText>(R.id.msjTextField);

        msjRecylerView = root.findViewById<RecyclerView>(R.id.msjRecylerView)

        initViews()

        return root

    }

    //Configuración de RecyclerView con su MensajeAdapter
    private fun initViews() {
        msjRecylerView.layoutManager = LinearLayoutManager(context)
        msjRecylerView.adapter = MessageAdapter(userLogueadoId)


        //Cuando pulsemos el botón enviar vamos a "enviarMensaje"
        enviarMsjButton.setOnClickListener { enviarMensaje() }


        database.child("chatsOneToOne").child(chatId).child("mensajes")
                .get()
                .addOnSuccessListener { mensajes ->
                    val listaMensajes = mensajes.children

                        if (listaMensajes != null) {
                            Toast.makeText(context, "ListMensajes tiene algo", Toast.LENGTH_LONG).show()
                            // (msjRecylerView.adapter as MessageAdapter).setData(listaMensajes)
                        }

                    //FALTAAAAA
                }

    }

    private fun enviarMensaje() {
        val mensaje = Mensaje(
                message = msjTextField.text.toString(),
                from = userLogueadoId

                //Meter un comprobador isEmpty???
        )
        var mensajeId = UUID.randomUUID().toString()
        database.child("chatsOneToOne").child(chatId).child("mensajes").child(mensajeId).setValue(mensaje)

        msjTextField.setText("")
        Toast.makeText(context, "llegamos3", Toast.LENGTH_LONG).show()
    }

}