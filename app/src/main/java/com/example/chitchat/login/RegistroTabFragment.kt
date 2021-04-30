package com.example.chitchat.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.chitchat.R
import com.example.chitchat.pojos.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistroTabFragment: Fragment() {
    //Declaracion de variables
    private var imageUri: Uri? = null
    private lateinit var nombreusuario: EditText
    private lateinit var email: EditText
    private lateinit var passwordone: EditText
    private lateinit var passwordtwo: EditText
    private lateinit var imgfoto: ImageView
    private lateinit var btregistro: Button
    //Autentificacion y registro en la base de datos
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.registro_fragment, container, false)
        nombreusuario = root.findViewById<EditText>(R.id.Nombre_edittext)
        email = root.findViewById<EditText>(R.id.Correo_edittext)
        passwordone = root.findViewById<EditText>(R.id.Passwordone_edittext)
        passwordtwo = root.findViewById<EditText>(R.id.Passwordotwo_edittext)
        imgfoto= root.findViewById<ImageView>(R.id.Seleccionfoto_imagenView)
        btregistro = root.findViewById<Button>(R.id.Registrarse_boton)
        setup(root)
        return root
    }
    private fun setup(root: View) {
        btregistro.setOnClickListener {
            comprobarcampos(nombreusuario, email, passwordone, passwordtwo, imageUri)

        }
        //Sirve para cambiar la foto, se ha añadido un evento OnClick al ImagenView
        imgfoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }
    private fun comprobarcampos(nombreusuario: EditText, email: EditText, passwordone: EditText, passwordtwo: EditText, foto: Uri?) {
        var nickglobal: String? = null
        var informacion: String? = null
        var telefono: Int? = null
        var usuario: User = User(nombreusuario.text.toString(), email.text.toString(), nickglobal, passwordone.text.toString(), foto, informacion, telefono)
        //Añadir cambio de colores y mejoras
        if (nombreusuario.text.toString().isEmpty() || email.text.toString().isEmpty() || passwordone.text.toString().isEmpty() || passwordtwo.text.toString().isEmpty()) {
            Toast.makeText(context, "Faltan campos que rellenar", Toast.LENGTH_SHORT).show()
        } else {
            if (passwordone.text.toString().length < 6) {
                Toast.makeText(context, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                if (!passwordone.text.toString().equals(passwordtwo.text.toString())) {
                    Toast.makeText(context, "Los password no coinciden", Toast.LENGTH_SHORT).show()
                } else {
                    registrarUser(usuario)
                }
            }

        }
    }
    //Funcion para registrar usuarios
    public fun registrarUser(usuario: User) {
        activity?.let {
            auth.createUserWithEmailAndPassword(usuario.email, usuario.password)
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Cuenta creada", Toast.LENGTH_SHORT).show()
                            nombreusuario.setText("")
                            email.setText("")
                            passwordone.setText("")
                            passwordtwo.setText("")
                            val usuarios = HashMap<String, String?>()
                            usuarios["nombreusuario"] = usuario.nombreusuario
                            usuarios["email"] = usuario.email
                            usuarios["nickglobal"] = usuario.nickglobal.toString()
                            usuarios["password"] = usuario.password
                            usuarios["foto"] = usuario.foto.toString()
                            usuarios["informacion"] = usuario.informacion.toString()
                            usuarios["telefono"] = usuario.telefono.toString()
                            var id: String = auth.currentUser.getUid()
                            database.child("users").child(id).setValue(usuarios).addOnCompleteListener(it) { task2 ->
                                if (task2.isSuccessful) {
                                    NavHostFragment.findNavController(this).popBackStack()
                                }
                            }
                        }else{
                            Alerta()
                        }
                    }
        }
    }
    //Esta funcion, es llamada al pulsar en el boton Seleccionfoto_imagenView. Linea 41
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            imageUri = data!!.data!!
            imgfoto.setImageURI(imageUri)
        }



    }
    private fun Alerta(){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Error")
        builder?.setMessage("Se ha producido un error autentificando al usuario")
        builder?.setPositiveButton("Aceptar",null)
        val dialog = builder?.create()
        dialog?.show()
    }

}