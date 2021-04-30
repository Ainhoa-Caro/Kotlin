package com.example.chitchat.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.adaptadores.ContactosAdapter
import com.example.chitchat.pojos.User
import com.example.chitchat.pojos.UserFromFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.File


class SeleccionContactoFragment : Fragment() {

    lateinit var recyclerViewContactos: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_seleccion_contacto, container, false)

        val returnButton = root.findViewById<View>(R.id.imageButtonReturnSeleccionContactos)
        returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).popBackStack()
        }

        getListaContactosFromDB(root)

        return root
    }


    //Create recyclerview with contact list
    private fun initRecyclerView(view: View, userList: MutableList<UserFromFirebase>) {
        //Create recyclerView
        recyclerViewContactos = view.findViewById(R.id.recyclerViewListaContactos)
        recyclerViewContactos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        val adapter = ContactosAdapter(userList, requireContext())
        recyclerViewContactos.adapter = adapter
    }


    //Get data from users in database and create the recyclerView
    private fun getListaContactosFromDB(view: View) {

        //Obtenemos referencia de los mensajes del chat existente
        //en Firebase (a través de su id) y los ordenamos por fecha.
        val refContactosFireBase = FirebaseDatabase.getInstance()
                .getReference("/users")
                .orderByChild("Nombre usuario")

        refContactosFireBase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var usersList: MutableList<UserFromFirebase> = mutableListOf()
                snapshot.children.forEach {
                    var user = it.getValue(UserFromFirebase::class.java)!!

                    if (user != null) {
                        usersList?.add(user)
                    }
                }
                if (usersList != null) {
                    initRecyclerView(view, usersList)
                } else {
                    Toast.makeText(context,"No users found in database", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(":::mensaj", "Error ennnnn onCancelled !!!!!!!!!!!!")
            }
        })
    }
}



