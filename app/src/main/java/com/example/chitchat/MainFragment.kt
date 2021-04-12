package com.example.chitchat
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val correo = requireArguments().getString("correo")
        Toast.makeText(context, correo, Toast.LENGTH_SHORT).show()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}