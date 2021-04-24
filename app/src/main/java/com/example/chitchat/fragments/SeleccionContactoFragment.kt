package com.example.chitchat.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.adaptadores.ContactosAdapter
import com.example.chitchat.pojos.User
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

        initRecyclerView(root)

        return root
    }


    //Create recyclerview witch contact list
    private fun initRecyclerView(view: View) {
        //Create recyclerView
        recyclerViewContactos = view.findViewById(R.id.recyclerViewListaContactos)
        recyclerViewContactos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

        var userList: ArrayList<User> = ArrayList()
        var user1: User = User("Sergio", "sergio@gmail.com", "spradox", "11111", null, "caca", 6666666)
        var user2: User = User("Edu", "sesdfsdfsd@gmail.com", "sedrfede", "si", null, "isdhfuiosd", 66456466)
        userList.add(user1)
        userList.add(user2)

        val adapter = ContactosAdapter(userList)
        recyclerViewContactos.adapter = adapter





    }
}


    /*
    private fun getListaContactosFromDB(): ArrayList<User> {

        val ref = FirebaseDatabase.getInstance().getReference("/users")
        var userList: ArrayList<User> = ArrayList()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d(":::usuarios", it.toString())
                    val user: User? = it.getValue(User::class.java) as User
                    Log.d(":::usuariosUsuario", user.toString())
                    userList.add(user!!)
                    Log.d(":::usuariosList", userList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return userList
    }
}



        var user: User? = null  // declare user object outside onCreate Method

        var ref = FirebaseDatabase.getInstance().getReference("Users")

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user = dataSnapshot.value as User
                textView.text = user?.getName()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        }
        ref.addListenerForSingleValueEvent(menuListener)
    }

     */




