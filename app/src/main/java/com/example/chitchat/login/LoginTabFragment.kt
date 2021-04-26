package com.example.chitchat.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.chitchat.R

class LoginTabFragment: Fragment() {

    private lateinit var btlogin: Button
    private  lateini
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.login_fragment, container, false)
        btlogin = root.findViewById<Button>(R.id.Login_boton)


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
        return root
    }


}