package com.example.chitchat.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.chitchat.R
import com.example.chitchat.pojos.User
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment : Fragment() {
    private lateinit var btgoogle: Button
    private lateinit var btfacebook: Button
    private lateinit var btlogin: Button
    private lateinit var btregistro: Button
    private lateinit var tvrecuperar: TextView
    private val GOOGLE_SIGN_IN = 100
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_login, container, false)
        btgoogle = root.findViewById<Button>(R.id.boton_google)
        btfacebook = root.findViewById<Button>(R.id.boton_facebook)
        btlogin = root.findViewById<Button>(R.id.Login_boton)
        btregistro = root.findViewById<Button>(R.id.Registrarse_boton)
        tvrecuperar = root.findViewById<TextView>(R.id.RecuperarContraseña_textView)
        setup(root)
        sesion(root)
        return root
    }//Fin del Oncreate

    override fun onStart() {
        super.onStart()
        general.visibility = View.VISIBLE
    }

    @SuppressLint("ResourceType")
    private fun setup(root: View) {
        // Boton que inicia sesion con Google
        btgoogle.setOnClickListener {
            val configuraciongoogle = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail().requestId()
                    .build()
            val cliente = activity?.let { it1 -> GoogleSignIn.getClient(it1, configuraciongoogle) }
            cliente?.signOut()
            startActivityForResult(cliente?.signInIntent, GOOGLE_SIGN_IN)


        }
        //Boton que inicia sesion con Facebook
        btfacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult?) {
                            result?.let {
                                val token = it.accessToken
                                val credencial = FacebookAuthProvider.getCredential(token.token)
                                FirebaseAuth.getInstance().signInWithCredential(credencial)
                                        .addOnCompleteListener { task2 ->
                                            if (task2.isSuccessful) {
                                                task2.result?.user?.email
                                                val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(task2.result?.user?.email)
                                                NavHostFragment.findNavController(this@LoginFragment).navigate(action)


                                            }
                                        }
                            }
                        }

                        override fun onCancel() {
                            Alerta()
                        }

                        override fun onError(error: FacebookException?) {
                            Alerta()
                        }
                    })
        }
        //Boton que incia  sesion con usuario y contraseña
        btlogin.setOnClickListener {
            if (Correo_edittext.text.toString().isEmpty() || Password_edittext.text.toString()
                            .isEmpty()
            ) {
                Toast.makeText(context, "Debe rellenar los campos", Toast.LENGTH_SHORT).show()
            } else {
                activity?.let { it1 ->
                    auth.signInWithEmailAndPassword(Correo_edittext.text.toString(), Password_edittext.text.toString()).addOnCompleteListener(it1) { task ->
                        if (task.isSuccessful) {
                            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(Correo_edittext.text.toString())
                            Navigation.findNavController(root).navigate(action)

                        } else {
                            Alerta()
                        }
                    }
                }
            }
        }
        //Boton que manda al fragment de registro
        btregistro.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registroFragment)
        }
        //Boton que te mante un correo con tu contraseña
        tvrecuperar.setOnClickListener {
            Log.d("MainActivity", "Recuperar")

        }
    }
//Permite tener una sesion iniciada
   private fun sesion(root: View) {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = sharedPref?.getString("correo", null)
        if (email != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(email)
            NavHostFragment.findNavController(this).navigate(action)

        }
    }


    //Se muestra cuando no se puede iniciar sesion
    private fun Alerta() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Error")
        builder?.setMessage("Se ha producido un error autentificando al usuario")
        builder?.setPositiveButton("Aceptar", null)
        val dialog = builder?.create()
        dialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("MainActivity", account.toString())
                if (account != null) {
                    val credencial = GoogleAuthProvider.getCredential(account.idToken, null)

                    activity?.let {
                        FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener(
                                it
                        ) { task ->
                            if (task.isSuccessful) {
                                val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(account.email)
                                NavHostFragment.findNavController(this).navigate(action)
                            } else {
                                Alerta()
                            }
                        }
                    }
                }
            } catch (e: ApiException) {
                Alerta()
            }
        }

    }



}