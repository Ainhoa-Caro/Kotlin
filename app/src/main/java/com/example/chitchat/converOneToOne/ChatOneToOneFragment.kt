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
import com.example.chitchat.adapters.MensajesAdapter
import com.example.chitchat.pojos.Mensaje
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
    private lateinit var msjRecyclerView: RecyclerView

    //Guardamos la instancia de acceso al nodo raíz de nuestra BD en Firebase.
    private val databaseRef: DatabaseReference = Firebase.database.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //Guardamos vista enlazando el Fragment(parte Kotlin) con su Layout(parte Xml).
        val rootView: View = inflater.inflate(R.layout.fragment_chat_one_to_one, container, false)

        //Método en el que recogemos el parámetro recibido.
        recogerParametrosRecibidos()

        //Método en el que relacionamos variables Kotlin (botónes,Recycler,etc) con sus referencias del Layout(Xml).
        relacionarElementosLayout(rootView)

        //Guardamos Id de usuario Logueado.
        userLogueadoId = Firebase.auth.currentUser.uid

        //Inicializamos vistas.
        initViews()

        //Devolvemos vista ya construida para su visualización.
        return rootView

    }


    //Método en el que recogemos el parámetro recibido y lo mostramos en un Toast.
    private fun recogerParametrosRecibidos() {
        //Guardamos parámetro recibido (idChat = conversacion.id) -> Chat que debemos mostrar.
        chatId = requireArguments().getString("idChat").toString()
        //Toast de comprobación -> Muestra el parámetro recibido (id del chat abierto).
        Toast.makeText(context, chatId, Toast.LENGTH_LONG).show()
    }


    //Método en el que relacionamos variables Kotlin con objetos del Layout(Xml).
    private fun relacionarElementosLayout(rootView: View) {
        //Botón "Enviar" mensaje:
        enviarMsjButton = rootView.findViewById(R.id.enviarMsjButton)
        //TextField donde escribiremos mensaje:
        msjTextField = rootView.findViewById(R.id.msjTextField)
        //RecyclerView donde se mostrarán todos los mensajes.
        msjRecyclerView = rootView.findViewById(R.id.msjRecylerView)
    }


    private fun initViews() {
        //Configuramos LayoutManager, es decir, la organización de los elementos ViewHolders(mensajes) dentro del RecyclerView.
        //LinearLayoutManager -> Dispone los elementos ViewHolders(mensajes) en una lista unidimensional.
        msjRecyclerView.layoutManager = LinearLayoutManager(context)
        //Configuración de RecyclerView con su adaptador.
        //El adaptador se encargará de crear y asociar datos a cada ViewHolder(mensaje) dentro del RecyclerView.
        //Enviamos por parámetro el Id del user Logueado.
        msjRecyclerView.adapter = MensajesAdapter(userLogueadoId)


        //Acción al pulsar "Enviar Mensaje" -> método "enviarMensaje"
        enviarMsjButton.setOnClickListener { enviarMensaje() }

        //Obtenemos referencia de una query hacia los mensajes del chat existente en Firebase (a través de su id)
        //ordenados por el campo "time" que está dentro del campo "fecha".
        val refMensajesFireBase = FirebaseDatabase.getInstance()
                .getReference("/chatsOneToOne/$chatId/mensajes")
                .orderByChild("fecha/time")

        //Método en el que iniciamos y configuramos el oyente de cambios en la conversación.
        iniciarOyenteChat(refMensajesFireBase)

    }


    private fun iniciarOyenteChat(refMensajesFireBase: Query) {
        //Con el método "addValueEventListener" agregamos un oyente a la instantanea
        // de la "refMensajesFireBase (lista de mensajes en FireBase)".
        refMensajesFireBase.addValueEventListener(object : ValueEventListener {

            //Entraremos en este método cada vez que se actualice la instantanea de
            // la referencia (refMensajesFireBase), esta se actualiza dentro del método "enviarMensaje".
            //Recibe por parámetro la instantanea de la referencia (contiene los mensajes).
            override fun onDataChange(snapshot: DataSnapshot) {
                //Creamos lista de mensajes vacía por el momento.
                var mensajeList: MutableList<Mensaje> = mutableListOf()
                //Iteramos por cada nodo hijo de la instantanea, es decir, por cada mensaje.
                snapshot.children.forEach {
                    // Y vamos creando una instancia de la clase Mensaje de cada uno de ellos.
                    var mensaje = it.getValue(Mensaje::class.java)!!
                    //Si NO es nulo, lo añadimos a la lista de objetos Mensaje.
                    if (mensaje != null) {
                        mensajeList.add(mensaje)
                    }
                }
                //Si la lista de mensajes NO es nula añadimos la lista al Adapter.
                if (mensajeList != null) {
                    (msjRecyclerView.adapter as MensajesAdapter).anadirDatos(mensajeList)
                }
            }

            //Entraremos en este método en el caso de que haya algún problema
            // en FireBase al obtener la referencia.
            override fun onCancelled(descripcionError: DatabaseError) {
                Log.d(":::mensajePropio", "Error onCancelled !!! -> $descripcionError")
            }
        })
    }


    private fun enviarMensaje() {

        //Si el texto del mensaje tiene carácteres y estos NO son solo espacios en blanco...
        if (msjTextField.text.toString().isNotBlank()){

            //Creamos nuevo objeto Mensaje.
            val objMensaje = Mensaje(
                    contenidoMensaje = msjTextField.text.toString(),
                    emisorId = userLogueadoId
            )

            //Generamos un Id de mensaje aleatorio.
            var mensajeId = UUID.randomUUID().toString()

            //Añadimos el nuevo objeto mensaje a FireBase:
            // Buscando nodo padre:"chatsOneToOne" mediante su Id(chatId).
            // Buscando nodo hijo:"mensajes".
            // Y añadiendo ahí el objeto "Mensaje" como valor y el "mensajeId" generado antes como clave.
            databaseRef.child("chatsOneToOne").child(chatId)
                    .child("mensajes")
                    .child(mensajeId).setValue(objMensaje)
        }else{
            Toast.makeText(context, "El mensaje debe tener contenido!", Toast.LENGTH_LONG).show()
        }

        //Limpiamos texto del TextView.
        msjTextField.setText("")

        /*PRUEBA VER NICK DE EMISOR*/

        // Buscamos "NickGlobal" de usuario que escribe:
        // Buscando nodo padre:"users" mediante su Id(userLogueadoId).
        // Buscando nodo hijo:"Nick Global".
       // var nickUserLog: String = FirebaseDatabase.getInstance().getReference("/chatsOneToOne/$userLogueadoId/Nick Global").getString()
        //Toast.makeText(context, nickUserLog, Toast.LENGTH_LONG).show()
    }

}//FIN Fragment ChatOneToOneFragment.