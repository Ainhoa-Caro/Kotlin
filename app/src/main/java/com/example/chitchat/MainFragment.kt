package com.example.chitchat
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.chitchat.pojos.Conversacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class MainFragment : Fragment() {

    private lateinit var btnNewConvOneToOne: Button
    private lateinit var editTextMailOtroUser: EditText
    private lateinit var btnOldConvOneToOne: Button
    private lateinit var editTextIdChatOld: EditText

    //Variable de enlace con base de datos
    private val auth: FirebaseAuth = Firebase.auth
    private val database: DatabaseReference = Firebase.database.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var correo = requireArguments().getString("correo")
        //Guardado de datos
        val sharedPref = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
        if (sharedPref != null) {
            Toast.makeText(context, correo, Toast.LENGTH_SHORT).show()
            sharedPref.putString("correo", correo)
            sharedPref.apply()
        }

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        //Declaración y acciones del botón nueva ConvOneToOne
        btnNewConvOneToOne = root.findViewById<Button>(R.id.btnNewConvOneToOne)
        //Mail del otro user (PROVISIONAL):
        editTextMailOtroUser = root.findViewById<EditText>(R.id.editTextMailOtroUser);
        accionBntNewConvOneToOne(root)


        //Declaración y acciones del botón vieja ConvOneToOne
        btnOldConvOneToOne = root.findViewById<Button>(R.id.btnOldConvOneToOne)
        //Id de chat existente (PROVISIONAL):
        editTextIdChatOld = root.findViewById<EditText>(R.id.editTextIdChatOld);
        accionBntOldConvOneToOne(root, editTextIdChatOld)

        return root
    }

    /*
    CHAT NUEVO
     */
    private fun accionBntNewConvOneToOne(root: View) {

        //Al pulsar el botón...
        btnNewConvOneToOne.setOnClickListener {

            //Creamos objeto Conversación, contendrá la Id del chat/conversación
            var conversacion = crearObjetoConversacion()

            //Creamos HashMap del chat para meterlo en la BD.
            var chat = crearHashMapChat(conversacion)

            //Guardamos nuevo Chat en BD y navegamos al ChatOneToOneFragment
            guardarChatyNavegar(root, conversacion, chat)
        }
    }


    private fun crearObjetoConversacion(): Conversacion {
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
                name = "Chat de $mailUserLogueado con ${editTextMailOtroUser.text}",
                userLoginId = userLogueadoId,
                otherUserId = otroUserId
        )
        return conversacion
    }

    private fun crearHashMapChat(conversacion: Conversacion): HashMap<String, String?> {
        var chat = HashMap<String, String?>()
        chat["Nombre chat"] = conversacion.name
        chat["Id Usuario Logueado"] = conversacion.userLoginId
        chat["Id otro usuario"] = conversacion.otherUserId
        return chat
    }

    private fun guardarChatyNavegar(root: View, conversacion: Conversacion, chat: HashMap<String, String?>) {
        activity?.let {
            //Agregamos nuevo chat a la BD, además...
            //FALTA:
            // Cada user debería tener un campo que incluyera todos los chats en los que está participando.
            database.child("chatsOneToOne").child(conversacion.id).setValue(chat).addOnCompleteListener(it) { task1 ->
                if (task1.isSuccessful) {
                    // Si todo está OK nos lleva al Fragment de Conversación One To One.
                        // Al que le pasamos por parámetro el id de la nueva conversacion/chat (ver mobile_navigation.xml)
                    val action = MainFragmentDirections.actionMainFragmentToChatOneToOneFragment(idChat = conversacion.id)
                    Navigation.findNavController(root).navigate(action)
                } else {
                    //Aquí pondríamos un mensaje de error
                }
            }
        }
    }

    /*
    CHAT EXISTENTE:
     */
    private fun accionBntOldConvOneToOne(root: View, idChatOld: EditText) {

        //Al pulsar el botón...
        btnOldConvOneToOne.setOnClickListener {
            // Nos lleva al Fragment de Conversación One To One.
            // Al que le pasamos por parámetro el id de la antigua conversacion/chat (EditText).
            val action = MainFragmentDirections.actionMainFragmentToChatOneToOneFragment(idChatOld.text.toString())
            Navigation.findNavController(root).navigate(action)
        }
    }


}

