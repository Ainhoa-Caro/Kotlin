package com.example.chitchat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    //Rama eduardo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        Login_boton.setOnClickListener {

            val email = Correo_edittext.text.toString()
            val password = Password_edittext.text.toString()

            Log.d("MainActivity","El email es: " +email)
            Log.d("MainActivity","Password: $password")

        }
        Registrarse_boton.setOnClickListener{
            Log.d("MainActivity","Recuperar")

        }
        RecuperarContraseña_textView.setOnClickListener {
            Log.d("MainActivity","Recuperar")

        }


    }
}