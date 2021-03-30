package com.example.chitchat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chitchat.MainActivity
import com.example.chitchat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Login_boton.setOnClickListener {
            if (Correo_edittext.text.toString().isEmpty() || Password_edittext.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "Debe rellenar los campos", Toast.LENGTH_SHORT).show()

            } else {
                auth.signInWithEmailAndPassword(
                    Correo_edittext.text.toString(),
                    Password_edittext.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Usuario o Password incorrecto", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        Registrarse_boton.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }
        RecuperarContraseña_textView.setOnClickListener {
            Log.d("MainActivity", "Recuperar")

        }
    }

}