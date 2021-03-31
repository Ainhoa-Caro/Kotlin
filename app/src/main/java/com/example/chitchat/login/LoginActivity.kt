package com.example.chitchat.login

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chitchat.MainActivity
import com.example.chitchat.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 100
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
                .requestEmail().requestId()
                .build()
            val cliente = GoogleSignIn.getClient(this,configuraciongoogle)
            cliente.signOut()
            startActivityForResult(cliente.signInIntent,GOOGLE_SIGN_IN)

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
                           Alerta()
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
    private fun Alerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credencial).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Alerta()
                        }
                    }
                }
            }catch(e: ApiException){
                Alerta()
            }
        }
    }

}


