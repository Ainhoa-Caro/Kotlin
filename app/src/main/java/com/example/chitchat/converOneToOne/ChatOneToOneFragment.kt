package com.example.chitchat.converOneToOne

import android.os.Bundle
import android.util.Log
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
import com.example.chitchat.adapters.MensajeAdapter
import com.example.chitchat.pojos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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


    private fun initViews() {
        //Configuración de RecyclerView con su MensajeAdapter
        msjRecylerView.layoutManager = LinearLayoutManager(context)
        msjRecylerView.adapter = MensajeAdapter(userLogueadoId)


        //Cuando pulsemos el botón enviar vamos a "enviarMensaje"
        enviarMsjButton.setOnClickListener { enviarMensaje() }

       val ref = FirebaseDatabase.getInstance().getReference("/chatsOneToOne/"+chatId+"/mensajes").orderByChild("fecha/time")

        ref.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var mensajeList: MutableList<Mensaje> = mutableListOf()
                snapshot.children.forEach {
                    var mensaje = it.getValue(Mensaje::class.java)!!

                    if (mensaje != null) {
                        mensajeList?.add(mensaje)
                    }
                    if (mensajeList != null) {
                        (msjRecylerView.adapter as MensajeAdapter).setData(mensajeList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(":::mensaj", "Error ennnnn onCancelled !!!!!!!!!!!!")
            }
        })




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
        }


}