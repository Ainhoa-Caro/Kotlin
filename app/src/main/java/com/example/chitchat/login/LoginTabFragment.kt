package com.example.chitchat.login

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
import com.example.chitchat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_fragment.*

class LoginTabFragment: Fragment() {

    private lateinit var btlogin: Button
    private lateinit var tvrecuperar: TextView
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.login_fragment, container, false)
        btlogin = root.findViewById<Button>(R.id.Login_boton)
        tvrecuperar = root.findViewById<TextView>(R.id.RecuperarContraseña_textView)
        setup(root)
        return root
    }
    private fun setup(root: View) {
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

        //Boton que te mante un correo con tu contraseña
        tvrecuperar.setOnClickListener {
            Log.d("MainActivity", "Recuperar")

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



}