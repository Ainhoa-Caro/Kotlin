package com.example.chitchat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chitchat.MainActivity
import com.example.chitchat.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Boton que inicia sesion con Google
        ///////////////////////////Por aki
        boton_google.setOnClickListener {
            val configuraciongoogle = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()


        }

        //Boton que inicia sesion con Facebook
        boton_fb.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {

                    }

                    override fun onCancel() {
                        TODO("Not yet implemented")
                    }

                    override fun onError(error: FacebookException?) {
                        Toast.makeText(
                            this@LoginActivity,
                            "No se pudo iniciar sesion con Facebook",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })


        }
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
                            Toast.makeText(
                                this,
                                "Usuario o Password incorrecto",
                                Toast.LENGTH_SHORT
                            ).show()
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


