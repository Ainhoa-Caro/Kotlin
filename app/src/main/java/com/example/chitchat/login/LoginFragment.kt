package com.example.chitchat.login

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.viewpager.widget.ViewPager
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*



class LoginFragment : Fragment()  {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPackage: ViewPager
    private lateinit var btgoogle: FloatingActionButton
    private lateinit var btfacebook: FloatingActionButton
    private var v= 0
    private val GOOGLE_SIGN_IN = 100
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_login, container, false)
        tabLayout = root.findViewById<TabLayout>(R.id.layout_interno)
        viewPackage = root.findViewById<ViewPager>(R.id.view_pager)
        btgoogle = root.findViewById<FloatingActionButton>(R.id.boton_google)
        btfacebook = root.findViewById<FloatingActionButton>(R.id.boton_facebook)

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = getActivity()?.let { LoginAdapter(it.getSupportFragmentManager(),this.requireContext(),tabLayout.getTabCount()) }

        viewPackage.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPackage)
        btfacebook.setTranslationY(10F)
        btgoogle.setTranslationY(10F)
        tabLayout.setTranslationY(10F)
        btfacebook.setAlpha(v.toFloat())
        btgoogle.setAlpha(v.toFloat())
        tabLayout.setAlpha(v.toFloat())
        btfacebook.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(900).start()
        btgoogle.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(900).start()
        tabLayout.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(900).start()

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
            var nickglobal: String? = null
            var informacion: String? = null
            var telefono: Int? = null
            var foto: Uri? = null
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
                                                task2.result?.user?.displayName
                                                var usuario: User = User(task2.result?.user?.displayName, task2.result!!.user.email, nickglobal, null, foto, informacion, telefono)
                                                registrarUser(usuario)
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


    }
//Permite tener una sesion iniciada
   private fun sesion(root: View) {
        val sharedPref = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = sharedPref?.getString("correo", null)
        if (email != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(null)
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
        var nickglobal: String? = null
        var informacion: String? = null
        var telefono: Int? = null
        var foto: Uri? = null
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
                                var usuario: User = User(account.displayName, account.email!!, nickglobal, null, foto, informacion, telefono)
                                registrarUser(usuario)
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
    //Funcion para registrar usuarios mediante FB y Google
    public fun registrarUser(usuario: User) {
                            val usuarios = HashMap<String, String?>()
                            usuarios["nombreusuario"] = usuario.nombreusuario
                            usuarios["email"] = usuario.email
                            usuarios["nickglobal"] = usuario.nickglobal.toString()
                            usuarios["password"] = usuario.password
                            usuarios["foto"] = usuario.foto.toString()
                            usuarios["informacion"] = usuario.informacion.toString()
                            usuarios["telefono"] = usuario.telefono.toString()
                            var id: String = auth.currentUser.getUid()
                            database.child("users").child(id).setValue(usuarios).addOnCompleteListener() { task2 ->
                                if (task2.isSuccessful) {
                                    NavHostFragment.findNavController(this).popBackStack()
                                }

                        }

    }
}