package com.example.chitchat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.chitchat.MainActivity
import com.example.chitchat.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*

class LoginFragment : Fragment() {
    private val GOOGLE_SIGN_IN = 100
    private var auth: FirebaseAuth = Firebase.auth
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val btgoogle=root.findViewById<View>(R.id.boton_google)
        val btfacebook=root.findViewById<View>(R.id.boton_fb)
        val btlogin=root.findViewById<View>(R.id.Login_boton)
        val btregistro=root.findViewById<View>(R.id.Registrarse_boton)
        val tvrecuperar=root.findViewById<View>(R.id.RecuperarContraseña_textView)
        // Boton que inicia sesion con Google
        ///////////////////////////Por aki
        btgoogle.setOnClickListener {
            val configuraciongoogle = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().requestId()
                .build()
            val cliente = activity?.let { it1 -> GoogleSignIn.getClient(it1,configuraciongoogle) }
            cliente?.signOut()
            startActivityForResult(cliente?.signInIntent,GOOGLE_SIGN_IN)

        }

        //Boton que inicia sesion con Facebook
        btfacebook.setOnClickListener {
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
                            context,
                            "No se pudo iniciar sesion con Facebook",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })


        }
        btlogin.setOnClickListener {
            if (Correo_edittext.text.toString().isEmpty() || Password_edittext.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(context, "Debe rellenar los campos", Toast.LENGTH_SHORT).show()

            } else {
                activity?.let { it1 ->
                    auth.signInWithEmailAndPassword(
                        Correo_edittext.text.toString(),
                        Password_edittext.text.toString()
                    )
                        .addOnCompleteListener(it1) { task ->
                            if (task.isSuccessful) {
                              //Navegacion a la ventana de Main Activity
                                  Log.d("MainActivity","error")
                                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_mainFragment)

                            } else {
                                Log.d("MainActivity","error")
                                Alerta()
                            }
                        }
                }
            }
        }
        btregistro.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registroFragment)
        }
        tvrecuperar.setOnClickListener {
            Log.d("MainActivity", "Recuperar")

        }
        return root
    }
    private fun Alerta(){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Error")
        builder?.setMessage("Se ha producido un error autentificando al usuario")
        builder?.setPositiveButton("Aceptar",null)
        val dialog = builder?.create()
        dialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)
                    activity?.let {
                        auth.signInWithCredential(credencial).addOnCompleteListener(it) { task ->
                            if (task.isSuccessful) {
                                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_mainFragment)
                            }else{
                                Alerta()
                            }
                        }
                    }
                }
            }catch(e: ApiException){
                Alerta()
            }
        }
    }




}