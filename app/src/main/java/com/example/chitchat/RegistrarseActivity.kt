package com.example.chitchat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registrarse.*
import java.util.*
import kotlin.collections.HashMap

class RegistrarseActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    lateinit var nombre: EditText
    lateinit var email: EditText
    lateinit var passwordone: EditText
    lateinit var passwordtwo: EditText
    lateinit var textoemail: String
    lateinit var textopass: String

    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)


        //Boton para volver a la pantalla de login
        Volver_boton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Boton que sirve para registrar usuarios
        Registrarse_boton.setOnClickListener {
            //Firebase Autentication to create a user whith email
            var nombre = findViewById<EditText>(R.id.Nombre_edittext)
            email= findViewById<EditText>(R.id.Correo_edittext)
            var passwordone = findViewById<EditText>(R.id.Passwordone_edittext)
            var passwordtwo  = findViewById<EditText>(R.id.Passwordotwo_edittext)
            textoemail = email.getText().toString()
            textopass = passwordone.getText().toString()
            Log.d(":::TAG", textoemail)
            Log.d(":::TAG", textopass)
            registrarUser(textoemail,textopass)
        }

        //Sirve para cambiar la foto, se ha añadido un evento OnClick al ImagenView
        Seleccionfoto_imagenView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


    }

    //Funcion para registrar usuarios
    private fun registrarUser(email: String, passwordone: String) {
        auth.createUserWithEmailAndPassword(email,passwordone)
          .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var id: String = auth.currentUser.getUid()
                    val prefijos: Map<String, String> =
                        mapOf("nombre" to email, "email" to passwordone)
                    database.child("Users").child(id).setValue(prefijos)
                      .addOnCompleteListener(this) { task2 ->
                           if (task2.isSuccessful) {
                                finish()
                            }
                        }
                }
            }
    }

    //Esta funcion, es llamada al pulsar en el boton Seleccionfoto_imagenView. Linea 41
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUri = data!!.data
        Seleccionfoto_imagenView.setImageURI(imageUri)
    }

}