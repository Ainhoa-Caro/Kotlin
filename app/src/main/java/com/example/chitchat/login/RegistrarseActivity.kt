package com.example.chitchat.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chitchat.MainActivity
import com.example.chitchat.R
import com.example.chitchat.pojos.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registrarse.*

class RegistrarseActivity : AppCompatActivity() {

    //Declaracion de variables
    private var imageUri: Uri? = null
    private lateinit var nombreusuario: EditText
    private lateinit var email: EditText
    private lateinit var passwordone: EditText
    private lateinit var passwordtwo: EditText

    //Autentificacion y registro en la base de datos
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        nombreusuario = findViewById<EditText>(R.id.Nombre_edittext)
        email = findViewById<EditText>(R.id.Correo_edittext)
        passwordone = findViewById<EditText>(R.id.Passwordone_edittext)
        passwordtwo = findViewById<EditText>(R.id.Passwordotwo_edittext)

        //Boton para volver a la pantalla de login
        Volver_boton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Boton que sirve para registrar usuarios
        Registrarse_boton.setOnClickListener {
            comprobarcampos(nombreusuario, email, passwordone, passwordtwo, imageUri)

        }
        //Sirve para cambiar la foto, se ha añadido un evento OnClick al ImagenView
        Seleccionfoto_imagenView.setOnClickListener {
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
                Toast.makeText(this, "Faltan campos que rellenar", Toast.LENGTH_SHORT).show()
            } else {
                if(passwordone.text.toString().length <6){
                    Toast.makeText(this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }else{
                    if(!passwordone.text.toString().equals(passwordtwo.text.toString())){
                        Toast.makeText(this, "Los password no coinciden", Toast.LENGTH_SHORT).show()
                    }else{
                        registrarUser(usuario)
                    }
                }

            }
    }
    //Funcion para registrar usuarios
    private fun registrarUser(usuario: User) {
        auth.createUserWithEmailAndPassword(usuario.email, usuario.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()
                    val usuarios = HashMap<String, String?>()
                    usuarios["Nombre usuario"] = usuario.nombreusuario
                    usuarios["Email"] = usuario.email
                    usuarios["Nick Global"] = usuario.nickglobal.toString()
                    usuarios["Password"] = usuario.password
                    usuarios["Foto"] = usuario.foto.toString()
                    usuarios["Informacion"] = usuario.informacion.toString()
                    usuarios["Telefono"] = usuario.telefono.toString()
                    var id: String = auth.currentUser.getUid()
                    database.child("users").child(id).setValue(usuarios).addOnCompleteListener(this) { task2 ->
                            if (task2.isSuccessful) {
                                finish()
                            }
                        }
                }else{
                    Toast.makeText(this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //Esta funcion, es llamada al pulsar en el boton Seleccionfoto_imagenView. Linea 41
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUri = data!!.data!!
        Seleccionfoto_imagenView.setImageURI(imageUri)
    }

}